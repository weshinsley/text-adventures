package com.teapotrecords.textadventures.logic.expr;

public class BEInv extends BE {
  BE b;
  public BEInv(BE be) { b = be; }
  public boolean eval() {
    return !b.eval();
  }

}
