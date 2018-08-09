package com.teapotrecords.textadventures.logic;

import java.util.ArrayList;

import com.teapotrecords.textadventures.Adventure;
import com.teapotrecords.textadventures.logic.expr.NEFlag;

public class Location {
  //int id;
  String name;
  String[] description;
  NEFlag selector;
  ArrayList<Item> items;
  ArrayList<Link> links;
  
  //public int getId() { return id; }
  public Item addItem(Item I) { items.add(I); return I;}
  public void removeItem(Item I) { items.remove(I); }
  public void addLink(Link L) { links.add(L); }
  public String getName() { return name; }
  
  public String getDescription() { 
    if (selector==null) return description[0];
    else return description[selector.eval()];
  }
  
  public ArrayList<Item> getItems() { return items; }
  
  public Location(String name, String description, Adventure A) {
    this.name = name;
    this.description = description.split(":");
    items = new ArrayList<Item>();
    links = new ArrayList<Link>();
    //id = 
    A.addLocation(this);
  }
  
  public Location(String name, String description, NEFlag sel, Adventure A) {
    this(name, description, A);
    selector = sel;
    
  }
  
  
  
  
  
  public Link getLink(byte direction) {
    Link result = null;
    for (int i=0; i<links.size(); i++) {
      if (links.get(i).getDirection()==direction) { 
        result = links.get(i);
        i = links.size();
      }
    }
    return result;
  }
  
  
  public Location travel(byte direction) {
    Location dest = null;
    for (int i=0; i<links.size(); i++) {
      if (links.get(i).getDirection()==direction) {
        dest = links.get(i).getDestination();
      }
    }
    return dest;
  }
}
