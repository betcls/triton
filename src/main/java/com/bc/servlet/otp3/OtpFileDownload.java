package com.bc.servlet.otp3;

import java.io.* ;
import java.util.* ;

public final class OtpFileDownload implements Serializable
{ static final long serialVersionUID = 195710000L ;
  // Versie ///////////////////////////////////
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;
  /////////////////////////////////////////////
  private File sourceFile = null ;

  public OtpFileDownload()
  {
  }
  public File getSourceFile()
  {
    return sourceFile;
  }
  public void setSourceFile(File sourceFile)
  {
    this.sourceFile = sourceFile;
  }
}