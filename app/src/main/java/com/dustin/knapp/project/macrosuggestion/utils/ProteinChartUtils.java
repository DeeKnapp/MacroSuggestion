package com.dustin.knapp.project.macrosuggestion.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import com.dustin.knapp.project.macrosuggestion.fragments.MacrosFragment;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import java.util.ArrayList;

/**
 * Created by dknapp on 4/26/17
 */
public class ProteinChartUtils {

  public static void updateChartViews(MacrosFragment.ViewHolder holder, float current, float goal) {

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
        Color.rgb(56, 142, 60), Color.rgb(224, 224, 224)
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
