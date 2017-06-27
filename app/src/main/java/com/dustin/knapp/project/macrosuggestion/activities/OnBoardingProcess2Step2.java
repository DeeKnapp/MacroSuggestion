package com.dustin.knapp.project.macrosuggestion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
public class OnBoardingProcess2Step2 extends BaseActivity {

  @BindView(R.id.loseWeightButton) RadioButton loseWeightButton;
  @BindView(R.id.gainWeightButton) RadioButton gainWeightButton;
  @BindView(R.id.nextButton) Button btnNext;

  @Inject public Observer<UserObject> pendingUserObjectObserver;
  @Inject public Observable<UserObject> pendingUserObjectObservable;

  private UserObject currentUserObject = new UserObject();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.on_boarding_process_2_step_2);
    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);

    ButterKnife.bind(this);

    pendingUserObjectObservable.subscribe(new Action1<UserObject>() {
      @Override public void call(UserObject userObject) {
        currentUserObject.setName(userObject.getName());
        currentUserObject.setEmail(userObject.getEmail());
      }
    });
  }

  @OnClick(R.id.nextButton) void nextButtonClicked(View v) {
    if (!(loseWeightButton.isChecked() || gainWeightButton.isChecked())) {
      Toast.makeText(this, "Please select goal type", Toast.LENGTH_LONG).show();
    } else {
      if (loseWeightButton.isChecked()) {
        currentUserObject.setGoalType(Constants.USER_GOAL_LOSE_WEIGHT);
      } else if (gainWeightButton.isChecked()) {
        currentUserObject.setGoalType(Constants.USER_GOAL_GAIN_WEIGHT);
      }
      pendingUserObjectObserver.onNext(currentUserObject);
      Intent nextStepIntent = new Intent(this, OnBoardingProcess2Step3.class);
      startActivity(nextStepIntent);
      finish();
    }
  }
}
