package boodschap.db ;

import java.util.Vector ;
import java.io.* ;
import com.bc.sql.* ;

public abstract class _ProductTable implements Serializable, SqlTable
{
  //----------------------------------------------------
  static final long serialVersionUID = 19570001L ;
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;
  //----------------------------------------------------

  protected Vector data = null ;

  public _ProductTable()
  {
    data = new Vector() ;
  }
  public _ProductTable( int initialSize )
  {
    data = new Vector( initialSize) ;
  }

  public final SqlDef getDef()
  {
    return _Product.SQL_TABLE_DEF ;
  }
  public void addObject( _Product arg) 
  {
    this.data.addElement( arg) ;
  }
  public void addObject( _ProductTable tab) 
  {
    if( tab == null) return ;
    for( int i = 0; i < tab.getSize(); i++)
    {
      this.data.addElement( tab.getObject(i)) ;
    }
  }
  public void removeAllObjects() 
  {
    this.data.removeAllElements() ;
  }
  public Product getObject( int index)
  {
    if( index >= data.size()) return null ;
    return (Product)data.elementAt( index) ;
  }
  public int getSize()
  {
    return data.size() ;
  }
  public void addData( Vector data)
    throws Exception
  {
    for( int i = 0; i < data.size(); i++)
    {
      Product obj = new Product() ;
      obj.setData( (Object [])(data.elementAt(i)));
      this.addObject( obj);
    }
  }
  public void print( PrintStream out)
    throws Exception
  {
    for( int i = 0; i < getSize(); i++)
    {
      out.println("--------------------------------------");
      getObject(i).print( out) ;
    }
  }
  public static ProductTable fetch( SqlContext context)
    throws Exception
  {
    ProductTable result = new ProductTable();
    context.fetch( result);
    return result;
  }
  public static ProductTable fetch( SqlContext context, String queryClause)
    throws Exception
  {
    ProductTable result = new ProductTable();
    context.fetch( result, queryClause);
    return result;
  }
  public static ProductTable fetch( SqlContext context, Product queryObject)
    throws Exception
  {
    ProductTable result = new ProductTable();
    context.fetch( result, queryObject);
    return result;
  }
  public static ProductTable fetchLike( SqlContext context, Product queryObject)
    throws Exception
  {
    ProductTable result = new ProductTable();
    context.fetchLike( result, queryObject);
    return result;
  }
}
