package com.dustin.knapp.project.macrosuggestion.networking.services;

import com.dustin.knapp.project.macrosuggestion.models.recipe.RecipeIngredients;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by dknapp on 4/11/17
 */
public interface RecipeService {
  @Headers({
      "Content-Type: application/json", "X-Mashape-Key: jhlTdXmrKwmshpZ7xK1a8QbDQpmmp1S3OcJjsnaQULuhcp2YYf"
  }) @GET("recipes/{id}/information") Observable<RecipeIngredients> getRecipe(@Path("id") String id);
}
