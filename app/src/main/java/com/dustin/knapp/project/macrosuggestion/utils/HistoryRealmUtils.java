package com.dustin.knapp.project.macrosuggestion.utils;

import android.content.Context;
import com.dustin.knapp.project.macrosuggestion.utils.storage.SharedPreferencesUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dknapp on 11/27/17.
 */

public class HistoryRealmUtils {

  private SharedPreferencesUtil sharedPreferencesUtil;
  private String email;
  private SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");

  public HistoryRealmUtils(Context context) {
    sharedPreferencesUtil = new SharedPreferencesUtil(context);
    email = sharedPreferencesUtil.getEnrolledEmail();
  }

  //todo use firebase utils here instead of realm
  public boolean userCompletedMacrosGoalForGivenDay(Date date) {
    //PendingNutritionData pendingNutritionData = realmUtils.getDaysNutritionData(format1.format(date).toString());
    //return pendingNutritionData != null
    //    && pendingNutritionData.getGoalCarb() <= pendingNutritionData.getCurrentCarb()
    //    && pendingNutritionData.getGoalFat() <= pendingNutritionData.getCurrentFat()
    //    && pendingNutritionData.getGoalProtein() <= pendingNutritionData.getCurrentProtein();
    return false;
  }

  public boolean userCompletedCaloriesGoalForGivenDay(Date date) {
    //PendingNutritionData pendingNutritionData = realmUtils.getDaysNutritionData(format1.format(date).toString());
    //
    //return pendingNutritionData != null && pendingNutritionData.getGoalCalorie() <= pendingNutritionData.getCurrentCalories();
    return true;
  }

  public boolean userCompletedWaterGoalForGivenDay(Date date) {
    //PendingWaterData pendingWaterData = realmUtils.getDaysWaterData(format1.format(date).toString());
    //
    //return pendingWaterData != null && pendingWaterData.getGoalWater() <= pendingWaterData.getCurrentWater();
    return false;
  }
}
