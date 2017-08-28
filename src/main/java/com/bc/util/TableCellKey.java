package com.bc.util ;

import java.io.* ;

class TableCellKey implements Serializable
{ ////////////////////////////////////////////////
  static final long serialVersionUID = 195710000L ;
  // Versie
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;
  ////////////////////////////////////////////////
  int row = -1 ;
  int col = -1 ;

  public TableCellKey( int row, int col)
  { this.row = row ;
    this.col = col ;
  }

  public boolean equals( Object o)
  { if( ! (o instanceof TableCellKey))
      return false ;
    //
    TableCellKey obj = (TableCellKey)o ;
    if( obj.row == row && obj.col == col)
      return true ;
    else
      return false ;
  }

  public int hashCode()
  { return row * col ;
  }

  public int getRow()
  { return row ; }

  public int getCol()
  { return col ; }

}