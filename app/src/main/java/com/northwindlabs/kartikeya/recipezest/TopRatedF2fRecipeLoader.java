package com.northwindlabs.kartikeya.recipezest;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Kartikeya
 */

public class TopRatedF2fRecipeLoader extends AsyncTaskLoader<List<FoodToForkRecipe>> {

    private static final String LOG_TAG = "TopRatedF2fRecipeLoader";

    private String url = null;

    public TopRatedF2fRecipeLoader(Context context, String mUrl) {
        super(context);
        Log.i(LOG_TAG, "TopRatedF2fRecipeLoader default constructor called.");
        url = mUrl;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoading called.");
        forceLoad();
    }

    @Override
    public List<FoodToForkRecipe> loadInBackground() {
        Log.i(LOG_TAG, "loadInBackground called.");
        // Don't perform the request if there are no URLs
        if (url == null) {
            return null;
        }
        List<FoodToForkRecipe> f2fRecipes = FoodToForkQueryUtils.fetchRecipeData(url);
        return f2fRecipes;
    }
}