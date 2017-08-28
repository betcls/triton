package boodschap ;

import java.text.SimpleDateFormat;
import java.util.Date ;
import java.util.Hashtable ;
import java.math.BigDecimal ;
import java.io.* ;
import java.sql.* ;

public class Product extends _Product implements Serializable, Cloneable
{
	//----------------------------------------------------
	static final long serialVersionUID = 195711L ;
	private static final short clsVersion = 1 ;
	private short objVersion = clsVersion ;
	//----------------------------------------------------

	public Product()
	{
		super() ;
	}
	public Object clone()
	{
		Product clone = new Product();
		try
		{
			clone.setData( ((_Product)super.clone()).getData());
		}
		catch( Exception excep){}
		return clone ;
	}

	public Product copy()
	{
		return (Product)this.clone() ;
	}

	// ---------------------------------------------------------

	public String getAsXml()
	{
		SimpleDateFormat ft = new SimpleDateFormat( "HH:mm:ss");
		String tijdstip = ft.format( new Date());

		StringBuffer xml = new StringBuffer();
		xml.append( "<?xml version=\"1.0\"?>\n");

		xml.append( "<Product generated=\"" + System.currentTimeMillis()
				+ "\" tijdstip=\"" + tijdstip + "\">\n");

		xml.append( "<item>\n");
		xml.append( "<id>");
		xml.append( "" + id);
		xml.append( "</id>\n");
		xml.append( "<soort>");
		xml.append( soort);
		xml.append( "</soort>\n");
		xml.append( "<naam>");
		xml.append( product);
		xml.append( "</naam>\n");
		xml.append( "<aantal>");
		xml.append( aantal);
		xml.append( "</aantal>\n");
		xml.append( "</item>\n");

		xml.append( "</Product>\n");
		return xml.toString();
	}

}
