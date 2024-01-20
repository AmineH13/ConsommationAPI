package com.example.films_retrofit.adapters;


import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.films_retrofit.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title,release_date;
    ImageView imageView;

    OnMovieListener onMovieListener;




    public MovieViewHolder(@NonNull View itemView,OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener=onMovieListener;
        title=itemView.findViewById(R.id.movie_title);
        release_date=itemView.findViewById(R.id.originale_langage);

        imageView=itemView.findViewById(R.id.movie_img);

        imageView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getAdapterPosition());

    }
}
