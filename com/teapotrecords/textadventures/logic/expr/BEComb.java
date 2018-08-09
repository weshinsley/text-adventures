package com.teapotrecords.textadventures.logic.expr;

public class BEComb extends BE {
  
  public static final byte AND = 1;
  public static final byte OR = 2;
  public static final byte XOR = 3;
  
  BE b1;
  BE b2;
  byte func;
  
  public BEComb(BE _b1, byte f, BE _b2) {
    b1 = _b1;
    func = f;
    b2 = _b2;
  }
  
  public boolean eval() {
    if (func==AND) return b1.eval() && b2.eval();
    else if (func==OR) return b1.eval() || b2.eval();
    else if (func==XOR) return b1.eval() ^ b2.eval();
    else return false;
  }
  
  

}
