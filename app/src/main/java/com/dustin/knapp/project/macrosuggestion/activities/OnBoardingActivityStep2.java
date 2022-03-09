package com.dustin.knapp.project.macrosuggestion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dustin.knapp.project.macrosuggestion.Constants;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import com.dustin.knapp.project.macrosuggestion.utils.FormulaUtils;
import javax.inject.Inject;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by dknapp on 5/8/17
 */
public class OnBoardingActivityStep2 extends BaseActivity {

  @BindView(R.id.tvWelcomeUser) TextView tvWelcomeUser;
  @BindView(R.id.noActivity) RadioButton notActiveButton;
  @BindView(R.id.lightActivity) RadioButton lightActiveButton;
  @BindView(R.id.moderateActivity) RadioButton moderateActiveButton;
  @BindView(R.id.intenseActivity) RadioButton intenseActiveButton;
  @BindView(R.id.submitButton) Button btnSubmit;

  @Inject public Observer<UserObject> pendingUserObjectObserver;
  @Inject public Observable<UserObject> pendingUserObjectObservable;

  private UserObject currentUserObject;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.on_boarding_step_2);

    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);

    ButterKnife.bind(this);

    pendingUserObjectObservable.subscribe(new Action1<UserObject>() {
      @Override public void call(UserObject userObject) {
        currentUserObject = userObject;
      }
    });

    tvWelcomeUser.setText(
        currentUserObject.getName() + ", how active are you on your typical day to day...?");

    int inchesTall =
        currentUserObject.getHeightInInches() + currentUserObject.getHeightInFeet() * 12;

    //todo need to add in the real age after saving in object
    //if (currentUserObject.getSex().equals("Male")) {
    //
    //} else {
    //
    //}
    double estimatedCaloriesBurnedSedentary =
        FormulaUtils.sedentaryCaloriesBurnedMen(inchesTall, currentUserObject.getCurrentWeight(),
            24);
    notActiveButton.setText("Not Active\nI don't usually workout\n"
        + "Estimated Calories Burned per day: "
        + estimatedCaloriesBurnedSedentary);

    double estimateCaloriesBurnedLightlyActive =
        FormulaUtils.estimateCaloriesBurnedDailyMen(inchesTall,
            currentUserObject.getCurrentWeight(), 24, 3);

    lightActiveButton.setText("Lightly Active\nI workout about 1-3 times per week\n"
        + "Estimated Calories Burned per day: "
        + (estimateCaloriesBurnedLightlyActive));

    double estimateCaloriesBurnedModeratelyActive =
        FormulaUtils.estimateCaloriesBurnedDailyMen(inchesTall,
            currentUserObject.getCurrentWeight(), 24, 5);

    moderateActiveButton.setText("Moderately Active\nI workout about 3-5 times per week\n"
        + "Estimated Calories Burned per day: "
        +
        estimateCaloriesBurnedModeratelyActive);

    double estimateCaloriesBurnedIntenselyActive =
        FormulaUtils.estimateCaloriesBurnedDailyMen(inchesTall,
            currentUserObject.getCurrentWeight(), 24, 7);

    intenseActiveButton.setText("Intensely Active\nI workout about 5-7 times per week\n"
        + "Estimated Calories Burned per day: "
        + estimateCaloriesBurnedIntenselyActive);
  }

  @OnClick(R.id.submitButton) void submitButtonClicked(View v) {
    if (!(notActiveButton.isChecked()
        || lightActiveButton.isChecked()
        || moderateActiveButton.isChecked()
        || intenseActiveButton.isChecked())) {
      Toast.makeText(this, "Must Select Active Level", Toast.LENGTH_LONG).show();
    } else {
      if (lightActiveButton.isChecked()) {
        currentUserObject.setUserActivityLevel(Constants.USER_LIGHT_ACTIVITY_LEVEL);
      } else if (moderateActiveButton.isChecked()) {
        currentUserObject.setUserActivityLevel(Constants.USER_MODERATE_ACTIVITY_LEVEL);
      } else if (intenseActiveButton.isChecked()) {
        currentUserObject.setUserActivityLevel(Constants.USER_INTENSE_ACTIVITY_LEVEL);
      } else if (notActiveButton.isChecked()) {
        currentUserObject.setUserActivityLevel(Constants.USER_NO_ACTIVITY_LEVEL);
      }

      pendingUserObjectObserver.onNext(currentUserObject);
      Intent intent = new Intent(this, OnBoardingActivityStep3.class);
      startActivity(intent);
      finish();
    }
  }

  @OnClick(R.id.infoFab) void infoFabClicked(View v) {
    //todo info fragment explaining estimations
  }
}