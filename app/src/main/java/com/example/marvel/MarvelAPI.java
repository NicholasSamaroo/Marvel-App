package com.example.marvel;

import com.example.marvel.Models.Wrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelAPI {


    @GET("v1/public/characters")
    Call<Wrapper> getCharacters(@Query("ts") long ts,
                                @Query("hash") String hash,
                                @Query("nameStartsWith") String character,
                                @Query("apikey") String apikey);
}
