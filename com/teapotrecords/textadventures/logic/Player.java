package com.teapotrecords.textadventures.logic;

import java.util.ArrayList;

public class Player {
  private ArrayList<Item> carrying;
  private Location location;
  int max_weight;
  
  public Player() {
    carrying = new ArrayList<Item>();
    max_weight = 100;
  }
  
  public void setLocation(Location L) { location = L; }
  public Location getLocation() { return location; }
  public ArrayList<Item> carrying() { return carrying; }
  public void carry(Item I) { System.out.println("Carry "+I.getLongName()); carrying.add(I); }
  public void drop(Item I) { carrying.remove(I); System.out.println("Drop "+I.getLongName());}
  
  public int weightCarried() {
    int total=0;
    for (int i=0; i<carrying.size(); i++) total+=carrying.get(i).getWeight();
    return total;
  }
}
