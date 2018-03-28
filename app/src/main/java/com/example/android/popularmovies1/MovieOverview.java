package com.example.android.popularmovies1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.popularmovies1.Utils.JSONUtils;
import com.example.android.popularmovies1.Utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieOverview extends AppCompatActivity {

    //Adapter for the list of stories
    private MovieAdapter movieAdapter;
    private List<Movie> movies = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);

        makeMovieQuery();


        // Simplification: Using a GridView instead of a RecyclerView
        GridView gridView = findViewById(R.id.movies_gridview);

        // Create a new adapter that takes an empty list of movies as input
        movieAdapter = new MovieAdapter(this, movies);
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position);
            }
        });
    }


    //method to get the preference for the sort order from the menu and build the URL to contact
    //the movie API TODO:get sort order instead of assigning it
    private void makeMovieQuery() {
        String orderBy = "popular";
        URL movieURL = NetworkUtils.buildURL(orderBy);
        new MovieQueryTask(getApplicationContext()).execute(movieURL);
    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }

    // MovieQueryTask that extends AsyncTask<URL, Void, String>
    public static class MovieQueryTask extends AsyncTask<URL, Void, String> {

        private Context mContext;

        public MovieQueryTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String movieQueryResults = null;
            try {
                movieQueryResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieQueryResults;
        }

        @Override
        protected void onPostExecute(String movieQueryResults) {
            if (movieQueryResults != null && !movieQueryResults.equals("")) {
                try {
                    List<Movie> movies = JSONUtils.getMovieDetailsFromJson(movieQueryResults);
                    MovieAdapter movieAdapter = new MovieAdapter(mContext, movies);
                    movieAdapter.setMovieData(movies);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
