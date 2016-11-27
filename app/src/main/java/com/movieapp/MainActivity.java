package com.movieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity  implements OnMoviesGridClicked {
        boolean isTowPanel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridFragment gridFragment = new GridFragment();
        gridFragment.setOnMoviesGridClicked(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.grid_fragment_container,gridFragment).commit();
        if (findViewById(R.id.details_container)==null){
            isTowPanel = false ;

        }else {
            isTowPanel = true ;
        }
    }

    @Override
    public void diplayDetails(MoviesModel moviesModel) {
        if (isTowPanel){

            detailsFrag(moviesModel);

        }else {

            startActivity(new Intent(getApplicationContext(),DetailsActivity.class).putExtra("feed",moviesModel));
        }
    }

    @Override
    public void onLoad(MoviesModel moviesModel) {
        if (isTowPanel){

            detailsFrag(moviesModel);

        }

    }
    void detailsFrag(MoviesModel moviesModel){
        DetailsActivityFragment detailsActivityFragment = new DetailsActivityFragment();
        Bundle extra = new Bundle();
        extra.putSerializable("feed",moviesModel);
        detailsActivityFragment.setArguments(extra) ;
        getSupportFragmentManager().beginTransaction().replace(R.id.details_container,detailsActivityFragment).commit();
    }
}
