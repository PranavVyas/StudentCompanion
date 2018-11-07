package com.vyas.pranav.studentcompanion.ui;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.dashboard.DashboardActivity;
import com.vyas.pranav.studentcompanion.holidays.HolidayFragment;
import com.vyas.pranav.studentcompanion.timetable.TimeTableFragment;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimetableMainFragment extends Fragment {


    @BindView(R.id.bottom_navigation_timetable)
    BottomNavigationView bottomNav;
    public static final int FRAG_HOLIDAYS_FRAG = 0;
    public static final int FRAG_TIMETABLE_FRAG = 1;
    OnTimeTableMainFragmentChangeListener mCallback;

    public TimetableMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable_main, container, false);
        ButterKnife.bind(this, view);
        timetableClicked();

        if (getArguments().getInt(DashboardActivity.KEY_SAVED_STATE_TIME_TABLE_MAIN_FRAG) == FRAG_TIMETABLE_FRAG) {
            timetableClicked();
        } else {
            holidayClicked();
        }

        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked}, // enabled
                new int[]{-android.R.attr.state_checked}, // disabled
        };

        int[] colors = new int[]{
                Color.BLACK,
                Color.GRAY
        };
        ColorStateList myList = new ColorStateList(states, colors);
        bottomNav.setItemIconTintList(myList);
        bottomNav.setItemTextColor(myList);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (OnTimeTableMainFragmentChangeListener) context;
    }

    @OnClick(R.id.timetable_bottomnav_holidays)
    public void holidayClicked() {
        HolidayFragment holidayFragment = new HolidayFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.frame_timetable_frag_container, holidayFragment)
                .commit();
        bottomNav.setSelectedItemId(R.id.timetable_bottomnav_holidays);
        mCallback.OnTimeTableMainFragmentChanged(FRAG_HOLIDAYS_FRAG);
    }

    @OnClick(R.id.timetable_bottomnav_timetable)
    public void timetableClicked() {
        TimeTableFragment timeTableFragment = new TimeTableFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.frame_timetable_frag_container, timeTableFragment)
                .commit();
        bottomNav.setSelectedItemId(R.id.timetable_bottomnav_timetable);
        mCallback.OnTimeTableMainFragmentChanged(FRAG_TIMETABLE_FRAG);
    }

    public interface OnTimeTableMainFragmentChangeListener {
        void OnTimeTableMainFragmentChanged(int currFrag);
    }

}
