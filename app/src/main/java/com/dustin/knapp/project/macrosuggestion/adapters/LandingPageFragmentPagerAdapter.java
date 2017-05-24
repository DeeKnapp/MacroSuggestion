package com.dustin.knapp.project.macrosuggestion.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.dustin.knapp.project.macrosuggestion.activities.fragments.CaloriesFragment;
import com.dustin.knapp.project.macrosuggestion.activities.fragments.MacrosFragment;
import com.dustin.knapp.project.macrosuggestion.activities.fragments.MacrosFragment2;

/**
 * Created by dknapp on 5/16/16.
 */
public class LandingPageFragmentPagerAdapter extends FragmentPagerAdapter {
  private static final int PAGE_COUNT = 3;
  private String[] tabTitles = new String[] {"Calories", "Macros", "Macros 2"};

  public CaloriesFragment caloriesFragment;
  public MacrosFragment macrosFragment;
  public MacrosFragment2 macrosFragment2;

  public LandingPageFragmentPagerAdapter(FragmentManager fm) {
    super(fm);
    caloriesFragment = new CaloriesFragment();
    macrosFragment = new MacrosFragment();
    macrosFragment2 = new MacrosFragment2();
  }

  @Override public int getCount() {
    return PAGE_COUNT;
  }

  @Override public Fragment getItem(int position) {
    if (position == 0) {
      return caloriesFragment;
    } else if (position == 1) {
      return macrosFragment;
    } else if (position == 2) {
      return macrosFragment2;
    } else {
      return null;
    }
  }

  @Override public CharSequence getPageTitle(int position) {
    // Generate title based on item position
    return tabTitles[position];
  }
}
