package com.vyas.pranav.studentcompanion.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.holidays.HolidayFragment;
import com.vyas.pranav.studentcompanion.timetable.TimeTableFragment;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimetableMainFragment extends Fragment {


    @BindView(R.id.bottom_navigation_timetable)
    BottomNavigationView bottomNav;


    public TimetableMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable_main, container, false);
        ButterKnife.bind(this, view);
        TimeTableFragment timeTableFragment = new TimeTableFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_timetable_frag_container, timeTableFragment)
                .commit();
        return view;
    }

    @OnClick(R.id.timetable_bottomnav_holidays)
    public void holidayClicked() {
        HolidayFragment holidayFragment = new HolidayFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_timetable_frag_container, holidayFragment)
                .commit();
        bottomNav.setSelectedItemId(R.id.timetable_bottomnav_holidays);
    }

    @OnClick(R.id.timetable_bottomnav_timetable)
    public void timetableClicked() {
        TimeTableFragment timeTableFragment = new TimeTableFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_timetable_frag_container, timeTableFragment)
                .commit();
        bottomNav.setSelectedItemId(R.id.timetable_bottomnav_timetable);
    }

}
