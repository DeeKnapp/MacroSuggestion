package com.dustin.knapp.project.macrosuggestion.models;

/**
 * Created by dknapp on 4/19/17
 */

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.dustin.knapp.project.macrosuggestion.Constants;
import com.dustin.knapp.project.macrosuggestion.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.PercentFormatter;

public class PieChartItem {

  private SpannableString mCenterText;
  private float current, goal;
  protected ChartData<?> mChartData;

  public PieChartItem(ChartData<?> cd, Context c, int macroGraphType, float current, float goal) {
    this.mChartData = cd;
    this.mCenterText = generateCenterText(macroGraphType);
    this.current = current;
    this.goal = goal;
  }

  public int getItemType() {
    return 0;
  }

  public View getView(int position, View convertView, Context c) {

    ViewHolder holder;

    if (convertView == null) {

      holder = new ViewHolder();

      convertView = LayoutInflater.from(c).inflate(R.layout.list_item_piechart, null);
      holder.chart = (PieChart) convertView.findViewById(R.id.chart);
      holder.goal = (TextView) convertView.findViewById(R.id.tvGoal);
      holder.current = (TextView) convertView.findViewById(R.id.tvCurrent);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    holder.goal.setText("Goal: " + goal + "g");
    holder.current.setText("Current: " + current + "g");

    // apply styling
    holder.chart.getDescription().setEnabled(false);
    holder.chart.setHoleRadius(45f);
    holder.chart.setTransparentCircleRadius(50f);
    holder.chart.setCenterText(mCenterText);
    holder.chart.setCenterTextSize(9f);
    holder.chart.setUsePercentValues(true);
    holder.chart.setExtraOffsets(5, 10, 5, 10);

    mChartData.setValueFormatter(new PercentFormatter());
    mChartData.setValueTextSize(11f);
    mChartData.setValueTextColor(Color.WHITE);
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

    return convertView;
  }

  private SpannableString generateCenterText(int macroGraphType) {
    SpannableString s;
    switch (macroGraphType) {
      case Constants.MACRO_GRAPH_TYPE_PROTEIN: {
        s = new SpannableString("Protein");
        s.setSpan(new RelativeSizeSpan(1.6f), 0, 7, 0);
        s.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 0, 7, 0);
        break;
      }
      case Constants.MACRO_GRAPH_TYPE_CARBS: {
        s = new SpannableString("Carbohydrates");
        s.setSpan(new RelativeSizeSpan(1.6f), 0, 13, 0);
        s.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 0, 13, 0);
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

  private static class ViewHolder {
    PieChart chart;
    TextView current, goal;
  }
}
