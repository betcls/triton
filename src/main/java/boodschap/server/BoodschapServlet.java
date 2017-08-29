package boodschap.server;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bc.sql.mysql.MySqlContext;

import boodschap.db.Product;
import boodschap.db.ProductTable;

@WebServlet("/Boodschap")
public class BoodschapServlet extends HttpServlet {
	MySqlContext sqlContext = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("*** init ***");
		ServletContext context = getServletContext();
		System.out.println(context.getServerInfo());
		System.out.println(context.getRealPath(""));
		System.out.println("*** end init ***");
		try { /*
				 * De bedoeling is het zaakje te draaien op OpenShift, maar ook
				 * af en toe lokaal in Tomcat 7. Daarom volgende aanpak. We
				 * maken gebruik van de omgevingsvariabele van OpenShift. Indien
				 * het resultaat null oplevert dan gaan we ervan uit dat we
				 * lokaal werken.
				 */

			String host = System.getenv("MYSQL_SERVICE_HOST");
			String port = System.getenv("MYSQL_SERVICE_PORT");
			String mysqlUser = System.getenv("MYSQL_USER");
			String mysqlPassword = System.getenv("MYSQL_PASSWORD");

			if (host != null || port != null) {
				sqlContext = new MySqlContext(host + ":" + port, "Boodschap", mysqlUser, mysqlPassword);
			} else if (false) 
			{ // Lokale MySql server aanspreken
				sqlContext = new MySqlContext("localhost", "Boodschap", "root", "19570219");
			} else { // Servlet lokaal + mysql via port forward op openshift
				sqlContext = new MySqlContext("mysql", "Boodschap", "root", "LJq3O4wjrMCbA6bd");
			}

			System.out.println("MySql connecteren ...");
			//sqlContext.connect();
			System.out.println("Servlet init uitgevoerd");
			
			/* Blijkbaar werkt autoreconnect=true niet. Dit hebben we
			 * opgelost door in een Tread de URL regelmatig te benaderen.
			 */
			//Wakkerhouder worker = Wakkerhouder.getInstance() ;
			//worker.start();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			cleanUp();
			System.out.println("Kan de database niet openen");
		}
	}

	public void cleanUp() {
		try 
		{
			System.out.println("De verbinding met de database wordt verbroken");
		} 
		catch (Exception e) 
		{
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {

		/*
		 *  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 *  Om toe te laten dat extended chars correct gedecodeerd kunnen worden
		 *  
		 *  Heeft uren gekost om hier achter te komen
		 */
		req.setCharacterEncoding("utf-8");
				
		Enumeration headers = req.getHeaderNames();
		String cartXml = "";
		
		while (headers.hasMoreElements()) {
			String header = (String) headers.nextElement();
			// System.out.println( header + ": " + req.getHeader( header));
		}

		// Onderstaande moet tot de header toegevoegd worden
		// teneinde cross origin toegang toe te laten voor de client
		res.setHeader("Access-Control-Allow-Origin", "*");
		String action = req.getParameter("action");
		try {
			if ((action != null) && action.equalsIgnoreCase("LaadLijst")) {
				ProductTable pTab = new ProductTable();
				// sqlContext.fetch( pTab);
				pTab = ProductTable.fetch(sqlContext, " order by " + Product.DBF_Soort + "," + Product.DBF_Product);
				System.out.println("geladen : " + pTab.getSize());
				cartXml = pTab.getAsXml();
			} else if ((action != null) && action.equalsIgnoreCase("Laad winkelenLijst")) {
				ProductTable pTab = new ProductTable();
				// sqlContext.fetch( pTab);
				pTab = ProductTable.fetch(sqlContext, " order by " + Product.DBF_Volgorde + "," + Product.DBF_Soort + "," + Product.DBF_Product);
				System.out.println("geladen : " + pTab.getSize());
				cartXml = pTab.getAsXml();
			} else if ((action != null) && action.equalsIgnoreCase("LaadSoorten")) {
				ProductTable pTab = new ProductTable();
				// sqlContext.fetch( pTab);
				pTab = ProductTable.fetch(sqlContext, " order by " + Product.DBF_Soort + "," + Product.DBF_Product);
				System.out.println("geladen : " + pTab.getSize());
				cartXml = pTab.getSoortenAsXml();
			} else if ((action != null) && action.equalsIgnoreCase("Laad soorten om te sorteren")) {
				ProductTable pTab = new ProductTable();
				// sqlContext.fetch( pTab);
				pTab = ProductTable.fetch(sqlContext, " order by " + Product.DBF_Soort + "," + Product.DBF_Product);
				System.out.println("geladen : " + pTab.getSize());
				cartXml = pTab.getSoortenToSortAsXml();
			} else if ((action != null) && action.equalsIgnoreCase("geef aantal")) {
				String code = req.getParameter("code");

				Product where = new Product();
				where.setId(Integer.parseInt(code));

				ProductTable pt = new ProductTable();
				this.sqlContext.fetch(pt, where);
				System.out.println("aantal = " + pt.getSize());

				Product p = pt.get(0);
				System.out.println("aantal voor " + p.getProduct() + " " + p.getAantal());
				cartXml = p.getAsXml();
			} else if ((action != null) && action.equalsIgnoreCase("bewaar")) {
				String code = req.getParameter("code");
				String aantal = req.getParameter("aantal");

				Product where = new Product();
				where.setId(Integer.parseInt(code));

				ProductTable pt = new ProductTable();
				// this.sqlContext.fetch( pt, where);
				pt = ProductTable.fetch(sqlContext, " where " + Product.DBF_Id + "=" + code + " order by "
						+ Product.DBF_Soort + "," + Product.DBF_Product);
				System.out.println("aantal = " + pt.getSize());

				Product p = pt.get(0);
				p.setAantal(aantal);
				p.setGekocht(false);

				this.sqlContext.update(p);

				ProductTable pTab = new ProductTable();
				// sqlContext.fetch( pTab);
				pTab = ProductTable.fetch(sqlContext, " order by " + Product.DBF_Soort + "," + Product.DBF_Product);
				System.out.println("geladen : " + pTab.getSize());

				cartXml = pTab.getAsXml();
			} 

			else if ((action != null) && action.equalsIgnoreCase("markeerGekocht")) {
				String code = req.getParameter("code");

				Product where = new Product();
				where.setId(Integer.parseInt(code));

				ProductTable pt = new ProductTable();
				this.sqlContext.fetch(pt, where);

				Product p = pt.get(0);
				p.setGekocht( !p.getGekocht());
				this.sqlContext.update(p);

				ProductTable pTab = new ProductTable();
				// sqlContext.fetch( pTab);
				pTab = ProductTable.fetch( sqlContext, " order by " + Product.DBF_Volgorde + "," + Product.DBF_Soort + "," + Product.DBF_Product);
				System.out.println("geladen : " + pTab.getSize());

				cartXml = pTab.getAsXml();
			}
			else if ((action != null) && action.equalsIgnoreCase("bewaar nieuw")) {
				String soort = req.getParameter("soort");
				String product = req.getParameter("product");

				Product prod = new Product();
				prod.setSoort(soort);
				prod.setProduct(product);
				
				prod.insert(sqlContext) ;
				
				System.out.println( "Nieuw : " + prod.getId() + "/" + prod.getSoort() + "/" + prod.getProduct());

				ProductTable pTab = new ProductTable();
				// sqlContext.fetch( pTab);
				pTab = ProductTable.fetch(sqlContext, " order by " + Product.DBF_Soort + "," + Product.DBF_Product);
				System.out.println("geladen : " + pTab.getSize());

				cartXml = pTab.getAsXml();
			
			}
			else if ((action != null) && action.equalsIgnoreCase("wis dmv code")) 
			{
				String code = req.getParameter("code");
			
				Product where = new Product();
				where.setId(Integer.parseInt(code));

				ProductTable pt = new ProductTable();
				this.sqlContext.fetch(pt, where);
				Product prod = pt.get(0);
				
				System.out.println( "Gewist : " + prod.getSoort() + "/" + prod.getProduct());

				prod.delete(sqlContext);
			
				ProductTable pTab = new ProductTable();
				
				pTab = ProductTable.fetch(sqlContext, " order by " + Product.DBF_Soort + "," + Product.DBF_Product);
				System.out.println("geladen : " + pTab.getSize());

				cartXml = pTab.getAsXml();
			}
			else if ((action != null) && action.equalsIgnoreCase("wijzig")) 
			{
				
				String code = req.getParameter("code");
				String soort = req.getParameter("soort");
				String product = req.getParameter("product");
				
				Product prod = new Product();
				prod.setId(Integer.parseInt(code));
								
				ProductTable pt = new ProductTable();
				this.sqlContext.fetch( pt, prod);
				prod = pt.get(0);
				
				System.out.println( "Wijzigen : " + prod.getId() + "/" + prod.getSoort() + "/" + prod.getProduct());
				
				prod.setSoort(soort);
				prod.setProduct(product);
				prod.update(sqlContext);
				
				System.out.println( "Gewijzigd : " + prod.getId() + "/" + prod.getSoort() + "/" + prod.getProduct());
			
				ProductTable pTab = new ProductTable();
				pTab = ProductTable.fetch(sqlContext, " order by " + Product.DBF_Soort + "," + Product.DBF_Product);
				System.out.println("geladen : " + pTab.getSize());

				cartXml = pTab.getAsXml();
			}
			else if ((action != null) && action.equalsIgnoreCase("Update volgorde")) 
			{
				String soort = req.getParameter("soort");
				String volgorde = req.getParameter( "volgorde") ;
				
				System.out.println("volgorde : " + soort + " --> " + volgorde);
			
				Product where = new Product();
				where.setSoort(soort);

				ProductTable pt = new ProductTable();
				this.sqlContext.fetch(pt, where);
				for( int i = 0; i < pt.getSize(); i++)
				{	Product prod = pt.get(i) ;
					
					prod.setVolgorde(volgorde);
					prod.update(sqlContext);

				}
				
				
				/*
				ProductTable pTab = new ProductTable();
				
				pTab = ProductTable.fetch(sqlContext, " order by " + Product.DBF_Soort + "," + Product.DBF_Product);
				System.out.println("geladen : " + pTab.getSize());

				cartXml = pTab.getAsXml();
				*/
				
				cartXml = "" ;
			}

			
			
			
		} catch (Exception excep) 
		{
			excep.printStackTrace() ;
			System.out.println( excep.getMessage());
			cartXml = getFoutXml( excep.getMessage() ) ;
		}

		//
		res.setContentType("text/xml");
		res.getWriter().write(cartXml);
	}
	
	
	private String getFoutXml(String boodschap) 
	{
		SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
		String tijdstip = ft.format(new Date());
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\"?>\n");

		xml.append("<Boodschappen generated=\"" + System.currentTimeMillis() + "\" tijdstip=\"" + tijdstip + "\">\n");

		xml.append("<fout>\n");
		xml.append(boodschap);
		xml.append("</fout>\n");

		xml.append("</Boodschappen>\n");
		return xml.toString();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
		// Bounce to post, for debugging use
		// Hit this servlet directly from the browser to see XML
		doPost(req, res);
	}

}
