package com.dustin.knapp.project.macrosuggestion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dustin.knapp.project.macrosuggestion.MacroSuggestionApplication;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.FoodEntry;
import com.dustin.knapp.project.macrosuggestion.models.PendingNutritionData;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuHelper;
import com.dustin.knapp.project.macrosuggestion.navigation_drawer.DrawerMenuItem;
import com.dustin.knapp.project.macrosuggestion.presenters.colories_fragment.CaloriesPresenter;
import com.dustin.knapp.project.macrosuggestion.utils.RealmUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

public class DailyLogActivity extends BaseNavDrawerActivity {

    PendingNutritionData currentPendingNutritionalData;

    LinearLayout foodEntryContainer;
    FloatingActionButton fabCalendar;

    @Inject
    public Observer<Integer> pendingMealTypeObserver;
    @Inject
    public Observer<PendingNutritionData> pendingNutritionalObserver;
    @Inject
    public Observable<PendingNutritionData> pendingNutritionDataObservable;
    @Inject
    public CaloriesPresenter caloriesPresenter;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(this).inflate(R.layout.daily_log_activity, null);

        ((MacroSuggestionApplication) getApplication()).getAppComponent().inject(this);
        this.content.removeAllViews();
        this.content.addView(view);

        foodEntryContainer = ButterKnife.findById(this, R.id.entryListContainer);
        fabCalendar = ButterKnife.findById(this, R.id.fab);

        toolbarTitle.setText(DrawerMenuItem.getTitle(DailyLogActivity.class));

        pendingNutritionDataObservable.subscribe(new Action1<PendingNutritionData>() {
            @Override
            public void call(PendingNutritionData pendingNutritionData) {
                currentPendingNutritionalData = pendingNutritionData;
            }
        });

        fabCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HistoryCalendarActivity.class));
                Log.d("TESTING", "FAB calendar clicked...");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        int position = DrawerMenuHelper.getNavDrawerIndex(DailyLogActivity.class);
        drawerMenuAdaper.updateWithSelectedPosition(position);
        drawerMenuList.smoothScrollToPosition(position);
        updateColorScheme(3);
        setUpDailyMeals();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == Constants.CAMERA_PERMISSION_CODE
//                && grantResults.length > 0
//                && grantResults[0] == PERMISSION_GRANTED) {
//            pendingMealTypeObserver.onNext(currentClickedViewGroup);
//            startBarcodeScannerActivity();
//        }
//    }

    private void setUpDailyMeals() {
        foodEntryContainer.removeAllViews();

        List<FoodEntry> foodEntryList = RealmUtils.getCurrentDayFoodEntries();

        for (FoodEntry entry : foodEntryList) {
            Log.d("Test", "Food Entry name: " + entry.getFoodName());
            foodEntryContainer.addView(generateFoodLogItem(entry.getFoodName(), entry.getCalories(), entry.getTimeStamp()));
        }
    }

    public View generateFoodLogItem(String foodName, float calories, String timeStamp) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.base_log_item, null);

        TextView tvTimeStamp = (TextView) view.findViewById(R.id.tvTimeStamp);
        TextView tvFoodName = (TextView) view.findViewById(R.id.tvFoodName);
        TextView tvCalories = (TextView) view.findViewById(R.id.tvCalories);

        tvTimeStamp.setText(timeStamp);
        tvFoodName.setText(foodName);
        tvCalories.setText(calories + "");

        return view;
    }

//    private void startBarcodeScannerActivity() {
//        Intent intent = new Intent(this, BarcodeScanner.class);
//        startActivity(intent);
//    }
//
//    private boolean needUsersCameraPermission() {
//        return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
//                != PERMISSION_GRANTED;
//    }
//
//    public void requestCameraPermission() {
//        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
//                Constants.CAMERA_PERMISSION_CODE);
//    }
}
