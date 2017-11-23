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
public class CarbFragment extends Fragment {

  View rootView;

  float goalCarb, currentCarb;

  public TextView currentCarbsTextView, remainingCarbsTextView;

  MacrosFragment.ViewHolder proteinViewHolder;

  @Inject public Observable<PendingNutritionData> pendingNutritionalObservable;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ((MacroSuggestionApplication) getActivity().getApplication()).getAppComponent().inject(this);
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.carb_fragment, container, false);

      currentCarbsTextView = (TextView) rootView.findViewById(R.id.currentCarbs);
      remainingCarbsTextView = (TextView) rootView.findViewById(R.id.remainingCarbs);
    }
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    proteinViewHolder = new MacrosFragment.ViewHolder();
    proteinViewHolder.chart = (PieChart) view.findViewById(R.id.carb_chart);
    proteinViewHolder.current = currentCarbsTextView;
    proteinViewHolder.remaining = remainingCarbsTextView;
  }

  @Override public void onResume() {
    super.onResume();
    pendingNutritionalObservable.subscribe(new Action1<PendingNutritionData>() {
      @Override public void call(PendingNutritionData pendingNutritionData) {
        goalCarb = pendingNutritionData.getGoalCarb();
        currentCarb = pendingNutritionData.getCurrentCarb();

        Log.d("Primary Key", "On Subscribe - Date: " + pendingNutritionData.getCurrentDate());
      }
    });

    updateChartView(proteinViewHolder, currentCarb, goalCarb);
  }

  private void updateChartView(MacrosFragment.ViewHolder holder, float current, float goal) {
    currentCarbsTextView.setText(String.valueOf(current));
    remainingCarbsTextView.setText(String.valueOf(goal - current));
    ProteinChartUtils.updateChartViews(holder, current, goal);
  }
}
