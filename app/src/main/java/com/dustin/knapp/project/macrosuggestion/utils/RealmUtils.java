package com.dustin.knapp.project.macrosuggestion.utils;

import com.dustin.knapp.project.macrosuggestion.models.FoodEntry;
import com.dustin.knapp.project.macrosuggestion.models.NutritionDataGoal;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dknapp on 5/8/17
 */
public class RealmUtils {

  public static void saveNutritionDataGoal(NutritionDataGoal nutritionDataGoal) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    realm.copyToRealmOrUpdate(nutritionDataGoal);
    realm.commitTransaction();
  }

  public static void saveUserObject(UserObject userObject) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    realm.copyToRealmOrUpdate(userObject);
    realm.commitTransaction();
  }

  public static void updateCurrentDayPendingNutritionData(
      PendingNutritionData pendingNutritionData) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    realm.copyToRealmOrUpdate(pendingNutritionData);
    realm.commitTransaction();
  }

  public static PendingNutritionData getCurrentDayNutritionObject() {
    Realm realm = Realm.getDefaultInstance();

    RealmQuery<PendingNutritionData> query = realm.where(PendingNutritionData.class);

    query.contains("currentDate", DateUtils.getCurrentDate());

    // Execute the query:
    RealmResults<PendingNutritionData> result1 = query.findAll();

    if (result1.size() == 0) {
      return null;
    } else {
      return result1.get(0);
    }
  }

  public static NutritionDataGoal getNutrtionDataGoal(String enrolledEmail) {
    Realm realm = Realm.getDefaultInstance();

    RealmQuery<NutritionDataGoal> query = realm.where(NutritionDataGoal.class);

    query.contains("email", enrolledEmail);

    // Execute the query:
    RealmResults<NutritionDataGoal> result1 = query.findAll();

    return result1.get(0);
  }

  public static void addFoodEntryToCurrentDay(FoodEntry foodEntry) {
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
      if (entry.getCurrentDate().equals(DateUtils.getCurrentDate())) {
        foodEntryList.add(entry);
      }
    }

    return foodEntryList;
  }
}
