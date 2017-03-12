package com.codepath.flickster.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by akhivesara on 3/10/17.
 */

public class Movie implements Parcelable {

    // playing now
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


    //details
    String tagline;
    String homepage;
    String status;

    String genres;
    // Getters

    public String getHomepage() {
        return homepage;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getPoster_path() {
        String size = "w342";

        String url = String.format(this.base_path + "%s%s", size, poster_path);

        Log.d("Movie", String.format(" %s : poster : %s", this.getTitle(), url));
        return url;
    }

    public Boolean getAdult() {
        return adult;
    }

    public String getGenres() {
        return genres;
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
        String size = "w780";

        String url = String.format(this.base_path + "%s%s", size, backdrop_path);

        Log.d("Movie", String.format(" %s : backdrop : %s", this.getTitle(), url));
        return url;
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
     *
     * @param result
     * @throws JSONException
     */
    public Movie(JSONObject result) throws JSONException {

        //TODO get the size from configuration !
        // https://api.themoviedb.org/3/configuration?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed

        this.base_path = "https://image.tmdb.org/t/p/";

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
     * @param results
     * @return
     */
    public static ArrayList<Movie> fromJSONArray(JSONArray results) {
        ArrayList<Movie> movies = new ArrayList<>();
        for (int x = 0; x < results.length(); x++) {
            try {
                movies.add(new Movie(results.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }

    public Movie updateObject(JSONObject additionalData) {
        if (additionalData != null) {
            try {
                this.tagline = additionalData.getString("tagline");
                this.homepage = additionalData.getString("homepage");
                this.status = additionalData.getString("status");

                ArrayList<String> g = StringsfromJSONArray(additionalData.getJSONArray("genres"));
                int limit = g.size();
                if (limit > 2) {
                    limit =2;
                }
                this.genres = TextUtils.join(", ",g.subList(0,limit));

                //additionalData.getJSONArray()
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this;
    }


    /**
     * Use when reconstructing User object from parcel
     * This will be used only by the 'CREATOR'
     *
     * @param in a parcel to read this object
     */
    public Movie(Parcel in) {
        this.base_path = in.readString();
        this.poster_path = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.original_title = in.readString();
        this.original_language = in.readString();
        this.title = in.readString();
        this.backdrop_path = in.readString();
        this.id = in.readInt();
        this.vote_count = in.readInt();
        this.vote_average = in.readInt();
        this.popularity = in.readLong();

        this.adult = in.readByte() == 1;
        this.video = in.readByte() == 1;

        this.tagline = in.readString();
        this.homepage = in.readString();
        this.status = in.readString();

//        String[] s = new String[]{};
//        in.readStringArray(s);
//
//        in.createTypedArray()


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

//        JSONArray genre_ids;


        dest.writeString(this.base_path);
        dest.writeString(this.poster_path);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.original_title);
        dest.writeString(this.original_language);
        dest.writeString(this.title);
        dest.writeString(this.backdrop_path);
        dest.writeInt(this.id);
        dest.writeInt(this.vote_count);
        dest.writeInt(this.vote_average);
        dest.writeLong(this.popularity);
        dest.writeByte((byte) (this.adult ? 1 : 0));
        dest.writeByte((byte) (this.video ? 1 : 0));
        dest.writeString(this.tagline);
        dest.writeString(this.homepage);
        dest.writeString(this.status);

//        JSONArray jArray = (JSONArray)this.genre_ids;
//        Object[] listdata = new Object[jArray.length()];
//
//        if (jArray != null) {
//            for (int i=0;i<jArray.length();i++){
//                try {
//                    listdata[i] = (jArray.getString(i));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            dest.writeArray(listdata);
//        }

    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private ArrayList<String> StringsfromJSONArray(JSONArray results) {
        ArrayList<String> items = new ArrayList<>();
        for (int x = 0; x < results.length(); x++) {
            try {
                items.add(results.getJSONObject(x).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return items;
    }


}
