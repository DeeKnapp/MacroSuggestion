package com.dustin.knapp.project.macrosuggestion.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.adapters.LandingPageFragmentPagerAdapter;
import com.dustin.knapp.project.macrosuggestion.models.NutritionDataGoal;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuHelper;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuItem;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.dustin.knapp.project.macrosuggestion.utils.RealmUtils;
import javax.inject.Inject;
import rx.Observable;
import rx.Observer;

public class LandingPageActivity extends BaseNavDrawerActivity {

  ViewPager mViewPager;

  private View view;

  @Inject public Observer<PendingNutritionData> pendingNutritionalObserver;
  @Inject public Observable<PendingNutritionData> pendingNutritionalObservable;
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
    toolbar.setBackgroundColor(
        ResourcesCompat.getColor(getResources(), R.color.dgm_dark_green, null));

    PendingNutritionData pendingNutritionData = new PendingNutritionData();
    PendingNutritionData realmPendingData = RealmUtils.getCurrentDayNutritionObject();

    if (realmPendingData == null) {
      NutritionDataGoal nutritionDataGoal =
          RealmUtils.getNutrtionDataGoal(sharedPreferencesUtil.getEnrolledEmail());
      pendingNutritionData = new PendingNutritionData();
      pendingNutritionData.setGoalCalorie(nutritionDataGoal.getGoalCalorie());
      pendingNutritionData.setGoalProtein(nutritionDataGoal.getGoalProtein());
      pendingNutritionData.setGoalFat(nutritionDataGoal.getGoalFat());
      pendingNutritionData.setGoalCarb(nutritionDataGoal.getGoalCarb());
      pendingNutritionData.setCurrentCalories(0);
      pendingNutritionData.setCurrentProtein(0);
      pendingNutritionData.setCurrentFat(0);
      pendingNutritionData.setCurrentCarb(0);
      pendingNutritionData.setCurrentDate(DateUtils.getCurrentDate());

      sharedPreferencesUtil.storeShouldShowCalorieAnimation(true);
      sharedPreferencesUtil.storeShouldShowWaterAnimation(true);
    } else {
      pendingNutritionData.setCurrentDate(DateUtils.getCurrentDate());
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
  }

  private void setupViewPager() {
    mViewPager = (ViewPager) view.findViewById(R.id.pager);
    landingPageFragmentPagerAdapter =
        new LandingPageFragmentPagerAdapter(getSupportFragmentManager());
    TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
    mViewPager.setAdapter(landingPageFragmentPagerAdapter);
    tabLayout.setupWithViewPager(mViewPager);
  }

  @Override protected void onStart() {
    super.onStart();
    int position = DrawerMenuHelper.getNavDrawerIndex(LandingPageActivity.class);
    drawerMenuAdaper.updateWithSelectedPosition(position);
    drawerMenuList.smoothScrollToPosition(position);
  }

  public void updateFragmentViews() {
    landingPageFragmentPagerAdapter.caloriesFragment.updateView();
    landingPageFragmentPagerAdapter.macrosFragment.updateViews();
  }
}
