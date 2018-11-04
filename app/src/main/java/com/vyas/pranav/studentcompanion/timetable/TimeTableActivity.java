package com.vyas.pranav.studentcompanion.timetable;

import android.os.Bundle;

import com.vyas.pranav.studentcompanion.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public class TimeTableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        ButterKnife.bind(this);
    }
}
