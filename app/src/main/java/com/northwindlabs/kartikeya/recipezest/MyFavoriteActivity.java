package com.northwindlabs.kartikeya.recipezest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.northwindlabs.kartikeya.recipezest.RZestContract.UserFavorites.COLUMN_FAVORITE_RECIPE_NAME;
import static com.northwindlabs.kartikeya.recipezest.RZestContract.UserFavorites.TABLE_NAME;
import static com.northwindlabs.kartikeya.recipezest.RZestContract.UserFavorites._ID;

public class MyFavoriteActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    FoodToForkAdapter f2fAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Set the content of the activity to use activity_menu.xml layout file */
        setContentView(R.layout.activity_my_favorite);

        Toast.makeText(this, "Long Press Recipes to Remove", Toast.LENGTH_LONG).show();

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
                                intent = new Intent(getBaseContext(), RecipeActivity.class);
                                startActivity(intent);
                                MyFavoriteActivity.this.onStop();
                                break;

                            case R.id.nav_myProfile:
                                intent = new Intent(getBaseContext(), AccountActivity.class);
                                startActivity(intent);
                                MyFavoriteActivity.this.onStop();
                                break;

                            case R.id.nav_shoppingList:
                                intent = new Intent(getBaseContext(), ShoppingListActivity.class);
                                startActivity(intent);
                                MyFavoriteActivity.this.onStop();
                                break;

                            case R.id.nav_myFavorite:
                                break;

                            case R.id.nav_settings:
                                intent = new Intent(getBaseContext(), SettingsActivity.class);
                                startActivity(intent);
                                MyFavoriteActivity.this.onStop();
                                break;

                            case R.id.nav_aboutUs:
                                intent = new Intent(getBaseContext(), AboutUsActivity.class);
                                startActivity(intent);
                                MyFavoriteActivity.this.onStop();
                                break;

                            case R.id.nav_feedback:
                                intent = new Intent(getBaseContext(), FeedbackActivity.class);
                                startActivity(intent);
                                MyFavoriteActivity.this.onStop();
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

        ListView favoriteListView = findViewById(R.id.list);

        // Create a new adapter that takes an empty list of recipes as input
        f2fAdapter = new FoodToForkAdapter(this, new ArrayList<FoodToForkRecipe>());

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        RecipeZestDBHelper mDbHelper = new RecipeZestDBHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM ingredients"
        // to get a Cursor that contains all rows from the ingredients table.
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        final ArrayList<FoodToForkRecipe> favoritesArrayList = new ArrayList<>();
        int columnIndexRecipeName = mCursor.getColumnIndex(RZestContract.UserFavorites.COLUMN_FAVORITE_RECIPE_NAME);
        int columnIndexRecipeImage = mCursor.getColumnIndex(RZestContract.UserFavorites.COLUMN_FAVORITE_RECIPE_IMAGE);
        int columnIndexRecipeUrl = mCursor.getColumnIndex(RZestContract.UserFavorites.COLUMN_FAVORITE_RECIPE_URL);
        int columnIndexRecipePublisher = mCursor.getColumnIndex(RZestContract.UserFavorites.COLUMN_FAVORITE_RECIPE_PUBLISHER);
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            // The Cursor is now set to the right position
            FoodToForkRecipe f2fRecipe = new FoodToForkRecipe(mCursor.getString(columnIndexRecipeName), mCursor.getString(columnIndexRecipeUrl), mCursor.getString(columnIndexRecipeImage), null, mCursor.getString(columnIndexRecipePublisher));
            favoritesArrayList.add(f2fRecipe);
        }
        //Invalidating the cursor, hence, releasing the resources.
        mCursor.close();

        f2fAdapter.addAll(favoritesArrayList);

        // Set the adapter on the ListView
        // so the list can be populated in the user interface
        favoriteListView.setAdapter(f2fAdapter);

        favoriteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FoodToForkRecipe currentRecipe = f2fAdapter.getItem(position);
                String currentRecipeTitle = currentRecipe.getTitle();

                RecipeZestDBHelper mDbHelper = new RecipeZestDBHelper(getBaseContext());
                SQLiteDatabase dbr = mDbHelper.getReadableDatabase();
                Cursor mCursor = dbr.rawQuery("SELECT " + _ID + " FROM " + TABLE_NAME + " where " + COLUMN_FAVORITE_RECIPE_NAME + " = \"" + currentRecipeTitle + "\"", null);
                int minId = -1;
                int columnIndex = mCursor.getColumnIndex(_ID);
                for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
                    // The Cursor is now set to the right position
                    minId = mCursor.getInt(columnIndex);
                    break;
                }
                mCursor.close();
                if (minId != -1) {
                    // Create and/or open a database to read from it
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    db.execSQL("delete from " + TABLE_NAME + " where " + _ID + " = " + minId);
                }
                //To refresh activity
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
                return true;
            }
        });

        favoriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current recipe that was clicked on
                FoodToForkRecipe currentRecipe = f2fAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri recipeUri = Uri.parse(currentRecipe.getF2fUrl());

                // Create a new intent to view the recipe URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, recipeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

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
