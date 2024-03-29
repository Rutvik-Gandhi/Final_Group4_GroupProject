package com.nihar.final_group4_groupproject.api;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static Retrofit postClient() {
        String baseURL = "https://www.rockingbharat.com/bvp_patan/";
        Retrofit retrofit = null;

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(80000, TimeUnit.SECONDS)
                .readTimeout(80000, TimeUnit.SECONDS)
                .writeTimeout(80000, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
        }

        return retrofit;
    }
}
