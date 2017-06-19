package com.dustin.knapp.project.macrosuggestion.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.utils.ProteinChartUtils;
import com.github.mikephil.charting.charts.PieChart;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by dknapp on 6/1/17
 */
public class FatFragment extends Fragment {

  View rootView;

  float goalFat, currentFat;

  public TextView currentFatTextView, remainingFatTextView;

  MacrosFragment.ViewHolder fatViewHolder;

  @Inject public Observable<PendingNutritionData> pendingNutritionalObservable;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ((MacroSuggestionApplication) getActivity().getApplication()).getAppComponent().inject(this);
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.fats_fragment, container, false);

      currentFatTextView = (TextView) rootView.findViewById(R.id.currentFat);
      remainingFatTextView = (TextView) rootView.findViewById(R.id.remainingFat);
    }
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    fatViewHolder = new MacrosFragment.ViewHolder();
    fatViewHolder.chart = (PieChart) view.findViewById(R.id.fat_chart);
    fatViewHolder.current = currentFatTextView;
    fatViewHolder.remaining = remainingFatTextView;
  }

  @Override public void onResume() {
    super.onResume();
    pendingNutritionalObservable.subscribe(new Action1<PendingNutritionData>() {
      @Override public void call(PendingNutritionData pendingNutritionData) {
        goalFat = pendingNutritionData.getGoalFat();
        currentFat = pendingNutritionData.getCurrentFat();

        Log.d("Primary Key", "On Subscribe - Date: " + pendingNutritionData.getCurrentDate());
      }
    });

    updateChartView(fatViewHolder, currentFat, goalFat);
  }

  private void updateChartView(MacrosFragment.ViewHolder holder, float current, float goal) {
    currentFatTextView.setText(String.valueOf(current));
    remainingFatTextView.setText(String.valueOf(goal - current));
    ProteinChartUtils.updateChartViews(holder, current, goal);
  }
}
