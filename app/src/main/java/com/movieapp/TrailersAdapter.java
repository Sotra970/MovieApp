package com.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sotra on 10/21/2016.
 */
public class TrailersAdapter extends BaseAdapter {
    ArrayList<HashMap<String,String>> data = new ArrayList<>();
    LayoutInflater mlayoutInflater ;
    Context context ;
    public TrailersAdapter(ArrayList data , Context context) {
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
     View  res = mlayoutInflater.inflate(R.layout.trailer_item, viewGroup , false);
        TextView trailers_name = (TextView) res.findViewById(R.id.trailers_name);
        trailers_name.setText(data.get(i).get("name"));
    return res;
    }
}
