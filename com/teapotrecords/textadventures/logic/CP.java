package com.teapotrecords.textadventures.logic;

import java.util.ArrayList;

import com.teapotrecords.textadventures.Adventure;
import com.teapotrecords.textadventures.logic.expr.BE;
// CP = Command Processor, but life's too short to be typing that all over the place.
public class CP {
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
  public static final short MOVE_ITEM = 15;
  
  private ArrayList<Intercept> intercepts = new ArrayList<Intercept>();
  public ArrayList<Intercept> getIntercepts() { return intercepts; }
  public void addIntercept(Intercept le) { intercepts.add(le); }
  
  public void addIntercept(Location w, short com, Item obj, BE c, byte a, String p, int i, 
      Adventure adv) {
    intercepts.add(new Intercept(w, com, obj, c, a, p, i, adv));
  }
  
  public void addIntercept(Location w, short[] com, Item obj, BE c, byte a, String p, int i, 
      Adventure adv) {
    for (int cc=0; cc<com.length; cc++) { 
      intercepts.add(new Intercept(w, com[cc], obj, c, a, p, i, adv));
    }
  }
  
  public void addIntercept(Location w, short com, Item obj, IC[] ics, Adventure adv) {
    for (int ic=0; ic<ics.length; ic++) {
      intercepts.add(new Intercept(w, com, obj, ics[ic].condition, ics[ic].action, ics[ic].param1, ics[ic].param2, adv));
    }
  }
  
  public void addIntercept(Location w, short[] com, Item obj, IC[] ics, Adventure adv) {
    for (int cc=0; cc<com.length; cc++) {
      for (int ic=0; ic<ics.length; ic++) {
        intercepts.add(new Intercept(w, com[cc], obj, ics[ic].condition, ics[ic].action, ics[ic].param1, ics[ic].param2, adv));
      }
    }
  }

  
  public CP() { }
  
  public void addItemList(StringBuffer sb, ArrayList<Item> is, int count) {
    int c=0;
    for (int i=0; i<is.size(); i++) {
      if (is.get(i).printInList()) {
        if (c==0) {
          sb.append(is.get(i).getLongName());
        } else if (c==count-1) {
          sb.append(" and "+is.get(i).getLongName());
        } else {
          sb.append(", "+is.get(i).getLongName());  
        } 
        c++;
      }
    }
    sb.append(".");
  }
  
  public void roomInfo(Adventure A) {
    A.G().echoText("", "#000000");
    A.G().echoText("<hr/>", "#000000");
    A.G().echoText(A.me().getLocation().getName(),"#ff0000");
    A.G().echoText("", "#000000");
    StringBuffer sb = new StringBuffer(A.me().getLocation().getDescription());
    
    ArrayList<Item> items = A.me().getLocation().getItems();
    int count = 0;
    for (int i=0; i<items.size(); i++) {
      if (items.get(i).printInList()) count++;
    }
    if (count>0) {
      sb.append(" You can see ");
      addItemList(sb, items, count);
    }      
    A.G().echoText(sb.toString(), "#000000");
    A.G().echoText("", "#000000");
  }
  
  public void movePlayer(Adventure A, byte link_dir) {
    Location L = A.me().getLocation();
    Link LL = L.getLink(link_dir);
    if (LL==null) {
      A.G().echoText("You can't go in that direction", "#000000");
    } else {
      A.me().setLocation(L.travel(link_dir));
      roomInfo(A);
    }
  }
  
  public void invent(Adventure A) {
    ArrayList<Item> items = A.me().carrying();
    int count = items.size();
    if (count==0) {
      A.G().echoText("You are not carrying anything.", "#000000");
    } else {
      StringBuffer sb = new StringBuffer("You are carring: ");
      addItemList(sb, items, items.size());
      A.G().echoText(sb.toString(), "#000000");
    }
  }
  
  public byte findIntercept(Location loc, short command, Item obj) {
    byte proceed = Intercept.RESULT_NULL;
    for (int i=0; i<intercepts.size(); i++) {
      Location L = intercepts.get(i).getLocation();
      if ((L==null) || (L==loc)) {
        short com = intercepts.get(i).getTriggerCommand();
        Item ii = intercepts.get(i).getObjectItem();
        if ((com == command) && (ii == obj)) {
          byte res = intercepts.get(i).tryExecute();
          if (res == Intercept.RESULT_FORBID) proceed=Intercept.RESULT_FORBID;
          if ((res == Intercept.RESULT_OK) && (proceed==Intercept.RESULT_NULL)) proceed=Intercept.RESULT_OK; 
        
        }
      }
    }
    return proceed;
  }
  
  
  public void execute(short command, Adventure A) {
    byte proceed = findIntercept(A.me().getLocation(), command, null);
    if (proceed!=Intercept.RESULT_FORBID) {
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
  }
  
  public void execute(short command, Item I, Adventure A) {
    byte proceed = findIntercept(A.me().getLocation(), command, I);
    if (proceed!=Intercept.RESULT_FORBID) {
      if (command == PICK_UP_ITEM) { takeItem(I, A); proceed = Intercept.RESULT_OK; }
      else if (command == DROP_ITEM) { dropItem(I, A); proceed = Intercept.RESULT_OK; }
      else if (command == EXAMINE_ITEM) { examineItem(I, A); proceed = Intercept.RESULT_OK; }
      if (proceed==Intercept.RESULT_NULL) {
        A.G().echoText("Nothing particular happens.", "#00000");
      }
    }
  }
  
  public void takeItem(Item I, Adventure A) {
    if (A.me().carrying().indexOf(I)>=0) {
      A.G().echoText("You are already carrying that.", "#000000");
    } else if (A.me().getLocation().getItems().indexOf(I)==-1) {
      A.G().echoText("You can't find that.", "#000000");
    } else if (I.getWeight()>1000) {
      A.G().echoText(A.specialMessage(I.getWeight()),"#000000");
    } else if (A.me().weightCarried()+I.getWeight() > A.me().max_weight) {
      A.G().echoText("That is too much to carry.", "#000000");
    } else {
      A.me().carry(I);
      A.me().getLocation().removeItem(I);
      A.G().echoText("Taken.", "#000000");

    }
  }
  
  public void dropItem(Item I, Adventure A) {
    if (A.me().carrying().indexOf(I)==-1) {
      A.G().echoText("You're not carrying that.", "#000000");
    } else {
      A.me().drop(I);
      A.me().getLocation().addItem(I);
      A.G().echoText("Dropped.", "#000000");
    }
  }
  
  public void dropAll(Adventure A) {
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

  
  public void examineItem(Item I, Adventure A) {
    if ((A.me().carrying().indexOf(I)>=0) || (A.me().getLocation().getItems().indexOf(I)>=0)) {
      A.G().echoText(I.getDetail(), "#000000");
    } else {
      A.G().echoText("You can't see that.", "#000000");
    }
  }
  

  
}
