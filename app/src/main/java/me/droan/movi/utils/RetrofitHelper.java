package me.droan.movi.utils;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import me.droan.movi.MoviApplication;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by drone on 15/04/16.
 */
public class RetrofitHelper {
  public static Retrofit getRetrofitObj() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    if (!MoviApplication.turnOnFabric) {
      httpClient.addInterceptor(logging);
      httpClient.networkInterceptors().add(new StethoInterceptor());
    }
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build();
    return retrofit;
  }
}
