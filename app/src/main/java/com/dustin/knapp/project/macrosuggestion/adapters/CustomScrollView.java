package com.dustin.knapp.project.macrosuggestion.adapters;

import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import android.content.Context;

/**
 * Created by dknapp on 6/14/17
 */
public class CustomScrollView extends ScrollView {
  OnBottomReachedListener mListener;

  public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public CustomScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomScrollView(Context context) {
    super(context);
  }

  @Override protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    View view = (View) getChildAt(getChildCount() - 1);
    int diff = (view.getBottom() - (getHeight() + getScrollY()));

    if (diff == 0 && mListener != null) {
      mListener.onBottomReached();
    }

    super.onScrollChanged(l, t, oldl, oldt);
  }

  // Getters & Setters

  public OnBottomReachedListener getOnBottomReachedListener() {
    return mListener;
  }

  public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
    mListener = onBottomReachedListener;
  }

  /**
   * Event listener.
   */
  public interface OnBottomReachedListener {
    void onBottomReached();
  }
}
