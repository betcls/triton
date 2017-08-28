package com.bc.sql.mysql;

import java.io.* ;
import java.util.* ;

import com.bc.servlet.otp3.* ;
import com.bc.sql.SqlResult ;


public class MySqlHttpRequest extends OtpRequest implements Serializable
{ static final long serialVersionUID = 195711000L ;
  //----------------------------------------------
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;
  //----------------------------------------------
  public final static String CONNECT_SINGLE_DB = "Connect single db" ;
  public final static String CONNECT_GENERIC = "Connect generic" ;
  public final static String DISCONNECT = "Disconnect" ;

  public final static String SQL_INSERT = "Insert" ;
  public final static String SQL_DELETE = "Delete" ;
  public final static String SQL_UPDATE = "Update" ;
  public final static String SQL_FETCH_GENERIC = "Fetch Generic" ;
  public final static String SQL_FETCH_ALL = "Fetch All" ;
  public final static String SQL_FETCH_WHERE = "Fetch Where" ;
  public final static String SQL_FETCH_LIKE = "Fetch Like" ;

  private MySqlHttpRequest()
  {
  }


  public static boolean connect( OtpServletConnection conn)
    throws Exception
  { MySqlHttpRequest rmc = new MySqlHttpRequest() ;
    rmc.setMethodName( MySqlHttpRequest.CONNECT_SINGLE_DB) ;
    Object response = conn.sendRequest( rmc) ;
    return ((Boolean)response).booleanValue() ;
  }

  public static boolean connect( OtpServletConnection conn, String dbUrl)
    throws Exception
  { MySqlHttpRequest rmc = new MySqlHttpRequest() ;
    //rmc.setMethodName( MySqlHttpRequest.CONNECT_GENERIC) ;
    rmc.setMethodName( MySqlHttpRequest.CONNECT_SINGLE_DB) ;
    rmc.addMethodArg( dbUrl);
    Object response = conn.sendRequest( rmc) ;
    return ((Boolean)response).booleanValue() ;
  }

  public static boolean disconnect( OtpServletConnection conn)
    throws Exception
  { MySqlHttpRequest rmc = new MySqlHttpRequest() ;
    rmc.setMethodName( MySqlHttpRequest.DISCONNECT) ;
    Object response = conn.sendRequest( rmc) ;
    return ((Boolean)response).booleanValue() ;
  }

  // --- INSERT
  public static Vector sqlInsert( OtpServletConnection conn,
    String tableName, String [] columnNames, Object [] data, String query)
    throws Exception
  { MySqlHttpRequest rmc = new MySqlHttpRequest() ;
    rmc.setMethodName( MySqlHttpRequest.SQL_INSERT) ;

    rmc.addMethodArg( tableName);
    rmc.addMethodArg( columnNames);
    rmc.addMethodArg( data);
    rmc.addMethodArg( query);
    Object response = conn.sendRequest( rmc) ;
    return (Vector)response ;
  }

  //--- DELETE
  public static int sqlDelete( OtpServletConnection conn,
    String tableName, String [] columnNames, Object [] data)
    throws Exception
  { MySqlHttpRequest rmc = new MySqlHttpRequest() ;
    rmc.setMethodName( MySqlHttpRequest.SQL_DELETE) ;
    rmc.addMethodArg( tableName);
    rmc.addMethodArg( columnNames);
    rmc.addMethodArg( data);
    Object response = conn.sendRequest( rmc) ;
    return ((Integer)response).intValue() ;
  }
  //--- UPDATE
  public static int sqlUpdate( OtpServletConnection conn,
    String tableName, String [] columnNames, Object [] data,
    String [] whereColumnNames, Object [] whereData)
    throws Exception
  { MySqlHttpRequest rmc = new MySqlHttpRequest() ;
    rmc.setMethodName( MySqlHttpRequest.SQL_UPDATE) ;

    rmc.addMethodArg( tableName);
    rmc.addMethodArg( columnNames);
    rmc.addMethodArg( data);
    rmc.addMethodArg( whereColumnNames);
    rmc.addMethodArg( whereData);
    Object response = conn.sendRequest( rmc) ;
    return ((Integer)response).intValue() ;
  }

  //--- FETCH
  public static SqlResult fetch( OtpServletConnection conn, String query)
    throws Exception
  { MySqlHttpRequest rmc = new MySqlHttpRequest() ;
    rmc.setMethodName( MySqlHttpRequest.SQL_FETCH_GENERIC);
    rmc.addMethodArg( query);
    Object response = conn.sendRequest( rmc) ;
    return (SqlResult)response ;
  }

  public static Vector sqlFetchAll( OtpServletConnection conn, String query)
    throws Exception
  { MySqlHttpRequest rmc = new MySqlHttpRequest() ;
    rmc.setMethodName( MySqlHttpRequest.SQL_FETCH_ALL) ;
    rmc.addMethodArg( query);
    Object response = conn.sendRequest( rmc) ;
    return (Vector)response ;
  }

  public static Vector sqlFetchWhere( OtpServletConnection conn, String query,
    String [] whereColumnNames, Object [] whereData)
    throws Exception
  { MySqlHttpRequest rmc = new MySqlHttpRequest() ;
    rmc.setMethodName( MySqlHttpRequest.SQL_FETCH_WHERE) ;
    rmc.addMethodArg( query);
    rmc.addMethodArg( whereColumnNames);
    rmc.addMethodArg( whereData);
    Object response = conn.sendRequest( rmc) ;
    return (Vector)response ;
  }

  public static Vector sqlFetchWhereLike( OtpServletConnection conn, String query,
    String whereLikeClause)
    throws Exception
  { MySqlHttpRequest rmc = new MySqlHttpRequest() ;
    rmc.setMethodName( MySqlHttpRequest.SQL_FETCH_LIKE) ;
    rmc.addMethodArg( query);
    rmc.addMethodArg( whereLikeClause);
    Object response = conn.sendRequest( rmc) ;
    return (Vector)response ;
  }








}
