package com.dustin.knapp.project.macrosuggestion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dustin.knapp.project.macrosuggestion.Constants;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.NutritionDataGoal;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.models.PendingWaterData;
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.dustin.knapp.project.macrosuggestion.utils.RealmUtils;
import com.google.firebase.auth.FirebaseAuth;
import javax.inject.Inject;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by dknapp on 5/8/17
 */
public class OnBoardingActivityStep3 extends BaseActivity {

  @BindView(R.id.tvGoalPerWeek) TextView tvGoalPerWeek;
  @BindView(R.id.tvGoalTimeConstraint) TextView tvGoalTimeConstraint;
  @BindView(R.id.submitButton) Button btnSubmit;

  @Inject public Observable<UserObject> pendingUserObjectObservable;
  @Inject public Observer<PendingNutritionData> pendingNutritionalObserver;
  @Inject public Observer<PendingWaterData> pendingWaterObserver;

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

    firebaseAuth = FirebaseAuth.getInstance();
    firebaseAuth.createUserWithEmailAndPassword(currentUserObject.getEmail(),
        currentUserObject.getPendingPassword());

    NutritionDataGoal nutritionDataGoal = new NutritionDataGoal();

    nutritionDataGoal.setGoalCalorie(2000);
    nutritionDataGoal.setGoalFat(1000);
    nutritionDataGoal.setGoalCarb(800);
    nutritionDataGoal.setGoalProtein(750);

    nutritionDataGoal.setEmail(currentUserObject.getEmail());

    RealmUtils.saveNutritionDataGoal(nutritionDataGoal);

    RealmUtils.saveUserObject(currentUserObject);

    PendingWaterData pendingWaterData = new PendingWaterData();
    pendingWaterData.setCurrentDate(DateUtils.getCurrentDate());
    pendingWaterData.setGoalWater(64);
    pendingWaterData.setCurrentWater(0);

    pendingWaterObserver.onNext(pendingWaterData);

    RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);

    sharedPreferencesUtil.storeUserIsEnrolled(true);
    sharedPreferencesUtil.storeEnrolledEmail(currentUserObject.getEmail());

    Intent intent = new Intent(this, LandingPageActivity.class);
    startActivity(intent);
    finish();
  }
}

