package com.codepath.flickster.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codepath.flickster.models.Movie;

/**
 * Created by akhivesara on 3/11/17.
 */

public class DetailsActivity extends AppCompatActivity {

    private static String LOG_TAG = "MovieDetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);

        ///Bundle bundle = getIntent().getExtras();


        //Movie m = bundle.getParcelable(String.valueOf(bundle.getInt("position")));

        Movie m2  = (Movie)getIntent().getParcelableExtra("myData");

        Log.i(LOG_TAG,"Movie = "+m2);
    }


}
