package com.teapotrecords.textadventures.logic;

import com.teapotrecords.textadventures.Adventure;

public class BuiltinFlag extends Flag {
  int type;
  Object obj1;
  Object obj2;
  
  public static final int F_PLAYER_IN_LOCATION = 1;
  public static final int F_ITEM_IN_LOCATION = 2;
  public static final int F_ITEM_PRESENT = 3;
  public static final int F_CARRYING_ITEM = 4;
  public static final int F_COUNT_ITEMS_CARRIED = 5;
    
  public int eval(Adventure A) {
    if (type==F_PLAYER_IN_LOCATION) {
      return (A.me().location == (Location) obj1)?1:0;
    } else if (type==F_ITEM_IN_LOCATION) {
      return (((Location)obj1).getItems().indexOf((Item) obj2)>=0)?1:0;
    } else if (type==F_ITEM_PRESENT) {
      return (A.me().location.getItems().indexOf((Item) obj1)>=0)?1:0;
    } else if (type==F_CARRYING_ITEM) {
      return (A.me().carrying().indexOf((Item) obj1)>=0)?1:0;
    } else if (type==F_COUNT_ITEMS_CARRIED) {
      return A.me().carrying().size();
    }
    return value;
  }
  public void setValue(int v) { value = v;}
  
  private BuiltinFlag(int t, Object target1, Object target2) {
    super(-1);
    type = t;
    obj1 = target1;
    obj2 = target2;
  }
  
}
