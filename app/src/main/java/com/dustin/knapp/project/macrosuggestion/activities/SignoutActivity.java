package com.dustin.knapp.project.macrosuggestion.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;

import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuHelper;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuItem;

import butterknife.ButterKnife;

public class SignoutActivity extends BaseNavDrawerActivity {

  @Override public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);
    unbinder = ButterKnife.bind(this);
    View view = LayoutInflater.from(this).inflate(R.layout.sign_out, null);
    ViewGroup content = (ViewGroup) findViewById(R.id.content);
    content.removeAllViews();
    content.addView(view);

    toolbarTitle.setText(DrawerMenuItem.getTitle(SignoutActivity.class));
    toolbar.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
  }

  @Override protected void onStart() {
    super.onStart();
    int position = DrawerMenuHelper.getNavDrawerIndex(SignoutActivity.class);
    drawerMenuAdaper.updateWithSelectedPosition(position);
    drawerMenuList.smoothScrollToPosition(position);
    finish();
  }
}
