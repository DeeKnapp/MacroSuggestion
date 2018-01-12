package com.dustin.knapp.project.macrosuggestion.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.activities.LandingPageActivity;
import com.dustin.knapp.project.macrosuggestion.models.BaseNutrition;
import com.dustin.knapp.project.macrosuggestion.models.FoodEntry;
import com.dustin.knapp.project.macrosuggestion.ui.QuickAddFoodDialogFragment;
import com.dustin.knapp.project.macrosuggestion.utils.charts.CaloriesChartUtils;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.dustin.knapp.project.macrosuggestion.utils.storage.FirebaseUtils;
import com.dustin.knapp.project.macrosuggestion.utils.storage.RealmUtils;
import com.github.mikephil.charting.charts.PieChart;

/**
 * Created by dknapp on 4/24/17
 */
public class CaloriesFragment extends Fragment implements QuickAddFoodDialogFragment.QuickAddDialogListener {

  View rootView;

  FloatingActionButton fab;

  TextView currentCalorieText, remainingCalorieText;

  float goalCalorie, currentCalorie;

  LandingPageActivity activity;

  ViewHolder caloriesViewHolder;

  CaloriesFragment fragment;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    ((MacroSuggestionApplication) getActivity().getApplication()).getAppComponent().inject(this);

    rootView = inflater.inflate(R.layout.updated_calories_fragment, container, false);

    currentCalorieText = (TextView) rootView.findViewById(R.id.currentCalories);
    remainingCalorieText = (TextView) rootView.findViewById(R.id.remainingCalories);

    activity = (LandingPageActivity) getActivity();

    this.fragment = this;
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    caloriesViewHolder = new CaloriesFragment.ViewHolder();
    caloriesViewHolder.chart = (PieChart) view.findViewById(R.id.calorie_chart);
    caloriesViewHolder.current = currentCalorieText;
    caloriesViewHolder.remaining = remainingCalorieText;

    fab = (FloatingActionButton) view.findViewById(R.id.fab);

    //todo launch quick add dialog overlay
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        QuickAddFoodDialogFragment quickAddFoodDialogFragment = QuickAddFoodDialogFragment.newInstance();
        quickAddFoodDialogFragment.setLayout(R.layout.calories_add_food_dialog);

        quickAddFoodDialogFragment.setListener(fragment);

        FragmentManager fm = getActivity().getSupportFragmentManager();

        quickAddFoodDialogFragment.show(fm, "Alert dialog");
      }
    });
  }

  //comment
  @Override public void onResume() {
    super.onResume();

    goalCalorie = activity.currentPendingNutritionalData.getGoalCalorie();
    currentCalorie = activity.currentPendingNutritionalData.getCurrentCalories();

    updateChartView(caloriesViewHolder, currentCalorie, goalCalorie, getActivity());
  }

  private void updateChartView(CaloriesFragment.ViewHolder holder, float current, float goal, Activity activity) {
    currentCalorieText.setText(String.valueOf(current));
    remainingCalorieText.setText(String.valueOf(goal - current));
    CaloriesChartUtils.updateChartViews(holder, current, goal, activity);
  }

  public void updateView() {
    updateChartView(caloriesViewHolder, activity.currentPendingNutritionalData.getCurrentCalories(), goalCalorie, getActivity());
  }

  @Override public void onQuickAddSubmit(final BaseNutrition baseNutrition) {

    activity.updateObservers(baseNutrition);

    FoodEntry currentFoodEntry = new FoodEntry();

    currentFoodEntry.setCurrentDate(DateUtils.getCurrentDateString());
    currentFoodEntry.setCalories(baseNutrition.calories);
    currentFoodEntry.setProtein(baseNutrition.protein);
    currentFoodEntry.setFats(baseNutrition.fats);
    currentFoodEntry.setCarbs(baseNutrition.carbs);
    currentFoodEntry.setFoodName(baseNutrition.name);
    currentFoodEntry.setTimeStamp(DateUtils.getCurrentTime());

    FirebaseUtils.saveFoodEntryToFirebase(currentFoodEntry, ((LandingPageActivity) getActivity()).sharedPreferencesUtil.getEnrolledUniqueUserId());

    RealmUtils.saveFoodEntry(currentFoodEntry);

    activity.updateFragmentViews();
  }

  public class ViewHolder {
    public PieChart chart;
    public TextView current, remaining;
  }
}
