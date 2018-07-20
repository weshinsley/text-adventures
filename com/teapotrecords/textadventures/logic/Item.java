package com.teapotrecords.textadventures.logic;

public class Item {
  private String aliases;
  private String long_name;
  private String detail;
  private int weight;
  
  public String getLongName() { return long_name; }
  public int getWeight() { return weight; }
  public String getDetail() { return detail; }
  public String getNames() { return aliases; }
  public Item(String n, String l, String d, int w) {
    aliases=n;
    long_name=l;
    detail=d;
    weight=w;
  }
  
}
