package com.vyas.pranav.studentcompanion.dashboard;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.overallAttandance.OverallAttendanceFragment;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Main Fragment responsible to show attendance fragment as well as overall fragment
 */
public class AttendanceMainFragment extends Fragment {

    private static final String KEY_SAVED_STATE_BOTTOM = "ExitState";
    @BindView(R.id.bottom_navigation_dashboard)
    BottomNavigationView mBottomNavigation;
    //constances for ease of comparing
    public static final int ATTENDANCE_FRAGMENT = 0;
    public static final int OVERALL_FRAGMENT = 1;
    //To store the current opened fragment
    private int currentFragment = ATTENDANCE_FRAGMENT;
    //send the stored fragment posititon to dashboard activity for saving instance while rotation
    private OnAttendanceMainFragmentChangeListener mCallback;

    public AttendanceMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_main, container, false);
        ButterKnife.bind(this, view);
        //Check if the parent activity sent the rotation saved data through bundle
        //If send than change according to that
        if (getArguments().getInt(DashboardActivity.KEY_SAVED_STATE_ATTENDNACE_MAIN_FRAG) == ATTENDANCE_FRAGMENT) {
            clickedToday();
        } else {
            clickedOverall();
        }
        setupBottomNavigationBar();
        return view;
    }

    //To set colors of bottom navigation bar programatically
    private void setupBottomNavigationBar() {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked}, // enabled
                new int[]{-android.R.attr.state_checked}, // disabled
        };
        int[] colors = new int[]{
                Color.BLACK,
                Color.GRAY
        };
        ColorStateList myList = new ColorStateList(states, colors);
        mBottomNavigation.setItemIconTintList(myList);
        mBottomNavigation.setItemTextColor(myList);
    }

    //Showing Attendance Fragment
    private void showAttendanceFragment() {
        DashboardFragment dashboardFragment = new DashboardFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.frame_dashboard_today_attendance, dashboardFragment)
                .commit();
        currentFragment = ATTENDANCE_FRAGMENT;
        mCallback.OnAttendanceMainFragmentChanged(currentFragment);
    }

    /*Showing Overall Fragment*/
    private void showOverallAttendanceFragment() {
        OverallAttendanceFragment overallAttendanceFragment = new OverallAttendanceFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.frame_dashboard_today_attendance, overallAttendanceFragment)
                .commit();
        currentFragment = OVERALL_FRAGMENT;
        mCallback.OnAttendanceMainFragmentChanged(currentFragment);
    }

    @OnClick(R.id.dashboard_bottomnav_today)
    void clickedToday() {
        Toast.makeText(getContext(), "Clicked Today", Toast.LENGTH_SHORT).show();
        mBottomNavigation.setSelectedItemId(R.id.dashboard_bottomnav_today);
        showAttendanceFragment();
    }

    @OnClick(R.id.dashboard_bottomnav_overall)
    void clickedOverall() {
        Toast.makeText(getContext(), "Clicked Overall", Toast.LENGTH_SHORT).show();
        mBottomNavigation.setSelectedItemId(R.id.dashboard_bottomnav_overall);
        showOverallAttendanceFragment();
    }

    /*For setting callback to dashboard activity*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (OnAttendanceMainFragmentChangeListener) context;
    }

    public interface OnAttendanceMainFragmentChangeListener {
        void OnAttendanceMainFragmentChanged(int currFrag);
    }
}
