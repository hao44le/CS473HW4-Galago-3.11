/**
 * Autogenerated by Thrift Compiler (0.8.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ciir.proteus.galago.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
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

public class AccessIdentifier implements org.apache.thrift.TBase<AccessIdentifier, AccessIdentifier._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("AccessIdentifier");

  private static final org.apache.thrift.protocol.TField IDENTIFIER_FIELD_DESC = new org.apache.thrift.protocol.TField("identifier", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField RESOURCE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("resource_id", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField ERROR_FIELD_DESC = new org.apache.thrift.protocol.TField("error", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new AccessIdentifierStandardSchemeFactory());
    schemes.put(TupleScheme.class, new AccessIdentifierTupleSchemeFactory());
  }

  public String identifier; // required
  /**
   * 
   * @see ProteusType
   */
  public ProteusType type; // required
  public String resource_id; // required
  public String error; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    IDENTIFIER((short)1, "identifier"),
    /**
     * 
     * @see ProteusType
     */
    TYPE((short)2, "type"),
    RESOURCE_ID((short)3, "resource_id"),
    ERROR((short)4, "error");

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
        case 1: // IDENTIFIER
          return IDENTIFIER;
        case 2: // TYPE
          return TYPE;
        case 3: // RESOURCE_ID
          return RESOURCE_ID;
        case 4: // ERROR
          return ERROR;
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
  private _Fields optionals[] = {_Fields.ERROR};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.IDENTIFIER, new org.apache.thrift.meta_data.FieldMetaData("identifier", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, ProteusType.class)));
    tmpMap.put(_Fields.RESOURCE_ID, new org.apache.thrift.meta_data.FieldMetaData("resource_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ERROR, new org.apache.thrift.meta_data.FieldMetaData("error", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(AccessIdentifier.class, metaDataMap);
  }

  public AccessIdentifier() {
  }

  public AccessIdentifier(
    String identifier,
    ProteusType type,
    String resource_id)
  {
    this();
    this.identifier = identifier;
    this.type = type;
    this.resource_id = resource_id;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public AccessIdentifier(AccessIdentifier other) {
    if (other.isSetIdentifier()) {
      this.identifier = other.identifier;
    }
    if (other.isSetType()) {
      this.type = other.type;
    }
    if (other.isSetResource_id()) {
      this.resource_id = other.resource_id;
    }
    if (other.isSetError()) {
      this.error = other.error;
    }
  }

  public AccessIdentifier deepCopy() {
    return new AccessIdentifier(this);
  }

  @Override
  public void clear() {
    this.identifier = null;
    this.type = null;
    this.resource_id = null;
    this.error = null;
  }

  public String getIdentifier() {
    return this.identifier;
  }

  public AccessIdentifier setIdentifier(String identifier) {
    this.identifier = identifier;
    return this;
  }

  public void unsetIdentifier() {
    this.identifier = null;
  }

  /** Returns true if field identifier is set (has been assigned a value) and false otherwise */
  public boolean isSetIdentifier() {
    return this.identifier != null;
  }

  public void setIdentifierIsSet(boolean value) {
    if (!value) {
      this.identifier = null;
    }
  }

  /**
   * 
   * @see ProteusType
   */
  public ProteusType getType() {
    return this.type;
  }

  /**
   * 
   * @see ProteusType
   */
  public AccessIdentifier setType(ProteusType type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public String getResource_id() {
    return this.resource_id;
  }

  public AccessIdentifier setResource_id(String resource_id) {
    this.resource_id = resource_id;
    return this;
  }

  public void unsetResource_id() {
    this.resource_id = null;
  }

  /** Returns true if field resource_id is set (has been assigned a value) and false otherwise */
  public boolean isSetResource_id() {
    return this.resource_id != null;
  }

  public void setResource_idIsSet(boolean value) {
    if (!value) {
      this.resource_id = null;
    }
  }

  public String getError() {
    return this.error;
  }

  public AccessIdentifier setError(String error) {
    this.error = error;
    return this;
  }

  public void unsetError() {
    this.error = null;
  }

  /** Returns true if field error is set (has been assigned a value) and false otherwise */
  public boolean isSetError() {
    return this.error != null;
  }

  public void setErrorIsSet(boolean value) {
    if (!value) {
      this.error = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case IDENTIFIER:
      if (value == null) {
        unsetIdentifier();
      } else {
        setIdentifier((String)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((ProteusType)value);
      }
      break;

    case RESOURCE_ID:
      if (value == null) {
        unsetResource_id();
      } else {
        setResource_id((String)value);
      }
      break;

    case ERROR:
      if (value == null) {
        unsetError();
      } else {
        setError((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case IDENTIFIER:
      return getIdentifier();

    case TYPE:
      return getType();

    case RESOURCE_ID:
      return getResource_id();

    case ERROR:
      return getError();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case IDENTIFIER:
      return isSetIdentifier();
    case TYPE:
      return isSetType();
    case RESOURCE_ID:
      return isSetResource_id();
    case ERROR:
      return isSetError();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof AccessIdentifier)
      return this.equals((AccessIdentifier)that);
    return false;
  }

  public boolean equals(AccessIdentifier that) {
    if (that == null)
      return false;

    boolean this_present_identifier = true && this.isSetIdentifier();
    boolean that_present_identifier = true && that.isSetIdentifier();
    if (this_present_identifier || that_present_identifier) {
      if (!(this_present_identifier && that_present_identifier))
        return false;
      if (!this.identifier.equals(that.identifier))
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_resource_id = true && this.isSetResource_id();
    boolean that_present_resource_id = true && that.isSetResource_id();
    if (this_present_resource_id || that_present_resource_id) {
      if (!(this_present_resource_id && that_present_resource_id))
        return false;
      if (!this.resource_id.equals(that.resource_id))
        return false;
    }

    boolean this_present_error = true && this.isSetError();
    boolean that_present_error = true && that.isSetError();
    if (this_present_error || that_present_error) {
      if (!(this_present_error && that_present_error))
        return false;
      if (!this.error.equals(that.error))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(AccessIdentifier other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    AccessIdentifier typedOther = (AccessIdentifier)other;

    lastComparison = Boolean.valueOf(isSetIdentifier()).compareTo(typedOther.isSetIdentifier());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIdentifier()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.identifier, typedOther.identifier);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(typedOther.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, typedOther.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetResource_id()).compareTo(typedOther.isSetResource_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResource_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resource_id, typedOther.resource_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetError()).compareTo(typedOther.isSetError());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetError()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.error, typedOther.error);
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
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("AccessIdentifier(");
    boolean first = true;

    sb.append("identifier:");
    if (this.identifier == null) {
      sb.append("null");
    } else {
      sb.append(this.identifier);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("resource_id:");
    if (this.resource_id == null) {
      sb.append("null");
    } else {
      sb.append(this.resource_id);
    }
    first = false;
    if (isSetError()) {
      if (!first) sb.append(", ");
      sb.append("error:");
      if (this.error == null) {
        sb.append("null");
      } else {
        sb.append(this.error);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
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

  private static class AccessIdentifierStandardSchemeFactory implements SchemeFactory {
    public AccessIdentifierStandardScheme getScheme() {
      return new AccessIdentifierStandardScheme();
    }
  }

  private static class AccessIdentifierStandardScheme extends StandardScheme<AccessIdentifier> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, AccessIdentifier struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // IDENTIFIER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.identifier = iprot.readString();
              struct.setIdentifierIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.type = ProteusType.findByValue(iprot.readI32());
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // RESOURCE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.resource_id = iprot.readString();
              struct.setResource_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ERROR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.error = iprot.readString();
              struct.setErrorIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, AccessIdentifier struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.identifier != null) {
        oprot.writeFieldBegin(IDENTIFIER_FIELD_DESC);
        oprot.writeString(struct.identifier);
        oprot.writeFieldEnd();
      }
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeI32(struct.type.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.resource_id != null) {
        oprot.writeFieldBegin(RESOURCE_ID_FIELD_DESC);
        oprot.writeString(struct.resource_id);
        oprot.writeFieldEnd();
      }
      if (struct.error != null) {
        if (struct.isSetError()) {
          oprot.writeFieldBegin(ERROR_FIELD_DESC);
          oprot.writeString(struct.error);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class AccessIdentifierTupleSchemeFactory implements SchemeFactory {
    public AccessIdentifierTupleScheme getScheme() {
      return new AccessIdentifierTupleScheme();
    }
  }

  private static class AccessIdentifierTupleScheme extends TupleScheme<AccessIdentifier> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, AccessIdentifier struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetIdentifier()) {
        optionals.set(0);
      }
      if (struct.isSetType()) {
        optionals.set(1);
      }
      if (struct.isSetResource_id()) {
        optionals.set(2);
      }
      if (struct.isSetError()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetIdentifier()) {
        oprot.writeString(struct.identifier);
      }
      if (struct.isSetType()) {
        oprot.writeI32(struct.type.getValue());
      }
      if (struct.isSetResource_id()) {
        oprot.writeString(struct.resource_id);
      }
      if (struct.isSetError()) {
        oprot.writeString(struct.error);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, AccessIdentifier struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.identifier = iprot.readString();
        struct.setIdentifierIsSet(true);
      }
      if (incoming.get(1)) {
        struct.type = ProteusType.findByValue(iprot.readI32());
        struct.setTypeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.resource_id = iprot.readString();
        struct.setResource_idIsSet(true);
      }
      if (incoming.get(3)) {
        struct.error = iprot.readString();
        struct.setErrorIsSet(true);
      }
    }
  }

}

