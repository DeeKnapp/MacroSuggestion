package com.dustin.knapp.project.macrosuggestion.networking.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
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
        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(new OkHttpClient())
        .build();
  }

  private static OkHttpClient getUnsafeOkHttpClient() {
    try {
      // Create a trust manager that does not validate certificate chains
      final TrustManager[] trustAllCerts = new TrustManager[] {
          new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
              return new java.security.cert.X509Certificate[]{};
            }
          }
      };

      // Install the all-trusting trust manager
      final SSLContext sslContext = SSLContext.getInstance("SSL");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
      // Create an ssl socket factory with our all-trusting manager
      final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

      OkHttpClient.Builder builder = new OkHttpClient.Builder();
      builder.sslSocketFactory(sslSocketFactory);
      builder.hostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });

      OkHttpClient okHttpClient = builder.build();
      return okHttpClient;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
