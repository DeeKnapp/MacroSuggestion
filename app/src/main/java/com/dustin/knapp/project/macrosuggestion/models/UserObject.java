package com.dustin.knapp.project.macrosuggestion.models;

/**
 * Created by dknapp on 5/11/17
 */
public class UserObject {

  public UserObject() {
    //default constructor
  }

  public String getUniqueUserId() {
    return uniqueUserId;
  }

  public void setUniqueUserId(String uniqueUserId) {
    this.uniqueUserId = uniqueUserId;
  }

  private String uniqueUserId;

  private String email;
  private NutritionDataGoal nutritionDataGoal;
  private WaterDataGoal waterDataGoal;
  private String name;
  private float currentWeight;
  private float targetWeight;
  private int heightInFeet;
  private int heightInInches;
  private boolean signedInWithGoogle;
  private boolean registeringWithEmail;

  public boolean isRegisteringWithEmail() {
    return registeringWithEmail;
  }

  public void setRegisteringWithEmail(boolean registeringWithEmail) {
    this.registeringWithEmail = registeringWithEmail;
  }

  public boolean isSignedInWithGoogle() {
    return signedInWithGoogle;
  }

  public void setSignedInWithGoogle(boolean signedInWithGoogle) {
    this.signedInWithGoogle = signedInWithGoogle;
  }

  public WaterDataGoal getWaterDataGoal() {
    return waterDataGoal;
  }

  public void setWaterDataGoal(WaterDataGoal waterDataGoal) {
    this.waterDataGoal = waterDataGoal;
  }

  public NutritionDataGoal getNutritionDataGoal() {

    return nutritionDataGoal;
  }

  public void setNutritionDataGoal(NutritionDataGoal nutritionDataGoal) {
    this.nutritionDataGoal = nutritionDataGoal;
  }

  public String getPendingPassword() {
    return pendingPassword;
  }

  public void setPendingPassword(String pendingPassword) {
    this.pendingPassword = pendingPassword;
  }

  private String pendingPassword;

  //0 - Lose Weight / 1 - Gain Weight / 2 - Maintain Weight
  private int goalType;

  //3 - Not / 4 - Light / 5 - Moderate / 6 - Intense
  private int userActivityLevel;

  public int getUserActivityLevel() {
    return userActivityLevel;
  }

  public void setUserActivityLevel(int userActivityLevel) {
    this.userActivityLevel = userActivityLevel;
  }

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
