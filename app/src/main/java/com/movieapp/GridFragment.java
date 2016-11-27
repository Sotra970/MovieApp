package com.movieapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class GridFragment extends Fragment {

    private View layout_resourse ;
    private ArrayList<MoviesModel> movies = new ArrayList<>() ;
    MoviesAdapter moviesAdapter ;
    GridView gridView ;
    String pref = "popular";
    OnMoviesGridClicked onMoviesGridClicked ;

    public void setOnMoviesGridClicked(OnMoviesGridClicked onMoviesGridClicked) {
        this.onMoviesGridClicked = onMoviesGridClicked;
    }

    public GridFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        layout_resourse =  inflater.inflate(R.layout.fragment_grid, container, false);
        setHasOptionsMenu(true);
            moviesAdapter = new MoviesAdapter(movies , getContext());
            gridView = (GridView) layout_resourse.findViewById(R.id.moviesGrid);
            gridView.setAdapter(moviesAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    onMoviesGridClicked.diplayDetails((MoviesModel)moviesAdapter.getItem(i));

            }
        });
            get_data();

        return layout_resourse;
    }

    private void get_data(){
        String url = "http://api.themoviedb.org/3/movie/"+pref+"?api_key=da84c4afea059e8ae06f74c450ea8793";
        JsonRequest movieRequest  = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONArray result = response.getJSONArray("results");
                  for (int i=0;i<result.length();i++){
                      MoviesModel feed  = new MoviesModel();
                    JSONObject currentFeed = result.getJSONObject(i);
                      feed.setName(currentFeed.getString("title"));
                      feed.setImg(currentFeed.getString("poster_path"));
                      feed.setDate(currentFeed.getString("release_date"));
                      feed.setDetails(currentFeed.getString("overview"));
                      feed.setCover(currentFeed.getString("backdrop_path"));
                      feed.setId(currentFeed.getString("id"));

                      movies.add(feed);
                  }
                    moviesAdapter.notifyDataSetChanged();
                    onMoviesGridClicked.onLoad( movies.get(0));

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_details,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.top_rated:
                pref = "top_rated";
                movies.clear();
                get_data();
                break;
            case  R.id.most_pop:
                pref = "popular";
                movies.clear();
                get_data();
                break;

            case  R.id.favourites:
                DatabaseHandler databaseHandler = new DatabaseHandler();
               parseData(databaseHandler.data());

                break;
        }
        return true;
    }

    private void parseData(List<DatabaseHandler> data) {
        movies.clear();
        for(int i =0 ; i<data.size() ; i++){
            MoviesModel moviesModel = new MoviesModel();
            moviesModel.setId(data.get(i).id);
            moviesModel.setDate(data.get(i).date);
            moviesModel.setDetails(data.get(i).details);
            moviesModel.setName(data.get(i).name);
            moviesModel.setCover(data.get(i).cover);
            moviesModel.setImg(data.get(i).img);
            moviesModel.setRate(data.get(i).rate);
            movies.add(moviesModel);
            Log.e("movies", moviesModel.getDate()+ "");
            Log.e("movies", data.get(i).date+ "");
        }
                 moviesAdapter.notifyDataSetChanged();
        try{ onMoviesGridClicked.onLoad(movies.get(0));}catch (Exception e){}

    }
}
