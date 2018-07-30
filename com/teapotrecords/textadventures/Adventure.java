package com.teapotrecords.textadventures;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.teapotrecords.textadventures.gui.Gui;
import com.teapotrecords.textadventures.logic.CP;
import com.teapotrecords.textadventures.logic.Flag;
import com.teapotrecords.textadventures.logic.FlagCondition;
import com.teapotrecords.textadventures.logic.Intercept;
import com.teapotrecords.textadventures.logic.Item;
import com.teapotrecords.textadventures.logic.Link;
import com.teapotrecords.textadventures.logic.Location;
import com.teapotrecords.textadventures.logic.Player;
import com.teapotrecords.textadventures.logic.UserFlag;
import com.teapotrecords.textadventures.parser.Parser;

public class Adventure {
  
  private Gui G;
  private Parser P;
  private Player me;
  private CP C;
  private String intro_text;
  private String title;
  private ArrayList<Location> locations = new ArrayList<Location>();
  private ArrayList<Flag> flags = new ArrayList<Flag>();
  private ArrayList<Item> items = new ArrayList<Item>();
  
  public String title() { return title; }
  public ArrayList<Flag> flags() { return flags; }
  public ArrayList<Item> items() { return items; }
  public Player me() { return me; }
  public Parser P() { return P; }
  public Gui G() { return G; }
  public CP C() { return C; }
  public void addLocation(Location L) { locations.add(L); }
  public void addItem(Item I) { items.add(I); }
  
  public ArrayList<String> specialMessages = new ArrayList<String>();
  public ArrayList<Integer> specialMessage_ids = new ArrayList<Integer>();
  
  public String specialMessage(int id) {
    for (int i=0; i<specialMessage_ids.size(); i++) {
      if (specialMessage_ids.get(i).intValue()==id) {
        return specialMessages.get(i);
      }
    }
    return "";
  }
  
  public void addSpecialMessage(int i, String s) {
    specialMessages.add(s);
    specialMessage_ids.add(i);
  }
  
  public Adventure() {
    title = "Luke's Adventure";
    
    P = new Parser(this);
    G = new Gui(this);
    C = new CP();
    me = new Player();
    intro_text = "Welcome to this text adventure! In here we need to put the plot, the background, instructions and all of that stuff...";
        
    G.echoText(intro_text,"#000000");
    G.echoText("", "#000000");
    
    // Load the adventure stuff here I guess.
    
    // Room 1;
    
    Location l1 = new Location("Beach Hut", "You are in your beach hut. It is wooden, warm and homely, with enough space to sleep, cook and store fishing equipment. A door leads South to the beach.", this);
    l1.addItem(new Item("FISHING ROD:ROD", "a fishing rod", "You see nothing special", 5, this));
    l1.addItem(new Item("FISHING NET:NET", "a fishing net", "You see nothing special", 5, this));
    l1.addItem(new Item("BUCKET", "a bucket", "You see nothing special", 5, this));
    l1.addItem(new Item("ROWING BOAT:BOAT", "a rowing boat", "You see nothing special", 150, this));
    
    Item iPotPlant = new Item("POT-PLANT:POT PLANT:POT:PLANT", "a pot-plant in the doorway", "You don't really want to make eye-contact with this thing, but looking down, you notice 'LEVEL 1' engraved on the pot.", 1001, this);
    addSpecialMessage(1001, "As you reach towards it, the pot-plant's utter silence intimidates you, and you decide against touching it.");

    // Room 2;
    Location l2 = new Location("The Beach", "You are on a sandy beach, occasionally interrupted by clusters of smooth round rocks, especially to the South. Your beach hut is to the North.", this);
    
    // As you try to move South, the pot plant appears if it hasn't already...
    Link l1l2 = new Link(l2, Link.DIR_SOUTH);
    l1.addLink(l1l2);
    UserFlag F_POT_PLANT = UserFlag.getUserFlag(this, "F_POT_PLANT", 0);
    FlagCondition FC_POT_PLANT_0 = new FlagCondition(F_POT_PLANT, FlagCondition.EQUAL, UserFlag.getUserFlag(this, "ZERO",0));
    
    C.addIntercept(l1, new short[] {CP.GO_SOUTH, CP.GO_OUT}, null, FC_POT_PLANT_0,  
        Intercept.PRINT, "A pot-plant suddenly appears, blocking your path.",0,this);
    
    C.addIntercept(l1,  new short[] {CP.GO_SOUTH, CP.GO_OUT}, null, FC_POT_PLANT_0, Intercept.ADD_ITEM_TO_ROOM, "POT-PLANT", 0, this);
    C.addIntercept(l1, new short[] {CP.GO_SOUTH, CP.GO_OUT}, null, FC_POT_PLANT_0, Intercept.FORBID_MOVE, null, 0, this);
    
        
    FlagCondition FC_POT_PLANT_1 = new FlagCondition(F_POT_PLANT, 
                                       FlagCondition.EQUAL, 1);
    
    C.addIntercept(l1,  new short[] {CP.GO_SOUTH, CP.GO_OUT},  null, FC_POT_PLANT_1,
        Intercept.PRINT, "The pot-plant stands in your way.", 0, this);
    
    C.addIntercept(l1,  new short[] {CP.GO_SOUTH, CP.GO_OUT},  null, FC_POT_PLANT_1,
        Intercept.PRINT_SEQUENCE, 
        "It maintains a stony silence.:It does absolutely nothing. Menacingly.:It waits, unmoved, not even looking at you.",
        0,this);
    
    C.addIntercept(l1, new short[] {CP.GO_SOUTH, CP.GO_OUT}, null, FC_POT_PLANT_1, Intercept.FORBID_MOVE, "", 0, this);
    C.addIntercept(l1, new short[] {CP.GO_SOUTH, CP.GO_OUT}, null, FC_POT_PLANT_0, Intercept.SET_FLAG, "F_POT_PLANT", 1, this);
    
    C.addIntercept(l1, new short[] {CP.MOVE_ITEM}, iPotPlant, FC_POT_PLANT_1,
        Intercept.PRINT, "You cautiously prod the base, and the pot-plant is moved. (Deeply). The doorway is now available.", 0, this);
    
    C.addIntercept(l1, new short[] {CP.MOVE_ITEM}, iPotPlant, FC_POT_PLANT_1,
        Intercept.CHANGE_ITEM_DESCRIPTION, "POT-PLANT:a pot-plant beside the doorway", 0, this);
    
    C.addIntercept(l1, new short[] {CP.MOVE_ITEM}, iPotPlant, FC_POT_PLANT_1, Intercept.SET_FLAG, "F_POT_PLANT", 2, this);
        
    l2.addLink(new Link(l1, Link.DIR_NORTH));
    l2.addLink(new Link(l1, Link.DIR_IN));
    
 // Location 3;
    Location l3 = new Location("The Beach", "You are on a sandy beach. A cluster of smooth rocks is to the South.", this);
    l2.addLink(new Link(l3, Link.DIR_SOUTH));
    l3.addLink(new Link(l2, Link.DIR_NORTH));
    
    // Locaiton 4:
    Location l4 = new Location("Rockpools", "You are standing gingerly on a smooth and slightly slippery rock, which together with a few others, forms a rockpool. "+
                               "The top of a mysterious box pokes out above the surface, guarded by a squadron of crabs, who "+
                               "snap their pincers slowly and ominously at you.", this);
    l3.addLink(new Link(l4, Link.DIR_SOUTH));
    l4.addLink(new Link(l3, Link.DIR_NORTH));
    
    
    me.setLocation(l1);
    C.roomInfo(this);
    
    
  }
  
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new Adventure();
      }   
    });
  }
}
