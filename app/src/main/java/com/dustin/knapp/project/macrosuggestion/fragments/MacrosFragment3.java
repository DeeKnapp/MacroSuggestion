package com.dustin.knapp.project.macrosuggestion.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.adapters.MacrosFragmentPagerAdapter;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.transformers.TabletTransformer;
import javax.inject.Inject;
import rx.Observable;
import rx.Observer;

/**
 * Created by dknapp on 4/25/17
 */
public class MacrosFragment3 extends Fragment {

  ViewPager mViewPager;

  private View view;

  @Inject public Observer<PendingNutritionData> pendingNutritionalObserver;
  @Inject public Observable<PendingNutritionData> pendingNutritionalObservable;
  //todo if nutritional data doesn't exist, add goals/current to default from dietary plan

  MacrosFragmentPagerAdapter macrosFragmentPagerAdapter;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (view == null) {
      view = LayoutInflater.from(getActivity()).inflate(R.layout.macros_layout3, null);

      ((MacroSuggestionApplication) getActivity().getApplication()).getAppComponent().inject(this);

      mViewPager = (ViewPager) view.findViewById(R.id.macros_view_pager);

      setupViewPager();
    }
    return view;
  }

  private void setupViewPager() {
    mViewPager = (ViewPager) view.findViewById(R.id.macros_view_pager);
    macrosFragmentPagerAdapter =
        new MacrosFragmentPagerAdapter(getActivity().getSupportFragmentManager());
    TabLayout tabLayout = (TabLayout) view.findViewById(R.id.macros_sliding_tabs);
    mViewPager.setAdapter(macrosFragmentPagerAdapter);
    mViewPager.setPageTransformer(true, new TabletTransformer());
    tabLayout.setupWithViewPager(mViewPager);
  }

  //public void updateTransformer(String transformation) {
  //  if (transformation.equals("Accordion")) {
  //    mViewPager.setPageTransformer(true, new AccordionTransformer());
  //  } else if (transformation.equals("Cube In")) {
  //    mViewPager.setPageTransformer(true, new CubeInTransformer());
  //  } else if (transformation.equals("Cube Out")) {
  //    mViewPager.setPageTransformer(true, new CubeOutTransformer());
  //  } else if (transformation.equals("Stack")) {
  //    mViewPager.setPageTransformer(true, new StackTransformer());
  //  } else if (transformation.equals("Zoom Out")) {
  //    mViewPager.setPageTransformer(true, new ZoomOutViewPagerTransformer());
  //  }
  //}
}

