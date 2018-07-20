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
  public static final short GO_IN = 7;
  public static final short GO_OUT = 8;
  public static final short SHOW_ROOM = 9;
  
  public static final short PICK_UP_ITEM = 10;
  public static final short DROP_ITEM = 11;
  public static final short EXAMINE_ITEM = 12;
  public static final short INVENTORY = 13;
  public static final short DROP_ALL = 14;
  
  
  int action;
  int object;
  public Command(int i) {
    action = i;
  }
  public static void addItemList(StringBuffer sb, ArrayList<Item> is) {
    int count = is.size();
    sb.append(is.get(0).getLongName());
    int i=1;
    while (i<count) {
      if (i==count-1) sb.append(" and "+is.get(i).getLongName());
      else sb.append(", "+is.get(i).getLongName());
      i++;
    }
    sb.append(".");
  }
  
  public static void roomInfo(Adventure A) {
    A.G().echoText("", "#000000");
    A.G().echoText("<hr/>", "#000000");
    A.G().echoText(A.me().getLocation().getName(),"#ff0000");
    A.G().echoText("", "#000000");
    StringBuffer sb = new StringBuffer(A.me().getLocation().getDescription());
    
    ArrayList<Item> items = A.me().getLocation().getItems();
    int count = items.size();
    if (count>0) {
      sb.append(" You can see ");
      addItemList(sb, items);
    }      
    A.G().echoText(sb.toString(), "#000000");
    A.G().echoText("", "#000000");
  }
  
  public static void movePlayer(Adventure A, byte link_dir) {
    Location L = A.me().getLocation();
    if (L.canMove(link_dir)) {
      A.me().setLocation(L.travel(link_dir));
      roomInfo(A);
    } else {
      A.G().echoText("You can't go in that direction", "#000000");
    }
  }
  
  public static void invent(Adventure A) {
    ArrayList<Item> items = A.me().carrying();
    int count = items.size();
    if (count==0) {
      A.G().echoText("You are not carrying anything.", "#000000");
    } else {
      StringBuffer sb = new StringBuffer("You are carring: ");
      addItemList(sb, items);
      A.G().echoText(sb.toString(), "#000000");
    }
  }
  
  public static void execute(short command, Adventure A) {
    if (command == GO_EAST) movePlayer(A, Link.DIR_EAST);
    else if (command == GO_WEST) movePlayer(A, Link.DIR_WEST);
    else if (command == GO_NORTH) movePlayer(A, Link.DIR_NORTH);
    else if (command == GO_SOUTH) movePlayer(A, Link.DIR_SOUTH);
    else if (command == GO_UP) movePlayer(A, Link.DIR_UP);
    else if (command == GO_DOWN) movePlayer(A, Link.DIR_DOWN);
    else if (command == GO_IN) movePlayer(A, Link.DIR_IN);
    else if (command == GO_OUT) movePlayer(A, Link.DIR_OUT);
    else if (command == SHOW_ROOM) roomInfo(A);
    else if (command == INVENTORY) invent(A);
    else if (command == DROP_ALL) dropAll(A);
  }
  
  public static void execute(short command, Item I, Adventure A) {
    if (command == PICK_UP_ITEM) takeItem(I, A);
    else if (command == DROP_ITEM) dropItem(I, A);
    else if (command == EXAMINE_ITEM) examineItem(I, A);
    
  }
  
  public static void takeItem(Item I, Adventure A) {
    if (A.me().carrying().indexOf(I)>=0) {
      A.G().echoText("You are already carrying that.", "#000000");
    } else if (A.me().getLocation().getItems().indexOf(I)==-1) {
      A.G().echoText("You can't find that.", "#000000");
    } else if (A.me().weightCarried()+I.getWeight() > A.me().max_weight) {
      A.G().echoText("That is too much to carry.", "#000000");
    } else {
      A.me().carry(I);
      A.me().getLocation().removeItem(I);
      A.G().echoText("Taken.", "#000000");

    }
  }
  
  public static void dropItem(Item I, Adventure A) {
    if (A.me().carrying().indexOf(I)==-1) {
      A.G().echoText("You're not carrying that.", "#000000");
    } else {
      A.me().drop(I);
      A.me().getLocation().addItem(I);
      A.G().echoText("Dropped.", "#000000");
    }
  }
  
  public static void dropAll(Adventure A) {
    if (A.me().carrying().size()==0) {
      A.G().echoText("You are not carrying anything.", "#000000");
    } else {
      while (A.me().carrying().size()>0) {
        Item I = A.me().carrying().get(0);
        A.me().drop(I);
        A.me().getLocation().addItem(I);
      }
      A.G().echoText("Dropped everything.", "#000000");
    }
  }

  
  public static void examineItem(Item I, Adventure A) {
    if ((A.me().carrying().indexOf(I)>=0) || (A.me().getLocation().getItems().indexOf(I)>=0)) {
      A.G().echoText(I.getDetail(), "#000000");
    } else {
      A.G().echoText("You can't see that.", "#000000");
    }
  }
  

  
}
