package com.vyas.pranav.studentcompanion.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.Drawer;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.aboutapp.AboutAppFragment;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.ViewsUtils;
import com.vyas.pranav.studentcompanion.prefences.AppSettingsFragment;
import com.vyas.pranav.studentcompanion.ui.AttendanceMainFragment;
import com.vyas.pranav.studentcompanion.ui.LoginActivity;
import com.vyas.pranav.studentcompanion.ui.TimetableMainFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements ViewsUtils.OnCustomDrawerItemClickListener, TimetableMainFragment.OnTimeTableMainFragmentChangeListener, AttendanceMainFragment.OnAttendanceMainFragmentChangeListener {

    public static final String KEY_SAVED_STATE_TIME_TABLE_MAIN_FRAG = "CurrentSavedStateOfTimeTableFragment";
    public static final String KEY_SAVED_STATE_ATTENDNACE_MAIN_FRAG = "CurrentSavedStateOfTimeTableFragment";
    private static final String SAVE_TIMETABLE_FRAG = "TimeTableMainFragState";
    private static final String SAVE_ATTENDANCE_FRAG = "AttendanceMainFragState";
    int CURR_FRAG_TIMETABLE = TimetableMainFragment.FRAG_TIMETABLE_FRAG;
    int CURR_FRAG_ATTENDANCE = AttendanceMainFragment.ATTENDANCE_FRAGMENT;

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    Drawer drawer;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        fragmentManager = getSupportFragmentManager();
        // and set things in overall database ShowSubjectAppWidget.UpdateWidgetNow(this);
        drawer = ViewsUtils.buildNavigationDrawer(DashboardActivity.this, mToolbar);
        if (savedInstanceState != null) {
            CURR_FRAG_TIMETABLE = savedInstanceState.getInt(SAVE_TIMETABLE_FRAG);
            CURR_FRAG_ATTENDANCE = savedInstanceState.getInt(SAVE_ATTENDANCE_FRAG);
            onClickedDrawerItem((int) savedInstanceState.getLong(Constances.SAVE_STATE_DASHBOARD_ACTVITY_DRAWER_ITEM));
            drawer.setSelection(savedInstanceState.getLong(Constances.SAVE_STATE_DASHBOARD_ACTVITY_DRAWER_ITEM));
        } else {
            showMainAttendanceFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

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

    @Override
    public void onClickedDrawerItem(int identifier) {
        switch (identifier) {
            case ViewsUtils.ID_DASHBOARD_NAVIGATION:
                showMainAttendanceFragment();
                break;

            case ViewsUtils.ID_TIME_TABLE_NAVIGATION:
                TimetableMainFragment timetableFrag = new TimetableMainFragment();
                Bundle currState = new Bundle();
                currState.putInt(KEY_SAVED_STATE_TIME_TABLE_MAIN_FRAG, CURR_FRAG_TIMETABLE);
                timetableFrag.setArguments(currState);
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_dashboard_container, timetableFrag)
                        .commit();
                mToolbar.setTitle("Time Table");
                break;

            case ViewsUtils.ID_SHARE_APP_NAVIGATION:
                Toast.makeText(this, "Share Clicked", Toast.LENGTH_SHORT).show();
                break;

            //TODO Implement Share feature here
            case ViewsUtils.ID_PREFERENCE_NAVIGATION:
                AppSettingsFragment settingsFrag = new AppSettingsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_dashboard_container, settingsFrag)
                        .commit();
                mToolbar.setTitle("Settings");
                break;

            case ViewsUtils.ID_ABOUT_THIS_APP_NAVIGATION:
                AboutAppFragment aboutAppFrag = new AboutAppFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_dashboard_container, aboutAppFrag)
                        .commit();
                mToolbar.setTitle("About this App");
                break;

            case ViewsUtils.ID_LOG_OUT_APP_NAVIGATION:
                AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(DashboardActivity.this, "Sucessfully Signed Out", Toast.LENGTH_SHORT).show();
                        Intent startAppAgain = new Intent(DashboardActivity.this, LoginActivity.class);
                        startActivity(startAppAgain);
                        DashboardActivity.this.finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DashboardActivity.this, "Error Occured while Signing Out Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_TIMETABLE_FRAG, CURR_FRAG_TIMETABLE);
        outState.putInt(SAVE_ATTENDANCE_FRAG, CURR_FRAG_ATTENDANCE);
        outState.putLong(Constances.SAVE_STATE_DASHBOARD_ACTVITY_DRAWER_ITEM, drawer.getCurrentSelection());
    }

    @Override
    public void OnTimeTableMainFragmentChanged(int currFrag) {
        CURR_FRAG_TIMETABLE = currFrag;
        if (currFrag == TimetableMainFragment.FRAG_TIMETABLE_FRAG) {
            mToolbar.setTitle("Time Table");
        } else {
            mToolbar.setTitle("Holidays");
        }
    }

    @Override
    public void OnAttendanceMainFragmentChanged(int currFrag) {
        CURR_FRAG_ATTENDANCE = currFrag;
        if (currFrag == AttendanceMainFragment.ATTENDANCE_FRAGMENT) {
            mToolbar.setTitle(R.string.app_name);
        } else {
            mToolbar.setTitle("Overall Attendance");
        }
    }
}
