package com.bc.sql.mysql;

import java.util.*;
import java.sql.*;

import com.bc.sql.* ;
import com.bc.servlet.otp3.*;


public class MySqlHttpSingleDbContext extends MySqlHttpBaseContext
{ public MySqlHttpSingleDbContext( OtpServletConnection servletConnection)
    throws Exception
  { this.appCon = servletConnection ;
  }
  public void connect()
    throws Exception
  { MySqlHttpRequest.connect( appCon) ;
  }
}