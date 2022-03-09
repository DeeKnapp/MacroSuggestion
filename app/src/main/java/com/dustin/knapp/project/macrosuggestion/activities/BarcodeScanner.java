package com.dustin.knapp.project.macrosuggestion.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.FoodEntry;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.models.ScannerNutritionalData;
import com.dustin.knapp.project.macrosuggestion.models.barcode_objects.UpcLookupResponse;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuHelper;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuItem;
import com.dustin.knapp.project.macrosuggestion.utils.DateUtils;
import com.dustin.knapp.project.macrosuggestion.utils.storage.FirebaseUtils;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.google.android.gms.vision.barcode.Barcode;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by dknapp on 4/14/17
 */
public class BarcodeScanner extends BaseNavDrawerActivity
    implements Spinner.OnItemSelectedListener {

  TextView barcodeInfo, etFoodName, tvCalories, tvFats, tvCarbs, tvProtein;
  View view;

  Spinner servingSizeSpinner;

  int servingSize = 0;

  @Inject public Observable<Integer> pendingFoodTypeObservable;
  @Inject public Observer<PendingNutritionData> pendingNutritionalObserver;
  @Inject public Observable<PendingNutritionData> pendingNutritionalObservable;

  public ScannerNutritionalData scannerNutritionalData;

  List<Integer> items = Arrays.asList(1, 2, 3, 4, 5);

  private PendingNutritionData currentPendingNutritionalData;
  private int pendingMealType;

  //todo option to add data manual if incorrect.
  //todo store user entries into firebase database
  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    view = LayoutInflater.from(this).inflate(R.layout.barcode_scanner, null);

    ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);

    this.content.removeAllViews();
    this.content.addView(view);

    toolbarTitle.setText(DrawerMenuItem.getTitle(BarcodeScanner.class));
    toolbar.setBackgroundColor(
        ResourcesCompat.getColor(getResources(), R.color.dgm_green, null));

    barcodeInfo = (TextView) findViewById(R.id.code_info);
    etFoodName = (TextView) findViewById(R.id.etFoodName);
    tvCalories = (TextView) findViewById(R.id.etCalories);
    tvFats = (TextView) findViewById(R.id.etFats);
    tvCarbs = (TextView) findViewById(R.id.etCarbs);
    tvProtein = (TextView) findViewById(R.id.etProtein);
    servingSizeSpinner = (Spinner) findViewById(R.id.servingSizeSpinner);

    ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, items);

    servingSizeSpinner.setAdapter(spinnerAdapter);
    servingSizeSpinner.setOnItemSelectedListener(this);

    pendingNutritionalObservable.subscribe(new Action1<PendingNutritionData>() {
      @Override public void call(PendingNutritionData pendingNutritionData) {
        currentPendingNutritionalData = pendingNutritionData;
      }
    });

    pendingFoodTypeObservable.subscribe(new Action1<Integer>() {
      @Override public void call(Integer integer) {
        pendingMealType = integer;
      }
    });

    Button verifyDataButton = (Button) findViewById(R.id.dataIsCorrectButton);

    verifyDataButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        float calories = Float.valueOf(tvCalories.getText().toString()) * servingSize;
        float fats = Float.valueOf(tvFats.getText().toString()) * servingSize;
        float carbs = Float.valueOf(tvCarbs.getText().toString()) * servingSize;
        float protein = Float.valueOf(tvProtein.getText().toString()) * servingSize;

        currentPendingNutritionalData.setCurrentCalories(
            currentPendingNutritionalData.getCurrentCalories() + calories);

        currentPendingNutritionalData.setCurrentProtein(
            currentPendingNutritionalData.getCurrentProtein() + protein);

        currentPendingNutritionalData.setCurrentFat(
            currentPendingNutritionalData.getCurrentFat() + fats);

        currentPendingNutritionalData.setCurrentCarb(
            currentPendingNutritionalData.getCurrentCarb() + carbs);

        pendingNutritionalObserver.onNext(currentPendingNutritionalData);

        FoodEntry currentFoodEntry = new FoodEntry();
        currentFoodEntry.setCurrentDate(DateUtils.getCurrentDateString());
        currentFoodEntry.setCalories(calories);
        currentFoodEntry.setProtein(protein);
        currentFoodEntry.setFats(fats);
        currentFoodEntry.setCarbs(carbs);
        currentFoodEntry.setFoodName(etFoodName.getText().toString());

       // FirebaseUtils.saveFoodEntryToFirebase(currentFoodEntry, userObject.getUUID());

        finish();
      }
    });
    startScan();
  }

  @Override protected void onStart() {
    super.onStart();
    int position = DrawerMenuHelper.getNavDrawerIndex(BarcodeScanner.class);
    drawerMenuAdaper.updateWithSelectedPosition(position);
    drawerMenuList.smoothScrollToPosition(position);
  }

  private void startScan() {
    /**
     * Build a new MaterialBarcodeScanner
     */
    final MaterialBarcodeScanner materialBarcodeScanner =
        new MaterialBarcodeScannerBuilder().withActivity(BarcodeScanner.this)
            .withEnableAutoFocus(true)
            .withBleepEnabled(true)
            .withBackfacingCamera()
            .withCenterTracker(R.drawable.scanner_bar, R.drawable.scanner_bar_success)
            .withText("Scanning...")
            .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
              @Override public void onResult(Barcode barcode) {
                barcodeInfo.setText("UPC Code - " + barcode.rawValue);
                lookupByUpc(barcode.rawValue);
              }
            })
            .build();
    materialBarcodeScanner.startScan();
  }

  public void lookupByUpc(String upc) {
    ScannerNutritionalData scannerNutritionalData = new ScannerNutritionalData();
    scannerNutritionalData.calories = "170";
    scannerNutritionalData.carbohydrates = "5";
    scannerNutritionalData.protein = "8";
    scannerNutritionalData.totalFat = "14";
    scannerNutritionalData.foodName = "Peanuts, Dry Roasted Unsalted";
    updateUi(scannerNutritionalData);

    //NutritionixApiServicesUtil apiServicesUtil = new NutritionixApiServicesUtil();
    //apiServicesUtil.createService(UpcLookupService.class)
    //    .upcLookup(upc)
    //    .subscribeOn(Schedulers.io())
    //    .observeOn(AndroidSchedulers.mainThread())
    //    .subscribe(new Action1<UpcLookupResponse>() {
    //      @Override public void call(UpcLookupResponse upcLookupResponse) {
    //        extractNutritionalData(upcLookupResponse);
    //        updateUi(scannerNutritionalData);
    //      }
    //    }, new Action1<Throwable>() {
    //      @Override public void call(Throwable throwable) {
    //        Log.d("Testing", "this shit didn't work");
    //      }
    //    });
  }

  public void updateUi(ScannerNutritionalData scannerNutritionalData) {
    if (!scannerNutritionalData.foodName.equals("")) {
      etFoodName.setText(scannerNutritionalData.foodName);
    } else {
      etFoodName.setText("Unknown");
    }
    if (!scannerNutritionalData.calories.equals("")) {
      tvCalories.setText(scannerNutritionalData.calories);
    } else {
      tvCalories.setText("0g");
    }
    if (!scannerNutritionalData.totalFat.equals("")) {
      tvFats.setText(scannerNutritionalData.totalFat);
    } else {
      tvFats.setText("0g");
    }
    if (!scannerNutritionalData.carbohydrates.equals("")) {
      tvCarbs.setText(scannerNutritionalData.carbohydrates);
    } else {
      tvCarbs.setText("0g");
    }
    if (!scannerNutritionalData.protein.equals("")) {
      tvProtein.setText(scannerNutritionalData.protein);
    } else {
      tvProtein.setText("0g");
    }
  }

  private void extractNutritionalData(UpcLookupResponse response) {
    scannerNutritionalData = new ScannerNutritionalData();
    scannerNutritionalData.foodName = response.foods[0].food_name;
    scannerNutritionalData.calories = response.foods[0].nf_calories;
    scannerNutritionalData.totalFat = response.foods[0].nf_total_fat;
    scannerNutritionalData.carbohydrates = response.foods[0].nf_total_carbohydrate;
    scannerNutritionalData.protein = response.foods[0].nf_protein;
  }

  @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    servingSize = (int) parent.getItemAtPosition(position);
  }

  @Override public void onNothingSelected(AdapterView<?> parent) {

  }
}
