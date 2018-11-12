package com.vyas.pranav.studentcompanion.individualattendance;

import android.os.Bundle;
import android.view.View;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.SharedPrefsUtils;
import com.vyas.pranav.studentcompanion.extrautils.Constances;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IndividualAttendanceActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_individual_attendence_activity)
    Toolbar toolbar;

    private String dateString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPrefsUtils.setThemeOfUser(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_attendance);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getIntent() != null){
            if(getIntent().getStringExtra(Constances.KEY_SEND_DATA_TO_INDIVIDUAL_ACTIVITY) != null){
                dateString = getIntent().getStringExtra(Constances.KEY_SEND_DATA_TO_INDIVIDUAL_ACTIVITY);
                toolbar.setTitle("Attendance for " + dateString);
            }
        }
        setSupportActionBar(toolbar);
        showAttendance(dateString);
    }

    public void showAttendance(String dateString){
        IndividualAttendanceFragment individualAttendanceFragment = new IndividualAttendanceFragment();
        Bundle dataToSend = new Bundle();
        dataToSend.putString(Constances.KEY_SEND_DATA_TO_INDIVIDUAL_FRAG,dateString);
        individualAttendanceFragment.setArguments(dataToSend);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_individual_activity_container,individualAttendanceFragment)
                .commit();
    }
}
