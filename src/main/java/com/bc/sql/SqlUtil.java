package com.bc.sql ;

import java.sql.* ;
import java.math.*;
import java.util.* ;
//import com.bc.util.DataSheet ;

public class SqlUtil
{ private Connection dbCon = null ;

  public SqlUtil( Connection dbCon)
  { this.dbCon = dbCon ;
  }







  //----------------------------------------------------------------------------
  // --- INSERT
  //----------------------------------------------------------------------------
  public int insert( String tableName, String [] fields, Object [] data)
    throws Exception
  { StringBuffer buf = new StringBuffer(1024) ;
    int nbFields = fields.length ;
    int count = 0 ;
    int rows = 0 ;
    //---
    buf.append( "INSERT INTO ") ;
    buf.append( tableName) ;
    buf.append( " (") ;
    for( int i = 0; i < nbFields ; i++)
    { if( data[i] == null)
      { continue ;
      }
      if( count > 0)
      { buf.append( ", ") ;
      }
      buf.append( fields[i]) ;
      count++ ;
    }
    //
    count = 0 ;
    buf.append( ") VALUES (") ;
    for( int i = 0; i < nbFields ; i++)
    { if( data[i] == null)
      { continue ;
      }
      if( count > 0)
      { buf.append( ", ") ;
      }
      buf.append( " ?") ;
      count++ ;
    }
    buf.append( ")") ;
    //---
    //System.out.println( buf.toString());
    PreparedStatement stm = dbCon.prepareStatement( buf.toString()) ;
    //---
    count = 0 ;
    for( int i = 0; i < nbFields ; i++)
    { if( data[i] == null)
      { continue ;
      }
      stm.setObject( count + 1, data[i]) ;
      count++ ;
    }
    //--- execute
    try
    { rows = stm.executeUpdate() ;
    }
    catch( SQLException excep)
    { throw new Exception( excep.getMessage() + " SQL state : " + excep.getSQLState()) ;
    }
    //---
    stm.close() ;
    //---
    return rows ;
  }

  //----------------------------------------------------------------------------
  // --- UPDATE
  //----------------------------------------------------------------------------
  public int update( String tableName, String [] fields, Object [] data,
    String [] whereFields, Object [] whereData)
    throws Exception
  { StringBuffer buf = new StringBuffer(1024) ;
    int nbFields = fields.length ;
    int count = 0 ;
    int rows = 0 ;
    //---
    buf.append( "UPDATE ") ;
    buf.append( tableName) ;
    buf.append( " SET ") ;
    for( int i = 0; i < nbFields ; i++)
    { if( data[i] == null)
      { continue ;
      }
      if( count > 0)
      { buf.append( ", ") ;
      }
      buf.append( fields[i]) ;
      buf.append( " = ?") ;
      count++ ;
    }
    if( count == 0)
    { throw new Exception( "No values provided for update 'SET' clause") ;
    }
    //---
    buf.append( " WHERE 1=1 ") ;
    nbFields = whereFields.length ;
    count = 0 ;
    for( int i = 0; i < nbFields ; i++)
    { if( whereData[i] == null)
      { continue ;
      }
      buf.append( " AND ") ;
      buf.append( whereFields[i]) ;
      buf.append( " = ?") ;
      count++ ;
    }
    if( count == 0)
    { throw new Exception( "No values provided for update 'WHERE' clause") ;
    }
    //---
    //System.out.println( buf.toString());
    PreparedStatement stm = dbCon.prepareStatement( buf.toString()) ;
    //---
    count = 1 ;
    if( data != null)
    {
      for( int i = 0; i < data.length; i++)
      { Object obj = data[i] ;
        if( obj != null)
        { stm.setObject( count, data[i]);
          count++ ;
        }
      }
    }

    //---
    if( whereData != null)
    {
      for( int i = 0; i < whereData.length; i++)
      { Object obj = whereData[i] ;
        if( obj != null)
        { stm.setObject( count, whereData[i]);
          count++ ;
        }
      }
    }
    //--- execute
    try
    { rows = stm.executeUpdate() ;
    }
    catch( SQLException excep)
    { throw new Exception( excep.getMessage() + " SQL state : " + excep.getSQLState()) ;
    }
    //---
    stm.close() ;
    //---
    return rows ;
  }

  public int updateNullable( String tableName, String [] fields, Object [] data,
    String [] whereFields, Object [] whereData)
    throws Exception
  { StringBuffer buf = new StringBuffer(1024) ;
    int nbFields = fields.length ;
    int count = 0 ;
    int createdRows = 0 ;
    //---
    buf.append( "UPDATE ") ;
    buf.append( tableName) ;
    buf.append( " SET ") ;
    for( int i = 0; i < nbFields ; i++)
    {
      if( count > 0)
      { buf.append( ", ") ;
      }
      buf.append( fields[i]) ;
      buf.append( " = ?") ;
      count++ ;
    }
    if( count == 0)
    { throw new Exception( "No values provided for update 'SET' clause") ;
    }
    //---
    buf.append( " WHERE 1=1 ") ;
    nbFields = whereFields.length ;
    count = 0 ;
    for( int i = 0; i < nbFields ; i++)
    {
      buf.append( " AND ") ;
      buf.append( whereFields[i]) ;
      buf.append( " = ?") ;
      count++ ;
    }
    if( count == 0)
    { throw new Exception( "No values provided for update 'WHERE' clause") ;
    }
    //---
    //System.out.println( buf.toString());
    PreparedStatement stm = dbCon.prepareStatement( buf.toString()) ;
    //---
    count = 1 ;
    if( data != null)
    {
      for( int i = 0; i < data.length; i++)
      { Object obj = data[i] ;

        { stm.setObject( count, data[i]);
          count++ ;
        }
      }
    }
    //---
    if( whereData != null)
    { for( int i = 0; i < whereData.length; i++)
      { Object obj = whereData[i] ;
        { stm.setObject( count, whereData[i]);
          count++ ;
        }
      }
    }
    //--- execute
    try
    { createdRows = stm.executeUpdate() ;
    }
    catch( SQLException excep)
    { throw new Exception( excep.getMessage() + " SQL state : " + excep.getSQLState()) ;
    }
    //---
    stm.close() ;
    //---
    return createdRows ;
  }
  //----------------------------------------------------------------------------
  // --- DELETE
  //----------------------------------------------------------------------------
  public int delete( String tableName,  String [] whereFields, Object [] whereData)
    throws Exception
  { int nbFields = whereFields.length ;
    //
    StringBuffer buf = new StringBuffer(512) ;
    int count = 0 ;
    buf.append( "DELETE FROM ") ;
    buf.append( tableName) ;
    buf.append( " WHERE 1=1 ") ;
    for( int i = 0; i < nbFields ; i++)
    { Object obj = whereData[i] ;
      if( obj == null)
        continue ;
      buf.append( " AND ") ;
      buf.append( whereFields[i]) ;
      buf.append( " = ?") ;
      count++ ;
    }
    //
    PreparedStatement stm = dbCon.prepareStatement( buf.toString()) ;
    //
    count = 0 ;
    if( whereData != null)
    { count = 1 ;
      for( int i = 0; i < nbFields; i++)
      { Object obj = whereData[i] ;
        if( obj != null)
        { stm.setObject( count, whereData[i]);
          count++ ;
        }
      }
    }
    //
    int rows = stm.executeUpdate() ;
    stm.close();
    return rows ;
  }
  //----------------------------------------------------------------------------
  // --- QUERY
  //----------------------------------------------------------------------------
  public Vector query( String tableName, String [] fields, String [] whereFields,
    Object [] whereData, String whereClause, String orderByClause)
    throws Exception
  { Vector result = new Vector(1000,500) ;

    StringBuffer buf = new StringBuffer(1024) ;
    int nbFields = fields.length ;
    //---
    buf.append( "SELECT ") ;
    for( int i = 0; i < nbFields ; i++)
    { buf.append( fields[i]) ;
      if( i < nbFields - 1)
      { buf.append( ", ") ;
      }
    }
    buf.append( " FROM ") ;
    buf.append( tableName) ;
    //---
    buf.append( " WHERE 1=1 ") ;
    if( whereData != null)
    { for( int i = 0; i < whereFields.length; i++)
      { Object obj = whereData[i] ;
        if( obj != null)
        { buf.append( " AND " + whereFields[i] + " = ?") ;
        }
      }
    }
    //---
    if( whereClause != null)
    { buf.append( " AND ") ;
      buf.append( whereClause) ;
    }
    //---
    if( orderByClause != null)
    { buf.append( " ORDER BY " + orderByClause) ;
    }

    //---
    PreparedStatement stm = dbCon.prepareStatement( buf.toString()) ;
    //---
    if( whereData != null)
    { int count = 1 ;
      for( int i = 0; i < whereFields.length; i++)
      { Object obj = whereData[i] ;
        if( obj != null)
        { stm.setObject( count, whereData[i]);
          count++ ;
        }
      }
    }
    //---
    ResultSet rset = stm.executeQuery() ;
    while( rset.next())
    { Object [] objs = new Object[nbFields] ;
      for( int i = 0; i < nbFields; i++)
      { objs[i] = rset.getObject( i+1) ;
      }
      result.add( objs) ;
    }

    //---
    rset.close();
    stm.close();
    //---
    //System.out.println( "aantal = " + result.size());
    //---
    return result ;
  }


  public Vector query( String query, String [] whereFields,
    Object [] whereData, String whereClause, String orderByClause)
    throws Exception
  { long startTime = System.currentTimeMillis() ;

    Vector result = new Vector(1000,500) ;

    StringBuffer buf = new StringBuffer(1024) ;
    //--- bevat de query een 'order by' clause ?
    String orderBy = "" ;
    String q = query ;
    int pos = query.toUpperCase().indexOf( "ORDER BY") ;
    if( pos > -1)
    { orderBy = query.substring( pos) ;
      q = query.substring( 0, pos) ;
    }


    //---
    buf.append( q) ;
    //---
    buf.append( " WHERE 1=1 ") ;
    if( whereData != null)
    { for( int i = 0; i < whereFields.length; i++)
      { Object obj = whereData[i] ;
        if( obj != null)
        { buf.append( " AND " + whereFields[i] + " = ?") ;
        }
      }
    }
    //---
    if( whereClause != null)
    { buf.append( " AND ") ;
      buf.append( whereClause) ;
    }
    //---
    if( orderByClause != null)
    { buf.append( " ORDER BY " + orderByClause) ;
    }
    else
    { buf.append( " " + orderBy) ;
    }
    //---
    //System.out.println( buf.toString());
    PreparedStatement stm = dbCon.prepareStatement( buf.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE) ;
    //---
    if( whereData != null)
    { int count = 1 ;
      for( int i = 0; i < whereFields.length; i++)
      { Object obj = whereData[i] ;
        if( obj != null)
        { stm.setObject( count, whereData[i]);
          count++ ;
        }
      }
    }
    //---
    ResultSet rset = stm.executeQuery() ;
    /*
    rset.last() ;
    result = new Vector(rset.getRow()) ;
    rset.beforeFirst();
    */
    int nbFields = rset.getMetaData().getColumnCount() ;
    while( rset.next())
    {
      Object [] objs = new Object[nbFields] ;
      for( int i = 0; i < nbFields; i++)
      { objs[i] = rset.getObject( i+1) ;
      }
      result.add( objs) ;
    }
    //---

    rset.close();
    stm.close();
    //---
    long endTime = System.currentTimeMillis() ;
    //System.out.println( "duration = " + (endTime-startTime)/1000);

    return result ;
  }

  public Vector query( String query, String queryClause)
    throws Exception
  { long startTime = System.currentTimeMillis() ;

    Vector result = new Vector(1000,500) ;

    StringBuffer buf = new StringBuffer(1024) ;
    //---
    buf.append( query) ;
    //---
    if( queryClause != null)
    { buf.append( " ") ;
      buf.append( queryClause) ;
    }
    //System.out.println( buf.toString());
    PreparedStatement stm = dbCon.prepareStatement( buf.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE) ;
    //---
    ResultSet rset = stm.executeQuery() ;
    /*
    rset.last() ;
    result = new Vector(rset.getRow()) ;
    rset.beforeFirst();
    */
    int nbFields = rset.getMetaData().getColumnCount() ;
    while( rset.next())
    {
      Object [] objs = new Object[nbFields] ;
      for( int i = 0; i < nbFields; i++)
      { objs[i] = rset.getObject( i+1) ;
      }
      result.add( objs) ;
    }
    //---
    rset.close();
    stm.close();

    //---
    long endTime = System.currentTimeMillis() ;
    //System.out.println( "duration = " + (endTime-startTime)/1000);

    return result ;
  }

  public SqlResult query( String query)
    throws Exception
  { long startTime = System.currentTimeMillis() ;

    SqlResult result = new SqlResult() ;

    //System.out.println( buf.toString());
    PreparedStatement stm = dbCon.prepareStatement( query,ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE) ;
    //---
    ResultSet rset = stm.executeQuery() ;
    /*
    rset.last() ;
    result = new Vector(rset.getRow()) ;
    rset.beforeFirst();
    */
    ResultSetMetaData rsmd = rset.getMetaData() ;
    int nbFields = rsmd.getColumnCount() ;

    for( int i = 0; i < rsmd.getColumnCount(); i++)
    { result.addCol( rsmd.getColumnName(i+1)) ;
    }

    int rowCounter = 0 ;
    while( rset.next())
    { for( int i = 0; i < nbFields; i++)
      { result.setData( rowCounter, i, rset.getObject( i+1));
      }
      rowCounter++ ;
    }
    //---
    rset.close();
    stm.close();

    //---
    long endTime = System.currentTimeMillis() ;
    //System.out.println( "duration = " + (endTime-startTime)/1000);

    return result ;
  }


  //----------------------------------------------------------------------------
  // --- COUNT
  //----------------------------------------------------------------------------
  public int count( String tableName)
    throws Exception
  { String sql = "SELECT COUNT(*) AS aantal FROM " + tableName ;
    Statement stm = dbCon.createStatement() ;
    ResultSet rs = stm.executeQuery( sql) ;
    rs.next() ;
    int rows = rs.getInt( "aantal") ;
    //---
    rs.close();
    stm.close();
    //---
    return rows ;
  }

  public int count( String tableName, String [] whereFields, Object [] whereData,
    String whereClause, Object dummy)
    throws Exception
  { StringBuffer buf = new StringBuffer(1024) ;
    int nbFields = 0 ;
    if( whereFields != null)
    { nbFields = whereFields.length ;
    }
    //---
    buf.append( "SELECT COUNT(*) AS aantal") ;
    buf.append( " FROM ") ;
    buf.append( tableName) ;
    //---
    buf.append( " WHERE 1=1 ") ;
    if( whereData != null)
    { for( int i = 0; i < nbFields; i++)
      { Object obj = whereData[i] ;
        if( obj != null)
        { buf.append( " AND " + whereFields[i] + " = ?") ;
        }
      }
    }
    //---
    if( whereClause != null)
    { buf.append( " AND ") ;
      buf.append( whereClause) ;
    }
    //---
    //System.out.println( buf.toString());
    PreparedStatement stm = dbCon.prepareStatement( buf.toString()) ;
    //---
    if( whereData != null)
    { int count = 1 ;
      for( int i = 0; i < nbFields; i++)
      { Object obj = whereData[i] ;
        if( obj != null)
        { stm.setObject( count, whereData[i]);
          count++ ;
        }
      }
    }
    //---
    ResultSet rset = stm.executeQuery() ;
    //---
    rset.next() ;
    int rows = rset.getInt( "aantal") ;
    //---
    rset.close();
    stm.close();
    //---

    return rows ;
  }


}
