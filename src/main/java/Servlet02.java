

import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bc.sql.mysql.MySqlContext;

import boodschap.Product;
import boodschap.ProductTable;



@WebServlet("/hallo")
public class Servlet02 extends HttpServlet
{	MySqlContext sqlContext = null ;
	
	public void init( ServletConfig config) throws ServletException
  { super.init( config) ;
    System.out.println( "*** init ***") ;
    ServletContext context = getServletContext() ;
    System.out.println( context.getServerInfo()) ;
    System.out.println(context.getRealPath( "")) ;
    System.out.println( "*** end init ***") ;
    try
    { 
    	String hostAndPort = System.getenv( "OPENSHIFT_MYSQL_DB_HOST") + ":" +
    			System.getenv( "OPENSHIFT_MYSQL_DB_PORT") ;
    	String mysqlUser = System.getenv( "OPENSHIFT_MYSQL_DB_USERNAME") ;
    	String mysqlPassword = System.getenv( "OPENSHIFT_MYSQL_DB_PASSWORD") ;
    	sqlContext = new MySqlContext( hostAndPort, "Boodschap", mysqlUser, mysqlPassword) ;
    	
    	// Servlet lokaal + mysql via port forward op openshift
    	//sqlContext = new MySqlContext( "localhost", "Boodschap", "adminhCHTwGt", "v6ITYrv2bjN2") ;
    	
    	// Servlet lokaal + mysql lokaal
    	//sqlContext = new MySqlContext( "gc02", "Boodschap", "root", "19570219") ;
    	
    	
      //sqlContext.connect() ;
      System.out.println( "Servlet init uitgevoerd") ;
    }
    catch( Exception e)
    { System.out.println( e.getMessage()) ;
    	cleanUp() ;
      System.out.println( "Kan de database niet openen") ;
    }
  }
	
	public void cleanUp()
  { try
    { System.out.println( "De verbinding met de database wordt verbroken") ;

    }
    catch( Exception e)
    {
    }
  }

	public void doPost( HttpServletRequest req, HttpServletResponse res)
			throws java.io.IOException
	{

		Enumeration headers = req.getHeaderNames();
		String cartXml = "";

		while (headers.hasMoreElements())
		{
			String header = (String) headers.nextElement();
			//System.out.println( header + ": " + req.getHeader( header));
		}

		// Onderstaande moet tot de header toegevoegd worden
		// teneinde cross origin toegang toe te laten voor de client
		res.setHeader( "Access-Control-Allow-Origin", "*");

		String action = req.getParameter( "action");

		if( (action != null) && action.equalsIgnoreCase( "LaadLijst"))
		{
			ProductTable pTab = new ProductTable() ;
			try
			{
				sqlContext.fetch( pTab);
			}
			catch( Exception e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			System.out.println( "geladen : " + pTab.getSize());
			
			cartXml = pTab.getAsXml() ;
			
		}
		else if( (action != null) && action.equalsIgnoreCase( "LaadSoorten"))
		{
			ProductTable pTab = new ProductTable() ;
			try
			{
				sqlContext.fetch( pTab);
			}
			catch( Exception e1)
			{
				e1.printStackTrace();
			}
			
			System.out.println( "geladen : " + pTab.getSize());
			
			cartXml = pTab.getSoortenAsXml() ;
			
		}
		else if( (action != null) && action.equalsIgnoreCase( "geef aantal"))
		{
			String code = req.getParameter( "code");
						
			Product where = new Product() ;
			where.setId( Integer.parseInt(code ));
			
			ProductTable pt = new ProductTable() ;
			try
			{
				this.sqlContext.fetch( pt, where);
				System.out.println( "aantal = " + pt.getSize()) ;
			}
			catch( Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Product p = pt.get(0) ;
			
			System.out.println( "aantal voor " + p.getProduct() + " " + p.getAantal());
			
			cartXml = p.getAsXml() ;
		}
		else if( (action != null) && action.equalsIgnoreCase( "bewaar"))
		{
			String code = req.getParameter( "code");
			String aantal = req.getParameter( "aantal");
			
			Product where = new Product() ;
			where.setId( Integer.parseInt(code ));
			
			ProductTable pt = new ProductTable() ;
			try
			{
				this.sqlContext.fetch( pt, where);
				System.out.println( "aantal = " + pt.getSize()) ;
			}
			catch( Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Product p = pt.get(0) ;
			p.setAantal( aantal);
			p.setGekocht( false);
			
			try
			{
				this.sqlContext.update( p);
			}
			catch( Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			ProductTable pTab = new ProductTable() ;
			try
			{
				sqlContext.fetch( pTab);
			}
			catch( Exception e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			System.out.println( "geladen : " + pTab.getSize());
			
			cartXml = pTab.getAsXml() ;

			
		}
		
		else if( (action != null) && action.equalsIgnoreCase( "markeerGekocht"))
		{
			String code = req.getParameter( "code");
			
			
			Product where = new Product() ;
			where.setId( Integer.parseInt(code ));
			
			ProductTable pt = new ProductTable() ;
			try
			{
				this.sqlContext.fetch( pt, where);
			}
			catch( Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Product p = pt.get(0) ;
			p.setGekocht( !p.getGekocht());
			
			try
			{
				this.sqlContext.update( p);
			}
			catch( Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			ProductTable pTab = new ProductTable() ;
			try
			{
				sqlContext.fetch( pTab);
			}
			catch( Exception e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			System.out.println( "geladen : " + pTab.getSize());
			
			cartXml = pTab.getAsXml() ;

			
		}
		
		//
		res.setContentType( "text/xml");
		res.getWriter().write( cartXml);
	}

	public void doGet( HttpServletRequest req, HttpServletResponse res)
			throws java.io.IOException
	{
		// Bounce to post, for debugging use
		// Hit this servlet directly from the browser to see XML
		doPost( req, res);
	}

}
