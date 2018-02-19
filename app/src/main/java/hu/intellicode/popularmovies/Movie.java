package hu.intellicode.popularmovies;

import android.net.Uri;

/**
 * Created by melinda.kostenszki on 2018.02.16.
 */

public class Movie {

    private int id;
    private String originalTitle;
    private String title;
    private String posterPath;
    private String MOVIE_IMAGE_URL = "http://image.tmdb.org/t/p/original/";
    private String releaseDate;
    private double voteAverage;
    private String overview;



    public Movie( int id, String originalTitle, String title, String posterPath, String releaseDate, double voteAverage, String overview) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getImageUriString() {
        Uri baseUri = Uri.parse(MOVIE_IMAGE_URL);
        Uri.Builder imageUri = baseUri.buildUpon();
        imageUri.appendEncodedPath(posterPath);
        String imageUriString = imageUri.toString();
        return imageUriString;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
