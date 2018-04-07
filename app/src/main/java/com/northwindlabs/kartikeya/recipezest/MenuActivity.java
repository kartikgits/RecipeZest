package com.northwindlabs.kartikeya.recipezest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class MenuActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int menuIdFromPreviousActivity;

        Bundle extras = getIntent().getExtras();
        //Handle intent thrown from previous activity
        if (extras != null) {
            menuIdFromPreviousActivity = extras.getInt("MenuItemId");
            android.support.v4.app.Fragment fragment = null;
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

            switch (menuIdFromPreviousActivity) {
                case R.id.nav_myProfile:
                    fragment = new FragmentMyProfile();
                    break;

                case R.id.nav_shoppingList:
                    fragment = new FragmentShoppingList();
                    break;

                case R.id.nav_myFavorite:
                    fragment = new FragmentMyFavorite();
                    break;

                case R.id.nav_settings:
                    fragment = new FragmentSettings();
                    break;

                case R.id.nav_aboutUs:
                    fragment = new FragmentAboutUs();
                    break;

                case R.id.nav_feedback:
                    fragment = new FragmentFeedback();
                    break;

                default:
                    Log.w("MenuActivity", "Unexpected Error handling menu item. Default case called");
            }
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        }

        /* Set the content of the activity to use activity_menu.xml layout file */
        setContentView(R.layout.activity_menu);

        final boolean recipeActivityFlag = false;

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
                        android.support.v4.app.Fragment fragment = null;
                        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                        if (menuItem.getItemId() != R.id.nav_home) {
                            switch (menuItem.getItemId()) {
                                case R.id.nav_myProfile:
                                    fragment = new FragmentMyProfile();
                                    break;

                                case R.id.nav_shoppingList:
                                    fragment = new FragmentShoppingList();
                                    break;

                                case R.id.nav_myFavorite:
                                    fragment = new FragmentMyFavorite();
                                    break;

                                case R.id.nav_settings:
                                    fragment = new FragmentSettings();
                                    break;

                                case R.id.nav_aboutUs:
                                    fragment = new FragmentAboutUs();
                                    break;

                                case R.id.nav_feedback:
                                    fragment = new FragmentFeedback();
                                    break;

                                default:
                                    Log.w("MenuActivity", "Unexpected Error handling menu item. Default case called");
                            }
                            if (fragment != null) {
                                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frame_container, fragment);
                                fragmentTransaction.commit();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container, fragment).commit();
                            } else {
                                Log.e("RecipeActivity", "Couldn't open desired fragment");
                            }
                        } else {
                            if (recipeActivityFlag) {
                                //If already in present activity, do nothing.
                            } else {
                                Intent intent = new Intent(getBaseContext(), RecipeActivity.class);
                                startActivity(intent);
                            }
                        }
//                        switch (menuItem.getItemId()){
//                            case R.id.nav_home:
//                                //if (recipeActivityFlag){
//                                //    Log.w("RecipeActivity","Current Activity already here");
//                                //} else {
//
//                                //}
//                            default:
//                                if (recipeActivityFlag){
//                                    Log.w("RecipeActivity","Current Activity already here");
//                                } else {
//                                    Intent intentD = new Intent(getParent(), RecipeActivity.class);
//                                    startActivity(intentD);
//                                }
//
//                        }
                        /* close drawer when item is tapped */
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                }
        );
    }
}
