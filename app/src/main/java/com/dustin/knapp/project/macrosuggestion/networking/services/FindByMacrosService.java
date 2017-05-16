package com.dustin.knapp.project.macrosuggestion.networking.services;

import com.dustin.knapp.project.macrosuggestion.models.FoodItem;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dknapp on 4/11/17
 */
public interface FindByMacrosService {
  @Headers({
      "Content-Type: application/json",
      "X-Mashape-Key: jhlTdXmrKwmshpZ7xK1a8QbDQpmmp1S3OcJjsnaQULuhcp2YYf"
  }) @GET("/recipes/findByNutrients?") Observable<List<FoodItem>> findByMacros(
      @Query("maxCalories") int maxCalories, @Query("maxCarbs") int maxCarbs,
      @Query("maxFat") int maxFat, @Query("maxProtein") int maxProtein,
      @Query("minCalories") int minCalories, @Query("minCarbs") int minCarbs,
      @Query("minFat") int minFat, @Query("minProtein") int minProtein, @Query("number") int number,
      @Query("offset") int offset, @Query("random") boolean random);
}
