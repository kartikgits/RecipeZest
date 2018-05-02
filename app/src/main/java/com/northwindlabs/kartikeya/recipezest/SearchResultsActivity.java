package com.northwindlabs.kartikeya.recipezest;

import android.app.LoaderManager;
import android.app.SearchManager;
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

public class SearchResultsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EdamamRecipe>> {

    private static final String LOG_TAG = "SearchResultsActivity";

    private AVLoadingIndicatorView avi;

    /**
     * Constant value for the Edamam-loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int F2F_LOADER_ID = 4;

    /**
     * Adapter for the list of recipes
     */
    private EdamamAdapter eAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    /**
     * URL for requesting recipe data from Edamam
     */
    private final String eRequestUrlQuery = "https://api.edamam.com/search?q=";

    /**
     * Place keyword like for eg. 'alcohol-free' for Alcohol Free category
     * instead of 'alcohol%20free'
     */
//    private final String eRequestUrlHealthLabel = "&app_id=8e776dd7&app_key=fccce5490f6d74ca24ea46bf81833ea8&from=0&to=30&health=";
//    private final String eRequestUrlDietLabel = "&app_id=8e776dd7&app_key=fccce5490f6d74ca24ea46bf81833ea8&from=0&to=30&diet=";

    /**
     * Remaining URL part (if no extra search constraints are put)
     */
    private final String remainingUrl = "&app_id=8e776dd7&app_key=fccce5490f6d74ca24ea46bf81833ea8&from=0&to=40";

    /**
     * Value to be received through calling intent.
     * "diet" for Diet, "health" for Health.
     */
    //private String DHLabel;

    /**
     * final URL
     */
    private String completeEUrl = null;

    /**
     * Recipe category variable. Value will be received through calling intent
     */
//    private String recipeCategory;
    private String userQueryKeyword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated_f2f_recipes);

        userQueryKeyword = getIntent().getStringExtra("searchKeyword");

        // Find a reference to the {@link ListView} in the layout
        ListView listView = findViewById(R.id.list);

        //Set an empty view
        mEmptyStateTextView = findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of recipes as input
        eAdapter = new EdamamAdapter(this, new ArrayList<EdamamRecipe>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        listView.setAdapter(eAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected recipe.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current recipe that was clicked on
                EdamamRecipe currentRecipe = eAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
//                Uri recipeUri = Uri.parse(currentRecipe.getUrl());

                Intent intent = new Intent(getBaseContext(), EdamamDetailRecipeActivity.class);
                intent.putExtra("edamamRecipeObject", currentRecipe);
                startActivity(intent);

//                // Create a new intent to view the recipe URI
//                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, recipeUri);
//
//                // Send the intent to launch a new activity
//                startActivity(websiteIntent);
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
    public Loader<List<EdamamRecipe>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "onCreateLoader() called.");
        //Add here the method to get current recipe as this is the method to create recipe loader
        completeEUrl = eRequestUrlQuery + userQueryKeyword + remainingUrl;
        return new EdamamRecipeLoader(this, completeEUrl);
    }


    @Override
    public void onLoadFinished(Loader<List<EdamamRecipe>> loader, List<EdamamRecipe> recipes) {
        //After the load is finished, we need to remove the progress bar
        avi = findViewById(R.id.loading_spinner);
        avi.setVisibility(View.GONE);
        // Set empty state text
        mEmptyStateTextView.setText(R.string.no_recipes);
        Log.i(LOG_TAG, "onLoadFinished called.");
        // Clear the adapter of previous recipe data
        eAdapter.clear();

        // If there is a valid list of Recipes, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (recipes != null && !recipes.isEmpty()) {
            //can comment out this line to act as if there were no data on the server
            eAdapter.addAll(recipes);
        }
    }


    @Override
    public void onLoaderReset(Loader<List<EdamamRecipe>> loader) {
        Log.i(LOG_TAG, "onLoaderReset called.");
        // Loader reset, so we can clear out our existing data.
        eAdapter.clear();
    }
}