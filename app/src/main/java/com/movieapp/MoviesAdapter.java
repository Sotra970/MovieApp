package com.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sotra on 10/21/2016.
 */
public class MoviesAdapter extends BaseAdapter {
    ArrayList<MoviesModel> data = new ArrayList<>();
    LayoutInflater mlayoutInflater ;
    Context context ;
    public MoviesAdapter(ArrayList<MoviesModel> data , Context context) {
        this.data = data;
        this.context = context ;
        mlayoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {

        return  data.get(i)  ;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
     View  res = mlayoutInflater.inflate(R.layout.movies_item, viewGroup , false);
       ImageView poster = (ImageView) res.findViewById(R.id.poster);
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185"+data.get(i).getImg())
                .fit()
                .into(poster);
        return res;
    }
}
