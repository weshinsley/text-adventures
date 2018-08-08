package com.teapotrecords.textadventures.logic.expr;

import com.teapotrecords.textadventures.Adventure;

public class NESpec extends NE {
  public static final byte F_COUNT_ITEMS_CARRIED = 1;
  
  byte func;
  Adventure A;
  
  public NESpec(byte _func, Adventure _A) {
    func = _func;
    A = _A;
  }
  
  public int eval() {
    if (func==F_COUNT_ITEMS_CARRIED) {
      return A.me().carrying().size(); 
    } else return Integer.MAX_VALUE;
  }
}
