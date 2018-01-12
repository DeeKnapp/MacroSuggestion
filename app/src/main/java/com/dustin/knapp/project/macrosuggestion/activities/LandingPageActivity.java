package com.dustin.knapp.project.macrosuggestion.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.dustin.knapp.project.macrosuggestion.Constants;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.adapters.LandingPageFragmentPagerAdapter;
import com.dustin.knapp.project.macrosuggestion.models.BaseNutrition;
import com.dustin.knapp.project.macrosuggestion.models.NutritionDataGoal;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.models.PendingWaterData;
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuHelper;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuItem;
import com.dustin.knapp.project.macrosuggestion.presenters.colories_fragment.InspirationQuotePresenter;
import com.dustin.knapp.project.macrosuggestion.presenters.colories_fragment.InspirtationQuoteReactiveView;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.dustin.knapp.project.macrosuggestion.utils.storage.RealmUtils;
import com.dustin.knapp.project.macrosuggestion.utils.storage.SharedPreferencesUtil;
import javax.inject.Inject;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class LandingPageActivity extends BaseNavDrawerActivity implements ViewPager.OnPageChangeListener, InspirtationQuoteReactiveView {

  public ViewPager mViewPager;
  public TabLayout mTabLayout;

  private View view;

  @Inject public Observer<PendingNutritionData> pendingNutritionalObserver;
  @Inject public Observer<PendingWaterData> pendingWaterObserver;
  @Inject public InspirationQuotePresenter inspirationQuotePresenter;
  @Inject public Observable<PendingNutritionData> pendingNutritionalObservable;
  @Inject public SharedPreferencesUtil sharedPreferencesUtil;

  public PendingNutritionData currentPendingNutritionalData;
  //todo if nutritional data doesn't exist, add goals/current to default from dietary plan

  LandingPageFragmentPagerAdapter landingPageFragmentPagerAdapter;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    view = LayoutInflater.from(this).inflate(R.layout.landing_page, null);

    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);

    toolbarTitle.setText(DrawerMenuItem.getTitle(LandingPageActivity.class));
    toolbar.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));

    setupViewPager();
    this.content.removeAllViews();
    this.content.addView(view);

    toolbarTitle.setText(DrawerMenuItem.getTitle(LandingPageActivity.class));
    toolbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.calories_toolbar, null));
    mTabLayout.setBackground(getDrawable(R.drawable.tab_layout_orange));
    inspirationQuotePresenter.setView(this);

    PendingNutritionData pendingNutritionData = new PendingNutritionData();
    PendingNutritionData realmPendingData = RealmUtils.getCurrentDayNutritionObject();

    UserObject currentUserObject = RealmUtils.getCurrentUserObject(sharedPreferencesUtil.getEnrolledUniqueUserId());
    if (realmPendingData == null) {
      //todo will goal ever be null here?
      if (currentUserObject.getNutritionDataGoal() == null) {
        NutritionDataGoal newGoal = new NutritionDataGoal();
        newGoal.setGoalCalorie(2000);
        newGoal.setGoalProtein(200);
        newGoal.setGoalCarb(200);
        newGoal.setGoalFat(200);
        currentUserObject.setNutritionDataGoal(new NutritionDataGoal());
      }
      pendingNutritionData = new PendingNutritionData();
      pendingNutritionData.setGoalCalorie(currentUserObject.getNutritionDataGoal().getGoalCalorie());
      pendingNutritionData.setGoalProtein(currentUserObject.getNutritionDataGoal().getGoalProtein());
      pendingNutritionData.setGoalFat(currentUserObject.getNutritionDataGoal().getGoalFat());
      pendingNutritionData.setGoalCarb(currentUserObject.getNutritionDataGoal().getGoalCarb());
      pendingNutritionData.setCurrentCalories(0);
      pendingNutritionData.setCurrentProtein(0);
      pendingNutritionData.setCurrentFat(0);
      pendingNutritionData.setCurrentCarb(0);
      pendingNutritionData.setCurrentDate(DateUtils.getCurrentDateString());

      sharedPreferencesUtil.storeShouldShowCalorieAnimation(true);
      sharedPreferencesUtil.storeShouldShowWaterAnimation(true);
    } else {
      pendingNutritionData.setCurrentDate(DateUtils.getCurrentDateString());
      pendingNutritionData.setGoalCalorie(realmPendingData.getGoalCalorie());
      pendingNutritionData.setGoalProtein(realmPendingData.getGoalProtein());
      pendingNutritionData.setGoalFat(realmPendingData.getGoalFat());
      pendingNutritionData.setGoalCarb(realmPendingData.getGoalCarb());
      pendingNutritionData.setCurrentCalories(realmPendingData.getCurrentCalories());
      pendingNutritionData.setCurrentProtein(realmPendingData.getCurrentProtein());
      pendingNutritionData.setCurrentFat(realmPendingData.getCurrentFat());
      pendingNutritionData.setCurrentCarb(realmPendingData.getCurrentCarb());
    }

    pendingNutritionalObserver.onNext(pendingNutritionData);

    PendingWaterData pendingWaterData = RealmUtils.getCurrentDayPendingWaterData();

    if (pendingWaterData == null) {
      pendingWaterData = new PendingWaterData();
      //todo will goal ever be null here?
      pendingWaterData.setGoalWater(currentUserObject.getWaterDataGoal().getGoalWater());
      pendingWaterData.setCurrentWater(currentUserObject.getWaterDataGoal().getCurrentWater());
      pendingWaterData.setCurrentDate(DateUtils.getCurrentDateString());
    }

    RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    pendingWaterObserver.onNext(pendingWaterData);

    pendingNutritionalObservable.subscribe(new Action1<PendingNutritionData>() {
      @Override public void call(PendingNutritionData pendingNutritionData) {
        currentPendingNutritionalData = pendingNutritionData;
      }
    });

    getNewQuote();
  }

  @Override public void updateColorScheme(int colorScheme) {
    super.updateColorScheme(colorScheme);
  }

  private void setupViewPager() {
    mViewPager = (ViewPager) view.findViewById(R.id.pager);
    landingPageFragmentPagerAdapter = new LandingPageFragmentPagerAdapter(getSupportFragmentManager());
    mTabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
    mViewPager.setAdapter(landingPageFragmentPagerAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
    mViewPager.addOnPageChangeListener(this);
  }

  @Override protected void onStart() {
    super.onStart();
    int position = DrawerMenuHelper.getNavDrawerIndex(LandingPageActivity.class);
    drawerMenuAdaper.updateWithSelectedPosition(position);
    drawerMenuList.smoothScrollToPosition(position);
    onPageSelected(mViewPager.getCurrentItem());
  }

  public void updateFragmentViews() {
    landingPageFragmentPagerAdapter.caloriesFragment.updateView();
    landingPageFragmentPagerAdapter.macrosFragment.updateViews();
  }

  public void updateObservers(BaseNutrition baseNutrition) {
    currentPendingNutritionalData.setCurrentCalories(currentPendingNutritionalData.getCurrentCalories() + baseNutrition.calories);

    currentPendingNutritionalData.setCurrentProtein(currentPendingNutritionalData.getCurrentProtein() + baseNutrition.protein);

    currentPendingNutritionalData.setCurrentFat(currentPendingNutritionalData.getCurrentFat() + baseNutrition.fats);

    currentPendingNutritionalData.setCurrentCarb(currentPendingNutritionalData.getCurrentCarb() + baseNutrition.carbs);

    pendingNutritionalObserver.onNext(currentPendingNutritionalData);

    RealmUtils.updateCurrentDayPendingNutritionData(currentPendingNutritionalData);
  }

  @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override public void onPageSelected(int position) {
    if (position == 0) {
      mTabLayout.setBackground(getDrawable(R.drawable.tab_layout_orange));
      updateColorScheme(0);
    } else if (position == 1) {
      mTabLayout.setBackground(getDrawable(R.drawable.tab_layout_purple));
      updateColorScheme(1);
    } else if (position == 2) {
      mTabLayout.setBackground(getDrawable(R.drawable.tab_layout_blue));
      updateColorScheme(2);
    }
  }

  @Override public void onPageScrollStateChanged(int state) {

  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == Constants.CAMERA_PERMISSION_CODE && grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
      startBarcodeScannerActivity();
    }
  }

  private void startBarcodeScannerActivity() {
    Intent intent = new Intent(this, BarcodeScanner.class);
    startActivity(intent);
  }

  private void getNewQuote() {
    inspirationQuotePresenter.getInspirationQuoteForSnackbar();
  }

  private boolean needUsersCameraPermission() {
    return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PERMISSION_GRANTED;
  }

  public void requestCameraPermission() {
    ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, Constants.CAMERA_PERMISSION_CODE);
  }

  @Override public void showProgressBar() {
    //probably won't use here
  }

  @Override public void dismissProgressBar() {
    //probably won't use here
  }

  @Override public void onServerSuccess(String quote) {
    if (!isDestroyed()) {
      Snackbar snackbar =
          Snackbar.make(findViewById(R.id.landing_page_wrapper), quote, Snackbar.LENGTH_INDEFINITE).setAction("Dismiss", new View.OnClickListener() {
            @Override public void onClick(View v) {
              //do nothing
            }
          }).setActionTextColor(Color.GREEN);
      View snackbarView = snackbar.getView();
      TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
      textView.setMaxLines(10);
      snackbar.show();
    }
  }

  @Override public void onServerError() {

  }
}
