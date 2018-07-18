package com.teapotrecords.textadventures.logic;

import java.util.ArrayList;

import com.teapotrecords.textadventures.Adventure;

public class Location {
  //int id;
  String name;
  String description;
  ArrayList<Item> items;
  ArrayList<Link> links;
  
  //public int getId() { return id; }
  public void addLink(Link L) { links.add(L); }
  public String getName() { return name; }
  public String getDescription() { return description; }
  
  public Location(String name, String description, Adventure A) {
    this.name = name;
    this.description = description;
    items = new ArrayList<Item>();
    links = new ArrayList<Link>();
    //id = 
    A.addLocation(this);
  };
  
  public boolean canMove(byte direction) {
    boolean can_move = false;
    for (int i=0; i<links.size(); i++) {
      if (links.get(i).direction==direction) { 
        can_move = true;
        i = links.size();
      }
    }
    return can_move;
  }
  
  public Location travel(byte direction) {
    Location dest = null;
    for (int i=0; i<links.size(); i++) {
      if (links.get(i).direction==direction) {
        dest = links.get(i).destination;
      }
    }
    return dest;
  }
}
