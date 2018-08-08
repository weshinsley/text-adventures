package com.teapotrecords.textadventures.logic.expr;

import com.teapotrecords.textadventures.Adventure;
import com.teapotrecords.textadventures.logic.Item;
import com.teapotrecords.textadventures.logic.Location;

public class BESpec extends BE {
  public static final byte F_PLAYER_IN_LOCATION = 1;
  public static final byte F_ITEM_IN_LOCATION = 2;
  public static final byte F_ITEM_PRESENT = 3;
  public static final byte F_CARRYING_ITEM = 4;
  
  Adventure A;
  byte func;
  Object arg1, arg2;
  
  public BESpec(byte _func, Object a1, Object a2, Adventure _A) { 
    func = _func;
    arg1 = a1;
    arg2 = a2;
    A = _A;
  }
  public boolean eval() {
    if (func==F_PLAYER_IN_LOCATION) {
      return (A.me().getLocation() == (Location) arg1);
    } else if (func==F_ITEM_IN_LOCATION) {
      return (((Location) arg1).getItems().indexOf((Item) arg2)>=0);
    } else if (func==F_ITEM_PRESENT) {
      return (A.me().getLocation().getItems().indexOf((Item) arg1)>=0);
    } else if (func==F_CARRYING_ITEM) {
      return (A.me().carrying().indexOf((Item) arg1)>=0);
    } else return false;
  }
}
