package com.dustin.knapp.project.macrosuggestion.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dknapp on 4/26/17
 */
public class PendingNutritionData extends RealmObject {
  @PrimaryKey private String currentDate;
  private float currentCalories;
  private float currentFat;
  private float currentProtein;
  private float currentCarb;
  private float goalCalorie;
  private float goalFat;
  private float goalProtein;
  private float goalCarb;

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

  public float getGoalCalorie() {

    return goalCalorie;
  }

  public void setGoalCalorie(float goalCalorie) {
    this.goalCalorie = goalCalorie;
  }

  public PendingNutritionData() {
  }

  public String getCurrentDate() {
    return currentDate;
  }

  public void setCurrentDate(String currentDate) {
    this.currentDate = currentDate;
  }

  public float getCurrentCarb() {
    return currentCarb;
  }

  public void setCurrentCarb(float currentCarb) {
    this.currentCarb = currentCarb;
  }

  public float getCurrentProtein() {

    return currentProtein;
  }

  public void setCurrentProtein(float currentProtein) {
    this.currentProtein = currentProtein;
  }

  public float getCurrentFat() {

    return currentFat;
  }

  public void setCurrentFat(float currentFat) {
    this.currentFat = currentFat;
  }

  public float getCurrentCalories() {
    return currentCalories;
  }

  public void setCurrentCalories(float currentCalories) {
    this.currentCalories = currentCalories;
  }
}
