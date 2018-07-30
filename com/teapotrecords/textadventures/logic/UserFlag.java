package com.teapotrecords.textadventures.logic;

import java.util.ArrayList;

import com.teapotrecords.textadventures.Adventure;

public class UserFlag extends Flag {
  String name;
  
  public String getName() { return name; }
  public int eval(Adventure A) { return value; }
  public void setValue(int v) { value = v;}
  
  private UserFlag(String n, int v) {
    super(v);
    name = n;
  }
  
  public static UserFlag getUserFlag(Adventure A, String n, int v) {
    ArrayList<Flag> f = A.flags();
    UserFlag result = null;
    for (int i=0; i<f.size(); i++) {
      if (f.get(i) instanceof UserFlag) {
        UserFlag uf = (UserFlag) f.get(i);
        if (uf.getName() == n) {
          result = uf;
          i=f.size();
        }
      }
    }
    if (result == null) {
      UserFlag nf = new UserFlag(n,v);
      f.add(nf);
      result = nf;
    }
    return result;
  }
}