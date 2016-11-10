package com.movieapp;

import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {
    View layout_res ;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MoviesModel extra_feed = (MoviesModel) getArguments().get("feed");
        layout_res = inflater.inflate(R.layout.fragment_details, container, false);
        TextView title = (TextView) layout_res.findViewById(R.id.title);
        TextView date = (TextView) layout_res.findViewById(R.id.Date);
        TextView details = (TextView)   layout_res.findViewById(R.id.Details);
        ImageView cover = (ImageView)   layout_res.findViewById(R.id.poster_details);

        title.setText(extra_feed.getName());
        date.setText(extra_feed.getDate());
        details.setText(extra_feed.getDetails());
        Picasso.with(getContext())
                .load("http://image.tmdb.org/t/p/w185"+extra_feed.getCover())
                .fit()
                .into(cover);

        return  layout_res;
    }
}
