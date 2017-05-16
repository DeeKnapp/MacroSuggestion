package com.dustin.knapp.project.macrosuggestion.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dknapp on 5/8/17
 */
public class FoodEntry extends RealmObject {

  private String currentDate;
  private float calories;
  private float fats;
  private float carbs;
  private float protein;
  private String foodName;
  private int mealEntryType;

  public int getMealEntryType() {
    return mealEntryType;
  }

  public void setMealEntryType(int mealEntryType) {
    this.mealEntryType = mealEntryType;
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
