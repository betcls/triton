package com.bc.sql;

import java.io.*;


public class SqlColumn implements Serializable
{ //----------------------------------------------------
  static final long serialVersionUID = 19571L ;
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;
  //----------------------------------------------------

  private int no = 0 ;
  private int sqlType = 0 ;
  private String name = null ;
  private String className = null ;
  private boolean nullable = false ;
  private boolean indexKey = false ;
  private boolean primaryKey = false ;
  private boolean uniqueIndex = false ;
  private boolean autoIncrement = false ;
  private Object data = null ;

  public SqlColumn( int no, int sqlType, String name, String className,
    boolean nullable, boolean indexKey, boolean primaryKey, boolean uniqueIndex, boolean autoIncrement)
  { this.no = no ;
    this.sqlType = sqlType ;
    this.name = name ;
    this.className = className ;
    this.nullable = nullable ;
    this.indexKey = indexKey ;
    this.primaryKey = primaryKey ;
    this.uniqueIndex = uniqueIndex ;
    this.autoIncrement = autoIncrement ;
  }
  public String getClassName()
  {
    return className;
  }
  public void setClassName(String className)
  {
    this.className = className;
  }
  public Object getData()
  {
    return data;
  }
  public void setData(Object data)
  {
    this.data = data;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public int getNo()
  {
    return no;
  }
  public void setNo(int no)
  {
    this.no = no;
  }
  public boolean isNullable()
  {
    return nullable;
  }
  public void setNullable(boolean nullable)
  {
    this.nullable = nullable;
  }
  public boolean isPrimaryKey()
  {
    return primaryKey;
  }
  public void setPrimaryKey(boolean primaryKey)
  {
    this.primaryKey = primaryKey;
  }
  public int getSqlType()
  {
    return sqlType;
  }
  public void setSqlType(int sqlType)
  {
    this.sqlType = sqlType;
  }
  public boolean isUniqueIndex()
  {
    return uniqueIndex;
  }
  public void setUniqueIndex(boolean uniqueIndex)
  {
    this.uniqueIndex = uniqueIndex;
  }
  public boolean isIndexKey()
  {
    return indexKey;
  }

  public boolean isAutoIncrement() {
    return autoIncrement;
  }

  public void setIndexKey(boolean indexKey)
  {
    this.indexKey = indexKey;
  }

  public void setAutoIncrement(boolean autoIncrement) {
    this.autoIncrement = autoIncrement;
  }
}
