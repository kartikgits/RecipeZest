package com.northwindlabs.kartikeya.recipezest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    EdamamIngredientListAdapter eAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Set the content of the activity to use activity_menu.xml layout file */
        setContentView(R.layout.activity_shopping_list);

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
                                ShoppingListActivity.this.onStop();
                                break;

                            case R.id.nav_myProfile:
                                intent = new Intent(getBaseContext(), AccountActivity.class);
                                startActivity(intent);
                                ShoppingListActivity.this.onStop();
                                break;

                            case R.id.nav_shoppingList:
                                break;

                            case R.id.nav_myFavorite:
                                intent = new Intent(getBaseContext(), MyFavoriteActivity.class);
                                startActivity(intent);
                                ShoppingListActivity.this.onStop();
                                break;

                            case R.id.nav_settings:
                                intent = new Intent(getBaseContext(), SettingsActivity.class);
                                startActivity(intent);
                                ShoppingListActivity.this.onStop();
                                break;

                            case R.id.nav_aboutUs:
                                intent = new Intent(getBaseContext(), AboutUsActivity.class);
                                startActivity(intent);
                                ShoppingListActivity.this.onStop();
                                break;

                            case R.id.nav_feedback:
                                intent = new Intent(getBaseContext(), FeedbackActivity.class);
                                startActivity(intent);
                                ShoppingListActivity.this.onStop();
                                break;

                            default:
                                Log.w("MenuActivity", "Unexpected Error handling menu item. Default case called");
                        }

                        /* close drawer when item is tapped */
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return false;
                    }
                }
        );

        ListView ingredientsListView = findViewById(R.id.edamam_ingredients_list_view);

        // Create a new adapter that takes an empty list of recipes as input
        eAdapter = new EdamamIngredientListAdapter(this, new ArrayList<String>());

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        RecipeZestDBHelper mDbHelper = new RecipeZestDBHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM ingredients"
        // to get a Cursor that contains all rows from the ingredients table.
        Cursor mCursor = db.rawQuery("SELECT * FROM " + RZestContract.IngredientsTable.TABLE_NAME, null);

        ArrayList<String> ingredientsArrayList = new ArrayList<>();
        int columnIndex = mCursor.getColumnIndex(RZestContract.IngredientsTable.COLUMN_RECIPE_NAME);
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            // The Cursor is now set to the right position
            ingredientsArrayList.add(mCursor.getString(columnIndex));
        }
        //Invalidating the cursor, hence, releasing the resources.
        mCursor.close();

        eAdapter.addAll(ingredientsArrayList);

        // Set the adapter on the ListView
        // so the list can be populated in the user interface
        ingredientsListView.setAdapter(eAdapter);
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
