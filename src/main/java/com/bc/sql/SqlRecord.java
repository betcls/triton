package com.bc.sql;



public interface SqlRecord
{ SqlDef getDef() ;

  String [] getColumnNames() ;

  Object [] getData() ;

  void setData( Object [] data)
    throws Exception ;

  public SqlDataPair getUniqueData()
    throws Exception ;

  public SqlDataPairList getDataPairList()
    throws Exception ;

  public SqlDataPairList getUpdateDataPairList()
    throws Exception ;

  public SqlDataPairList compaire( SqlRecord modifiedRecord)
    throws Exception ;
}
