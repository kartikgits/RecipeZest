package com.northwindlabs.kartikeya.recipezest;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Kartikeya
 */

public class FoodToForkAdapter extends ArrayAdapter<FoodToForkRecipe> {

    private Activity context;

    public FoodToForkAdapter(@NonNull Activity context, @NonNull ArrayList<FoodToForkRecipe> recipes) {
        super(context, 0, recipes);
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_list_item, parent, false);
        }

        FoodToForkRecipe currentRecipe = getItem(position);

        //Get the view id
        TextView recipeName = listItemView.findViewById(R.id.f2f_recipe_name);
        //Get the view data to be set
        String mRecipeName = currentRecipe.getTitle();
        //Set the data in the view
        recipeName.setText(mRecipeName);

        TextView publisherName = listItemView.findViewById(R.id.f2f_recipe_publisher);
        String mPublisherName = currentRecipe.getPublisher();
        publisherName.setText(mPublisherName);

        ImageView smallRecipeImage = listItemView.findViewById(R.id.f2f_recipe_image);
        String link = currentRecipe.getImageUrl();
        if (link != null && !TextUtils.isEmpty(link)) {
            //use Picasso API to handle downloading images
            Picasso.with(context).load(link).into(smallRecipeImage);
        } else {
            //if there is no image link retrieved from GoogleBooks API, use this image instead
            smallRecipeImage.setImageResource(R.drawable.no_recipe_image);
        }

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}
