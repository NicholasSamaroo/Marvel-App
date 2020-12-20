package com.example.marvel;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://gateway.marvel.com/";
    /*public static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder().addHeader("apiKey", "c55b9a56c8fd2080fc238d4666c2386d").build();
            return chain.proceed(request);
        }
    };*/

    public static Retrofit getRetrofitInstance() {
        if(retrofit == null) {
            //OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //builder.interceptors().add(interceptor);
            //OkHttpClient client = builder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
