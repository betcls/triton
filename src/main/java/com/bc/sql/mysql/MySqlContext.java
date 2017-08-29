package com.bc.sql.mysql;

import java.io.* ;
import java.sql.* ;
import java.util.* ;

import com.bc.sql.* ;
//import com.bc.util.DataSheet;

public class MySqlContext implements SqlContext
{ private String jdbcDriver = "com.mysql.jdbc.Driver" ;
  private String fullDbUrl = null ;
  private String dbUrl = null ;
  private String host = null ;
  private String schema = null ;
  private String user = null ;
  private String password = null ;
  private boolean autoReconnect = true ;

  private Connection dbCon = null ;
  private PrintWriter logger = null ;

  public MySqlContext( String fullDbUrl)
  { this.fullDbUrl = fullDbUrl ;
  }

  public MySqlContext( String host, String schema, String user, String password)
  { this.host = host ;
    this.schema = schema ;
    this.user = user ;
    this.password = password ;
  }

  public MySqlContext( String host, String schema, String user, String password,
    boolean autoReconnect)
  { this.host = host ;
    this.schema = schema ;
    this.user = user ;
    this.password = password ;
    this.autoReconnect = autoReconnect ;
  }



  public void connect()
    throws Exception
  { Class.forName( jdbcDriver).newInstance() ;
    if( fullDbUrl == null)
    { this.dbUrl = "jdbc:mysql://" + host + "/" + schema
        + "?user=" + this.user
        + "&password=" + this.password
        + "&autoReconnect=true"
        + "&useUnicode=true"
        + "&tinyInt1isBit=true" ;
    	
    	System.out.println( "DBURL = " + this.dbUrl);
    }
    else
    { this.dbUrl = this.fullDbUrl ;
    }
    this.dbCon = DriverManager.getConnection( this.dbUrl) ;
    
  }

  public void setLogger( PrintWriter logger)
  { this.logger = logger ;
  }



  //////////////////////////////////////////////////////////////////////////////

  public int insert( SqlRecord record)
    throws Exception
  { return this.insert( record, false) ;
  }

  public int insert( SqlRecord record, boolean updateObject)
    throws Exception
  { SqlDef def = record.getDef() ;
    SqlUtil util = new SqlUtil( this.dbCon) ;
    int result = util.insert( def.getName(), def.getColumnList().getColumnNames(), record.getData());
    if( ! updateObject)
    { return result;
    }
    Vector v = util.query( def.getQuery(), def.getColumnList().getColumnNames(), record.getData(),null,null) ;
    if( v.size() > 0)
    { record.setData( (Object [])(v.elementAt(v.size()-1)));
    }
    return result ;
  }


  public int delete( SqlRecord record)
    throws Exception
  { SqlDef def = record.getDef() ;
    SqlDataPair unique = record.getUniqueData() ;
    SqlUtil util = new SqlUtil( this.dbCon) ;
    int rows = util.delete( def.getName(), new String[]{unique.getName()},
      new Object[]{unique.getValue()}) ;
    return rows ;
  }

  /*
  public int update( SqlRecord record)
    throws Exception
  { SqlDef def = record.getDef() ;
    SqlDataPair unique = record.getUniqueData() ;
    SqlUtil util = new SqlUtil( this.dbCon) ;
    return util.update( def.getName(),
      def.getColumnList().getColumnNames(),
      record.getData(),
      new String[]{unique.getName()},
      new Object[]{unique.getValue()}) ;
  }
  */

 // Onderstaande code vervangt bovenstaande code om dit gelijklopend te
 // te houden met AccessContext class
  public int update( SqlRecord record)
     throws Exception
  {
    SqlDef def = record.getDef();
    SqlDataPair unique = record.getUniqueData();
    SqlUtil util = new SqlUtil(this.dbCon);
    SqlDataPairList dataList = record.getUpdateDataPairList();
    return util.update(def.getName(),
                       dataList.getNames(),
                       dataList.getValues(),
                       new String[] {unique.getName()},
                       new Object[] {unique.getValue()});
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
    if( logger != null)
    { logger.println("-----------------------------------------------------");
      unique.print( logger);
      logger.println( "New data :");
      toUpdate.print( logger);
      logger.println( "Current data :");
      record.compaire(originalRecord).print( logger);
      logger.println("-----------------------------------------------------");

    }
    //---
    String [] colNames = toUpdate.getNames() ;
    Object [] values = toUpdate.getValues() ;
    //---
    SqlUtil util = new SqlUtil( this.dbCon) ;
    return util.updateNullable( def.getName(),
      colNames,
      values,
      new String[]{unique.getName()},
      new Object[]{unique.getValue()}) ;
  }

  public int updateWhere( SqlRecord dataToChange, SqlRecord whereData)
    throws Exception
  {
    //---
    SqlDef def = dataToChange.getDef();
    SqlDataPairList unique = whereData.getDataPairList();
    //---
    String[] colNames = dataToChange.getDataPairList().getNames();
    Object[] values = dataToChange.getDataPairList().getValues();
    //---
    SqlUtil util = new SqlUtil(this.dbCon);
    return util.updateNullable(
        def.getName(),
        colNames,
        values,
        whereData.getDataPairList().getNames(),
        whereData.getDataPairList().getValues());
  }



  public void fetch( SqlTable table)
    throws Exception
  { SqlDef def = table.getDef() ;
    SqlUtil util = new SqlUtil( this.dbCon) ;
    Vector result = util.query( def.getQuery(), null, null,null,null) ;
    table.addData( result);
  }


  public void fetch( SqlTable table, String queryClause)
    throws Exception
  { SqlDef def = table.getDef() ;
    String query = def.getQuery() ;
    SqlUtil util = new SqlUtil( this.dbCon) ;
    Vector result = util.query( def.getQuery(), queryClause) ;
    table.addData( result);
  }

  public void fetch( SqlTable table, SqlRecord whereRecord)
    throws Exception
  { SqlDef def = table.getDef() ;
    SqlDataPairList whereList = whereRecord.getDataPairList() ;
    SqlUtil util = new SqlUtil( this.dbCon) ;
    Vector result = util.query( def.getQuery(),
      whereList.getNames(), whereList.getValues(),null,null) ;
    table.addData( result);
  }

  public void fetchLike( SqlTable table, SqlRecord whereRecord)
    throws Exception
  { SqlDef def = table.getDef() ;
    SqlDataPairList whereList = whereRecord.getDataPairList() ;
    String [] names = whereList.getNames() ;
    Object [] values = whereList.getValues() ;
    String whereClause = "" ;
    for( int i = 0; i < names.length; i++)
    { if( i > 0)
      { whereClause += " AND " ;
      }
      whereClause += names[i] + " LIKE " + "'" + values[i].toString() +"'" ;
    }
    SqlUtil util = new SqlUtil( this.dbCon) ;
    Vector result = util.query( def.getQuery(),
      null, null, whereClause, null) ;
    table.addData( result);
  }

  public SqlResult fetch( String query)
    throws Exception
  { SqlUtil util = new SqlUtil( this.dbCon) ;
    return util.query( query) ;
  }






}
