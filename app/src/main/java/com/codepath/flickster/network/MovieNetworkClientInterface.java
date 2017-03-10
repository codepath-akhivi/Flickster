package com.codepath.flickster.network;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by akhivesara on 3/10/17.
 */

public interface MovieNetworkClientInterface {

    void onMovieClientCompleted(JSONArray results);
    void onMovieClientFailed(JSONException e);
}
