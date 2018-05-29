package com.northwindlabs.kartikeya.recipezest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipeZestDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RecipeZest.db";

    public RecipeZestDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the ingredients table
        String SQL_CREATE_INGREDIENTS_TABLE = "CREATE TABLE IF NOT EXISTS " + RZestContract.IngredientsTable.TABLE_NAME + " ("
                + RZestContract.IngredientsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RZestContract.IngredientsTable.COLUMN_RECIPE_NAME + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_INGREDIENTS_TABLE);


        // Create a String that contains the SQL statement to create the ingredients table
        String SQL_CREATE_PREFERENCES_TABLE = "CREATE TABLE IF NOT EXISTS " + RZestContract.UserPreferences.TABLE_NAME + " ("
                + RZestContract.UserPreferences._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RZestContract.UserPreferences.COLUMN_PREFERENCE_NAME + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_PREFERENCES_TABLE);

        // Create a String that contains the SQL statement to create the favorites table
        String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE IF NOT EXISTS " + RZestContract.UserFavorites.TABLE_NAME + " ("
                + RZestContract.UserFavorites._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RZestContract.UserFavorites.COLUMN_FAVORITE_RECIPE_NAME + " TEXT NOT NULL, "
                + RZestContract.UserFavorites.COLUMN_FAVORITE_RECIPE_IMAGE + " TEXT NOT NULL, "
                + RZestContract.UserFavorites.COLUMN_FAVORITE_RECIPE_URL + " TEXT NOT NULL, "
                + RZestContract.UserFavorites.COLUMN_FAVORITE_RECIPE_PUBLISHER + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
