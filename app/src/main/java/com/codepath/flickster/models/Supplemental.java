package com.codepath.flickster.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by akhivesara on 3/11/17.
 */

public class Supplemental implements Parcelable {

    private String id;
    private String name;
    private String key;
    private String type;
    private String site;
    private int size;

    public Supplemental(JSONObject data) throws JSONException {
        this.id = data.getString("id");
        this.name = data.getString("name");
        this.key = data.getString("key");
        this.site = data.getString("site");
        this.type = data.getString("type");
        this.size = data.getInt("size");
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public Supplemental(Parcel source) {
        this.id = source.readString();
        this.key = source.readString();
        this.name = source.readString();
        this.type = source.readString();
        this.site = source.readString();
        this.size = source.readInt();
    }

    public static ArrayList<Supplemental> fromJSONArray(JSONArray results) {
        ArrayList<Supplemental> videos = new ArrayList<>();
        for (int x = 0; x < results.length(); x++) {
            try {
                videos.add(new Supplemental(results.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return videos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.site);
        dest.writeInt(this.size);
    }

    public static final Parcelable.Creator<Supplemental> CREATOR = new Parcelable.Creator<Supplemental>() {

        @Override
        public Supplemental createFromParcel(Parcel source) {
            return new Supplemental(source);
        }

        @Override
        public Supplemental[] newArray(int size) {
            return new Supplemental[size];
        }
    };
}
