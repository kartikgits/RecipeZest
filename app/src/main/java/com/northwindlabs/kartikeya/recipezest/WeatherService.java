package com.northwindlabs.kartikeya.recipezest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.util.Log;


public class WeatherService implements LoaderManager.LoaderCallbacks<Weather> {
    private static String condition;
    private static int temp;
    private static int conditionCode;
    private Context context;

    /**
     * Refer to Yahoo Weather API Documentation for information about the codes
     */
    private static int[] windyWeatherCodeArray = {0, 1, 2, 3, 4, 24, 37, 38, 39};
    private static int[] rainyWeatherCodeArray = {11, 12, 26, 27, 28, 35, 40, 44, 45, 47};

    private String city;

    private final String LOG_TAG = "WeatherService";

    private static String weatherConditionRequestUrl = "https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22";
    private static String remainingUrlPart = "%22)%20and%20u%3D%22c%22&format=json";

    public WeatherService(String city, Context context) {
        this.context = context;
        this.city = city;
    }


    @Override
    public android.support.v4.content.Loader<Weather> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "onCreateLoader() called.");
        return new WeatherLoader(context, weatherConditionRequestUrl + city + remainingUrlPart);
    }


    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Weather> loader, Weather weather) {
        Log.i(LOG_TAG, "onLoadFinished called.");

        if (weather != null) {
            //can comment out this line to act as if there were no data on the server
            condition = weather.getCondition();
            temp = weather.getTemp();
            conditionCode = weather.getConditionCode();
        }
    }


    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Weather> loader) {
        Log.i(LOG_TAG, "onLoaderReset called.");
        // Loader reset, so we can clear out our existing data.
        // Nothing to do here
    }

    public String getWeatherCondition() {
        if (temp <= 15) {
            return "cold";
        } else if (temp <= 30) {
            for (int n : windyWeatherCodeArray) {
                if (conditionCode == n) {
                    return "windy";
                }
            }
            for (int n : rainyWeatherCodeArray) {
                if (conditionCode == n) {
                    return "rainy";
                }
            }

            //If none of the conditions match, return either rainy, windy, cold or hot by further
            //diving into temperatures
            if (temp <= 20) {
                return "cold";
            } else if (temp <= 23) {
                return "rainy";
            } else if (temp <= 25) {
                return "windy";
            } else return "hot";
        } else return "hot";
    }
}
