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
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.models.PendingWaterData;
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuHelper;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuItem;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.dustin.knapp.project.macrosuggestion.utils.RealmUtils;
import javax.inject.Inject;
import rx.Observable;
import rx.Observer;

public class LandingPageActivity extends BaseNavDrawerActivity
    implements ViewPager.OnPageChangeListener {

  public ViewPager mViewPager;
  public TabLayout mTabLayout;

  private View view;

  @Inject public Observer<PendingNutritionData> pendingNutritionalObserver;
  @Inject public Observer<PendingWaterData> pendingWaterObserver;
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
    toolbar.setBackground(
        ResourcesCompat.getDrawable(getResources(), R.drawable.calories_toolbar, null));
    mTabLayout.setBackground(getDrawable(R.drawable.tab_layout_orange));

    PendingNutritionData pendingNutritionData = new PendingNutritionData();
    PendingNutritionData realmPendingData = RealmUtils.getCurrentDayNutritionObject();

    UserObject currentUserObject =
        RealmUtils.getCurrentUserObject(sharedPreferencesUtil.getEnrolledUniqueUserId());
    if (realmPendingData == null) {
      pendingNutritionData = new PendingNutritionData();
      pendingNutritionData.setGoalCalorie(
          currentUserObject.getNutritionDataGoal().getGoalCalorie());
      pendingNutritionData.setGoalProtein(
          currentUserObject.getNutritionDataGoal().getGoalProtein());
      pendingNutritionData.setGoalFat(currentUserObject.getNutritionDataGoal().getGoalFat());
      pendingNutritionData.setGoalCarb(currentUserObject.getNutritionDataGoal().getGoalCarb());
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

    PendingWaterData pendingWaterData = RealmUtils.getCurrentDayPendingWaterData();

    if (pendingWaterData == null) {
      pendingWaterData = new PendingWaterData();
      pendingWaterData.setGoalWater(currentUserObject.getWaterDataGoal().getGoalWater());
      pendingWaterData.setCurrentWater(currentUserObject.getWaterDataGoal().getCurrentWater());
      pendingWaterData.setCurrentDate(DateUtils.getCurrentDate());
    }

    RealmUtils.updateCurrentDayPendingWaterData(pendingWaterData);
    pendingWaterObserver.onNext(pendingWaterData);
  }

  @Override public void updateColorScheme(int colorScheme) {
    super.updateColorScheme(colorScheme);
  }

  private void setupViewPager() {
    mViewPager = (ViewPager) view.findViewById(R.id.pager);
    landingPageFragmentPagerAdapter =
        new LandingPageFragmentPagerAdapter(getSupportFragmentManager());
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
  }

  public void updateFragmentViews() {
    landingPageFragmentPagerAdapter.caloriesFragment.updateView();
    landingPageFragmentPagerAdapter.macrosFragment.updateViews();
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

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
}
