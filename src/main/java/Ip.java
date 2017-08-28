import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




/**
 * Servlet implementation class Ip
 */
@WebServlet("/Ip")
public class Ip extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	String ip = "a.bet.c.d" ;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ip() 
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		doPost( request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		Enumeration headers = request.getHeaderNames();
	    
	    while (headers.hasMoreElements()) 
	    {
	      String key  = (String)headers.nextElement();
	      String value = request.getHeader( key);
	      //System.out.println( key + " = " + value);
	    }
	    
	    // Indien een "ip" parameter/value ontvangen wordt
	    // wordt het ip adres bijgehouden in de lokale variabele
	    String newIp = request.getParameter("ip");
	    if (( newIp != null)) 
	    {	System.out.println( "New ip" + " = " + newIp);
	    	ip = newIp ;
	    }
	    
		// RESPONSE
		response.setContentType("text/html");
		response.getOutputStream().print( ip);
	}

}
