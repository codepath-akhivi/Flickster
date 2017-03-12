package com.codepath.flickster.network;

import android.util.Log;

import com.codepath.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by akhivesara on 3/10/17.
 */


public class MovieDBNetworkClient {

    private static String BASE_URL = "https://api.themoviedb.org/3/";

    private static String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private static String APPEND_KEY = "&append_to_response=";

    private static String APPEND_DELIMITER = ",";

    private static String LOG_TAG = "MovieDBNetworkClient";

    private static List<String> ALLOWED_APPENDS = Arrays.asList("credits", "reviews", "release_dates", "keywords", "similar" , "videos");

    private MovieNetworkClientInterface listener;

//    public MovieDBNetworkClient(MovieNetworkClientInterface listener) {
//
//        this.listener = listener;
//    }

    public static void getPlayingNowMovieList(final MovieNetworkClientInterface listener) {

        String url = BASE_URL + "movie/now_playing?api_key=" + API_KEY;

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.i(LOG_TAG, "onFailure");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieResults = null;
                Log.d(LOG_TAG, "onSuccess");
                try {
                    movieResults = response.getJSONArray("results");
                    Log.d(LOG_TAG, "movieResults : " + movieResults);
                    Log.d(LOG_TAG, "movieResults count : " + movieResults.length());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onMovieClientCompleted(movieResults);
                super.onSuccess(statusCode, headers, response);
            }
        });
    }

    public static void getMovieDetails(Movie movie, final MovieNetworkClientInterface listener , List optionals) {

        //https://api.themoviedb.org/3/movie/263115?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed

        String url = BASE_URL + "movie/"+ movie.getId() +"?api_key=" + API_KEY;
        //Boolean toAppend = false;
        final List toAppend = new ArrayList();
        if (optionals != null) {
            for (int x=0; x < optionals.size() ; x++) {
                if (ALLOWED_APPENDS.indexOf(optionals.get(x)) !=-1) {
                    //toAppend = true;
                    toAppend.add(optionals.get(x));
                }
            }
        }

        if (toAppend.size() > 0) {
            String appendString = android.text.TextUtils.join(APPEND_DELIMITER, toAppend);
            url = url +  APPEND_KEY + appendString;

        }
        Log.d(LOG_TAG, "MovieDetails : url "+url);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d(LOG_TAG, "getMovieDetails: onSuccess");
                Log.d(LOG_TAG, "movieResults : " + response);

                listener.onMovieClientCompleted(response);
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }



}
