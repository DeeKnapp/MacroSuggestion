package com.dustin.knapp.project.macrosuggestion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.dustin.knapp.project.macrosuggestion.utils.HistoryRealmUtils;
import com.dustin.knapp.project.macrosuggestion.utils.storage.SharedPreferencesUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import butterknife.ButterKnife;

import static com.dustin.knapp.project.macrosuggestion.Constants.SELECTED_DAY_EXTRA;

/**
 * Created by dknapp on 6/30/17.
 */

public class HistoryCalendarActivity extends BaseStandAloneActivity {

  private MaterialCalendarView calendarView;

  private RadioButton caloriesButton;
  private RadioButton macrosButton;
  private RadioButton waterButton;

    /*
   i.e.
   On Monday you meet all your goals 85%, way to go!
   Tuesdays u suck
    */

  HistoryRealmUtils realmUtils;

  private boolean showMacrosGoals = true;
  private boolean showWaterGoals;

  DayViewDecorator dayViewDecorator;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    View view = LayoutInflater.from(this).inflate(R.layout.history_calendar, null);

    this.content.removeAllViews();
    this.content.addView(view);

    calendarView = ButterKnife.findById(this, R.id.calendarView);
    caloriesButton = ButterKnife.findById(this, R.id.caloriesButton);
    macrosButton = ButterKnife.findById(this, R.id.macrosButton);
    waterButton = ButterKnife.findById(this, R.id.waterButton);

    calendarView.setSelectedDate(DateUtils.getCurrentDateForCalendar());

    caloriesButton.setChecked(true);

    toolbar.setBackgroundColor(getResources().getColor(R.color.history_toolbar));

    toolbarTitle.setText("History");

    realmUtils = new HistoryRealmUtils(this);
    sharedPreferencesUtil = new SharedPreferencesUtil(this);

    dayViewDecorator = new DayViewDecorator() {
      @Override public boolean shouldDecorate(CalendarDay day) {
        if (showMacrosGoals) {
          return realmUtils.userCompletedMacrosGoalForGivenDay(day.getDate());
        } else if (showWaterGoals) {
          return realmUtils.userCompletedWaterGoalForGivenDay(day.getDate());
        } else {
          return realmUtils.userCompletedCaloriesGoalForGivenDay(day.getDate());
        }
      }

      @Override public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(getDrawable(R.drawable.calendar_day_complete));
      }
    };

    calendarView.addDecorator(dayViewDecorator);

    calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
      @Override public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Intent intent = new Intent(HistoryCalendarActivity.this, DailyLogActivity.class);
        intent.putExtra(SELECTED_DAY_EXTRA, DateUtils.formatDateForRealm(date.getDate()));
        startActivity(intent);
      }
    });

    setOnClickListeners();
  }

  @Override protected void onResume() {
    super.onResume();
    calendarView.setSelectedDate(DateUtils.getCurrentDate());
  }

  private void setOnClickListeners() {
    caloriesButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        showMacrosGoals = false;
        showWaterGoals = false;
        refreshCalendarView();
      }
    });

    macrosButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        showMacrosGoals = true;
        showWaterGoals = false;
        refreshCalendarView();
      }
    });

    waterButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        showMacrosGoals = false;
        showWaterGoals = true;
        refreshCalendarView();
      }
    });
  }

  private void refreshCalendarView() {
    calendarView.removeDecorator(dayViewDecorator);
    calendarView.addDecorator(dayViewDecorator);
  }
}
