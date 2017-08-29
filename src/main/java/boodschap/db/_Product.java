package boodschap.db ;

import java.util.Date ;
import java.util.Vector ;
import java.util.Hashtable ;
import java.math.BigDecimal ;
import java.io.* ;
import java.sql.* ;
import com.bc.sql.* ;

public abstract class _Product implements Serializable, Cloneable, SqlRecord
{
  //----------------------------------------------------
  static final long serialVersionUID = 19571L ;
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;
  //----------------------------------------------------

  public final static String DBF_Id = "Product.Id" ;
  public final static String DBF_Volgorde = "Product.Volgorde" ;
  public final static String DBF_Soort = "Product.Soort" ;
  public final static String DBF_Product = "Product.Product" ;
  public final static String DBF_Aantal = "Product.Aantal" ;
  public final static String DBF_Gekocht = "Product.Gekocht" ;

  public final static String [] columnNames = new String[]
    {
      DBF_Id,
      DBF_Volgorde,
      DBF_Soort,
      DBF_Product,
      DBF_Aantal,
      DBF_Gekocht
    };

  public final static SqlDef SQL_TABLE_DEF = new SqlDef();
  static
  {
    SQL_TABLE_DEF.setName("Product");
    SQL_TABLE_DEF.setQuery("SELECT Product.Id, Product.Volgorde, Product.Soort, Product.Product, Product.Aantal, Product.Gekocht FROM Product");
    SQL_TABLE_DEF.setType(1);
    SqlColumnList cols = new SqlColumnList(6);
    SqlColumn col = null;
    col = new SqlColumn(1,4,"Product.Id","java.lang.Integer",false,true,true,true,true);
    cols.addObject( col);
    col = new SqlColumn(2,12,"Product.Volgorde","java.lang.String",false,false,false,false,false);
    cols.addObject( col);
    col = new SqlColumn(3,12,"Product.Soort","java.lang.String",false,true,false,true,false);
    cols.addObject( col);
    col = new SqlColumn(4,12,"Product.Product","java.lang.String",false,true,false,true,false);
    cols.addObject( col);
    col = new SqlColumn(5,12,"Product.Aantal","java.lang.String",false,false,false,false,false);
    cols.addObject( col);
    col = new SqlColumn(6,-7,"Product.Gekocht","java.lang.Boolean",true,false,false,false,false);
    cols.addObject( col);
    SQL_TABLE_DEF.setColumnList( cols);
  }
  protected java.lang.Integer id = null;
  protected java.lang.String volgorde = null;
  protected java.lang.String soort = null;
  protected java.lang.String product = null;
  protected java.lang.String aantal = null;
  protected java.lang.Boolean gekocht = null;
  public _Product()
  {
    super() ;
  }


  public void setId(java.lang.Integer id)
  {
    this.id = id;
  }
  public void setId(int id)
  {
    this.id =  new java.lang.Integer(id);
  }
  public java.lang.Integer getId()
  {
    return id;
  }

  public void setVolgorde(java.lang.String volgorde)
  {
    this.volgorde = volgorde;
  }
  public java.lang.String getVolgorde()
  {
    return volgorde;
  }

  public void setSoort(java.lang.String soort)
  {
    this.soort = soort;
  }
  public java.lang.String getSoort()
  {
    return soort;
  }

  public void setProduct(java.lang.String product)
  {
    this.product = product;
  }
  public java.lang.String getProduct()
  {
    return product;
  }

  public void setAantal(java.lang.String aantal)
  {
    this.aantal = aantal;
  }
  public java.lang.String getAantal()
  {
    return aantal;
  }

  public void setGekocht(java.lang.Boolean gekocht)
  {
    this.gekocht = gekocht;
  }
  public void setGekocht(boolean gekocht)
  {
    this.gekocht =  new java.lang.Boolean(gekocht);
  }
  public java.lang.Boolean getGekocht()
  {
    return gekocht;
  }
  public final String [] getColumnNames()
  {
    return columnNames;
  }
  public Object clone()
  {
    Product result = new Product();
    if( this.id != null) 
    {
      result.id = this.id;
    }
    if( this.volgorde != null) 
    {
      result.volgorde = this.volgorde;
    }
    if( this.soort != null) 
    {
      result.soort = this.soort;
    }
    if( this.product != null) 
    {
      result.product = this.product;
    }
    if( this.aantal != null) 
    {
      result.aantal = this.aantal;
    }
    if( this.gekocht != null) 
    {
      result.gekocht = this.gekocht;
    }
    return result;
  }

  public final SqlDef getDef()
  {
    return SQL_TABLE_DEF;
  }
  public final Object [] getData()
  {
    Object [] data = new Object[6];
    data[0] = id;
    data[1] = volgorde;
    data[2] = soort;
    data[3] = product;
    data[4] = aantal;
    data[5] = gekocht;
    return data;
  }
  public final void setData( Object [] data)
    throws Exception
  {
    try
    {
      id = (java.lang.Integer)data[0];
      volgorde = (java.lang.String)data[1];
      soort = (java.lang.String)data[2];
      product = (java.lang.String)data[3];
      aantal = (java.lang.String)data[4];
      gekocht = (java.lang.Boolean)data[5];
    }
    catch( ClassCastException excep)
    {
      throw new Exception( "Class cast exception -> " + excep.getMessage());
    }
  }
  public SqlDataPair getUniqueData()
    throws Exception
  {
    Object [] data = this.getData() ;
    for( int i = 0; i < SQL_TABLE_DEF.getColumnList().getSize(); i++)
    {
      SqlColumn col = SQL_TABLE_DEF.getColumnList().getObject(i) ;
      if( col.isPrimaryKey() && col.isUniqueIndex() && data[i] != null)
      {
        return  new SqlDataPair( col.getName(), data[i]) ;
      }
    }
    for( int i = 0; i < SQL_TABLE_DEF.getColumnList().getSize(); i++)
    {
      SqlColumn col = SQL_TABLE_DEF.getColumnList().getObject(i) ;
      if( col.isUniqueIndex() && data[i] != null)
      {
        return  new SqlDataPair( col.getName(), data[i]) ;
      }
    }
    throw new Exception( "Object doesn't contain a UNIQUE identifier") ;
  }
  public SqlDataPairList getDataPairList()
    throws Exception
  {
    SqlDataPairList result = new SqlDataPairList() ;
    Object [] data = this.getData() ;
    for( int i = 0; i < SQL_TABLE_DEF.getColumnList().getSize(); i++)
    {
      SqlColumn col = SQL_TABLE_DEF.getColumnList().getObject(i) ;
      if( data[i] != null)
      {
        result.addObject( new SqlDataPair( col.getName(), data[i])) ;
      }
    }
    return result ;
  }
  public SqlDataPairList getUpdateDataPairList()
    throws Exception
  {
    SqlDataPairList result = new SqlDataPairList() ;
    Object [] data = this.getData() ;
    for( int i = 0; i < SQL_TABLE_DEF.getColumnList().getSize(); i++)
    {
      SqlColumn col = SQL_TABLE_DEF.getColumnList().getObject(i) ;
      if( data[i] != null && ! SQL_TABLE_DEF.getColumnList().getObject(i).isAutoIncrement())
      {
        result.addObject( new SqlDataPair( col.getName(), data[i])) ;
      }
    }
    return result ;
  }
  public void print( PrintStream out)
    throws Exception
  {
    Object [] data = this.getData() ;
    for( int i = 0; i < SQL_TABLE_DEF.getColumnList().getSize(); i++)
    {
      SqlColumn col = SQL_TABLE_DEF.getColumnList().getObject(i) ;
      if( data[i] != null)
      {
        out.println(col.getName() + " = " + data[i]) ;
      }
    }
  }
  public SqlDataPairList compaire(SqlRecord item)
    throws Exception
  {
    _Product obj = (_Product)item ;
    SqlDataPairList result = new SqlDataPairList(SQL_TABLE_DEF.getColumnList().getSize());
    if( obj == null) return result ;
    if( this.id == null && obj.id == null)
    {} //nothing 
    else if( (this.id == null && obj.id != null) || (this.id != null && obj.id == null))
      result.addObject( new SqlDataPair("Product.Id", this.id));
    else if( this.id.compareTo( obj.id) != 0)
      result.addObject( new SqlDataPair("Product.Id", this.id));
    if( this.volgorde == null && obj.volgorde == null)
    {} //nothing 
    else if( (this.volgorde == null && obj.volgorde != null) || (this.volgorde != null && obj.volgorde == null))
      result.addObject( new SqlDataPair("Product.Volgorde", this.volgorde));
    else if( this.volgorde.compareTo( obj.volgorde) != 0)
      result.addObject( new SqlDataPair("Product.Volgorde", this.volgorde));
    if( this.soort == null && obj.soort == null)
    {} //nothing 
    else if( (this.soort == null && obj.soort != null) || (this.soort != null && obj.soort == null))
      result.addObject( new SqlDataPair("Product.Soort", this.soort));
    else if( this.soort.compareTo( obj.soort) != 0)
      result.addObject( new SqlDataPair("Product.Soort", this.soort));
    if( this.product == null && obj.product == null)
    {} //nothing 
    else if( (this.product == null && obj.product != null) || (this.product != null && obj.product == null))
      result.addObject( new SqlDataPair("Product.Product", this.product));
    else if( this.product.compareTo( obj.product) != 0)
      result.addObject( new SqlDataPair("Product.Product", this.product));
    if( this.aantal == null && obj.aantal == null)
    {} //nothing 
    else if( (this.aantal == null && obj.aantal != null) || (this.aantal != null && obj.aantal == null))
      result.addObject( new SqlDataPair("Product.Aantal", this.aantal));
    else if( this.aantal.compareTo( obj.aantal) != 0)
      result.addObject( new SqlDataPair("Product.Aantal", this.aantal));
    if( this.gekocht == null && obj.gekocht == null)
    {} //nothing 
    else if( (this.gekocht == null && obj.gekocht != null) || (this.gekocht != null && obj.gekocht == null))
      result.addObject( new SqlDataPair("Product.Gekocht", this.gekocht));
    else if( this.gekocht.booleanValue() != obj.gekocht.booleanValue())
      result.addObject( new SqlDataPair("Product.Gekocht", this.gekocht));
    return result;
  }

  public void insert( SqlContext context)
    throws Exception
  {
    context.insert( this);
  }
  public void insert( SqlContext context, boolean update)
    throws Exception
  {
    context.insert( this, update);
  }
  public void update( SqlContext context)
    throws Exception
  {
    context.update( this);
  }
  public void update( SqlContext context, _Product originalObject)
    throws Exception
  {
    context.update( this, originalObject);
  }
  public void delete( SqlContext context)
    throws Exception
  {
    context.delete( this);
  }
}
