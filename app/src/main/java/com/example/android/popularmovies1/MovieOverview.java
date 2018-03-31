package com.example.android.popularmovies1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies1.Utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieOverview extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    //Constant value for the movie loader ID.
    private static final int MOVIE_LOADER_ID = 1;
    private LoaderManager loaderManager;
    private TextView emptyView;
    private ProgressBar progressBar;
    //Adapter for the list of movies
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        progressBar = findViewById(R.id.progress);
        GridView gridView = findViewById(R.id.movies_gridview);
        //set the empty TextView as EmptyView on the ListView
        emptyView = findViewById(R.id.empty_view);
        gridView.setEmptyView(findViewById(R.id.empty_view));

        // Create a new adapter that takes an empty list of movies as input
        movieAdapter = new MovieAdapter(this, new ArrayList<Movie>());
        gridView.setAdapter(movieAdapter);

        if (isConnected) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(MOVIE_LOADER_ID, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet);
            Log.d("Connection", "notConnected");

        }
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        URL requestMoviesUrl = NetworkUtils.buildURL(orderBy);

        return new MovieLoader(this, requestMoviesUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        emptyView.setText(R.string.no_movies);
        progressBar.setVisibility(View.GONE);

        // Clear the adapter of previous movie data
        movieAdapter.clear();
        // If there is a valid list of {@link Movie}s, then add them to the adapter's
        // data set. This will trigger the GridView to update.
        if (movies != null && !movies.isEmpty()) {
            movieAdapter.addAll(movies);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        // Loader reset, so we can clear out our existing data.
        movieAdapter.clear();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
