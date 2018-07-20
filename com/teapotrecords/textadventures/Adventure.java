package com.teapotrecords.textadventures;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.teapotrecords.textadventures.gui.Gui;
import com.teapotrecords.textadventures.logic.Command;
import com.teapotrecords.textadventures.logic.Item;
import com.teapotrecords.textadventures.logic.Link;
import com.teapotrecords.textadventures.logic.Location;
import com.teapotrecords.textadventures.logic.Player;
import com.teapotrecords.textadventures.parser.Parser;
public class Adventure {
  
  private Gui G;
  private Parser P;
  private Player me;
  private String intro_text;
  private String title;
  private ArrayList<Location> locations = new ArrayList<Location>();
  
  public String title() { return title; }
  public Player me() { return me; }
  public Parser P() { return P; }
  public Gui G() { return G; }
  public void addLocation(Location L) { locations.add(L); }
  
  public Adventure() {
    title = "Luke's Adventure";
    
    P = new Parser(this);
    G = new Gui(this);
    me = new Player();
    intro_text = "Welcome to this text adventure! In here we need to put the plot, the background, instructions and all of that stuff...";
        
    G.echoText(intro_text,"#000000");
    G.echoText("", "#000000");
    
    // Load the adventure stuff here I guess.
    
    Location l1 = new Location("Beach Hut", "You are in your beach hut. It is wooden, warm and homely, with enough space to sleep, cook and store fishing equipment.", this);
    l1.addItem(new Item("FISHING ROD:ROD", "a fishing rod", "You see nothing special", 5));
    l1.addItem(new Item("FISHING NET:NET", "a fishing net", "You see nothing special", 5));
    l1.addItem(new Item("BUCKET", "a bucket", "You see nothing special", 5));
    l1.addItem(new Item("ROWING BOAT:BOAT", "a rowing boat", "You see nothing special", 150));
    
    Location l2 = new Location("The Beach", "You are on the beach. All you can see is sand, and gentle rippling waves. The door of your beach hut is to the North.", this);
    l1.addLink(new Link(l2, Link.DIR_SOUTH));
    l1.addLink(new Link(l2, Link.DIR_OUT));
    l2.addLink(new Link(l1, Link.DIR_NORTH));
    l2.addLink(new Link(l1, Link.DIR_IN));
    
    me.setLocation(l1);
    Command.roomInfo(this);
    
    
  }
  
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new Adventure();
      }   
    });
  }
}
