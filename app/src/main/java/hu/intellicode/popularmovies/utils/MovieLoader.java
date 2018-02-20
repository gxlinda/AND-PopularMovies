package hu.intellicode.popularmovies.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import hu.intellicode.popularmovies.Movie;

/**
 * Created by melinda.kostenszki on 2018.02.18.
 * In the making of this app I used materials from Udacity lessons
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    //Tag for log messages
    private static final String LOG_TAG = MovieLoader.class.getName();

    //Query URL
    private String url;

    public MovieLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (url == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of movies
        return QueryUtils.fetchMovieData(url);
    }
}