package com.northwindlabs.kartikeya.recipezest;

/**
 * Created by Kartikeya on 2/10/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class LoginActivity extends AppCompatActivity {

    public static int APP_REQUEST_CODE = 1;
    LoginButton fbLoginButton;
    // CallbackManager will help in handling the result of login attempt through facebook
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FontHelper.setCustomTypeface(findViewById(R.id.view_root));

        fbLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        fbLoginButton.setReadPermissions("email");

        // Login Button callback registration
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                launchAccountActivity();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                // display Error
                String errorMessage = error.getMessage();
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        // check for an existing token i.e. if a user is already logged in
        AccessToken accessToken = AccountKit.getCurrentAccessToken();
        com.facebook.AccessToken loginToken = com.facebook.AccessToken.getCurrentAccessToken();
        if (accessToken != null || loginToken != null){
            // if previously logged in, proceed to account activity
            launchAccountActivity();
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        // Forward result of login attempt to the callback manager for Login Button
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // confirm that this response matches your request
        if (requestCode == APP_REQUEST_CODE){
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (loginResult.getError() != null){
                // display login error
                String toastMessage = loginResult.getError().getErrorType().getMessage();
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
            } else if (loginResult.getAccessToken() != null){
                // on successful login, proceed to the account activity
                launchAccountActivity();
            }
        }
    }


    /**
     * The function onLogin() will handle what happens during the login.
     * This function will implement the token request.
     */
    private void onLogin(final LoginType loginType){
        // create intent for the Account Kit Activity
        final Intent intent = new Intent(this, AccountKitActivity.class);

        // configure login type and response type
        AccountKitConfiguration.AccountKitConfigurationBuilder accountKitConfigurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        loginType,
                        AccountKitActivity.ResponseType.TOKEN
                );

        final AccountKitConfiguration configuration = accountKitConfigurationBuilder.build();
        // launch the Account Kit Activity
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configuration);
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    // Function call if using phone
    public void onPhoneLogin(View view){
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("onSMSLogin");
        onLogin(LoginType.PHONE);
    }

    // Function call if using email
    public void onEmailLogin(View view){
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("onEmailLogin");
        onLogin(LoginType.EMAIL);
    }

    /**
     * The function launchAccountActivity() will hand over the control to AccountActivity.class
     * once we are done with login.
     */
    private void launchAccountActivity() {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
        finish(); //Finishing the current Activity
    }

}

