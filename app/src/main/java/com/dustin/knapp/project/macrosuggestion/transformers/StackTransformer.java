package com.dustin.knapp.project.macrosuggestion.transformers;

/**
 * Created by dknapp on 6/1/17
 */
import android.view.View;

public class StackTransformer extends ABaseTransformer {

  @Override
  protected void onTransform(View view, float position) {
    if (position < 0) {
      view.setTranslationX(0f);
    } else {
      view.setTranslationX(-view.getWidth() * position);
    }
  }

}