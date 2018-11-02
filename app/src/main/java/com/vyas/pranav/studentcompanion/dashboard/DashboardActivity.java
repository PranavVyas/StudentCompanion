package com.vyas.pranav.studentcompanion.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.evernote.android.job.JobManager;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.extraUtils.ViewsUtils;
import com.vyas.pranav.studentcompanion.jobs.DailyAttendanceCreater;
import com.vyas.pranav.studentcompanion.overallAttandance.OverallAttendanceFragment;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity {

    public static final int REQUEST_SIGN_IN_KEY = 1023;
    @BindView(R.id.toolbar_dashboard)
    Toolbar mToolbar;
    @BindView(R.id.bottom_navigation_dashboard)
    BottomNavigationView mBottomNavigation;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
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
        } else {
            setSupportActionBar(mToolbar);
            showAttendanceFragment();
            startJobIfNotStarted();
            ViewsUtils.buildNavigationDrawer(DashboardActivity.this, mToolbar);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SIGN_IN_KEY) {
            if (resultCode == RESULT_OK) {
                FirebaseUser user = mAuth.getCurrentUser();
                ViewsUtils.buildNavigationDrawer(DashboardActivity.this, mToolbar);
                Toast.makeText(this, "Sucessfully Signed in as " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                this.finish();
            } else {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    Toast.makeText(this, "Result is null", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void startJobIfNotStarted() {
        if (!JobManager.instance().getAllJobRequestsForTag(DailyAttendanceCreater.TAG).isEmpty()) {
            Logger.d("Job is running already...Skipping Setting...");
            return;
        }
        DailyAttendanceCreater.schedule(0, 10, 0, 11);
    }

    public void showAttendanceFragment() {
        DashboardFragment dashboardFragment = new DashboardFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_dashboard_today_attendance, dashboardFragment)
                .commit();
    }

    public void showOverallAttendanceFragment() {
        OverallAttendanceFragment overallAttendanceFragment = new OverallAttendanceFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_dashboard_today_attendance, overallAttendanceFragment)
                .commit();
    }

    @OnClick(R.id.dashboard_bottomnav_today)
    public void clickedToday() {
        //Toast.makeText(this, "Clicked Today", Toast.LENGTH_SHORT).show();
        mBottomNavigation.setSelectedItemId(R.id.dashboard_bottomnav_today);
        showAttendanceFragment();
    }

    @OnClick(R.id.dashboard_bottomnav_overall)
    public void clickedOverall() {
        //Toast.makeText(this, "Clicked Overall", Toast.LENGTH_SHORT).show();
        mBottomNavigation.setSelectedItemId(R.id.dashboard_bottomnav_overall);
        showOverallAttendanceFragment();
    }
}
