package com.example.films_retrofit.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import com.example.films_retrofit.Models.MovieModel;
import com.example.films_retrofit.repositories.MovieRepository;

public class MovieListViewModel extends ViewModel {

    private final MovieRepository movieRepository;

    public MovieListViewModel()
    {
        movieRepository=MovieRepository.getInstance();
    }


    public LiveData<List<MovieModel>> getMovies() {
        return movieRepository.getMovies();
    }
    public void SearchMoviesApi(String query, int pageNumber)
    {
        movieRepository.SearchMoviesApi(query,pageNumber);
    }




}
