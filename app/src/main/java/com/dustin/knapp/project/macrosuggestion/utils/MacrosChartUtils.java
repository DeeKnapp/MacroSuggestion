package com.dustin.knapp.project.macrosuggestion.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import com.dustin.knapp.project.macrosuggestion.Constants;
import com.dustin.knapp.project.macrosuggestion.activities.fragments.MacrosFragment;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;

/**
 * Created by dknapp on 4/30/17
 */
public class MacrosChartUtils {

  public static void updateChartViews(MacrosFragment.ViewHolder holder, int macroChartType,
      float current, float goal) {
    holder.goal.setText(goal + "g");
    holder.current.setText(current + "g");
    holder.remaining.setText((goal - current) + "g");

    // apply styling
    holder.chart.getDescription().setEnabled(false);
    holder.chart.getLegend().setEnabled(false);
    holder.chart.setHoleRadius(45f);
    holder.chart.setTransparentCircleRadius(50f);
    holder.chart.setCenterText(generateCenterText(macroChartType));
    holder.chart.setCenterTextSize(9f);
    holder.chart.setUsePercentValues(false);
    holder.chart.setExtraOffsets(5, 10, 5, 10);
    holder.chart.setTouchEnabled(false);

    float currentPercent = (current / goal) * 100;

    ChartData<?> mChartData;
    if (currentPercent <= 100.0) {
      mChartData = generateDataPie(macroChartType, currentPercent);
    } else {
      mChartData = generateDataPie(macroChartType, 100);
    }

    mChartData.setValueTextSize(0);

    // set data
    holder.chart.setData((PieData) mChartData);

    Legend l = holder.chart.getLegend();
    l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
    l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
    l.setDrawInside(false);
    l.setYEntrySpace(0f);
    l.setYOffset(0f);

    // do not forget to refresh the chart
    // holder.chart.invalidate();
    holder.chart.animateY(900);
  }

  private static SpannableString generateCenterText(int macroGraphType) {
    SpannableString s;
    switch (macroGraphType) {
      case Constants.MACRO_GRAPH_TYPE_PROTEIN: {
        s = new SpannableString("Protein");
        s.setSpan(new RelativeSizeSpan(1.6f), 0, 7, 0);
        s.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 0, 7, 0);
        break;
      }
      case Constants.MACRO_GRAPH_TYPE_CARBS: {
        s = new SpannableString("Carbs");
        s.setSpan(new RelativeSizeSpan(1.6f), 0, 5, 0);
        s.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 0, 5, 0);
        break;
      }
      case Constants.MACRO_GRAPH_TYPE_FATS: {
        s = new SpannableString("Fats");
        s.setSpan(new RelativeSizeSpan(1.6f), 0, 4, 0);
        s.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 0, 4, 0);
        break;
      }
      default: {
        s = new SpannableString("Default");
        s.setSpan(new RelativeSizeSpan(1.6f), 0, 7, 0);
        s.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 0, 7, 0);
        break;
      }
    }
    return s;
  }

  private static PieData generateDataPie(int pieChartType, float percentToGoal) {

    ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
    PieDataSet d;
    switch (pieChartType) {
      case Constants.MACRO_GRAPH_TYPE_PROTEIN: {
        d = new PieDataSet(entries, "");
        break;
      }
      case Constants.MACRO_GRAPH_TYPE_CARBS: {
        d = new PieDataSet(entries, "");
        break;
      }
      case Constants.MACRO_GRAPH_TYPE_FATS: {
        d = new PieDataSet(entries, "");
        break;
      }
      default: {
        d = new PieDataSet(entries, "null");
        break;
      }
    }

    float leftOver = 100 - percentToGoal;

    entries.add(new PieEntry((percentToGoal), ""));
    entries.add(new PieEntry(leftOver, ""));

    // space between slices
    d.setSliceSpace(2f);
    int[] color_Scheme = {
        Color.rgb(56, 142, 60), Color.rgb(211, 47, 47)
    };

    d.setColors(color_Scheme);

    return new PieData(d);
  }
}
