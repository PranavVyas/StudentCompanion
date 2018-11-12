package com.vyas.pranav.studentcompanion.splash;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.dashboard.DashboardActivity;
import com.vyas.pranav.studentcompanion.data.SharedPrefsUtils;
import com.vyas.pranav.studentcompanion.firstrun.FirstRunActivity;
import com.vyas.pranav.studentcompanion.login.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Splash activity.
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * Redirect user to appropriate activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (SharedPrefsUtils.isFirstTimeRunActivity(this, FirstRunActivity.TAG)) {
            startActivity(new Intent(this, FirstRunActivity.class));
        } else {
            startActivity(new Intent(this, DashboardActivity.class));
        }
        finish();
    }
}
