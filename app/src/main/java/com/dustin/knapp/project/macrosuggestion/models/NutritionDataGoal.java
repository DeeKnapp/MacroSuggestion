package com.dustin.knapp.project.macrosuggestion.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dknapp on 4/26/17
 */
public class NutritionDataGoal extends RealmObject {
  @PrimaryKey private String email;
  private float goalCalorie;
  private float goalFat;
  private float goalProtein;
  private float goalCarb;

  public NutritionDataGoal() {
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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
