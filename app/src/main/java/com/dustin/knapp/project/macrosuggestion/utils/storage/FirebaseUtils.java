package com.dustin.knapp.project.macrosuggestion.utils.storage;

import com.dustin.knapp.project.macrosuggestion.Constants;
import com.dustin.knapp.project.macrosuggestion.models.FoodEntry;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by dknapp on 12/10/17.
 */

public final class FirebaseUtils {

  public static void saveFoodEntryToFirebase(FoodEntry foodEntry, String uniqueUserId) {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    DatabaseReference databaseReference =
        firebaseDatabase.getReference().child(Constants.BASE_DATABASE_REFERENCE).child(Constants.USER_RECORDS_DATABASE_REFERENCE);

    foodEntry.setUid(databaseReference.child(uniqueUserId).push().getKey());
    databaseReference.child(uniqueUserId).child(foodEntry.getUid()).setValue(foodEntry);
  }

  public static void removeFoodEntryToFirebase(FoodEntry foodEntry, String uniqueUserId) {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    DatabaseReference databaseReference =
        firebaseDatabase.getReference().child(Constants.BASE_DATABASE_REFERENCE).child(Constants.USER_RECORDS_DATABASE_REFERENCE);

    databaseReference.child(uniqueUserId).child(foodEntry.getUid()).removeValue();
  }
}
