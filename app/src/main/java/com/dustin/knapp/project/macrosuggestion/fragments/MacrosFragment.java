package com.dustin.knapp.project.macrosuggestion.fragments;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.dustin.knapp.project.macrosuggestion.Constants;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.activities.LandingPageActivity;
import com.dustin.knapp.project.macrosuggestion.activities.MacroSuggestionResults;
import com.dustin.knapp.project.macrosuggestion.adapters.CustomScrollView;
import com.dustin.knapp.project.macrosuggestion.models.BaseNutrition;
import com.dustin.knapp.project.macrosuggestion.models.ExcessMacros;
import com.dustin.knapp.project.macrosuggestion.models.FoodEntry;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.ui.QuickAddFoodDialogFragment;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.dustin.knapp.project.macrosuggestion.utils.MacrosChartUtils;
import com.dustin.knapp.project.macrosuggestion.utils.RealmUtils;
import com.github.mikephil.charting.charts.PieChart;
import javax.inject.Inject;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by dknapp on 4/25/17
 */
public class MacrosFragment extends Fragment implements CustomScrollView.OnBottomReachedListener, QuickAddFoodDialogFragment.QuickAddDialogListener {

  View rootView;

  @Inject public Observable<PendingNutritionData> pendingNutritionalObservable;
  @Inject public Observer<PendingNutritionData> pendingNutritionalObserver;

  LandingPageActivity activity;

  float goalCarb;
  float goalProtein;
  float goalFat;

  float currentCarb;
  float currentProtein;
  float currentFat;

  float proteinRemaining = goalProtein - currentProtein;
  float carbRemaining = goalCarb - currentCarb;
  float fatRemaining = goalFat - currentFat;

  PendingNutritionData currentPendingNutritionalData;

  CustomScrollView scrollView;

  MacrosFragment.ViewHolder proteinViewHolder;

  MacrosFragment.ViewHolder carbViewHolder;

  MacrosFragment.ViewHolder fatViewHolder;

  FloatingActionButton fab;

  MacrosFragment fragment;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ((MacroSuggestionApplication) getActivity().getApplication()).getAppComponent().inject(this);
    rootView = inflater.inflate(R.layout.macros_layout, container, false);

    activity = (LandingPageActivity) getActivity();

    fragment = this;

    return rootView;
  }

  @RequiresApi(api = Build.VERSION_CODES.M) @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    proteinViewHolder = new MacrosFragment.ViewHolder();
    proteinViewHolder.chart = (PieChart) view.findViewById(R.id.proteinChart);
    proteinViewHolder.current = (TextView) view.findViewById(R.id.proteinCurrent);
    proteinViewHolder.remaining = (TextView) view.findViewById(R.id.proteinRemaining);

    carbViewHolder = new MacrosFragment.ViewHolder();
    carbViewHolder.chart = (PieChart) view.findViewById(R.id.carbChart);
    carbViewHolder.current = (TextView) view.findViewById(R.id.carbCurrent);
    carbViewHolder.remaining = (TextView) view.findViewById(R.id.carbRemaining);

    fatViewHolder = new MacrosFragment.ViewHolder();
    fatViewHolder.chart = (PieChart) view.findViewById(R.id.fatChart);
    fatViewHolder.current = (TextView) view.findViewById(R.id.fatCurrent);
    fatViewHolder.remaining = (TextView) view.findViewById(R.id.fatRemaining);

    scrollView = ButterKnife.findById(view, R.id.scrollView);

    fab = ButterKnife.findById(view, R.id.fab);

    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        QuickAddFoodDialogFragment quickAddFoodDialogFragment = QuickAddFoodDialogFragment.newInstance();

        quickAddFoodDialogFragment.setListener(fragment);

        FragmentManager fm = getActivity().getSupportFragmentManager();

        quickAddFoodDialogFragment.show(fm, "Alert dialog");
      }
    });

    scrollView.setOnBottomReachedListener(this);
  }

  @Override public void onResume() {
    super.onResume();
    pendingNutritionalObservable.subscribe(new Action1<PendingNutritionData>() {
      @Override public void call(PendingNutritionData pendingNutritionData) {
        goalCarb = pendingNutritionData.getGoalCarb();
        goalProtein = pendingNutritionData.getGoalProtein();
        goalFat = pendingNutritionData.getGoalFat();

        currentCarb = pendingNutritionData.getCurrentCarb();
        currentProtein = pendingNutritionData.getCurrentProtein();
        currentFat = pendingNutritionData.getCurrentFat();
      }
    });

    MacrosChartUtils.updateChartViews(proteinViewHolder, Constants.MACRO_GRAPH_TYPE_PROTEIN, currentProtein, goalProtein);

    MacrosChartUtils.updateChartViews(carbViewHolder, Constants.MACRO_GRAPH_TYPE_CARBS, currentCarb, goalCarb);

    MacrosChartUtils.updateChartViews(fatViewHolder, Constants.MACRO_GRAPH_TYPE_FATS, currentFat, goalFat);
  }

  @Override public void onBottomReached() {
    showSnackbar();
  }

  @Override public void onQuickAddSubmit(BaseNutrition baseNutrition) {
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

  public static class ViewHolder {
    public PieChart chart;
    public TextView current, remaining;
  }

  public void updateViews() {
    MacrosChartUtils.updateChartViews(proteinViewHolder, Constants.MACRO_GRAPH_TYPE_PROTEIN, currentProtein, goalProtein);

    MacrosChartUtils.updateChartViews(carbViewHolder, Constants.MACRO_GRAPH_TYPE_CARBS, currentCarb, goalCarb);

    MacrosChartUtils.updateChartViews(fatViewHolder, Constants.MACRO_GRAPH_TYPE_FATS, currentFat, goalFat);
  }

  private void showSnackbar() {
    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.scrollView), "Would you like to see food suggestions based off of extra macros",
        Snackbar.LENGTH_INDEFINITE).setAction("Yes!", new View.OnClickListener() {
      @Override public void onClick(View v) {
        ExcessMacros excessMacros = new ExcessMacros();

        excessMacros.maxCarbs = (int) carbRemaining;
        excessMacros.maxFat = (int) fatRemaining;
        excessMacros.maxProtein = (int) proteinRemaining;

        excessMacros.maxCalories = (int) (carbRemaining * 4 + proteinRemaining * 4 + fatRemaining * 9);

        Intent i = new Intent(getActivity(), MacroSuggestionResults.class);
        i.putExtra("macros_remaining", excessMacros);
        startActivity(i);
      }
    }).setActionTextColor(Color.GREEN);
    View snackbarView = snackbar.getView();
    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
    textView.setMaxLines(10);
    snackbar.show();
  }
}

