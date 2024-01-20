package com.example.films_retrofit.request;

import com.example.films_retrofit.MovieApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class Servicey {

    public static final String Base_URL="https://api.themoviedb.org";

    private static Retrofit.Builder retrofitBuilder=
            new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit=retrofitBuilder.build();
    private static MovieApi movieApi= retrofit.create(MovieApi.class);
    public static MovieApi getMovieApi()
    {
        return movieApi;
    }


}

