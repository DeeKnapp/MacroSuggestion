package com.dustin.knapp.project.macrosuggestion.rx;

import rx.Observable;
import rx.Subscription;

public final class RxUtils {

  private RxUtils() {

  }

  public static void unsubscribeIfNotNull(Subscription subscription) {
    if (subscription != null) {
      subscription.unsubscribe();
    }
  }

  public static <T> T blockForLatest(final Observable<T> observable) {
    return observable.take(1)
        .lift(new SwallowOnComplete<T>())
        .toBlocking()
        .latest()
        .iterator()
        .next();
  }
}
