package com.bc.sql ;

import java.util.Vector ;
import java.sql.*;


public interface SqlContext
{


  public void connect() throws Exception ;

  public int insert( SqlRecord data)
    throws Exception ;

  public int insert( SqlRecord data, boolean updateObject)
    throws Exception ;

  public int delete( SqlRecord data)
    throws Exception ;

  public int update( SqlRecord data)
    throws Exception ;

  public int update( SqlRecord data, SqlRecord originalData)
    throws Exception ;

  public int updateWhere( SqlRecord data, SqlRecord originalData)
    throws Exception ;

  public void fetch( SqlTable table)
    throws Exception ;

  public void fetch( SqlTable table, String queryClause)
    throws Exception ;

  public void fetch( SqlTable table, SqlRecord item)
    throws Exception ;

  public void fetchLike( SqlTable table, SqlRecord item)
    throws Exception ;

  public SqlResult fetch( String query)
    throws Exception ;

}
