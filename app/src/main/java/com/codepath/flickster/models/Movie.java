package com.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by akhivesara on 3/10/17.
 */

public class Movie {

    String base_path;
    String poster_path;
    Boolean adult;
    String overview;
    String release_date;
    JSONArray genre_ids;
    int id;
    String original_title;
    String original_language;
    String title;
    String backdrop_path;
    Long popularity;
    int vote_count;
    Boolean video;
    int vote_average;

    // Getters

    public String getPoster_path() {

        return String.format(this.base_path+"%s",poster_path);
    }

    public Boolean getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public JSONArray getGenre_ids() {
        return genre_ids;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public Long getPopularity() {
        return popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public Boolean getVideo() {
        return video;
    }

    public int getVote_average() {
        return vote_average;
    }


    /**
     * Constructor
     * @param result
     * @throws JSONException
     */
    public Movie(JSONObject result) throws JSONException {

        //TODO get the size from configuration !
        // https://api.themoviedb.org/3/configuration?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed

        this.base_path = "https://image.tmdb.org/t/p/w342/";

        this.poster_path = result.getString("poster_path");
        this.adult = result.getBoolean("adult");
        this.overview = result.getString("overview");
        this.release_date = result.getString("release_date");
        this.genre_ids = result.getJSONArray("genre_ids");
        this.id = result.getInt("id");
        this.original_title = result.getString("original_title");
        this.original_language = result.getString("original_language");
        this.title = result.getString("title");
        this.backdrop_path = result.getString("backdrop_path");
        this.popularity = result.getLong("popularity");
        this.vote_count = result.getInt("vote_count");
        this.video = result.getBoolean("video");
        this.vote_average = result.getInt("vote_average");
    }

    /**
     *
     * @param results
     * @return
     */
    public static ArrayList<Movie> fromJSONArray(JSONArray results) {
        ArrayList<Movie> movies = new ArrayList<>();
        for (int x = 0 ; x< results.length() ; x++) {
            try {
                movies.add(new Movie(results.getJSONObject(x)));
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  movies;
    }
}
