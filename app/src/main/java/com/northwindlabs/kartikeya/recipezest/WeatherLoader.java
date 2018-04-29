package com.northwindlabs.kartikeya.recipezest;

import android.app.LoaderManager;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Created by Kartikeya
 */

public class WeatherLoader extends AsyncTaskLoader<Weather> {

    private static final String LOG_TAG = "WeatherLoader";

    private String url = null;

    public WeatherLoader(Context context, String mUrl) {
        super(context);
        Log.i(LOG_TAG, "WeatherLoader default constructor called.");
        url = mUrl;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoading called.");
        forceLoad();
    }

    @Override
    public Weather loadInBackground() {
        Log.i(LOG_TAG, "loadInBackground called.");
        // Don't perform the request if there are no URLs
        if (url == null) {
            return null;
        }
        Weather weather = WeatherQueryUtils.fetchWeatherData(url);
        return weather;
    }
}
