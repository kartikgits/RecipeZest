package com.northwindlabs.kartikeya.recipezest;

/**
 * Created by Kartikeya on 2/10/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        FontHelper.setCustomTypeface(findViewById(R.id.view_root));

        id = (TextView) findViewById(R.id.id);
        infoLabel = (TextView) findViewById(R.id.info_label);
        info = (TextView) findViewById(R.id.info);

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

