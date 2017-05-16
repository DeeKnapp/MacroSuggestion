package com.dustin.knapp.project.macrosuggestion.presenters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import java.lang.ref.WeakReference;
import javax.inject.Provider;
import rx.Subscription;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by dknapp on 4/26/17
 */
public abstract class Presenter<V extends BaseReactiveView> {
  public Subscription subscription;

  protected Provider<Boolean> hasInternetServiceProvider;

  public WeakReference<V> reactiveView;

  public abstract void setView(V v);

  public void unSubscribe() {
    if (subscription != null) {
      subscription.unsubscribe();
    }
  }

  protected boolean hasInternetConnection() {
    if (hasInternetServiceProvider.get()) {
      return true;
    } else {
      handleNoInternetConnection();
      return false;
    }
  }

  private void handleNoInternetConnection() {
    BaseReactiveView view = reactiveView.get();
    if (view != null) {
      //if (view instanceof BaseActivity) {
      //  new LaunchpadDialogBuilder().title(NO_INTERNET_CONNECTION)
      //      .positiveButtonImConnected()
      //      .negativeButtonCancel()
      //      .build()
      //      .show((BaseActivity) view);
      //} else if (view instanceof Fragment) {
      //  Activity activity = ((Fragment) view).getActivity();
      //  if (activity != null) {
      //    new LaunchpadDialogBuilder().title(NO_INTERNET_CONNECTION)
      //        .positiveButtonImConnected()
      //        .negativeButtonCancel()
      //        .build()
      //        .show(activity);
      //  }
      //}
      view.dismissProgressBar();
    }
  }
}


