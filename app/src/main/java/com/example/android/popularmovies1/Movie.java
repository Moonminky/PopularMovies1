package com.example.android.popularmovies1;

import java.io.Serializable;

// Movie class with title, release date, movie poster, vote average, and plot synopsis

public class Movie implements Serializable {

    private String title;
    private String releaseDate;
    private String moviePoster;
    private Double voteAverage;
    private String overview;

    public Movie(String title, String releaseDate, String moviePoster, Double voteAverage, String overview) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.moviePoster = moviePoster;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    //getter and setter methods for the Movie class
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
