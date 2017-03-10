package com.codepath.flickster.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.network.MovieDBNetworkClient;
import com.codepath.flickster.network.MovieNetworkClientInterface;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {

    ArrayList<Movie> movieList;
    private static String LOG_TAG = "MovieActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(LOG_TAG, "onCreate : ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        MovieDBNetworkClient.getMovieList(new MovieNetworkClientInterface() {

            @Override
            public void onMovieClientCompleted(JSONArray results) {
                Log.d(LOG_TAG, "onMovieClientCompleted");
                movieList = Movie.fromJSONArray(results);
            }

            @Override
            public void onMovieClientFailed(JSONException e) {
                Log.d(LOG_TAG, "onMovieClientFailed");
                movieList = null;
            }

        });
    }

}
