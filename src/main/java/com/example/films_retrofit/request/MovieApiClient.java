package com.example.films_retrofit.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.example.films_retrofit.AppExecutors;
import com.example.films_retrofit.Models.MovieModel;
import com.example.films_retrofit.response.MovieSearchResponse;
import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    private final MutableLiveData<List<MovieModel>> mMovies;

    private static MovieApiClient instance;

    private  RetreiveMoviesRunnable retreiveMoviesRunnable;
    public static final String API_Key="d45c640b747e1ff2fda6cf81b7bae24d";

    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }

    public void SearchMoviesAPi(String query, int pageNumber) {
        if(retreiveMoviesRunnable !=null){
            retreiveMoviesRunnable=null;
        }

        retreiveMoviesRunnable=new RetreiveMoviesRunnable(query, pageNumber);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retreiveMoviesRunnable);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }


    //Retreive the data  from RestAPI:
    private class RetreiveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetreiveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }



        @Override
        public void run() {
            //getting the response objects:
            try{
                Response response= getMovies(query,pageNumber).execute();
                if(cancelRequest)
                {
                    return;
                }
                if(response.code()==200)
                {
                    List<MovieModel> list=new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber==1)
                    {
                        mMovies.postValue(list);
                    }else{

                        List<MovieModel> currentMovies=mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                }else{
                    String error=response.errorBody().string();
                    Log.v("Tag","Error"+error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }


            if (cancelRequest) {
                return;
            }
        }

        private Call<MovieSearchResponse> getMovies (String query,int pageNumber)
        {
            return Servicey.getMovieApi().searchMovie(API_Key, query, pageNumber);
        }
    }
}
