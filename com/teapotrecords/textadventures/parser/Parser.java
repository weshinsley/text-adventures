package com.teapotrecords.textadventures.parser;

import java.util.ArrayList;

import com.teapotrecords.textadventures.Adventure;
import com.teapotrecords.textadventures.logic.CP;
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
  
  public Item findItem(String[] bits, int first_index) {
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
      if (s.equals("E") || s.equals("EAST")) A.C().execute(CP.GO_EAST, A);
      else if (s.equals("W") || s.equals("WEST")) A.C().execute(CP.GO_WEST, A); 
      else if (s.equals("N") || s.equals("NORTH")) A.C().execute(CP.GO_NORTH, A);
      else if (s.equals("S") || s.equals("SOUTH")) A.C().execute(CP.GO_SOUTH, A);
      else if (s.equals("U") || s.equals("UP")) A.C().execute(CP.GO_UP, A);
      else if (s.equals("D") || s.equals("DOWN")) A.C().execute(CP.GO_DOWN, A);
      else if (s.equals("IN")) A.C().execute(CP.GO_IN, A);
      else if (s.equals("OUT")) A.C().execute(CP.GO_OUT, A);
      else if (s.equals("L") || s.equals("LOOK")) A.C().execute(CP.SHOW_ROOM, A);
      else if (s.equals("I") || s.startsWith("INVENT")) A.C().execute(CP.INVENTORY, A);
      else A.G().echoText("I didn't understand that", "#000000");
    } else {
      
      if ((bits[0].equals("GET")) || (bits[0].equals("TAKE"))) {
        A.C().execute(CP.PICK_UP_ITEM, findItem(bits,1), A);
      } else if ((bits[0].equals("PICK")) && (bits[1].equals("UP"))) {
        A.C().execute(CP.PICK_UP_ITEM, findItem(bits,2), A);
      } else if ((bits[0].equals("DROP")) && (bits[1].equals("ALL"))) {
        A.C().execute(CP.DROP_ALL,  A);
      } else if (bits[0].equals("DROP")) {
        A.C().execute(CP.DROP_ITEM, findItem(bits,1), A);
      } else if ((bits[0].equals("LOOK")) || (bits[0].equals("EXAMINE"))) {
        A.C().execute(CP.EXAMINE_ITEM, findItem(bits,1), A);
      } else if (bits[0].equals("MOVE") || (bits[0].equals("PUSH"))) {
        A.C().execute(CP.MOVE_ITEM, findItem(bits,1), A);
      } else A.G().echoText("I didn't understand that",  "#000000");
    }
  }
  
}
