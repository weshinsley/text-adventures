package com.teapotrecords.textadventures.logic;

import java.util.ArrayList;

import com.teapotrecords.textadventures.Adventure;

public class LinkEvent {
  
  public static final byte PRINT = 1;
  public static final byte SET_FLAG = 2;
  public static final byte ADD_ITEM_TO_ROOM = 3;
  public static final byte FORBID_MOVE = 4;
  public static final byte PRINT_RANDOM = 5;
  
  public static final byte RESULT_OK = 1;
  public static final byte RESULT_FORBID = 2;
  
  FlagCondition cond;
  byte action;
  String param1;
  int param2;
  Adventure A;
  
  public LinkEvent(FlagCondition c, byte a, String p, int i, Adventure adv) {
    cond = c;
    action = a;
    param1 = p;
    param2 = i;
    A = adv;
    
  }
  
  public byte tryExecute() {
    if (cond.eval()) {
      if (action==PRINT) {
        A.G().echoText(param1, "#000000");
        return RESULT_OK;
        
      } else if (action==PRINT_RANDOM) {
        String[] text = param1.split(":");
        int rand = (int) (Math.random()*text.length);
        A.G().echoText(text[rand], "#000000");
        return RESULT_OK;
        
      } else if (action==SET_FLAG) {
        ArrayList<Flag> flags = A.flags();
        for (int i=0; i<flags.size(); i++) {
          if (flags.get(i).getName().equals(param1)) {
            flags.get(i).setValue(param2);
            i = flags.size();
          }
        }
        return RESULT_OK;
        
      } else if (action==ADD_ITEM_TO_ROOM) {
        ArrayList<Item> items = A.items();
        for (int i=0; i<items.size(); i++) {
          if (items.get(i).getNames().split(":")[0].equals(param1)) {
            A.me().getLocation().addItem(items.get(i));
          }
        }
        return RESULT_OK;
      } else if (action==FORBID_MOVE) {
        return RESULT_FORBID;
      } else return RESULT_OK;
    } else return RESULT_OK;
  }
}
