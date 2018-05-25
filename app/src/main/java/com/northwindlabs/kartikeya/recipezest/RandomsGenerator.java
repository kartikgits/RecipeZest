package com.northwindlabs.kartikeya.recipezest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import static com.northwindlabs.kartikeya.recipezest.RZestContract.UserPreferences.COLUMN_PREFERENCE_NAME;
import static com.northwindlabs.kartikeya.recipezest.RZestContract.UserPreferences.TABLE_NAME;

final class RandomsGenerator {

    private RandomsGenerator() {
    }

    public static int getRandomIntegerBetweenRange(double min, double max) {
        int x = (int) ((int) (Math.random() * (max - min)) + min);
        return x;
    }

    public static String getRandomAlphabet() {
        Random rnd = new Random();
        char c = (char) (rnd.nextInt(26) + 'a');
        return String.valueOf(c);
    }

    public static String getRandomDHLabel(Context context) {
        // To access our database, we instantiate our subclass of RecipeZestDBHelper
        // and pass the context, which is the current activity.
        RecipeZestDBHelper mDbHelper = new RecipeZestDBHelper(context);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM preferences_table_name"
        // to get a Cursor that contains all rows from the ingredients table.
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        final ArrayList<String> preferencesArrayList = new ArrayList<>();
        int columnIndex = mCursor.getColumnIndex(COLUMN_PREFERENCE_NAME);
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            // The Cursor is now set to the right position
            preferencesArrayList.add(mCursor.getString(columnIndex));
        }
        //Invalidating the cursor, hence, releasing the resources.
        mCursor.close();

        String currentDHLabel = null;
        if (preferencesArrayList.size() != 0) {
            Random randomGenerator = new Random();
            int index = randomGenerator.nextInt(preferencesArrayList.size());
            currentDHLabel = preferencesArrayList.get(index);
            return currentDHLabel;
        } else {
            currentDHLabel = "no-label";
        }
        return currentDHLabel;
    }

}