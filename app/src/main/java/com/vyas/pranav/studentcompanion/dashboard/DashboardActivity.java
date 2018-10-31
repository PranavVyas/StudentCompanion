package com.vyas.pranav.studentcompanion.dashboard;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.extraUtils.ViewsUtils;
import com.vyas.pranav.studentcompanion.jobs.DailyAttendanceCreater;
import com.vyas.pranav.studentcompanion.overallAttandance.OverallAttendanceFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_dashboard)
    Toolbar mToolbar;
    @BindView(R.id.bottom_navigation_dashboard)
    BottomNavigationView mBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        showAttendanceFragment();
        ViewsUtils.buildNavigationDrawer(this,mToolbar);
        startJob();
    }

    public void startJob(){
        DailyAttendanceCreater.schedule();
    }

    public void showAttendanceFragment(){
        DashboardFragment dashboardFragment = new DashboardFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_dashboard_today_attendance,dashboardFragment)
                .commit();
    }

    public void showOverallAttendenceFragment(){
        OverallAttendanceFragment overallAttendanceFragment = new OverallAttendanceFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_dashboard_today_attendance,overallAttendanceFragment)
                .commit();
    }

    @OnClick(R.id.dashboard_bottomnav_today)
    public void clickedToday(){
        Toast.makeText(this, "Clicked Today", Toast.LENGTH_SHORT).show();
        mBottomNavigation.setSelectedItemId(R.id.dashboard_bottomnav_today);
        showAttendanceFragment();
    }

    @OnClick(R.id.dashboard_bottomnav_overall)
    public void clickedOverall(){
        Toast.makeText(this, "Clicked Overall", Toast.LENGTH_SHORT).show();
        mBottomNavigation.setSelectedItemId(R.id.dashboard_bottomnav_overall);
        showOverallAttendenceFragment();
    }
}
