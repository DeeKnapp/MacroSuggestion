package com.dustin.knapp.project.macrosuggestion.networking.retrofit;

import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jmai on 11/11/16.
 */
@Singleton public final class SpoonacularApiServicesUtil {
  String baseUrl = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com";

  private Retrofit retrofit;

  @Inject public SpoonacularApiServicesUtil() {
  }

  public <S> S createService(Class<S> serviceClass) {
    if (this.retrofit == null) {
      this.retrofit = initRetrofit();
    }
    return retrofit.create(serviceClass);
  }

  private Retrofit initRetrofit() {
    //HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    //// set your desired log level
    //logging.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

    httpClientBuilder.writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS);

    return new Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(httpClientBuilder.build())
        .build();
  }
}

