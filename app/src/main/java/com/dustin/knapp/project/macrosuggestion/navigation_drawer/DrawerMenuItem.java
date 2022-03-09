package com.dustin.knapp.project.macrosuggestion.navigation_drawer;

import androidx.collection.ArrayMap;

import com.dustin.knapp.project.macrosuggestion.activities.DailyLogActivity;
import com.dustin.knapp.project.macrosuggestion.activities.LandingPageActivity;
import com.dustin.knapp.project.macrosuggestion.activities.ProfileActivity;
import com.dustin.knapp.project.macrosuggestion.activities.SignoutActivity;

import java.util.EnumSet;
import java.util.Map;

/**
 * Created by jmai on 5/23/16.
 */
public enum DrawerMenuItem {
  HOME(0, "Home", "Home", LandingPageActivity.class),
  DAILYLOG(1, "Daily Log", "Daily Log", DailyLogActivity.class),
  PROFILE(2, "Profile", "Profile", ProfileActivity.class),
  SIGNOUT(3, "Sign Out", "Sign Out", SignoutActivity.class);

  private final int position;
  private final String navName;
  private final String title;
  private final Class clazz;
  private static final Map<Class, String> TITLE_LOOKUP = new ArrayMap<>();
  private static final Map<Class, Integer> POSITION_LOOKUP = new ArrayMap<>();
  private static final Map<Integer, Class> CLASS_LOOKUP = new ArrayMap<>();

  static {
    for (DrawerMenuItem menuItem : EnumSet.allOf(DrawerMenuItem.class)) {
      TITLE_LOOKUP.put(menuItem.clazz, menuItem.title);
      POSITION_LOOKUP.put(menuItem.clazz, menuItem.position);
      CLASS_LOOKUP.put(menuItem.position, menuItem.clazz);
    }
  }

  DrawerMenuItem(int position, String navName, String title, Class clazz) {
    this.position = position;
    this.navName = navName;
    this.title = title;
    this.clazz = clazz;
  }

  public String getTitle() {
    return this.title;
  }

  public static String getTitle(Class fragmentClass) {
    return TITLE_LOOKUP.get(fragmentClass);
  }

  //public static int getPosition(Class fragmentClass) {
  //  return POSITION_LOOKUP.get(fragmentClass);
  //}

  //public static Class getClazz(int index) {
  //  return CLASS_LOOKUP.get(index);
  //}

  public String getNavName() {
    return this.navName;
  }

}
