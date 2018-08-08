package com.teapotrecords.textadventures.logic.expr;

import com.teapotrecords.textadventures.Adventure;

public class NEFlag extends NE {
  int value;
  String name;
  
  public int eval() { return value; }
  public String getName() { return name; }
  public void set(int v) { value = v;}
  public NEFlag(String n, int v, Adventure A) { 
    name = n; 
    value = v;
    A.addFlag(this);
  }
}
