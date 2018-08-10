package com.teapotrecords.textadventures;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.teapotrecords.textadventures.gui.Gui;
import com.teapotrecords.textadventures.logic.CP;
import com.teapotrecords.textadventures.logic.IA;
import com.teapotrecords.textadventures.logic.IC;
import com.teapotrecords.textadventures.logic.Intercept;
import com.teapotrecords.textadventures.logic.Item;
import com.teapotrecords.textadventures.logic.Link;
import com.teapotrecords.textadventures.logic.Location;
import com.teapotrecords.textadventures.logic.Player;
import com.teapotrecords.textadventures.logic.expr.BE;
import com.teapotrecords.textadventures.logic.expr.BEComb;
import com.teapotrecords.textadventures.logic.expr.BEComp;
import com.teapotrecords.textadventures.logic.expr.BEInv;
import com.teapotrecords.textadventures.logic.expr.BESpec;
import com.teapotrecords.textadventures.logic.expr.NE;
import com.teapotrecords.textadventures.logic.expr.NEFlag;
import com.teapotrecords.textadventures.logic.expr.NESpec;
import com.teapotrecords.textadventures.parser.Parser;

public class Adventure {
  
  private Gui G;
  private Parser P;
  private Player me;
  private CP C;
  private String intro_text;
  private String title;
  private ArrayList<Location> locations = new ArrayList<Location>();
  private ArrayList<NEFlag> flags = new ArrayList<NEFlag>();
  private ArrayList<Item> items = new ArrayList<Item>();
  
  public String title() { return title; }
  public ArrayList<NEFlag> flags() { return flags; }
  public void addFlag(NEFlag f) { flags.add(f); }
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
    l1.addItem(new Item("FISHING ROD:ROD", "a fishing rod", "You see nothing special", 5, true, this));
    Item iNet = l1.addItem(new Item("FISHING NET:NET", "a fishing net", "You see nothing special", 5, true, this));
    Item iBucket = l1.addItem(new Item("BUCKET", "a bucket", "You see nothing special", 5, true, this));
    l1.addItem(new Item("ROWING BOAT:BOAT", "a rowing boat", "You see nothing special", 150, true, this));
    
    Item iPotPlant = new Item("POT-PLANT:POT PLANT:POT:PLANT", "a pot-plant in the doorway", "You don't really want to make eye-contact with this thing, but looking down, you notice 'LEVEL 1' engraved on the pot.", 1001, true, this);
    addSpecialMessage(1001, "As you reach towards it, the pot-plant's utter silence intimidates you, and you decide against touching it.");

    // Room 2;
    Location l2 = new Location("The Beach", "You are on a sandy beach, occasionally interrupted by clusters of smooth round rocks, especially to the South. Your beach hut is to the North.", this);
    
    Link l1l2 = new Link(l2, Link.DIR_SOUTH);
    l1.addLink(l1l2);

    // The pot plant puzzle.
    
    NEFlag F_POT_PLANT = new NEFlag(0, this);
    
    BE FC_POT_PLANT_0 = new BEComp(F_POT_PLANT, BEComp.EQUAL, NE.ZERO);
    
    C.addIntercept(l1, new short[] {CP.GO_SOUTH, CP.GO_OUT}, null, new IC(FC_POT_PLANT_0, new IA[] { 
        new IA(Intercept.PRINT, "A pot-plant suddenly appears, blocking your path."),
        new IA(Intercept.ADD_ITEM_HERE, iPotPlant),
        new IA(Intercept.FORBID_MOVE)}),this);
            
    BE FC_POT_PLANT_1 = new BEComp(F_POT_PLANT,BEComp.EQUAL, NE.ONE);
    
    C.addIntercept(l1,  new short[] {CP.GO_SOUTH, CP.GO_OUT},  null, new IC(FC_POT_PLANT_1, new IA[] {
        new IA(Intercept.PRINT, "The pot-plant stands in your way."),
        new IA(Intercept.PRINT_SEQUENCE, "It maintains a stony silence.:It does absolutely nothing. Menacingly.:It waits, unmoved, not even looking at you."),
        new IA(Intercept.FORBID_MOVE)}), this);
        
    C.addIntercept(l1, new short[] {CP.GO_SOUTH, CP.GO_OUT}, null, FC_POT_PLANT_0, Intercept.SET_FLAG, F_POT_PLANT, 1, this);
    
    C.addIntercept(l1, new short[] {CP.MOVE_ITEM}, iPotPlant, new IC(FC_POT_PLANT_1, new IA[] {
        new IA(Intercept.PRINT, "You cautiously prod the base, and the pot-plant is moved. (Deeply). The doorway is now available."),
        new IA(Intercept.CHANGE_ITEM_DESCRIPTION, iPotPlant, "a pot-plant beside the doorway"),
        new IA(Intercept.SET_FLAG, F_POT_PLANT, 2)}), this);
    
       
    l2.addLink(new Link(l1, Link.DIR_NORTH));
    l2.addLink(new Link(l1, Link.DIR_IN));
    
    // Location 3;
    Location l3 = new Location("The Beach", "You are on a sandy beach. A cluster of smooth rocks is to the South.", this);
    l2.addLink(new Link(l3, Link.DIR_SOUTH));
    l3.addLink(new Link(l2, Link.DIR_NORTH));
    
    // Location 4:

    // The Crab Puzzle
    
    NEFlag F_GOT_CRABS = new NEFlag(0, this);
    
    Location l4 = new Location("Rockpools", "You are standing gingerly on a smooth and slightly slippery rock, which together with a few others, forms a rockpool. "+
                               "The top of a mysterious box pokes out above the surface, guarded by a squadron of crabs, who "+
                               "snap their pincers slowly and meaningfully at you.:You are standing gingerly on a smooth and "+
                               "slightly slippery rock, which together with a few others, forms a rockpool. The top of a mysterious "+
                               "box pokes out above the surface.", F_GOT_CRABS, this);
    
    l3.addLink(new Link(l4, Link.DIR_SOUTH));
    l4.addLink(new Link(l3, Link.DIR_NORTH));
    
    Item iCrabs = new Item("CRABS", "", "They look angry.", 10, false, this);
    Item iBox = new Item("BOX:MYSTERIOUS BOX","","It is too mysterious to adequately describe.", 1001, false, this);

    l4.addItem(iCrabs);
    l4.addItem(iBox);
        
    C.addIntercept(l4, CP.DROP_ITEM, iBucket, new IC(BE.TRUE, new IA[] {
        new IA(Intercept.PRINT, "The bucket slips onto its side on the slippery rock."),
        new IA(Intercept.NO_EXTRA_ECHO)}),this);
    
    
    BE only_net = new BEComb(new BESpec(BESpec.F_CARRYING, iNet, this), BEComb.AND, new BEComp(new NESpec(NESpec.F_COUNT_ITEMS_CARRIED, this), BEComp.EQUAL, NE.ONE));
    BE net_plus = new BEComb(new BESpec(BESpec.F_CARRYING, iNet, this), BEComb.AND, new BEComp(new NESpec(NESpec.F_COUNT_ITEMS_CARRIED, this), BEComp.GREATER, NE.ONE));
    BE no_net = new BEInv(new BESpec(BESpec.F_CARRYING, iNet, this)); 
    
    BE crabs_dropable = new BEComb(new BESpec(BESpec.F_ITEM_PRESENT, iBucket, this), BEComb.AND, new BESpec(BESpec.F_PLAYER_NOT_IN_LOCATION, l4, this));
    BE crabs_undropable = new BEInv(crabs_dropable);
    
    Item iNetCrabs = new Item("NET OF CRABS:NET", "a fishing net bulging with angry crabs", "The net pulsates with wriggling crustaceans, and it takes all your strength to keep them under control.", 5, true, this);
    BE got_crabs = new BESpec(BESpec.F_CARRYING, iNetCrabs, this); 
    
    C.addIntercept(null, new short[] {CP.EXAMINE_ITEM, CP.PICK_UP_ITEM}, null, new IC(got_crabs, new IA[] {
        new IA(Intercept.PRINT, "As you try, the crabs wrestle free and escape from the fishing net."),
        new IA(Intercept.SET_FLAG, F_GOT_CRABS, 0),
        new IA(Intercept.FORBID_MOVE),
        new IA(Intercept.REMOVE_ITEM_PLAYER, iCrabs),
        new IA(Intercept.ADD_ITEM_TO_ROOM, l4, iCrabs),
        new IA(Intercept.REMOVE_ITEM_PLAYER, iNetCrabs),
        new IA(Intercept.ADD_ITEM_PLAYER, iNet)}),this);
    
    C.addIntercept(l4, new short[] {CP.PICK_UP_ITEM}, iCrabs, new IC[] {
      new IC(net_plus, new IA[] {
         new IA(Intercept.PRINT, "The crabs are too quick, and evade you. They snap their pincers mockingly."),
         new IA(Intercept.FORBID_MOVE)}),
      new IC(no_net, new IA[] {
          new IA(Intercept.PRINT, "With your bare hands? No, I don't think so."),
          new IA(Intercept.FORBID_MOVE)}),
      new IC(only_net, new IA[] {
          new IA(Intercept.NO_EXTRA_ECHO),
          new IA(Intercept.FORBID_MOVE),          
          new IA(Intercept.PRINT, "With admirable skill and concentration, you catch the menacing crabs in the net."),
          new IA(Intercept.SET_FLAG, F_GOT_CRABS, 1),
          new IA(Intercept.REMOVE_ITEM_PLAYER, iNet),
          new IA(Intercept.REMOVE_ITEM_HERE, iCrabs),
          new IA(Intercept.ADD_ITEM_PLAYER, iNetCrabs),
          new IA(Intercept.ADD_ITEM_PLAYER, iCrabs)})
    },this);
      
    Item iBucketCrabs = new Item("BUCKET OF CRABS:BUCKET","a bucket of angry crabs","It's a bucket of angry crabs. What more can I say?",15,true,this);
    
    C.addIntercept(null, new short[] {CP.DROP_ITEM}, iNetCrabs, new IC(BE.TRUE, new IA[] {
        new IA(Intercept.NO_EXTRA_ECHO),
        new IA(Intercept.FORBID_MOVE),
        new IA(Intercept.PRINT, "The crabs scuttle away."),
        new IA(Intercept.SET_FLAG, F_GOT_CRABS, 0),            
        new IA(Intercept.REMOVE_ITEM_PLAYER, iCrabs),
        new IA(Intercept.ADD_ITEM_TO_ROOM, l4, iCrabs),
        new IA(Intercept.REMOVE_ITEM_PLAYER, iNetCrabs),
        new IA(Intercept.ADD_ITEM_HERE, iNet)}), this);
    
    C.addIntercept(null, new short[] {CP.DROP_ITEM}, iCrabs, new IC[] {
        new IC(crabs_undropable, new IA[] {
            new IA(Intercept.NO_EXTRA_ECHO),
            new IA(Intercept.FORBID_MOVE),
            new IA(Intercept.PRINT, "The crabs scuttle away."),
            new IA(Intercept.SET_FLAG, F_GOT_CRABS, 0),            
            new IA(Intercept.REMOVE_ITEM_PLAYER, iCrabs),
            new IA(Intercept.ADD_ITEM_TO_ROOM, l4, iCrabs),
            new IA(Intercept.REMOVE_ITEM_PLAYER, iNetCrabs),
            new IA(Intercept.ADD_ITEM_PLAYER, iNet)}),
        new IC(crabs_dropable, new IA[] {
            new IA(Intercept.NO_EXTRA_ECHO),
            new IA(Intercept.FORBID_MOVE),
            new IA(Intercept.PRINT, "You drop the crabs into the bucket. They try to climb out, but it's too steep."),
            new IA(Intercept.ADD_ITEM_HERE, iBucketCrabs),
            new IA(Intercept.REMOVE_ITEM_PLAYER, iCrabs),
            new IA(Intercept.REMOVE_ITEM_PLAYER, iNetCrabs),
            new IA(Intercept.ADD_ITEM_PLAYER, iNet),
            new IA(Intercept.REMOVE_ITEM_HERE, iBucket)})}, this);
        
    //  Can we get to the box?
    
    BE get_to_box = new BEComb(
        new BEComp(F_GOT_CRABS, BEComp.EQUAL, NE.ONE),
        BEComb.AND,
        new BESpec(BESpec.F_NOT_CARRYING, iNetCrabs, this));
    
    C.addIntercept(l4, new short[] {CP.PICK_UP_ITEM, CP.OPEN_ITEM, CP.MOVE_ITEM, CP.EXAMINE_ITEM}, iBox, new IC(new BEInv(get_to_box), new IA[] {
        new IA(Intercept.FORBID_MOVE),
        new IA(Intercept.PRINT, "The snappy crabs stop you getting close.")}),this);
        
    C.addIntercept(l4, new short[] {CP.PICK_UP_ITEM, CP.MOVE_ITEM}, iBox, new IC(get_to_box, new IA[] {
        new IA(Intercept.FORBID_MOVE),
        new IA(Intercept.PRINT, "The box is well and truly stuck in the rock; only the lid is visible.")}),this);
    
    NEFlag got_letter = new NEFlag(0, this);
    Item iLetter = new Item("LETTER", "a small letter", "It looks worth reading.",0, true, this);
    
    C.addIntercept(l4, new short[] {CP.OPEN_ITEM}, iBox, new IC(
        new BEComb(get_to_box, BEComb.AND, new BEComp(got_letter, BEComp.EQUAL, NE.ONE)),
        new IA[] {
          new IA(Intercept.PRINT, "The mysterious box is mysteriously empty.")}),this);
    
    C.addIntercept(l4, new short[] {CP.OPEN_ITEM}, iBox, new IC(
        new BEComb(get_to_box, BEComb.AND, new BEComp(got_letter, BEComp.EQUAL, NE.ZERO)),
        new IA[] {
          new IA(Intercept.FORBID_MOVE),
          new IA(Intercept.PRINT, "You prise the box open; a letter springs into your hand."),
          new IA(Intercept.ADD_ITEM_PLAYER, iLetter),
          new IA(Intercept.SET_FLAG, got_letter, 1)}), this);
    
          
    
    C.addIntercept(null, new short[] {CP.READ_ITEM}, iLetter, new IC(new BESpec(BESpec.F_CARRYING, iLetter, this),
        new IA[] {
          new IA(Intercept.PRINT, "The letter reads as follows, in large, spidery, black, smudgy scrawl...\n\n "+
           "? ")
        }),this);
    
    // Init.
    
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
