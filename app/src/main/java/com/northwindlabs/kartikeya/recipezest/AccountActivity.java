package com.northwindlabs.kartikeya.recipezest;

/**
 * Created by Kartikeya on 2/10/2018.
 */

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.login.LoginManager;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;


public class AccountActivity extends AppCompatActivity {

    TextView id;
    TextView infoLabel;
    TextView info;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        FontHelper.setCustomTypeface(findViewById(R.id.view_root));

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
                                AccountActivity.this.onStop();
                                break;

                            case R.id.nav_myProfile:
                                break;

                            case R.id.nav_shoppingList:
                                intent = new Intent(getBaseContext(), ShoppingListActivity.class);
                                startActivity(intent);
                                AccountActivity.this.onStop();
                                break;

                            case R.id.nav_myFavorite:
                                intent = new Intent(getBaseContext(), MyFavoriteActivity.class);
                                startActivity(intent);
                                AccountActivity.this.onStop();
                                break;

                            case R.id.nav_settings:
                                intent = new Intent(getBaseContext(), SettingsActivity.class);
                                startActivity(intent);
                                AccountActivity.this.onStop();
                                break;

                            case R.id.nav_aboutUs:
                                intent = new Intent(getBaseContext(), AboutUsActivity.class);
                                startActivity(intent);
                                AccountActivity.this.onStop();
                                break;

                            case R.id.nav_feedback:
                                intent = new Intent(getBaseContext(), FeedbackActivity.class);
                                startActivity(intent);
                                AccountActivity.this.onStop();
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

        id = findViewById(R.id.id);
        infoLabel = findViewById(R.id.info_label);
        info = findViewById(R.id.info);

        if (AccessToken.getCurrentAccessToken() != null) {
            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                Log.i("AccountActivity","Profile has data");
                String accountKitId = profile.getId();
                id.setText(accountKitId);
                String profileLinkUri = profile.getLinkUri().toString();
                info.setText(profileLinkUri);
                infoLabel.setText("Profile URL");
            }
            else {
                Log.i("AccountActivity","Fetching the profile");
                // Fetch the profile, which will trigger the onCurrentProfileChanged receiver
                Profile.fetchProfileForCurrentAccessToken();
                ProfileTracker profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        this.stopTracking();
                        Profile.setCurrentProfile(currentProfile);
                        Profile profile = Profile.getCurrentProfile();
                        Log.i("AccountActivity","Profile has data");
                        String accountKitId = profile.getId();
                        id.setText(accountKitId);
                        String profileLinkUri = profile.getLinkUri().toString();
                        info.setText(profileLinkUri);
                        infoLabel.setText("Profile URL");
                    }
                };
            }
        } else {
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(final Account account) {
                    // in the account success, an 'account' is being passed in,
                    // and that is where we'll grab the whole account info.

                    //Get Account Kit ID
                    String accountKitId = account.getId();
                    id.setText(accountKitId);

                    PhoneNumber phonenumber = account.getPhoneNumber();
                    if (phonenumber != null){
                        // if pno is available, display it
                        String formattedPhoneNumber = formatPhoneNumber(phonenumber.toString());
                        info.setText(formattedPhoneNumber);
                        infoLabel.setText(R.string.phone_label);
                    } else {
                        // if pno is not available that means email is available, display email
                        String emailString = account.getEmail();
                        info.setText(emailString);
                        infoLabel.setText(R.string.email_label);
                    }
                }

                @Override
                public void onError(final AccountKitError accountKitError) {
                    Log.e("onError Called", "Internal inconsistency error.");
                    // display error
                    String errorMessage = accountKitError.getErrorType().getMessage();
                    Toast.makeText(AccountActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void onLogout(View view){
        // logout of Account Kit
        AccountKit.logOut();
        // logout of Login button
        LoginManager.getInstance().logOut();

        launchLoginActivity();
    }

    private void launchLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private String formatPhoneNumber(String phoneNumber) {
        // helper method to format the phone number for display
        try {
            PhoneNumberUtil pnu = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber pn = pnu.parse(phoneNumber, Locale.getDefault().getCountry());
            phoneNumber = pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
        }
        catch (NumberParseException e) {
            e.printStackTrace();
        }
        return phoneNumber;
    }

    public void startRecipeActivity(View view){
        // start Recipe Activity through intent
        Intent intent = new Intent(AccountActivity.this, RecipeActivity.class);
        startActivity(intent);
    }

}

