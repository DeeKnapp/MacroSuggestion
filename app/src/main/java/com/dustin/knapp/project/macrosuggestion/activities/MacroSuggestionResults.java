package com.dustin.knapp.project.macrosuggestion.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.adapters.FoodListAdapter;
import com.dustin.knapp.project.macrosuggestion.models.FoodItem;
import com.dustin.knapp.project.macrosuggestion.models.ExcessMacros;
import com.dustin.knapp.project.macrosuggestion.networking.retrofit.SpoonacularApiServicesUtil;
import com.dustin.knapp.project.macrosuggestion.networking.services.FindByMacrosService;
import java.util.ArrayList;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MacroSuggestionResults extends BaseStandAloneActivity {

  List<FoodItem> foodItems = new ArrayList<>();

  RecyclerView recyclerView;

  FoodListAdapter foodListAdapter;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    View view = LayoutInflater.from(this).inflate(R.layout.macro_suggestions, null);

    this.content.removeAllViews();
    this.content.addView(view);

    toolbar.setBackground(getDrawable(R.drawable.macros_toolbar));

    toolbarTitle.setText("Food Suggestions");

    ExcessMacros excessMacros = (ExcessMacros) getIntent().getSerializableExtra("macros_remaining");

    Log.d("Macros", "Remaining calories: " + excessMacros.maxCalories);
    Log.d("Macros", "Remaining tvProtein: " + excessMacros.maxProtein);
    Log.d("Macros", "Remaining tvCarbs: " + excessMacros.maxCarbs);
    Log.d("Macros", "Remaining tvFats: " + excessMacros.maxFat);

    //getFoodByMacros(excessMacros);
    foodItems.add(generateMockFoodItems("Anchovies With Breadcrumbs & Scallions",
        "https://spoonacular.com/recipeImages/anchovies_with_breadcrumbs_scallions-2.jpg", "jpg", "38", "4g", "2g", "0g"));

    foodItems.add(generateMockFoodItems("Fish Tagine With Tomatoes, Capers, and Cinnamon",
        "https://spoonacular.com/recipeImages/fish-tagine-with-tomatoes-capers-and-cinnamon-2-111567.jpg", "jpg", "319", "36g", "17g", "8g"));

    foodItems.add(
        generateMockFoodItems("Seared Chicken with Avocado", "https://spoonacular.com/recipeImages/seared-chicken-with-avocado-196932.jpg", "jpg",
            "319", "36g", "17g", "8g"));

    foodItems.add(generateMockFoodItems("Lamb Shank Stew with Root Vegetables",
        "https://spoonacular.com/recipeImages/Lamb-Shank-Stew-with-Root-Vegetables-247018.jpg", "jpg", "319", "36g", "17g", "8g"));

    foodItems.add(
        generateMockFoodItems("Southwestern Chicken Salad", "https://spoonacular.com/recipeImages/Southwestern-Chicken-Salad-358838.jpg", "jpg",
            "319", "36g", "17g", "8g"));

    foodItems.add(generateMockFoodItems("Chili I", "https://spoonacular.com/recipeImages/Chili-I-362913.gif", "gif", "319", "36g", "17g", "8g"));

    foodItems.add(generateMockFoodItems("Cream Cake with Bing Cherry Sauce",
        "https://spoonacular.com/recipeImages/Cream-Cake-with-Bing-Cherry-Sauce-457747.png", "png", "319", "36g", "17g", "8g"));

    foodItems.add(
        generateMockFoodItems("More Power Gingerbread Smoothie", "https://spoonacular.com/recipeImages/More-Power-Gingerbread-Smoothie-551315.jpg",
            "jpg", "319", "36g", "17g", "8g"));

    foodItems.add(
        generateMockFoodItems("Slow Cooker Red Lentil Dal", "https://spoonacular.com/recipeImages/Slow-Cooker-Red-Lentil-Dal-573776.jpg", "jpg",
            "319", "36g", "17g", "8g"));

    foodItems.add(generateMockFoodItems("Browned Butter and Lemon Brussels Sprouts",
        "https://spoonacular.com/recipeImages/browned-butter-and-lemon-brussels-sprouts-707586.jpg", "jpg", "319", "36g", "17g", "8g"));

    foodListAdapter = new FoodListAdapter(this, foodItems);

    recyclerView = (RecyclerView) findViewById(R.id.foodItemsRecyclerView);

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(foodListAdapter);

   // getFoodByMacros(excessMacros);
  }

  public FoodItem generateMockFoodItems(String title, String imageUrl, String imageType, String calories, String protein, String fat, String carbs) {
    FoodItem foodItem = new FoodItem();
    foodItem.setId("0");
    foodItem.setTitle(title);
    foodItem.setImage(imageUrl);
    foodItem.setImageType(imageType);
    foodItem.setCalories(calories);
    foodItem.setProtein(protein);
    foodItem.setFat(fat);
    foodItem.setCarbs(carbs);
    return foodItem;
  }

  public void getFoodByMacros(ExcessMacros excessMacros) {
    SpoonacularApiServicesUtil apiServicesUtil = new SpoonacularApiServicesUtil();
    apiServicesUtil.createService(FindByMacrosService.class)
        .findByMacros(excessMacros.maxCalories, excessMacros.maxCarbs, excessMacros.maxFat, excessMacros.maxProtein, excessMacros.minCalories,
            excessMacros.minCarbs, excessMacros.minFat, excessMacros.minProtein, excessMacros.number, excessMacros.offset, excessMacros.random)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<FoodItem>>() {
          @Override public void call(List<FoodItem> foodItems) {
            MacroSuggestionResults.this.foodItems.addAll(foodItems);
            foodListAdapter.notifyDataSetChanged();
            Log.d("Testing", "This shit worked");
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            Log.d("Testing", "this shit didn't work");
          }
        });
  }
}
