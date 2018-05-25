package com.northwindlabs.kartikeya.recipezest;

import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kartikeya on 2/15/2018.
 */

/**
 * To display 'For You' Fragment
 */
public class ForYouFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<EdamamRecipe>> {

    private static final String LOG_TAG = "ForYouFragment";

    private AVLoadingIndicatorView avi;

    /**
     * Constant value for the Edamam-loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int F2F_LOADER_ID = 8;

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
    private final String eRequestUrlHealthLabel = "&app_id=8e776dd7&app_key=fccce5490f6d74ca24ea46bf81833ea8&from=0&to=30&health=";
    private final String eRequestUrlDietLabel = "&app_id=8e776dd7&app_key=fccce5490f6d74ca24ea46bf81833ea8&from=0&to=30&diet=";
    private final String eRequestUrlNoLabel = "&app_id=8e776dd7&app_key=fccce5490f6d74ca24ea46bf81833ea8&from=0&to=30";

    /**
     * final URL
     */
    private String completeEUrl = null;

    /**
     * Recipe category variable. Value will be received through calling intent
     */
    private String recipeCategory;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_top_rated_f2f_recipes, container, false);
        // Find a reference to the {@link ListView} in the layout
        ListView listView = view.findViewById(R.id.list);

        //Set an empty view
        mEmptyStateTextView = view.findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of recipes as input
        eAdapter = new EdamamAdapter(getActivity(), new ArrayList<EdamamRecipe>());

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

                Intent intent = new Intent(getContext(), EdamamDetailRecipeActivity.class);
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
        android.support.v4.app.LoaderManager loaderManager = getLoaderManager();

        //Before initializing the loader, check for the network connectivity
        ConnectivityManager cm = (ConnectivityManager) getActivity().getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected == true) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(F2F_LOADER_ID, null, this);
            Log.i(LOG_TAG, "initLoader called.");
        } else {
            mEmptyStateTextView = view.findViewById(R.id.empty_view);
            mEmptyStateTextView.setText(R.string.no_internet);
            avi = view.findViewById(R.id.loading_spinner);
            avi.hide();
        }

        return view;
    }


    @Override
    public android.support.v4.content.Loader<List<EdamamRecipe>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "onCreateLoader() called.");
        return new EdamamV4RecipeLoader(getContext(), getCompleteEUrl());
    }


    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<EdamamRecipe>> loader, List<EdamamRecipe> recipes) {
        //After the load is finished, we need to remove the progress bar
        avi = getView().findViewById(R.id.loading_spinner);
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
    public void onLoaderReset(android.support.v4.content.Loader<List<EdamamRecipe>> loader) {
        Log.i(LOG_TAG, "onLoaderReset called.");
        // Loader reset, so we can clear out our existing data.
        eAdapter.clear();
    }

    public String getCompleteEUrl() {
        String currentDHLabel = RandomsGenerator.getRandomDHLabel(getContext());
        if (currentDHLabel.equals("balanced") || currentDHLabel.equals("high-protein") ||
                currentDHLabel.equals("low-fat") || currentDHLabel.equals("low-carb")) {
            completeEUrl = eRequestUrlQuery + RandomsGenerator.getRandomAlphabet() + eRequestUrlDietLabel + currentDHLabel;
        } else if (currentDHLabel.equals("no-label")) {
            completeEUrl = eRequestUrlQuery + RandomsGenerator.getRandomAlphabet() + eRequestUrlNoLabel;
        } else {
            completeEUrl = eRequestUrlQuery + RandomsGenerator.getRandomAlphabet() + eRequestUrlHealthLabel + currentDHLabel;
        }
        return completeEUrl;
    }
}