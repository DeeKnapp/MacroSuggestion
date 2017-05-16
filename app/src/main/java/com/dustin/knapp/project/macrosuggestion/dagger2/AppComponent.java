package com.dustin.knapp.project.macrosuggestion.dagger2;

import com.dustin.knapp.project.macrosuggestion.activities.BarcodeScanner;
import com.dustin.knapp.project.macrosuggestion.activities.DailyLogActivity;
import com.dustin.knapp.project.macrosuggestion.activities.LandingPageActivity;
import com.dustin.knapp.project.macrosuggestion.activities.LoginActivity;
import com.dustin.knapp.project.macrosuggestion.activities.OnBoardingActivityStep2;
import com.dustin.knapp.project.macrosuggestion.activities.OnBoardingActivityStep1;
import com.dustin.knapp.project.macrosuggestion.activities.OnBoardingActivityStep3;
import com.dustin.knapp.project.macrosuggestion.activities.OnBoardingProcess2Step1;
import com.dustin.knapp.project.macrosuggestion.activities.OnBoardingProcess2Step2;
import com.dustin.knapp.project.macrosuggestion.activities.OnBoardingProcess2Step3;
import com.dustin.knapp.project.macrosuggestion.activities.SignoutActivity;
import com.dustin.knapp.project.macrosuggestion.activities.fragments.CaloriesFragment;
import com.dustin.knapp.project.macrosuggestion.activities.fragments.FoodFragment;
import com.dustin.knapp.project.macrosuggestion.activities.fragments.MacrosFragment;
import dagger.Component;
import javax.inject.Singleton;

@Component(modules = AppModule.class) @Singleton public interface AppComponent {

  void inject(LoginActivity activity);

  void inject(SignoutActivity activity);

  void inject(OnBoardingProcess2Step1 activity);

  void inject(OnBoardingProcess2Step2 activity);

  void inject(OnBoardingProcess2Step3 activity);

  void inject(OnBoardingActivityStep1 activity);

  void inject(OnBoardingActivityStep2 activity);

  void inject(OnBoardingActivityStep3 activity);

  void inject(LandingPageActivity activity);

  void inject(DailyLogActivity activity);

  void inject(BarcodeScanner activity);

  void inject(CaloriesFragment caloriesFragment);

  void inject(MacrosFragment macrosFragment);

  void inject(FoodFragment foodFragment);
}
