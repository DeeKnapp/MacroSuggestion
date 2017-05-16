package com.dustin.knapp.project.macrosuggestion.networking.services;

import com.dustin.knapp.project.macrosuggestion.models.barcode_objects.UpcLookupResponse;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dknapp on 4/11/17
 */
public interface UpcLookupService {
  @Headers({
      "Content-Type: application/json", "x-app-id: dbcca75c",
      "x-app-key: 9fdab229d0876f88bc286ea1590b1d24", "x-remote-user-id: 0"
  }) @GET("search/item") Observable<UpcLookupResponse> upcLookup(@Query("upc") String upc);
}
