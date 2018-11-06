package com.vyas.pranav.studentcompanion.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.Drawer;
import com.orhanobut.logger.Logger;
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

public class DashboardActivity extends AppCompatActivity implements ViewsUtils.OnCustomDrawerItemClickListener {

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
        fragmentManager = getSupportFragmentManager();
        // and set things in overall database ShowSubjectAppWidget.UpdateWidgetNow(this);
        drawer = ViewsUtils.buildNavigationDrawer(DashboardActivity.this, mToolbar);
        if (savedInstanceState != null) {
            Logger.d("Activity State Changed");
            Toast.makeText(this, "sdkfhajksdfsjkdfajdfkjasdfkajsdkfjslkdjflksjdfklsjadkfjalskjflkejakjflkadjlkasjdijaoe,aovoe io ei fwe iefiwief iefi we fwei fwef", Toast.LENGTH_LONG).show();
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
        //Logger.d("Adding Main Fragment to Root");
        fragmentManager.beginTransaction()
                .replace(R.id.frame_dashboard_container, attendanceMainFragment)
                .commit();
    }

    @Override
    public void onClickedDrawerItem(int identifier) {
        switch (identifier) {
            case ViewsUtils.ID_DASHBOARD_NAVIGATION:
                showMainAttendanceFragment();
                break;

            case ViewsUtils.ID_TIME_TABLE_NAVIGATION:
                TimetableMainFragment timetableFrag = new TimetableMainFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_dashboard_container, timetableFrag)
                        .commit();
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
                break;

            case ViewsUtils.ID_ABOUT_THIS_APP_NAVIGATION:
                AboutAppFragment aboutAppFrag = new AboutAppFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_dashboard_container, aboutAppFrag)
                        .commit();
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
        outState.putLong(Constances.SAVE_STATE_DASHBOARD_ACTVITY_DRAWER_ITEM, drawer.getCurrentSelection());
    }
}
