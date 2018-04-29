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
 * This is a helper class to request and receive data from Yahoo Weather service
 */

final class WeatherQueryUtils {

    //Private constructor to ensure not to create objects of this class as instance is not needed
    private WeatherQueryUtils() {
    }

    /**
     * Return the Weather object that has been built up
     * from passing the JSON response
     */
    private static Weather extractWeatherConditionFromJSON(String YahooQueryJSON) {
        //If JSON string is empty or null, return early
        if (TextUtils.isEmpty(YahooQueryJSON)) {
            return null;
        }

        Weather weather = null;

        /**
         * Try to parse the JSON response string. If there is a problem with the way
         * JSON is formatted, a JSONException will be thrown. Catch this Exception.
         */
        try {
            //Create a JSON Object from the JSON response string
            JSONObject baseJSONResponse = new JSONObject(YahooQueryJSON);

            //Extracting JSON as per the requirement
            JSONObject query = baseJSONResponse.getJSONObject("query");
            JSONObject item = query.getJSONObject("item");
            JSONObject condition = item.getJSONObject("condition");
            int conditionCode = condition.getInt("code");
            int temp = condition.getInt("temp");
            String mCondition = condition.getString("text");

            weather = new Weather(mCondition, temp, conditionCode);

        } catch (JSONException e) {
            Log.e("WeatherQueryUtils", "Problem parsing the Yahoo Weather JSON results", e);
        }

        return weather;
    }


    /**
     * Query the Yahoo Weather service and request weather object
     */
    public static Weather fetchWeatherData(String requestUrl) {
        Log.i("WeatherQueryUtils", "fetchWeatherData called.");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("WeatherQueryUtils", "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response
        Weather weather = extractWeatherConditionFromJSON(jsonResponse);

        // Return the Weather object
        return weather;
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
                Log.e("WeatherQueryUtils", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("WeatherQueryUtils", "Problem retrieving the recipe JSON results.", e);
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
            Log.e("WeatherQueryUtils", "Problem building the URL ", e);
        }
        return url;
    }

}