package com.teapotrecords.textadventures;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.teapotrecords.textadventures.gui.Gui;
import com.teapotrecords.textadventures.logic.Command;
import com.teapotrecords.textadventures.logic.Link;
import com.teapotrecords.textadventures.logic.Location;
import com.teapotrecords.textadventures.logic.Player;
import com.teapotrecords.textadventures.parser.Parser;
public class Adventure {
  
  private Gui G;
  private Parser P;
  private Player me;
  private String intro_text;
  private ArrayList<Location> locations = new ArrayList<Location>();
  
  public Player me() { return me; }
  public Parser P() { return P; }
  public Gui G() { return G; }
  public void addLocation(Location L) { locations.add(L); }
  
  public Adventure() {
    P = new Parser(this);
    G = new Gui(this);
    me = new Player();
    
    intro_text = "Welcome to this text adventure! In here we need to put the plot, the background, instructions and all of that stuff...";
        
    G.echoText(intro_text,"#000000");
    G.echoText("", "#000000");
    
    // Load the adventure stuff here I guess.
    
    Location l1 = new Location("First room", "You are in the first room in the adventure.", this);
    Location l2 = new Location("Second room", "You are the in second room in the adventure.", this);
    l1.addLink(new Link(l2, Link.DIR_EAST));
    l2.addLink(new Link(l1, Link.DIR_WEST));
    me.setLocation(l1);
    System.out.println(locations.get(1).getName());
    
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
