package com.teapotrecords.textadventures.logic;

import com.teapotrecords.textadventures.Adventure;

public class ConstantFlag extends Flag {
  public ConstantFlag(int val) { super(val); }
  public int eval(Adventure A) { return value; }
}
  
 