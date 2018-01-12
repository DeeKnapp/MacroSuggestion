package com.dustin.knapp.project.macrosuggestion.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.dustin.knapp.project.macrosuggestion.Constants;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;

import android.content.Intent;

import com.dustin.knapp.project.macrosuggestion.models.FoodEntry;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.models.PendingWaterData;
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import rx.Observer;

/**
 * Created by dknapp on 5/10/17
 */

public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

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
        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();
    googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
        .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
        .build();
  }

  @OnClick(R.id.googleLoginButton) void googleLoginButtonClicked(View v) {
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
    firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
      @Override public void onComplete(@NonNull Task<AuthResult> task) {
        Log.d("THIS", "signInWithCredential:onComplete:" + task.isSuccessful());
        if (!task.isSuccessful()) {
          Log.w("THIS", "signInWithCredential", task.getException());
          Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
        } else {
          //todo authenticate user
          //todo create new goal every login
          if (!sharedPreferencesUtil.getUserIsEnrolled()) {
            updateUserFromFirebaseForGoogle(task.getResult());
          } else {
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
    Intent createAccountIntent = new Intent(this, LottieExample.class);
    startActivity(createAccountIntent);
  }

  @OnClick(R.id.loginButton) void loginClicked(View v) {
    //todo authenticate user
    //todo create new goal every login
    //todo remove this chunk of code right here
    //PendingNutritionData pendingNutritionData = new PendingNutritionData();
    //
    //String date = "01-11-2017";
    //String date2 = "10-11-2017";
    //String date3 = "11-11-2017";
    //String date4 = "15-11-2017";
    //String date5 = "18-11-2017";
    //String date6 = "20-11-2017";
    //String date7 = "25-11-2017";
    //String date8 = "26-11-2017";
    //String date9 = "27-11-2017";
    //
    //pendingNutritionData.setGoalCalorie(2000);
    //pendingNutritionData.setGoalFat(1500);
    //pendingNutritionData.setGoalCarb(1000);
    //pendingNutritionData.setGoalProtein(800);
    //
    //PendingWaterData pendingWaterData = new PendingWaterData();
    //pendingWaterData.setGoalWater(64);
    //
    //////////////day 1////////////////
    //pendingNutritionData.setCurrentCalories(2000);
    //pendingNutritionData.setCurrentCarb(1500);
    //pendingNutritionData.setCurrentFat(1500);
    //pendingNutritionData.setCurrentProtein(1500);
    //
    //pendingWaterData.setCurrentWater(64);
    //pendingNutritionData.setCurrentDate(date);
    //pendingWaterData.setCurrentDate(date);
    //RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);
    //RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    //
    //////////////day 2////////////////
    //pendingNutritionData.setCurrentCalories(2000);
    //pendingNutritionData.setCurrentCarb(1500);
    //pendingNutritionData.setCurrentFat(1500);
    //pendingNutritionData.setCurrentProtein(1500);
    //
    //pendingWaterData.setCurrentWater(64);
    //pendingNutritionData.setCurrentDate(date2);
    //pendingWaterData.setCurrentDate(date2);
    //RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);
    //RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    //
    //////////////day 3////////////////
    //pendingNutritionData.setCurrentCalories(2000);
    //pendingNutritionData.setCurrentCarb(1500);
    //pendingNutritionData.setCurrentFat(1500);
    //pendingNutritionData.setCurrentProtein(1500);
    //
    //pendingWaterData.setCurrentWater(64);
    //pendingNutritionData.setCurrentDate(date3);
    //pendingWaterData.setCurrentDate(date3);
    //RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);
    //RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    //
    //////////////day 4////////////////
    //pendingNutritionData.setCurrentCalories(2000);
    //pendingNutritionData.setCurrentCarb(1500);
    //pendingNutritionData.setCurrentFat(1500);
    //pendingNutritionData.setCurrentProtein(1500);
    //
    //pendingWaterData.setCurrentWater(64);
    //pendingNutritionData.setCurrentDate(date4);
    //pendingWaterData.setCurrentDate(date4);
    //RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);
    //RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    //
    //////////////day 5////////////////
    //pendingNutritionData.setCurrentCalories(2000);
    //pendingNutritionData.setCurrentCarb(1500);
    //pendingNutritionData.setCurrentFat(1500);
    //pendingNutritionData.setCurrentProtein(1500);
    //
    //pendingWaterData.setCurrentWater(64);
    //pendingNutritionData.setCurrentDate(date5);
    //pendingWaterData.setCurrentDate(date5);
    //RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);
    //RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    //
    //////////////day 6////////////////
    //pendingNutritionData.setCurrentCalories(2000);
    //pendingNutritionData.setCurrentCarb(1500);
    //pendingNutritionData.setCurrentFat(1500);
    //pendingNutritionData.setCurrentProtein(1500);
    //
    //pendingWaterData.setCurrentWater(64);
    //pendingNutritionData.setCurrentDate(date6);
    //pendingWaterData.setCurrentDate(date6);
    //RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);
    //RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    //
    //////////////day 7////////////////
    //pendingNutritionData.setCurrentCalories(2000);
    //pendingNutritionData.setCurrentCarb(1500);
    //pendingNutritionData.setCurrentFat(1500);
    //pendingNutritionData.setCurrentProtein(1500);
    //
    //pendingWaterData.setCurrentWater(64);
    //pendingNutritionData.setCurrentDate(date7);
    //pendingWaterData.setCurrentDate(date7);
    //RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);
    //RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    //
    //////////////day 8////////////////
    //pendingNutritionData.setCurrentCalories(2000);
    //pendingNutritionData.setCurrentCarb(1500);
    //pendingNutritionData.setCurrentFat(1500);
    //pendingNutritionData.setCurrentProtein(1500);
    //
    //pendingWaterData.setCurrentWater(64);
    //pendingNutritionData.setCurrentDate(date8);
    //pendingWaterData.setCurrentDate(date8);
    //RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);
    //RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    //
    //////////////day 9////////////////
    //pendingNutritionData.setCurrentCalories(2000);
    //pendingNutritionData.setCurrentCarb(1500);
    //pendingNutritionData.setCurrentFat(1500);
    //pendingNutritionData.setCurrentProtein(1500);
    //
    //pendingWaterData.setCurrentWater(64);
    //pendingNutritionData.setCurrentDate(date9);
    //pendingWaterData.setCurrentDate(date9);
    //RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);
    //RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    //
    //pendingNutritionData.setCurrentCalories(0);
    //pendingNutritionData.setCurrentCarb(0);
    //pendingNutritionData.setCurrentFat(0);
    //pendingNutritionData.setCurrentProtein(0);
    //
    //pendingWaterData.setCurrentWater(0);
    //RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);
    //RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    //
    //pendingNutritionData.setCurrentCalories(0);
    //pendingNutritionData.setCurrentCarb(0);
    //pendingNutritionData.setCurrentFat(0);
    //pendingNutritionData.setCurrentProtein(0);
    //
    //pendingWaterData.setCurrentWater(0);
    //RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);
    //RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    //
    //pendingNutritionData.setCurrentCalories(0);
    //pendingNutritionData.setCurrentCarb(0);
    //pendingNutritionData.setCurrentFat(0);
    //pendingNutritionData.setCurrentProtein(0);
    //
    //pendingWaterData.setCurrentWater(0);
    //RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);
    //RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    //
    //pendingNutritionData.setCurrentDate(DateUtils.getCurrentDateString());
    //pendingWaterData.setCurrentDate(DateUtils.getCurrentDateString());
    //RealmUtils.updateCurrentDayPendingNutritionData(pendingNutritionData);
    //RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    //pendingWaterObserver.onNext(pendingWaterData);
    //
    //pendingNutritionalObserver.onNext(pendingNutritionData);
    //sharedPreferencesUtil.storeUserIsEnrolled(true);
    //sharedPreferencesUtil.storeEnrolledEmail("drknapp@aol.com");
    //
    //UserObject userObject = new UserObject();
    //
    ////todo decouple goal from the current day objects.
    //userObject.setEmail("drknapp@aol.com");
    //userObject.setUniqueUserId("drknapp@aol.com");
    //RealmUtils.saveUserObject(userObject);
    //
    //Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
    //startActivity(intent);
    //finish();

    //////////////////end chunk of code to remove.../////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    if (etEmail.getText().toString().equals("")) {
      etEmail.requestFocus();
      showKeyboard(etEmail);
    } else if (etPassword.getText().toString().equals("")) {
      etPassword.requestFocus();
      showKeyboard(etPassword);
    } else {

      firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override public void onComplete(@NonNull Task<AuthResult> task) {
              Log.d("Login", "Login successful: " + task.isSuccessful());
              if (task.isSuccessful()) {
                //login is successful
                //todo need to update this check right here
                Log.d("Login", "User Is Enrolled: " + sharedPreferencesUtil.getUserIsEnrolled());

                if (!sharedPreferencesUtil.getUserIsEnrolled()) {
                  updateUserFromFirebase(task.getResult().getUser().getUid());
                }
              } else {
                //todo invalid credentials error message...
              }
            }
          });
    }
  }

  private void updateUserFromFirebaseForGoogle(final AuthResult task) {
    if (task.getUser().getUid().equals(sharedPreferencesUtil.getEnrolledUniqueUserId())) {
      Log.d("Testing", "User exists!.");
      sharedPreferencesUtil.storeUserIsEnrolled(true);
      Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
      startActivity(intent);
      finish();
    } else {
      DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();

      Query mQuery = mReference.child(Constants.BASE_DATABASE_REFERENCE).child(Constants.USER_DATABASE_REFERENCE).child(task.getUser().getUid());

      Log.d("Login", "Querying Firebase for User UID: " + task.getUser().getUid());

      mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override public void onDataChange(DataSnapshot accountDataSnapshot) {
          Log.d("Login", "Querying Firebase Snapshot exists: " + accountDataSnapshot.exists());
          if (accountDataSnapshot.exists()) {
            //todo resave goals to realm local storage
            //todo pull in all previous records in background storage

            UserObject currentUser = accountDataSnapshot.getValue(UserObject.class);

            PendingNutritionData pendingNutritionData = new PendingNutritionData();

            pendingNutritionData.setGoalCalorie(currentUser.getNutritionDataGoal().getGoalCalorie());
            pendingNutritionData.setGoalFat(currentUser.getNutritionDataGoal().getGoalFat());
            pendingNutritionData.setGoalCarb(currentUser.getNutritionDataGoal().getGoalCarb());
            pendingNutritionData.setGoalProtein(currentUser.getNutritionDataGoal().getGoalCalorie());
            pendingNutritionData.setCurrentCalories(0);
            pendingNutritionData.setCurrentCarb(0);
            pendingNutritionData.setCurrentFat(0);
            pendingNutritionData.setCurrentProtein(0);

            pendingNutritionData.setCurrentDate(DateUtils.getCurrentDateString());

            pendingNutritionalObserver.onNext(pendingNutritionData);

            PendingWaterData pendingWaterData = new PendingWaterData();
            pendingWaterData.setCurrentDate(DateUtils.getCurrentDateString());
            pendingWaterData.setGoalWater(currentUser.getWaterDataGoal().getGoalWater());
            pendingWaterData.setCurrentWater(0);

            pendingWaterObserver.onNext(pendingWaterData);

            sharedPreferencesUtil.storeUserIsEnrolled(true);
            sharedPreferencesUtil.
                storeEnrolledEmail(currentUser.getEmail());
            sharedPreferencesUtil.storeEnrolledUniqueUserId(currentUser.getUniqueUserId());
            retrieveBackupRecords(currentUser.getUniqueUserId());
          } else {
            UserObject userObject = new UserObject();
            userObject.setEmail(task.getUser().getEmail());
            userObject.setName(task.getUser().getDisplayName());
            userObject.setUniqueUserId(task.getUser().getUid());
            userObject.setSignedInWithGoogle(true);

            pendingUserObjectObserver.onNext(userObject);

            Intent intent = new Intent(getApplicationContext(), OnBoardingActivityStep1.class);
            startActivity(intent);
            finish();
          }
        }

        @Override public void onCancelled(DatabaseError databaseError) {

        }
      });
    }
  }

  private void updateUserFromFirebase(String uUId) {

    if (sharedPreferencesUtil.getEnrolledUniqueUserId().equals(uUId)) {
      Log.d("Testing", "User exists!.");
      sharedPreferencesUtil.storeUserIsEnrolled(true);
      Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
      startActivity(intent);
      finish();
    } else {
      DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();

      Query mQuery = mReference.child(Constants.BASE_DATABASE_REFERENCE).child(Constants.USER_DATABASE_REFERENCE).child(uUId);

      mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override public void onDataChange(DataSnapshot dataSnapshot) {
          if (dataSnapshot.exists()) {
            //todo resave goals to realm local storage
            //todo pull in all previous records in background storage

            UserObject currentUser = dataSnapshot.getValue(UserObject.class);

            PendingNutritionData pendingNutritionData = new PendingNutritionData();

            pendingNutritionData.setGoalCalorie(currentUser.getNutritionDataGoal().getGoalCalorie());
            pendingNutritionData.setGoalFat(currentUser.getNutritionDataGoal().getGoalFat());
            pendingNutritionData.setGoalCarb(currentUser.getNutritionDataGoal().getGoalCarb());
            pendingNutritionData.setGoalProtein(currentUser.getNutritionDataGoal().getGoalCalorie());
            pendingNutritionData.setCurrentCalories(0);
            pendingNutritionData.setCurrentCarb(0);
            pendingNutritionData.setCurrentFat(0);
            pendingNutritionData.setCurrentProtein(0);

            pendingNutritionData.setCurrentDate(DateUtils.getCurrentDateString());

            pendingNutritionalObserver.onNext(pendingNutritionData);

            PendingWaterData pendingWaterData = new PendingWaterData();
            pendingWaterData.setCurrentDate(DateUtils.getCurrentDateString());
            pendingWaterData.setGoalWater(currentUser.getWaterDataGoal().getGoalWater());
            pendingWaterData.setCurrentWater(0);

            pendingWaterObserver.onNext(pendingWaterData);

            sharedPreferencesUtil.storeUserIsEnrolled(true);
            sharedPreferencesUtil.storeEnrolledEmail(currentUser.getEmail());
            sharedPreferencesUtil.storeEnrolledUniqueUserId(currentUser.getUniqueUserId());
            retrieveBackupRecords(currentUser.getUniqueUserId());
          } else {
            //todo account create
            //this should never happen, unless records are cleared from firebase
            Log.d("Login", "Querying Firebase Snapshot, need user to create account...");

            Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
            startActivity(intent);
            finish();
          }
        }

        @Override public void onCancelled(DatabaseError databaseError) {

        }
      });
    }
  }

  private void retrieveBackupRecords(String uid) {
    DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();

    Query mQuery = mReference.child(Constants.BASE_DATABASE_REFERENCE).child(Constants.USER_RECORDS_DATABASE_REFERENCE).child(uid);

    mQuery.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot recordsDataSnapshot) {
        ObjectMapper mapper = new ObjectMapper();
        //for (DataSnapshot recordDataSnapShot : recordsDataSnapshot.getChildren()) {
        //  RealmUtils.saveFoodEntry(mapper.convertValue(recordDataSnapShot.getValue(), FoodEntry.class));
        //}

        Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
        startActivity(intent);
        finish();
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        //todo check error states?
        Log.d("TESTING", "onCancelled: ");
      }
    });
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
