package com.dustin.knapp.project.macrosuggestion.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtil {

  private Context context;

  private static final String USER_IS_ENROLLED = "User is enrolled";
  private static final String ENROLLED_EMAIL = "Enrolled Email";
  private static final String SHOULD_SHOW_CALORIE_ANIMATION = "Should show calorie animation";
  private static final String SHOULD_SHOW_WATER_ANIMATION = "Should show water animation";

  public SharedPreferencesUtil(Context context) {
    this.context = context;
  }

  public void storeShouldShowCalorieAnimation(boolean shouldShowCalorieAnimation) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(SHOULD_SHOW_CALORIE_ANIMATION, shouldShowCalorieAnimation);
    editor.apply();
  }

  public boolean shouldShouldCalorieAnimation() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getBoolean(SHOULD_SHOW_CALORIE_ANIMATION, true);
  }

  public void storeShouldShowWaterAnimation(boolean shouldShowWaterAnimation) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(SHOULD_SHOW_WATER_ANIMATION, shouldShowWaterAnimation);
    editor.apply();
  }

  public boolean shouldShowWaterAnimation() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getBoolean(SHOULD_SHOW_WATER_ANIMATION, true);
  }

  public void storeUserIsEnrolled(boolean userIsEnrolled) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(USER_IS_ENROLLED, userIsEnrolled);
    editor.apply();
  }

  public boolean getUserIsEnrolled() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getBoolean(USER_IS_ENROLLED, false);
  }

  public void clearUserIsEnrolled() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.remove(USER_IS_ENROLLED);
    editor.apply();
  }

  public void storeEnrolledEmail(String email) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(ENROLLED_EMAIL, email);
    editor.apply();
  }

  public String getEnrolledEmail() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getString(ENROLLED_EMAIL, "none");
  }

  public void clearEnrolledEmail() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.remove(ENROLLED_EMAIL);
    editor.apply();
  }
}
