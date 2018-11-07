package com.vyas.pranav.studentcompanion.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.asynTasks.AddAllAttendanceAsyncTask;
import com.vyas.pranav.studentcompanion.asynTasks.OverallAttendanceAsyncTask;
import com.vyas.pranav.studentcompanion.dashboard.DashboardActivity;
import com.vyas.pranav.studentcompanion.data.SharedPrefsUtils;
import com.vyas.pranav.studentcompanion.data.firebase.HolidayFetcher;
import com.vyas.pranav.studentcompanion.data.firebase.TimetableDataFetcher;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;
import com.vyas.pranav.studentcompanion.jobs.DailyExecutingJobs;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstRunActivity extends AppCompatActivity implements TimetableDataFetcher.OnTimeTableReceived, HolidayFetcher.OnHolidayFechedListener, OverallAttendanceAsyncTask.OnOverallAttendanceAddedListener, AddAllAttendanceAsyncTask.OnAllAttendanceInitializedListener {
    public static final String TAG = "FirstRunActivity";

    @BindView(R.id.tv_first_run_greeting)
    TextView tvGreetings;
    @BindView(R.id.spinner_first_run_term)
    Spinner mSpinner;
    @BindView(R.id.progress_first_run_term)
    ProgressBar mProgress;
    @BindView(R.id.tv_first_run_progress_tag)
    TextView tvProgressTag;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        ButterKnife.bind(this);
        mProgress.setVisibility(View.GONE);
        tvProgressTag.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        //Check if user has completed registration or not
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
        tvGreetings.setText("Hello " + mAuth.getCurrentUser().getDisplayName());
        // Set Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.term_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    @OnClick(R.id.btn_first_run_continue)
    public void loginBtnClicked() {
        if (mSpinner.getSelectedItem().toString().equals(getResources().getStringArray(R.array.term_array)[1])) {
            startFetchingNecessaryData();
            mProgress.setVisibility(View.VISIBLE);
            tvProgressTag.setVisibility(View.VISIBLE);
        }
    }

    public void startFetchingNecessaryData() {
        mSpinner.setEnabled(false);
        //Fetch TimeTable Data
        tvProgressTag.setText("Fetching Timetable from Internet...");
        TimetableDataFetcher dataFetcher = new TimetableDataFetcher(this, this);
        dataFetcher.fetchTimetable();
    }

    /*When Timetable is successfully retrieved than Start retrieving the Holidays Database*/
    @Override
    public void OnTimetableReceived() {
        tvProgressTag.setText("Fetching Holidays from Internet...");
        Toast.makeText(this, "Time Table Recieved", Toast.LENGTH_SHORT).show();
        HolidayFetcher holidayFetcher = new HolidayFetcher(this, this);
        holidayFetcher.startFething();
        //HolidayFetcher.fetchHolidays(this);
    }

    /*When we have both the database ready (Holiday and Timetable)
     * Initialize Attendance Database for current Term
     * Initialize Overall Attendance database after attendance Database is initialized*/
    @Override
    public void OnHolidayFeched() {
        tvProgressTag.setText("Initilazing Attendance Database Now...\nThis might take a minute or less...");
        Toast.makeText(this, "Holiday Received", Toast.LENGTH_SHORT).show();
        Date startDate = Converters.convertStringToDate(Constances.startOfSem);
        Date endDate = Converters.convertStringToDate(Constances.endOfSem);
        AddAllAttendanceAsyncTask addAllAttendanceAsyncTask = new AddAllAttendanceAsyncTask(this, this);
        addAllAttendanceAsyncTask.setDates(startDate, endDate);
        addAllAttendanceAsyncTask.execute();
    }

    /*
     * Method to initlize the overall database for the first time
     */
    @Override
    public void OnAllAttendanceInitialized() {
        Toast.makeText(this, "Attendance Initialized", Toast.LENGTH_SHORT).show();
        //Date currDate = Converters.convertStringToDate("22/02/2019");
        Date currDate = new Date();
        OverallAttendanceAsyncTask overallAttendanceAsyncTask = new OverallAttendanceAsyncTask(this, this);
        overallAttendanceAsyncTask.setCurrDate(currDate);
        overallAttendanceAsyncTask.execute();
    }

    /*
     * After the overall database is initlized the Dashboard Activity should be started
     */
    @Override
    public void OnOverallAttendanceAdded() {
        DailyExecutingJobs.sheduleDailyJob();
        Intent startDashboard = new Intent(this, DashboardActivity.class);
        startActivity(startDashboard);
        SharedPrefsUtils.setFirstTimeRunActivity(this, TAG, true);
        finish();
    }

    /*
     * User pressed Login button*/
    @OnClick(R.id.btn_first_run_login)
    public void onBackToLoginPressed() {
        mAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


}
