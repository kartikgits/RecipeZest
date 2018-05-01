package com.northwindlabs.kartikeya.recipezest;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class EdamamIngredientListAdapter extends ArrayAdapter<String> {

    private Activity context;

    public EdamamIngredientListAdapter(@NonNull Activity context, @NonNull ArrayList<String> ingredients) {
        super(context, 0, ingredients);
        this.context = context;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_list_item, parent, false);
        }

        String currentIngredient = getItem(position);
        //Get the view id
        CheckBox ingredient = listItemView.findViewById(R.id.list_item_ingredient);
        //Get the view data to be set
        String ingredientData = currentIngredient;
        //Set the data in the view
        ingredient.setText(ingredientData);

        return listItemView;
    }

}
