package com.bc.sql.mysql.server;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.* ;
import java.net.* ;
import java.sql.*;
//
import com.bc.servlet.otp3.*;
//
import com.bc.sql.* ;
import com.bc.sql.mysql.* ;


public abstract class MySqlSingleConnectionContextServlet extends OtpServlet
{ static final String JDBC_DRIVER = "com.mysql.jdbc.Driver" ;
  //----------------------------------------------------------------------------
  protected String httpSessionKey = "MySqlSingleDbConnectionContextServlet" ;
  protected int httpSessionTimeOutSeconds = -1 ;
  protected Connection dbCon = null ;
  protected String host = "localhost" ;
  protected String schema = null ;
  protected String user = null ;
  protected String password = null ;
  protected boolean autoReconnect = true ;

  //----------------------------------------------------------------------------
  class AppSession implements HttpSessionBindingListener
  {
    public AppSession()
    {
    }
    public void valueBound( HttpSessionBindingEvent event)
    { System.out.println( "bound to " + httpSessionKey + " : " + event.getSession().getId());
    }
    public void valueUnbound( HttpSessionBindingEvent event)
    { String httpSessionId = event.getSession().getId() ;
      System.out.println( "unbound from " + httpSessionKey + " : "+ event.getSession().getId());
    }
  }
  //----------------------------------------------------------------------------


  public void init( ServletConfig config) throws ServletException
  { super.init( config) ;
    System.out.println( "Servlet init") ;
    try
    { //--- Load the MySql driver
      Class.forName( JDBC_DRIVER).newInstance() ;
      //--- connect
      this.connect();
    }
    catch( Exception e)
    { System.out.println( e.getMessage()) ;
      cleanUp() ;
    }
    //---
    System.out.println( "Servlet init end") ;
  }

  public void cleanUp()
  { try
    { System.out.println( "cleanUp : Closing the DB connection") ;
      dbCon.close() ;
    }
    catch( SQLException e)
    {
    }
  }

  public void destroy()
  { try
    { System.out.println( "destroy : Closing the DB connection") ;
      dbCon.close() ;
    }
    catch( SQLException e)
    {
    }
  }
  //----------------------------------------------------------------------------
  public synchronized void connect()
    throws Exception
  { this.initDbConnectionAttributes() ;
    //
    String dbUrl = "jdbc:mysql://" ;
    dbUrl += this.host + "/" + this.schema + "?" + "user=" + this.user
      + "&" + "password=" + this.password
      + "&autoReconnect=" + (this.autoReconnect ? "true" : "false")
      + "&useUnicode=true";
    this.dbCon = DriverManager.getConnection( dbUrl) ;
  }
  public abstract void initDbConnectionAttributes() ;

  //----------------------------------------------------------------------------
  public Object processRequest( HttpServletRequest httpRequest, OtpRequest otpRequest)
    throws Exception
  { //--- session mgnt
    HttpSession httpSession = httpRequest.getSession() ;
    
	AppSession appSession = (AppSession)httpSession.getValue( httpSessionKey) ;

    //---
    Object request = otpRequest ;


    if( request instanceof MySqlHttpRequest)
    { MySqlHttpRequest rmc = (MySqlHttpRequest)request ;
      String action = rmc.getMethodName() ;
      //--- actions
      if( appSession == null)
      { if( action.equalsIgnoreCase( MySqlHttpRequest.CONNECT_SINGLE_DB))
        { appSession = new AppSession() ;
          httpSession.putValue( this.httpSessionKey, appSession) ;
          httpSession.setMaxInactiveInterval( httpSessionTimeOutSeconds) ;
          return new Boolean( true) ;
        }
        else
        { return new OtpSessionExpired( "Session has expired, please reconnect") ;
        }
      }
      else
      { if( action.equalsIgnoreCase( MySqlHttpRequest.CONNECT_SINGLE_DB))
        { return new Boolean( true) ;
        }
        else if( action.equalsIgnoreCase( MySqlHttpRequest.DISCONNECT))
        { httpSession.invalidate();
          return new Boolean( true) ;
        }
      }

      //---
      if( action.equalsIgnoreCase( MySqlHttpRequest.SQL_INSERT))
      { return new MySqlHttpRequestProcessor().sqlCreate( dbCon, rmc) ;
      }
      else if( action.equalsIgnoreCase( MySqlHttpRequest.SQL_DELETE))
      { return new MySqlHttpRequestProcessor().sqlDelete( dbCon, rmc) ;
      }
      else if( action.equalsIgnoreCase( MySqlHttpRequest.SQL_UPDATE))
      { return new MySqlHttpRequestProcessor().sqlUpdate( dbCon, rmc) ;
      }
      else if( action.equalsIgnoreCase( MySqlHttpRequest.SQL_FETCH_GENERIC))
      { return new MySqlHttpRequestProcessor().sqlFetchGeneric( dbCon, rmc) ;
      }
      else if( action.equalsIgnoreCase( MySqlHttpRequest.SQL_FETCH_ALL))
      { return new MySqlHttpRequestProcessor().sqlFetch( dbCon, rmc) ;
      }
      else if( action.equalsIgnoreCase( MySqlHttpRequest.SQL_FETCH_WHERE))
      { return new MySqlHttpRequestProcessor().sqlFetchWhere( dbCon, rmc) ;
      }
      else
      { return new Exception( "The server received an unknown request action") ;
      }
    }
    else
    { return new Exception( "The server received an unknown request object") ;
    }
  }
  //----------------------------------------------------------------------------

}
