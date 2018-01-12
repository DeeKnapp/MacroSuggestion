package com.dustin.knapp.project.macrosuggestion.models.recipe;

import com.google.gson.annotations.Expose;

/**
 * Created by dknapp on 12/12/17.
 */

public class Steps {
  @Expose private Ingredients[] ingredients;

  @Expose private Equipment[] equipment;

  @Expose private String number;

  @Expose private String step;

  public Ingredients[] getIngredients() {
    return ingredients;
  }

  public void setIngredients(Ingredients[] ingredients) {
    this.ingredients = ingredients;
  }

  public Equipment[] getEquipment() {
    return equipment;
  }

  public void setEquipment(Equipment[] equipment) {
    this.equipment = equipment;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getStep() {
    return step;
  }

  public void setStep(String step) {
    this.step = step;
  }

  @Override public String toString() {
    return "ClassPojo [ingredients = " + ingredients + ", equipment = " + equipment + ", number = " + number + ", step = " + step + "]";
  }
}
