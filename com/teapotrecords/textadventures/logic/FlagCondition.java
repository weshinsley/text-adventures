package com.teapotrecords.textadventures.logic;

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
  public boolean eval() {
    if (comparator==EQUAL) return flag1.getValue() == flag2.getValue();
    else if (comparator==NOT_EQUAL) return flag1.getValue() != flag2.getValue();
    else if (comparator==GREATER) return flag1.getValue() > flag2.getValue();
    else if (comparator==GREATER_EQUAL) return flag1.getValue() >= flag2.getValue();
    else if (comparator==LESS) return flag1.getValue() < flag2.getValue();
    else if (comparator==LESS_EQUAL) return flag1.getValue() <= flag2.getValue();
    else return false; 
  }
  
  
}
