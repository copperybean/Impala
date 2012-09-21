/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hive.service.sql.operation;

import org.apache.hadoop.hive.metastore.TableType;
import org.apache.hive.service.sql.FetchOrientation;
import org.apache.hive.service.sql.HiveSQLException;
import org.apache.hive.service.sql.OperationState;
import org.apache.hive.service.sql.OperationType;
import org.apache.hive.service.sql.RowSet;
import org.apache.hive.service.sql.TableSchema;
import org.apache.hive.service.sql.session.Session;

/**
 * GetTableTypesOperation.
 *
 */
public class GetTableTypesOperation extends MetadataOperation {

  protected static TableSchema RESULT_SET_SCHEMA = new TableSchema()
  .addStringColumn("TABLE_TYPE", "Table type name.");

  private RowSet rowSet;

  protected GetTableTypesOperation(Session parentSession) {
    super(parentSession, OperationType.GET_TABLE_TYPES);
  }

  /* (non-Javadoc)
   * @see org.apache.hive.service.sql.Operation#run()
   */
  @Override
  public void run() throws HiveSQLException {
    setState(OperationState.RUNNING);
    try {
      rowSet = new RowSet();
      for (TableType type : TableType.values()) {
        rowSet.addRow(RESULT_SET_SCHEMA, new String[] {type.toString()});
      }
      setState(OperationState.FINISHED);
    } catch (Exception e) {
      setState(OperationState.ERROR);
      throw new HiveSQLException(e);
    }
  }

  /* (non-Javadoc)
   * @see org.apache.hive.service.sql.Operation#getResultSetSchema()
   */
  @Override
  public TableSchema getResultSetSchema() throws HiveSQLException {
    assertState(OperationState.FINISHED);
    return RESULT_SET_SCHEMA;
  }

  /* (non-Javadoc)
   * @see org.apache.hive.service.sql.Operation#getNextRowSet(org.apache.hive.service.sql.FetchOrientation, long)
   */
  @Override
  public RowSet getNextRowSet(FetchOrientation orientation, long maxRows) throws HiveSQLException {
    assertState(OperationState.FINISHED);
    return rowSet.extractSubset((int)maxRows);
  }

}
