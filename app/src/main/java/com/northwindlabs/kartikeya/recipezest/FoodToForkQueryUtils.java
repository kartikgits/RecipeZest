package com.northwindlabs.kartikeya.recipezest;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a helper class to request and receive data from Food2Fork
 */

final class FoodToForkQueryUtils {

    //Private constructor to ensure not to create objects of this class as instance is not needed
    private FoodToForkQueryUtils() {
    }

    //Adding the search_string to the URL of FoodToFork
    //private static String setSearchLink(String)

    /**
     * Return the list of FoodToForkRecipe objects that has been built up
     * from passing the JSON response
     */
    private static List<FoodToForkRecipe> extractRecipesFromJSON(String FoodToForkRecipeJSON) {
        //If JSON string is empty or null, return early
        if (TextUtils.isEmpty(FoodToForkRecipeJSON)) {
            return null;
        }

        //Create an empty ArrayList that we can start adding recipes to
        List<FoodToForkRecipe> foodToForkRecipes = new ArrayList<>();

        /**
         * Try to parse the JSON response string. If there is a problem with the way
         * JSON is formatted, a JSONException will be thrown. Catch this Exception.
         */
        try {
            //Create a JSON Object from the JSON response string
            JSONObject baseJSONResponse = new JSONObject(FoodToForkRecipeJSON);

            // For a given recipe, extract the JSONArray of recipes from key "recipes"
            JSONArray recipesArray = baseJSONResponse.getJSONArray("recipes");

            //Extract values from array
            for (int i = 0; i < recipesArray.length(); i++) {
                JSONObject currentRecipe = recipesArray.getJSONObject(i);
                String publisher = currentRecipe.getString("publisher");
                String f2fUrl = currentRecipe.getString("source_url");
                String title = currentRecipe.getString("title");
                String recipeId = currentRecipe.getString("recipe_id");
                String imageUrl = currentRecipe.getString("image_url");

                // Create a new ForkToForkRecipe object with the title, f2fUrl, imageUrl, recipeId
                // and publisher from the JSON response.
                FoodToForkRecipe f2fRecipe = new FoodToForkRecipe(title, f2fUrl, imageUrl, recipeId, publisher);

                // Add the new {@link Earthquake} to the list of earthquakes.
                foodToForkRecipes.add(f2fRecipe);
            }

        } catch (JSONException e) {
            Log.e("FoodToFortQueryUtils", "Problem parsing the FoodToFork JSON results", e);
        }

        //Return the list of recipes
        return foodToForkRecipes;
    }


    /**
     * Query the FoodToFork data-set and request a list of FoodToForkRecipe objects
     */
    public static List<FoodToForkRecipe> fetchRecipeData(String requestUrl) {
        Log.i("FoodToForkUtils", "fetchRecipeData called.");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("FoodToForkQueryUtils", "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of FoodToForkRecipe(s)
        List<FoodToForkRecipe> f2fRecipes = extractRecipesFromJSON(jsonResponse);

        // Return the list of FoodToForkRecipe(s)
        return f2fRecipes;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("FoodToForkQueryUtils", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("FoodToForkQueryUtils", "Problem retrieving the recipe JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    /**
     * Convert the InputStream into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("FoodToForkQueryUtils", "Problem building the URL ", e);
        }
        return url;
    }

}
