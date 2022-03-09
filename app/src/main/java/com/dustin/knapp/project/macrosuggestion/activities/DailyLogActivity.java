package com.dustin.knapp.project.macrosuggestion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.FoodEntry;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuHelper;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuItem;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;

import static android.view.View.GONE;
import static com.dustin.knapp.project.macrosuggestion.Constants.SELECTED_DAY_EXTRA;

public class DailyLogActivity extends BaseNavDrawerActivity {

  LinearLayout foodEntryContainer;
  FloatingActionButton fabCalendar;

  @Inject public Observer<Integer> pendingMealTypeObserver;
  @Inject public Observer<PendingNutritionData> pendingNutritionalObserver;
  @Inject public Observable<PendingNutritionData> pendingNutritionDataObservable;
  View view;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    view = LayoutInflater.from(this).inflate(R.layout.daily_log_activity, null);

    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);
    this.content.removeAllViews();
    this.content.addView(view);

    foodEntryContainer = ButterKnife.findById(this, R.id.entryListContainer);
    fabCalendar = ButterKnife.findById(this, R.id.fab);

    String day = getIntent().getStringExtra(SELECTED_DAY_EXTRA);
    if (day != null) {
      toolbarTitle.setText(DateUtils.getReadableDate(day));
      fabCalendar.setVisibility(GONE);
      findViewById(R.id.fabSpace).setVisibility(GONE);
    } else {
      toolbarTitle.setText(DrawerMenuItem.getTitle(DailyLogActivity.class));
      fabCalendar.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          startActivity(new Intent(getApplicationContext(), HistoryCalendarActivity.class));
        }
      });
    }
    setUpDailyMeals(day);
  }

  @Override protected void onStart() {
    super.onStart();
    int position = DrawerMenuHelper.getNavDrawerIndex(DailyLogActivity.class);
    drawerMenuAdaper.updateWithSelectedPosition(position);
    drawerMenuList.smoothScrollToPosition(position);
    updateColorScheme(3);
  }

  private void setUpDailyMeals(String day) {
    foodEntryContainer.removeAllViews();

    List<FoodEntry> foodEntryList = new ArrayList<>();

    //todo this will use FirebaseUtils instead
    //if (day == null) {
    //  foodEntryList = RealmUtils.getCurrentDayFoodEntries();
    //} else {
    //  foodEntryList = RealmUtils.getPastDayFoodEntries(day);
    //}

    if (foodEntryList.size() > 0) {
      for (FoodEntry entry : foodEntryList) {
        Log.d("Test", "Food Entry name: " + entry.getFoodName());
        foodEntryContainer.addView(generateFoodLogItem(entry.getFoodName(), entry.getCalories(), entry.getTimeStamp()));
      }
    } else {
      foodEntryContainer.addView(generateNoItemsView());
    }
  }

  private View generateNoItemsView() {
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    return inflater.inflate(R.layout.no_items_view, null);
  }

  public View generateFoodLogItem(String foodName, float calories, String timeStamp) {
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    View view = inflater.inflate(R.layout.base_log_item, null);

    TextView tvTimeStamp = (TextView) view.findViewById(R.id.tvTimeStamp);
    TextView tvFoodName = (TextView) view.findViewById(R.id.tvFoodName);
    TextView tvCalories = (TextView) view.findViewById(R.id.tvCalories);

    tvTimeStamp.setText(timeStamp);
    tvFoodName.setText(foodName);
    tvCalories.setText(calories + "");

    return view;
  }
}
