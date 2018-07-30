package com.teapotrecords.textadventures.logic;

import com.teapotrecords.textadventures.Adventure;

public class FlagPair {
  public static final byte AND = 1;
  public static final byte OR = 2;
  public static final byte XOR = 3;
  
  FlagCondition fc1, fc2;
  byte comparator;
  
  public boolean eval(Adventure A) {
    if (comparator==AND) return fc1.eval(A) && fc2.eval(A); 
    else if (comparator==OR) return fc1.eval(A) || fc2.eval(A);
    else if (comparator==XOR) return fc1.eval(A) ^ fc2.eval(A);
    else return false; 
  }
  
  public FlagPair(byte c, FlagCondition f1, FlagCondition f2) {
    comparator=c;
    fc1=f1;
    fc2=f2;
  }
  
}
