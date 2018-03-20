package com.northwindlabs.kartikeya.recipezest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by Kartikeya on 2/14/2018.
 */

public class RecipeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Set the content of the activity to use activity_recipe.xml layout file
        setContentView(R.layout.activity_recipe);

        //Set the toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        //Navigation drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);

        /**
         * When an item is tapped, this code sets the selected item as checked,
         * changing the list item's style to be highlighted because the list items
         * are part of a checkable group
         */
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        /**
                         * Add code here to update the UI based on the item selected.
                         * For example, swap UI fragments here
                         */
                        return true;
                    }
                }
        );

        //Find the viewPager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);

        //Create an adapter that knows which fragment is to be shown on each page
        RecipeFragmentPager adapter = new RecipeFragmentPager(getSupportFragmentManager(), RecipeActivity.this);

        //set the adapter on to the viewPager
        viewPager.setAdapter(adapter);

        //Give the tabLayout to the viewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
