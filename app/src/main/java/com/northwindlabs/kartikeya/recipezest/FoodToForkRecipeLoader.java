package com.northwindlabs.kartikeya.recipezest;

import android.app.LoaderManager;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Created by Kartikeya
 */

public class FoodToForkRecipeLoader extends AsyncTaskLoader<List<FoodToForkRecipe>> {

    private static final String LOG_TAG = "FoodToForkRecipeLoader";

    private String url = null;

    public FoodToForkRecipeLoader(Context context, String mUrl) {
        super(context);
        Log.i(LOG_TAG, "FoodToForkRecipeLoader default constructor called.");
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
