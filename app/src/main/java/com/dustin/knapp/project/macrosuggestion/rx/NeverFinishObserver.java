package com.dustin.knapp.project.macrosuggestion.rx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observer;

public class NeverFinishObserver<T> extends ErrorHandlingObserver<T> {
  private static final Logger LOGGER = LoggerFactory.getLogger(NeverFinishObserver.class);

  private final Observer<T> delegate;

  public NeverFinishObserver(final Observer<T> delegate) {
    this.delegate = delegate;
  }

  @Override protected void next(final T t) {
    delegate.onNext(t);
  }

  @Override protected void error(final Throwable t) {
    LOGGER.error("got onError and swallowed it", t);
  }

  @Override protected void complete() {
    LOGGER.warn("onComplete swallowed.");
  }
}
