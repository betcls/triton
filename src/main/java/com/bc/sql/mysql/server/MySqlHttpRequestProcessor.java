package com.bc.sql.mysql.server;

import java.util.*;
import java.sql.*;
import com.bc.sql.* ;
import com.bc.sql.mysql.* ;

class MySqlHttpRequestProcessor
{

  public MySqlHttpRequestProcessor()
  {
  }

  public Object sqlCreate( Connection dbCon, MySqlHttpRequest rmc)
    throws Exception
  { String tableName = rmc.getString( 0) ;
    String [] columnNames = (String [])rmc.getObject(1) ;
    Object [] data = (Object [])rmc.getObject( 2) ;
    String query = rmc.getString(3) ;
    //---
    SqlUtil util = new SqlUtil( dbCon) ;
    int row = util.insert( tableName, columnNames, data) ;
    if( row == 0)
    { return new Exception( "Couldn't create the DB record.") ;
    }
    //
    Vector result = util.query( query, columnNames, data, null, null) ;
    //
    return result ;
  }

  public Integer sqlDelete( Connection dbCon, MySqlHttpRequest rmc)
    throws Exception
  { String tableName = rmc.getString( 0) ;
    String [] columnNames = (String [])rmc.getObject(1) ;
    Object [] data = (Object [])rmc.getObject( 2) ;
    //---
    SqlUtil util = new SqlUtil( dbCon) ;
    int row = util.delete( tableName, columnNames, data) ;
    return new Integer( row) ;
  }

  public Integer sqlUpdate( Connection dbCon, MySqlHttpRequest rmc)
    throws Exception
  { String tableName = rmc.getString( 0) ;
    String [] columnNames = (String [])rmc.getObject(1) ;
    Object [] data = (Object [])rmc.getObject( 2) ;
    String [] whereColumnNames = (String [])rmc.getObject(3) ;
    Object [] whereData = (Object [])rmc.getObject( 4) ;
    //---
    SqlUtil util = new SqlUtil( dbCon) ;
    int row = util.updateNullable( tableName, columnNames, data, whereColumnNames, whereData) ;
    return new Integer( row) ;
  }

  public Object sqlFetchGeneric( Connection dbCon, MySqlHttpRequest rmc)
    throws Exception
  { String query = rmc.getString(0) ;
    //---
    SqlUtil util = new SqlUtil( dbCon) ;
    //System.out.println( "free memory before query = " +  (Runtime.getRuntime().freeMemory()/1024)) ;
    Object result = util.query( query) ;
    //System.out.println( "free memory after query = " +  (Runtime.getRuntime().freeMemory()/1024)) ;
    //
    return result ;
  }


  public Object sqlFetch( Connection dbCon, MySqlHttpRequest rmc)
    throws Exception
  {
    String query = rmc.getString(0) ;
    //---

    SqlUtil util = new SqlUtil( dbCon) ;
    System.out.println( "free memory before query = " +  (Runtime.getRuntime().freeMemory()/1024)) ;
    Vector result = util.query( query, null, null, null, null) ;
    System.out.println( "free memory after query = " +  (Runtime.getRuntime().freeMemory()/1024)) ;
    //
    return result ;
  }

  public Object sqlFetchWhere( Connection dbCon, MySqlHttpRequest rmc)
    throws Exception
  { String query = rmc.getString(0) ;
    String [] whereColumnNames = (String [])rmc.getObject(1) ;
    Object [] whereData = (Object [])rmc.getObject(2) ;
    //---

    SqlUtil util = new SqlUtil( dbCon) ;
    Vector result = util.query( query, whereColumnNames, whereData, null, null) ;
    //---
    return result ;
  }
}