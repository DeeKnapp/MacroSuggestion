package com.dustin.knapp.project.macrosuggestion.utils;

import android.app.Activity;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.widget.Toast;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.activities.fragments.WaterFragment;
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
public class WaterChartUtils {

  public static void updateChartViews(WaterFragment.ViewHolder holder, float current, float goal,
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

    //todo only display once goal is complete, not every additional add
    if (current >= goal) {

      new ParticleSystem(activity, 800, R.mipmap.rain_drop, 10000).setSpeedByComponentsRange(0f, 0f,
          0.05f, 0.1f)
          .setAcceleration(0.00005f, 90)
          .emitWithGravity(activity.findViewById(R.id.emiter), Gravity.BOTTOM, 8);

      Toast.makeText(activity, "Goal Complete!", Toast.LENGTH_SHORT).show();
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
        Color.rgb(25, 118, 210), Color.rgb(224, 224, 224)
    };

    d.setColors(color_Scheme);

    return new PieData(d);
  }

  private static SpannableString generateCenterText(float goalFluidOzWater, int goalTextLength) {
    SpannableString s;
    s = new SpannableString("Goal\n" + goalFluidOzWater + " oz");
    s.setSpan(new RelativeSizeSpan(1.6f), 0, 4 + goalTextLength + 1, 0);
    s.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 0, 1, 0);
    return s;
  }
}
