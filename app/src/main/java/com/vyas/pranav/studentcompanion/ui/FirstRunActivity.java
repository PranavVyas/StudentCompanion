package com.vyas.pranav.studentcompanion.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.dashboard.DashboardActivity;
import com.vyas.pranav.studentcompanion.data.SharedPrefsUtils;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceHelper;
import com.vyas.pranav.studentcompanion.data.firebase.HolidayDataFetcher;
import com.vyas.pranav.studentcompanion.data.firebase.TimetableDataFetcher;
import com.vyas.pranav.studentcompanion.data.overallDatabase.OverallAttendanceHelper;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstRunActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimetableDataFetcher.OnTimeTableReceived, HolidayDataFetcher.OnHolidaysFetcherListener, AttendanceHelper.OnAttendanceDatabaseInitializedListener, OverallAttendanceHelper.OnOverallDatabaseInitializedListener {
    public static final String TAG = "FirstRunActivity";

    @BindView(R.id.spinner_first_run_term)
    Spinner mSpinner;
    @BindView(R.id.progress_first_run_term)
    ProgressBar mProgress;
    @BindView(R.id.tv_first_run_progress_tag)
    TextView tvProgressTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        ButterKnife.bind(this);
        //Check if user has completed registration or not
        mProgress.setVisibility(View.GONE);
        tvProgressTag.setVisibility(View.GONE);
        if (SharedPrefsUtils.isFirstTimeRunActivity(this, TAG)) {
            //User has not registered
            showDetails();
        } else {
            //User has registered already
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void showDetails() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.term_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinner.setAdapter(adapter);
        //Set listener for Item Selected
        mSpinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getItemAtPosition(i).toString().equals("Jan 2019 - May 2019")) {
            //SharedPrefsUtils.setFirstTimeRunActivity(this,TAG,true);
            mProgress.setVisibility(View.VISIBLE);
            tvProgressTag.setVisibility(View.VISIBLE);
            //Start fetching data like Holidays and Timetable from Firebase Database
            startFetchingNecessaryData();
        }
        Toast.makeText(this, "Selected : " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Nothing Selected", Toast.LENGTH_SHORT).show();
    }

    public void startFetchingNecessaryData() {
        //Fetch TimeTable Data
        tvProgressTag.setText("Fetching Timetable from Internet...");
        TimetableDataFetcher.getTimetableFromFirebase(this);
    }

    /*When Timetable is successfully retrieved than Start retrieving the Holidays Database*/
    @Override
    public void OnTimetableReceived() {
        tvProgressTag.setText("Fetching Holidays from Internet...");
        Toast.makeText(this, "Time Table Recieved", Toast.LENGTH_SHORT).show();
        HolidayDataFetcher.fetchHolidays(this);
    }

    /*When we have both the database ready (Holiday and Timetable)
     * Initialize Attendance Database for current Term
     * Initialize Overall Attendance database after attendance Database is initialized*/
    @Override
    public void OnHolidayFetched() {
        tvProgressTag.setText("Initilazing Attendance Database Now...\nThis might take a minute or less...");
        Toast.makeText(this, "Holiday Received", Toast.LENGTH_SHORT).show();
        Date startDate = Converters.convertStringToDate(Constances.startOfSem);
        Date endDate = Converters.convertStringToDate(Constances.endOfSem);
        AttendanceHelper.initAttendanceDatabaseFirstTime(this, startDate, endDate);
    }

    /*
     * Method to initlize the overall database for the first time
     */
    @Override
    public void OnAttendanceDatabaseInitialized() {
        Toast.makeText(this, "Attendance Initialized", Toast.LENGTH_SHORT).show();
        Date currDate = Converters.convertStringToDate("15/02/2019");
        //TODO Date currDate = new Date();
        OverallAttendanceHelper.addDataInOverallAttendance(this, currDate);
    }

    /*
     * After the overall database is initlized the Dashboard Activity should be started
     */
    @Override
    public void onOverallDatabaseinitilazed() {
        Toast.makeText(this, "Thanks for Waiting... \nDatabse is ready to use now...", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Overall Inintialized", Toast.LENGTH_SHORT).show();
        Intent startDashboard = new Intent(this, DashboardActivity.class);
        startActivity(startDashboard);
        SharedPrefsUtils.setFirstTimeRunActivity(this, TAG, true);
        finish();
    }
}
