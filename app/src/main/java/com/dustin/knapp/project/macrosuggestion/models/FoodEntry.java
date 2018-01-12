package com.dustin.knapp.project.macrosuggestion.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by dknapp on 5/8/17
 */
@JsonIgnoreProperties(ignoreUnknown = true) public class FoodEntry {

  private String currentDate;
  private String timeStamp;
  private float calories;
  private float fats;
  private float carbs;
  private float protein;
  private String foodName;
  private String uid;

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getFoodName() {

    return foodName;
  }

  public void setFoodName(String foodName) {
    this.foodName = foodName;
  }

  public float getProtein() {

    return protein;
  }

  public void setProtein(float protein) {
    this.protein = protein;
  }

  public float getCarbs() {

    return carbs;
  }

  public void setCarbs(float carbs) {
    this.carbs = carbs;
  }

  public float getFats() {

    return fats;
  }

  public void setFats(float fats) {
    this.fats = fats;
  }

  public float getCalories() {

    return calories;
  }

  public void setCalories(float calories) {
    this.calories = calories;
  }

  public String getCurrentDate() {

    return currentDate;
  }

  public void setCurrentDate(String currentDate) {
    this.currentDate = currentDate;
  }
}
