package com.dustin.knapp.project.macrosuggestion.presenters.colories_fragment;

import com.dustin.knapp.project.macrosuggestion.presenters.BaseReactiveView;

/**
 * Created by dknapp on 4/26/17
 */
public interface CaloriesReactiveView extends BaseReactiveView {
  void onServerSuccess(String quote);
  void onServerError();
}
