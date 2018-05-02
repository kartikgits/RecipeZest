package com.northwindlabs.kartikeya.recipezest;

import android.content.Intent;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class EdamamDetailRecipeActivity extends AppCompatActivity {

    EdamamIngredientListAdapter eAdapter;

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

        //Set all the values to the adapter
        Log.e("EdamamDetailRecipe", currentRecipe.getIngredients().toString());

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

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected recipe.
        ingredientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current recipe that was clicked on
                String currentIngredient = eAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
//                Uri recipeUri = Uri.parse(currentRecipe.getUrl());

                Toast.makeText(getBaseContext(), currentIngredient, Toast.LENGTH_SHORT).show();

//                // Create a new intent to view the recipe URI
//                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, recipeUri);
//
//                // Send the intent to launch a new activity
//                startActivity(websiteIntent);
            }
        });

//        Button listAddButton = findViewById(R.id.add_to_list_button);
//        listAddButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i<currentRecipe.getIngredientsArrayListSize(); i++){
//                    if (eAdapter.getItem(i).)
//                }
//            }
//        });
    }
}
