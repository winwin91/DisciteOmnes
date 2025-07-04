package com.example.disciteomnes.api;

import android.content.Context;
import android.content.SharedPreferences;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// Hier ist die ganze Klasse generiert, da ich mit dem Backend leider am Verzweifeln war!

public class ApiClient {

    private static final String BASE_URL = "http://10.0.2.2:8080/api/";
    private static Retrofit retrofit;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {

            Interceptor interceptor = chain -> {
                SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String token = prefs.getString("TOKEN", "");

                Request original = chain.request();
                Request.Builder builder = original.newBuilder();

                if (!token.isEmpty()) {
                    builder.header("Authorization", "Bearer " + token);
                }

                Request request = builder.build();
                return chain.proceed(request);
            };

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
