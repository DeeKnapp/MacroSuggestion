package com.dustin.knapp.project.macrosuggestion.models.recipe;

/**
 * Created by dknapp on 12/12/17.
 */

public class Length {
  private String unit;

  private String number;

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  @Override public String toString() {
    return "ClassPojo [unit = " + unit + ", number = " + number + "]";
  }
}
