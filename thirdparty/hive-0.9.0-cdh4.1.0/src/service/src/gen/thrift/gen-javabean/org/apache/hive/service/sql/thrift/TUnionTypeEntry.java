/**
 * Autogenerated by Thrift Compiler (0.7.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package org.apache.hive.service.sql.thrift;

import org.apache.commons.lang.builder.HashCodeBuilder;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TUnionTypeEntry implements org.apache.thrift.TBase<TUnionTypeEntry, TUnionTypeEntry._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TUnionTypeEntry");

  private static final org.apache.thrift.protocol.TField NAME_TO_TYPE_PTR_FIELD_DESC = new org.apache.thrift.protocol.TField("nameToTypePtr", org.apache.thrift.protocol.TType.MAP, (short)1);

  private Map<String,Integer> nameToTypePtr; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NAME_TO_TYPE_PTR((short)1, "nameToTypePtr");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // NAME_TO_TYPE_PTR
          return NAME_TO_TYPE_PTR;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments

  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NAME_TO_TYPE_PTR, new org.apache.thrift.meta_data.FieldMetaData("nameToTypePtr", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32            , "TTypeEntryPtr"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TUnionTypeEntry.class, metaDataMap);
  }

  public TUnionTypeEntry() {
  }

  public TUnionTypeEntry(
    Map<String,Integer> nameToTypePtr)
  {
    this();
    this.nameToTypePtr = nameToTypePtr;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TUnionTypeEntry(TUnionTypeEntry other) {
    if (other.isSetNameToTypePtr()) {
      Map<String,Integer> __this__nameToTypePtr = new HashMap<String,Integer>();
      for (Map.Entry<String, Integer> other_element : other.nameToTypePtr.entrySet()) {

        String other_element_key = other_element.getKey();
        Integer other_element_value = other_element.getValue();

        String __this__nameToTypePtr_copy_key = other_element_key;

        Integer __this__nameToTypePtr_copy_value = other_element_value;

        __this__nameToTypePtr.put(__this__nameToTypePtr_copy_key, __this__nameToTypePtr_copy_value);
      }
      this.nameToTypePtr = __this__nameToTypePtr;
    }
  }

  public TUnionTypeEntry deepCopy() {
    return new TUnionTypeEntry(this);
  }

  @Override
  public void clear() {
    this.nameToTypePtr = null;
  }

  public int getNameToTypePtrSize() {
    return (this.nameToTypePtr == null) ? 0 : this.nameToTypePtr.size();
  }

  public void putToNameToTypePtr(String key, int val) {
    if (this.nameToTypePtr == null) {
      this.nameToTypePtr = new HashMap<String,Integer>();
    }
    this.nameToTypePtr.put(key, val);
  }

  public Map<String,Integer> getNameToTypePtr() {
    return this.nameToTypePtr;
  }

  public void setNameToTypePtr(Map<String,Integer> nameToTypePtr) {
    this.nameToTypePtr = nameToTypePtr;
  }

  public void unsetNameToTypePtr() {
    this.nameToTypePtr = null;
  }

  /** Returns true if field nameToTypePtr is set (has been assigned a value) and false otherwise */
  public boolean isSetNameToTypePtr() {
    return this.nameToTypePtr != null;
  }

  public void setNameToTypePtrIsSet(boolean value) {
    if (!value) {
      this.nameToTypePtr = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case NAME_TO_TYPE_PTR:
      if (value == null) {
        unsetNameToTypePtr();
      } else {
        setNameToTypePtr((Map<String,Integer>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NAME_TO_TYPE_PTR:
      return getNameToTypePtr();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case NAME_TO_TYPE_PTR:
      return isSetNameToTypePtr();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TUnionTypeEntry)
      return this.equals((TUnionTypeEntry)that);
    return false;
  }

  public boolean equals(TUnionTypeEntry that) {
    if (that == null)
      return false;

    boolean this_present_nameToTypePtr = true && this.isSetNameToTypePtr();
    boolean that_present_nameToTypePtr = true && that.isSetNameToTypePtr();
    if (this_present_nameToTypePtr || that_present_nameToTypePtr) {
      if (!(this_present_nameToTypePtr && that_present_nameToTypePtr))
        return false;
      if (!this.nameToTypePtr.equals(that.nameToTypePtr))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();

    boolean present_nameToTypePtr = true && (isSetNameToTypePtr());
    builder.append(present_nameToTypePtr);
    if (present_nameToTypePtr)
      builder.append(nameToTypePtr);

    return builder.toHashCode();
  }

  public int compareTo(TUnionTypeEntry other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TUnionTypeEntry typedOther = (TUnionTypeEntry)other;

    lastComparison = Boolean.valueOf(isSetNameToTypePtr()).compareTo(typedOther.isSetNameToTypePtr());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNameToTypePtr()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nameToTypePtr, typedOther.nameToTypePtr);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    org.apache.thrift.protocol.TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == org.apache.thrift.protocol.TType.STOP) { 
        break;
      }
      switch (field.id) {
        case 1: // NAME_TO_TYPE_PTR
          if (field.type == org.apache.thrift.protocol.TType.MAP) {
            {
              org.apache.thrift.protocol.TMap _map5 = iprot.readMapBegin();
              this.nameToTypePtr = new HashMap<String,Integer>(2*_map5.size);
              for (int _i6 = 0; _i6 < _map5.size; ++_i6)
              {
                String _key7; // required
                int _val8; // required
                _key7 = iprot.readString();
                _val8 = iprot.readI32();
                this.nameToTypePtr.put(_key7, _val8);
              }
              iprot.readMapEnd();
            }
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();
    validate();
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    if (this.nameToTypePtr != null) {
      oprot.writeFieldBegin(NAME_TO_TYPE_PTR_FIELD_DESC);
      {
        oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.I32, this.nameToTypePtr.size()));
        for (Map.Entry<String, Integer> _iter9 : this.nameToTypePtr.entrySet())
        {
          oprot.writeString(_iter9.getKey());
          oprot.writeI32(_iter9.getValue());
        }
        oprot.writeMapEnd();
      }
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TUnionTypeEntry(");
    boolean first = true;

    sb.append("nameToTypePtr:");
    if (this.nameToTypePtr == null) {
      sb.append("null");
    } else {
      sb.append(this.nameToTypePtr);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetNameToTypePtr()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'nameToTypePtr' is unset! Struct:" + toString());
    }

  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

}

