package com.teapotrecords.textadventures.logic;

import java.util.ArrayList;

import com.teapotrecords.textadventures.Adventure;
import com.teapotrecords.textadventures.logic.expr.BE;
import com.teapotrecords.textadventures.logic.expr.NEFlag;

public class Intercept {
  
  public static final byte PRINT = 1;
  public static final byte SET_FLAG = 2;
  public static final byte ADD_ITEM_TO_ROOM = 3;
  public static final byte FORBID_MOVE = 4;
  public static final byte PRINT_RANDOM = 5;
  public static final byte PRINT_SEQUENCE = 6;
  public static final byte CHANGE_ITEM_DESCRIPTION = 7;
  
  public static final byte RESULT_NULL = 0;
  public static final byte RESULT_OK = 1;
  public static final byte RESULT_FORBID = 2;
  
  
  Location where;
  short onCommand;
  Item objItem;
 
  BE cond;
  byte action;
  String param1;
  int param2;
  Adventure A;
  
  public Location getLocation() { return where; }
  public short getTriggerCommand() { return onCommand; }
  public Item getObjectItem() { return objItem; }
  
  public Intercept(Location w, short com, Item obj, BE c, byte a, String p, int i, 
                   Adventure adv) {
    where = w;
    onCommand = com;
    objItem = obj;
    cond = c;
    action = a;
    param1 = p;
    param2 = i;
    A = adv;
   
  }
  
  public byte tryExecute() {
    byte final_result = RESULT_OK;
    if (cond.eval()) {
      if (action==PRINT) {
        A.G().echoText(param1, "#000000");
        
      } else if (action==PRINT_RANDOM) {
        String[] text = param1.split(":");
        int rand = (int) (Math.random()*text.length);
        A.G().echoText(text[rand], "#000000");
        
      } else if (action==PRINT_SEQUENCE) {
        String[] text = param1.split(":");
        A.G().echoText(text[0], "#000000");
        param1 = param1.substring(param1.indexOf(":")+1,param1.length()) +":"
               + param1.substring(0, param1.indexOf(":"));
        
      } else if (action==CHANGE_ITEM_DESCRIPTION) {
        String[] bits = param1.split(":");
        Item I = A.P().findItem(new String[] {bits[0]}, 0);
        I.setLongName(bits[1]);
                
      } else if (action==SET_FLAG) {
        ArrayList<NEFlag> flags = A.flags();
        for (int j=0; j<flags.size(); j++) {
          if (flags.get(j).getName().equals(param1)) {
            flags.get(j).set(param2);
            j = flags.size();
          }
        }
      
      } else if (action==ADD_ITEM_TO_ROOM) {
        ArrayList<Item> items = A.items();
        for (int j=0; j<items.size(); j++) {
          if (items.get(j).getNames().split(":")[0].equals(param1)) {
            A.me().getLocation().addItem(items.get(j));
          }
        }
      
      } else if (action==FORBID_MOVE) {
        final_result = RESULT_FORBID;
      } else final_result = RESULT_NULL; 
    } else final_result = RESULT_NULL;
 
    return final_result;
  }
  
}
