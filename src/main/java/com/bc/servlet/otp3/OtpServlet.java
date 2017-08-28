package com.bc.servlet.otp3;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;



public abstract class OtpServlet extends HttpServlet
{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet( HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  { resp.setContentType("text/html");
    PrintWriter out = new PrintWriter (resp.getOutputStream());
    out.println("<html>");
    out.println("<head><title>OtpServlet01</title></head>");
    out.println("<body>");
    out.println("Please, don't access me via a Web Browser") ;
    out.println("</body></html>");
    out.close();
  }


  public void doPost( HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  { try
    { HttpSession session = req.getSession( true) ;
      String sessionId = (String)session.getId() ;
      //---
      Enumeration enum1 = req.getHeaderNames() ;
      while( enum1.hasMoreElements())
      { String key = (String)enum1.nextElement() ;
        String value = req.getHeader( key) ;
        //System.out.println( key + " = " + value);
      }
      ObjectInputStream input =
        new ObjectInputStream(
          new BufferedInputStream(
            new GZIPInputStream(
              req.getInputStream()))) ;
      Object requestObj = input.readObject() ;
      input.close() ;
      //


      //---
      try
      {
        if( requestObj instanceof OtpRequest)
        { OtpRequest otpRequest = (OtpRequest)requestObj ;
          Object result = this.processRequest( req, otpRequest) ;
          this.returnObject( resp.getOutputStream(), result);
        }

        else if( requestObj instanceof OtpFileUpload)
        { OtpFileUpload otpFile = (OtpFileUpload)requestObj ;
          //System.out.println( "bytes = " + otpFile.getData().length) ;
          //---
          File parentDir = otpFile.getDestinationFile().getParentFile() ;
          //System.out.println( "parent dir = " + parentDir) ;
          if( parentDir != null && !parentDir.isDirectory())
          { parentDir.mkdirs() ;
          }
          //---

          if( otpFile.getDestinationFile().isFile() &&
              otpFile.getChunkNumber() == 1 &&
              ! otpFile.isReplace())
          { String mssg = "The file " + otpFile.getDestinationFile() +
              " already exists" ;
            //System.out.println( mssg);
            Exception excep = new Exception( mssg) ;
            this.returnObject( resp.getOutputStream(), excep);
            return ;
          }
          else if( otpFile.getDestinationFile().isFile() &&
                   otpFile.getChunkNumber() == 1 &&
                   otpFile.isReplace())
          { otpFile.getDestinationFile().delete() ;
          }
          BufferedOutputStream fileOut =
            new BufferedOutputStream(
              new FileOutputStream( otpFile.getDestinationFile().toString(), true)) ;
          fileOut.write( otpFile.getData());
          fileOut.flush();
          fileOut.close();
          //---
          File result = null ;
          if( parentDir == null)
          { File workDir = new File( ".") ;
            result = new File( workDir.getCanonicalPath(), otpFile.getDestinationFile().toString()) ;
          }
          else if( ! parentDir.isAbsolute())
          { result = otpFile.getDestinationFile().getCanonicalFile() ;
          }
          else
          { result = otpFile.getDestinationFile() ;
          }
          //---
          this.returnObject( resp.getOutputStream(), result);
          return ;
        }

        else if( requestObj instanceof OtpFileDownload)
        { OtpFileDownload otpFile = (OtpFileDownload)requestObj ;

          BufferedOutputStream servletOut =
            new BufferedOutputStream(
              new GZIPOutputStream(
                resp.getOutputStream())) ;

          BufferedInputStream fileIn =
            new BufferedInputStream(
                new FileInputStream( otpFile.getSourceFile())) ;
          int bufferSize = 2*1024 ;
          byte [] buffer = new byte[bufferSize] ;
          int bytesRead = 0 ;
          while( (bytesRead = fileIn.read( buffer, 0, bufferSize)) > -1)
          { servletOut.write( buffer, 0, bytesRead) ;
          }
          fileIn.close();
          servletOut.flush();
          servletOut.close();
        }
        else
        { this.returnObject( resp.getOutputStream(),
            new Exception( "OtpServlet exception : the server received an unkown request object"));
        }
      }
      catch( Exception excep)
      { this.returnObject( resp.getOutputStream(),
            new Exception( excep.getMessage()));
      }
    }
    catch( Exception excep)
    { System.out.println( excep.getMessage());
      excep.printStackTrace( System.out);
    }
  }

  private void returnObject( ServletOutputStream servletOut, Object objectToReturn)
    throws Exception
  { ObjectOutputStream out =
      new ObjectOutputStream(
        new BufferedOutputStream(
          new GZIPOutputStream(
            servletOut))) ;
    out.writeObject( objectToReturn);
    out.flush();
    out.close();
  }

  
  public abstract Object processRequest( HttpServletRequest httpRequest, OtpRequest otpRequest)
    throws Exception ;
	



}