package com.bc.sql;


import java.util.Vector ;
import java.util.Hashtable ;
import java.io.* ;


public class SqlColumnList implements Serializable
{ //----------------------------------------------------
  static final long serialVersionUID = 19571L ;
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;
  //----------------------------------------------------
  private Vector data = null ;

  public SqlColumnList()
  { data = new Vector() ;
  }

  public SqlColumnList( int initialSize )
  { data = new Vector( initialSize) ;
  }

  public void addObject( SqlColumn arg)
  { this.data.addElement( arg) ;
  }

  public void addObject( SqlColumnList tab)
  { if( tab == null) return ;
    for( int i = 0; i < tab.getSize(); i++)
    { this.data.addElement( tab.getObject(i)) ;
    }
  }

  public void removeAllObjects()
  { this.data.removeAllElements() ;
  }

  public SqlColumn getObject( int index)
  { if( index >= data.size())
    { return null ;
    }
    return (SqlColumn)data.elementAt( index) ;
  }

  public int getSize()
  { return data.size() ;
  }

  public String [] getColumnNames()
  { String [] result = new String[this.getSize()] ;
    for( int i = 0; i < this.getSize(); i++)
    { result[i] = this.getObject(i).getName() ;
    }
    return result ;
  }

}