package com.teapotrecords.textadventures.parser;

import java.util.ArrayList;

import com.teapotrecords.textadventures.Adventure;
import com.teapotrecords.textadventures.logic.Command;
import com.teapotrecords.textadventures.logic.Item;

public class Parser {
  private Adventure A;
  public Parser(Adventure A) {
    this.A = A;
  }
  
  Item lookupItem(ArrayList<Item> items, String name) {
    Item result = null;
    for (int i=0; i<items.size(); i++) {
      String[] aliases = items.get(i).getNames().split(":");
      for (int j=0; j<aliases.length; j++) {
        if (aliases[j].equals(name)) {
          result = items.get(i);
        }
      }
    }
    return result;
  }
  
  Item findItem(String[] bits, int first_index) {
    StringBuffer sb = new StringBuffer();
    for (int i=first_index; i<bits.length; i++) {
      if (bits[i].length()>0) {
        sb.append(" "+bits[i]);
      }
    }
    sb.deleteCharAt(0);
    ArrayList<Item> loc_items = A.me().getLocation().getItems();
    Item lookup = lookupItem(loc_items, sb.toString());
    if (lookup==null) {
      ArrayList<Item> carry_items = A.me().carrying();
      lookup = lookupItem(carry_items, sb.toString());
    }
    return lookup;
  }
  
  
  public void process(String s) {
    String[] bits = s.trim().toUpperCase().split("\\s+");
    if (bits.length==1) {
      if (s.equals("E") || s.equals("EAST")) Command.execute(Command.GO_EAST, A);
      else if (s.equals("W") || s.equals("WEST")) Command.execute(Command.GO_WEST, A); 
      else if (s.equals("N") || s.equals("NORTH")) Command.execute(Command.GO_NORTH, A);
      else if (s.equals("S") || s.equals("SOUTH")) Command.execute(Command.GO_SOUTH, A);
      else if (s.equals("U") || s.equals("UP")) Command.execute(Command.GO_UP, A);
      else if (s.equals("D") || s.equals("DOWN")) Command.execute(Command.GO_DOWN, A);
      else if (s.equals("IN")) Command.execute(Command.GO_IN, A);
      else if (s.equals("OUT")) Command.execute(Command.GO_OUT, A);
      else if (s.equals("L") || s.equals("LOOK")) Command.execute(Command.SHOW_ROOM, A);
      else if (s.equals("I") || s.startsWith("INVENT")) Command.execute(Command.INVENTORY, A);
    } else {
      
      if ((bits[0].equals("GET")) || (bits[0].equals("TAKE"))) {
        Command.execute(Command.PICK_UP_ITEM, findItem(bits,1), A);
      } else if ((bits[0].equals("PICK")) && (bits[1].equals("UP"))) {
        Command.execute(Command.PICK_UP_ITEM, findItem(bits,2), A);
      } else if ((bits[0].equals("DROP")) && (bits[1].equals("ALL"))) {
        Command.execute(Command.DROP_ALL,  A);
      } else if (bits[0].equals("DROP")) {
        Command.execute(Command.DROP_ITEM, findItem(bits,1), A);
      } else if ((bits[0].equals("LOOK")) || (bits[0].equals("EXAMINE"))) {
        Command.execute(Command.EXAMINE_ITEM, findItem(bits,1), A);
      }
    }
  }
  
}
