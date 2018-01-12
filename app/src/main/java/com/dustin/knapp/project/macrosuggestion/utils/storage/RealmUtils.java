package com.dustin.knapp.project.macrosuggestion.utils.storage;

import android.util.Log;
import com.dustin.knapp.project.macrosuggestion.models.FoodEntry;
import com.dustin.knapp.project.macrosuggestion.models.NutritionDataGoal;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.models.PendingWaterData;
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import com.dustin.knapp.project.macrosuggestion.models.WaterDataGoal;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dknapp on 5/8/17
 */
public class RealmUtils {

  public static void saveUserObject(UserObject userObject) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    realm.copyToRealmOrUpdate(userObject);
    realm.commitTransaction();
  }

  public static void updateCurrentDayPendingNutritionData(PendingNutritionData pendingNutritionData) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    realm.copyToRealmOrUpdate(pendingNutritionData);
    realm.commitTransaction();
  }

  public static PendingNutritionData getCurrentDayNutritionObject() {
    Realm realm = Realm.getDefaultInstance();

    RealmQuery<PendingNutritionData> query = realm.where(PendingNutritionData.class);

    query.contains("currentDate", DateUtils.getCurrentDateString());
    Log.d("Current Day", " todays date : " + DateUtils.getCurrentDateString());

    // Execute the query:
    RealmResults<PendingNutritionData> result1 = query.findAll();

    if (result1.size() == 0) {
      return null;
    } else {
      return result1.get(0);
    }
  }

  public static PendingNutritionData getDaysNutritionData(String date) {
    Realm realm = Realm.getDefaultInstance();

    RealmQuery<PendingNutritionData> query = realm.where(PendingNutritionData.class);

    query.contains("currentDate", date);

    // Execute the query:
    RealmResults<PendingNutritionData> result1 = query.findAll();

    if (result1.size() == 0) {
      return null;
    } else {
      return result1.get(0);
    }
  }

  public static PendingWaterData getDaysWaterData(String date) {
    Realm realm = Realm.getDefaultInstance();

    RealmQuery<PendingWaterData> query = realm.where(PendingWaterData.class);

    query.contains("currentDate", date);

    // Execute the query:
    RealmResults<PendingWaterData> result1 = query.findAll();

    if (result1.size() == 0) {
      return null;
    } else {
      return result1.get(0);
    }
  }

  public static UserObject getCurrentUserObject(String email) {
    Realm realm = Realm.getDefaultInstance();

    RealmQuery<UserObject> query = realm.where(UserObject.class);

    query.contains("uniqueUserId", email);

    // Execute the query:
    RealmResults<UserObject> result1 = query.findAll();

    if (result1.size() == 0) {
      return null;
    } else {
      return result1.get(0);
    }
  }

  public static void saveFoodEntry(FoodEntry foodEntry) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    realm.copyToRealm(foodEntry);
    realm.commitTransaction();
  }

  public static List<FoodEntry> getCurrentDayFoodEntries() {
    List<FoodEntry> foodEntryList = new ArrayList<>();
    Realm realm = Realm.getDefaultInstance();

    RealmQuery<FoodEntry> query = realm.where(FoodEntry.class);

    for (FoodEntry entry : query.findAll()) {
      if (entry.getCurrentDate().equals(DateUtils.getCurrentDateString())) {
        foodEntryList.add(entry);
      }
    }

    return foodEntryList;
  }

  public static List<FoodEntry> getPastDayFoodEntries(String day) {
    List<FoodEntry> foodEntryList = new ArrayList<>();
    Realm realm = Realm.getDefaultInstance();

    RealmQuery<FoodEntry> query = realm.where(FoodEntry.class);

    for (FoodEntry entry : query.findAll()) {
      if (entry.getCurrentDate().equals(day)) {
        foodEntryList.add(entry);
      }
    }

    return foodEntryList;
  }

  public static void storePastFoodEntries(List<FoodEntry> entries) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    realm.copyToRealm(entries);
    realm.commitTransaction();
  }

  //todo save set goal then if  == null create new daily object
  //todo see pending nutritional data object
  public static void updateCurrentDayPendingWaterData(PendingWaterData pendingWaterData) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    realm.copyToRealmOrUpdate(pendingWaterData);
    realm.commitTransaction();
  }

  public static PendingWaterData getCurrentDayPendingWaterData() {
    Realm realm = Realm.getDefaultInstance();

    RealmQuery<PendingWaterData> query = realm.where(PendingWaterData.class);

    query.contains("currentDate", DateUtils.getCurrentDateString());

    // Execute the query:
    RealmResults<PendingWaterData> result1 = query.findAll();

    if (result1.size() == 0) {
      return null;
    } else {
      return result1.get(0);
    }
  }
}
