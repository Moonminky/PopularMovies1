package com.example.android.popularmovies1.Utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Utilities to communicate with the API from themoviedb.org
 */

public class NetworkUtils {
    final static String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
    final static String IMAGE_BASE_URL = " http://image.tmdb.org/t/p/";
    final static String IMAGE_SIZE = "w185";
    final static String SORT_BY_POPULARITY = "popular/";
    final static String SORT_BY_RATING = "top_rated/";
    //API key in the format: final static String API_KEY="[YOUR API KEY HERE]";
    
    final static String API_KEY_PARAM = "api_key";
    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static URL buildURL(String sortedBy) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(sortedBy)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);

        return url;
    }
    public static URL imageURL(String image) {
        Uri builtUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendEncodedPath(IMAGE_SIZE)
                .appendPath(image)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * Method to return the result from the HTTP response
     *
     * @param url URL to fetch HTTP response from
     * @return Contents of  HTTP response
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
