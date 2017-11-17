package com.dustin.knapp.project.macrosuggestion.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
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
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by dknapp on 5/8/17
 */
public class OnBoardingActivityStep1 extends BaseActivity {

  @BindView(R.id.etEnterName) EditText etName;
  @BindView(R.id.etEnterEmail) EditText etEmail;
  @BindView(R.id.tvEnterPasswordTitle) TextView enterPasswordHeader;
  @BindView(R.id.etEnterPassword) EditText etPassword;
  @BindView(R.id.etBirthday) EditText etBirthday;
  @BindView(R.id.etCurrentWeight) EditText etCurrentWeight;
  @BindView(R.id.etTargetWeight) EditText etTargetWeight;
  @BindView(R.id.etEnterHeightFeet) EditText etHeightFt;
  @BindView(R.id.etEnterHeightInches) EditText etHeightInches;
  @BindView(R.id.loseWeightButton) RadioButton loseWeightButton;
  @BindView(R.id.maintainWeight) RadioButton maintaineightButton;
  @BindView(R.id.gainWeightButton) RadioButton gainWeightButton;
  @BindView(R.id.nextButton) Button nextButton;

  @Inject public Observer<UserObject> pendingUserObjectObserver;
  @Inject public Observable<UserObject> pendingUserObjectObservable;

  private UserObject pendingNewUser;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.on_boarding_step_1);

    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);

    unbinder = ButterKnife.bind(this);
  }

  @Override protected void onStart() {
    super.onStart();
    subscriptions.add(pendingUserObjectObservable.subscribe(new Action1<UserObject>() {
      @Override public void call(UserObject userObject) {
        if (userObject.isSignedInWithGoogle()) {
          pendingNewUser = userObject;
          etName.setText(userObject.getName());
          etEmail.setText(userObject.getEmail());
          etName.setFocusable(false);
          etEmail.setFocusable(false);
          etPassword.setVisibility(View.GONE);
          enterPasswordHeader.setVisibility(View.GONE);
        } else {
          pendingNewUser = new UserObject();
        }
      }
    }));
  }

  @OnClick(R.id.nextButton) void nextButtonClicked(View v) {
    if (etName.getText().toString().equals("")) {
      etName.requestFocus();
      showKeyboard(etName);
    } else if (etEmail.getText().toString().equals("")) {
      etEmail.requestFocus();
      showKeyboard(etEmail);
    } else if (etBirthday.getText().toString().equals("")) {
      etBirthday.requestFocus();
      showKeyboard(etPassword);
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
    } else if (!(loseWeightButton.isChecked() || gainWeightButton.isChecked()) || maintaineightButton.isChecked()) {
      Toast.makeText(this, "Must select lose weight or gain weight", Toast.LENGTH_LONG).show();
      loseWeightButton.requestFocus();
    } else if (!pendingNewUser.isSignedInWithGoogle() && etPassword.getText().toString().equals("")) {
      etPassword.requestFocus();
    } else {
      //todo save birthday in user object -- use in diet plan calculation
      if (pendingNewUser.isSignedInWithGoogle()) {
        pendingNewUser.setPendingPassword("");
      } else {
        pendingNewUser.setRegisteringWithEmail(true);
        pendingNewUser.setEmail(etEmail.getText().toString());
        pendingNewUser.setPendingPassword(etPassword.getText().toString());
      }
      pendingNewUser.setHeightInFeet(Integer.valueOf(etHeightFt.getText().toString()));
      pendingNewUser.setHeightInInches(Integer.valueOf(etHeightInches.getText().toString()));
      pendingNewUser.setCurrentWeight(Float.valueOf(etCurrentWeight.getText().toString()));
      pendingNewUser.setTargetWeight(Float.valueOf(etTargetWeight.getText().toString()));

      if (loseWeightButton.isChecked()) {
        pendingNewUser.setGoalType(Constants.USER_GOAL_LOSE_WEIGHT);
      } else if (gainWeightButton.isChecked()) {
        pendingNewUser.setGoalType(Constants.USER_GOAL_GAIN_WEIGHT);
      } else if (maintaineightButton.isChecked()) {
        pendingNewUser.setGoalType(Constants.USER_GOAL_GAIN_WEIGHT);
      }

      pendingUserObjectObserver.onNext(pendingNewUser);

      Intent nextStepIntent = new Intent(this, OnBoardingActivityStep2.class);
      startActivity(nextStepIntent);
      finish();
    }
  }
}
