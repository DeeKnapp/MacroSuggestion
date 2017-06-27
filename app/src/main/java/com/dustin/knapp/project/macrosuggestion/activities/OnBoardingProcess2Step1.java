package com.dustin.knapp.project.macrosuggestion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import javax.inject.Inject;
import rx.Observer;

/**
 * Created by dknapp on 5/16/17
 */
public class OnBoardingProcess2Step1 extends BaseActivity {

  @BindView(R.id.etEnterName) EditText etName;
  @BindView(R.id.etEnterEmail) EditText etEmail;
  @BindView(R.id.nextButton) Button nextButton;

  @Inject public Observer<UserObject> pendingUserObjectObserver;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.on_boarding_process_2_step_1);
    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);

    unbinder = ButterKnife.bind(this);
  }

  @OnClick(R.id.nextButton) void nextButtonClicked(View v) {
    if (etName.getText().toString().equals("")) {
      etName.requestFocus();
    } else if (etEmail.getText().toString().equals("")) {
      etEmail.requestFocus();
    } else {
      UserObject userObject = new UserObject();
      userObject.setName(etName.getText().toString());
      userObject.setEmail(etEmail.getText().toString());
      pendingUserObjectObserver.onNext(userObject);
      Intent nextStepIntent = new Intent(this, OnBoardingProcess2Step2.class);
      startActivity(nextStepIntent);
      finish();
    }
  }
}
