package com.example.android.popularmovies1.Utils;

import android.util.Log;

import com.example.android.popularmovies1.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class JSONUtils {

    private static final String MOVIE_LIST = "results";
    private static final String MOVIE_TITLE = "original_title";
    private static final String MOVIE_POSTER = "poster_path";
    private static final String MOVIE_PLOT = "overview";
    private static final String MOVIE_VOTE_AVERAGE = "vote_average";
    private static final String MOVIE_RELEASE_DATE = "release_date";

    public static List<Movie> getMovieDetailsFromJson(String movieJsonString)
            throws JSONException {


        List<Movie> moviesList = new ArrayList<>();
        try {
            JSONObject baseMoviesJson = new JSONObject(movieJsonString);
            JSONArray moviesJsonArray = baseMoviesJson.getJSONArray(MOVIE_LIST);

            for (int i = 0; i < moviesJsonArray.length(); i++) {
                JSONObject currentMovie = moviesJsonArray.getJSONObject(i);
                String movieTitle = currentMovie.getString(MOVIE_TITLE);
                Double voteAverage = currentMovie.getDouble(MOVIE_VOTE_AVERAGE);
                String moviePlot = currentMovie.getString(MOVIE_PLOT);
                String movieReleaseDate = currentMovie.getString(MOVIE_RELEASE_DATE);
                String moviePoster = currentMovie.getString(MOVIE_POSTER);

                Movie movie = new Movie(movieTitle,
                        movieReleaseDate, moviePoster, voteAverage, moviePlot);

                moviesList.add(movie);

                if (baseMoviesJson.has(movieTitle)) {
                    int errorCode = baseMoviesJson.getInt(movieTitle);

                    switch (errorCode) {
                        case HttpURLConnection.HTTP_OK:
                            break;
                        case HttpURLConnection.HTTP_NOT_FOUND:
                            return null;
                        default:
                            return null;
                    }

                }
            }
        } catch (JSONException e) {
            Log.e("MoviesUtils", "Problem parsing movies JSON results", e);
        }
        return moviesList;
    }
}

