package com.dustin.knapp.project.macrosuggestion.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import android.content.Intent;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.models.PendingWaterData;
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.dustin.knapp.project.macrosuggestion.utils.RealmUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import javax.inject.Inject;
import rx.Observer;

/**
 * Created by dknapp on 5/10/17
 */

public class LoginActivity extends BaseActivity
    implements GoogleApiClient.OnConnectionFailedListener {

  private static final int REQUEST_CODE_GOOGLE_SIGN_IN = 0;

  @BindView(R.id.createAccountButton) Button createAccountButton;
  @BindView(R.id.createAccountButton2) Button createAccountButton2;
  @BindView(R.id.etEnterEmail) EditText etEmail;
  @BindView(R.id.etEnterPassword) EditText etPassword;

  @BindView(R.id.loginButton) Button loginButton;

  @Inject public Observer<PendingNutritionData> pendingNutritionalObserver;
  @Inject public Observer<UserObject> pendingUserObjectObserver;
  @Inject public Observer<PendingWaterData> pendingWaterObserver;

  @BindView(R.id.googleLoginButton) SignInButton googleLoginButton;

  GoogleApiClient googleApiClient;

  FirebaseAuth firebaseAuth;
  private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

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

    firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
      @Override public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
          // User is signed in
          Log.d("THIS", "onAuthStateChanged:signed_in:" + user.getUid());
        } else {
          // User is signed out
          Log.d("THIS", "onAuthStateChanged:signed_out");
        }
      }
    };

    firebaseAuth = FirebaseAuth.getInstance();

    // Configure Google Sign In
    GoogleSignInOptions googleSignInOptions =
        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
            getString(R.string.default_web_client_id)).requestEmail().build();
    googleApiClient =
        new GoogleApiClient.Builder(this).enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
            .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
            .build();
  }

  @OnClick(R.id.googleLoginButton) void googleLoginButtonClicked(View v) {
    //todo auth with google
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_SIGN_IN);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      if (result.isSuccess()) {
        GoogleSignInAccount account = result.getSignInAccount();
        authenticateGoogleAccountWithFirebase(account);
      } else {
        Log.e("THIS", "Google Sign In failed.");
        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
      }
    }
  }

  private void authenticateGoogleAccountWithFirebase(final GoogleSignInAccount acct) {
    Log.d("THIS", "firebaseAuthWithGooogle:" + acct.getId());
    AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
    firebaseAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            Log.d("THIS", "signInWithCredential:onComplete:" + task.isSuccessful());
            if (!task.isSuccessful()) {
              Log.w("THIS", "signInWithCredential", task.getException());
              Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT)
                  .show();
            } else {
              Log.w("THIS", "signInWithCredential", task.getException());
              UserObject signedInUser =
                  RealmUtils.getCurrentUserObject(task.getResult().getUser().getEmail());
              if (signedInUser == null) {
                UserObject userObject = new UserObject();
                userObject.setEmail(acct.getEmail());
                userObject.setName(acct.getDisplayName());

                pendingUserObjectObserver.onNext(userObject);

                Intent intent = new Intent(getApplicationContext(), OnBoardingActivityStep1.class);
                startActivity(intent);
                finish();
              } else {
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

                Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
                startActivity(intent);
                finish();
              }
            }
          }
        });
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

    if (etEmail.getText().toString().equals("")) {
      etEmail.requestFocus();
      showKeyboard(etEmail);
    } else if (etPassword.getText().toString().equals("")) {
      etPassword.requestFocus();
      showKeyboard(etPassword);
    } else {

      Task<AuthResult> login = firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(),
          etPassword.getText().toString());

      if (login.isSuccessful()) {
        //login is successful
        //todo need to update this check right here
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

          PendingWaterData pendingWaterData = new PendingWaterData();
          pendingWaterData.setCurrentDate(DateUtils.getCurrentDate());
          pendingWaterData.setGoalWater(64);
          pendingWaterData.setCurrentWater(0);

          pendingWaterObserver.onNext(pendingWaterData);

          RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);
          RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);

          sharedPreferencesUtil.storeUserIsEnrolled(true);
        }

        Intent intent = new Intent(this, LandingPageActivity.class);
        startActivity(intent);
        finish();
      }
    }
  }

  @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Log.d("THIS", "onConnectionFailed:" + connectionResult);
    Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
  }

  @Override protected void onStart() {
    super.onStart();
    firebaseAuth.addAuthStateListener(firebaseAuthStateListener);
  }

  @Override protected void onStop() {
    super.onStop();
    if (firebaseAuthStateListener != null) {
      firebaseAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
  }
}
