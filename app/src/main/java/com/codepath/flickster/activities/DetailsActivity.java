package com.codepath.flickster.activities;

import android.app.ActionBar;
import android.content.Intent;
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
    private Bundle bundle;

    private static String LOG_TAG = "MovieDetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);

        bundle = getIntent().getExtras();

        movie = bundle.getParcelable("myData");
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

        renderSecondaryViews();

        MovieDBNetworkClient.getMovieDetails(movie, new MovieNetworkClientInterface() {

            @Override
            public void onMovieClientCompleted(JSONArray results) {

            }

            @Override
            public void onMovieClientCompleted(JSONObject results) {
                //Do nothing

                movie.updateObject(results);



                try {
                    JSONObject videosObject = results.getJSONObject("videos");
                    JSONArray videos = videosObject.getJSONArray("results");
                    supplementals = Supplemental.fromJSONArray(videos);

                    JSONObject castsObject = results.getJSONObject("credits");
                    JSONArray cast = castsObject.getJSONArray("cast");
                    casts = Cast.fromJSONArray(cast);




                } catch (JSONException e) {
                    e.printStackTrace();
                }

                renderSecondaryViews();

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

    private void renderSecondaryViews() {

        TextView tagline = (TextView)findViewById(R.id.tagline);

        if (movie.getTagline() != null) {
            tagline.setText(movie.getTagline());
        }
        if (movie.getGenres() != null) {
            ((TextView) findViewById(R.id.genre)).setText(movie.getGenres());
        }

        String castString = "";

        if (casts != null) {
            for (int x = 0; x < (casts.size() <= 2 ? casts.size() : 2); x++) {
                String delimiter = x == 0 ? "" : ", ";
                castString += delimiter + casts.get(x).getName();
            }
        }
        ((TextView)findViewById(R.id.casts)).setText(castString);

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();

        bundle.putParcelable("myData", movie);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);

        this.finish();
        //super.onBackPressed();
    }
}
