package com.dustin.knapp.project.macrosuggestion.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dknapp on 5/11/17
 */
public class UserObject extends RealmObject {

  @PrimaryKey private String email;

  private String name;
  private float currentWeight;
  private float targetWeight;
  private int heightInFeet;
  private int heightInInches;

  //0 - Lose Weight / 1 - Gain Weight
  private int goalType;

  //2 - Light / 3 - Moderate / 4 - Intense

  public int getUserActivityLevel() {
    return userActivityLevel;
  }

  public void setUserActivityLevel(int userActivityLevel) {
    this.userActivityLevel = userActivityLevel;
  }

  private int userActivityLevel;

  public int getGoalType() {
    return goalType;
  }

  public void setGoalType(int goalType) {
    this.goalType = goalType;
  }

  public int getHeightInInches() {

    return heightInInches;
  }

  public void setHeightInInches(int heightInInches) {
    this.heightInInches = heightInInches;
  }

  public int getHeightInFeet() {

    return heightInFeet;
  }

  public void setHeightInFeet(int heightInFeet) {
    this.heightInFeet = heightInFeet;
  }

  public float getTargetWeight() {

    return targetWeight;
  }

  public void setTargetWeight(float targetWeight) {
    this.targetWeight = targetWeight;
  }

  public float getCurrentWeight() {

    return currentWeight;
  }

  public void setCurrentWeight(float currentWeight) {
    this.currentWeight = currentWeight;
  }

  public String getEmail() {

    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {

    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
