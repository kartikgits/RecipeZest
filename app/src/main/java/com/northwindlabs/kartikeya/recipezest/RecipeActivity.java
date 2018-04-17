package com.northwindlabs.kartikeya.recipezest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

/**
 * Created by Kartikeya on 2/14/2018.
 */

public class RecipeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private FusedLocationProviderClient mFusedLocationClient;

    //Default location city set to Stockholm, Sweden if no location is detected due to one or more reasons
    public static String city = "stockholm";

    //Required default empty constructor
    public RecipeActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        //Get user's fused (last known) location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(RecipeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Toast.makeText(this, "No Location Permission. " + city, Toast.LENGTH_SHORT).show();
        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                Geocoder geocoder = null;
                                List<Address> addresses = null;
                                geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

                                try {
                                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned
                                    if (addresses == null) {
                                        //Keep default city
                                        Toast.makeText(getBaseContext(), "No Location Found " + city, Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If any additional address line present than only
                                        city = addresses.get(0).getLocality();
                                        if (city == null) {
                                            city = addresses.get(0).getAdminArea();
                                            if (city == null) {
                                                Toast.makeText(getBaseContext(), "No City Found", Toast.LENGTH_SHORT).show();
                                                city = "stockholm";
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    Log.e("RecipeActivity", "Null Address");
                                }
                            } else {
                                Toast.makeText(getBaseContext(), "No Location Found", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getBaseContext(), city, Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        /* Set the content of the activity to use activity_recipe.xml layout file */
        setContentView(R.layout.activity_recipe);

        /* Set the toolbar as the action bar */
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        /* Enable the app bar's "home" button */
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        /* Navigation drawer */
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
                        /* set item as selected to persist highlight */
                        menuItem.setChecked(false);

                        /**
                         * Add code here to update the UI based on the item selected.
                         * For example, swap UI fragments here
                         */
                        Intent intent = null;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                break;

                            case R.id.nav_myProfile:
                                intent = new Intent(getBaseContext(), AccountActivity.class);
                                startActivity(intent);
                                RecipeActivity.this.onStop();
                                break;

                            case R.id.nav_shoppingList:
                                intent = new Intent(getBaseContext(), ShoppingListActivity.class);
                                startActivity(intent);
                                RecipeActivity.this.onStop();
                                break;

                            case R.id.nav_myFavorite:
                                intent = new Intent(getBaseContext(), MyFavoriteActivity.class);
                                startActivity(intent);
                                RecipeActivity.this.onStop();
                                break;

                            case R.id.nav_settings:
                                intent = new Intent(getBaseContext(), SettingsActivity.class);
                                startActivity(intent);
                                RecipeActivity.this.onStop();
                                break;

                            case R.id.nav_aboutUs:
                                intent = new Intent(getBaseContext(), AboutUsActivity.class);
                                startActivity(intent);
                                RecipeActivity.this.onStop();
                                break;

                            case R.id.nav_feedback:
                                intent = new Intent(getBaseContext(), FeedbackActivity.class);
                                startActivity(intent);
                                RecipeActivity.this.onStop();
                                break;

                            default:
                                Log.w("MenuActivity", "Unexpected Error handling menu item. Default case called");
                        }

                        /* close drawer when item is tapped */
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                }
        );

        /* Find the viewPager that will allow the user to swipe between fragments */
        ViewPager viewPager = findViewById(R.id.viewpager);

        /* Create an adapter that knows which fragment is to be shown on each page */
        RecipeFragmentPager adapter = new RecipeFragmentPager(getSupportFragmentManager(), RecipeActivity.this);

        /* set the adapter on to the viewPager */
        viewPager.setAdapter(adapter);

        /* Give the tabLayout to the viewPager */
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    //Open the drawer when the button is tapped
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /* pass GravityCompat.START as the open drawer animation gravity to openDrawer() */
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
