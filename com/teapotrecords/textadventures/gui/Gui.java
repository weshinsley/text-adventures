package com.teapotrecords.textadventures.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.teapotrecords.textadventures.Adventure;


public class Gui extends JFrame {
  private JEditorPane je_main = new JEditorPane();
  private FontMetrics fm_main;
  private JTextField jt_entry = new JTextField();
  private JLabel jl_arrow = new JLabel("> ");
  private Adventure A;
  private EventHandler eh = new EventHandler();
  private StringBuffer snippets = new StringBuffer();
  
  
  public void echoText(String s, String col) {
    int wid = je_main.getWidth();
    if (fm_main.stringWidth(s) > wid) {
      String[] bits = s.split("\\s+");
      StringBuffer sb = new StringBuffer();
      int i = 0;
      int last=-1;
      boolean done = false;
      while (!done) {
        if (i == bits.length) {
          done = true;
          if (last != i) echoText(sb.toString(), col);
        } else {
           if (fm_main.stringWidth(sb.toString() + " " + bits[i]) < wid) {
             sb.append(" "+bits[i]);
             i++;
           } else {
             echoText(sb.toString(), col);
             sb = new StringBuffer();
             last=i;
           }
        }
      }
    } else {
      snippets.append("<span style=\"font-family:Courier New;font-size:14;color:"+col+"\">"+s+"<br/></span>\n");
      snippets = snippets.delete(0,snippets.indexOf("\n")+1);
      je_main.setText(snippets.toString());
    }
  }
  
  public Gui(Adventure A) {
    super();
    this.A = A;
    setSize(new Dimension(600,700));
    setTitle(A.title());
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(je_main,BorderLayout.CENTER);
    JPanel jp_bottomPanel = new JPanel(new FlowLayout());
    jp_bottomPanel.add(jl_arrow);
    jp_bottomPanel.add(jt_entry);
    Font courier = new Font("Courier New", Font.BOLD, 16);
    
    jt_entry.setFont(courier);
    jl_arrow.setFont(courier);
    jl_arrow.setForeground(Color.BLUE);
    jt_entry.setForeground(Color.BLUE);
    jt_entry.setPreferredSize(new Dimension(550,30));
    jt_entry.addKeyListener(eh);
    je_main.setFocusable(false);
    je_main.setFont(courier);
    je_main.setContentType("text/html");
    je_main.setEditable(false);
    jl_arrow.setFocusable(false);
    getContentPane().add(jp_bottomPanel,  BorderLayout.SOUTH);
    setVisible(true);
    
    fm_main = je_main.getFontMetrics(new Font("Courier New", Font.PLAIN, 14));
    int hei = fm_main.getHeight();
    for (int i=0; i<(je_main.getHeight()-40)/hei; i++) {
      snippets.append("<span style=\"font-family:Courier New;font-size:14;color:#0000ff\">&nbsp;<br/></span>\n");
    }
    je_main.setText(snippets.toString());

    
    
  }
  
  class EventHandler implements KeyListener {

    
        @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {
      Object obj = e.getSource();
      if (obj == jt_entry) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
          echoText("> "+jt_entry.getText(),"#0000FF");
          A.P().process(jt_entry.getText());
          
          jt_entry.setText("");
        }
        e.setKeyChar((""+e.getKeyChar()).toUpperCase().charAt(0));
      }
      
    }
        
  }

}
