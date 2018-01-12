package com.dustin.knapp.project.macrosuggestion.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by dknapp on 5/8/17
 */
public class DateUtils {

  private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

  public static Date getCurrentDateForCalendar() {
    Calendar c = Calendar.getInstance();
    System.out.println("Current time => " + c.getTime());

    return c.getTime();
  }

  public static String getCurrentDateString() {
    Calendar c = Calendar.getInstance();
    System.out.println("Current time => " + c.getTime());

    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    return df.format(c.getTime());
  }

  public static Date getCurrentDate() {
    Calendar c = Calendar.getInstance();
    System.out.println("Current time => " + c.getTime());

    return c.getTime();
  }

  public static String formatDateForRealm(Date date) {
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    return df.format(date);
  }

  public static String getReadableDate(String unformattedDate) {
    if (unformattedDate == null || unformattedDate.trim().isEmpty()) {
      return "";
    }
    String[] stringChunks = unformattedDate.split("-");

    int monthNumber = Integer.parseInt(stringChunks[1]);
    String month = MONTHS[monthNumber - 1];

    if (stringChunks[2].startsWith("0")) {
      stringChunks[2] = stringChunks[2].substring(1);
    }
    return month + " " + stringChunks[0] + ", " + stringChunks[2];
  }

  public static String getCurrentTime() {
    Calendar c = Calendar.getInstance();
    System.out.println("Current time => " + c.getTime());

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

    GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
    calendar.setTimeInMillis(c.getTimeInMillis());

    String[] newString = df.format(calendar.getTime()).split(" ");
    return convertTo12HourTime(newString[1].split(",")[0]);
  }

  private static String convertTo12HourTime(String oldTime) {
    try {
      final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
      final Date dateObj = sdf.parse(oldTime);
      return new SimpleDateFormat("K:mm aa").format(dateObj);
    } catch (final ParseException e) {
      e.printStackTrace();
    }
    return "";
  }
}
