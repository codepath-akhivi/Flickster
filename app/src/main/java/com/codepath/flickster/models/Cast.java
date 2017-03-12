package com.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by akhivesara on 3/11/17.
 */

//TODO: Sort by order
public class Cast {


    private String id;
    private String character;
    private String name;
    private int order;
    private String profile_path;

    public Cast(JSONObject object) throws JSONException {
        this.id = object.getString("id");
        this.character = object.getString("character");
        this.name = object.getString("name");
        this.profile_path = object.getString("profile_path");
        this.order = object.getInt("order");
    }

    public static ArrayList<Cast> fromJSONArray(JSONArray results) {
        ArrayList<Cast> casts = new ArrayList<>();
        for (int x = 0; x < results.length(); x++) {
            try {
                casts.add(new Cast(results.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return casts;
    }
}
