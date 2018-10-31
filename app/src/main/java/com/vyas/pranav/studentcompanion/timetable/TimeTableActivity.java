package com.vyas.pranav.studentcompanion.timetable;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.extraUtils.ViewsUtils;
import com.vyas.pranav.studentcompanion.holidays.HolidayFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeTableActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation_timetable) BottomNavigationView bottomNav;
    @BindView(R.id.toolbar_time_table) Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ViewsUtils.buildNavigationDrawer(this,mToolbar);
        TimeTableFragment timeTableFragment = new TimeTableFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_timetable_frag_container,timeTableFragment)
                .commit();
    }

    @OnClick(R.id.timetable_bottomnav_holidays)
    public void holidayClicked(){
        HolidayFragment holidayFragment = new HolidayFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_timetable_frag_container,holidayFragment)
                .commit();
        bottomNav.setSelectedItemId(R.id.timetable_bottomnav_holidays);
    }

    @OnClick(R.id.timetable_bottomnav_timetable)
    public void timetableClicked(){
        TimeTableFragment timeTableFragment = new TimeTableFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_timetable_frag_container,timeTableFragment)
                .commit();
        bottomNav.setSelectedItemId(R.id.timetable_bottomnav_timetable);
    }
}
