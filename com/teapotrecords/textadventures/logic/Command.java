package com.teapotrecords.textadventures.logic;

import java.util.ArrayList;

import com.teapotrecords.textadventures.Adventure;

public class Command {
  public static final short GO_WEST = 1;
  public static final short GO_EAST = 2;
  public static final short GO_NORTH = 3;
  public static final short GO_SOUTH = 4;
  public static final short GO_UP = 5;
  public static final short GO_DOWN = 6;
  public static final short SHOW_ROOM = 7;
  
  public static final short PICK_UP_ITEM = 10;
  public static final short DROP_ITEM = 11;
  public static final short EXAMINE_ITEM = 12;
  public static final short INVENTORY = 13;
  
  int action;
  int object;
  public Command(int i) {
    action = i;
  }
  
  
  public static void roomInfo(Adventure A) {
    A.G().echoText("", "#000000");
    A.G().echoText(A.me().getLocation().getName(),"#ff0000");
    A.G().echoText("", "#000000");
    A.G().echoText(A.me().getLocation().getDescription(),"#000000");
    A.G().echoText("", "#000000");
  }
  
  public static void move_commands(Adventure A, byte link_dir) {
    Location L = A.me().getLocation();
    if (L.canMove(link_dir)) {
      A.me().setLocation(L.travel(link_dir));
      roomInfo(A);
    } else {
      A.G().echoText("You can't go in that direction", "#000000");
      A.G().echoText("", "#000000");
    }
  }
  
  public static void invent(Adventure A) {
    ArrayList<Item> items = A.me().carrying();
    int count = items.size();
    if (count==0) {
      A.G().echoText("You are not carrying anything.", "#000000");
      A.G().echoText("", "#000000");
    } else {
      StringBuffer sb = new StringBuffer("You are carring: "+items.get(0).long_name());
      int i=1;
      while (i<count) {
        if (i==count-1) sb.append(" and "+items.get(i).long_name());
        else sb.append(", "+items.get(i).long_name());
        i++;
      }
      sb.append(".");
      A.G().echoText(sb.toString(), "#000000");
      A.G().echoText("", "#000000");
    }
  }
  
  public static void execute(short command, Adventure A) {
    if (command == GO_EAST) move_commands(A, Link.DIR_EAST);
    else if (command == GO_WEST) move_commands(A, Link.DIR_WEST);
    else if (command == GO_NORTH) move_commands(A, Link.DIR_NORTH);
    else if (command == GO_SOUTH) move_commands(A, Link.DIR_SOUTH);
    else if (command == GO_UP) move_commands(A, Link.DIR_UP);
    else if (command == GO_DOWN) move_commands(A, Link.DIR_DOWN);
    else if (command == SHOW_ROOM) roomInfo(A);
    else if (command == INVENTORY) invent(A);
  }
  
  
}
