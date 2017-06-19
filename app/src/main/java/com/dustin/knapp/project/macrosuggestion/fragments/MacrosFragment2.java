package com.dustin.knapp.project.macrosuggestion.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.activities.LandingPageActivity;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by dknapp on 4/25/17
 */
public class MacrosFragment2 extends Fragment {

  View rootView;

  @Inject public Observable<PendingNutritionData> pendingNutritionalObservable;

  LandingPageActivity activity;

  float goalCarb;
  float goalProtein;
  float goalFat;

  float currentCarb;
  float currentProtein;
  float currentFat;

  BarChart mChart;

  //todo how to illustrate the overages??
  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ((MacroSuggestionApplication) getActivity().getApplication()).getAppComponent().inject(this);
    rootView = inflater.inflate(R.layout.macros_layout2, container, false);

    activity = (LandingPageActivity) getActivity();

    mChart = (BarChart) rootView.findViewById(R.id.macrosBarChart);
    //mChart.setOnChartValueSelectedListener(this);

    mChart.getDescription().setEnabled(false);

    // if more than 60 entries are displayed in the chart, no values will be
    // drawn
    mChart.setMaxVisibleValueCount(40);

    // scaling can now only be done on x- and y-axis separately
    mChart.setPinchZoom(false);

    mChart.setDrawGridBackground(false);
    mChart.setDrawBarShadow(false);

    mChart.setDrawValueAboveBar(false);
    mChart.setHighlightFullBarEnabled(false);

    // change the position of the y-labels
    YAxis leftAxis = mChart.getAxisLeft();
    leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
    mChart.getAxisRight().setEnabled(false);
    mChart.setTouchEnabled(false);

    XAxis xLabels = mChart.getXAxis();
    xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
    xLabels.setCenterAxisLabels(false);
    xLabels.setLabelCount(3);
    String[] labels = {"Protein", "Carbs", "Fats"};

    xLabels.setValueFormatter(new LabelFormatter(labels));

    // mChart.setDrawXLabels(false);
    // mChart.setDrawYLabels(false);

    Legend l = mChart.getLegend();
    l.setEnabled(false);
    return rootView;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
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

    List<BarEntry> entries = new ArrayList<>();
    entries.add(new BarEntry(0f, new float[] {currentProtein, goalProtein - currentProtein}));
    entries.add(new BarEntry(1f, new float[] {currentCarb, goalCarb - currentCarb}));
    entries.add(new BarEntry(2f, new float[] {currentFat, goalProtein - currentFat}));

    List<Integer> colors = new ArrayList<>();
    colors.add(Color.rgb(56, 142, 60));
    colors.add(Color.rgb(224, 224, 224));

    BarDataSet set = new BarDataSet(entries, "BarDataSet");
    set.setColors(colors);

    ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
    dataSets.add(set);

    BarData data = new BarData(dataSets);
    data.setValueTextColor(Color.WHITE);

    data.setBarWidth(0.5f); // set custom bar width
    mChart.setData(data);
    mChart.setFitBars(true); // make the x-axis fit exactly all bars
    mChart.invalidate();

    mChart.getData().notifyDataChanged();
    mChart.notifyDataSetChanged();
  }

  public class LabelFormatter implements IAxisValueFormatter {
    private final String[] mLabels;

    public LabelFormatter(String[] labels) {
      mLabels = labels;
    }

    @Override public String getFormattedValue(float value, AxisBase axis) {
      Log.d("Testing", "Value: " + value);
      if (value == 0 || value == 1 || value == 2) {
        return mLabels[(int) value];
      }
      return "";
    }
  }
}

