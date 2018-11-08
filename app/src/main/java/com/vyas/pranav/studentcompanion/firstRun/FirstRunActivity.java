package com.vyas.pranav.studentcompanion.firstRun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.vyas.pranav.studentcompanion.login.LoginActivity;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstRunActivity extends AppCompatActivity implements TimetableDataFetcher.OnTimeTableReceived, HolidayFetcher.OnHolidayFechedListener, OverallAttendanceAsyncTask.OnOverallAttendanceAddedListener, AddAllAttendanceAsyncTask.OnAllAttendanceInitializedListener, DatePickerFrag.OnSelectedStartDateListener {
    public static final String TAG = "FirstRunActivity";

    @BindView(R.id.tv_first_run_greeting)
    TextView tvGreetings;
    @BindView(R.id.progress_first_run_term)
    ProgressBar mProgress;
    @BindView(R.id.tv_first_run_progress_tag)
    TextView tvProgressTag;
    @BindView(R.id.tv_first_run_start_date)
    TextView tvStartDate;
    @BindView(R.id.tv_first_run_end_date)
    TextView tvEndDate;
    @BindView(R.id.btn_first_run_open_start_date)
    Button startDateBtn;
    @BindView(R.id.btn_first_run_open_end_date)
    Button endDateBtn;

    private FirebaseAuth mAuth;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        ButterKnife.bind(this);
        mProgress.setVisibility(View.GONE);
        tvProgressTag.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPrefs.edit();
        mEditor.apply();
        startDateBtn.setEnabled(true);
        endDateBtn.setEnabled(true);
        //Check if user has completed registration or not
        if (SharedPrefsUtils.isFirstTimeRunActivity(this, TAG)) {
            //User has not registered
            tvGreetings.setText(String.format("Hello %s", mAuth.getCurrentUser().getDisplayName()));
        } else {
            //User has registered already
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.btn_first_run_open_start_date)
    void clickedStartDateSelector() {
        DialogFragment datePicker = new DatePickerFrag();
        datePicker.show(getSupportFragmentManager(), "StartDate");
    }

    @OnClick(R.id.btn_first_run_open_end_date)
    void clickedEndDateSelector() {
        DialogFragment datePicker = new DatePickerFrag();
        datePicker.show(getSupportFragmentManager(), "EndDate");
    }

    @OnClick(R.id.btn_first_run_continue)
    public void continueBtnClicked() {
        if (tvStartDate.getText().equals(getString(R.string.tv_first_run_please_select)) || tvEndDate.getText().equals(getString(R.string.tv_first_run_please_select))) {
            Toast.makeText(this, "Please Select all two dates", Toast.LENGTH_SHORT).show();
        } else if (tvEndDate.getText().equals(tvStartDate.getText())) {
            Toast.makeText(this, "Both the dates can not be equal", Toast.LENGTH_SHORT).show();
        } else {
            startFetchingNecessaryData();
            mProgress.setVisibility(View.VISIBLE);
            tvProgressTag.setVisibility(View.VISIBLE);
        }
    }

    public void startFetchingNecessaryData() {
        startDateBtn.setEnabled(false);
        endDateBtn.setEnabled(false);
        //Fetch TimeTable Data
        tvProgressTag.setText(getString(R.string.java_first_run_progress_timetable));
        TimetableDataFetcher dataFetcher = new TimetableDataFetcher(this, this);
        dataFetcher.fetchTimetable();
    }

    /*When Timetable is successfully retrieved than Start retrieving the Holidays Database*/
    @Override
    public void OnTimetableReceived() {
        tvProgressTag.setText(getString(R.string.java_first_run_progress_holiday));
        Toast.makeText(this, "Time Table Recieved", Toast.LENGTH_SHORT).show();
        HolidayFetcher holidayFetcher = new HolidayFetcher(this, this);
        holidayFetcher.startFetching();
        //HolidayFetcher.fetchHolidays(this);
    }

    /*When we have both the database ready (Holiday and Timetable)
     * Initialize Attendance Database for current Term
     * Initialize Overall Attendance database after attendance Database is initialized*/
    @Override
    public void OnHolidayFeched() {
        tvProgressTag.setText("Initilazing Attendance Database Now...\nThis might take a minute or less...");
        Toast.makeText(this, "Holiday Received", Toast.LENGTH_SHORT).show();
        String startDateStr = mPrefs.getString(Constances.START_DATE_SEM, "01/01/2019");
        String endDateStr = mPrefs.getString(Constances.END_DATE_SEM, "06/05/2019");
        Date startDate = Converters.convertStringToDate(startDateStr);
        Date endDate = Converters.convertStringToDate(endDateStr);
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
        DailyExecutingJobs.scheduleDailyJob();
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

    @Override
    public void OnSelectedStartDate(String dateStr) {
        mEditor.putString(Constances.START_DATE_SEM, dateStr);
        mEditor.apply();
        tvStartDate.setText(dateStr);
    }

    @Override
    public void OnSelectedEndDate(String dateStr) {
        mEditor.putString(Constances.END_DATE_SEM, dateStr);
        mEditor.apply();
        tvEndDate.setText(dateStr);
    }
}
