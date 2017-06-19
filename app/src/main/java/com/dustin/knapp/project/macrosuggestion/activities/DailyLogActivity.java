package com.dustin.knapp.project.macrosuggestion.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dustin.knapp.project.macrosuggestion.Constants;
import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.BaseNutrition;
import com.dustin.knapp.project.macrosuggestion.models.FoodEntry;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuHelper;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuItem;
import com.dustin.knapp.project.macrosuggestion.presenters.colories_fragment.CaloriesPresenter;
import com.dustin.knapp.project.macrosuggestion.ui.AddFoodDialogFragment;
import com.dustin.knapp.project.macrosuggestion.ui.QuickAddFoodDialogFragment;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.dustin.knapp.project.macrosuggestion.utils.RealmUtils;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import android.content.Intent;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class DailyLogActivity extends BaseNavDrawerActivity
    implements AddFoodDialogFragment.AddFoodDialogListener,
    QuickAddFoodDialogFragment.QuickAddDialogListener {

  PendingNutritionData currentPendingNutritionalData;

  LinearLayout breakfastItemsContainer, lunchItemsContainer, dinnerItemsContainer,
      quickAddItemsContainer;

  ImageButton addBreakfastButton, addLunchButton, addDinnerButton, addSwiftAddButton;

  ImageButton addBreakfastScanButton, addLunchScanButton, addDinnerScanButton;

  private int currentClickedViewGroup;

  @Inject public Observer<Integer> pendingMealTypeObserver;
  @Inject public Observer<PendingNutritionData> pendingNutritionalObserver;
  @Inject public Observable<PendingNutritionData> pendingNutritionDataObservable;
  @Inject public CaloriesPresenter caloriesPresenter;

  View view;

  DailyLogActivity activity;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    view = LayoutInflater.from(this).inflate(R.layout.food_fragment, null);

    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);
    this.content.removeAllViews();
    this.content.addView(view);

    activity = this;

    toolbarTitle.setText(DrawerMenuItem.getTitle(DailyLogActivity.class));

    breakfastItemsContainer = (LinearLayout) view.findViewById(R.id.breakItemsContainer);
    addBreakfastButton = (ImageButton) view.findViewById(R.id.addBreakfastButton);
    addBreakfastScanButton = (ImageButton) view.findViewById(R.id.addBreakfastScanButton);

    lunchItemsContainer = (LinearLayout) view.findViewById(R.id.lunchItemsContainer);
    addLunchButton = (ImageButton) view.findViewById(R.id.addLunchButton);
    addLunchScanButton = (ImageButton) view.findViewById(R.id.addLunchScanButton);

    dinnerItemsContainer = (LinearLayout) view.findViewById(R.id.dinnerItemsContainer);
    addDinnerButton = (ImageButton) view.findViewById(R.id.addDinnerButton);
    addDinnerScanButton = (ImageButton) view.findViewById(R.id.addDinnerScanButton);

    quickAddItemsContainer = (LinearLayout) view.findViewById(R.id.quickAddItemsContainer);
    addSwiftAddButton = (ImageButton) view.findViewById(R.id.addQuickAddButton);

    pendingNutritionDataObservable.subscribe(new Action1<PendingNutritionData>() {
      @Override public void call(PendingNutritionData pendingNutritionData) {
        currentPendingNutritionalData = pendingNutritionData;
      }
    });

    addBreakfastButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AddFoodDialogFragment addFoodDialogFragment =
            AddFoodDialogFragment.newInstance(Constants.MEAL_TYPE_BREAKFAST);

        addFoodDialogFragment.setListener(activity);

        FragmentManager fm = getSupportFragmentManager();

        addFoodDialogFragment.show(fm, "Alert dialog");
      }
    });

    addLunchButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AddFoodDialogFragment addFoodDialogFragment =
            AddFoodDialogFragment.newInstance(Constants.MEAL_TYPE_LUNCH);

        addFoodDialogFragment.setListener(activity);

        FragmentManager fm = getSupportFragmentManager();

        addFoodDialogFragment.show(fm, "Alert dialog");
      }
    });

    addDinnerButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AddFoodDialogFragment addFoodDialogFragment =
            AddFoodDialogFragment.newInstance(Constants.MEAL_TYPE_DINNER);

        addFoodDialogFragment.setListener(activity);

        FragmentManager fm = getSupportFragmentManager();

        addFoodDialogFragment.show(fm, "Alert dialog");
      }
    });

    addSwiftAddButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        QuickAddFoodDialogFragment addFoodDialogFragment = QuickAddFoodDialogFragment.newInstance();

        addFoodDialogFragment.setListener(activity);

        FragmentManager fm = getSupportFragmentManager();

        addFoodDialogFragment.show(fm, "Alert dialog");
      }
    });

    addBreakfastScanButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!needUsersCameraPermission()) {
          pendingMealTypeObserver.onNext(Constants.MEAL_TYPE_BREAKFAST);
          startBarcodeScannerActivity();
        } else {
          currentClickedViewGroup = Constants.MEAL_TYPE_BREAKFAST;
          requestCameraPermission();
        }
      }
    });

    addLunchScanButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!needUsersCameraPermission()) {
          pendingMealTypeObserver.onNext(Constants.MEAL_TYPE_LUNCH);
          startBarcodeScannerActivity();
        } else {
          currentClickedViewGroup = Constants.MEAL_TYPE_LUNCH;
          requestCameraPermission();
        }
      }
    });

    addDinnerScanButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!needUsersCameraPermission()) {
          pendingMealTypeObserver.onNext(Constants.MEAL_TYPE_DINNER);
          startBarcodeScannerActivity();
        } else {
          currentClickedViewGroup = Constants.MEAL_TYPE_DINNER;
          requestCameraPermission();
        }
      }
    });
  }

  @Override protected void onStart() {
    super.onStart();
    int position = DrawerMenuHelper.getNavDrawerIndex(DailyLogActivity.class);
    drawerMenuAdaper.updateWithSelectedPosition(position);
    drawerMenuList.smoothScrollToPosition(position);
    updateColorScheme(3);
    setUpDailyMeals();
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == Constants.CAMERA_PERMISSION_CODE
        && grantResults.length > 0
        && grantResults[0] == PERMISSION_GRANTED) {
      pendingMealTypeObserver.onNext(currentClickedViewGroup);
      startBarcodeScannerActivity();
    }
  }

  private void setUpDailyMeals() {
    breakfastItemsContainer.removeAllViews();
    lunchItemsContainer.removeAllViews();
    dinnerItemsContainer.removeAllViews();

    List<FoodEntry> foodEntryList = RealmUtils.getCurrentDayFoodEntries();

    for (FoodEntry entry : foodEntryList) {
      Log.d("FoodFragment",
          "Current Entry: " + entry.getFoodName() + "\nMeal type: " + entry.getMealEntryType());
      switch (entry.getMealEntryType()) {
        case Constants.MEAL_TYPE_BREAKFAST:
          breakfastItemsContainer.addView(
              generateFoodLogItem(entry.getFoodName(), entry.getCalories()));
          break;

        case Constants.MEAL_TYPE_LUNCH:
          lunchItemsContainer.addView(
              generateFoodLogItem(entry.getFoodName(), entry.getCalories()));
          break;

        case Constants.MEAL_TYPE_DINNER:
          dinnerItemsContainer.addView(
              generateFoodLogItem(entry.getFoodName(), entry.getCalories()));
          break;

        case Constants.MEAL_TYPE_QUICK_ADD:
          quickAddItemsContainer.addView(
              generateFoodLogItem(entry.getFoodName(), entry.getCalories()));
          break;

        default:
          break;
      }
    }
  }

  @Override
  public void onQuickAddSubmit(int callingContainer, String foodName, BaseNutrition baseNutrition) {

    currentPendingNutritionalData.setCurrentCalories(
        currentPendingNutritionalData.getCurrentCalories() + baseNutrition.calories);

    currentPendingNutritionalData.setCurrentProtein(
        currentPendingNutritionalData.getCurrentProtein() + baseNutrition.protein);

    currentPendingNutritionalData.setCurrentFat(
        currentPendingNutritionalData.getCurrentFat() + baseNutrition.fats);

    currentPendingNutritionalData.setCurrentCarb(
        currentPendingNutritionalData.getCurrentCarb() + baseNutrition.carbs);

    pendingNutritionalObserver.onNext(currentPendingNutritionalData);

    RealmUtils.updateCurrentDayPendingNutritionData(currentPendingNutritionalData);

    FoodEntry currentFoodEntry = new FoodEntry();

    currentFoodEntry.setCurrentDate(DateUtils.getCurrentDate());
    currentFoodEntry.setCalories(baseNutrition.calories);
    currentFoodEntry.setProtein(baseNutrition.protein);
    currentFoodEntry.setFats(baseNutrition.fats);
    currentFoodEntry.setCarbs(baseNutrition.carbs);
    currentFoodEntry.setFoodName(foodName);
    currentFoodEntry.setMealEntryType(callingContainer);

    RealmUtils.addFoodEntryToCurrentDay(currentFoodEntry);

    View view = generateFoodLogItem(foodName, baseNutrition.calories);

    if (callingContainer == Constants.MEAL_TYPE_BREAKFAST) {
      breakfastItemsContainer.addView(view);
    } else if (callingContainer == Constants.MEAL_TYPE_LUNCH) {
      lunchItemsContainer.addView(view);
    } else if (callingContainer == Constants.MEAL_TYPE_DINNER) {
      dinnerItemsContainer.addView(view);
    }
  }

  @Override public void onQuickAddSubmit(BaseNutrition baseNutrition) {
    currentPendingNutritionalData.setCurrentCalories(
        currentPendingNutritionalData.getCurrentCalories() + baseNutrition.calories);

    currentPendingNutritionalData.setCurrentProtein(
        currentPendingNutritionalData.getCurrentProtein() + baseNutrition.protein);

    currentPendingNutritionalData.setCurrentFat(
        currentPendingNutritionalData.getCurrentFat() + baseNutrition.fats);

    currentPendingNutritionalData.setCurrentCarb(
        currentPendingNutritionalData.getCurrentCarb() + baseNutrition.carbs);

    pendingNutritionalObserver.onNext(currentPendingNutritionalData);

    RealmUtils.updateCurrentDayPendingNutritionData(currentPendingNutritionalData);

    FoodEntry currentFoodEntry = new FoodEntry();

    currentFoodEntry.setCurrentDate(DateUtils.getCurrentDate());
    currentFoodEntry.setCalories(baseNutrition.calories);
    currentFoodEntry.setProtein(baseNutrition.protein);
    currentFoodEntry.setFats(baseNutrition.fats);
    currentFoodEntry.setCarbs(baseNutrition.carbs);
    currentFoodEntry.setFoodName("Swift Add");
    currentFoodEntry.setMealEntryType(Constants.MEAL_TYPE_QUICK_ADD);

    RealmUtils.addFoodEntryToCurrentDay(currentFoodEntry);

    View view = generateFoodLogItem("Swift Add", baseNutrition.calories);

    quickAddItemsContainer.addView(view);
  }

  public View generateFoodLogItem(String foodName, float calories) {
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    View view = inflater.inflate(R.layout.base_log_item, null);

    TextView tvFoodName = (TextView) view.findViewById(R.id.tvFoodName);
    TextView tvCalories = (TextView) view.findViewById(R.id.tvCalories);

    tvFoodName.setText(foodName);
    tvCalories.setText(calories + "");

    return view;
  }

  private void startBarcodeScannerActivity() {
    Intent intent = new Intent(this, BarcodeScanner.class);
    startActivity(intent);
  }

  private boolean needUsersCameraPermission() {
    return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
        != PERMISSION_GRANTED;
  }

  public void requestCameraPermission() {
    ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA},
        Constants.CAMERA_PERMISSION_CODE);
  }
}
