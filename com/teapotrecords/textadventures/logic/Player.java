package com.teapotrecords.textadventures.logic;

import java.util.ArrayList;

public class Player {
  int id;
  ArrayList<Item> carrying;
  String detail;
  Location location;
  int max_weight;
  
  public Player() {
    carrying = new ArrayList<Item>();
    detail = "";
    max_weight = 100;
  }
  
  public void setLocation(Location L) { location = L; }
  public Location getLocation() { return location; }
  public ArrayList<Item> carrying() { return carrying; } 
}
