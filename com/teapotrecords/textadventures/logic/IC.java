package com.teapotrecords.textadventures.logic;

import com.teapotrecords.textadventures.logic.expr.BE;

public class IC {
  BE condition; 
  IA[] commands;
  
  public IC(BE c, IA[] a) {
    condition = c;
    commands = a;
  }
  
  public IC(BE c, IA a) {
    condition = c;
    commands = new IA[] {a};
  }
  
}
