package com.teapotrecords.textadventures.logic.expr;

import com.teapotrecords.textadventures.Adventure;

public class NEFlag extends NE {
  int value;
  
  public int eval() { return value; }
  public void set(int v) { value = v;}
  public NEFlag(int v, Adventure A) { 
    value = v;
    A.addFlag(this);
  }
}
