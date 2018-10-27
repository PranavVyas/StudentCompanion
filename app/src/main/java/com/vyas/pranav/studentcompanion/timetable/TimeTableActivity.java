package com.vyas.pranav.studentcompanion.timetable;

import android.os.Bundle;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.extraUtils.viewsUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeTableActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_time_table) Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        viewsUtils.buildNavigationDrawer(this,mToolbar);
    }
}
