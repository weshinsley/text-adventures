package com.teapotrecords.textadventures.logic.expr;

public class NEFunc extends NE {
  NE n1;
  NE n2;
  byte func;
  public static final byte ADD = 1;
  public static final byte SUB = 2;
  public static final byte MUL = 3;
  public static final byte DIV = 4;
  
  public int eval() {
    if (func==ADD) return n1.eval() + n2.eval();
    else if (func==SUB) return n1.eval() - n2.eval();
    else if (func==MUL) return n1.eval() * n2.eval();
    else if (func==DIV) return n1.eval() * n2.eval();
    else return Integer.MAX_VALUE;
  }

}
