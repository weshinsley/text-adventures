package com.teapotrecords.textadventures.logic.expr;

public class BEConst extends BE {
  boolean value;
  public boolean eval() { return value; }
  public BEConst(boolean b) { value = b; }
}
