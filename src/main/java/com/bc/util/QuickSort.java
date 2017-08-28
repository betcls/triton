package com.bc.util ;

public class QuickSort
{ Compare compare = null ;

  public void sort( Object [] rgo)
  { sort( rgo, 0, rgo.length - 1) ;
  }

  // new BC
  public void sort( Object [] rgo, Compare c)
  { compare = c ;
    sortWithCompare( rgo, 0, rgo.length - 1) ;
  }

  private void sort( Object [] rgo, int nLow0, int nHigh0)
  { int nLow = nLow0;
    int nHigh = nHigh0;

    Object oMid ;

    if( nHigh0 > nLow0)
    { oMid = rgo[ (nLow0 + nHigh0) / 2 ];
      while( nLow <= nHigh)
      { while( (nLow < nHigh0) && lessThan( rgo[nLow], oMid))
          ++nLow;

        while((nLow0 < nHigh) && lessThan( oMid, rgo[nHigh]))
          --nHigh;

        if(nLow <= nHigh)
        { swap( rgo, nLow++, nHigh--);
        }
      }

      if(nLow0 < nHigh)
        sort(rgo, nLow0, nHigh);

      if(nLow < nHigh0)
        sort(rgo, nLow, nHigh0);
    }
  }

  private void sortWithCompare( Object [] rgo, int nLow0, int nHigh0)
  { int nLow = nLow0;
    int nHigh = nHigh0;

    Object oMid ;

    if( nHigh0 > nLow0)
    { oMid = rgo[ (nLow0 + nHigh0) / 2 ];
      while( nLow <= nHigh)
      { while( (nLow < nHigh0) && compare.lessThan( rgo[nLow], oMid))
          ++nLow;

        while((nLow0 < nHigh) && compare.lessThan( oMid, rgo[nHigh]))
          --nHigh;

        if(nLow <= nHigh)
        { swap( rgo, nLow++, nHigh--);
        }
      }

      if(nLow0 < nHigh)
        sortWithCompare(rgo, nLow0, nHigh);

      if(nLow < nHigh0)
        sortWithCompare(rgo, nLow, nHigh0);
    }
  }


  private void swap(Object [] rgo, int i, int j)
  { Object o;

    o = rgo[i];
    rgo[i] = rgo[j];
    rgo[j] = o;
  }

  protected boolean lessThan( Object oFirst, Object oSecond)
  { return oFirst.hashCode() < oSecond.hashCode();
  }
}
