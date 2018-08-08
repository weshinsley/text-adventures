package com.teapotrecords.textadventures.logic.expr;

public abstract class NE {
  public abstract int eval();
  
  public static NE ZERO = new NEVal(0);
  public static NE ONE = new NEVal(1);
  
}
