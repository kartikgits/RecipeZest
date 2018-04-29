package com.northwindlabs.kartikeya.recipezest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
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
import java.util.Random;

/**
 * Created by Kartikeya on 2/15/2018.
 */

/**
 * To display 'Local Recipes' Fragment
 */

//TODO (1) : Make this class load only once when starting the app



public class LocalRecipesFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<FoodToForkRecipe>> {

    private static final String LOG_TAG = "LocalRecipesFragment";

    private AVLoadingIndicatorView avi;

    ArrayList<String> recipeSH = new ArrayList<>();
    ArrayList<String> recipeSC = new ArrayList<>();
    ArrayList<String> recipeSR = new ArrayList<>();
    ArrayList<String> recipeSW = new ArrayList<>();

    ArrayList<String> recipeDC = new ArrayList<>();
    ArrayList<String> recipeDR = new ArrayList<>();
    ArrayList<String> recipeDH = new ArrayList<>();
    ArrayList<String> recipeDW = new ArrayList<>();

    /**
     * Constant value for the FoodToFork-loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int F2F_LOADER_ID = 1;

    /**
     * Adapter for the list of recipes
     */
    private FoodToForkAdapter f2fAdapter;

    /**
     * Progress Bar to be show while data loading
     */
    //private ProgressBar progressBar;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    /**
     * URL for requesting recipe data from FoodToFork
     */
    private String f2fRequestUrl = "http://food2fork.com/api/search?key=f0bcc213e09ecf97334084b8bc42b49a&q=";


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.example_fragment_local_recipe, container, false);
        // Find a reference to the {@link ListView} in the layout
        ListView f2fRecipesListView = view.findViewById(R.id.list);

        //Set an empty view
        mEmptyStateTextView = view.findViewById(R.id.empty_view);
        f2fRecipesListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of recipes as input
        f2fAdapter = new FoodToForkAdapter(getActivity(), new ArrayList<FoodToForkRecipe>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        f2fRecipesListView.setAdapter(f2fAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected recipe.
        f2fRecipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current recipe that was clicked on
                FoodToForkRecipe currentRecipe = f2fAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri f2fUri = Uri.parse(currentRecipe.getF2fUrl());

                // Create a new intent to view the recipe URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, f2fUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
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
    public android.support.v4.content.Loader<List<FoodToForkRecipe>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "onCreateLoader() called.");
        //Add here the method to get current recipe as this is the method to create recipe loader
        f2fRequestUrl += getCurrentRecipe();
        return new FoodToForkRecipeLoader(getContext(), f2fRequestUrl);
    }


    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<FoodToForkRecipe>> loader, List<FoodToForkRecipe> recipes) {
        //After the load is finished, we need to remove the progress bar
        avi = getView().findViewById(R.id.loading_spinner);
        avi.setVisibility(View.GONE);
        // Set empty state text
        mEmptyStateTextView.setText(R.string.no_recipes);
        Log.i(LOG_TAG, "onLoadFinished called.");
        // Clear the adapter of previous recipe data
        f2fAdapter.clear();

        // If there is a valid list of Recipes, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (recipes != null && !recipes.isEmpty()) {
            //can comment out this line to act as if there were no data on the server
            f2fAdapter.addAll(recipes);
        }
    }


    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<FoodToForkRecipe>> loader) {
        Log.i(LOG_TAG, "onLoaderReset called.");
        // Loader reset, so we can clear out our existing data.
        f2fAdapter.clear();
    }


    /**
     * This method acts as an option to getting the current recipe keyword from FireBase
     */
    private String getCurrentRecipe() {

        recipeSH.add("poached%20pears");
        recipeSH.add("parfait");
        recipeSH.add("apple%20pie");
        recipeSH.add("swiss%20roll");
        recipeSH.add("anchovy%20salad");
        recipeSH.add("herring");
        recipeSH.add("swedish%20pancake");
        recipeSH.add("crisp%20bread");
        recipeSH.add("veal%20burger");

        recipeSC.add("janssons%20temptation");
        recipeSC.add("meatballs");
        recipeSC.add("gravlax");
        recipeSC.add("meat%20patties");
        recipeSC.add("black%20pudding");
        recipeSC.add("beef%20soup");
        recipeSC.add("nettle%20soup");
        recipeSC.add("rice%20porridge");
        recipeSC.add("danish%20pastry");

        recipeSR.add("blueberry%20soup");
        recipeSR.add("cinnamon%20buns");
        recipeSR.add("saffron%20buns");
        recipeSR.add("yellow%20pea%20soup");
        recipeSR.add("crayfish");
        recipeSR.add("waffles");
        recipeSR.add("sausage%20and%20macaroni");

        recipeSW.add("chives%20and%20sour%20cream");
        recipeSW.add("peppermint%20candy");
        recipeSW.add("semla");
        recipeSW.add("chocolate%20ball");
        recipeSW.add("toast%20skagen");
        recipeSW.add("open%20sandwich");
        recipeSW.add("hasselback%20potatoes");

        recipeDH.add("dosa");
        recipeDH.add("momo");
        recipeDH.add("spring%20roll");
        recipeDH.add("ras%20malai");
        recipeDH.add("paneer");
        recipeDH.add("lassi");
        recipeDH.add("chaat");
        recipeDH.add("rajma");
        recipeDH.add("cold%20coffee");

        recipeDC.add("kofte");
        recipeDC.add("chole");
        recipeDC.add("butter%20chicken");
        recipeDC.add("tandoori%20naan");
        recipeDC.add("dal%20makhani");
        recipeDC.add("murgh%20mussallam");
        recipeDC.add("paratha");

        recipeDR.add("samosa");
        recipeDR.add("pakora");
        recipeDR.add("jalfrezi");
        recipeDR.add("korma");
        recipeDR.add("sambar");
        recipeDR.add("ladoo");
        recipeDR.add("baingan%20bharta");

        recipeDW.add("halwa");
        recipeDW.add("pastry");
        recipeDW.add("cake");
        recipeDW.add("kheer");
        recipeDW.add("aloo%20gobi");
        recipeDW.add("egg%20curry");

        WeatherService weatherService = new WeatherService(RecipeActivity.city, getContext());

        String currentRecipe = "soup";

        if (RecipeActivity.city.equals("Dehradun")) {
            switch (weatherService.getWeatherCondition()) {
                case "hot":
                    currentRecipe = recipeDH.get(RandomsGenerator.getRandomIntegerBetweenRange(0, 9));
                    break;

                case "cold":
                    currentRecipe = recipeDC.get(RandomsGenerator.getRandomIntegerBetweenRange(0, 7));
                    break;

                case "rainy":
                    currentRecipe = recipeDR.get(RandomsGenerator.getRandomIntegerBetweenRange(0, 7));
                    break;

                case "windy":
                    currentRecipe = recipeDW.get(RandomsGenerator.getRandomIntegerBetweenRange(0, 6));
                    break;
            }
        } else if (RecipeActivity.city.equals("stockholm")) {
            switch (weatherService.getWeatherCondition()) {
                case "hot":
                    currentRecipe = recipeSH.get(RandomsGenerator.getRandomIntegerBetweenRange(0, 9));
                    break;

                case "cold":
                    currentRecipe = recipeSC.get(RandomsGenerator.getRandomIntegerBetweenRange(0, 9));
                    break;

                case "rainy":
                    currentRecipe = recipeSR.get(RandomsGenerator.getRandomIntegerBetweenRange(0, 7));
                    break;

                case "windy":
                    currentRecipe = recipeSW.get(RandomsGenerator.getRandomIntegerBetweenRange(0, 6));
                    break;
            }
        }

        freeArrayListMemory();
        return currentRecipe;

    }

    private void freeArrayListMemory() {
        recipeDC.clear();
        recipeDH.clear();
        recipeDR.clear();
        recipeDW.clear();
        recipeSC.clear();
        recipeSR.clear();
        recipeSW.clear();
        recipeSH.clear();
    }
}