package com.example.films_retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import com.example.films_retrofit.Models.MovieModel;
import com.example.films_retrofit.adapters.MovieRecycleView;
import com.example.films_retrofit.adapters.OnMovieListener;
import com.example.films_retrofit.viewmodels.MovieListViewModel;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {
    private RecyclerView recycleView;
    private MovieRecycleView movieRecycleAdapter;
    private MovieListViewModel movieListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
          setSupportActionBar(toolbar);
        SetupSearchView();

        recycleView = findViewById(R.id.recycleView);
        movieListViewModel=new  ViewModelProvider(this).get(MovieListViewModel.class);
        ConfigureRecyclerView();
        ObserveAnyChange();

    }



    private void ObserveAnyChange()
    {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels !=null)
                {
                    for(MovieModel movieModel:movieModels)
                    {
                        movieRecycleAdapter.setmMovies(movieModels);

                    }
                }
            }
        });
    }


    private void ConfigureRecyclerView()
    {
        movieRecycleAdapter=new MovieRecycleView(this);
        recycleView.setAdapter(movieRecycleAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onMovieClick(int position) {
        Intent intent=new Intent(this,MovieDetails.class);
        intent.putExtra("movie",movieRecycleAdapter.getSelectedMovie(position));
        startActivity(intent);


    }


    private void SetupSearchView() {
        final SearchView searchView=findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                movieListViewModel.SearchMoviesApi(query,1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}