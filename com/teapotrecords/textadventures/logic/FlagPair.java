package com.teapotrecords.textadventures.logic;

public class FlagPair {
  public static final byte AND = 1;
  public static final byte OR = 2;
  public static final byte XOR = 3;
  
  FlagCondition fc1, fc2;
  byte comparator;
  
  public boolean eval() {
    if (comparator==AND) return fc1.eval() && fc2.eval(); 
    else if (comparator==OR) return fc1.eval() || fc2.eval();
    else if (comparator==XOR) return fc1.eval() ^ fc2.eval();
    else return false; 
  }
  
  public FlagPair(byte c, FlagCondition f1, FlagCondition f2) {
    comparator=c;
    fc1=f1;
    fc2=f2;
  }
  
}
