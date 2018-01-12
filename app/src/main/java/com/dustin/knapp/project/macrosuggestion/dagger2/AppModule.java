package com.dustin.knapp.project.macrosuggestion.dagger2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.models.PendingWaterData;
import com.dustin.knapp.project.macrosuggestion.models.UserObject;
import com.dustin.knapp.project.macrosuggestion.rx.NeverFinishObserver;
import com.dustin.knapp.project.macrosuggestion.utils.storage.SharedPreferencesUtil;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import rx.Observable;
import rx.Observer;
import rx.subjects.BehaviorSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by jmai on 5/27/16.
 */

@SuppressWarnings("PMD") @Module public class AppModule {
  private final MacroSuggestionApplication app;

  public AppModule(MacroSuggestionApplication app) {
    this.app = app;
  }

  @Provides @Singleton @Named("application") Context context() {
    return app.getApplicationContext();
  }

  @Provides @Singleton MacroSuggestionApplication application() {
    return app;
  }

  @Provides @Singleton @Named("auth_token") Subject<String, String> authTokenSubject() {
    return new SerializedSubject<>(BehaviorSubject.create("empty"));
  }

  @Provides @Singleton Subject<PendingNutritionData, PendingNutritionData> pendingUserNutrition() {
    return new SerializedSubject<>(BehaviorSubject.create(new PendingNutritionData()));
  }

  @Provides Observable<PendingNutritionData> pendingNutritionObservable(
      Subject<PendingNutritionData, PendingNutritionData> pendingItemSubject) {
    return pendingItemSubject.asObservable();
  }

  @Provides Observer<PendingNutritionData> pendingNutritionObserver(
      Subject<PendingNutritionData, PendingNutritionData> pendingItemSubject) {
    return new NeverFinishObserver<>(pendingItemSubject);
  }

  @Provides @Singleton Subject<UserObject, UserObject> pendingUserObjectSubject() {
    return new SerializedSubject<>(BehaviorSubject.create(new UserObject()));
  }

  @Provides Observable<UserObject> pendingUserObjectObservable(
      Subject<UserObject, UserObject> pendingUserObjectSubject) {
    return pendingUserObjectSubject.asObservable();
  }

  @Provides Observer<UserObject> pendingUserObjectObserver(
      Subject<UserObject, UserObject> pendingUserObjectSubject) {
    return new NeverFinishObserver<>(pendingUserObjectSubject);
  }

  @Provides @Singleton Subject<PendingWaterData, PendingWaterData> pendingWaterSubject() {
    return new SerializedSubject<>(BehaviorSubject.create(new PendingWaterData()));
  }

  @Provides Observable<PendingWaterData> pendingWaterObservable(
      Subject<PendingWaterData, PendingWaterData> pendingWaterSubject) {
    return pendingWaterSubject.asObservable();
  }

  @Provides Observer<PendingWaterData> pendingWaterObserver(
      Subject<PendingWaterData, PendingWaterData> pendingWaterSubject) {
    return new NeverFinishObserver<>(pendingWaterSubject);
  }

  @Provides @Singleton Subject<Integer, Integer> pendingMealTypeSubject() {
    return new SerializedSubject<>(BehaviorSubject.create(0));
  }

  @Provides Observable<Integer> pendingMealTypeObservable(
      Subject<Integer, Integer> pendingMealTypeSubject) {
    return pendingMealTypeSubject.asObservable();
  }

  @Provides Observer<Integer> pendingMealTypeObserver(
      Subject<Integer, Integer> pendingMealTypeSubject) {
    return new NeverFinishObserver<>(pendingMealTypeSubject);
  }

  @Provides @Singleton SharedPreferencesUtil getSharedPreferencesUtil(
      @Named("application") Context context) {
    return new SharedPreferencesUtil(context);
  }

  @Provides @Named("hasInternetService") Boolean checkInternetService() {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    return activeNetwork != null && activeNetwork.isConnected();
  }
}
