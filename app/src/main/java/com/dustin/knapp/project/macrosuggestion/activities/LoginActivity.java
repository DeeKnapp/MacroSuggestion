package com.dustin.knapp.project.macrosuggestion.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import android.content.Intent;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.dustin.knapp.project.macrosuggestion.utils.RealmUtils;
import javax.inject.Inject;
import rx.Observer;

/**
 * Created by dknapp on 5/10/17
 */
public class LoginActivity extends BaseActivity {

  @BindView(R.id.createAccountButton) Button createAccountButton;
  @BindView(R.id.createAccountButton2) Button createAccountButton2;
  @BindView(R.id.loginButton) Button loginButton;

  @Inject public Observer<PendingNutritionData> pendingNutritionalObserver;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_activity);
    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);

    unbinder = ButterKnife.bind(this);

    if (sharedPreferencesUtil.getUserIsEnrolled()) {
      Intent landingPageIntent = new Intent(this, LandingPageActivity.class);
      startActivity(landingPageIntent);
      finish();
    }
  }

  @OnClick(R.id.createAccountButton) void createAccountClicked(View v) {
    Intent createAccountIntent = new Intent(this, OnBoardingActivityStep1.class);
    startActivity(createAccountIntent);
  }

  @OnClick(R.id.createAccountButton2) void createAccount2Clicked(View v) {
    Intent createAccountIntent = new Intent(this, OnBoardingProcess2Step1.class);
    startActivity(createAccountIntent);
  }

  @OnClick(R.id.loginButton) void loginClicked(View v) {
    //todo authenticate user
    //todo create new goal every login
    if (!sharedPreferencesUtil.getUserIsEnrolled()) {

      PendingNutritionData pendingNutritionData = new PendingNutritionData();

      pendingNutritionData.setGoalCalorie(2000);
      pendingNutritionData.setGoalFat(1000);
      pendingNutritionData.setGoalCarb(800);
      pendingNutritionData.setGoalProtein(750);
      pendingNutritionData.setCurrentCalories(0);
      pendingNutritionData.setCurrentCarb(0);
      pendingNutritionData.setCurrentFat(0);
      pendingNutritionData.setCurrentProtein(0);

      pendingNutritionData.setCurrentDate(DateUtils.getCurrentDate());

      pendingNutritionalObserver.onNext(pendingNutritionData);

      RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);

      sharedPreferencesUtil.storeUserIsEnrolled(true);
    }

    Intent intent = new Intent(this, LandingPageActivity.class);
    startActivity(intent);
    finish();
  }
}
