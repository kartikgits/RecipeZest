package com.northwindlabs.kartikeya.recipezest;

import android.provider.BaseColumns;

public final class RZestContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private RZestContract() {
    }

    /**
     * Inner class that defines constant values for the ingredients database table.
     * Each entry in the table represents a single recipe.
     */
    public static final class IngredientsTable implements BaseColumns {

        /**
         * Name of database table for ingredients
         */
        public final static String TABLE_NAME = "ingredients";

        /**
         * Unique ID number for the ingredient (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the ingredient.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_RECIPE_NAME = "recipeName";
    }

    /**
     * Inner class that defines constant values for the user-preferences database table.
     * Each entry in the table represents a single recipe.
     */
    public static final class UserPreferences implements BaseColumns {

        /**
         * Name of database table for user-preferences
         */
        public final static String TABLE_NAME = "user_preferences";

        /**
         * Unique ID number for the preferences (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the ingredient.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_PREFERENCE_NAME = "preferencesState";
    }

    /**
     * Inner class that defines constant values for the user database table.
     * Each entry in the table represents a single recipe.
     */
    public static final class UserFavorites implements BaseColumns {

        /**
         * Name of database table for user-preferences
         */
        public final static String TABLE_NAME = "user_favorites";

        /**
         * Unique ID number for the favorites (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the recipe.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_FAVORITE_RECIPE_NAME = "favoriteRecipe";

        /**
         * Url of the recipe.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_FAVORITE_RECIPE_URL = "recipeUrl";

        /**
         * Url of the recipe.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_FAVORITE_RECIPE_IMAGE = "recipeImage";

        /**
         * Url of the recipe.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_FAVORITE_RECIPE_PUBLISHER = "recipePublisher";
    }

}