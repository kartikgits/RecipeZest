package com.northwindlabs.kartikeya.recipezest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kartikeya on 2/15/2018.
 */

/**
 * Fragment to display 'Local Recipes' Fragment
 */
public class LocalRecipesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.example_fragment_local_recipe, container, false);
    }
}
