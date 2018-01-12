package com.dustin.knapp.project.macrosuggestion.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import butterknife.Unbinder;
import com.dustin.knapp.project.macrosuggestion.utils.storage.SharedPreferencesUtil;
import javax.inject.Inject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dknapp on 4/26/17
 */
public abstract class BaseActivity extends AppCompatActivity {

  protected Unbinder unbinder;
  protected CompositeSubscription subscriptions;
  @Inject public SharedPreferencesUtil sharedPreferencesUtil;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected void onStart() {
    super.onStart();
    subscriptions = new CompositeSubscription();
  }

  @Override protected void onDestroy() {
    if (unbinder != null) {
      unbinder.unbind();
    }
    if (subscriptions != null) {
      subscriptions.unsubscribe();
    }
    super.onDestroy();
  }

  protected void hideKeyboard(final View topView) {
    if (topView != null) {
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      if (imm != null) {
        imm.hideSoftInputFromWindow(topView.getWindowToken(), 0);
      }
    }
  }

  protected void showKeyboard(final View topView) {
    if (topView != null) {
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      if (imm != null) {
        imm.showSoftInput(topView, 0);
      }
    }
  }
}
