package com.bc.servlet.otp3;



public class OtpSessionExpired extends Exception
{
  public OtpSessionExpired( String message)
  { super( message) ;
  }
}