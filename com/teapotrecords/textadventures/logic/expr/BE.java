package com.teapotrecords.textadventures.logic.expr;

public abstract class BE {
  public abstract boolean eval();
  
  public static BE TRUE = new BEConst(true);
  public static BE FALSE = new BEConst(false);
}
