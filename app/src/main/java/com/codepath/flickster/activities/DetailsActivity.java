package com.codepath.flickster.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Cast;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.models.Supplemental;
import com.codepath.flickster.network.MovieDBNetworkClient;
import com.codepath.flickster.network.MovieNetworkClientInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by akhivesara on 3/11/17.
 */

public class DetailsActivity extends AppCompatActivity {

    private Movie movie;
    private ArrayList<Supplemental> supplementals;
    private ArrayList<Cast> casts;

    private static String LOG_TAG = "MovieDetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);

        movie = getIntent().getParcelableExtra("myData");

        if (movie == null) {
            Log.e(LOG_TAG, "Details cannot load, as movie is null");
        }

        setContentView(R.layout.movie_details);
        createCustomActionBar();

        TextView vote = (TextView)findViewById(R.id.vote);
        vote.setText(String.valueOf(movie.getVote_average()));

        TextView overview = (TextView)findViewById(R.id.overview);
        overview.setText(movie.getOverview());

        String date = movie.getRelease_date().split("-")[0];

        ((TextView)findViewById(R.id.year)).setText(date);

        Picasso.with(getApplicationContext())
                .load(movie.getBackdrop_path())
                .into((ImageView)findViewById(R.id.imageView));


        MovieDBNetworkClient.getMovieDetails(movie, new MovieNetworkClientInterface() {

            @Override
            public void onMovieClientCompleted(JSONArray results) {

            }

            @Override
            public void onMovieClientCompleted(JSONObject results) {
                //Do nothing
                movie.updateObject(results);

                TextView tagline = (TextView)findViewById(R.id.tagline);
                tagline.setText(movie.getTagline());


                try {
                    JSONObject videosObject = results.getJSONObject("videos");
                    JSONArray videos = videosObject.getJSONArray("results");
                    supplementals = Supplemental.fromJSONArray(videos);

                    JSONObject castsObject = results.getJSONObject("credits");
                    JSONArray cast = videosObject.getJSONArray("cast");
                    casts = Cast.fromJSONArray(cast);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onMovieClientFailed(JSONException e) {
                //Do nothing
            }

        } , Arrays.asList("credits", "videos" , "reviews"));

        Log.i(LOG_TAG,"Movie = "+movie);
    }

    private void createCustomActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        ((TextView)findViewById(R.id.actionBarText)).setText(movie.getTitle());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
