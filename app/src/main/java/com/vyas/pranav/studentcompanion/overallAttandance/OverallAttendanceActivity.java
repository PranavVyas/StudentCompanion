package com.vyas.pranav.studentcompanion.overallAttandance;

import android.os.Bundle;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.extraUtils.viewsUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OverallAttendanceActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_overall_attandance) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_attendance);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        viewsUtils.buildNavigationDrawer(this,mToolbar);
    }
}
