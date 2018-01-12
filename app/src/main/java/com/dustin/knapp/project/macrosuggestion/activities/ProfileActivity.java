package com.dustin.knapp.project.macrosuggestion.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuHelper;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuItem;
import com.dustin.knapp.project.macrosuggestion.utils.storage.RealmUtils;

/**
 * Created by dknapp on 5/28/17
 */
public class ProfileActivity extends BaseNavDrawerActivity {

  View view;
  TextView currentUserName;
  TextView currentUserEmail;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    view = LayoutInflater.from(this).inflate(R.layout.profile_activity, null);

    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);

    this.content.removeAllViews();
    this.content.addView(view);

    currentUserName = ButterKnife.findById(view, R.id.tvUserName);
    currentUserEmail = ButterKnife.findById(view, R.id.tvUserEmail);

    toolbarTitle.setText(DrawerMenuItem.getTitle(ProfileActivity.class));
    toolbar.setBackground(getDrawable(R.drawable.profile_toolbar));

    if (sharedPreferencesUtil.getEnrolledUniqueUserId().equals("none")) {
      //todo oh shit
    } else {
      UserObject currentUser = RealmUtils.getCurrentUserObject(sharedPreferencesUtil.getEnrolledUniqueUserId());

      if (currentUser.getName() != null) {
        currentUserName.setText(currentUser.getName());
        currentUserEmail.setText(currentUser.getEmail());
      }
    }
  }

  @Override protected void onStart() {
    super.onStart();
    int position = DrawerMenuHelper.getNavDrawerIndex(ProfileActivity.class);
    drawerMenuAdaper.updateWithSelectedPosition(position);
    drawerMenuList.smoothScrollToPosition(position);
    updateColorScheme(4);
  }
}
