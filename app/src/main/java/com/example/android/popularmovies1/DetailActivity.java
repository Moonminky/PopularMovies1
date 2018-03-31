package com.example.android.popularmovies1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies1.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "intent_extra_movie";
    private static final Movie DEFAULT_MOVIE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView moviePoster = findViewById(R.id.moviePoster_content);
        TextView titleTV = findViewById(R.id.title_content);
        TextView releasedateTV = findViewById(R.id.releaseDate_content);
        TextView voteAverageTV = findViewById(R.id.voteAverage_content);
        TextView plotTV = findViewById(R.id.plot_content);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        Movie movie = (Movie) intent.getSerializableExtra(EXTRA_MOVIE);
        if (movie == DEFAULT_MOVIE) {
            // EXTRA_MOVIE not found in intent
            closeOnError();
            return;
        }

        populateUI(movie, moviePoster, titleTV, releasedateTV, voteAverageTV, plotTV);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    private void populateUI(Movie movie, ImageView moviePoster, TextView titleTV, TextView releasedateTV,
                            TextView voteAverageTV, TextView plotTV) {

        // Get the image from the current Movie object, build the URL and
        // set the image to imageView
        String posterString = movie.getMoviePoster();
        URL imageUrl = NetworkUtils.imageURL(posterString);
        String image = imageUrl.toString();
        Picasso.with(getApplicationContext()).load(image).into(moviePoster);

        if (movie.getTitle().equals("")) {
            titleTV.setText(R.string.noValue);
        } else {
            titleTV.setText(movie.getTitle());
        }

        if (movie.getReleaseDate().equals("")) {
            releasedateTV.setText(R.string.noValue);
        } else {
            releasedateTV.setText(movie.getReleaseDate());
        }

        if (movie.getVoteAverage().isNaN()) {
            voteAverageTV.setText(R.string.noValue);
        } else {
            voteAverageTV.setText(movie.getVoteAverage().toString());
        }

        if (movie.getOverview().equals("")) {
            plotTV.setText(R.string.noValue);
        } else {
            plotTV.setText(movie.getOverview());
        }

    }
}
