package com.dustin.knapp.project.macrosuggestion.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.dustin.knapp.project.macrosuggestion.fragments.FoodFragment;

/**
 * Created by dknapp on 5/16/16.
 */
public class DailyLogFragmentPagerAdapter extends FragmentPagerAdapter {
  private static final int PAGE_COUNT = 1;
  private String[] tabTitles = new String[] {"Food"};

  public FoodFragment foodFragment;

  public DailyLogFragmentPagerAdapter(FragmentManager fm) {
    super(fm);
    foodFragment = new FoodFragment();
  }

  @Override public int getCount() {
    return PAGE_COUNT;
  }

  @Override public Fragment getItem(int position) {
    if (position == 0) {
      return foodFragment;
    }
    return null;
  }

  @Override public CharSequence getPageTitle(int position) {
    // Generate title based on item position
    return tabTitles[position];
  }
}
