package com.northwindlabs.kartikeya.recipezest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

import static com.northwindlabs.kartikeya.recipezest.RZestContract.UserPreferences.COLUMN_PREFERENCE_NAME;
import static com.northwindlabs.kartikeya.recipezest.RZestContract.UserPreferences.TABLE_NAME;
import static com.northwindlabs.kartikeya.recipezest.RZestContract.UserPreferences._ID;

public class SettingDietaryPreferences extends AppCompatActivity {

    private RecipeZestDBHelper mDbHelper;
    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dietary_prefernces);


        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        RecipeZestDBHelper mDbHelper = new RecipeZestDBHelper(this);

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

        Log.e("SettingDPreferences", preferencesArrayList.toString());
        boolean checkState;

        //Check for checkbox states and set/unset checkboxes
        CheckBox balancedCheckbox = findViewById(R.id.balanced);
        checkState = preferencesArrayList.contains("balanced");
        balancedCheckbox.setChecked(checkState);

        CheckBox highProteinCheckbox = findViewById(R.id.high_protein);
        checkState = preferencesArrayList.contains("high_protein");
        highProteinCheckbox.setChecked(checkState);

        CheckBox lowFatCheckbox = findViewById(R.id.low_fat);
        checkState = preferencesArrayList.contains("low_fat");
        lowFatCheckbox.setChecked(checkState);

        CheckBox lowCarbCheckbox = findViewById(R.id.low_carb);
        checkState = preferencesArrayList.contains("low_carb");
        lowCarbCheckbox.setChecked(checkState);

        CheckBox veganCheckbox = findViewById(R.id.vegan);
        checkState = preferencesArrayList.contains("vegan");
        veganCheckbox.setChecked(checkState);

        CheckBox vegetarianCheckbox = findViewById(R.id.vegetarian);
        checkState = preferencesArrayList.contains("vegetarian");
        vegetarianCheckbox.setChecked(checkState);

        CheckBox sugarConsciousCheckbox = findViewById(R.id.sugar_conscious);
        checkState = preferencesArrayList.contains("sugar_conscious");
        sugarConsciousCheckbox.setChecked(checkState);

        CheckBox peanutFreeCheckbox = findViewById(R.id.peanut_free);
        checkState = preferencesArrayList.contains("peanut_free");
        peanutFreeCheckbox.setChecked(checkState);

        CheckBox treenutFreeCheckbox = findViewById(R.id.treenut_free);
        checkState = preferencesArrayList.contains("treenut_free");
        treenutFreeCheckbox.setChecked(checkState);

        CheckBox alcoholCheckbox = findViewById(R.id.alcohal_free);
        checkState = preferencesArrayList.contains("alcohol_free");
        alcoholCheckbox.setChecked(checkState);

        //Detect checkbox state changes and apply corresponding changes in database
        balancedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addPreferenceToDatabase("balanced");
                } else {
                    removePreferenceFromDatabase("balanced");
                }

            }
        });

        //Detect checkbox state changes and apply corresponding changes in database
        highProteinCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addPreferenceToDatabase("high_protein");
                } else {
                    removePreferenceFromDatabase("high_protein");
                }

            }
        });

        //Detect checkbox state changes and apply corresponding changes in database
        lowCarbCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addPreferenceToDatabase("low_fat");
                } else {
                    removePreferenceFromDatabase("low_fat");
                }

            }
        });

        //Detect checkbox state changes and apply corresponding changes in database
        lowCarbCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addPreferenceToDatabase("low_carb");
                } else {
                    removePreferenceFromDatabase("low_carb");
                }

            }
        });

        //Detect checkbox state changes and apply corresponding changes in database
        veganCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addPreferenceToDatabase("vegan");
                } else {
                    removePreferenceFromDatabase("vegan");
                }

            }
        });

        //Detect checkbox state changes and apply corresponding changes in database
        vegetarianCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addPreferenceToDatabase("vegetarian");
                } else {
                    removePreferenceFromDatabase("vegetarian");
                }

            }
        });

        //Detect checkbox state changes and apply corresponding changes in database
        sugarConsciousCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addPreferenceToDatabase("sugar_conscious");
                } else {
                    removePreferenceFromDatabase("sugar_conscious");
                }

            }
        });

        //Detect checkbox state changes and apply corresponding changes in database
        peanutFreeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addPreferenceToDatabase("peanut_free");
                } else {
                    removePreferenceFromDatabase("peanut_free");
                }

            }
        });

        //Detect checkbox state changes and apply corresponding changes in database
        treenutFreeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addPreferenceToDatabase("treenut_free");
                } else {
                    removePreferenceFromDatabase("treenut_free");
                }

            }
        });

        //Detect checkbox state changes and apply corresponding changes in database
        alcoholCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addPreferenceToDatabase("alcohol_free");
                } else {
                    removePreferenceFromDatabase("alcohol_free");
                }

            }
        });

    }

    private void addPreferenceToDatabase(String value) {
        // Create database helper
        mDbHelper = new RecipeZestDBHelper(this);

        // Gets the database in write mode
        db = mDbHelper.getWritableDatabase();

        // Insert a new row for preference in the database, returning the ID of that new row.
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COLUMN_PREFERENCE_NAME + ") " + "VALUES ('" + value + "')");
    }

    private void removePreferenceFromDatabase(String value) {
        // Create database helper
        mDbHelper = new RecipeZestDBHelper(this);

        db = mDbHelper.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_PREFERENCE_NAME + "='" + value + "'");
    }

}
