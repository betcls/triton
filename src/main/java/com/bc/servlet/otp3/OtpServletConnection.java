package com.bc.servlet.otp3;

import java.io.* ;
import java.net.* ;
import java.util.zip.* ;

public class OtpServletConnection
{
  private String sessionId = null ;
  private URL servletUrl = null ;

  public OtpServletConnection( URL servletUrl)
  { this.servletUrl = servletUrl ;
  }
  public OtpServletConnection( String servletUrl)
    throws Exception
  { this.servletUrl = new java.net.URL( servletUrl) ;
  }


  public Object sendRequest( Object request)
    throws Exception
  { HttpURLConnection servletCon = null ;
    Object result = null ;
    try
    { servletCon = (HttpURLConnection)servletUrl.openConnection() ;
    }
    catch( IOException ioe)
    { throw new Exception( "Can't connect : " + ioe.getMessage()) ;
    }
    //--- initialize the connection
    servletCon.setUseCaches(false);
    servletCon.setDoOutput(true);
    servletCon.setDoInput(true);
    //--- set headers
    servletCon.setRequestProperty( "user.name", System.getProperty( "user.name"));
    if( sessionId != null)
    { servletCon.setRequestProperty( "cookie", sessionId);
      //System.out.println("sessionid = " + sessionId);
    }
    servletCon.setRequestProperty("Content-type", "application/octet-stream");

    // --- open de output stream
    ObjectOutputStream objectOut =
      new ObjectOutputStream(
        new GZIPOutputStream(
          servletCon.getOutputStream())) ;
    objectOut.writeObject( request);
    //--- flush the output stream and close it
    objectOut.flush() ;
    objectOut.close() ;
    //----------------
    //--- the response
    //----------------
    ObjectInputStream objectIn =
      new ObjectInputStream(
        new BufferedInputStream(
          new GZIPInputStream(
            servletCon.getInputStream())));
    result  = objectIn.readObject() ;
    objectIn.close() ;
    //---
    if( result instanceof OtpSessionExpired)
    { sessionId = null ;
    }

    //---
    String cookie = servletCon.getHeaderField( "set-cookie") ;
    if( cookie != null)
    { sessionId = parseCookie( cookie) ;
    }
    if( result instanceof Exception)
    { throw (Exception)result ;
    }

    return result ;
  }

  public Object uploadFile( File sourceFile, File destinationFile)
    throws Exception
  { return this.uploadFile( sourceFile, destinationFile, false) ;
  }

  public Object uploadFile( File sourceFile, File destinationFile, boolean replace)
    throws Exception
  { Object result = null ;
    int fileLen = (int)sourceFile.length() ;
    int chunkSize = 10*1024*1024 ;
    int nbChunks = fileLen/chunkSize +
      (fileLen % chunkSize > 0 ? 1 : 0) ;
    int [] chunkSizes  = new int[nbChunks] ;
    int nb = 0 ;
    for( nb = 0; (nb+1)*chunkSize <= fileLen; nb++)
    { chunkSizes[nb] = chunkSize ;
    }
    if( (fileLen % chunkSize) > 0)
    { chunkSizes[nb] = (int)(fileLen % chunkSize) ;
    }
    BufferedInputStream fileIn =
      new BufferedInputStream(
        new FileInputStream( sourceFile)) ;
    for( int  i= 0; i < chunkSizes.length; i++)
    { //---
      byte [] buffer = new byte[chunkSizes[i]] ;
      int bytesRead = fileIn.read( buffer) ;
      System.out.println( "bytes read = " + bytesRead);
      OtpFileUpload request = new OtpFileUpload() ;
      request.setChunkNumber(i+1);
      request.setNumberOfChunks( nbChunks) ;
      request.setDestinationFile( destinationFile);
      request.setSourceFile( sourceFile);
      request.setData( buffer);
      request.setReplace( replace);
      result = this.sendRequest( request) ;

      if( result instanceof Exception)
      { fileIn.close();
        throw (Exception)result ;
      }
    }
    fileIn.close();
    //---
    return result ;
  }


  public void downloadFile( File sourceFile, File destinationFile)
    throws Exception
  { HttpURLConnection servletCon = null ;

    try
    { servletCon = (HttpURLConnection)servletUrl.openConnection() ;
    }
    catch( IOException ioe)
    { throw new Exception( "Can't connect : " + ioe.getMessage()) ;
    }
    //--- initialize the connection
    servletCon.setUseCaches(false);
    servletCon.setDoOutput(true);
    servletCon.setDoInput(true);
    //--- set headers
    if( sessionId != null)
    { servletCon.setRequestProperty( "cookie", sessionId);
      //System.out.println("sessionid = " + sessionId);
    }
    servletCon.setRequestProperty("Content-type", "application/octet-stream");
    //---
    OtpFileDownload request = new OtpFileDownload() ;
    request.setSourceFile( sourceFile);
    //--- open de output stream
    ObjectOutputStream conOut =
      new ObjectOutputStream(
        new GZIPOutputStream(
          servletCon.getOutputStream())) ;
    conOut.writeObject( request);
    //--- flush the output stream and close it
    conOut.flush() ;
    conOut.close() ;
    //----------------
    //--- the response
    //----------------
    BufferedInputStream conIn =
        new BufferedInputStream(
          new GZIPInputStream(
            servletCon.getInputStream()));

    BufferedOutputStream fileOut =
      new BufferedOutputStream(
        new FileOutputStream( destinationFile)) ;
    int BUFFER_SIZE = 2*1024 ;
    byte [] buffer = new byte[BUFFER_SIZE] ;
    int bytesRead = 0 ;
    while( (bytesRead = conIn.read( buffer, 0, BUFFER_SIZE)) > -1)
    { fileOut.write( buffer, 0, bytesRead) ;
    }
    fileOut.flush();
    fileOut.close() ;
    conIn.close();

    //---
    String cookie = servletCon.getHeaderField( "set-cookie") ;
    if( cookie != null)
    { sessionId = parseCookie( cookie) ;
    }

  }






  private String parseCookie( String raw)
  { String c = raw;
    if (raw != null)
    { // Find the first ';'
      int endIndex = raw.indexOf(";");
      // Found a ';', assume the key/value is prior
      if (endIndex >= 0)
      { c = raw.substring(0, endIndex);
      }
    }
    return c;
  }
}