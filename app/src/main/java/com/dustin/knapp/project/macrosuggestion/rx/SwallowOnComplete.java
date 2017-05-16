package com.dustin.knapp.project.macrosuggestion.rx;

import org.slf4j.Logger;
import rx.Observable;
import rx.Subscriber;

import static org.slf4j.LoggerFactory.getLogger;

public class SwallowOnComplete<V> implements Observable.Operator<V, V> {
  private static final Logger LOGGER = getLogger(SwallowOnComplete.class);

  @Override public Subscriber<? super V> call(final Subscriber<? super V> subscriber) {
    return new Subscriber<V>() {
      @Override public void onCompleted() {
        LOGGER.info("{} completed and did nothing", this);
        // do nothing!! aka "swallow" any onCompletes...
      }

      @Override public void onError(final Throwable e) {
        subscriber.onError(e);
      }

      @Override public void onNext(final V t) {
        subscriber.onNext(t);
      }
    };
  }
}
