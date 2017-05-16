package com.dustin.knapp.project.macrosuggestion.models;

import java.io.Serializable;

/**
 * Created by dknapp on 4/11/17
 */
public class ExcessMacros implements Serializable {

  public int maxCalories = 250;
  public int maxCarbs = 20;
  public int maxFat = 20;
  public int maxProtein = 100;
  public int minCalories = 0;
  public int minCarbs = 0;
  public int minFat = 5;
  public int minProtein = 0;
  public int number = 10;
  public int offset = 0;
  public boolean random = false;
}
