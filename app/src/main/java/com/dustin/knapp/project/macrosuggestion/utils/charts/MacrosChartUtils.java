package com.dustin.knapp.project.macrosuggestion.utils.charts;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import com.dustin.knapp.project.macrosuggestion.fragments.MacrosFragment;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by dknapp on 4/30/17
 */
public class MacrosChartUtils {

  public static void updateChartViews(MacrosFragment.ViewHolder holder, int macroChartType,
      float current, float goal) {
    holder.current.setText(current + "g");
    holder.remaining.setText((goal - current) + "g");

    float currentPercent = (current / goal) * 100;

    NumberFormat formatter = NumberFormat.getNumberInstance();
    formatter.setMinimumFractionDigits(2);
    formatter.setMaximumFractionDigits(2);
    String output = formatter.format(currentPercent);

    int goalTextLength = output.length();

    // apply styling
    holder.chart.getDescription().setEnabled(false);
    holder.chart.getLegend().setEnabled(false);
    holder.chart.setHoleRadius(90f);
    holder.chart.setHoleColor(Color.TRANSPARENT);
    holder.chart.setTransparentCircleRadius(55f);
    holder.chart.setCenterText(generateCenterText(output, goalTextLength));
    holder.chart.setCenterTextSize(18f);
    holder.chart.setCenterTextColor(Color.WHITE);
    holder.chart.setUsePercentValues(false);
    holder.chart.setExtraOffsets(5, 10, 5, 10);
    holder.chart.setTouchEnabled(false);

    ChartData<?> mChartData;
    if (currentPercent <= 100.0) {
      mChartData = generateDataPie(currentPercent);
    } else {
      mChartData = generateDataPieOverage(currentPercent);
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

  private static SpannableString generateCenterText(String goalPercent, int goalTextLength) {
    SpannableString s;
    s = new SpannableString(goalPercent + "%");
    s.setSpan(new RelativeSizeSpan(1.6f), 0, goalTextLength + 1, 0);
    return s;
  }

  private static PieData generateDataPie(float percentToGoal) {

    ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
    PieDataSet d;
    d = new PieDataSet(entries, "");

    float leftOver = 100 - percentToGoal;

    entries.add(new PieEntry((percentToGoal), ""));
    entries.add(new PieEntry(leftOver, ""));

    // space between slices
    d.setSliceSpace(2f);
    int[] color_Scheme = {
        Color.rgb(255, 255, 255), Color.argb(50, 0, 0, 0)
    };

    d.setColors(color_Scheme);

    return new PieData(d);
  }

  private static PieData generateDataPieOverage(float percentToGoal) {

    ArrayList<PieEntry> entries = new ArrayList<>();
    PieDataSet d;

    d = new PieDataSet(entries, "");
    float leftOver;
    if (percentToGoal >= 200) {
      leftOver = 0;
      percentToGoal = 100;
    } else {
      leftOver = 100 - percentToGoal;
    }
    entries.add(new PieEntry((percentToGoal), ""));
    entries.add(new PieEntry((leftOver), ""));

    d.setSliceSpace(1f);
    int[] color_Scheme = {
        Color.rgb(211, 47, 47), Color.rgb(255, 255, 255)
    };

    d.setColors(color_Scheme);

    return new PieData(d);
  }
}
