package com.northwindlabs.kartikeya.recipezest;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class TopRatedRecipesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<FoodToForkRecipe>> {

    private static final String LOG_TAG = "LocalRecipesFragment";

    private AVLoadingIndicatorView avi;

    /**
     * Constant value for the FoodToFork-loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int F2F_LOADER_ID = 3;

    /**
     * Adapter for the list of recipes
     */
    private FoodToForkAdapter f2fAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    /**
     * URL for requesting recipe data from FoodToFork
     */
    private String f2fRequestUrl = "http://food2fork.com/api/search?key=f0bcc213e09ecf97334084b8bc42b49a&q=";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated_f2f_recipes);

        // Find a reference to the {@link ListView} in the layout
        ListView listView = findViewById(R.id.list);

        //Set an empty view
        mEmptyStateTextView = findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of recipes as input
        f2fAdapter = new FoodToForkAdapter(this, new ArrayList<FoodToForkRecipe>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        listView.setAdapter(f2fAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected recipe.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        //Before initializing the loader, check for the network connectivity
        ConnectivityManager cm = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected == true) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(F2F_LOADER_ID, null, this);
            Log.i(LOG_TAG, "initLoader called.");
        } else {
            mEmptyStateTextView = findViewById(R.id.empty_view);
            mEmptyStateTextView.setText(R.string.no_internet);
            avi = findViewById(R.id.loading_spinner);
            avi.hide();
        }
    }


    @Override
    public Loader<List<FoodToForkRecipe>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "onCreateLoader() called.");
        //Add here the method to get current recipe as this is the method to create recipe loader
        f2fRequestUrl += RandomsGenerator.getRandomAlphabet();
        return new TopRatedF2fRecipeLoader(this, f2fRequestUrl);
    }


    @Override
    public void onLoadFinished(Loader<List<FoodToForkRecipe>> loader, List<FoodToForkRecipe> recipes) {
        //After the load is finished, we need to remove the progress bar
        avi = findViewById(R.id.loading_spinner);
        avi.setVisibility(View.GONE);
        // Set empty state text
        mEmptyStateTextView.setText(R.string.no_recipes);
        Log.i(LOG_TAG, "onLoadFinished called.");
        // Clear the adapter of previous recipe data
        f2fAdapter.clear();

        // If there is a valid list of Recipes, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (recipes != null && !recipes.isEmpty()) {
            //can comment out this line to act as if there were no data on the server
            f2fAdapter.addAll(recipes);
        }
    }


    @Override
    public void onLoaderReset(Loader<List<FoodToForkRecipe>> loader) {
        Log.i(LOG_TAG, "onLoaderReset called.");
        // Loader reset, so we can clear out our existing data.
        f2fAdapter.clear();
    }

}
