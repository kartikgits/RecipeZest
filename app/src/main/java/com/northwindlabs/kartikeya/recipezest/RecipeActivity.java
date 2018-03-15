package com.northwindlabs.kartikeya.recipezest;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Kartikeya on 2/14/2018.
 */

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //to remove shadow under the action bar as we are using Facebook's support libraries
        getSupportActionBar().setElevation(0);

        //Set the content of the activity to use activity_recipe.xml layout file
        setContentView(R.layout.activity_recipe);

        //Find the viewPager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Create an adapter that knows which fragment is to be shown on each page
        RecipeFragmentPager adapter = new RecipeFragmentPager(getSupportFragmentManager(), RecipeActivity.this);

        //set the adapter on to the viewPager
        viewPager.setAdapter(adapter);

        //Give the tabLayout to the viewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
