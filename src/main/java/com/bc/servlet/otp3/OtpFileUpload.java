package com.bc.servlet.otp3;

import java.io.* ;
import java.util.* ;

public final class OtpFileUpload implements Serializable
{ static final long serialVersionUID = 195710000L ;
  // Versie ///////////////////////////////////
  private static final short clsVersion = 1 ;
  private short objVersion = clsVersion ;
  /////////////////////////////////////////////
  private File sourceFile = null ;
  private File destinationFile = null ;
  private byte [] data = null ;
  private int numberOfChunks = 0 ;
  private int chunkNumber = 0 ;
  private boolean replace = false ;


  public OtpFileUpload()
  {
  }
  public int getChunkNumber()
  {
    return chunkNumber;
  }
  public void setChunkNumber(int chunkNumber)
  {
    this.chunkNumber = chunkNumber;
  }
  public byte[] getData()
  {
    return data;
  }
  public void setData(byte[] data)
  {
    this.data = data;
  }
  public File getDestinationFile()
  {
    return destinationFile;
  }
  public void setDestinationFile(File destinationFile)
  {
    this.destinationFile = destinationFile;
  }
  public int getNumberOfChunks()
  {
    return numberOfChunks;
  }
  public void setNumberOfChunks(int numberOfChunks)
  {
    this.numberOfChunks = numberOfChunks;
  }
  public File getSourceFile()
  {
    return sourceFile;
  }
  public void setSourceFile(File sourceFile)
  {
    this.sourceFile = sourceFile;
  }
  public boolean isReplace()
  {
    return replace;
  }
  public void setReplace(boolean replace)
  {
    this.replace = replace;
  }
}