package com.bc.sql;

import java.io.* ;

public class SqlDataPair implements Serializable
{
  //----------------------------------------------------
  static final long serialVersionUID = 19571L ;
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;
  //----------------------------------------------------
  private String name ;
  private Object value ;

  public SqlDataPair( String name, Object value)
  { this.name = name ;
    this.value = value ;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public Object getValue()
  {
    return value;
  }
  public void setValue(Object value)
  {
    this.value = value;
  }

  public void print( PrintWriter stream)
    throws Exception
  { stream.println( this.name + " = " + this.value) ;
  }


}