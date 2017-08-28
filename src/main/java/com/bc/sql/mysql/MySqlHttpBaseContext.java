package com.bc.sql.mysql;

import java.util.*;
import java.sql.*;

import com.bc.sql.* ;
import com.bc.servlet.otp3.*;


public abstract class MySqlHttpBaseContext implements SqlContext
{
  protected String dbUrl = null ;


  protected OtpServletConnection appCon = null ;

  public MySqlHttpBaseContext()
    throws Exception
  {
  }



  public abstract void connect()
    throws Exception ;


  public void disconnect()
    throws Exception
  { boolean result = MySqlHttpRequest.disconnect( appCon) ;
  }


  public int insert( SqlRecord record)
    throws Exception
  { return this.insert( record, false) ;
  }

  public int insert( SqlRecord record, boolean updateObject)
    throws Exception
  { SqlDef def = record.getDef() ;

    String tableName = def.getName() ;
    String [] columnNames = def.getColumnList().getColumnNames() ;
    Object [] data = record.getData() ;


    Vector result = MySqlHttpRequest.sqlInsert( this.appCon, tableName, columnNames, data, record.getDef().getQuery()) ;

    if( updateObject && result.size() > 0)
    { record.setData( (Object [])(result.elementAt(result.size()-1)));
    }
    return result.size() ;
  }


  public int delete( SqlRecord record)
    throws Exception
  { SqlDef def = record.getDef() ;
    SqlDataPair unique = record.getUniqueData() ;

    String tableName = def.getName() ;
    String [] whereColumnNames = new String[]{unique.getName()} ;
    Object [] whereData = new Object[]{unique.getValue()} ;


    return MySqlHttpRequest.sqlDelete( this.appCon, tableName, whereColumnNames, whereData) ;
  }

  public int update( SqlRecord record)
    throws Exception
  { SqlDef def = record.getDef() ;
    SqlDataPair unique = record.getUniqueData() ;

    String tableName = def.getName() ;
    String [] columnNames = def.getColumnList().getColumnNames() ;
    Object [] data = record.getData() ;
    String [] whereColumnNames = new String[]{unique.getName()} ;
    Object [] whereData = new Object[]{unique.getValue()} ;


    return MySqlHttpRequest.sqlUpdate( this.appCon, tableName, columnNames, data, whereColumnNames, whereData) ;
  }

  public int update( SqlRecord record, SqlRecord originalRecord)
    throws Exception
  { SqlDataPairList toUpdate = record.compaire( originalRecord) ;
    if( toUpdate.getSize() == 0)
    { return 0 ;
    }
    //---
    SqlDef def = record.getDef() ;
    SqlDataPair unique = record.getUniqueData() ;
    //---
    String tableName = def.getName() ;
    String [] columnNames = toUpdate.getNames() ;
    Object [] data = toUpdate.getValues() ;
    String [] whereColumnNames = new String[]{unique.getName()} ;
    Object [] whereData = new Object[]{unique.getValue()} ;
    //---
   return  MySqlHttpRequest.sqlUpdate( this.appCon, tableName, columnNames, data, whereColumnNames, whereData) ;
  }

  public int updateWhere( SqlRecord dataRecord, SqlRecord whereRecord)
    throws Exception
  {
    //---
    String tableName = dataRecord.getDef().getName() ;
    String [] columnNames = dataRecord.getDataPairList().getNames() ;
    Object [] data = dataRecord.getDataPairList().getValues();
    String [] whereColumnNames = whereRecord.getDataPairList().getNames() ;
    Object [] whereData = whereRecord.getDataPairList().getValues();
    //---
   return  MySqlHttpRequest.sqlUpdate( this.appCon, tableName, columnNames, data, whereColumnNames, whereData) ;
  }

  public void fetch( SqlTable table)
    throws Exception
  { SqlDef def = table.getDef() ;
    Vector result = MySqlHttpRequest.sqlFetchAll( this.appCon, def.getQuery());
    table.addData( result);
  }

  public void fetch( SqlTable table, String queryClause)
    throws Exception
  { throw new Exception( "method is not implemented yet") ;
  }

  public void fetch( SqlTable table, SqlRecord whereRecord)
    throws Exception
  { SqlDef def = table.getDef() ;
    String query = def.getQuery() ;
    String [] whereColumnNames = whereRecord.getColumnNames() ;
    Object [] whereData = whereRecord.getData() ;
    Vector result = MySqlHttpRequest.sqlFetchWhere( this.appCon, query, whereColumnNames, whereData);
    table.addData( result);
  }

  public void fetchLike( SqlTable table, SqlRecord whereRecord)
    throws Exception
  { SqlDef def = table.getDef() ;
    SqlDataPairList whereList = whereRecord.getDataPairList() ;

    String query = def.getQuery() ;
    String [] names = whereList.getNames() ;
    Object [] values = whereList.getValues() ;
    String whereClause = "" ;
    for( int i = 0; i < names.length; i++)
    { if( i > 0)
      { whereClause += " AND " ;
      }
      whereClause += names[i] + " LIKE " + "'" + values[i].toString() +"'" ;
    }
    Vector result = MySqlHttpRequest.sqlFetchWhereLike( this.appCon, query, whereClause) ;
    table.addData( result);
  }

  public SqlResult fetch( String query)
    throws Exception
  { return MySqlHttpRequest.fetch( this.appCon, query) ;
  }

}
