package com.teapotrecords.textadventures.parser;

import com.teapotrecords.textadventures.Adventure;
import com.teapotrecords.textadventures.logic.Command;

public class Parser {
  private Adventure A;
  public Parser(Adventure A) {
    this.A = A;
  }
  
  
  
  public void process(String s) {
    String[] bits = s.toUpperCase().split("\\s+");
    if (bits.length==1) {
      if (s.equals("E") || s.equals("EAST")) Command.execute(Command.GO_EAST, A);
      else if (s.equals("W") || s.equals("WEST")) Command.execute(Command.GO_WEST, A); 
      else if (s.equals("N") || s.equals("NORTH")) Command.execute(Command.GO_NORTH, A);
      else if (s.equals("S") || s.equals("SOUTH")) Command.execute(Command.GO_SOUTH, A);
      else if (s.equals("U") || s.equals("UP")) Command.execute(Command.GO_UP, A);
      else if (s.equals("D") || s.equals("DOWN")) Command.execute(Command.GO_DOWN, A);
      else if (s.equals("L") || s.equals("LOOK")) Command.execute(Command.SHOW_ROOM, A);
      else if (s.equals("I") || s.startsWith("INVENT")) Command.execute(Command.INVENTORY, A);
    }
  }
  
}
