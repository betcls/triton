package com.bc.sql;

import java.io.* ;
import java.util.* ;


public interface SqlTable
{ SqlDef getDef() ;
  void addData( Vector data)
    throws Exception ;

}