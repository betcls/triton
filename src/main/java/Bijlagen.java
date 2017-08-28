import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.swing.text.html.HTMLDocument.Iterator;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;

import com.sendgrid.SendGrid;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;


@WebServlet("/Vennootschap/AV/2016/Oproeping/Bijlagen")
public class Bijlagen extends HttpServlet
{	Data data = new Data() ;
	
		
	class MatrixContextSession implements HttpSessionBindingListener
  { ServletContext context = null ;

    public MatrixContextSession( ServletContext context)
    { this.context = context ;
    }

    public void valueBound( HttpSessionBindingEvent event)
    { System.out.println( "bound : " + event.getSession().getId());
    }

    public void valueUnbound( HttpSessionBindingEvent event)
    { String httpSessionId = event.getSession().getId() ;
      System.out.println( "unbound : " + event.getSession().getId());
    }
  }

	
	
	
	
	public void doGet( HttpServletRequest request, HttpServletResponse res)
			throws java.io.IOException
	{	doPost( request, res);
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response)
			throws java.io.IOException
	{
		Aandeelhouder sessionAandeelhouder = (Aandeelhouder) request.getSession().getValue( "aandeelhouder") ;
		Aandeelhouder aandeelhouder = null ;
		if( sessionAandeelhouder != null)
		{
			aandeelhouder = (Aandeelhouder)data.getAandeelhouders().getByCode( sessionAandeelhouder.getCode()) ;
		}
		
		response.setHeader( "Cache-Control", "no-cache");
		response.setHeader( "Cache-Control", "no-store");
		response.setHeader( "Pragma", "no-cache");
		
		if( true) // niet nodig
		{	System.out.println( "---------------------------------------------------") ;
			Enumeration enu = request.getParameterNames();
			for (; enu.hasMoreElements();)
			{ // Get the name of the request parameter
				String param = (String) enu.nextElement();
				System.out.println( param);
				// Get the value of the request parameter
				String value = request.getParameter( param);
				// If the request parameter can appear more than once in the query
				// string,
				// get all values
				String[] values = request.getParameterValues( param);
				for (int i = 0; i < values.length; i++)
				{
					System.out.println( "    " + values[i]);
				}
			}
		}
		
		
		String bevestiging = request.getParameter( "bevestiging") ;
		String download = request.getParameter( "download") ;

		// De gebruiker bevestigt dat zijn identiteit correct is
		if( aandeelhouder != null && bevestiging != null)
		{
			System.out.println( bevestiging) ;
			if( bevestiging == null || bevestiging.equalsIgnoreCase( "neen"))
			{	response.sendRedirect( "http://www.google.be");
			}
			else if( bevestiging.equalsIgnoreCase( "ja"))
			{	
				Event event = new Event() ;
				event.setTijd( new Date());
				String content = 
						"De webapp. gebruiker heeft formeel bevestigd dat zijn identiteit " +
						"overeenstemt met de door hem gebruikte unieke toegangscode \"" + aandeelhouder.getCode() + 
						"\" en hij/zij dus inderdaad \"" + aandeelhouder.getNaam() + "\" is."	;
				content += "Uitgevoerd op " + request.getHeader( "X-CLIENT-IP");
				event.setContent( content);
				aandeelhouder.getEventTab().add( event) ;
				try
				{
					data.save() ;
				}
				catch( Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Toon de download pagina
				this.downloadPage( response) ;
			}
			
			
		}
		// 
		else if( aandeelhouder != null && download != null)
		{
			Document doc = null ;
			System.out.println( download);
			try
			{	
				if( download.equalsIgnoreCase( "cancel"))
				{	response.sendRedirect( "http://www.google.be");
				}
				else if( download.startsWith( "1."))
				{	doc = aandeelhouder.getDocumentTab().getByCode( "1.") ;
					
				}
				else if( download.startsWith( "2."))
				{	doc = aandeelhouder.getDocumentTab().getByCode( "2.") ;
					
				}
				else if( download.startsWith( "3."))
				{	doc = aandeelhouder.getDocumentTab().getByCode( "3.") ;
					
				}
				else if( download.startsWith( "4."))
				{	doc = aandeelhouder.getDocumentTab().getByCode( "4.") ;
					
				}
				else if( download.startsWith( "5."))
				{	doc = aandeelhouder.getDocumentTab().getByCode( "5.") ;
					
				}
				
				if( doc != null)
				{	if( doc.getAantalPogingen() >= 3)
					{		
						Event event = new Event() ;
						event.setTijd( new Date()) ;
						String content = "Document " + doc.getBronNaam() + " werd niet afgeladen wegens aantal pogingen >= " + 
								doc.getAantalPogingen() + ". Laatst afgeladen op " + doc.getTijdstip() + " als " +
								doc.getDoelNaam() + " op " + doc.getBestemming() ;
						event.setContent(  content);
						aandeelhouder.getEventTab().add( event);
						data.save() ;
						this.sendMail( aandeelhouder, "Download", content);
						this.showDownloadRestriction(response, content);
						//Thread.sleep(3000);
						//this.downloadPage( response) ;
					}
					else
					{
						this.download( response, aandeelhouder, doc) ;
						doc.addAantalPogingen() ;
						doc.setTijdstip( new Date()) ;
						doc.setBestemming( request.getHeader( "X-CLIENT-IP"));
						Event event = new Event() ;
						event.setTijd( new Date()) ;
						String content = "Document " + doc.getBronNaam() + " werd afgeladen op " +
								doc.getTijdstip() + " als " + 
								doc.getDoelNaam() + " op " +  doc.getBestemming() ;
						event.setContent(  content);
						aandeelhouder.getEventTab().add( event);
						data.save() ;
						
						this.sendMail( aandeelhouder, "download", content);
					}
				}
			}
			catch( Exception e)
			{	e.printStackTrace();
			}
		}
		
		else if( this.hasParameter( request, "toegangscode"))
		{	try {
			data.read();
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			String pValue = request.getParameter( "toegangscode").trim() ;
			aandeelhouder = data.getAandeelhouders().getByCode(  pValue) ;
			if( pValue == null) // maagdelijk
			{	showLogin( response);
			}
			else if( aandeelhouder != null)
			{
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval( 120); // sec
				session.putValue( "toegangscode", pValue);
				session.putValue( "aandeelhouder", aandeelhouder);
				
				
				String hostName = request.getRemoteHost();
				String remoteUser = request.getRemoteUser();
				String ipAddress = request.getHeader( "X-FORWARDED-FOR");
				System.out.println( "X-FORWARDED-FOR = " + ipAddress);
				ipAddress = request.getHeader( "X-CLIENT-IP");
				System.out.println( "X-CLIENT-IP = " + ipAddress);
				
				if( ipAddress == null)
				{
					ipAddress = request.getRemoteAddr();
				}
				
				//
				this.bevestigAandeelhouder( response, aandeelhouder.getNaam()) ;
			}
			else // foute code
			{
				System.out.println( "code = " + pValue);
				//showLogin( response);
				showFouteLoginBoodschap( response, pValue) ;
			}
		}
		else
		{
			showLogin( response);
		}
		
		
	}
	
	public boolean hasParameter(HttpServletRequest request, String paramName)
	{
		Enumeration enu = request.getParameterNames();
		for (; enu.hasMoreElements();)
		{ // Get the name of the request parameter
			String param = (String) enu.nextElement();
			if( param.equalsIgnoreCase( paramName)) 
				return true ;
			
		}
		return false ;
	}
	

	public void showLogin( HttpServletResponse response)
			throws java.io.IOException
	{
		response.setContentType( "text/html");
		PrintWriter out = new PrintWriter( response.getOutputStream());

		out.println( "<html>");
		out.println( "<head><title>" + "AV2016-Bijlagen" + "</title></head>");
		out.println( "<head>");

		out.println( "<meta http-equiv=\"Content-Type\""
				+ "content=\"text/html; charset=ISO-8859-1\">" 
				+ "<style type=\"text/css\">" + ".auto-style1 {"
				+ "text-align: center;}" +

				"</style>");

		out.println( "</head>");
		out.println( "<body>");

		out.println( "<table style=\"width: 100%\">");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td> <form action=\"Bijlagen\" method=\"post\" class=\"auto-style1\">"
				+ "Persoonlijke toegangscode : <input type=\"password\" name=\"toegangscode\"/>"
				+ "<br/><br/> <input type=\"submit\" value=\"Login\"/> </form>");

		out.println( "</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "</table>");

		out.println( "</body>");
		out.println( "</html>");

		out.flush();

		out.close();

	}
	
	
	public void showFouteLoginBoodschap( HttpServletResponse response, String code)
			throws java.io.IOException
	{
		response.setContentType( "text/html");
		PrintWriter out = new PrintWriter( response.getOutputStream());

		
		out.println( "<html>");
		out.println( "<head><title>" + "AV2016-Bijlagen" + "</title></head>");
		out.println( "<head>");
		

		out.println( "<meta http-equiv=\"Content-Type\""
				+ "content=\"text/html; charset=ISO-8859-1\">" 
				+ "<style type=\"text/css\">" + ".auto-style1 {"
				+ "text-align: center;}" +

				"</style>");

		out.println( "</head>");
		out.println( "<body>");

		out.println( "<table style=\"width: 100%\">");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td class=\"auto-style1\">" + code + " : FOUTE TOEGANGSCODE !!!") ;

		out.println( "</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "</table>");
		
		
		out.println( "<script>")  ;
		out.println( "setTimeout(function(){window.location='Bijlagen';}, 5000);") ;
		out.println( "</script>") ;
		

		out.println( "</body>");
		out.println( "</html>");

		out.flush();

		out.close();

	}
	
	
	public void bevestigAandeelhouder( HttpServletResponse response, String gebruiker)
			throws java.io.IOException
	{
		response.setContentType( "text/html");
		PrintWriter out = new PrintWriter( response.getOutputStream());

	
		out.println( "<html>");
		out.println( "<head><title>" + "AV2016-Bijlagen" + "</title></head>");
		out.println( "<head>");
		
		out.println( "<meta http-equiv=\"Content-Type\""
				+ "content=\"text/html; charset=ISO-8859-1\">" 
				+ "<style type=\"text/css\">" + ".auto-style1 {"
				+ "text-align: center;}" +

				"</style>");

		out.println( "</head>");
		out.println( "<body>");

		out.println( "<table style=\"width: 100%\">");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td> <form action=\"Bijlagen\" method=\"post\" class=\"auto-style1\">"
				+ "Hierbij bevestigt de gebruiker van deze toepassing wel degelijk " + gebruiker + " te zijn"
				+ "<br/><br/> <input type=\"submit\" value=\"Ja\" name=\"bevestiging\"/>  <input type=\"submit\" value=\"Neen\" name=\"bevestiging\"/> </form>");
		out.println( "</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "</table>");

		out.println( "</body>");
		out.println( "</html>");

		out.flush();

		out.close();

	}
	
	public void downloadPage( HttpServletResponse response)
			throws java.io.IOException
	{
		response.setContentType( "text/html");
		PrintWriter out = new PrintWriter( response.getOutputStream());
		
		out.println( "<html>");
		out.println( "<head><title>" + "AV2016-Bijlagen" + "</title></head>");
		out.println( "<head>");
		

		out.println( "<meta http-equiv=\"Content-Type\""
				+ "content=\"text/html; charset=ISO-8859-1\">"
				+ "<style type=\"text/css\">" + ".auto-style1 {"
				+ "text-align: center;}" +

				"</style>");

		out.println( "</head>");
		out.println( "<body>");

		out.println( "<table style=\"width: 100%\">");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td> <form action=\"Bijlagen\" method=\"post\" class=\"auto-style1\">"
				+ "DOWNLOAD : "
				+ "<br/><br/><br><input type=\"submit\" value=\"1. Oproeping\" name=\"download\"/>"
				+ "<br/><br/><input type=\"submit\" value=\"2. Jaarrekening/Afschrijvingstabel\" name=\"download\"/>" 
				+ "<br><br><input type=\"submit\" value=\"3. Toelichting bij de jaarrekening\" name=\"download\"/>"
				+ "<br><br><input type=\"submit\" value=\"4. Waarderingsregels\" name=\"download\"/>" 
				+ "<br><br><input type=\"submit\" value=\"5. Grootboek ? / nota 2015\" name=\"download\"/>"
				+ "<br><br><br><input type=\"submit\" value=\"Cancel\" name=\"download\"/></form>");
		out.println( "</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "</table>");

		out.println( "</body>");
		out.println( "</html>");

		out.flush();

		out.close();

	}
	
	public void showDownloadRestriction( HttpServletResponse response, String boodschap)
			throws java.io.IOException
	{
		response.setContentType( "text/html");
		PrintWriter out = new PrintWriter( response.getOutputStream());

		
		out.println( "<html>");
		out.println( "<head><title>" + "AV2016-Bijlagen" + "</title></head>");
		out.println( "<head>");
		

		out.println( "<meta http-equiv=\"Content-Type\""
				+ "content=\"text/html; charset=ISO-8859-1\">" 
				+ "<style type=\"text/css\">" + ".auto-style1 {"
				+ "text-align: center;}" +

				"</style>");

		out.println( "</head>");
		out.println( "<body>");

		out.println( "<table style=\"width: 100%\">");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td class=\"auto-style1\">" + boodschap) ;

		out.println( "</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "<tr>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "<td>&nbsp;</td>");
		out.println( "</tr>");
		out.println( "</table>");
		out.println( "<script>")  ;
		out.println( "setTimeout(function(){window.location='Bijlagen';}, 5000);") ;
		out.println( "</script>") ;
		out.println( "</body>");
		out.println( "</html>");

		out.flush();
		out.close();
	}
	
	
	
	void download( HttpServletResponse response, Aandeelhouder aandeelhouder, Document document)
			throws Exception
	{
				
		File dir = new File( "./data") ;
		if( !dir.isDirectory())
		{
			dir.mkdirs() ;
		}
		System.out.println( dir.getCanonicalPath()) ;
		
		File source = new File( dir, document.getBronNaam()) ;
			
		FileInputStream inStream = new FileInputStream( source);

		// if you want to use a relative path to context root:
		//String relativePath = getServletContext().getRealPath( "");
		//System.out.println( "relativePath = " + relativePath);

		// obtains ServletContext
		ServletContext context = getServletContext();

		// gets MIME type of the file
		String mimeType = context.getMimeType( source.toString());
		if( mimeType == null)
		{	// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
		}
		System.out.println( "MIME type: " + mimeType);
		
		// doelnaam
		String doelnaam = new SimpleDateFormat ("yyyyMMddHHmmss").format( new Date()) + aandeelhouder.getAlias() ; 
		document.setDoelNaam(  doelnaam) ;

		// modifies response
		response.setContentType( mimeType);
		response.setContentLength( (int) source.length());

		// forces download
		String headerKey = "Content-Disposition";
		String headerValue = String.format( "attachment; filename=\"%s\"", doelnaam) ;
				//source.getName());
		response.setHeader( headerKey, headerValue);

		// obtains response's output stream
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		while ((bytesRead = inStream.read( buffer)) != -1)
		{
			outStream.write( buffer, 0, bytesRead);
		}

		inStream.close();
		outStream.close();
		
		aandeelhouder.setPogingen(  aandeelhouder.getPogingen() + 1);
		data.save();
	}
	
	private void sendMail( Aandeelhouder aandeelhouder, String subject, String body)
		throws Exception
	{
		SendGrid sendgrid = new SendGrid("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");

	    SendGrid.Email email = new SendGrid.Email();
	    email.addTo( aandeelhouder.getMailAdres());
	    email.setFrom( "noreply-triton@proximus.be");
	    email.addBcc( "noreply-triton@proximus.be") ;
	    email.setSubject( subject);
	    email.setHtml( body);
	    SendGrid.Response resp = sendgrid.send(email);
	
	}
	
	
	public void init( ServletConfig config) 
			throws ServletException
	{
		super.init( config);
		String initial = config.getInitParameter( "initial") ;
		
		try
		{
			data.read() ;
			System.out.println( data.formatXml()) ;
			Aandeelhouder a = data.getAandeelhouders().getByCode( "bbb") ;
			
			/*
			Aandeelhouder a = new Aandeelhouder() ;
			a.setCode( "bbb");
			a.setNaam( "Bernard Claeys");
			a.setMailAdres( "Bernard_Claeys@skynet.be");
			data.getAandeelhouders().add( a);
			*/
			/*
			Document doc = new Document() ;
			doc.setCode( "1.");
			doc.setBronNaam( "Oproeping_kj1.pdf");
			doc.setDoelNaam( "hallo.pdf");
			a.getDocumentTab().add( doc) ;
			data.save();
			*/
			
		}
		catch( Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
	}
	
	public void destroy() 
	{
    try
		{
			data.save();
		}
		catch( Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
	
	

}
