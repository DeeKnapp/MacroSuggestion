package com.dustin.knapp.project.macrosuggestion.models;

/**
 * Created by dknapp on 4/26/17
 */
public class NutritionDataGoal {
  private float goalCalorie;
  private float goalFat;
  private float goalProtein;
  private float goalCarb;

  public NutritionDataGoal() {
  }

  public float getGoalCalorie() {
    return goalCalorie;
  }

  public void setGoalCalorie(float goalCalorie) {
    this.goalCalorie = goalCalorie;
  }

  public float getGoalCarb() {

    return goalCarb;
  }

  public void setGoalCarb(float goalCarb) {
    this.goalCarb = goalCarb;
  }

  public float getGoalProtein() {

    return goalProtein;
  }

  public void setGoalProtein(float goalProtein) {
    this.goalProtein = goalProtein;
  }

  public float getGoalFat() {
    return goalFat;
  }

  public void setGoalFat(float goalFat) {
    this.goalFat = goalFat;
  }
}
