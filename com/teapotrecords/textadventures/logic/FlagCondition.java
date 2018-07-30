package com.teapotrecords.textadventures.logic;

import com.teapotrecords.textadventures.Adventure;

public class FlagCondition {
  public static final byte EQUAL = 1;
  public static final byte NOT_EQUAL = 2;
  public static final byte GREATER = 3;
  public static final byte GREATER_EQUAL = 4;
  public static final byte LESS = 5;
  public static final byte LESS_EQUAL = 6;
  
  Flag flag1, flag2;
  byte comparator;
  
  public FlagCondition(Flag f1, byte fc, Flag f2) {
    flag1 = f1;
    flag2 = f2;
    comparator = fc;
  }
  
  public FlagCondition(Flag f1, byte fc, int v) {
    flag1 = f1;
    flag2 = new ConstantFlag(v);
    comparator = fc;
  }

  public boolean eval(Adventure A) {
    if (comparator==EQUAL) return flag1.eval(A) == flag2.eval(A);
    else if (comparator==NOT_EQUAL) return flag1.eval(A) != flag2.eval(A);
    else if (comparator==GREATER) return flag1.eval(A) > flag2.eval(A);
    else if (comparator==GREATER_EQUAL) return flag1.eval(A) >= flag2.eval(A);
    else if (comparator==LESS) return flag1.eval(A) < flag2.eval(A);
    else if (comparator==LESS_EQUAL) return flag1.eval(A) <= flag2.eval(A);
    else return false; 
  }
  
}
