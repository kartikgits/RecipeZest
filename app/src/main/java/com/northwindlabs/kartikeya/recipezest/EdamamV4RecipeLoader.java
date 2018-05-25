package com.northwindlabs.kartikeya.recipezest;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;


/**
 * Created by Kartikeya
 */

public class EdamamV4RecipeLoader extends AsyncTaskLoader<List<EdamamRecipe>> {

    private static final String LOG_TAG = "EdamamV4RecipeLoader";

    private String url = null;

    public EdamamV4RecipeLoader(Context context, String mUrl) {
        super(context);
        Log.i(LOG_TAG, "EdamamV4RecipeLoader default constructor called.");
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
