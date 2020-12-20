package com.example.marvel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelAPI {


    @GET("v1/public/characters")
    Call<Wrapper> getCharacters(@Query("ts") int ts,
                             @Query("hash") String hash,
                             @Query("nameStartsWith") String character,
                             @Query("apikey") String apikey);
}
