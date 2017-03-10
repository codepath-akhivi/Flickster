package com.codepath.flickster.network;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by akhivesara on 3/10/17.
 */

public class MovieDBNetworkClient {

    private static String BASE_URL = "https://api.themoviedb.org/";

    private static String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private static String LOG_TAG = "MovieDBNetworkClient";

    private MovieNetworkClientInterface listener;

//    public MovieDBNetworkClient(MovieNetworkClientInterface listener) {
//
//        this.listener = listener;
//    }

    public static void getMovieList(final MovieNetworkClientInterface listener) {

        String url = BASE_URL + "3/movie/now_playing?api_key=" + API_KEY;

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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onMovieClientCompleted(movieResults);
                super.onSuccess(statusCode, headers, response);
            }
        });
    }


}
