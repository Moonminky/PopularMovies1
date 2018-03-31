package com.example.android.popularmovies1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.android.popularmovies1.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

/**
 * Adapter for the movies
 *
 * Referenced https://github.com/karenclaire/PopMovieSearchStage1/blob/master/app/src/main/java/com/example/android/popmoviesearchstage1/MovieAdapter.java
 * for onClickListener
 */

class MovieAdapter extends ArrayAdapter<Movie> implements Serializable {

    private Context mContext;
    private ArrayList<Movie> mMovieData;

    public MovieAdapter(Activity context, ArrayList<Movie> moviesList) {

        super(context, 0, moviesList);
        this.mContext = context;
        this.mMovieData = moviesList;
        Log.d("MovieAdapterTag", "MovieAdapter: " + moviesList);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_list_item, parent, false);
        }

        // Get the {@link Movie} object located at this position in the list
        final Movie currentMovie = mMovieData.get(position);

        // Find the ImageView in the movie_list_item.xml layout with the ID listItem_movieImage
        ImageView imageView = listItemView.findViewById(R.id.listItem_movieImage);

        // Get the image from the current Movie object, build the URL and
        // set the image to imageView
        String posterString = currentMovie.getMoviePoster();
        URL imageUrl = NetworkUtils.imageURL(posterString);
        String image = imageUrl.toString();
        Picasso.with(mContext).load(image).into(imageView);

        //set OnItemClickListener to listItemView, which sends an intent to
        //open DetailActivity with more information on the clicked movie
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie movieInformation = new Movie(currentMovie.getTitle(),
                        currentMovie.getReleaseDate(), currentMovie.getMoviePoster(),
                        currentMovie.getVoteAverage(), currentMovie.getOverview());

                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, movieInformation);
                getContext().startActivity(intent);

            }
        });

        return listItemView;
    }
}
