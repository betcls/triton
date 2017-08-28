package com.bc.util ;
import java.util.* ;
import java.io.* ;

class TableRowColData implements Serializable
{ ////////////////////////////////////////////////
  static final long serialVersionUID = 19570001L ;
  // Version
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;
  ////////////////////////////////////////////////
  Hashtable data = new Hashtable() ;

  public TableRowColData()
  {
  }

  public int add( String key, int index)
  { Object obj = data.get( key) ;
    if( obj == null)
    { data.put( key, new Integer( index)) ;
      return index ;
    }
    else
    { return getIndex( key) ;
    }
  }

  public int getIndex( String key)
  { Object obj = data.get( key) ;
    if( obj == null)
      return -1 ;
    else
      return ((Integer)obj).intValue() ;
  }

  public int getSize()
  { return data.size() ;
  }

  public String [] getKeys()
  { String [] result = new String[data.size()] ;
    int counter = 0 ;
    Enumeration enum1 = data.keys() ;
    while( enum1.hasMoreElements())
    { String key = (String)enum1.nextElement() ;
      int index = getIndex( key) ;
      result[index] = key ;
    }
    return result ;
  }

  public boolean equals( TableRowColData obj)
  { String [] thisKeys = getKeys() ;
    String [] objKeys = obj.getKeys() ;
    //---
    if( thisKeys.length != objKeys.length)
    { return false ;
    }
    //---
    for( int i = 0; i < thisKeys.length; i++)
    { if( ! thisKeys[i].equals( objKeys[i]))
      { return false ;
      }
    }
    return true ;
  }



}