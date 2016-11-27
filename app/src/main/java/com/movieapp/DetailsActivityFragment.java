package com.movieapp;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {
    View layout_res ;
    MoviesModel extra_feed ;
    ArrayList  trailers_list = new ArrayList() , reviews_list = new ArrayList() ;
    ReviewAdapter reviewAdapter ;
    TrailersAdapter trailersAdapter ;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         extra_feed = (MoviesModel) getArguments().get("feed");
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

        final ExpandableHeightListView trailres = (ExpandableHeightListView) layout_res.findViewById(R.id.trailers);
        trailres.setExpanded(true);
        trailersAdapter = new TrailersAdapter(trailers_list,getContext());
        trailres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                       "https://www.youtube.com/watch?v=" +  ((HashMap)  trailersAdapter.getItem(i)).get("key")
                ));
                startActivity(browserIntent);
            }
        });
        trailres.setAdapter(trailersAdapter);

        ExpandableHeightListView reviews = (ExpandableHeightListView) layout_res.findViewById(R.id.reviews);
        reviews.setExpanded(true);
        reviewAdapter = new ReviewAdapter(reviews_list,getContext());
        reviews.setAdapter(reviewAdapter);

        get_trailers();
        get_reviews();
        layout_res.findViewById(R.id.fav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler databaseHandler = new DatabaseHandler();
                    if (databaseHandler.check(extra_feed.getId())){
                        databaseHandler.deletefav(extra_feed.getId());
                        Toast.makeText(getContext(),"unsaved", Toast.LENGTH_LONG).show();
                    }else {

                        databaseHandler.id = extra_feed.getId();
                        databaseHandler.name = extra_feed.getName();
                        databaseHandler.img = extra_feed.getImg();
                        databaseHandler.rate = extra_feed.getRate();
                        databaseHandler.date = extra_feed.getDate();
                        databaseHandler.details = extra_feed.getDetails();
                        databaseHandler.cover = extra_feed.getCover();
                        databaseHandler.save();
                        Toast.makeText(getContext(),"saved", Toast.LENGTH_LONG).show();
                    }

            }
        });
        return  layout_res;
    }
    private void get_trailers(){
        String url = "http://api.themoviedb.org/3/movie/"+extra_feed.getId()+"/trailers"+"?api_key=da84c4afea059e8ae06f74c450ea8793";
        JsonRequest movieRequest  = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONArray result = response.getJSONArray("youtube");
                    for (int i=0;i<result.length();i++){
                        HashMap feed = new HashMap( );
                        JSONObject currentFeed = result.getJSONObject(i);
                        feed.put("name",currentFeed.getString("name"));
                        feed.put("key",currentFeed.getString("source"));


                        trailers_list.add(feed);
                    }
                    trailersAdapter.notifyDataSetChanged();


                }catch (Exception e){
                    Log.e("response error ",e.toString());
                }
                Log.e("response",response.toString());
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                    }
                }

        );
        SingleTone.getInstance().addToRequestQueue(movieRequest);
    }
    private void get_reviews(){
        String url = "http://api.themoviedb.org/3/movie/"+extra_feed.getId()+"/reviews"+"?api_key=da84c4afea059e8ae06f74c450ea8793";
        JsonRequest movieRequest  = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONArray result = response.getJSONArray("results");
                    for (int i=0;i<result.length();i++){
                        HashMap feed = new HashMap( );
                        JSONObject currentFeed = result.getJSONObject(i);
                        feed.put("author",currentFeed.getString("author"));
                        feed.put("content",currentFeed.getString("content"));

                        reviews_list.add(feed);
                    }
                    reviewAdapter.notifyDataSetChanged();


                }catch (Exception e){
                    Log.e("response error ",e.toString());
                }
                Log.e("response",response.toString());
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                    }
                }

        );
        SingleTone.getInstance().addToRequestQueue(movieRequest);
    }
}
