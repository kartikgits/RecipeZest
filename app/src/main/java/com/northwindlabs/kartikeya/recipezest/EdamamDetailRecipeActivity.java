package com.northwindlabs.kartikeya.recipezest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EdamamDetailRecipeActivity extends AppCompatActivity {

    EdamamIngredientListAdapter eAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edamam_recipe_details);

        //Get Intent and extract object
        Intent intent = getIntent();
        EdamamRecipe currentRecipe = (EdamamRecipe) intent.getSerializableExtra("edamamRecipeObject");

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
    }
}
