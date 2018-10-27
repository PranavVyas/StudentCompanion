package com.vyas.pranav.studentcompanion.dashboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.extraUtils.viewsUtils;
import com.vyas.pranav.studentcompanion.individualAttandance.IndividualAttendanceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_dashboard)
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        showIndividualAttendance();
        setSupportActionBar(mToolbar);
        viewsUtils.buildNavigationDrawer(this,mToolbar);
    }

    private void showIndividualAttendance(){
        IndividualAttendanceFragment todayAttandanceFrag = new IndividualAttendanceFragment();
        Bundle data = new Bundle();
        //TODO Send data to Fragment Like Date...
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_dashboard_today_attendance,todayAttandanceFrag)
                .commit();
    }
}
