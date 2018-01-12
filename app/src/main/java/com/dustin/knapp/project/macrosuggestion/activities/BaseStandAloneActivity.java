package com.dustin.knapp.project.macrosuggestion.activities;

import android.os.Bundle;
import android.view.View;
import com.dustin.knapp.project.macrosuggestion.R;

/**
 * Created by dknapp on 11/27/17.
 */

public abstract  class BaseStandAloneActivity extends BaseNavDrawerActivity {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mDrawerToggle.setDrawerIndicatorEnabled(false);
    // Show back button
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onBackPressed();
      }
    });
  }
}
