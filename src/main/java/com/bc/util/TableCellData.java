package com.bc.util ;

import java.util.* ;
import java.io.* ;

class TableCellData implements Serializable
{ ////////////////////////////////////////////////
  static final long serialVersionUID = 195710000L ;
  // Versie
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;
  ////////////////////////////////////////////////
  int maxRow = 0 ;
  int maxCol = 0 ;
  int rowSize = 1000 ;
  int colSize = 10 ;
  int rowGrowthSize = 500 ;
  int colGrowthSize = 5 ;
  Object [][] data = null ;

  public TableCellData()
  { data = new Object[rowSize][colSize] ;
  }

  public TableCellData( int rowSize, int colSize)
  { this.rowSize = rowSize ;
    this.colSize = colSize ;
    data = new Object[rowSize][colSize] ;
  }

  public void setRowSize( int arg)
  { maxRow = arg ; }

  public void setColumnSize( int arg)
  { maxCol = arg ; }


  public void setObject( int row, int col, Object value)
  { if( row > rowSize - 1 && col > colSize -1 )
    { increaseRows( row - rowSize + 1) ;
      increaseCols( col - colSize + 1) ;
    }
    else if( row > rowSize - 1)
    { increaseRows( row - rowSize + 1) ;
    }
    else if( col > colSize - 1)
    { increaseCols( col - colSize + 1) ;
    }
    if( row >= maxRow)
      maxRow = row + 1 ;
    if( col >= maxCol)
      maxCol = col + 1 ;
    data[row][col] = value ;

  }

  public Object getObject( int row, int col)
  { if( row >= rowSize || col >= colSize || row < 0 || col < 0)
      return null ;
    return data[row][col] ;
  }

  public int getRows()
  { return maxRow ; }
  public int getCols()
  { return maxCol ; }



  private void increaseRows( int growthSize)
  { if( growthSize > rowGrowthSize)
    { rowSize += (int)(growthSize/rowGrowthSize) * rowGrowthSize ;
      if( (growthSize % rowGrowthSize) > 0)
        rowSize += rowGrowthSize ;
    }
    else
      rowSize += rowGrowthSize ;
    Object [][] tmpData = new Object[rowSize][colSize] ;
    try
    { System.arraycopy( data, 0, tmpData, 0, data.length);
    }
    catch( Exception excep)
    { System.out.println( excep.getMessage());
    }
    data = tmpData ;
  }

  private void increaseCols( int growthSize)
  { if( growthSize > colGrowthSize)
    { colSize += (int)(growthSize/colGrowthSize) * colGrowthSize ;
      if( (growthSize % colGrowthSize) > 0)
        colSize += colGrowthSize ;
    }
    else
      colSize += colGrowthSize ;

    Object [][] tmpData = new Object[rowSize][colSize] ;
    for( int i = 0; i < data.length; i++)
    { try
      { System.arraycopy( data[i], 0, tmpData[i], 0, data[i].length) ;
      }
      catch( Exception excep)
      { System.out.println( excep.getMessage());
      }
    }
    data = tmpData ;
  }

  public void swapRow( int [] indexTab)
  { boolean shouldSwap = false ;
    for( int i = 0; i < indexTab.length; i++)
    { if( i != indexTab[i])
      { shouldSwap = true ;
        break ;
      }
    }
    if( !shouldSwap)
      return ;
    //

    Object [][] tmpData = new Object[rowSize][colSize] ;
    for( int i = 0; i < indexTab.length; i++)
    { tmpData[i] = data[indexTab[i]] ;
    }
    data = tmpData ;
  }

  //////////////////////////////////////////////////////////////////////////////
  //
  //////////////////////////////////////////////////////////////////////////////
  private void writeObject(ObjectOutputStream s)
    throws IOException
  {
    if( maxRow < rowSize || maxCol < colSize)
    { rowSize = maxRow ;
      colSize = maxCol ;

      Object [][] tmpData = new Object[maxRow][maxCol] ;
      for( int i = 0; i < maxRow; i++)
      { for( int j = 0; j < maxCol; j++)
        { tmpData[i][j]= data[i][j] ;
        }
      }
      data = tmpData ;
    }

    s.defaultWriteObject() ;
  }
  private void readObject(ObjectInputStream s)
    throws IOException, ClassNotFoundException
  { s.defaultReadObject() ;
    //System.out.println( "rowSize" + this.rowSize) ;
    //System.out.println( "colSize" + this.colSize) ;
  }



}