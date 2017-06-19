package com.dustin.knapp.project.macrosuggestion.navigation_drawer;

import android.util.SparseArray;
import com.dustin.knapp.project.macrosuggestion.activities.BarcodeScanner;
import com.dustin.knapp.project.macrosuggestion.activities.DailyLogActivity;
import com.dustin.knapp.project.macrosuggestion.activities.LandingPageActivity;
import com.dustin.knapp.project.macrosuggestion.activities.ProfileActivity;
import com.dustin.knapp.project.macrosuggestion.activities.SignoutActivity;

/**
 * Created by jmai on 1/3/17.
 */
public final class DrawerMenuHelper {
  private static SparseArray<Class> sparseArray = new SparseArray<>();

  private DrawerMenuHelper() {

  }

  @SuppressWarnings("checkstyle:magicnumber") public static DrawerMenuItem[] getMenuItems() {
    final DrawerMenuItem[] items = new DrawerMenuItem[4];
    items[0] = DrawerMenuItem.HOME;
    sparseArray.put(0, LandingPageActivity.class);
    items[1] = DrawerMenuItem.DAILYLOG;
    sparseArray.put(1, DailyLogActivity.class);
    items[2] = DrawerMenuItem.PROFILE;
    sparseArray.put(2, ProfileActivity.class);
    items[3] = DrawerMenuItem.SIGNOUT;
    sparseArray.put(3, SignoutActivity.class);
    return items;
  }

  public static int getNavDrawerIndex(Class clazz) {
    return sparseArray.indexOfValue(clazz);
  }

  public static Class getClazz(int position) {
    return sparseArray.get(position);
  }
}
