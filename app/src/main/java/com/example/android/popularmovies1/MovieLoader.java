package com.example.android.popularmovies1;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.android.popularmovies1.Utils.JSONUtils;
import com.example.android.popularmovies1.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private static final String LOG_TAG = MovieLoader.class.getName();
    List<Movie> movies;

    // Query URL
    private URL mUrl;

    /**
     * Constructs a new {@link MovieLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public MovieLoader(Context context, URL url) {
        super(context);
        mUrl = url;
    }

    /**
     * Query the api and return a {@link List<Movie>} object
     */
    private static ArrayList<Movie> fetchMovieData(URL requestUrl) {

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = NetworkUtils.getResponseFromHttpUrl(requestUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<Movie> movies = JSONUtils.getMovieDetailsFromJson(jsonResponse);

        // Return the {@link Event}
        return movies;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of movies
        List<Movie> movies = fetchMovieData(mUrl);
        return movies;
    }
}