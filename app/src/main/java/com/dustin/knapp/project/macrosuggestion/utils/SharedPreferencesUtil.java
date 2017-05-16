package com.dustin.knapp.project.macrosuggestion.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

public class SharedPreferencesUtil {

  Context context;

  private static final String USER_IS_ENROLLED = "User is enrolled";
  private static final String ENROLLED_EMAIL = "Enrolled Email";

  public SharedPreferencesUtil(Context context) {
    this.context = context;
  }

  public void storeUserIsEnrolled(boolean userIsEnrolled) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(USER_IS_ENROLLED, userIsEnrolled);
    editor.commit();
  }

  public boolean getUserIsEnrolled() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getBoolean(USER_IS_ENROLLED, false);
  }

  public void clearUserIsEnrolled() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.remove(USER_IS_ENROLLED);
    editor.commit();
  }

  public void storeEnrolledEmail(String email) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(ENROLLED_EMAIL, email);
    editor.commit();
  }

  public String getEnrolledEmail() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getString(ENROLLED_EMAIL, "none");
  }

  public void clearEnrolledEmail() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.remove(ENROLLED_EMAIL);
    editor.commit();
  }
}
