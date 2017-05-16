package com.dustin.knapp.project.macrosuggestion.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.dustin.knapp.project.macrosuggestion.activities.fragments.CaloriesFragment;
import com.dustin.knapp.project.macrosuggestion.activities.fragments.FoodFragment;
import com.dustin.knapp.project.macrosuggestion.activities.fragments.MacrosFragment;
import com.dustin.knapp.project.macrosuggestion.activities.fragments.WaterFragment;

/**
 * Created by dknapp on 5/16/16.
 */
public class DailyLogFragmentPagerAdapter extends FragmentPagerAdapter {
  private static final int PAGE_COUNT = 2;
  private String[] tabTitles = new String[] { "Food", "Water" };

  public FoodFragment foodFragment;
  public WaterFragment waterFragment;

  public DailyLogFragmentPagerAdapter(FragmentManager fm) {
    super(fm);
    foodFragment = new FoodFragment();
    waterFragment = new WaterFragment();
  }

  @Override public int getCount() {
    return PAGE_COUNT;
  }

  @Override public Fragment getItem(int position) {
    if (position == 0) {
      return foodFragment;
    } else if (position == 1) {
      return waterFragment;
    } else {
      return null;
    }
  }

  @Override public CharSequence getPageTitle(int position) {
    // Generate title based on item position
    return tabTitles[position];
  }
}
