package com.dustin.knapp.project.macrosuggestion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dustin.knapp.project.macrosuggestion.Constants;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.NutritionDataGoal;
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import com.dustin.knapp.project.macrosuggestion.models.WaterDataGoal;
import com.dustin.knapp.project.macrosuggestion.utils.RealmUtils;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by dknapp on 5/8/17
 */
public class OnBoardingActivityStep3 extends BaseActivity {

  @BindView(R.id.tvGoalPerWeek) TextView tvGoalPerWeek;
  @BindView(R.id.tvGoalTimeConstraint) TextView tvGoalTimeConstraint;
  @BindView(R.id.submitButton) Button btnSubmit;

  @Inject public Observable<UserObject> pendingUserObjectObservable;

  FirebaseAuth firebaseAuth;

  private UserObject currentUserObject;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.on_boarding_step_3);

    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);

    ButterKnife.bind(this);

    pendingUserObjectObservable.subscribe(new Action1<UserObject>() {
      @Override public void call(UserObject userObject) {
        currentUserObject = userObject;
      }
    });
  }

  @OnClick(R.id.submitButton) void submitButtonClicked(View v) {

    if (currentUserObject.isSignedInWithGoogle()) {
      saveGoalsLocalAndRemote();
    } else if (currentUserObject.isRegisteringWithEmail()) {
      firebaseAuth = FirebaseAuth.getInstance();
      Task<AuthResult> resultTask =
          firebaseAuth.createUserWithEmailAndPassword(currentUserObject.getEmail(),
              currentUserObject.getPendingPassword());
      if (resultTask.isSuccessful()) {
        currentUserObject.setPendingPassword("");
        saveGoalsLocalAndRemote();
      }
    } else {
      //todo solve for this
    }
  }

  private void saveGoalsLocalAndRemote() {
    NutritionDataGoal nutritionDataGoal = new NutritionDataGoal();

    nutritionDataGoal.setGoalCalorie(2000);
    nutritionDataGoal.setGoalFat(1000);
    nutritionDataGoal.setGoalCarb(800);
    nutritionDataGoal.setGoalProtein(750);

    nutritionDataGoal.setEmail(currentUserObject.getEmail());

    RealmUtils.saveNutritionDataGoal(nutritionDataGoal);

    WaterDataGoal waterDataGoal = new WaterDataGoal();
    waterDataGoal.setEmail(currentUserObject.getEmail());
    waterDataGoal.setGoalWater(64);
    waterDataGoal.setCurrentWater(0);

    RealmUtils.saveWaterDataGoal(waterDataGoal);

    sharedPreferencesUtil.storeUserIsEnrolled(true);
    sharedPreferencesUtil.storeEnrolledEmail(currentUserObject.getEmail());
    sharedPreferencesUtil.storeEnrolledUniqueUserId(currentUserObject.getUniqueUserId());

    currentUserObject.setNutritionDataGoal(nutritionDataGoal);
    currentUserObject.setWaterDataGoal(waterDataGoal);

    RealmUtils.saveUserObject(currentUserObject);

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    firebaseDatabase.getReference()
        .child(Constants.BASE_DATABASE_REFERENCE)
        .child(Constants.USER_DATABASE_REFERENCE)
        .push()
        .setValue(currentUserObject);

    Intent intent = new Intent(this, LandingPageActivity.class);
    startActivity(intent);
    finish();
  }
}