package com.bc.sql;

import java.io.* ;
import java.util.Vector ;

public class SqlDataPairList implements Serializable
{
  //----------------------------------------------------
  static final long serialVersionUID = 19571L ;
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;
  //----------------------------------------------------
  private Vector data = null ;

  public SqlDataPairList()
  { data = new Vector() ;
  }

  public SqlDataPairList( int initialSize )
  { data = new Vector( initialSize) ;
  }

  public void addObject( SqlDataPair arg)
  { this.data.addElement( arg) ;
  }

  public void addObject( SqlDataPairList tab)
  { if( tab == null) return ;
    for( int i = 0; i < tab.getSize(); i++)
    { this.data.addElement( tab.getObject(i)) ;
    }
  }

  public void removeAllObjects()
  { this.data.removeAllElements() ;
  }

  public SqlDataPair getObject( int index)
  { if( index >= data.size())
    { return null ;
    }
    return (SqlDataPair)data.elementAt( index) ;
  }

  public int getSize()
  { return data.size() ;
  }

  public String [] getNames()
  { String [] result = new String[this.getSize()] ;
    for( int i = 0; i < this.getSize(); i++)
    { result[i] = this.getObject(i).getName() ;
    }
    return result ;
  }

  public Object [] getValues()
  { Object [] result = new Object[this.getSize()] ;
    for( int i = 0; i < this.getSize(); i++)
    { result[i] = this.getObject(i).getValue() ;
    }
    return result ;
  }



  public void print( PrintWriter stream)
    throws Exception
  { for( int i = 0; i < getSize(); i++)
    { this.getObject(i).print( stream) ;
    }
  }
}