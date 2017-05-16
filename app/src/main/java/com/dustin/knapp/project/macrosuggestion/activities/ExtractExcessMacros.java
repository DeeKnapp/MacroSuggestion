package com.dustin.knapp.project.macrosuggestion.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.dustin.knapp.project.macrosuggestion.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

/**
 * Created by dknapp on 4/12/17
 */
public class ExtractExcessMacros extends AppCompatActivity {

  private PieChart mChart;

  protected String[] mParties = new String[] {
      "Fats", "Carbohydrates", "Protein", "Party D", "Party E", "Party F", "Party G", "Party H",
      "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
      "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
      "Party Y", "Party Z"
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.extract_excess_macros);

    mChart = (PieChart) findViewById(R.id.chart1);
    mChart.setBackgroundColor(Color.WHITE);

  //  moveOffScreen();

    mChart.setUsePercentValues(true);
    mChart.getDescription().setEnabled(false);

    mChart.setCenterText(generateCenterSpannableText());

    mChart.setDrawHoleEnabled(true);
    mChart.setHoleColor(Color.WHITE);

    mChart.setTransparentCircleColor(Color.WHITE);
    mChart.setTransparentCircleAlpha(110);

    mChart.setHoleRadius(58f);
    mChart.setTransparentCircleRadius(61f);

    mChart.setDrawCenterText(true);

    mChart.setRotationEnabled(false);
    mChart.setHighlightPerTapEnabled(true);

    mChart.setMaxAngle(180f); // HALF CHART
    mChart.setRotationAngle(180f);
    mChart.setCenterTextOffset(0, -20);

    setData(3, 100);

    mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

    Legend l = mChart.getLegend();
    l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
    l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
    l.setDrawInside(false);
    l.setXEntrySpace(7f);
    l.setYEntrySpace(0f);
    l.setYOffset(0f);

    // entry label styling
    mChart.setEntryLabelColor(Color.WHITE);
    mChart.setEntryLabelTextSize(12f);
  }

  private void setData(int count, float range) {

    ArrayList<PieEntry> values = new ArrayList<PieEntry>();

    for (int i = 0; i < count; i++) {
      values.add(new PieEntry((float) ((Math.random() * range) + range / 3),
          mParties[i % mParties.length]));
    }

    PieDataSet dataSet = new PieDataSet(values, "Daily Macros");
    dataSet.setSliceSpace(3f);
    dataSet.setSelectionShift(5f);

    dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
    //dataSet.setSelectionShift(0f);

    PieData data = new PieData(dataSet);
    data.setValueFormatter(new PercentFormatter());
    data.setValueTextSize(11f);
    data.setValueTextColor(Color.WHITE);
    mChart.setData(data);

    mChart.invalidate();
  }

  private SpannableString generateCenterSpannableText() {

    SpannableString s = new SpannableString("Macros");
    s.setSpan(new RelativeSizeSpan(1.7f), 0, 6, 0);
    s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length() - 6, 0);
    s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length() - 6, 0);
    s.setSpan(new RelativeSizeSpan(.8f), 0, s.length() - 6, 0);
    s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 6, s.length(), 0);
    s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 6, s.length(), 0);
    return s;
  }

  private void moveOffScreen() {

    Display display = getWindowManager().getDefaultDisplay();
    int height = display.getHeight();  // deprecated

    int offset = (int) (height * 0.65); /* percent to move */

    RelativeLayout.LayoutParams rlParams = (RelativeLayout.LayoutParams) mChart.getLayoutParams();
    rlParams.setMargins(0, 0, 0, -offset);
    mChart.setLayoutParams(rlParams);
  }
}
