package com.teapotrecords.textadventures.logic;

import java.util.ArrayList;

public class Link {
 private Location destination;
 private byte direction;
 private ArrayList<LinkEvent> events = new ArrayList<LinkEvent>();
 
 public ArrayList<LinkEvent> getEvents() { return events; }
 public byte getDirection() { return direction; }
 public Location getDestination() { return destination; }
 public void setDirection(byte b) { direction = b; }
 public void setDestination(Location d) { destination = d; }
 
 public Link(Location dest, byte d) {
   destination=dest;
   direction=d;
 }
 
 public void addEvent(LinkEvent le) { events.add(le); }
 public void copyEvents(Link l) { events = l.getEvents(); };
 
 public static final byte DIR_NORTH = 1;
 public static final byte DIR_EAST = 2;
 public static final byte DIR_SOUTH = 3;
 public static final byte DIR_WEST = 4;
 public static final byte DIR_UP = 5;
 public static final byte DIR_DOWN = 6;
 public static final byte DIR_IN = 5;
 public static final byte DIR_OUT = 6;
 
}
