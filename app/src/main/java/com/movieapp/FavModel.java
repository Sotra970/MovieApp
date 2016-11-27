package com.movieapp;

import java.util.ArrayList;

/**
 * Created by sotra on 11/25/2016.
 */
public class FavModel {
    MoviesModel moviesModel ;
    ArrayList trailers , reviews ;

    public MoviesModel getMoviesModel() {
        return moviesModel;
    }

    public void setMoviesModel(MoviesModel moviesModel) {
        this.moviesModel = moviesModel;
    }

    public ArrayList getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList trailers) {
        this.trailers = trailers;
    }

    public ArrayList getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList reviews) {
        this.reviews = reviews;
    }
}
