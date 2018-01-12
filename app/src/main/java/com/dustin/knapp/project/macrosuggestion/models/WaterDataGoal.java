package com.dustin.knapp.project.macrosuggestion.models;

/**
 * Created by dknapp on 5/1/17
 */
public class WaterDataGoal {

  private float currentWater;

  private float goalWater;

  public float getGoalWater() {
    return goalWater;
  }

  public void setGoalWater(float goalWater) {
    this.goalWater = goalWater;
  }

  public float getCurrentWater() {

    return currentWater;
  }

  public void setCurrentWater(float currentWater) {
    this.currentWater = currentWater;
  }

}
