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


public abstract class MySqlContextServlet extends OtpServlet
{ static final String JDBC_DRIVER = "com.mysql.jdbc.Driver" ;
  //----------------------------------------------------------------------------
  protected Connection dbCon = null ;
  protected String httpSessionKey = "MySqlContextServlet" ;
  protected int httpSessionTimeOutSeconds = -1 ;


  //////////////////////////////////////////////////////////////////////////////
  class DbContext
  { Connection dbCon = null ;

    void connect( String dbUrl)
      throws Exception
    {
      this.dbCon = DriverManager.getConnection( dbUrl) ;
    }

    void disconnect()
      throws Exception
    { dbCon.close();

    }
  }


  //////////////////////////////////////////////////////////////////////////////
  class AppSession implements HttpSessionBindingListener
  { DbContext dbContext = null ;
    public AppSession( DbContext dbContext)
    { this.dbContext = dbContext ;
    }
    public void valueBound( HttpSessionBindingEvent event)
    { System.out.println( "bound to " + httpSessionKey + " : " + event.getSession().getId());
    }
    public void valueUnbound( HttpSessionBindingEvent event)
    { String httpSessionId = event.getSession().getId() ;
      System.out.println( "unbound from " + httpSessionKey + " : "+ event.getSession().getId());
      // Close the DB connection
      try
      { if( dbContext.dbCon != null)
        { dbContext.dbCon.close();
        }
      }
      catch( Exception excep)
      { System.out.println( excep.getMessage()) ;
      }
    }
  }

  public void init( ServletConfig config) throws ServletException
  { super.init( config) ;
    System.out.println( "Servlet init") ;
    try
    { //--- Load the MySql driver
      Class.forName( JDBC_DRIVER).newInstance() ;
      //--- init attributes
      initDbConnectionAttributes() ;

    }
    catch( Exception e)
    { System.out.println( e.getMessage()) ;
      cleanUp() ;
    }
    //---
    System.out.println( "Servlet init end") ;
  }

  protected abstract void initDbConnectionAttributes() ;

  public void cleanUp()
  { try
    { System.out.println( "De verbinding met de database wordt verbroken") ;
      dbCon.close() ;
    }
    catch( SQLException e)
    {
    }
  }

  public void destroy()
  { try
    { System.out.println( "De verbinding met de database wordt verbroken") ;
      dbCon.close() ;
    }
    catch( SQLException e)
    {
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  public Object processRequest( HttpServletRequest httpRequest, OtpRequest otpRequest)
    throws Exception
  { HttpSession httpSession = httpRequest.getSession() ;

    AppSession appSession = (AppSession)httpSession.getValue( httpSessionKey) ;
    DbContext dbContext = null ;

    if( appSession != null)
    { dbContext = appSession.dbContext ;
    }
    //---
    Object request = otpRequest ;



    if( request instanceof MySqlHttpRequest)
    { MySqlHttpRequest rmc = (MySqlHttpRequest)request ;
      String action = rmc.getMethodName() ;
      //--- Context stuff

      if( dbContext == null)
      { String dbUrl = null ;
        if( action.equalsIgnoreCase( MySqlHttpRequest.CONNECT_GENERIC))
        { dbUrl = rmc.getString(0) ;
        }
        else
        { return new OtpSessionExpired( "Session has expired, please reconnect") ;
        }
        dbContext = new DbContext() ;
        dbContext.connect( dbUrl) ;
        //--- Store the SqlContext in the App Session
        appSession = new AppSession( dbContext) ;
        httpSession.putValue( this.httpSessionKey, appSession) ;
        httpSession.setMaxInactiveInterval( httpSessionTimeOutSeconds) ;
        //---
        return new Boolean( true) ;
      }
      else
      { if( action.equalsIgnoreCase( MySqlHttpRequest.DISCONNECT))
        { httpSession.invalidate() ;
          return new Boolean( true) ;
        }
      }

      //--- actions
      if( action.equalsIgnoreCase( MySqlHttpRequest.SQL_INSERT))
      { return new MySqlHttpRequestProcessor().sqlCreate( dbContext.dbCon, rmc) ;
      }
      else if( action.equalsIgnoreCase( MySqlHttpRequest.SQL_DELETE))
      { return new MySqlHttpRequestProcessor().sqlDelete( dbContext.dbCon, rmc) ;
      }
      else if( action.equalsIgnoreCase( MySqlHttpRequest.SQL_UPDATE))
      { return new MySqlHttpRequestProcessor().sqlUpdate( dbContext.dbCon, rmc) ;
      }
      else if( action.equalsIgnoreCase( MySqlHttpRequest.SQL_FETCH_GENERIC))
      { return new MySqlHttpRequestProcessor().sqlFetchGeneric( dbContext.dbCon, rmc) ;
      }
      else if( action.equalsIgnoreCase( MySqlHttpRequest.SQL_FETCH_ALL))
      { return new MySqlHttpRequestProcessor().sqlFetch( dbContext.dbCon, rmc) ;
      }
      else if( action.equalsIgnoreCase( MySqlHttpRequest.SQL_FETCH_WHERE))
      { return new MySqlHttpRequestProcessor().sqlFetchWhere( dbContext.dbCon, rmc) ;
      }
      else
      { return new Exception( "The server received an unknown request method") ;
      }
    }
    else
    { return new Exception( "The server received an unknown request") ;
    }
  }
  //----------------------------------------------------------------------------







}
