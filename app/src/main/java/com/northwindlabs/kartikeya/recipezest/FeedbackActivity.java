package com.northwindlabs.kartikeya.recipezest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class FeedbackActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Set the content of the activity to use activity_menu.xml layout file */
        setContentView(R.layout.activity_feedback);

        /* Set the toolbar as the action bar */
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        /* Enable the app bar's "home" button */
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        /* Navigation drawer */
        mDrawerLayout = findViewById(R.id.drawer_layout);
        /**
         * When an item is tapped, this code sets the selected item as checked,
         * changing the list item's style to be highlighted because the list items
         * are part of a checkable group
         */
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        /* set item as selected to persist highlight */
                        menuItem.setChecked(false);

                        /**
                         * Add code here to update the UI based on the item selected.
                         * For example, swap UI fragments here
                         */
                        Intent intent = null;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                intent = new Intent(getBaseContext(), RecipeActivity.class);
                                startActivity(intent);
                                FeedbackActivity.this.onStop();
                                break;

                            case R.id.nav_myProfile:
                                intent = new Intent(getBaseContext(), AccountActivity.class);
                                startActivity(intent);
                                FeedbackActivity.this.onStop();
                                break;

                            case R.id.nav_shoppingList:
                                intent = new Intent(getBaseContext(), ShoppingListActivity.class);
                                startActivity(intent);
                                FeedbackActivity.this.onStop();
                                break;

                            case R.id.nav_myFavorite:
                                intent = new Intent(getBaseContext(), MyFavoriteActivity.class);
                                startActivity(intent);
                                FeedbackActivity.this.onStop();
                                break;

                            case R.id.nav_settings:
                                intent = new Intent(getBaseContext(), SettingsActivity.class);
                                startActivity(intent);
                                FeedbackActivity.this.onStop();
                                break;

                            case R.id.nav_aboutUs:
                                intent = new Intent(getBaseContext(), AboutUsActivity.class);
                                startActivity(intent);
                                FeedbackActivity.this.onStop();
                                break;

                            case R.id.nav_feedback:
                                break;

                            default:
                                Log.w("MenuActivity", "Unexpected Error handling menu item. Default case called");
                        }

                        /* close drawer when item is tapped */
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                }
        );

    }

    public void sendFeedback(View v) {
        EditText message = findViewById(R.id.message);
        String body = message.getText().toString();
        String[] recipient = {"nehapanwar26198@gmail.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Recipe Zest Feedback");
        intent.putExtra(Intent.EXTRA_EMAIL, recipient);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    //Open the drawer when the button is tapped
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /* pass GravityCompat.START as the open drawer animation gravity to openDrawer() */
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
