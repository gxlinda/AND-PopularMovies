package hu.intellicode.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.intellicode.popularmovies.utils.MovieLoader;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Movie>> {

    private String MOVIE_DB_REQUEST_URL;
    private static final int ID_MOVIE_LOADER = 13;
    private List<Movie> movies = new ArrayList<>();
    private List<Movie> savedMovieList = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyStateTextView;
    private String ORDER_BUY_POPULAR = "popular";
    private String ORDER_BUY_TOP_RATED = "top_rated";
    private String orderBy = ORDER_BUY_POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MOVIE_DB_REQUEST_URL = "https://api.themoviedb.org/3/movie";

        recyclerView = findViewById(R.id.rv_movies);
        progressBar = findViewById(R.id.progress_bar);
        emptyStateTextView = findViewById(R.id.tv_empty_state);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        final Context context = getApplicationContext();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        //listener for onClick
        // //TODO WHY NOT WORKING??
        MovieAdapter.OnItemClickListener listener = new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                Toast.makeText(context, "Item Clicked", Toast.LENGTH_LONG).show();
            }
        };

        movieAdapter = new MovieAdapter(movies, listener);//clickListener
        recyclerView.setAdapter(movieAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(ID_MOVIE_LOADER, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {

        //builds the URI according to the query parameters below
        Uri baseUri = Uri.parse(MOVIE_DB_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath(orderBy);
        uriBuilder.appendQueryParameter("api_key", "enter_your_API_key_here");

        return new MovieLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        //hides the progress bar circle
        progressBar.setVisibility(View.GONE);

        // Clear the adapter of previous movie data
        movieAdapter.setMovieList(null);

        // If there is a valid list of movies, then add them to the adapter's data set.
        if (movies != null && !movies.isEmpty()) {
            emptyStateTextView.setVisibility(View.GONE);
            movieAdapter.setMovieList(movies);
            movieAdapter.notifyDataSetChanged();
            savedMovieList = new ArrayList<>(movies);

        } else {
            // Set empty state text to display "No movies found."
            emptyStateTextView.setText(R.string.no_movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        movieAdapter.setMovieList(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies, menu);
        //Return true so that the menu is displayed in the Toolbar
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //Checks the setting choosen by the user, and refreshes layout if necessary

        if (id == R.id.most_popular) {
            if (!orderBy.equals(ORDER_BUY_POPULAR)) {
                orderBy = ORDER_BUY_POPULAR;
                getLoaderManager().restartLoader(ID_MOVIE_LOADER, null, this);
                return true;
            }
        }
        if (id == R.id.highest_rated) {
            if (!orderBy.equals(ORDER_BUY_TOP_RATED)) {
                orderBy = ORDER_BUY_TOP_RATED;
                getLoaderManager().restartLoader(ID_MOVIE_LOADER, null, this);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
