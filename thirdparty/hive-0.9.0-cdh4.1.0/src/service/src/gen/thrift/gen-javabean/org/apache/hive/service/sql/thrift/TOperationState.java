/**
 * Autogenerated by Thrift Compiler (0.7.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package org.apache.hive.service.sql.thrift;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum TOperationState implements org.apache.thrift.TEnum {
  INITIALIZED(0),
  RUNNING(1),
  FINISHED(2),
  CANCELED(3),
  CLOSED(4),
  ERROR(5),
  UKNOWN(6);

  private final int value;

  private TOperationState(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static TOperationState findByValue(int value) { 
    switch (value) {
      case 0:
        return INITIALIZED;
      case 1:
        return RUNNING;
      case 2:
        return FINISHED;
      case 3:
        return CANCELED;
      case 4:
        return CLOSED;
      case 5:
        return ERROR;
      case 6:
        return UKNOWN;
      default:
        return null;
    }
  }
}
