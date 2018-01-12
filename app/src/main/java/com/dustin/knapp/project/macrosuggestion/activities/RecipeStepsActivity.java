package com.dustin.knapp.project.macrosuggestion.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.adapters.RecipeInstructionAdapter;
import com.dustin.knapp.project.macrosuggestion.models.recipe.Ingredients;
import com.dustin.knapp.project.macrosuggestion.models.recipe.RecipeIngredients;
import com.dustin.knapp.project.macrosuggestion.models.recipe.Steps;
import com.dustin.knapp.project.macrosuggestion.networking.retrofit.SpoonacularApiServicesUtil;
import com.dustin.knapp.project.macrosuggestion.networking.services.RecipeService;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.view.View.GONE;

/**
 * Created by dknapp on 12/12/17.
 */


//todo add FAB star icon for favorite
public class RecipeStepsActivity extends BaseActivity {

  private String json = "{  \n"
      + "   \"vegetarian\":true,\n"
      + "   \"vegan\":false,\n"
      + "   \"glutenFree\":false,\n"
      + "   \"dairyFree\":false,\n"
      + "   \"veryHealthy\":false,\n"
      + "   \"cheap\":false,\n"
      + "   \"veryPopular\":false,\n"
      + "   \"sustainable\":false,\n"
      + "   \"weightWatcherSmartPoints\":12,\n"
      + "   \"gaps\":\"no\",\n"
      + "   \"lowFodmap\":false,\n"
      + "   \"ketogenic\":false,\n"
      + "   \"whole30\":false,\n"
      + "   \"servings\":8,\n"
      + "   \"preparationMinutes\":20,\n"
      + "   \"cookingMinutes\":30,\n"
      + "   \"sourceUrl\":\"http://www.tasteofhome.com/Recipes/pecan-sweet-potato-bake\",\n"
      + "   \"spoonacularSourceUrl\":\"https://spoonacular.com/pecan-sweet-potato-bake-386395\",\n"
      + "   \"aggregateLikes\":0,\n"
      + "   \"spoonacularScore\":26.0,\n"
      + "   \"healthScore\":3.0,\n"
      + "   \"creditText\":\"Taste of Home\",\n"
      + "   \"sourceName\":\"Taste of Home\",\n"
      + "   \"pricePerServing\":63.84,\n"
      + "   \"extendedIngredients\":[  \n"
      + "      {  \n"
      + "         \"id\":19334,\n"
      + "         \"aisle\":\"Baking\",\n"
      + "         \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/brown-sugar-light.jpg\",\n"
      + "         \"consistency\":\"solid\",\n"
      + "         \"name\":\"brown sugar\",\n"
      + "         \"amount\":0.5,\n"
      + "         \"unit\":\"cup\",\n"
      + "         \"unitShort\":\"cup\",\n"
      + "         \"unitLong\":\"cups\",\n"
      + "         \"originalString\":\"1/2 cup packed brown sugar\",\n"
      + "         \"metaInformation\":[  \n"
      + "            \"packed\"\n"
      + "         ]\n"
      + "      },\n"
      + "      {  \n"
      + "         \"id\":1001,\n"
      + "         \"aisle\":\"Milk, Eggs, Other Dairy\",\n"
      + "         \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/butter-sliced.jpg\",\n"
      + "         \"consistency\":\"solid\",\n"
      + "         \"name\":\"butter\",\n"
      + "         \"amount\":0.25,\n"
      + "         \"unit\":\"cup\",\n"
      + "         \"unitShort\":\"cup\",\n"
      + "         \"unitLong\":\"cups\",\n"
      + "         \"originalString\":\"1/4 cup cold butter, cubed\",\n"
      + "         \"metaInformation\":[  \n"
      + "            \"cold\",\n"
      + "            \"cubed\"\n"
      + "         ]\n"
      + "      },\n"
      + "      {  \n"
      + "         \"id\":1123,\n"
      + "         \"aisle\":\"Milk, Eggs, Other Dairy\",\n"
      + "         \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/egg.jpg\",\n"
      + "         \"consistency\":\"solid\",\n"
      + "         \"name\":\"eggs\",\n"
      + "         \"amount\":2.0,\n"
      + "         \"unit\":\"\",\n"
      + "         \"unitShort\":\"\",\n"
      + "         \"unitLong\":\"\",\n"
      + "         \"originalString\":\"2 eggs\",\n"
      + "         \"metaInformation\":[  \n"
      + "\n"
      + "         ]\n"
      + "      },\n"
      + "      {  \n"
      + "         \"id\":20081,\n"
      + "         \"aisle\":\"Baking\",\n"
      + "         \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/flour.png\",\n"
      + "         \"consistency\":\"solid\",\n"
      + "         \"name\":\"flour\",\n"
      + "         \"amount\":2.0,\n"
      + "         \"unit\":\"tablespoons\",\n"
      + "         \"unitShort\":\"Tbsp\",\n"
      + "         \"unitLong\":\"tablespoons\",\n"
      + "         \"originalString\":\"2 tablespoons all-purpose flour\",\n"
      + "         \"metaInformation\":[  \n"
      + "            \"all-purpose\"\n"
      + "         ]\n"
      + "      },\n"
      + "      {  \n"
      + "         \"id\":1049,\n"
      + "         \"aisle\":\"Milk, Eggs, Other Dairy\",\n"
      + "         \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/fluid-cream.jpg\",\n"
      + "         \"consistency\":\"solid\",\n"
      + "         \"name\":\"half & half cream\",\n"
      + "         \"amount\":0.25,\n"
      + "         \"unit\":\"cup\",\n"
      + "         \"unitShort\":\"cup\",\n"
      + "         \"unitLong\":\"cups\",\n"
      + "         \"originalString\":\"1/4 cup half-and-half cream\",\n"
      + "         \"metaInformation\":[  \n"
      + "\n"
      + "         ]\n"
      + "      },\n"
      + "      {  \n"
      + "         \"id\":12142,\n"
      + "         \"aisle\":\"Nuts;Baking\",\n"
      + "         \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/pecans.jpg\",\n"
      + "         \"consistency\":\"solid\",\n"
      + "         \"name\":\"pecans\",\n"
      + "         \"amount\":0.5,\n"
      + "         \"unit\":\"cup\",\n"
      + "         \"unitShort\":\"cup\",\n"
      + "         \"unitLong\":\"cups\",\n"
      + "         \"originalString\":\"1/2 cup chopped pecans\",\n"
      + "         \"metaInformation\":[  \n"
      + "            \"chopped\"\n"
      + "         ]\n"
      + "      },\n"
      + "      {  \n"
      + "         \"id\":2047,\n"
      + "         \"aisle\":\"Spices and Seasonings\",\n"
      + "         \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/salt.jpg\",\n"
      + "         \"consistency\":\"solid\",\n"
      + "         \"name\":\"salt\",\n"
      + "         \"amount\":0.125,\n"
      + "         \"unit\":\"teaspoon\",\n"
      + "         \"unitShort\":\"tsp\",\n"
      + "         \"unitLong\":\"teaspoons\",\n"
      + "         \"originalString\":\"1/8 teaspoon salt\",\n"
      + "         \"metaInformation\":[  \n"
      + "\n"
      + "         ]\n"
      + "      },\n"
      + "      {  \n"
      + "         \"id\":19335,\n"
      + "         \"aisle\":\"Baking\",\n"
      + "         \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/sugar-cubes.jpg\",\n"
      + "         \"consistency\":\"solid\",\n"
      + "         \"name\":\"sugar\",\n"
      + "         \"amount\":0.5,\n"
      + "         \"unit\":\"cup\",\n"
      + "         \"unitShort\":\"cup\",\n"
      + "         \"unitLong\":\"cups\",\n"
      + "         \"originalString\":\"1/2 cup sugar\",\n"
      + "         \"metaInformation\":[  \n"
      + "\n"
      + "         ]\n"
      + "      },\n"
      + "      {  \n"
      + "         \"id\":11507,\n"
      + "         \"aisle\":\"Produce\",\n"
      + "         \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/sweet-potato.jpg\",\n"
      + "         \"consistency\":\"solid\",\n"
      + "         \"name\":\"sweet potatoes\",\n"
      + "         \"amount\":3.0,\n"
      + "         \"unit\":\"cups\",\n"
      + "         \"unitShort\":\"cup\",\n"
      + "         \"unitLong\":\"cups\",\n"
      + "         \"originalString\":\"3 cups mashed sweet potatoes\",\n"
      + "         \"metaInformation\":[  \n"
      + "            \"mashed\"\n"
      + "         ]\n"
      + "      },\n"
      + "      {  \n"
      + "         \"id\":2050,\n"
      + "         \"aisle\":\"Baking\",\n"
      + "         \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/vanilla-extract.jpg\",\n"
      + "         \"consistency\":\"liquid\",\n"
      + "         \"name\":\"vanilla extract\",\n"
      + "         \"amount\":2.0,\n"
      + "         \"unit\":\"teaspoons\",\n"
      + "         \"unitShort\":\"tsp\",\n"
      + "         \"unitLong\":\"teaspoons\",\n"
      + "         \"originalString\":\"2 teaspoons vanilla extract\",\n"
      + "         \"metaInformation\":[  \n"
      + "\n"
      + "         ]\n"
      + "      }\n"
      + "   ],\n"
      + "   \"id\":386395,\n"
      + "   \"title\":\"Pecan Sweet Potato Bake\",\n"
      + "   \"readyInMinutes\":50,\n"
      + "   \"image\":\"https://spoonacular.com/recipeImages/386395-556x370.jpg\",\n"
      + "   \"imageType\":\"jpg\",\n"
      + "   \"cuisines\":[  \n"
      + "\n"
      + "   ],\n"
      + "   \"dishTypes\":[  \n"
      + "      \"side dish\"\n"
      + "   ],\n"
      + "   \"diets\":[  \n"
      + "      \"lacto ovo vegetarian\"\n"
      + "   ],\n"
      + "   \"occasions\":[  \n"
      + "\n"
      + "   ],\n"
      + "   \"winePairing\":{  \n"
      + "\n"
      + "   },\n"
      + "   \"instructions\":\"Directions                                                                                In a large bowl, combine the first seven ingredients; beat until light and fluffy. Transfer to a greased 11-in. x 7-in. baking dish.                                                                                              For topping, combine the brown sugar and flour in a small bowl; cut in butter until crumbly. Fold in pecans. Sprinkle over sweet potato mixture.                                                                                            Bake, uncovered, at 350° for 30-35 minutes or until a thermometer reads 160°.                                        Yield: 6-8 servings.                                                                                                                         Originally published as Pecan Sweet Potato Bake in  Taste of HomeDecember/January 2006, p41                                                                                                                                                                                                        Nutritional Facts                                                                             1 serving (3/4 cup) equals 419 calories, 19 g fat (8 g saturated fat), 88 mg cholesterol, 194 mg sodium, 59 g carbohydrate, 3 g fiber, 5 g protein.                                                                                                                                                                     Print                                                                                    Add to Recipe Box                            Email a Friend\",\n"
      + "   \"analyzedInstructions\":[  \n"
      + "      {  \n"
      + "         \"name\":\"\",\n"
      + "         \"steps\":[  \n"
      + "            {  \n"
      + "               \"number\":1,\n"
      + "               \"step\":\"In a large bowl, combine the first seven ingredients; beat until light and fluffy.\",\n"
      + "               \"ingredients\":[  \n"
      + "\n"
      + "               ],\n"
      + "               \"equipment\":[  \n"
      + "                  {  \n"
      + "                     \"id\":404783,\n"
      + "                     \"name\":\"bowl\",\n"
      + "                     \"image\":\"https://spoonacular.com/cdn/equipment_100x100/bowl.jpg\"\n"
      + "                  }\n"
      + "               ]\n"
      + "            },\n"
      + "            {  \n"
      + "               \"number\":2,\n"
      + "               \"step\":\"Transfer to a greased 11-in. x 7-in. baking dish.\",\n"
      + "               \"ingredients\":[  \n"
      + "\n"
      + "               ],\n"
      + "               \"equipment\":[  \n"
      + "                  {  \n"
      + "                     \"id\":404646,\n"
      + "                     \"name\":\"baking pan\",\n"
      + "                     \"image\":\"https://spoonacular.com/cdn/equipment_100x100/roasting-pan.jpg\"\n"
      + "                  }\n"
      + "               ]\n"
      + "            },\n"
      + "            {  \n"
      + "               \"number\":3,\n"
      + "               \"step\":\"For topping, combine the brown sugar and flour in a small bowl; cut in butter until crumbly. Fold in pecans. Sprinkle over sweet potato mixture.\",\n"
      + "               \"ingredients\":[  \n"
      + "                  {  \n"
      + "                     \"id\":11507,\n"
      + "                     \"name\":\"sweet potato\",\n"
      + "                     \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/sweet-potato.jpg\"\n"
      + "                  },\n"
      + "                  {  \n"
      + "                     \"id\":19334,\n"
      + "                     \"name\":\"brown sugar\",\n"
      + "                     \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/brown-sugar-dark.jpg\"\n"
      + "                  },\n"
      + "                  {  \n"
      + "                     \"id\":1001,\n"
      + "                     \"name\":\"butter\",\n"
      + "                     \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/butter-sliced.jpg\"\n"
      + "                  },\n"
      + "                  {  \n"
      + "                     \"id\":12142,\n"
      + "                     \"name\":\"pecans\",\n"
      + "                     \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/pecans.jpg\"\n"
      + "                  },\n"
      + "                  {  \n"
      + "                     \"id\":20081,\n"
      + "                     \"name\":\"all purpose flour\",\n"
      + "                     \"image\":\"https://spoonacular.com/cdn/ingredients_100x100/flour.png\"\n"
      + "                  }\n"
      + "               ],\n"
      + "               \"equipment\":[  \n"
      + "                  {  \n"
      + "                     \"id\":404783,\n"
      + "                     \"name\":\"bowl\",\n"
      + "                     \"image\":\"https://spoonacular.com/cdn/equipment_100x100/bowl.jpg\"\n"
      + "                  }\n"
      + "               ]\n"
      + "            },\n"
      + "            {  \n"
      + "               \"number\":4,\n"
      + "               \"step\":\"Bake, uncovered, at 350° for 30-35 minutes or until a thermometer reads 160°.\",\n"
      + "               \"ingredients\":[  \n"
      + "\n"
      + "               ],\n"
      + "               \"equipment\":[  \n"
      + "                  {  \n"
      + "                     \"id\":404789,\n"
      + "                     \"name\":\"kitchen thermometer\",\n"
      + "                     \"image\":\"https://spoonacular.com/cdn/equipment_100x100/food-thermometer.jpg\"\n"
      + "                  }\n"
      + "               ],\n"
      + "               \"length\":{  \n"
      + "                  \"number\":35,\n"
      + "                  \"unit\":\"minutes\"\n"
      + "               }\n"
      + "            }\n"
      + "         ]\n"
      + "      }\n"
      + "   ]\n"
      + "}";

  ImageView toolbarImageView;
  private CollapsingToolbarLayout collapsingToolbarLayout;
  Toolbar toolbar;
  private List<Steps> stepsList = new ArrayList<>();
  private RecyclerView instructionsRecyclerView;
  private boolean appBarExpanded;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.recipe_activity);
    // ingredientsList = (ViewGroup) view.findViewById(R.id.ingredientsList);
    instructionsRecyclerView = (RecyclerView) findViewById(R.id.instructionRecyclerView);
    collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

    // collapsingToolbarLayout.setTitle("");

    setSupportActionBar(toolbar);

    //appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
    //  @Override public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
    //    if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
    //      toolbar.setVisibility(View.VISIBLE);
    //      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //    } else {
    //      toolbar.setVisibility(GONE);
    //      getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    //    }
    //  }
    //});

    //toolbar.setBackground(getDrawable(R.drawable.macros_toolbar));
    collapsingToolbarLayout.setBackground(getDrawable(R.drawable.macros_toolbar));
    toolbarImageView = (ImageView) findViewById(R.id.toolbarImageView);

    Intent intent = getIntent();
    String id = intent.getStringExtra("foodItemId");
    String foodName = intent.getStringExtra("foodItemName");
    String imageUrl = intent.getStringExtra("imageUrl");
    //toolbarTitle.setText("Recipe - " + foodName);

    appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

      @Override public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //  Vertical offset == 0 indicates appBar is fully expanded.
        if (Math.abs(verticalOffset) > 300) {
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
          collapsingToolbarLayout.setTitle("Detailed Recipe");
        } else {
          collapsingToolbarLayout.setTitle(" ");
          getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
      }
    });

    //final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.back);
    //upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    //getSupportActionBar().setHomeAsUpIndicator(upArrow);
    collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
    collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

    Glide.with(this).load(imageUrl).into(toolbarImageView);

    //   id = "386395";

    Gson gson = new Gson();

    RecipeIngredients recipeIngredients = gson.fromJson(json, RecipeIngredients.class);

    populateRecipe(recipeIngredients);

    //getRecipe("386395");
  }

  private void populateRecipe(RecipeIngredients recipeIngredients) {

    Collections.addAll(stepsList, recipeIngredients.getAnalyzedInstructions()[0].getSteps());
    //for (Steps recipeStep : stepsList) {
    //  for (Ingredients ingredient : recipeStep.getIngredients()) {
    //    ingredientsList.addView(generateIngredientView(ingredient.getName()));
    //  }
    //}

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    instructionsRecyclerView.setLayoutManager(layoutManager);
    RecipeInstructionAdapter adapter = new RecipeInstructionAdapter(stepsList);
    instructionsRecyclerView.setAdapter(adapter);

    Log.d("TESTING", " Item count : " + adapter.getItemCount());
  }

  public View generateIngredientView(String ingredientName) {
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    View view = inflater.inflate(R.layout.ingredients_listing, null);
    TextView tvIngredient = (TextView) view.findViewById(R.id.ingredientText);
    tvIngredient.setText(ingredientName);
    return view;
  }

  public void getRecipe(String id) {
    SpoonacularApiServicesUtil apiServicesUtil = new SpoonacularApiServicesUtil();
    apiServicesUtil.createService(RecipeService.class)
        .getRecipe(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<RecipeIngredients>() {
          @Override public void call(RecipeIngredients recipeSteps) {
            Log.d("Testing", "This shit worked");
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            Log.d("Testing", "this shit didn't work");
          }
        });
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
