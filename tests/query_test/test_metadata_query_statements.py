#!/usr/bin/env python
# Copyright (c) 2012 Cloudera, Inc. All rights reserved.
# Impala tests for queries that query metadata and set session settings
import logging
import pytest
from tests.beeswax.impala_beeswax import ImpalaBeeswaxException
from subprocess import call
from tests.common.test_vector import *
from tests.common.impala_test_suite import *

# TODO: For these tests to pass, all table metadata must be created exhaustively.
# the tests should be modified to remove that requirement.
class TestMetadataQueryStatements(ImpalaTestSuite):

  CREATE_DATA_SRC_STMT = ("CREATE DATASOURCE %s "
      "LOCATION '/test-warehouse/data-sources/test-data-source.jar' "
      "CLASS 'com.cloudera.impala.extdatasource.AllTypesDataSource' API_VERSION 'V1'")
  DROP_DATA_SRC_STMT = "DROP DATASOURCE IF EXISTS %s"
  TEST_DATA_SRC_NAMES = ["show_test_ds1", "show_test_ds2"]

  @classmethod
  def get_workload(self):
    return 'functional-query'

  @classmethod
  def add_test_dimensions(cls):
    super(TestMetadataQueryStatements, cls).add_test_dimensions()
    sync_ddl_opts = [0, 1]
    if cls.exploration_strategy() != 'exhaustive':
      # Cut down on test runtime by only running with SYNC_DDL=1
      sync_ddl_opts = [0]

    cls.TestMatrix.add_dimension(create_exec_option_dimension(
        cluster_sizes=ALL_NODES_ONLY,
        disable_codegen_options=[False],
        batch_sizes=[0],
        sync_ddl=sync_ddl_opts))
    cls.TestMatrix.add_dimension(create_uncompressed_text_dimension(cls.get_workload()))

  def __drop_data_sources(self):
    for name in self.TEST_DATA_SRC_NAMES:
      self.client.execute(self.DROP_DATA_SRC_STMT % (name,))

  def __create_data_sources(self):
    self.__drop_data_sources()
    for name in self.TEST_DATA_SRC_NAMES:
      self.client.execute(self.CREATE_DATA_SRC_STMT % (name,))

  def setup_method(self, method):
    self.cleanup_db('hive_test_db')

  def teardown_method(self, method):
    self.cleanup_db('hive_test_db')

  def test_show(self, vector):
    self.run_test_case('QueryTest/show', vector)

  @pytest.mark.execute_serially
  def test_show_data_sources(self, vector):
    try:
      self.__create_data_sources()
      self.run_test_case('QueryTest/show-data-sources', vector)
    finally:
      self.__drop_data_sources()

  def test_show_stats(self, vector):
    self.run_test_case('QueryTest/show-stats', vector)

  def test_describe_table(self, vector):
    self.run_test_case('QueryTest/describe', vector)

  def test_describe_formatted(self, vector):
    # Describe a partitioned table.
    self.exec_and_compare_hive_and_impala_hs2("describe formatted functional.alltypes")
    self.exec_and_compare_hive_and_impala_hs2(
        "describe formatted functional_text_lzo.alltypes")
    # Describe an unpartitioned table.
    self.exec_and_compare_hive_and_impala_hs2("describe formatted tpch.lineitem")
    self.exec_and_compare_hive_and_impala_hs2("describe formatted functional.jointbl")

    try:
      # Describe a view
      self.exec_and_compare_hive_and_impala_hs2(\
          "describe formatted functional.alltypes_view_sub")
    except AssertionError:
      pytest.xfail("Investigate minor difference in displaying null vs empty values")

  def test_use_table(self, vector):
    self.run_test_case('QueryTest/use', vector)

  @pytest.mark.execute_serially
  def test_impala_sees_hive_created_tables_and_databases(self, vector):
    self.client.set_configuration(vector.get_value('exec_option'))
    db_name = 'hive_test_db'
    tbl_name = 'testtbl'
    call(["hive", "-e", "DROP DATABASE IF EXISTS %s CASCADE" % db_name])
    self.client.execute("invalidate metadata")

    result = self.client.execute("show databases")
    assert db_name not in result.data

    call(["hive", "-e", "CREATE DATABASE %s" % db_name])

    # Run 'invalidate metadata <table name>' when the parent database does not exist.
    try:
      self.client.execute("invalidate metadata %s.%s"  % (db_name, tbl_name))
      assert 0, 'Expected to fail'
    except ImpalaBeeswaxException as e:
      assert "TableNotFoundException: Table not found: %s.%s"\
          % (db_name, tbl_name) in str(e)

    result = self.client.execute("show databases")
    assert db_name not in result.data

    # Create a table external to Impala.
    call(["hive", "-e", "CREATE TABLE %s.%s (i int)" % (db_name, tbl_name)])

    # Impala does not know about this database or table.
    result = self.client.execute("show databases")
    assert db_name not in result.data

    # Run 'invalidate metadata <table name>'. It should add the database and table
    # in to Impala's catalog.
    self.client.execute("invalidate metadata %s.%s"  % (db_name, tbl_name))
    result = self.client.execute("show databases")
    assert db_name in result.data

    result = self.client.execute("show tables in %s" % db_name)
    assert tbl_name in result.data
    assert len(result.data) == 1

    self.client.execute("create table %s.%s (j int)" % (db_name, tbl_name + "_test"))
    call(["hive", "-e", "drop table %s.%s" % (db_name, tbl_name + "_test")])

    # Re-create the table in Hive. Use the same name, but different casing.
    call(["hive", "-e", "CREATE TABLE %s.%s (i bigint)" % (db_name, tbl_name + "_TEST")])
    self.client.execute("invalidate metadata %s.%s"  % (db_name, tbl_name + "_Test"))
    result = self.client.execute("show tables in %s" % db_name)
    assert tbl_name + "_test" in result.data
    assert tbl_name + "_Test" not in result.data
    assert tbl_name + "_TEST" not in result.data

    # Verify this table is the version created in Hive (the column should be BIGINT)
    result = self.client.execute("describe %s.%s" % (db_name, tbl_name + '_test'))
    assert 'bigint' in result.data[0]

    self.client.execute("drop table %s.%s" % (db_name, tbl_name + "_TEST"))

    # Make sure we can actually use the table
    self.client.execute(("insert overwrite table %s.%s "
                        "select 1 from functional.alltypes limit 5"
                         % (db_name, tbl_name)))
    result = self.execute_scalar("select count(*) from %s.%s" % (db_name, tbl_name))
    assert int(result) == 5

    # Should be able to call invalidate metadata multiple times on the same table.
    self.client.execute("invalidate metadata %s.%s"  % (db_name, tbl_name))
    self.client.execute("refresh %s.%s"  % (db_name, tbl_name))
    result = self.client.execute("show tables in %s" % db_name)
    assert tbl_name in result.data

    # Can still use the table.
    result = self.execute_scalar("select count(*) from %s.%s" % (db_name, tbl_name))
    assert int(result) == 5

    # Run 'invalidate metadata <table name>' when no table exists with that name.
    try:
      self.client.execute("invalidate metadata %s.%s"  % (db_name, tbl_name + '2'))
      assert 0, 'Expected to fail'
    except ImpalaBeeswaxException as e:
      assert "TableNotFoundException: Table not found: %s.%s"\
          % (db_name, tbl_name + '2') in str(e)

    result = self.client.execute("show tables in %s" % db_name);
    assert len(result.data) == 1
    assert tbl_name in result.data

    # Create another table
    call(["hive", "-e", "CREATE TABLE %s.%s (i int)" % (db_name, tbl_name + '2')])
    self.client.execute("invalidate metadata %s.%s"  % (db_name, tbl_name + '2'))
    result = self.client.execute("show tables in %s" % db_name)
    assert tbl_name + '2' in result.data
    assert tbl_name in result.data

    # Drop the table, and then verify invalidate metadata <table name> removes the
    # table from the catalog.
    call(["hive", "-e", "DROP TABLE %s.%s " % (db_name, tbl_name)])
    self.client.execute("invalidate metadata %s.%s"  % (db_name, tbl_name))
    result = self.client.execute("show tables in %s" % db_name)
    assert tbl_name + '2' in result.data
    assert tbl_name not in result.data

    # Should be able to call invalidate multiple times on the same table when the table
    # does not exist.
    try:
      self.client.execute("invalidate metadata %s.%s"  % (db_name, tbl_name))
      assert 0, 'Expected to fail'
    except ImpalaBeeswaxException as e:
      assert "TableNotFoundException: Table not found: %s.%s"\
          % (db_name, tbl_name) in str(e)

    result = self.client.execute("show tables in %s" % db_name)
    assert tbl_name + '2' in result.data
    assert tbl_name not in result.data

    # Drop the parent database (this will drop all tables). Then invalidate the table
    call(["hive", "-e", "DROP DATABASE %s CASCADE" % db_name])
    self.client.execute("invalidate metadata %s.%s"  % (db_name, tbl_name + '2'))
    result = self.client.execute("show tables in %s" % db_name);
    assert len(result.data) == 0

    # Requires a refresh to see the dropped database
    result = self.client.execute("show databases");
    assert db_name in result.data

    self.client.execute("invalidate metadata")
    result = self.client.execute("show databases");
    assert db_name not in result.data
