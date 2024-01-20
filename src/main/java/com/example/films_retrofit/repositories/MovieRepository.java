package com.example.films_retrofit.repositories;


import androidx.lifecycle.LiveData;

import java.util.List;

import com.example.films_retrofit.Models.MovieModel;
import com.example.films_retrofit.request.MovieApiClient;

public class MovieRepository {

    private  static MovieRepository instance;
    //Live data:
    private MovieApiClient movieApiClient;
    public static MovieRepository getInstance()
    {
        if(instance == null)
        {
            instance=new MovieRepository();
        }
        return instance;
    }
    private MovieRepository()
    {
        movieApiClient=MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies()
    {
        return movieApiClient.getMovies();
    }

    //2- Calling the method in repository:
    public void SearchMoviesApi(String query, int pageNumber)
    {
        movieApiClient.SearchMoviesAPi(query,pageNumber);
    }



}
