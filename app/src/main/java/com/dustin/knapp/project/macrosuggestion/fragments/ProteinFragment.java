package com.dustin.knapp.project.macrosuggestion.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class ProteinFragment extends Fragment {

  View rootView;

  float goalProtein, currentProtein;

  public TextView currentProteinTextView, remainingProteinTextView;

  MacrosFragment.ViewHolder proteinViewHolder;

  @Inject public Observable<PendingNutritionData> pendingNutritionalObservable;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ((MacroSuggestionApplication) getActivity().getApplication()).getAppComponent().inject(this);
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.protein_fragment, container, false);

      currentProteinTextView = (TextView) rootView.findViewById(R.id.currentProtein);
      remainingProteinTextView = (TextView) rootView.findViewById(R.id.remainingProtein);
    }
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    proteinViewHolder = new MacrosFragment.ViewHolder();
    proteinViewHolder.chart = (PieChart) view.findViewById(R.id.protein_chart);
    proteinViewHolder.current = currentProteinTextView;
    proteinViewHolder.remaining = remainingProteinTextView;
  }

  @Override public void onResume() {
    super.onResume();
    pendingNutritionalObservable.subscribe(new Action1<PendingNutritionData>() {
      @Override public void call(PendingNutritionData pendingNutritionData) {
        goalProtein = pendingNutritionData.getGoalProtein();
        currentProtein = pendingNutritionData.getCurrentProtein();

        Log.d("Primary Key", "On Subscribe - Date: " + pendingNutritionData.getCurrentDate());
      }
    });

    updateChartView(proteinViewHolder, currentProtein, goalProtein);
  }

  private void updateChartView(MacrosFragment.ViewHolder holder, float current, float goal) {
    currentProteinTextView.setText(String.valueOf(current));
    remainingProteinTextView.setText(String.valueOf(goal - current));
    ProteinChartUtils.updateChartViews(holder, current, goal);
  }

  //@Override public void onTransformerSelected(String transformer) {
  //  baseFragment = (MacrosFragment3) getParentFragment();
  //
  //  baseFragment.updateTransformer(transformer);
  //}
}
