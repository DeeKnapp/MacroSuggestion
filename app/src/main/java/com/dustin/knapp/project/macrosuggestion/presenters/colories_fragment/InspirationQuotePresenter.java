package com.dustin.knapp.project.macrosuggestion.presenters.colories_fragment;

import android.util.Log;
import com.dustin.knapp.project.macrosuggestion.networking.InspirationalCall;
import com.dustin.knapp.project.macrosuggestion.presenters.Presenter;
import java.lang.ref.WeakReference;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by dknapp on 4/26/17
 */
public class InspirationQuotePresenter extends Presenter<InspirtationQuoteReactiveView> {

  private InspirationalCall inspirationalCall;

  @Inject @Named("hasInternetService") Provider<Boolean> internetServiceProvider;

  @Inject InspirationQuotePresenter(InspirationalCall inspirationalCall) {
    this.inspirationalCall = inspirationalCall;
  }

  @Override public void setView(InspirtationQuoteReactiveView inspirtationQuoteReactiveView) {
    this.reactiveView = new WeakReference<>(inspirtationQuoteReactiveView);
    this.hasInternetServiceProvider = internetServiceProvider;
  }

  public void getInspirationQuoteForSnackbar() {
    final InspirtationQuoteReactiveView inspirtationQuoteReactiveView = reactiveView.get();
    subscription = Observable.defer(new Func0<Observable<InspirationalCall.Quote>>() {
      @Override public Observable<InspirationalCall.Quote> call() {
        return Observable.just(inspirationalCall.getQuote());
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<InspirationalCall.Quote>() {
          @Override public void call(InspirationalCall.Quote quote) {
            String quoteText;
            if (quote.getQuoteText() != null) {
              if (quote.getQuoteAuthor().equals("")) {
                quoteText = "\"" + quote.getQuoteText() + "\" - Unknown";
              } else {
                quoteText = "\"" + quote.getQuoteText() + "\" - " + quote.getQuoteAuthor();
              }
              Log.d("QuoteText: ", quoteText);
              if (!quoteText.equals("")) {
                inspirtationQuoteReactiveView.onServerSuccess(quoteText);
              } else {
                getInspirationQuoteForSnackbar();
              }
            }
          }
        });
  }
}
