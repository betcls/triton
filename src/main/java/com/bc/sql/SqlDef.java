package com.bc.sql;


import java.io.* ;
import java.sql.* ;

public class SqlDef implements Serializable
{ //----------------------------------------------------
  static final long serialVersionUID = 19571L ;
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;
  //----------------------------------------------------
  static final int TYPE_TABLE = 1 ;
  static final int TYPE_QUERY = 2 ;
  //----------------------------------------------------
  private String name = null ;
  private int type = 0 ;
  private SqlColumnList columnList = new SqlColumnList() ;
  private String query = null ;

  public SqlDef()
  {
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public int getType()
  {
    return type;
  }
  public void setType(int type)
  {
    this.type = type;
  }
  public SqlColumnList getColumnList()
  {
    return columnList;
  }
  public void setColumnList(SqlColumnList columnList)
  {
    this.columnList = columnList;
  }
  public String getQuery()
  {
    return query;
  }
  public void setQuery(String query)
  {
    this.query = query;
  }
}