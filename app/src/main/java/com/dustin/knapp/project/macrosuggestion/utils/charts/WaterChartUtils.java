package com.dustin.knapp.project.macrosuggestion.utils.charts;

import android.app.Activity;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.widget.Toast;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.activities.DailyLogActivity;
import com.dustin.knapp.project.macrosuggestion.fragments.WaterFragment;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by dknapp on 4/26/17
 */
public class WaterChartUtils {

  public static void updateChartViews(WaterFragment.ViewHolder holder, float current, float goal,
      Activity activity) {

    float waterPercent = (current / goal) * 100;

    NumberFormat formatter = NumberFormat.getNumberInstance();
    formatter.setMinimumFractionDigits(2);
    formatter.setMaximumFractionDigits(2);
    String output = formatter.format(waterPercent);

    int goalTextLength = output.length();

    holder.chart.getDescription().setEnabled(false);
    holder.chart.getLegend().setEnabled(false);
    holder.chart.setHoleRadius(90f);
    holder.chart.setHoleColor(Color.TRANSPARENT);
    holder.chart.setTransparentCircleRadius(55f);
    holder.chart.setCenterText(generateCenterText(output, goalTextLength));
    holder.chart.setCenterTextSize(18f);
    holder.chart.setCenterTextColor(Color.WHITE);
    holder.chart.setExtraOffsets(5, 10, 5, 10);
    holder.chart.setTouchEnabled(false);

    ChartData<?> mChartData;
    if (waterPercent <= 100.0) {
      mChartData = generateDataPie(waterPercent);
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

    if (activity instanceof DailyLogActivity) {
      if (((DailyLogActivity) activity).sharedPreferencesUtil.shouldShowWaterAnimation()) {
        if (current >= goal) {
          Toast.makeText(activity, "Goal Complete!", Toast.LENGTH_SHORT).show();

          ((DailyLogActivity) activity).sharedPreferencesUtil.storeShouldShowWaterAnimation(false);
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

    d.setSliceSpace(2f);
    int[] color_Scheme = {
        Color.rgb(255, 255, 255), Color.argb(40, 0, 0, 0)
    };

    d.setColors(color_Scheme);

    return new PieData(d);
  }

  private static SpannableString generateCenterText(String goalWater, int goalTextLength) {
    SpannableString s;
    s = new SpannableString(goalWater + "%");
    s.setSpan(new RelativeSizeSpan(1.6f), 0, goalTextLength + 1, 0);
    return s;
  }
}
