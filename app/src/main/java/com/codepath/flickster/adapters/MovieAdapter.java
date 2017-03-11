package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by akhivesara on 3/10/17.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    private static class ViewHolder {
        TextView title;
        TextView overview;
        ImageView imageView;
    }

    public MovieAdapter(Context context, ArrayList<Movie> objects) {
        super(context, 0 , objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Movie movie = getItem(position);
        // View Holder Implementation
        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_list_item, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.overview = (TextView) convertView.findViewById(R.id.overview);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.poster);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }



        viewHolder.title.setText(movie.getTitle());
        viewHolder.overview.setText(movie.getOverview());

        Boolean isPortrait = getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        viewHolder.imageView.setImageResource(0);

        viewHolder.imageView.setBackgroundColor(Color.LTGRAY);

        Picasso.with(getContext()).
                load(isPortrait ? movie.getPoster_path() : movie.getBackdrop_path()).
                fit().
                //into(new ImageView(getContext()), new Callback() {
                into(viewHolder.imageView, new Callback() {

                    @Override
                    public void onSuccess() {
                        // remove placeholder

                        viewHolder.imageView.setBackgroundColor(Color.TRANSPARENT);
                    }

                    @Override
                    public void onError() {

                    }
                });

        return convertView;

        /*

        // Default handling of Custom Adapter, w/o View Holder

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_list_item,parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView overview = (TextView) convertView.findViewById(R.id.overview);

        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());

        return convertView;
        */

    }
}
