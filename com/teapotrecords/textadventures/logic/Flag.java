package com.teapotrecords.textadventures.logic;

import com.teapotrecords.textadventures.Adventure;

public abstract class Flag {
  int value;
  abstract int eval(Adventure A);

  Flag(int v) {
    value = v;
  }
}