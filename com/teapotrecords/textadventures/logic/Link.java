package com.teapotrecords.textadventures.logic;

public class Link {
 Location destination;
 byte direction;
 
 public Link(Location dest, byte d) {
   destination=dest;
   direction=d;
 }
 
 public static final byte DIR_NORTH = 1;
 public static final byte DIR_EAST = 2;
 public static final byte DIR_SOUTH = 3;
 public static final byte DIR_WEST = 4;
 public static final byte DIR_UP = 5;
 public static final byte DIR_DOWN = 6;
 public static final byte DIR_IN = 5;
 public static final byte DIR_OUT = 6;
 
}
