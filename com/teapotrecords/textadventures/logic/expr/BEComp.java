package com.teapotrecords.textadventures.logic.expr;

public class BEComp extends BE {
  public static final byte EQUAL = 1;
  public static final byte NOT_EQUAL = 2;
  public static final byte GREATER = 3;
  public static final byte GREATER_EQUAL = 4;
  public static final byte LESS = 5;
  public static final byte LESS_EQUAL = 6;

  NE n1, n2;
  byte comp;
  
  public BEComp(NE _n1, byte c, NE _n2) {
    n1 = _n1;
    n2 = _n2;
    comp = c;
  }
  
  public boolean eval() {
    if (comp==EQUAL) return n1.eval() == n2.eval();
    else if (comp==NOT_EQUAL) return n1.eval() != n2.eval();
    else if (comp==GREATER) return n1.eval() > n2.eval();
    else if (comp==GREATER_EQUAL) return n1.eval() >= n2.eval();
    else if (comp==LESS) return n1.eval() < n2.eval();
    else if (comp==LESS_EQUAL) return n1.eval() <= n2.eval();
    else return false; 
  }
  
  
}
