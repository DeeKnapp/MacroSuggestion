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
import com.dustin.knapp.project.macrosuggestion.ui.CustomScrollView;
import com.dustin.knapp.project.macrosuggestion.models.BaseNutrition;
import com.dustin.knapp.project.macrosuggestion.models.ExcessMacros;
import com.dustin.knapp.project.macrosuggestion.models.FoodEntry;
import com.dustin.knapp.project.macrosuggestion.ui.QuickAddFoodDialogFragment;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.dustin.knapp.project.macrosuggestion.utils.charts.MacrosChartUtils;
import com.dustin.knapp.project.macrosuggestion.utils.storage.FirebaseUtils;
import com.dustin.knapp.project.macrosuggestion.utils.storage.RealmUtils;
import com.github.mikephil.charting.charts.PieChart;

/**
 * Created by dknapp on 4/25/17
 */
public class MacrosFragment extends Fragment implements CustomScrollView.OnBottomReachedListener, QuickAddFoodDialogFragment.QuickAddDialogListener {

  View rootView;

  LandingPageActivity activity;

  float goalCarb;
  float goalProtein;
  float goalFat;

  float currentCarb;
  float currentProtein;
  float currentFat;

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
        quickAddFoodDialogFragment.setLayout(R.layout.macros_add_food_dialog);

        quickAddFoodDialogFragment.setListener(fragment);

        FragmentManager fm = getActivity().getSupportFragmentManager();

        quickAddFoodDialogFragment.show(fm, "Alert dialog");
      }
    });

    scrollView.setOnBottomReachedListener(this);
  }

  @Override public void onResume() {
    super.onResume();
    goalCarb = activity.currentPendingNutritionalData.getGoalCarb();
    goalProtein = activity.currentPendingNutritionalData.getGoalProtein();
    goalFat = activity.currentPendingNutritionalData.getGoalFat();

    currentCarb = activity.currentPendingNutritionalData.getCurrentCarb();
    currentProtein = activity.currentPendingNutritionalData.getCurrentProtein();
    currentFat = activity.currentPendingNutritionalData.getCurrentFat();

    MacrosChartUtils.updateChartViews(proteinViewHolder, Constants.MACRO_GRAPH_TYPE_PROTEIN, currentProtein, goalProtein);

    MacrosChartUtils.updateChartViews(carbViewHolder, Constants.MACRO_GRAPH_TYPE_CARBS, currentCarb, goalCarb);

    MacrosChartUtils.updateChartViews(fatViewHolder, Constants.MACRO_GRAPH_TYPE_FATS, currentFat, goalFat);
  }

  @Override public void onBottomReached() {
    showSnackbar();
  }

  @Override public void onQuickAddSubmit(BaseNutrition baseNutrition) {
    activity.updateObservers(baseNutrition);

    FoodEntry currentFoodEntry = new FoodEntry();

    currentFoodEntry.setCurrentDate(DateUtils.getCurrentDateString());
    currentFoodEntry.setCalories(baseNutrition.calories);
    currentFoodEntry.setProtein(baseNutrition.protein);
    currentFoodEntry.setFats(baseNutrition.fats);
    currentFoodEntry.setCarbs(baseNutrition.carbs);
    currentFoodEntry.setFoodName(baseNutrition.name);
    currentFoodEntry.setTimeStamp(DateUtils.getCurrentTime());

    RealmUtils.saveFoodEntry(currentFoodEntry);
    FirebaseUtils.saveFoodEntryToFirebase(currentFoodEntry, ((LandingPageActivity) getActivity()).sharedPreferencesUtil.getEnrolledUniqueUserId());

    activity.updateFragmentViews();
  }

  public static class ViewHolder {
    public PieChart chart;
    public TextView current, remaining;
  }

  public void updateViews() {
    MacrosChartUtils.updateChartViews(proteinViewHolder, Constants.MACRO_GRAPH_TYPE_PROTEIN,
        activity.currentPendingNutritionalData.getCurrentProtein(), goalProtein);

    MacrosChartUtils.updateChartViews(carbViewHolder, Constants.MACRO_GRAPH_TYPE_CARBS, activity.currentPendingNutritionalData.getCurrentCarb(),
        goalCarb);

    MacrosChartUtils.updateChartViews(fatViewHolder, Constants.MACRO_GRAPH_TYPE_FATS, activity.currentPendingNutritionalData.getCurrentFat(),
        goalFat);
  }

  private void showSnackbar() {
    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.scrollView), "Would you like to see food suggestions based off of extra macros",
        Snackbar.LENGTH_INDEFINITE).setAction("Yes!", new View.OnClickListener() {
      @Override public void onClick(View v) {
        ExcessMacros excessMacros = new ExcessMacros();

        excessMacros.maxCarbs = Math.round(goalCarb - currentCarb);
        excessMacros.maxFat = Math.round(goalFat - currentFat);
        excessMacros.maxProtein = Math.round(goalProtein - currentProtein);

        excessMacros.maxCalories = excessMacros.maxCarbs * 4 + excessMacros.maxProtein * 4 + excessMacros.maxFat * 9;

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

