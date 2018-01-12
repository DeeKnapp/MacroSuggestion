package com.dustin.knapp.project.macrosuggestion.models.recipe;

import com.google.gson.annotations.Expose;

/**
 * Created by dknapp on 12/12/17.
 */

public class AnalyzedInstructions {
  @Expose private String name;

  @Expose private Steps[] steps;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Steps[] getSteps() {
    return steps;
  }

  public void setSteps(Steps[] steps) {
    this.steps = steps;
  }

  @Override public String toString() {
    return "ClassPojo [name = " + name + ", steps = " + steps + "]";
  }
}
