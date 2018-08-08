
package com.teapotrecords.textadventures.logic;

import com.teapotrecords.textadventures.Adventure;

public class Item {
  private String aliases;
  private String long_name;
  private String detail;
  private int weight;
  private boolean printInList;
  
  public String getLongName() { return long_name; }
  public void setLongName(String s) { long_name = s; }
  public int getWeight() { return weight; }
  public String getDetail() { return detail; }
  public String getNames() { return aliases; }
  public boolean printInList() { return printInList; }
  
  public Item(String n, String l, String d, int w, boolean pil, Adventure A) {
    aliases=n;
    long_name=l;
    detail=d;
    weight=w;
    printInList = pil;
    A.addItem(this);
  }
  
}
