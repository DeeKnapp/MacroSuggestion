package com.dustin.knapp.project.macrosuggestion.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.adapters.DailyLogFragmentPagerAdapter;
import com.dustin.knapp.project.macrosuggestion.models.PendingWaterData;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuHelper;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuItem;
import com.dustin.knapp.project.macrosuggestion.presenters.colories_fragment.CaloriesPresenter;
import javax.inject.Inject;
import rx.Observable;
import rx.Observer;

public class DailyLogActivity extends BaseNavDrawerActivity {

  ViewPager mViewPager;

  private View view;
  @Inject public Observable<PendingWaterData> pendingWaterObservable;
  @Inject public Observer<PendingWaterData> pendingWaterObserver;

  @Inject public CaloriesPresenter caloriesPresenter;

  DailyLogFragmentPagerAdapter dailyLogFragmentPagerAdapter;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    view = LayoutInflater.from(this).inflate(R.layout.landing_page, null);

    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);

    PendingWaterData pendingWaterData = new PendingWaterData();
    pendingWaterData.currentWater = 16;
    pendingWaterData.goalWater = 64;

    pendingWaterObserver.onNext(pendingWaterData);

    toolbarTitle.setText(DrawerMenuItem.getTitle(DailyLogActivity.class));
    toolbar.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));

    setupViewPager();
    this.content.removeAllViews();
    this.content.addView(view);

    toolbarTitle.setText(DrawerMenuItem.getTitle(DailyLogActivity.class));
    toolbar.setBackgroundColor(
        ResourcesCompat.getColor(getResources(), R.color.dgm_dark_green, null));
  }

  private void setupViewPager() {
    mViewPager = (ViewPager) view.findViewById(R.id.pager);
    dailyLogFragmentPagerAdapter = new DailyLogFragmentPagerAdapter(getSupportFragmentManager());
    TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
    mViewPager.setAdapter(dailyLogFragmentPagerAdapter);
    tabLayout.setupWithViewPager(mViewPager);
  }

  @Override protected void onStart() {
    super.onStart();
    int position = DrawerMenuHelper.getNavDrawerIndex(DailyLogActivity.class);
    drawerMenuAdaper.updateWithSelectedPosition(position);
    drawerMenuList.smoothScrollToPosition(position);
  }


  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    dailyLogFragmentPagerAdapter.getItem(0)
        .onRequestPermissionsResult(requestCode, permissions, grantResults);
  }
}
