package com.amannawabi.bakingtime.Utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static String strBaseUrl = "https://d17h27t6h515a5.cloudfront.net";


    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(strBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
