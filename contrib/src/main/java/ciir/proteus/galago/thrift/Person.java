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

public class Person implements org.apache.thrift.TBase<Person, Person._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Person");

  private static final org.apache.thrift.protocol.TField FULL_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("full_name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField ALTERNATE_NAMES_FIELD_DESC = new org.apache.thrift.protocol.TField("alternate_names", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField WIKI_LINK_FIELD_DESC = new org.apache.thrift.protocol.TField("wiki_link", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField BIRTH_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("birth_date", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField DEATH_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("death_date", org.apache.thrift.protocol.TType.I64, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PersonStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PersonTupleSchemeFactory());
  }

  public String full_name; // optional
  public List<String> alternate_names; // required
  public String wiki_link; // optional
  public long birth_date; // optional
  public long death_date; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FULL_NAME((short)1, "full_name"),
    ALTERNATE_NAMES((short)2, "alternate_names"),
    WIKI_LINK((short)3, "wiki_link"),
    BIRTH_DATE((short)4, "birth_date"),
    DEATH_DATE((short)5, "death_date");

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
        case 1: // FULL_NAME
          return FULL_NAME;
        case 2: // ALTERNATE_NAMES
          return ALTERNATE_NAMES;
        case 3: // WIKI_LINK
          return WIKI_LINK;
        case 4: // BIRTH_DATE
          return BIRTH_DATE;
        case 5: // DEATH_DATE
          return DEATH_DATE;
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
  private static final int __BIRTH_DATE_ISSET_ID = 0;
  private static final int __DEATH_DATE_ISSET_ID = 1;
  private BitSet __isset_bit_vector = new BitSet(2);
  private _Fields optionals[] = {_Fields.FULL_NAME,_Fields.WIKI_LINK,_Fields.BIRTH_DATE,_Fields.DEATH_DATE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FULL_NAME, new org.apache.thrift.meta_data.FieldMetaData("full_name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ALTERNATE_NAMES, new org.apache.thrift.meta_data.FieldMetaData("alternate_names", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.WIKI_LINK, new org.apache.thrift.meta_data.FieldMetaData("wiki_link", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BIRTH_DATE, new org.apache.thrift.meta_data.FieldMetaData("birth_date", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DEATH_DATE, new org.apache.thrift.meta_data.FieldMetaData("death_date", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Person.class, metaDataMap);
  }

  public Person() {
  }

  public Person(
    List<String> alternate_names)
  {
    this();
    this.alternate_names = alternate_names;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Person(Person other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetFull_name()) {
      this.full_name = other.full_name;
    }
    if (other.isSetAlternate_names()) {
      List<String> __this__alternate_names = new ArrayList<String>();
      for (String other_element : other.alternate_names) {
        __this__alternate_names.add(other_element);
      }
      this.alternate_names = __this__alternate_names;
    }
    if (other.isSetWiki_link()) {
      this.wiki_link = other.wiki_link;
    }
    this.birth_date = other.birth_date;
    this.death_date = other.death_date;
  }

  public Person deepCopy() {
    return new Person(this);
  }

  @Override
  public void clear() {
    this.full_name = null;
    this.alternate_names = null;
    this.wiki_link = null;
    setBirth_dateIsSet(false);
    this.birth_date = 0;
    setDeath_dateIsSet(false);
    this.death_date = 0;
  }

  public String getFull_name() {
    return this.full_name;
  }

  public Person setFull_name(String full_name) {
    this.full_name = full_name;
    return this;
  }

  public void unsetFull_name() {
    this.full_name = null;
  }

  /** Returns true if field full_name is set (has been assigned a value) and false otherwise */
  public boolean isSetFull_name() {
    return this.full_name != null;
  }

  public void setFull_nameIsSet(boolean value) {
    if (!value) {
      this.full_name = null;
    }
  }

  public int getAlternate_namesSize() {
    return (this.alternate_names == null) ? 0 : this.alternate_names.size();
  }

  public java.util.Iterator<String> getAlternate_namesIterator() {
    return (this.alternate_names == null) ? null : this.alternate_names.iterator();
  }

  public void addToAlternate_names(String elem) {
    if (this.alternate_names == null) {
      this.alternate_names = new ArrayList<String>();
    }
    this.alternate_names.add(elem);
  }

  public List<String> getAlternate_names() {
    return this.alternate_names;
  }

  public Person setAlternate_names(List<String> alternate_names) {
    this.alternate_names = alternate_names;
    return this;
  }

  public void unsetAlternate_names() {
    this.alternate_names = null;
  }

  /** Returns true if field alternate_names is set (has been assigned a value) and false otherwise */
  public boolean isSetAlternate_names() {
    return this.alternate_names != null;
  }

  public void setAlternate_namesIsSet(boolean value) {
    if (!value) {
      this.alternate_names = null;
    }
  }

  public String getWiki_link() {
    return this.wiki_link;
  }

  public Person setWiki_link(String wiki_link) {
    this.wiki_link = wiki_link;
    return this;
  }

  public void unsetWiki_link() {
    this.wiki_link = null;
  }

  /** Returns true if field wiki_link is set (has been assigned a value) and false otherwise */
  public boolean isSetWiki_link() {
    return this.wiki_link != null;
  }

  public void setWiki_linkIsSet(boolean value) {
    if (!value) {
      this.wiki_link = null;
    }
  }

  public long getBirth_date() {
    return this.birth_date;
  }

  public Person setBirth_date(long birth_date) {
    this.birth_date = birth_date;
    setBirth_dateIsSet(true);
    return this;
  }

  public void unsetBirth_date() {
    __isset_bit_vector.clear(__BIRTH_DATE_ISSET_ID);
  }

  /** Returns true if field birth_date is set (has been assigned a value) and false otherwise */
  public boolean isSetBirth_date() {
    return __isset_bit_vector.get(__BIRTH_DATE_ISSET_ID);
  }

  public void setBirth_dateIsSet(boolean value) {
    __isset_bit_vector.set(__BIRTH_DATE_ISSET_ID, value);
  }

  public long getDeath_date() {
    return this.death_date;
  }

  public Person setDeath_date(long death_date) {
    this.death_date = death_date;
    setDeath_dateIsSet(true);
    return this;
  }

  public void unsetDeath_date() {
    __isset_bit_vector.clear(__DEATH_DATE_ISSET_ID);
  }

  /** Returns true if field death_date is set (has been assigned a value) and false otherwise */
  public boolean isSetDeath_date() {
    return __isset_bit_vector.get(__DEATH_DATE_ISSET_ID);
  }

  public void setDeath_dateIsSet(boolean value) {
    __isset_bit_vector.set(__DEATH_DATE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case FULL_NAME:
      if (value == null) {
        unsetFull_name();
      } else {
        setFull_name((String)value);
      }
      break;

    case ALTERNATE_NAMES:
      if (value == null) {
        unsetAlternate_names();
      } else {
        setAlternate_names((List<String>)value);
      }
      break;

    case WIKI_LINK:
      if (value == null) {
        unsetWiki_link();
      } else {
        setWiki_link((String)value);
      }
      break;

    case BIRTH_DATE:
      if (value == null) {
        unsetBirth_date();
      } else {
        setBirth_date((Long)value);
      }
      break;

    case DEATH_DATE:
      if (value == null) {
        unsetDeath_date();
      } else {
        setDeath_date((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case FULL_NAME:
      return getFull_name();

    case ALTERNATE_NAMES:
      return getAlternate_names();

    case WIKI_LINK:
      return getWiki_link();

    case BIRTH_DATE:
      return Long.valueOf(getBirth_date());

    case DEATH_DATE:
      return Long.valueOf(getDeath_date());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case FULL_NAME:
      return isSetFull_name();
    case ALTERNATE_NAMES:
      return isSetAlternate_names();
    case WIKI_LINK:
      return isSetWiki_link();
    case BIRTH_DATE:
      return isSetBirth_date();
    case DEATH_DATE:
      return isSetDeath_date();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Person)
      return this.equals((Person)that);
    return false;
  }

  public boolean equals(Person that) {
    if (that == null)
      return false;

    boolean this_present_full_name = true && this.isSetFull_name();
    boolean that_present_full_name = true && that.isSetFull_name();
    if (this_present_full_name || that_present_full_name) {
      if (!(this_present_full_name && that_present_full_name))
        return false;
      if (!this.full_name.equals(that.full_name))
        return false;
    }

    boolean this_present_alternate_names = true && this.isSetAlternate_names();
    boolean that_present_alternate_names = true && that.isSetAlternate_names();
    if (this_present_alternate_names || that_present_alternate_names) {
      if (!(this_present_alternate_names && that_present_alternate_names))
        return false;
      if (!this.alternate_names.equals(that.alternate_names))
        return false;
    }

    boolean this_present_wiki_link = true && this.isSetWiki_link();
    boolean that_present_wiki_link = true && that.isSetWiki_link();
    if (this_present_wiki_link || that_present_wiki_link) {
      if (!(this_present_wiki_link && that_present_wiki_link))
        return false;
      if (!this.wiki_link.equals(that.wiki_link))
        return false;
    }

    boolean this_present_birth_date = true && this.isSetBirth_date();
    boolean that_present_birth_date = true && that.isSetBirth_date();
    if (this_present_birth_date || that_present_birth_date) {
      if (!(this_present_birth_date && that_present_birth_date))
        return false;
      if (this.birth_date != that.birth_date)
        return false;
    }

    boolean this_present_death_date = true && this.isSetDeath_date();
    boolean that_present_death_date = true && that.isSetDeath_date();
    if (this_present_death_date || that_present_death_date) {
      if (!(this_present_death_date && that_present_death_date))
        return false;
      if (this.death_date != that.death_date)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Person other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Person typedOther = (Person)other;

    lastComparison = Boolean.valueOf(isSetFull_name()).compareTo(typedOther.isSetFull_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFull_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.full_name, typedOther.full_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAlternate_names()).compareTo(typedOther.isSetAlternate_names());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAlternate_names()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.alternate_names, typedOther.alternate_names);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetWiki_link()).compareTo(typedOther.isSetWiki_link());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWiki_link()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.wiki_link, typedOther.wiki_link);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBirth_date()).compareTo(typedOther.isSetBirth_date());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBirth_date()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.birth_date, typedOther.birth_date);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDeath_date()).compareTo(typedOther.isSetDeath_date());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDeath_date()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.death_date, typedOther.death_date);
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
    StringBuilder sb = new StringBuilder("Person(");
    boolean first = true;

    if (isSetFull_name()) {
      sb.append("full_name:");
      if (this.full_name == null) {
        sb.append("null");
      } else {
        sb.append(this.full_name);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("alternate_names:");
    if (this.alternate_names == null) {
      sb.append("null");
    } else {
      sb.append(this.alternate_names);
    }
    first = false;
    if (isSetWiki_link()) {
      if (!first) sb.append(", ");
      sb.append("wiki_link:");
      if (this.wiki_link == null) {
        sb.append("null");
      } else {
        sb.append(this.wiki_link);
      }
      first = false;
    }
    if (isSetBirth_date()) {
      if (!first) sb.append(", ");
      sb.append("birth_date:");
      sb.append(this.birth_date);
      first = false;
    }
    if (isSetDeath_date()) {
      if (!first) sb.append(", ");
      sb.append("death_date:");
      sb.append(this.death_date);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PersonStandardSchemeFactory implements SchemeFactory {
    public PersonStandardScheme getScheme() {
      return new PersonStandardScheme();
    }
  }

  private static class PersonStandardScheme extends StandardScheme<Person> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Person struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FULL_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.full_name = iprot.readString();
              struct.setFull_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ALTERNATE_NAMES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list122 = iprot.readListBegin();
                struct.alternate_names = new ArrayList<String>(_list122.size);
                for (int _i123 = 0; _i123 < _list122.size; ++_i123)
                {
                  String _elem124; // optional
                  _elem124 = iprot.readString();
                  struct.alternate_names.add(_elem124);
                }
                iprot.readListEnd();
              }
              struct.setAlternate_namesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // WIKI_LINK
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.wiki_link = iprot.readString();
              struct.setWiki_linkIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // BIRTH_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.birth_date = iprot.readI64();
              struct.setBirth_dateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // DEATH_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.death_date = iprot.readI64();
              struct.setDeath_dateIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Person struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.full_name != null) {
        if (struct.isSetFull_name()) {
          oprot.writeFieldBegin(FULL_NAME_FIELD_DESC);
          oprot.writeString(struct.full_name);
          oprot.writeFieldEnd();
        }
      }
      if (struct.alternate_names != null) {
        oprot.writeFieldBegin(ALTERNATE_NAMES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.alternate_names.size()));
          for (String _iter125 : struct.alternate_names)
          {
            oprot.writeString(_iter125);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.wiki_link != null) {
        if (struct.isSetWiki_link()) {
          oprot.writeFieldBegin(WIKI_LINK_FIELD_DESC);
          oprot.writeString(struct.wiki_link);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetBirth_date()) {
        oprot.writeFieldBegin(BIRTH_DATE_FIELD_DESC);
        oprot.writeI64(struct.birth_date);
        oprot.writeFieldEnd();
      }
      if (struct.isSetDeath_date()) {
        oprot.writeFieldBegin(DEATH_DATE_FIELD_DESC);
        oprot.writeI64(struct.death_date);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PersonTupleSchemeFactory implements SchemeFactory {
    public PersonTupleScheme getScheme() {
      return new PersonTupleScheme();
    }
  }

  private static class PersonTupleScheme extends TupleScheme<Person> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Person struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetFull_name()) {
        optionals.set(0);
      }
      if (struct.isSetAlternate_names()) {
        optionals.set(1);
      }
      if (struct.isSetWiki_link()) {
        optionals.set(2);
      }
      if (struct.isSetBirth_date()) {
        optionals.set(3);
      }
      if (struct.isSetDeath_date()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetFull_name()) {
        oprot.writeString(struct.full_name);
      }
      if (struct.isSetAlternate_names()) {
        {
          oprot.writeI32(struct.alternate_names.size());
          for (String _iter126 : struct.alternate_names)
          {
            oprot.writeString(_iter126);
          }
        }
      }
      if (struct.isSetWiki_link()) {
        oprot.writeString(struct.wiki_link);
      }
      if (struct.isSetBirth_date()) {
        oprot.writeI64(struct.birth_date);
      }
      if (struct.isSetDeath_date()) {
        oprot.writeI64(struct.death_date);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Person struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.full_name = iprot.readString();
        struct.setFull_nameIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list127 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.alternate_names = new ArrayList<String>(_list127.size);
          for (int _i128 = 0; _i128 < _list127.size; ++_i128)
          {
            String _elem129; // optional
            _elem129 = iprot.readString();
            struct.alternate_names.add(_elem129);
          }
        }
        struct.setAlternate_namesIsSet(true);
      }
      if (incoming.get(2)) {
        struct.wiki_link = iprot.readString();
        struct.setWiki_linkIsSet(true);
      }
      if (incoming.get(3)) {
        struct.birth_date = iprot.readI64();
        struct.setBirth_dateIsSet(true);
      }
      if (incoming.get(4)) {
        struct.death_date = iprot.readI64();
        struct.setDeath_dateIsSet(true);
      }
    }
  }

}

