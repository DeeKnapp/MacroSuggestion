package com.dustin.knapp.project.macrosuggestion.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import javax.inject.Inject;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by dknapp on 5/16/17
 */
public class OnBoardingProcess2Step3 extends BaseActivity {

  @BindView(R.id.etCurrentWeight) EditText etCurrentWeight;
  @BindView(R.id.etTargetWeight) EditText etTargetWeight;
  @BindView(R.id.etEnterHeightFeet) EditText etHeightFt;
  @BindView(R.id.etEnterHeightInches) EditText etHeightInches;
  @BindView(R.id.nextButton) Button btnNext;

  @Inject public Observer<UserObject> pendingUserObjectObserver;
  @Inject public Observable<UserObject> pendingUserObjectObservable;

  private UserObject currentUserObject = new UserObject();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.on_boarding_process_2_step_3);
    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);

    ButterKnife.bind(this);

    pendingUserObjectObservable.subscribe(new Action1<UserObject>() {
      @Override public void call(UserObject userObject) {
        currentUserObject.setName(userObject.getName());
        currentUserObject.setEmail(userObject.getEmail());
        currentUserObject.setGoalType(userObject.getGoalType());
      }
    });
  }

  @OnClick(R.id.nextButton) void nextButtonClicked(View v) {
    if (etHeightFt.getText().toString().equals("")) {
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
    } else {
      currentUserObject.setHeightInFeet(Integer.valueOf(etHeightFt.getText().toString()));
      currentUserObject.setHeightInInches(Integer.valueOf(etHeightInches.getText().toString()));
      currentUserObject.setCurrentWeight(Float.valueOf(etCurrentWeight.getText().toString()));
      currentUserObject.setTargetWeight(Float.valueOf(etTargetWeight.getText().toString()));

      pendingUserObjectObserver.onNext(currentUserObject);

      Intent nextStepIntent = new Intent(this, OnBoardingActivityStep2.class);
      startActivity(nextStepIntent);
      finish();
    }
  }
}
