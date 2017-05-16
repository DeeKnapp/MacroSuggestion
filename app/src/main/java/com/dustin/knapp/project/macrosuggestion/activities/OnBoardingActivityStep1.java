package com.dustin.knapp.project.macrosuggestion.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dustin.knapp.project.macrosuggestion.Constants;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import android.content.Intent;
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import javax.inject.Inject;
import rx.Observer;

/**
 * Created by dknapp on 5/8/17
 */
public class OnBoardingActivityStep1 extends BaseActivity {

  @BindView(R.id.etEnterName) EditText etName;
  @BindView(R.id.etEnterEmail) EditText etEmail;
  @BindView(R.id.etCurrentWeight) EditText etCurrentWeight;
  @BindView(R.id.etTargetWeight) EditText etTargetWeight;
  @BindView(R.id.etEnterHeightFeet) EditText etHeightFt;
  @BindView(R.id.etEnterHeightInches) EditText etHeightInches;
  @BindView(R.id.loseWeightButton) RadioButton loseWeightButton;
  @BindView(R.id.gainWeightButton) RadioButton gainWeightButton;
  @BindView(R.id.nextButton) Button nextButton;

  @Inject public Observer<UserObject> pendingUserObjectObserver;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.on_boarding_step_1);

    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);

    unbinder = ButterKnife.bind(this);
  }

  @OnClick(R.id.nextButton) void nextButtonClicked(View v) {
    if (etName.getText().toString().equals("")) {
      etName.requestFocus();
      showKeyboard(etName);
    } else if (etEmail.getText().toString().equals("")) {
      etEmail.requestFocus();
      showKeyboard(etEmail);
    } else if (etHeightFt.getText().toString().equals("")) {
      etHeightFt.requestFocus();
      showKeyboard(etHeightFt);
    } else if (etHeightInches.getText().toString().equals("")) {
      etHeightInches.requestFocus();
      showKeyboard(etHeightInches);
    } else if (etCurrentWeight.getText().toString().equals("")) {
      etCurrentWeight.requestFocus();
      showKeyboard(etCurrentWeight);
    } else if (etTargetWeight.getText().toString().equals("")) {
      etTargetWeight.requestFocus();
      showKeyboard(etTargetWeight);
    } else if (!(loseWeightButton.isChecked() || gainWeightButton.isChecked())) {
      Toast.makeText(this, "Must select lose weight or gain weight", Toast.LENGTH_LONG).show();
      loseWeightButton.requestFocus();
    } else {
      UserObject newUser = new UserObject();
      newUser.setName(etName.getText().toString());
      newUser.setEmail(etEmail.getText().toString());
      newUser.setHeightInFeet(Integer.valueOf(etHeightFt.getText().toString()));
      newUser.setHeightInInches(Integer.valueOf(etHeightInches.getText().toString()));
      newUser.setCurrentWeight(Float.valueOf(etCurrentWeight.getText().toString()));
      newUser.setTargetWeight(Float.valueOf(etTargetWeight.getText().toString()));

      if (loseWeightButton.isChecked()) {
        newUser.setGoalType(Constants.USER_GOAL_LOSE_WEIGHT);
      } else {
        newUser.setGoalType(Constants.USER_GOAL_GAIN_WEIGHT);
      }

      pendingUserObjectObserver.onNext(newUser);
      Intent nextStepIntent = new Intent(this, OnBoardingActivityStep2.class);
      startActivity(nextStepIntent);
      finish();
    }
  }
}
