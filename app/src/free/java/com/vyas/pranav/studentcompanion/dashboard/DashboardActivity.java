package com.vyas.pranav.studentcompanion.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.Drawer;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.aboutapp.AboutAppFragment;
import com.vyas.pranav.studentcompanion.data.SharedPrefsUtils;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.ViewsUtils;
import com.vyas.pranav.studentcompanion.login.LoginActivity;
import com.vyas.pranav.studentcompanion.prefences.AppSettingsFragment;
import com.vyas.pranav.studentcompanion.timetable.TimetableMainFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * Very first activity that will be called after all fetching is done
 * Contains all the main fragments*/
public class DashboardActivity extends AppCompatActivity implements ViewsUtils.OnCustomDrawerItemClickListener, TimetableMainFragment.OnTimeTableMainFragmentChangeListener, AttendanceMainFragment.OnAttendanceMainFragmentChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

    //For Saving state
    public static final String KEY_SAVED_STATE_TIME_TABLE_MAIN_FRAG = "CurrentSavedStateOfTimeTableFragment";
    public static final String KEY_SAVED_STATE_ATTENDNACE_MAIN_FRAG = "CurrentSavedStateOfTimeTableFragment";
    private static final String SAVE_TIMETABLE_FRAG = "TimeTableMainFragState";
    private static final String SAVE_ATTENDANCE_FRAG = "AttendanceMainFragState";
    //for holding values of both timetableMain and attendanceMain fragment
    int CURR_FRAG_TIMETABLE = TimetableMainFragment.FRAG_TIMETABLE_FRAG;
    int CURR_FRAG_ATTENDANCE = AttendanceMainFragment.ATTENDANCE_FRAGMENT;

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.ad_dashboard_main)
    AdView adMain;
    SharedPreferences mPrefs;
    private Drawer drawer;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPrefsUtils.setThemeOfUser(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        fragmentManager = getSupportFragmentManager();
        drawer = ViewsUtils.buildNavigationDrawer(DashboardActivity.this, mToolbar);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (savedInstanceState != null) {
            CURR_FRAG_TIMETABLE = savedInstanceState.getInt(SAVE_TIMETABLE_FRAG);
            CURR_FRAG_ATTENDANCE = savedInstanceState.getInt(SAVE_ATTENDANCE_FRAG);
            onClickedDrawerItem((int) savedInstanceState.getLong(Constances.SAVE_STATE_DASHBOARD_ACTVITY_DRAWER_ITEM));
            drawer.setSelection(savedInstanceState.getLong(Constances.SAVE_STATE_DASHBOARD_ACTVITY_DRAWER_ITEM));
        } else {
            showMainAttendanceFragment();
        }
        MobileAds.initialize(this, getString(R.string.banner_ad_unit_id));
        AdRequest request = new AdRequest.Builder()
                .addTestDevice("57C0017BCFBFA5603DF4520A562C5B19")
                .build();
        adMain.loadAd(request);
    }

    //Show main fragment as attendanceMainFragment
    public void showMainAttendanceFragment() {
        AttendanceMainFragment attendanceMainFragment = new AttendanceMainFragment();
        Bundle currState = new Bundle();
        currState.putInt(KEY_SAVED_STATE_ATTENDNACE_MAIN_FRAG, CURR_FRAG_ATTENDANCE);
        attendanceMainFragment.setArguments(currState);
        //Logger.d("Adding Main Fragment to Root");
        fragmentManager.beginTransaction()
                .replace(R.id.frame_dashboard_container, attendanceMainFragment)
                .commit();
        mToolbar.setTitle(R.string.app_name);
    }

    //Callback from Drawer, when An item in drawer is clicked change the fragment accordingly
    @Override
    public void onClickedDrawerItem(int identifier) {
        switch (identifier) {
            case ViewsUtils.ID_DASHBOARD_NAVIGATION:
                showMainAttendanceFragment();
                break;

            case ViewsUtils.ID_TIME_TABLE_NAVIGATION:
                TimetableMainFragment timetableFrag = new TimetableMainFragment();
                Bundle currState = new Bundle();
                //Send data to timeTableMainFragment to indicate last saved state before rotation of device
                currState.putInt(KEY_SAVED_STATE_TIME_TABLE_MAIN_FRAG, CURR_FRAG_TIMETABLE);
                timetableFrag.setArguments(currState);
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_dashboard_container, timetableFrag)
                        .commit();
                mToolbar.setTitle(R.string.action_time_table_navigation);
                break;

            case ViewsUtils.ID_SHARE_APP_NAVIGATION:
                //Toast.makeText(this, "Share Clicked", Toast.LENGTH_SHORT).show();
                shareApp();
                break;

            case ViewsUtils.ID_PREFERENCE_NAVIGATION:
                AppSettingsFragment settingsFrag = new AppSettingsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_dashboard_container, settingsFrag)
                        .commit();
                mToolbar.setTitle(R.string.action_preference_navigation);
                break;

            case ViewsUtils.ID_ABOUT_THIS_APP_NAVIGATION:
                AboutAppFragment aboutAppFrag = new AboutAppFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_dashboard_container, aboutAppFrag)
                        .commit();
                mToolbar.setTitle(R.string.action_about_app_navigation);
                break;

            case ViewsUtils.ID_LOG_OUT_APP_NAVIGATION:
                AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //User successfully signed out
                        Toast.makeText(DashboardActivity.this, "Sucessfully Signed Out", Toast.LENGTH_SHORT).show();
                        Intent startAppAgain = new Intent(DashboardActivity.this, LoginActivity.class);
                        startActivity(startAppAgain);
                        DashboardActivity.this.finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //There was some error while signing out
                        Toast.makeText(DashboardActivity.this, "Error Occured while Signing Out Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    /**
     * Sharing App url
     */
    private void shareApp() {
        String url = "<url-will-come-here>";
        String text = "Hey ! I am  using this app to track my attendance,\nYou can also get it for free from this link \n" + url;
        Intent shareApp = new Intent(Intent.ACTION_SEND);
        shareApp.setType("text/plain");
        shareApp.putExtra(Intent.EXTRA_SUBJECT, "Checkout this awesome attendance app");
        shareApp.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareApp, "Share Using..."));
    }


    /*
     * Overriding back button :
     * If drawer is opened than close it first
     * than Move to dashboard activity if not already on that activity
     * finally close app */
    @Override
    public void onBackPressed() {
        if (drawer != null) {
            if (FirebaseAuth.getInstance()
                    .getCurrentUser() != null && drawer.isDrawerOpen()) {
                drawer.closeDrawer();
            } else if (drawer.getCurrentSelectedPosition() == 1) {
                super.onBackPressed();
            } else {
                drawer.setSelection(ViewsUtils.ID_DASHBOARD_NAVIGATION);
            }
        } else {
            super.onBackPressed();
        }
    }

    /*Saving state in bundle to retrieve it afterwards*/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_TIMETABLE_FRAG, CURR_FRAG_TIMETABLE);
        outState.putInt(SAVE_ATTENDANCE_FRAG, CURR_FRAG_ATTENDANCE);
        outState.putLong(Constances.SAVE_STATE_DASHBOARD_ACTVITY_DRAWER_ITEM, drawer.getCurrentSelection());
    }

    /*
     * Callback from timetableMainFragment means the fragment is changed in the timetableFragmentMain
     * either to holiday or to timetable*/
    @Override
    public void OnTimeTableMainFragmentChanged(int currFrag) {
        CURR_FRAG_TIMETABLE = currFrag;
        if (currFrag == TimetableMainFragment.FRAG_TIMETABLE_FRAG) {
            mToolbar.setTitle("Time Table");
        } else {
            mToolbar.setTitle("Holidays");
        }
    }

    /*
     * Callback from attendanceMain means the fragment is changed in the attendanceMain
     * either to today or to overall*/
    @Override
    public void OnAttendanceMainFragmentChanged(int currFrag) {
        CURR_FRAG_ATTENDANCE = currFrag;
        if (currFrag == AttendanceMainFragment.ATTENDANCE_FRAGMENT) {
            mToolbar.setTitle(R.string.app_name);
        } else {
            mToolbar.setTitle("Overall Attendance");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPrefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPrefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(getString(R.string.pref_switch_key_dark_theme))) {
            this.recreate();
        }
    }
}
