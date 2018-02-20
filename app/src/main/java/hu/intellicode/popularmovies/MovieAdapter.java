package hu.intellicode.popularmovies;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by melinda.kostenszki on 2018.02.16.
 * Used guidelines:
 * https://antonioleiva.com/recyclerview-listener/
 * https://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    public interface OnItemClickListener  {
        void onItemClick(Movie item);
    }

    private Context context;
    private List<Movie> movies;
    private final OnItemClickListener listener;

    public MovieAdapter(Context context, List<Movie> movies, OnItemClickListener listener) {
        this.context = context;
        this.movies = movies;
        this.listener = listener;
    }

    @Override
    public MovieAdapter.MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_item, viewGroup, false);

        return new MovieAdapterViewHolder(view);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        final Movie movie = movies.get(position);

        String voteString = String.valueOf(movie.getVoteAverage());
        holder.movieTitleView.setText("Rating: " + voteString + "/10");

        Picasso.with(context)
                .load(movie.getImageUriString())
                .into(holder.moviePosterView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(movie);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        if (null == movies) return 0;
        return movies.size();
    }

    // Define viewholder
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView moviePosterView;
        TextView movieTitleView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cv_item);
            moviePosterView = itemView.findViewById(R.id.iv_thumbnail);
            movieTitleView = itemView.findViewById(R.id.tv_movie_title);
        }

    }

    // Helper method to set the actual movie list into the recyclerview on the activity
    public void setMovieList(List<Movie> movieList) {
        movies = movieList;
        notifyDataSetChanged();
    }
}
