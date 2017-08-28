package com.bc.util ;

import java.util.* ;
import java.io.* ;




public class DataSheet implements Serializable
{ static final long serialVersionUID = 195710000L ;
  // Version
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;

  private final static String DQ = "\"" ;
  private final static String FS = "\t" ;

  private TableRowColData colData = new TableRowColData() ;
  private TableCellData dataTab = new TableCellData() ;





  public DataSheet()
  {
  }

  public int addCol( String colName)
  { int index = colData.getIndex( colName) ;
    if( index == -1)
    { index = dataTab.getCols() ;
      colData.add( colName, index) ;
      dataTab.setColumnSize( index + 1) ;
    }
    return index ;
  }


  public void setData( int row, String colName, Object obj)
  { int col = this.addCol( colName) ;
    dataTab.setObject( row, col, obj) ;
  }

  public void setData( int row, int col, Object obj)
  { dataTab.setObject( row, col, obj) ;
  }

  public int getRowSize()
  { return dataTab.getRows() ;
  }

  public int getColSize()
  { return dataTab.getCols() ;
  }

  public Object getData( int row, String colName)
  { int col = colData.getIndex(colName) ;
    return dataTab.getObject( row, col);
  }

  public Object getData( int row, int col)
  { return dataTab.getObject( row, col);
  }


  private TableRowColData getColData()
  { return colData ;
  }


  public void sort( String colName)
  { int col = colData.getIndex(colName) ;
    sort( col) ;
  }

  public void sort( int col)
  { int rows = dataTab.getRows() ;
    SortPair [] tab = new SortPair[rows] ;
    for( int i = 0; i < rows; i++)
    { tab[i] = new SortPair( i, dataTab.getObject(i,col)) ;
    }

    QuickSort sorter = new QuickSort() ;
    sorter.sort( tab, new SortCompare());

    int [] swapIndexTab = new int[rows] ;
    for( int i = 0; i < rows; i++)
    { int oldIndex = tab[i].index ;
      //System.out.println( "" + i + " -> " + oldIndex) ;
      swapIndexTab[i] = oldIndex ;
    }
    dataTab.swapRow( swapIndexTab);
  }




  public void createColTotals( String [] colNames)
  { double total = 0 ;
    for( int i = 0; i < colNames.length; i++)
    { total = 0 ;
      for( int j = 0; j < dataTab.getRows(); j++)
      { Object obj = getData( j, colNames[i]) ;
        if( obj instanceof Integer)
        { total += ((Integer)obj).intValue() ;
        }
      }
      if( total != 0)
        this.setData( dataTab.getRows(), colNames[i], new Integer((int)total));
    }
  }


  public void print()
  { int rows = this.dataTab.getRows() ;
    int cols = this.dataTab.getCols() ;
    for( int i = 0; i < rows; i++)
    { for( int j = 0; j < cols; j++)
      { Object obj = getData( i, j) ;

        if( obj == null)
        { System.out.print( obj + " ") ;
          continue ;
        }
        //String s = (String)obj ;
        System.out.print( "" + obj + " ") ;
      }
      System.out.println() ;
    }
  }


  public String toString()
  { StringBuffer buffer = new StringBuffer() ;
    String [] colNames = colData.getKeys() ;
    // Creëren van de kolomtitels
    for( int i = 0; i < colNames.length; i++)
    { buffer.append( DQ + colNames[i] + DQ + FS) ;
    }
    buffer.append( '\n') ;
    //---
    for( int i = 0; i < dataTab.getRows(); i++)
    { for( int j = 0; j < colNames.length; j++)
      { Object obj = this.getData( i, colNames[j]) ;

        if( obj == null)
        { buffer.append( DQ + DQ + FS) ;
          continue ;
        }
        if( obj instanceof String)
          buffer.append( DQ + obj + DQ + FS)  ;
        else if( obj instanceof Integer)
          buffer.append( DQ + ((Integer)obj).toString() + DQ + FS) ;
      }
      buffer.append( "\n") ;
    }
    return buffer.toString() ;
  }


  public void save( String fileName)
    throws Exception
  { PrintWriter stream =
      new PrintWriter(
        new BufferedWriter(
          new FileWriter( fileName))) ;
    stream.print( this.toString());
    stream.flush();
    stream.close();
  }

  public String toPipeSeparatedString()
  { StringBuffer buffer = new StringBuffer() ;
    String [] colNames = colData.getKeys() ;

    // Creëren van de kolomtitels
    for( int i = 0; i < colNames.length; i++)
    { buffer.append( DQ + colNames[i] + DQ + FS) ;
    }
    buffer.append( '\n') ;

    for( int i = 0; i < this.getRowSize(); i++)
    { for( int j = 0; j < colNames.length; j++)
      { Object obj = this.getData( i, colNames[j]) ;

        if( obj == null)
        { continue ;
        }
        else if( obj instanceof String)
        { if( j == 0)
          { buffer.append( "" + obj) ;
          }
          else
          { buffer.append( " | " + obj) ;
          }
        }
      }
      buffer.append( "\n") ;
    }
    return buffer.toString() ;
  }

  public void savePipeSeparated( String fileName)
    throws Exception
  { PrintWriter stream =
      new PrintWriter(
        new BufferedWriter(
          new FileWriter( fileName))) ;
    stream.print( this.toPipeSeparatedString());
    stream.flush();
    stream.close();
  }



  public boolean equals( DataSheet obj)
  { boolean result = true ;
    // Vergelijken van de row & col namen
    if( ! colData.equals( obj.getColData()))
      return false ;

    //
    // Vergelijken van de cellen
    //
    String [] colNames = colData.getKeys() ;
    //
    for( int i = 0; i < dataTab.getRows(); i++)
    { for( int j = 0; j < colNames.length; j++)
      { Object obj1 = this.getData( i, colNames[j]) ;
        Object obj2 = obj.getData( i, colNames[j]) ;
        //---
        if( obj1 == null && obj2 == null)
        { continue ;
        }
        else if( obj1 == null && obj2 != null)
        { result = false ;
          System.out.println( "Not equal : " + "R" + i + " C" + j ) ;
          continue ;
        }
        else if( obj1 != null && obj2 == null)
        { result = false ;
          System.out.println( "Not equal : " + "R" + i + " C" + j ) ;
          continue ;
        }
        else if( ! obj1.getClass().getName().equals( obj2.getClass().getName()))
        { result = false ;
          System.out.println( "Not equal : " + "R" + i + " C" + j ) ;
          continue ;
        }
        // Voorlopig Strings vergelijken
        if( ! ((String)obj1).equals( (String)obj2))
        { result = false ;
          System.out.println( "Not equal : " + "R" + i + " C" + j ) ;
          continue ;
        }
      }
    }
    return result ;
  }

  //----------------------------------------------------------------------------
  //
  //----------------------------------------------------------------------------
  class SortPair
  { int index = 0 ;
    Object value = null ;
    SortPair( int index, Object value)
    { this.index = index ;
      this.value = value ;
    }
  }

  public class SortCompare implements Compare
  { public boolean lessThan( Object o1, Object o2)
    { Object val1 = ((SortPair)o1).value ;
      Object val2 = ((SortPair)o2).value ;
      //
      if( val1 == null && val2 != null)
        return true ;
      else if( val1 != null && val2 == null)
        return false ;
      else if( val1 == null && val2 == null)
        return false ;
      // Aanname : alle cellen van dezelfde kolom bevatten hetzelfde type !!!
      Class type = val1.getClass() ;
      if( type.getSuperclass() == java.lang.Number.class)
      { Number n1 = (Number)val1 ;
        Number n2 = (Number)val2 ;
        double d1 = n1.doubleValue();
        double d2 = n2.doubleValue();
        //
        if( d1 < d2)
          return true ;
        else
          return false ;
      }
      else if( type == java.util.Date.class)
      { Date d1 = (Date)val1 ;
        long n1 = d1.getTime();
        Date d2 = (Date)val2 ;
        long n2 = d2.getTime();
        if( n1 < n2)
          return true ;
        else
          return false ;
      }
      else if( type == String.class)
      { String s1 = (String)val1 ;
        String s2 = (String)val2 ;
        if( s1.compareTo(s2) < 0)
          return true ;
        else
          return false ;
      }
      else if( type == Boolean.class)
      { Boolean bool1 = (Boolean)val1 ;
        boolean b1 = bool1.booleanValue();
        Boolean bool2 = (Boolean)val2 ;
        boolean b2 = bool2.booleanValue();

        if( b1 == b2)
          return false ;
        else if( b1) // Define false < true
          return false ;
        else
          return true ;
      }
      else
      { String s1 = val1.toString();
        String s2 = val2.toString();
        if( s1.compareTo(s2) < 0)
          return true ;
        else
          return false ;
      }
    }
  }


}
