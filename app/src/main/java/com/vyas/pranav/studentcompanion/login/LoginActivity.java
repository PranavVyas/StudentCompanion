package com.vyas.pranav.studentcompanion.login;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.dashboard.DashboardActivity;
import com.vyas.pranav.studentcompanion.data.SharedPrefsUtils;
import com.vyas.pranav.studentcompanion.firstrun.FirstRunActivity;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    public static final int REQUEST_SIGN_IN_KEY = 1023;

    private FirebaseAuth mAuth;

    @BindView(R.id.toolbar_login)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPrefsUtils.setThemeOfUser(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        setSupportActionBar(mToolbar);
        //If User is not logged in Start new Activity to Log in or Sign up
        if (mAuth.getCurrentUser() == null) {
            //User is not logged In
            logIn();
        } else {
            /* User is already logged in
             * Proceed to check if user has completed initial setup or not if not than redirect to the setup activity
             *  If finished setup start the DashboardActivity Now
             *  In both cases finish this activity
             */
            if (SharedPrefsUtils.isFirstTimeRunActivity(this, FirstRunActivity.TAG)) {
                //App Starting For First Time or User has not yet completed set up
                //Send user to complete registration first
                Intent startRegistration = new Intent(this, FirstRunActivity.class);
                startActivity(startRegistration);
                finish();
            } else {
                Intent startDashboard = new Intent(this, DashboardActivity.class);
                startActivity(startDashboard);
                finish();
            }
        }

    }

    //Result of activity created to sign in user
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SIGN_IN_KEY) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            // Successfully signed in
            if (resultCode == RESULT_OK) {
                //Recreate this activity in order to check if the user has completed initial setup
                this.recreate();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar(getString(R.string.java_login_sign_in_cancelled));
                    //Finish the Activity as user has declined to sign in
                    //this.finish();
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    //No Internet Connection
                    showSnackbar(getString(R.string.java_login_no_connection));
                    return;
                }

                showSnackbar(getString(R.string.java_login_default_error));
                Logger.d("Sign-in error: " + response.getError());
            }
        }
    }

    //Helper method to show Snackbar
    public void showSnackbar(String message) {
        Snackbar.make(mToolbar, message, Snackbar.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_login_login)
    public void logIn() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );
        startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .setLogo(R.drawable.ic_launcher_foreground)
                        .build()
                , REQUEST_SIGN_IN_KEY);
    }
}
