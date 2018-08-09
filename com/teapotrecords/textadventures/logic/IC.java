package com.teapotrecords.textadventures.logic;

import com.teapotrecords.textadventures.logic.expr.BE;

public class IC {
  BE condition; 
  byte action;
  String param1; 
  int param2;
  
  public IC(BE c, byte a, String p, int i) {
    condition = c;
    action=a;
    param1=p;
    param2=i;
  }
}
