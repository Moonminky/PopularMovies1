package com.example.android.popularmovies1;

import android.content.Context;
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

import java.net.URL;
import java.util.List;

/**
 * Adapter for the movies
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    private List<Movie> mMovieData;

    public MovieAdapter(Context context, List<Movie> moviesList){
        super (context,0, moviesList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Context context = getContext();
        Movie movie = getItem(position);

        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_list_item, parent, false);
        }

        ImageView imageView = listItemView.findViewById(R.id.listItem_movieImage);
        assert movie != null;
        URL imageUrl = NetworkUtils.imageURL(movie.getMoviePoster());
        Log.v("imageURL", "Built URI " + imageUrl);
        String image = imageUrl.toString();
        Picasso.with(context).load(image).into(imageView);

        return listItemView;
    }

    /**
     * This method is used to set the movie data on a MovieAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new MovieAdapter to display it.
     *
     * @param movieData The new movie data to be displayed.
     */
    public void setMovieData(List<Movie> movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
