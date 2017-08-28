package com.bc.servlet.otp3;

import java.io.* ;
import java.util.* ;
import java.math.*;

public class OtpRequest implements Serializable
{ static final long serialVersionUID = 195710000L ;
  // Versie ///////////////////////////////////
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;
  /////////////////////////////////////////////

  private String methodName = null ;
  private Vector methodKeys = new Vector() ;
  private Vector methodValues = new Vector() ;
  private transient Hashtable hashtab = new Hashtable() ;

  public OtpRequest()
  {
  }

  public void setMethodName( String arg)
  { methodName = arg ;
  }

  public void addMethodArgs( Hashtable arg)
  { Enumeration enum1 = arg.keys() ;
    while( enum1.hasMoreElements())
    { String key = (String)enum1.nextElement() ;
      Object value = arg.get( key) ;
      methodKeys.addElement( key) ;
      methodValues.addElement( value) ;
    }
  }

  public void addMethodArg( Object arg)
  { addDummyKey() ;
    methodValues.add( arg) ;
  }
  public void addMethodArg( String key, Object arg)
  { methodKeys.add( key) ;
    methodValues.add( arg) ;
  }

  public void addMethodArg( char arg)
  { addDummyKey() ;
    methodValues.add( new Character(arg)) ;
  }
  public void addMethodArg( String key, char arg)
  { methodKeys.add( key) ;
    methodValues.add( new Character(arg)) ;
  }

  public void addMethodArg( short arg)
  { addDummyKey() ;
    methodValues.add( new Short(arg)) ;
  }
  public void addMethodArg( String key, short arg)
  { methodKeys.add( key) ;
    methodValues.add( new Short(arg)) ;
  }

  public void addMethodArg( int arg)
  { addDummyKey() ;
    methodValues.add( new Integer(arg)) ;
  }
  public void addMethodArg( String key, int arg)
  { methodKeys.add( key) ;
    methodValues.add( new Integer(arg)) ;
  }

  public void addMethodArg( long arg)
  { addDummyKey() ;
    methodValues.add( new Long(arg)) ;
  }
  public void addMethodArg( String key, long arg)
  { methodKeys.add( key) ;
    methodValues.add( new Long(arg)) ;
  }

  public void addMethodArg( float arg)
  { addDummyKey() ;
    methodValues.add( new Float(arg)) ;
  }
  public void addMethodArg( String key, float arg)
  { methodKeys.add( key) ;
    methodValues.add( new Float(arg)) ;
  }

  public void addMethodArg( double arg)
  { addDummyKey() ;
    methodValues.add( new Double(arg)) ;
  }
  public void addMethodArg( String key, double arg)
  { methodKeys.add( key) ;
    methodValues.add( new Double(arg)) ;
  }

  public void addMethodArg( boolean arg)
  { addDummyKey() ;
    methodValues.add( new Boolean(arg)) ;
  }
  public void addMethodArg( String key, boolean arg)
  { methodKeys.add( key) ;
    methodValues.add( new Boolean(arg)) ;
  }

  public Object getObject( int index)
    throws Exception
  { checkOutOfBounds( index) ;
    return this.methodValues.elementAt( index) ;
  }

  public Object getObject( String key)
    throws Exception
  { return this.hashtab.get( key) ;
  }

  public char getChar( int index)
    throws Exception
  { checkOutOfBounds( index) ;
    try
    { return ((Character)this.methodValues.elementAt( index)).charValue() ;
    }
    catch( Exception excep)
    { throw this.throwCastException( index, excep) ;
    }
  }

  public boolean getBoolean( int index)
    throws Exception
  { checkOutOfBounds( index) ;
    try
    { return ((Boolean)this.methodValues.elementAt( index)).booleanValue() ;
    }
    catch( Exception excep)
    { throw this.throwCastException( index, excep) ;
    }
  }

  public BigDecimal getBigDecimal( int index)
    throws Exception
  { checkOutOfBounds( index) ;
    try
    { return (BigDecimal)this.methodValues.elementAt( index) ;
    }
    catch( Exception excep)
    { throw this.throwCastException( index, excep) ;
    }
  }

  public int getInt( int index)
    throws Exception
  { checkOutOfBounds( index) ;
    try
    { return ((Integer)this.methodValues.elementAt( index)).intValue() ;
    }
    catch( Exception excep)
    { throw this.throwCastException( index, excep) ;
    }
  }

  public long getLong( int index)
    throws Exception
  { checkOutOfBounds( index) ;
    try
    { return ((Long)this.methodValues.elementAt( index)).longValue() ;
    }
    catch( Exception excep)
    { throw this.throwCastException( index, excep) ;
    }
  }

  public float getFloat( int index)
    throws Exception
  { checkOutOfBounds( index) ;
    try
    { return ((Float)this.methodValues.elementAt( index)).floatValue() ;
    }
    catch( Exception excep)
    { throw this.throwCastException( index, excep) ;
    }
  }

  public double getDouble( int index)
    throws Exception
  { checkOutOfBounds( index) ;
    try
    { return ((Double)this.methodValues.elementAt( index)).doubleValue() ;
    }
    catch( Exception excep)
    { throw this.throwCastException( index, excep) ;
    }
  }

  public String getString( int index)
    throws Exception
  { checkOutOfBounds( index) ;
    try
    { return (String)this.methodValues.elementAt( index) ;
    }
    catch( Exception excep)
    { throw this.throwCastException( index, excep) ;
    }
  }

  public Date getDate( int index)
    throws Exception
  { checkOutOfBounds( index) ;
    try
    { return (Date)this.methodValues.elementAt( index) ;
    }
    catch( Exception excep)
    { throw this.throwCastException( index, excep) ;
    }
  }

  public Vector getVector( int index)
    throws Exception
  { checkOutOfBounds( index) ;
    try
    { return (Vector)this.methodValues.elementAt( index) ;
    }
    catch( Exception excep)
    { throw this.throwCastException( index, excep) ;
    }
  }

  public Hashtable getHashtable( int index)
    throws Exception
  { checkOutOfBounds( index) ;
    try
    { return (Hashtable)this.methodValues.elementAt( index) ;
    }
    catch( Exception excep)
    { throw this.throwCastException( index, excep) ;
    }
  }

  //////////////////////////////////////////////////////////////////////////////

  public int getInt( String key)
    throws Exception
  { checkKey( key) ;
    try
    { return ((Integer)this.hashtab.get( key)).intValue() ;
    }
    catch( Exception excep)
    { throw this.throwCastException( key, excep) ;
    }
  }

  public String getString( String key)
    throws Exception
  { checkKey( key) ;
    try
    { return (String)this.hashtab.get( key) ;
    }
    catch( Exception excep)
    { throw this.throwCastException( key, excep) ;
    }
  }

  public boolean getBoolean( String key)
    throws Exception
  { checkKey( key) ;
    try
    { return ((Boolean)this.hashtab.get( key)).booleanValue() ;
    }
    catch( Exception excep)
    { throw this.throwCastException( key, excep) ;
    }
  }

  public Hashtable getHashtable( String key)
    throws Exception
  { checkKey( key) ;
    try
    { return (Hashtable)this.hashtab.get( key) ;
    }
    catch( Exception excep)
    { throw this.throwCastException( key, excep) ;
    }
  }













  /////////////////////////////////////////////////////////////////////////////
  private void checkKey( String key)
    throws Exception
  { if( ! this.hashtab.containsKey( key))
    { throw new Exception(
        "Server RMC exception.\n"
        + "Key = " + key + " not found") ;
    }
  }

  private void checkOutOfBounds( int index)
    throws Exception
  { if( index >= this.methodValues.size())
    { throw new Exception(
        "Server RMC out of bounds exception.\n"
        + "Index " + index + " >= the size of the data container, which is " + this.methodValues.size());
    }
  }

  private Exception throwCastException( int index, Exception excep)
  { return new Exception(
      "Server RMC cast exception.\n"
      + "Class of Arg at index " + index + " (key = "
      + this.methodKeys.elementAt(index) + ") is "
      + this.methodValues.elementAt( index).getClass().toString() + "\n"
      + "message = " + excep.getMessage()) ;
  }

  private Exception throwCastException( String key, Exception excep)
  { return new Exception(
      "Server RMC cast exception.\n"
      + "Class of Arg (key = " + key + ") = "
      + this.hashtab.get( key).getClass().toString() + "\n"
      + "message = " + excep.getMessage()) ;
  }


  private void addDummyKey()
  { methodKeys.add( "" + methodKeys.size()) ;
  }



  public String getMethodName()
  { return methodName ;
  }

  public Object[] getMethodArgs()
  { return this.getArgValues() ;
  }
  public Object[] getArgValues()
  { Object obj[] = new Object[methodValues.size()] ;
    for( int i = 0; i < methodValues.size(); i++)
      obj[i] = methodValues.elementAt(i) ;
    return obj ;
  }

  public Hashtable getArgs()
  { return this.hashtab ;
  }


  private void readObject(ObjectInputStream s)
    throws IOException, ClassNotFoundException
  { s.defaultReadObject() ;
    if( this.hashtab == null)
    { this.hashtab = new Hashtable() ;
      for( int i = 0; i < this.methodValues.size(); i++)
      { if( this.methodValues.elementAt(i) != null)
        { hashtab.put( this.methodKeys.elementAt(i), this.methodValues.elementAt(i)) ;
        }
      }
    }
  }
}