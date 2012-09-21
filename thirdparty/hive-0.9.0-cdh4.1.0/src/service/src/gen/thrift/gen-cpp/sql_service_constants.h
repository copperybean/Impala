/**
 * Autogenerated by Thrift Compiler (0.7.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
#ifndef sql_service_CONSTANTS_H
#define sql_service_CONSTANTS_H

#include "sql_service_types.h"

namespace Apache { namespace Hive { namespace Service { namespace Sql { namespace Thrift {

class sql_serviceConstants {
 public:
  sql_serviceConstants();

  std::set<TType::type>  PRIMITIVE_TYPES;
  std::set<TType::type>  COMPLEX_TYPES;
  std::set<TType::type>  COLLECTION_TYPES;
  std::map<TType::type, std::string>  TYPE_NAMES;
};

extern const sql_serviceConstants g_sql_service_constants;

}}}}} // namespace

#endif
