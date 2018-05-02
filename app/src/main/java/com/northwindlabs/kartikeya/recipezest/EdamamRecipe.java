package com.northwindlabs.kartikeya.recipezest;

import android.widget.CheckBox;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class implements Serializable interface because we want an object
 * of this class to be passed as an intent from Activity X to Activity Y.
 */

public class EdamamRecipe implements Serializable {

    private String title;
    private String imageUrl;
    private String source;
    private String url;
    private int calories;
    private int totalTime;
    private ArrayList<String> ingredients;
    private int ingredientsArrayListSize;

    public EdamamRecipe(String title, String imageUrl, String source, String url, int calories, int totalTime, ArrayList<String> ingredients, int ingredientsArrayListSize) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.source = source;
        this.url = url;
        this.calories = calories;
        this.totalTime = totalTime;
        this.ingredients = ingredients;
        this.ingredientsArrayListSize = ingredientsArrayListSize;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }

    public String getCalories() {
        return "" + calories + " KCal";
    }

    public String getTotalTime() {
        return "" + totalTime + " Minutes";
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public int getIngredientsArrayListSize() {
        return ingredientsArrayListSize;
    }
}
