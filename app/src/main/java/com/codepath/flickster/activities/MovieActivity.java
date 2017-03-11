package com.codepath.flickster.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.flickster.R;
import com.codepath.flickster.adapters.MovieAdapter;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.network.MovieDBNetworkClient;
import com.codepath.flickster.network.MovieNetworkClientInterface;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {

    ListView movieListView;
    ArrayList<Movie> movieList;
    MovieAdapter movieAdapter;
    private static String LOG_TAG = "MovieActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(LOG_TAG, "onCreate : ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        createCustomActionBar();


        // view
        movieListView = (ListView)findViewById(R.id.movieListView);

        // model
        movieList = new ArrayList<>();

        // adapter
        movieAdapter = new MovieAdapter(this,movieList);

        movieListView.setAdapter(movieAdapter);

        MovieDBNetworkClient.getMovieList(new MovieNetworkClientInterface() {

            @Override
            public void onMovieClientCompleted(JSONArray results) {
                Log.d(LOG_TAG, "onMovieClientCompleted");

                movieList.addAll(Movie.fromJSONArray(results));

                // This is a weird quick - if you set it again,
                // notify does now work, vs you need to addAll
                //movieList = Movie.fromJSONArray(results);

                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onMovieClientFailed(JSONException e) {
                Log.d(LOG_TAG, "onMovieClientFailed");
                movieList = null;
            }

        });
    }

    private void createCustomActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

    }



}
