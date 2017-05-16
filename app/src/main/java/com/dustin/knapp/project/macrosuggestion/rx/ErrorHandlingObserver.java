package com.dustin.knapp.project.macrosuggestion.rx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observer;

public abstract class ErrorHandlingObserver<T> implements Observer<T> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandlingObserver.class);

  @Override public final void onCompleted() {
    try {
      complete();
    } catch (Throwable e2) {
      LOGGER.error(String.format("ErrorHandlingObserver.onComplete"), e2);
    }
  }

  @Override public final void onError(final Throwable e) {
    try {
      error(e);
    } catch (Throwable e2) {
      LOGGER.error(String.format("ErrorHandlingObserver.onError(%s)", e), e2);
    }
  }

  @Override public final void onNext(final T t) {
    try {
      next(t);
    } catch (Throwable e) {
      LOGGER.error(String.format("ErrorHandlingObserver.onNext(%s)", t), e);
    }
  }

  protected abstract void next(final T t);

  protected abstract void error(final Throwable t);

  protected abstract void complete();
}
