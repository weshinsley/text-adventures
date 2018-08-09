package com.teapotrecords.textadventures.logic;

import com.teapotrecords.textadventures.Adventure;
import com.teapotrecords.textadventures.logic.expr.NEFlag;

public class Intercept {
  
  public static final byte PRINT = 1;
  public static final byte SET_FLAG = 2;
  public static final byte ADD_ITEM_HERE = 3;
  public static final byte ADD_ITEM_TO_ROOM = 4;
  public static final byte ADD_ITEM_PLAYER = 5;
  public static final byte REMOVE_ITEM_FROM = 6;
  public static final byte REMOVE_ITEM_HERE = 7;
  public static final byte REMOVE_ITEM_PLAYER = 8;
  public static final byte FORBID_MOVE = 9;
  public static final byte PRINT_RANDOM = 10;
  public static final byte PRINT_SEQUENCE = 11;
  public static final byte CHANGE_ITEM_DESCRIPTION = 12;
  public static final byte NO_EXTRA_ECHO = 13;
  
  public static final byte RESULT_NULL = 0;
  public static final byte RESULT_OK = 1;
  public static final byte RESULT_FORBID = 2;
  
  
  Location where;
  short onCommand;
  Item objItem;
 
  IC[] actions;
  Adventure A;
  
  public Location getLocation() { return where; }
  public short getTriggerCommand() { return onCommand; }
  public Item getObjectItem() { return objItem; }
  
  public Intercept(Location w, short com, Item obj, IC[] a, Adventure adv) {
    where = w;
    onCommand = com;
    objItem = obj;
    actions = a;
    A = adv;
   
  }
  
  public byte tryExecute() {
    byte final_result = RESULT_OK;
    for (int a=0; a<actions.length; a++) {
      if (actions[a].condition.eval()) {
        for (int c=0; c<actions[a].commands.length; c++) {
          if (actions[a].commands[c].command==PRINT) {
            A.G().echoText((String)actions[a].commands[c].param1, "#000000");
        
          } else if (actions[a].commands[c].command==PRINT_RANDOM) {
            String[] text = ((String)actions[a].commands[c].param1).split(":");
            int rand = (int) (Math.random()*text.length);
            A.G().echoText(text[rand], "#000000");
        
          } else if (actions[a].commands[c].command==PRINT_SEQUENCE) {
            String[] text = ((String)actions[a].commands[c].param1).split(":");
            A.G().echoText(text[0], "#000000");
            final String ps1 = (String) actions[a].commands[c].param1;
            actions[a].commands[c].param1 = (Object) ps1.substring(ps1.indexOf(":")+1,ps1.length()) +":"
                   + ps1.substring(0, ps1.indexOf(":"));
        
          } else if (actions[a].commands[c].command==CHANGE_ITEM_DESCRIPTION) {
            ((Item) actions[a].commands[c].param1).setLongName((String)actions[a].commands[c].param2);
                
          } else if (actions[a].commands[c].command==SET_FLAG) {
            NEFlag flag = (NEFlag) actions[a].commands[c].param1;
            flag.set((Integer)actions[a].commands[c].param2);
      
          } else if (actions[a].commands[c].command==ADD_ITEM_HERE) {
            A.me().getLocation().addItem((Item)actions[a].commands[c].param1);
        
          } else if (actions[a].commands[c].command==ADD_ITEM_TO_ROOM) {
            ((Location)actions[a].commands[c].param1).addItem((Item)actions[a].commands[c].param2);

          } else if (actions[a].commands[c].command==ADD_ITEM_PLAYER) {
            System.out.println("I:81");
            A.me().carry((Item)actions[a].commands[c].param1);
            System.out.println(A.me().carrying().size());

          } else if (actions[a].commands[c].command==REMOVE_ITEM_HERE) {
            A.me().getLocation().removeItem((Item)actions[a].commands[c].param1);

          } else if (actions[a].commands[c].command==REMOVE_ITEM_FROM) {
            ((Location)actions[a].commands[c].param1).removeItem((Item)actions[a].commands[c].param2);
        
          } else if (actions[a].commands[c].command==REMOVE_ITEM_PLAYER) {
            A.me().drop((Item)actions[a].commands[c].param1);
            System.out.println(A.me().carrying().size());
         
          } else if (actions[a].commands[c].command==FORBID_MOVE) {
            final_result = RESULT_FORBID;
          
          } else if (actions[a].commands[c].command==NO_EXTRA_ECHO) {
            A.C().set_rtr();
          
          } 
        } 
      } 
    }
    return final_result;
  }
}
