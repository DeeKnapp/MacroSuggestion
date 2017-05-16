package com.dustin.knapp.project.macrosuggestion.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.activities.DailyLogActivity;
import com.dustin.knapp.project.macrosuggestion.models.PendingWaterData;
import com.dustin.knapp.project.macrosuggestion.ui.QuickAddWaterDialogFragment;
import com.dustin.knapp.project.macrosuggestion.utils.WaterChartUtils;
import com.github.mikephil.charting.charts.PieChart;
import rx.functions.Action1;

/**
 * Created by dknapp on 4/24/17
 */
public class WaterFragment extends Fragment
    implements QuickAddWaterDialogFragment.QuickAddDialogListener {

  View rootView;

  FloatingActionButton fab;

  TextView currentWaterText, remainingWaterText;

  float goalWater, currentWater;

  DailyLogActivity activity;

  ViewHolder waterViewHolder;

  WaterFragment fragment;

  PendingWaterData currentPendingWaterData;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.water_fragment, container, false);

    currentWaterText = (TextView) rootView.findViewById(R.id.currentWater);
    remainingWaterText = (TextView) rootView.findViewById(R.id.remainingWater);

    activity = (DailyLogActivity) getActivity();

    this.fragment = this;
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    waterViewHolder = new WaterFragment.ViewHolder();
    waterViewHolder.chart = (PieChart) view.findViewById(R.id.water_chart);

    activity.pendingWaterObservable.subscribe(new Action1<PendingWaterData>() {
      @Override public void call(PendingWaterData pendingWaterData) {
        currentPendingWaterData = pendingWaterData;
        currentWater = pendingWaterData.currentWater;
        goalWater = pendingWaterData.goalWater;
      }
    });

    updateChartView(waterViewHolder, currentWater, goalWater, getActivity());

    fab = (FloatingActionButton) view.findViewById(R.id.waterFab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        QuickAddWaterDialogFragment quickAddFoodDialogFragment =
            QuickAddWaterDialogFragment.newInstance();

        quickAddFoodDialogFragment.setListener(fragment);

        FragmentManager fm = getActivity().getSupportFragmentManager();

        quickAddFoodDialogFragment.show(fm, "Alert dialog");
      }
    });
  }

  @Override public void onResume() {
    super.onResume();
  }

  private void updateChartView(WaterFragment.ViewHolder holder, float current, float goal,
      Activity activity) {
    currentWaterText.setText(current + " oz");
    remainingWaterText.setText((goal - current) + " oz");
    WaterChartUtils.updateChartViews(holder, current, goal, activity);
  }

  public void updateView() {
    updateChartView(waterViewHolder, currentPendingWaterData.currentWater, goalWater,
        getActivity());
  }

  @Override public void onQuickAddSubmit(Float water) {
    currentPendingWaterData.currentWater += water;
    activity.pendingWaterObserver.onNext(currentPendingWaterData);
    updateView();
  }

  public class ViewHolder {
    public PieChart chart;
  }
}
