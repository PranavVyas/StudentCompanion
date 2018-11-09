package com.vyas.pranav.studentcompanion.timetable;


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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimetableMainFragment extends Fragment {
    public static final int FRAG_HOLIDAYS_FRAG = 0;
    public static final int FRAG_TIMETABLE_FRAG = 1;
    @BindView(R.id.bottom_navigation_timetable)
    BottomNavigationView bottomNav;
    private OnTimeTableMainFragmentChangeListener mCallback;

    public TimetableMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void setupBottomNavigation() {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked}, // enabled
                new int[]{-android.R.attr.state_checked}, // disabled
        };

        int[] colors = new int[]{
                getContext().getResources().getColor(R.color.accent),
                Color.GRAY
        };
        ColorStateList myList = new ColorStateList(states, colors);
        bottomNav.setItemIconTintList(myList);
        bottomNav.setItemTextColor(myList);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timetableClicked();
        setupBottomNavigation();
        //If the saved instance has returned the state of the selected state of the fragment set it thorugh this
        if (getArguments().getInt(DashboardActivity.KEY_SAVED_STATE_TIME_TABLE_MAIN_FRAG) == FRAG_TIMETABLE_FRAG) {
            timetableClicked();
        } else {
            holidayClicked();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (OnTimeTableMainFragmentChangeListener) context;
    }

    @OnClick(R.id.timetable_bottomnav_holidays)
    void holidayClicked() {
        HolidayFragment holidayFragment = new HolidayFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.frame_timetable_frag_container, holidayFragment)
                .commit();
        bottomNav.setSelectedItemId(R.id.timetable_bottomnav_holidays);
        mCallback.OnTimeTableMainFragmentChanged(FRAG_HOLIDAYS_FRAG);
    }

    @OnClick(R.id.timetable_bottomnav_timetable)
    void timetableClicked() {
        TimeTableFragment timeTableFragment = new TimeTableFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.frame_timetable_frag_container, timeTableFragment)
                .commit();
        bottomNav.setSelectedItemId(R.id.timetable_bottomnav_timetable);
        mCallback.OnTimeTableMainFragmentChanged(FRAG_TIMETABLE_FRAG);
    }

    /**
     * Callback to the DashboardActivity giving the current selected
     * fragment so that it can be saved inside a savedInstancesBundle and
     * can be retrived through the getArguments() Method while using
     * setArgument() to start fragment transition
     */
    public interface OnTimeTableMainFragmentChangeListener {
        void OnTimeTableMainFragmentChanged(int currFrag);
    }

}
