package com.dustin.knapp.project.macrosuggestion;

import android.app.Application;
import com.dustin.knapp.project.macrosuggestion.dagger2.AppComponent;
import com.dustin.knapp.project.macrosuggestion.dagger2.AppModule;
import com.dustin.knapp.project.macrosuggestion.dagger2.DaggerAppComponent;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by jmai on 4/13/16.
 */
public class MacroSuggestionApplication extends Application {
  private AppComponent component;

  @Override public void onCreate() {
    super.onCreate();

    FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    component = DaggerAppComponent.builder().appModule(getAppModule()).build();

    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return;
    }
    LeakCanary.install(this);
  }

  private AppModule getAppModule() {
    return new AppModule(this);
  }

  public AppComponent getAppComponent() {
    return component;
  }
}
