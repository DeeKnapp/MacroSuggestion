package com.dustin.knapp.project.macrosuggestion.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuHelper;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuItem;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuItemAdapter;

/**
 * Created by dknapp on 4/26/17
 */
public class BaseNavDrawerActivity extends BaseActivity {

  private static final int DELAY_CLOSE_DRAWER = 300;

  protected Activity activity;
  protected DrawerMenuItemAdapter drawerMenuAdaper;

  @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
  public @BindView(R.id.content) FrameLayout content;
  @BindView(R.id.status_bar_mask) View statusBarSpaceView;

  @BindView(R.id.list_view_inside_nav) ListView drawerMenuList;
  @BindView(R.id.navigation_view) NavigationView navigationView;
  @BindView(R.id.last_divider) View lastDivider;

  public Toolbar toolbar;
  public TextView toolbarTitle;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.base_nav_drawer_activity);
    unbinder = ButterKnife.bind(this);

    LinearLayout.LayoutParams params =
        (LinearLayout.LayoutParams) statusBarSpaceView.getLayoutParams();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      params.height = getStatusBarHeight();
    } else {
      params.height = 0;
    }
    statusBarSpaceView.setLayoutParams(params);

    content = (FrameLayout) findViewById(R.id.content);

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    toolbarTitle = (TextView) findViewById(R.id.toolbar_title);

    ActionBarDrawerToggle mDrawerToggle =
        new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,
            R.string.drawer_close) {

          public void onDrawerClosed(View drawerView) {
            // Code here will be triggered once the drawer closes
            // as we dont want anything to happen so we leave this blank
            super.onDrawerClosed(drawerView);
          }

          /** Called when a drawer has settled in a completely open state. */
          public void onDrawerOpened(View drawerView) {
            //close keyboard if any
            InputMethodManager inputMethodManager =
                (InputMethodManager) BaseNavDrawerActivity.this.getSystemService(
                    INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                BaseNavDrawerActivity.this.getCurrentFocus().getWindowToken(), 0);
            super.onDrawerOpened(drawerView);
          }
        };

    getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);

    // Set the drawer toggle as the DrawerListener
    mDrawerLayout.addDrawerListener(mDrawerToggle);
    mDrawerToggle.syncState();
  }

  @Override protected void onStart() {
    super.onStart();
    DrawerMenuItem[] items;

    items = DrawerMenuHelper.getMenuItems();

    drawerMenuAdaper = new DrawerMenuItemAdapter(this, items);

    drawerMenuList.setAdapter(drawerMenuAdaper);
    drawerMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Class nextActivityClass = DrawerMenuHelper.getClazz(position);
        if (nextActivityClass != null) {
          if (SignoutActivity.class == nextActivityClass) {
            Intent i = new Intent(BaseNavDrawerActivity.this, LoginActivity.class);
            // make sure login clears everything else behind this activity
            //todo clear all local stored data
            sharedPreferencesUtil.storeUserIsEnrolled(false);
            startActivity(
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
          } else {
            Intent intent = new Intent(getCurrentActivity(), nextActivityClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            closeDrawerAndStartNewActivity(intent);
          }
        }
      }
    });
  }

  public void updateColorScheme(int colorScheme) {
    switch (colorScheme) {
      case 0:
        Log.d("Color Update", "Updating color scheme for int:" + colorScheme);
        toolbar.setBackground(getDrawable(R.drawable.calories_toolbar));
        navigationView.setBackgroundColor(
            getResources().getColor(R.color.caloriesNavDrawerBackground));
        drawerMenuAdaper.updateColorScheme(colorScheme);
        drawerMenuList.setDivider(new ColorDrawable(0xFFD86639));
        drawerMenuList.setDividerHeight(1);
        drawerMenuList.setBackgroundColor(
            getResources().getColor(R.color.caloriesNavDrawerBackground));
        lastDivider.setBackgroundColor(
            getResources().getColor(R.color.caloriesNavItemSelectedColor));
        drawerMenuAdaper.notifyDataSetChanged();
        break;
      case 1:
        Log.d("Color Update", "Updating color scheme for int:" + colorScheme);
        toolbar.setBackground(getDrawable(R.drawable.macros_toolbar));
        navigationView.setBackgroundColor(
            getResources().getColor(R.color.macrosNavDrawerBackground));
        drawerMenuAdaper.updateColorScheme(colorScheme);
        drawerMenuList.setDivider(new ColorDrawable(0xffc965ec));
        drawerMenuList.setDividerHeight(1);
        drawerMenuList.setBackgroundColor(
            getResources().getColor(R.color.macrosNavDrawerBackground));
        lastDivider.setBackgroundColor(getResources().getColor(R.color.macrosNavItemSelectedColor));
        drawerMenuAdaper.notifyDataSetChanged();
        break;
      case 2:
        Log.d("Color Update", "Updating color scheme for int:" + colorScheme);
        toolbar.setBackground(getDrawable(R.drawable.water_toolbar));
        navigationView.setBackgroundColor(
            getResources().getColor(R.color.waterNavDrawerBackground));
        drawerMenuAdaper.updateColorScheme(colorScheme);
        drawerMenuList.setDivider(new ColorDrawable(0xFF69C8E1));
        drawerMenuList.setDividerHeight(1);
        drawerMenuList.setBackgroundColor(
            getResources().getColor(R.color.waterNavDrawerBackground));
        lastDivider.setBackgroundColor(getResources().getColor(R.color.waterNavItemSelectedColor));
        drawerMenuAdaper.notifyDataSetChanged();
        break;
      case 3:
        Log.d("Color Update", "Updating color scheme for int:" + colorScheme);
        toolbar.setBackground(getDrawable(R.drawable.log_toolbar));
        navigationView.setBackgroundColor(getResources().getColor(R.color.logNavDrawerBackground));
        drawerMenuAdaper.updateColorScheme(colorScheme);
        drawerMenuList.setDivider(new ColorDrawable(0xff66bb6a));
        drawerMenuList.setDividerHeight(1);
        drawerMenuList.setBackgroundColor(getResources().getColor(R.color.logNavDrawerBackground));
        lastDivider.setBackgroundColor(getResources().getColor(R.color.logNavItemSelectedColor));
        drawerMenuAdaper.notifyDataSetChanged();
        break;
      default:
        break;
    }
  }

  @Override public void onBackPressed() {
    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
      mDrawerLayout.closeDrawer(GravityCompat.START);
    } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
      finish();
    } else {
      super.onBackPressed();
      //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
  }

  private int getStatusBarHeight() {
    int result = 0;
    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }

  private Activity getCurrentActivity() {
    return this;
  }

  private void closeDrawerAndStartNewActivity(Intent intent) {
    startActivity(intent);
    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    //drawerLayout.closeDrawer(GravityCompat.START);
    closeDrawerWithPostDelay();
  }

  private void closeDrawerWithPostDelay() {
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        if (mDrawerLayout != null) {
          mDrawerLayout.closeDrawer(GravityCompat.START);
        }
      }
    }, DELAY_CLOSE_DRAWER);
  }

  /* Called whenever we call invalidateOptionsMenu() */
  @Override public boolean onPrepareOptionsMenu(Menu menu) {
    // If the nav drawer is open, hide action items related to the content view
    return super.onPrepareOptionsMenu(menu);
  }
}
