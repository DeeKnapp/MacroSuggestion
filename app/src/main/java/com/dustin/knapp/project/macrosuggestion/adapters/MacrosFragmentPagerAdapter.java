package com.dustin.knapp.project.macrosuggestion.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.dustin.knapp.project.macrosuggestion.fragments.CarbFragment;
import com.dustin.knapp.project.macrosuggestion.fragments.FatFragment;
import com.dustin.knapp.project.macrosuggestion.fragments.ProteinFragment;

/**
 * Created by dknapp on 5/16/16.
 */
public class MacrosFragmentPagerAdapter extends FragmentPagerAdapter {
  private static final int PAGE_COUNT = 3;
  private String[] tabTitles = new String[] {"Protein", "Fats", "Carbs"};

  public ProteinFragment proteinFragment;
  public FatFragment fatFragment;
  public CarbFragment carbFragment;

  public MacrosFragmentPagerAdapter(FragmentManager fm) {
    super(fm);
    proteinFragment = new ProteinFragment();
    fatFragment = new FatFragment();
    carbFragment = new CarbFragment();
  }

  @Override public int getCount() {
    return PAGE_COUNT;
  }

  @Override public Fragment getItem(int position) {
    if (position == 0) {
      return proteinFragment;
    } else if (position == 1) {
      return fatFragment;
    } else if (position == 2) {
      return carbFragment;
    } else {
      return null;
    }
  }

  @Override public CharSequence getPageTitle(int position) {
    // Generate title based on item position
    return null;
  }
}
