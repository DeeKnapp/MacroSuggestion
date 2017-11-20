package com.dustin.knapp.project.macrosuggestion.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.dustin.knapp.project.macrosuggestion.Constants;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.activities.BarcodeScanner;
import com.dustin.knapp.project.macrosuggestion.activities.LandingPageActivity;
import com.dustin.knapp.project.macrosuggestion.models.BaseNutrition;
import com.dustin.knapp.project.macrosuggestion.models.FoodEntry;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.presenters.colories_fragment.CaloriesPresenter;
import com.dustin.knapp.project.macrosuggestion.presenters.colories_fragment.CaloriesReactiveView;
import com.dustin.knapp.project.macrosuggestion.ui.QuickAddFoodDialogFragment;
import com.dustin.knapp.project.macrosuggestion.utils.CaloriesChartUtils;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.dustin.knapp.project.macrosuggestion.utils.RealmUtils;
import com.github.mikephil.charting.charts.PieChart;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by dknapp on 4/24/17
 */
public class CaloriesFragment extends Fragment implements CaloriesReactiveView, QuickAddFoodDialogFragment.QuickAddDialogListener {

  View rootView;

  FloatingActionButton fab;

  TextView currentCalorieText, remainingCalorieText;

  float goalCalorie, currentCalorie;

  LandingPageActivity activity;

  ViewHolder caloriesViewHolder;

  CaloriesFragment fragment;

  PendingNutritionData currentPendingNutritionalData;

  @Inject public CaloriesPresenter caloriesPresenter;
  @Inject public Observable<PendingNutritionData> pendingNutritionalObservable;
  @Inject public Observer<PendingNutritionData> pendingNutritionalObserver;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    ((MacroSuggestionApplication) getActivity().getApplication()).getAppComponent().inject(this);

    rootView = inflater.inflate(R.layout.updated_calories_fragment, container, false);

    currentCalorieText = (TextView) rootView.findViewById(R.id.currentCalories);
    remainingCalorieText = (TextView) rootView.findViewById(R.id.remainingCalories);

    activity = (LandingPageActivity) getActivity();

    caloriesPresenter.setView(this);
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

        quickAddFoodDialogFragment.setListener(fragment);

        FragmentManager fm = getActivity().getSupportFragmentManager();

        quickAddFoodDialogFragment.show(fm, "Alert dialog");
      }
    });
  }
  
  @Override public void onResume() {
    super.onResume();
    pendingNutritionalObservable.subscribe(new Action1<PendingNutritionData>() {
      @Override public void call(PendingNutritionData pendingNutritionData) {
        goalCalorie = pendingNutritionData.getGoalCalorie();
        currentCalorie = pendingNutritionData.getCurrentCalories();
        currentPendingNutritionalData = pendingNutritionData;

        Log.d("Primary Key", "On Subscribe - Date: " + pendingNutritionData.getCurrentDate());
      }
    });

    updateChartView(caloriesViewHolder, currentCalorie, goalCalorie, getActivity());

    getNewQuote();
  }

  private void getNewQuote() {
    caloriesPresenter.getInspirationQuoteForSnackbar();
  }

  private void updateChartView(CaloriesFragment.ViewHolder holder, float current, float goal, Activity activity) {
    currentCalorieText.setText(String.valueOf(current));
    remainingCalorieText.setText(String.valueOf(goal - current));
    CaloriesChartUtils.updateChartViews(holder, current, goal, activity);
  }

  @Override public void onServerSuccess(String quote) {
    if (!getActivity().isDestroyed()) {
      Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.calorie_fragment_wrapper), quote, Snackbar.LENGTH_INDEFINITE)
          .setAction("Dismiss", new View.OnClickListener() {
            @Override public void onClick(View v) {
              //do nothing
            }
          })
          .setActionTextColor(Color.GREEN);
      View snackbarView = snackbar.getView();
      TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
      textView.setMaxLines(10);
      snackbar.show();
    }
  }

  @Override public void onServerError() {

  }

  @Override public void showProgressBar() {
    //probably won't use here
  }

  @Override public void dismissProgressBar() {
    //probably won't use here
  }

  public void updateView() {
    updateChartView(caloriesViewHolder, currentPendingNutritionalData.getCurrentCalories(), goalCalorie, getActivity());
  }

  @Override public void onQuickAddSubmit(final BaseNutrition baseNutrition) {

    currentPendingNutritionalData.setCurrentCalories(currentPendingNutritionalData.getCurrentCalories() + baseNutrition.calories);

    currentPendingNutritionalData.setCurrentProtein(currentPendingNutritionalData.getCurrentProtein() + baseNutrition.protein);

    currentPendingNutritionalData.setCurrentFat(currentPendingNutritionalData.getCurrentFat() + baseNutrition.fats);

    currentPendingNutritionalData.setCurrentCarb(currentPendingNutritionalData.getCurrentCarb() + baseNutrition.carbs);

    pendingNutritionalObserver.onNext(currentPendingNutritionalData);

    RealmUtils.updateCurrentDayPendingNutritionData(currentPendingNutritionalData);

    FoodEntry currentFoodEntry = new FoodEntry();

    currentFoodEntry.setCurrentDate(DateUtils.getCurrentDateString());
    currentFoodEntry.setCalories(baseNutrition.calories);
    currentFoodEntry.setProtein(baseNutrition.protein);
    currentFoodEntry.setFats(baseNutrition.fats);
    currentFoodEntry.setCarbs(baseNutrition.carbs);
    currentFoodEntry.setFoodName("Swift Add");
    currentFoodEntry.setTimeStamp(DateUtils.getCurrentTime());
    currentFoodEntry.setMealEntryType(Constants.MEAL_TYPE_QUICK_ADD);

    RealmUtils.addFoodEntryToCurrentDay(currentFoodEntry);

    activity.updateFragmentViews();
  }

  public class ViewHolder {
    public PieChart chart;
    public TextView current, remaining;
  }
}
