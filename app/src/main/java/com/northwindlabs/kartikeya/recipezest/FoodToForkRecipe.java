package com.northwindlabs.kartikeya.recipezest;

/**
 * Stores data for recipes loaded from Food2Fork
 */

public class FoodToForkRecipe {

    private String title;
    private String f2fUrl;
    private String imageUrl;
    private String recipeId;
    private String publisher;
    private String sourceSite;

    public FoodToForkRecipe(String mTitle, String mF2fUrl, String mImageUrl, String mRecipeId, String mPublisher) {
        title = mTitle;
        f2fUrl = mF2fUrl;
        imageUrl = mImageUrl;
        recipeId = mRecipeId;
        publisher = mPublisher;
        sourceSite = "f2f";
    }

    public String getTitle() {
        return title;
    }

    public String getF2fUrl() {
        return f2fUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSourceSite() {
        return sourceSite;
    }

}
