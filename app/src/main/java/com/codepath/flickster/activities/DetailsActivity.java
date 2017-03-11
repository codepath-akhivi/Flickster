package com.codepath.flickster.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;

/**
 * Created by akhivesara on 3/11/17.
 */

public class DetailsActivity extends AppCompatActivity {

    private Movie movie;
    private static String LOG_TAG = "MovieDetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);

        movie = (Movie)getIntent().getParcelableExtra("myData");

        if (movie == null) {
            Log.e(LOG_TAG, "Details cannot load, as movie is null");
        }

        setContentView(R.layout.movie_details);
        createCustomActionBar();

        TextView overview = (TextView)findViewById(R.id.overview);
        overview.setText(movie.getOverview());


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
