package com.northwindlabs.kartikeya.recipezest;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Kartikeya
 */

public class EdamamRecipeLoader extends AsyncTaskLoader<List<EdamamRecipe>> {

    private static final String LOG_TAG = "EdamamRecipeLoader";

    private String url = null;

    public EdamamRecipeLoader(Context context, String mUrl) {
        super(context);
        Log.i(LOG_TAG, "default constructor called.");
        url = mUrl;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoading called.");
        forceLoad();
    }

    @Override
    public List<EdamamRecipe> loadInBackground() {
        Log.i(LOG_TAG, "loadInBackground called.");
        // Don't perform the request if there are no URLs
        if (url == null) {
            return null;
        }
        List<EdamamRecipe> eRecipes = EdamamQueryUtils.fetchRecipeData(url);
        return eRecipes;
    }
}
