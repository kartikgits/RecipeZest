package com.northwindlabs.kartikeya.recipezest;


import android.text.TextUtils;
import android.util.Log;
import android.widget.CheckBox;

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

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * This is a helper class to request and receive data from Edamam
 */

final class EdamamQueryUtils {

    //Private constructor to ensure not to create objects of this class as instance is not needed
    private EdamamQueryUtils() {
    }

    /**
     * Return the list of EdamamRecipe objects that has been built up
     * from passing the JSON response
     */
    private static List<EdamamRecipe> extractRecipesFromJSON(String EdamamRecipeJSON) {
        //If JSON string is empty or null, return early
        if (TextUtils.isEmpty(EdamamRecipeJSON)) {
            return null;
        }

        //Create an empty ArrayList that we can start adding recipes to
        List<EdamamRecipe> edamamRecipes = new ArrayList<>();

        /**
         * Try to parse the JSON response string. If there is a problem with the way
         * JSON is formatted, a JSONException will be thrown. Catch this Exception.
         */
        try {
            //Create a JSON Object from the JSON response string
            JSONObject baseJSONResponse = new JSONObject(EdamamRecipeJSON);

            // For a given recipe, extract the JSONArray of recipes from array "hits"
            JSONArray hitsArray = baseJSONResponse.getJSONArray("hits");

            //Extract values from array
            for (int i = 0; i < hitsArray.length(); i++) {
                JSONObject currentHit = hitsArray.getJSONObject(i);
                JSONObject currentRecipe = currentHit.getJSONObject("recipe");
                String title = currentRecipe.getString("label");
                String url = currentRecipe.getString("url");
                String source = currentRecipe.getString("source");
                int calories = currentRecipe.getInt("calories");
                String imageUrl = currentRecipe.getString("image");
                int totalTime = currentRecipe.getInt("totalTime");
                JSONArray ingredientsArray = currentRecipe.getJSONArray("ingredientLines");
                ArrayList<String> ingredientsArrayList = new ArrayList<>();

                for (int j = 0; j < ingredientsArray.length(); j++) {
                    ingredientsArrayList.add(j, ingredientsArray.getString(j));
                }

                // Create a new Edamam object passing the extracted values
                EdamamRecipe edamamRecipe = new EdamamRecipe(title, imageUrl, source, url, calories, totalTime, ingredientsArrayList, ingredientsArray.length());

                // Add the new {@link Earthquake} to the list of earthquakes.
                edamamRecipes.add(edamamRecipe);
            }

        } catch (JSONException e) {
            Log.e("EdamamQueryUtils", "Problem parsing the Edamam JSON results", e);
        }

        //Return the list of recipes
        return edamamRecipes;
    }


    /**
     * Query the Edamam data-set and request a list of EdamamRecipe objects
     */
    public static List<EdamamRecipe> fetchRecipeData(String requestUrl) {
        Log.i("EdamamQueryUtils", "fetchRecipeData called.");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("EdamamQueryUtils", "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of EdamamRecipe(s)
        List<EdamamRecipe> edamamRecipes = extractRecipesFromJSON(jsonResponse);

        // Return the list of EdamamRecipe(s)
        return edamamRecipes;
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
                Log.e("EdamamQueryUtils", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("EdamamQueryUtils", "Problem retrieving the recipe JSON results.", e);
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
            Log.e("EdamamQueryUtils", "Problem building the URL ", e);
        }
        return url;
    }

}
