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
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + RZestContract.IngredientsTable.TABLE_NAME + " ("
                + RZestContract.IngredientsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RZestContract.IngredientsTable.COLUMN_RECIPE_NAME + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_INGREDIENTS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
