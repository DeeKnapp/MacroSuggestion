package com.dustin.knapp.project.macrosuggestion.activities;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dknapp on 6/30/17.
 */

public class HistoryCalendarActivity extends BaseActivity {

    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;


    /*
   Calendar view add spinner for different goal types
   fuck trying to manipulate the calendar cells...
   we can adjust the background of the views GREEN / RED
   for goal met or failed...
   Accumulate stats based on user input.
   Start tracking,

   i.e.
   On Monday you meet all your goals 85%, way to go!
   Tuesdays u suck
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_calendar);

        unbinder = ButterKnife.bind(this);

        calendarView.setSelectedDate(DateUtils.getCurrentDateForCalendar());

        DayViewDecorator dayViewDecorator = new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                //todo check past goals met here and return true if met
                return !day.getDate().equals(DateUtils.getCurrentDate());
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.setBackgroundDrawable(getDrawable(R.drawable.calendar_item_selector));
            }
        };

        calendarView.addDecorator(dayViewDecorator);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

            }
        });
    }
}
