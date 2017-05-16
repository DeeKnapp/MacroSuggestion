package com.dustin.knapp.project.macrosuggestion.activities;

import android.content.Intent;
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
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import javax.inject.Inject;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by dknapp on 5/8/17
 */
public class OnBoardingActivityStep2 extends BaseActivity {

  @BindView(R.id.tvWelcomeUser) TextView tvWelcomeUser;
  @BindView(R.id.lightActivity) RadioButton lightActivityButton;
  @BindView(R.id.moderateActivity) RadioButton moderateActivityButton;
  @BindView(R.id.intenseActivity) RadioButton intenseActivityButton;
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
  }

  @OnClick(R.id.submitButton) void submitButtonClicked(View v) {
    if (!(lightActivityButton.isChecked()
        || moderateActivityButton.isChecked()
        || intenseActivityButton.isChecked())) {
      Toast.makeText(this, "Must Select Active Level", Toast.LENGTH_LONG).show();
    } else {
      if (lightActivityButton.isChecked()) {
        currentUserObject.setUserActivityLevel(Constants.USER_LIGHT_ACTIVITY_LEVEL);
      } else if (moderateActivityButton.isChecked()) {
        currentUserObject.setUserActivityLevel(Constants.USER_MODERATE_ACTIVITY_LEVEL);
      } else if (intenseActivityButton.isChecked()) {
        currentUserObject.setUserActivityLevel(Constants.USER_INTENSE_ACTIVITY_LEVEL);
      }

      pendingUserObjectObserver.onNext(currentUserObject);
      Intent intent = new Intent(this, OnBoardingActivityStep3.class);
      startActivity(intent);
      finish();
    }
  }
}
