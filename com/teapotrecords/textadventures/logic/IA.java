package com.teapotrecords.textadventures.logic;

public class IA {
  byte command;
  Object param1; 
  Object param2;
  
  public IA(byte c, Object p1, Object p2) {
    command = c;
    param1 = p1;
    param2 = p2;
  }
  public IA(byte c, Object p1) {
    command = c;
    param1 = p1;
    param2 = null;
  }
  
  public IA(byte c) {
    command = c;
    param1 = null;
    param2 = null;
  }
}
