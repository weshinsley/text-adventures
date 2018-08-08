package com.teapotrecords.textadventures.logic.expr;

public class NEVal extends NE {
  int value;
  
  public int eval() { return value; }
  public void set(int v) { value = v;}
  public NEVal(int v) { value = v; }
}
