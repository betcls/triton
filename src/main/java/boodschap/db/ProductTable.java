package boodschap.db;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;
import java.io.*;

//import developerworks.ajax.store.Item;

public class ProductTable extends _ProductTable implements Serializable {
	// ----------------------------------------------------
	static final long serialVersionUID = 19570010L;
	private static final short clsVersion = 1;
	private short objVersion = clsVersion;
	// ----------------------------------------------------

	public ProductTable() {
		super();
	}

	public ProductTable(int initialSize) {
		super(initialSize);
	}

	public Product get(int index) {
		return (Product) super.getObject(index);
	}

	// ADD YOUR CODE BELOW THIS LINE

	public String getAsXml() {
		SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
		String tijdstip = ft.format(new Date());

		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\"?>\n");

		xml.append("<Boodschappen generated=\"" + System.currentTimeMillis() + "\" tijdstip=\"" + tijdstip + "\">\n");

		for (int i = 0; i < this.getSize(); i++) {
			Product product = this.get(i);

			xml.append("<item>\n");
			xml.append("<id>");
			xml.append(product.getId());
			xml.append("</id>\n");
			xml.append("<volgorde>");
			xml.append(product.volgorde);
			xml.append("</volgorde>\n");
			xml.append("<soort>");
			xml.append(product.getSoort());
			xml.append("</soort>\n");
			xml.append("<naam>");
			xml.append(product.getProduct());
			xml.append("</naam>\n");
			xml.append("<aantal>");
			xml.append(product.getAantal());
			xml.append("</aantal>\n");
			xml.append("<gekocht>");
			xml.append(product.getGekocht());
			xml.append("</gekocht>\n");
			xml.append("</item>\n");
		}
		xml.append("</Boodschappen>\n");
		return xml.toString();
	}

	public String getSoortenAsXml() 
	{
		Set<String> soorten = new LinkedHashSet<String>();

		for (int i = 0; i < this.getSize(); i++) {
			Product product = this.get(i);
			if (soorten.contains(product.getSoort()))
				continue;
			soorten.add(product.getSoort());
		}

		SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
		String tijdstip = ft.format(new Date());

		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\"?>\n");

		xml.append("<Soorten generated=\"" + System.currentTimeMillis() + "\" tijdstip=\"" + tijdstip + "\">\n");

		for (Iterator<String> iter = soorten.iterator(); iter.hasNext();) {
			String soort = iter.next();
			xml.append("<item>\n");
			xml.append("<id>");
			xml.append(soort);
			xml.append("</id>\n");
			xml.append("<soort>");
			xml.append(soort);
			xml.append("</soort>\n");

			xml.append("</item>\n");
		}
		xml.append("</Soorten>\n");
		return xml.toString();
	}
	
	public String getSoortenToSortAsXml() 
	{
		ProductTable tab = new ProductTable() ;
		String soort = "" ;
		String volgorde = "-" ;
		Product prod = null ;
		
		for (int i = 0; i < this.getSize(); i++) 
		{	Product product = this.get(i);
			
			if( ! product.getSoort().equalsIgnoreCase(soort))
			{	soort = product.getSoort() ;
				volgorde = "-" ;
				prod = new Product() ;
				prod.setSoort(product.getSoort());
				prod.setVolgorde(product.getVolgorde());
				tab.addObject( prod);
			}
			else
			{
				if( ! product.getVolgorde().equalsIgnoreCase(volgorde))
				{ 	prod.setSoort(product.getSoort());
					prod.setVolgorde(product.getVolgorde());
				}
			}
			
		}

		SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
		String tijdstip = ft.format(new Date());

		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\"?>\n");

		xml.append("<SoortenSort generated=\"" + System.currentTimeMillis() + "\" tijdstip=\"" + tijdstip + "\">\n");
		
		for (int i = 0; i < tab.getSize(); i++) 
		{	prod = tab.get(i);
			xml.append("<item>\n");
			xml.append("<soort>");
			xml.append( prod.getSoort());
			xml.append("</soort>\n");
			xml.append("<volgorde>");
			xml.append( prod.getVolgorde());
			xml.append("</volgorde>\n");
			xml.append("</item>\n");
		}
		xml.append("</SoortenSort>\n");
		return xml.toString();
	}

}
