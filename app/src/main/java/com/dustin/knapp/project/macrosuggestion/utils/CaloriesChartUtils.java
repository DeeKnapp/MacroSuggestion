package com.dustin.knapp.project.macrosuggestion.utils;

import android.app.Activity;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.Toast;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.activities.DailyLogActivity;
import com.dustin.knapp.project.macrosuggestion.activities.LandingPageActivity;
import com.dustin.knapp.project.macrosuggestion.activities.fragments.CaloriesFragment;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.plattysoft.leonids.ParticleSystem;
import java.util.ArrayList;

/**
 * Created by dknapp on 4/26/17
 */
public class CaloriesChartUtils {

  public static void updateChartViews(CaloriesFragment.ViewHolder holder, float current, float goal,
      Activity activity) {

    int goalTextLength = String.valueOf(goal).length();

    holder.chart.getDescription().setEnabled(false);
    holder.chart.getLegend().setEnabled(false);
    holder.chart.setHoleRadius(45f);
    holder.chart.setTransparentCircleRadius(50f);
    holder.chart.setCenterText(generateCenterText(goal, goalTextLength));
    holder.chart.setCenterTextSize(9f);
    holder.chart.setUsePercentValues(false);
    holder.chart.setExtraOffsets(5, 10, 5, 10);
    holder.chart.setTouchEnabled(false);

    float caloriePercent = (current / goal) * 100;

    ChartData<?> mChartData;
    if (caloriePercent <= 100.0) {
      mChartData = generateDataPie(caloriePercent);
    } else {
      mChartData = generateDataPie(100);
    }

    mChartData.setValueFormatter(new PercentFormatter());
    mChartData.setValueTextSize(0);
    mChartData.setValueTextColor(Color.WHITE);
    // set data
    holder.chart.setData((PieData) mChartData);

    // do not forget to refresh the chart
    // holder.chart.invalidate();
    holder.chart.animateY(400);

    if (activity instanceof LandingPageActivity) {
      if (((LandingPageActivity) activity).sharedPreferencesUtil.shouldShouldCalorieAnimation()) {
        if (current >= goal) {
          new ParticleSystem(activity, 1000, R.drawable.extra_small_confetti, 1500,
              R.id.calorie_fragment_wrapper).setSpeedRange(0.1f, 0.5f).oneShot(holder.chart, 1000);

          new ParticleSystem(activity, 1000, R.drawable.small_confetti, 1500,
              R.id.calorie_fragment_wrapper).setSpeedRange(0.1f, 0.5f)
              .oneShot(holder.remaining, 1000);

          new ParticleSystem(activity, 1000, R.drawable.medium_confetti, 1500,
              R.id.calorie_fragment_wrapper).setSpeedRange(0.1f, 0.5f)
              .oneShot(holder.current, 1000);

          Toast.makeText(activity, "Goal Complete!", Toast.LENGTH_SHORT).show();
          ((LandingPageActivity) activity).sharedPreferencesUtil.storeShouldShowCalorieAnimation(
              false);
        }
      }
    }
  }

  private static PieData generateDataPie(float percentToGoal) {

    ArrayList<PieEntry> entries = new ArrayList<>();
    PieDataSet d;

    d = new PieDataSet(entries, "");

    float leftOver = 100 - percentToGoal;
    entries.add(new PieEntry((percentToGoal), ""));
    entries.add(new PieEntry((leftOver), ""));

    d.setSliceSpace(1f);
    int[] color_Scheme = {
        Color.rgb(56, 142, 60), Color.rgb(211, 47, 47)
    };

    d.setColors(color_Scheme);

    return new PieData(d);
  }

  private static SpannableString generateCenterText(float goalCalorie, int goalTextLength) {
    SpannableString s;
    s = new SpannableString("Goal\n" + goalCalorie);
    s.setSpan(new RelativeSizeSpan(1.6f), 0, 4 + goalTextLength + 1, 0);
    s.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 0, 1, 0);
    return s;
  }
}
