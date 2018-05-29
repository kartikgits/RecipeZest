package com.northwindlabs.kartikeya.recipezest;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.annotation.Nullable;

import static com.northwindlabs.kartikeya.recipezest.RZestContract.UserFavorites.COLUMN_FAVORITE_RECIPE_NAME;
import static com.northwindlabs.kartikeya.recipezest.RZestContract.UserFavorites.TABLE_NAME;

public class EdamamDetailRecipeActivity extends AppCompatActivity {

    EdamamIngredientListAdapter eAdapter;

    private RecipeZestDBHelper mDbHelper;
    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edamam_recipe_details);

        //Get Intent and extract object
        Intent intent = getIntent();
        final EdamamRecipe currentRecipe = (EdamamRecipe) intent.getSerializableExtra("edamamRecipeObject");

        ImageView recipeImage = findViewById(R.id.recipe_image);
        String link = currentRecipe.getImageUrl();
        if (link != null && !TextUtils.isEmpty(link)) {
            //use Picasso API to handle downloading images
            Picasso.with(this).load(link).into(recipeImage);
        } else {
            //if there is no image, use this image instead
            recipeImage.setImageResource(R.drawable.no_recipe_image);
        }

        TextView recipeTitle = findViewById(R.id.recipe_title);
        recipeTitle.setText(currentRecipe.getTitle());

        TextView ingredientCount = findViewById(R.id.ingredient_count);
        String numberOfIngredients = "" + currentRecipe.getIngredientsArrayListSize() + " ingredients";
        ingredientCount.setText(numberOfIngredients);

        TextView calorieCount = findViewById(R.id.calorie_count);
        String calories = "" + currentRecipe.getCalories();
        calorieCount.setText(calories);

        TextView timing = findViewById(R.id.minutes_count);
        String minutesCount = "" + currentRecipe.getTotalTime();
        timing.setText(minutesCount);

        ListView ingredientsListView = findViewById(R.id.edamam_ingredients_list_view);

        // Create a new adapter that takes an empty list of recipes as input
        eAdapter = new EdamamIngredientListAdapter(this, new ArrayList<String>());

        eAdapter.addAll(currentRecipe.getIngredients());

        // Set the adapter on the ListView
        // so the list can be populated in the user interface
        ingredientsListView.setAdapter(eAdapter);

        Button directionsButton = findViewById(R.id.directions_button);
        directionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri recipeUri = Uri.parse(currentRecipe.getUrl());

                // Create a new intent to view the recipe URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, recipeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        LikeButton likeButton = findViewById(R.id.like_button);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                //For creating key-value pairs to be stored in database
                final ContentValues values = new ContentValues();
                values.put(RZestContract.UserFavorites.COLUMN_FAVORITE_RECIPE_NAME, currentRecipe.getTitle());
                values.put(RZestContract.UserFavorites.COLUMN_FAVORITE_RECIPE_IMAGE, currentRecipe.getImageUrl());
                values.put(RZestContract.UserFavorites.COLUMN_FAVORITE_RECIPE_PUBLISHER, currentRecipe.getSource());
                values.put(RZestContract.UserFavorites.COLUMN_FAVORITE_RECIPE_URL, currentRecipe.getUrl());
                insertRecipeToFavorites(values);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                removeRecipeFromFavorites(currentRecipe.getTitle());
            }
        });

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected recipe.
        ingredientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current recipe that was clicked on
                String currentIngredient = eAdapter.getItem(position);
                //For creating key-value pairs to be stored in database
                final ContentValues values = new ContentValues();
                values.put(RZestContract.IngredientsTable.COLUMN_RECIPE_NAME, currentIngredient);
                insertIngredientToDatabase(values);
            }
        });
    }

    private void insertIngredientToDatabase(ContentValues value) {
        // Create database helper
        mDbHelper = new RecipeZestDBHelper(this);

        // Gets the database in write mode
        db = mDbHelper.getWritableDatabase();

        // Insert a new row for ingredient in the database, returning the ID of that new row.
        long newRowId = db.insert(RZestContract.IngredientsTable.TABLE_NAME, null, value);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Added to Shopping List ", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertRecipeToFavorites(ContentValues values) {
        // Create database helper
        mDbHelper = new RecipeZestDBHelper(this);

        // Gets the database in write mode
        db = mDbHelper.getWritableDatabase();

        // Insert a new row for ingredient in the database, returning the ID of that new row.
        long newRowId = db.insert(TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with adding to favorites", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeRecipeFromFavorites(String recipeName) {
        mDbHelper = new RecipeZestDBHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where " + COLUMN_FAVORITE_RECIPE_NAME + " = '" + recipeName + "'");
        Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
    }
}
