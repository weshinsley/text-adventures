package com.teapotrecords.textadventures.logic;

import java.util.ArrayList;

import com.teapotrecords.textadventures.Adventure;

public class Flag {
  String name;
  int value;
  
  private Flag(String n, int v) {
    name = n;
    value = v;
  }
  public int getValue() { return value; }
  public String getName() { return name; }
  public void setValue(int v) { value = v;}
  
  public static Flag getFlag(Adventure A, String n, int v) {
    ArrayList<Flag> f = A.flags();
    Flag result = null;
    for (int i=0; i<f.size(); i++) {
      if (f.get(i).name == n) {
        result = f.get(i);
        i=f.size();
      }
    }
    if (result == null) {
      Flag nf = new Flag(n,v);
      f.add(nf);
      result = nf;
    }
    return result;
  }
}