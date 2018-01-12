package com.dustin.knapp.project.macrosuggestion.fragments;

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
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.PendingWaterData;
import com.dustin.knapp.project.macrosuggestion.ui.QuickAddWaterDialogFragment;
import com.dustin.knapp.project.macrosuggestion.utils.storage.RealmUtils;
import com.dustin.knapp.project.macrosuggestion.utils.charts.WaterChartUtils;
import com.github.mikephil.charting.charts.PieChart;
import javax.inject.Inject;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by dknapp on 4/24/17
 */
public class WaterFragment extends Fragment implements QuickAddWaterDialogFragment.QuickAddDialogListener {

  View rootView;

  FloatingActionButton fab;

  TextView currentWaterText, remainingWaterText;

  float goalWater, currentWater;

  ViewHolder waterViewHolder;

  WaterFragment fragment;

  PendingWaterData currentPendingWaterData;

  @Inject public Observable<PendingWaterData> pendingWaterObservable;
  @Inject public Observer<PendingWaterData> pendingWaterObserver;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.water_fragment, container, false);

    ((MacroSuggestionApplication) getActivity().getApplication()).getAppComponent().inject(this);

    currentWaterText = (TextView) rootView.findViewById(R.id.currentWater);
    remainingWaterText = (TextView) rootView.findViewById(R.id.remainingWater);

    this.fragment = this;
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    waterViewHolder = new WaterFragment.ViewHolder();
    waterViewHolder.chart = (PieChart) view.findViewById(R.id.water_chart);

    pendingWaterObservable.subscribe(new Action1<PendingWaterData>() {
      @Override public void call(PendingWaterData pendingWaterData) {
        currentPendingWaterData = new PendingWaterData();
        currentPendingWaterData.setCurrentDate(pendingWaterData.getCurrentDate());
        currentPendingWaterData.setGoalWater(pendingWaterData.getGoalWater());
        currentPendingWaterData.setCurrentWater(pendingWaterData.getCurrentWater());
        currentWater = pendingWaterData.getCurrentWater();
        goalWater = pendingWaterData.getGoalWater();
      }
    });

    updateChartView(waterViewHolder, currentWater, goalWater, getActivity());

    fab = (FloatingActionButton) view.findViewById(R.id.waterFab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        QuickAddWaterDialogFragment quickAddWaterDialogFragment = QuickAddWaterDialogFragment.newInstance();

        quickAddWaterDialogFragment.setListener(fragment);

        FragmentManager fm = getActivity().getSupportFragmentManager();

        quickAddWaterDialogFragment.show(fm, "Alert dialog");
      }
    });
  }

  @Override public void onResume() {
    super.onResume();
  }

  private void updateChartView(WaterFragment.ViewHolder holder, float current, float goal, Activity activity) {
    currentWaterText.setText(current + " oz");
    remainingWaterText.setText((goal - current) + " oz");
    WaterChartUtils.updateChartViews(holder, current, goal, activity);
  }

  public void updateView() {
    updateChartView(waterViewHolder, currentPendingWaterData.getCurrentWater(), goalWater, getActivity());
  }

  @Override public void onQuickAddSubmit(Float water) {
    currentPendingWaterData.setCurrentWater(currentPendingWaterData.getCurrentWater() + water);
    pendingWaterObserver.onNext(currentPendingWaterData);
    RealmUtils.updateCurrentDayPendingWaterData(currentPendingWaterData);
    updateView();
  }

  public class ViewHolder {
    public PieChart chart;
  }
}
