package hu.intellicode.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    TextView originalTitleView;
    TextView releaseDateView;
    ImageView backdropView;
    TextView voteAverageView;
    TextView overviewView;

    static final String ORIGINAL_TITLE = "original_title";
    static final String RELEASE_DATE = "release_date";
    static final String BACKDROP_URL = "backdrop_string";
    static final String VOTE = "vote_average";
    static final String OVERVIEW = "overview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        originalTitleView = findViewById(R.id.tv_original_title);
        releaseDateView = findViewById(R.id.tv_release_date);
        backdropView = findViewById(R.id.iv_detail_poster);
        voteAverageView = findViewById(R.id.tv_vote_average);
        overviewView = findViewById(R.id.tv_overview);

        Intent receivedIntent = getIntent();
        if (receivedIntent.hasExtra(ORIGINAL_TITLE)){
            String originalTitle = receivedIntent.getStringExtra(ORIGINAL_TITLE);
            originalTitleView.setText(originalTitle);
            setTitle(originalTitle);
        }
        if (receivedIntent.hasExtra(RELEASE_DATE)){
            String releaseDate = receivedIntent.getStringExtra(RELEASE_DATE);
            releaseDateView.setText(releaseDate);
        }
        if (receivedIntent.hasExtra(BACKDROP_URL)){
            String posterURL = receivedIntent.getStringExtra(BACKDROP_URL);
            Picasso.with(this)
                    .load(posterURL)
                    .into(backdropView);
        }
        if (receivedIntent.hasExtra(VOTE)){
            Double voteAverage = receivedIntent.getDoubleExtra(VOTE, 0);
            voteAverageView.setText(voteAverage.toString());
        }
        if (receivedIntent.hasExtra(OVERVIEW)){
            String overview = receivedIntent.getStringExtra(OVERVIEW);
            overviewView.setText(overview);
        }
    }
}
