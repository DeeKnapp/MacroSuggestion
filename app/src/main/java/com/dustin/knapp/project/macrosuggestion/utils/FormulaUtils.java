package com.dustin.knapp.project.macrosuggestion.utils;

/**
 * Created by dknapp on 5/31/17
 */
public class FormulaUtils {

  public static double sedentaryCaloriesBurnedMen(int heightInches, float weightPounds,
      int yearsOld) {

    double value = 66 + (6.23 * weightPounds) + (12.7 * heightInches) - (6.8 * yearsOld);

    return Math.round(value * 100.0) / 100.0;
  }

  public static double sedentaryCaloriesBurnedWomen(int heightInches, float weightPounds,
      int yearsOld) {

    double value = 655 + (4.35 * weightPounds) + (4.7 * heightInches) - (4.7 * yearsOld);

    return Math.round(value * 100.0) / 100.0;
  }

  //estimates are based on one hour activity at rate of 460 calories burned
  //todo calculate sedentary calories with these calculatiosn -- that is prerequisite for all other plans anyway
  public static double estimateCaloriesBurnedDailyMen(int heightInches, float weightPounds,
      int yearsOld, int daysOfActivity) {

    double value = sedentaryCaloriesBurnedMen(heightInches, weightPounds, yearsOld);

    value += ((weightPounds / 175) * 460) * daysOfActivity;

    return Math.round(value * 100.0) / 100.0;
  }

  //estimates are based on one hour activity at rate of 370 calories burned
  public static double estimateCaloriesBurnedDailyWomen(int heightInches, float weightPounds,
      int yearsOld, int daysOfActivity) {

    double value = sedentaryCaloriesBurnedWomen(heightInches, weightPounds, yearsOld);

    value = ((weightPounds / 140) * 370) * daysOfActivity;

    return Math.round(value * 100.0) / 100.0;
  }
}
