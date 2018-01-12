package com.dustin.knapp.project.macrosuggestion.models;

/**
 * Created by dknapp on 4/28/17
 */
public class BaseNutrition {
  public String name;
  public float calories;
  public float protein;
  public float fats;
  public float carbs;

  public BaseNutrition(String name, float calories, float protein, float fats, float carbs) {
    this.name = name;
    this.calories = calories;
    this.protein = protein;
    this.fats = fats;
    this.carbs = carbs;
  }
}
