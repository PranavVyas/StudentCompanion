package com.vyas.pranav.studentcompanion.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.dashboard.DashboardFragment;
import com.vyas.pranav.studentcompanion.overallAttandance.OverallAttendanceFragment;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceMainFragment extends Fragment {

    @BindView(R.id.bottom_navigation_dashboard)
    BottomNavigationView mBottomNavigation;

    public AttendanceMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_main, container, false);
        ButterKnife.bind(this, view);
        showAttendanceFragment();
        //startJobIfNotStarted();
        return view;
    }

    //public void startJobIfNotStarted() {
    //if (!JobManager.instance().getAllJobRequestsForTag(DailyAttendanceCreater.TAG).isEmpty()) {
    //    Logger.d("Job is running already...Skipping Setting...");
    //    return;
    //}
    //DailyAttendanceCreater.schedule(0, 10, 0, 11);
    //}

    public void showAttendanceFragment() {
        DashboardFragment dashboardFragment = new DashboardFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.frame_dashboard_today_attendance, dashboardFragment)
                .commit();
    }

    public void showOverallAttendanceFragment() {
        OverallAttendanceFragment overallAttendanceFragment = new OverallAttendanceFragment();
        getChildFragmentManager().beginTransaction()
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
