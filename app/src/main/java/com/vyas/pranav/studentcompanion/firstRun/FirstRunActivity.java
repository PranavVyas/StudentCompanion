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
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;
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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstRunActivity extends AppCompatActivity implements InternetConnectivityListener, TimetableDataFetcher.OnTimeTableReceived, HolidayFetcher.OnHolidayFechedListener, OverallAttendanceAsyncTask.OnOverallAttendanceAddedListener, AddAllAttendanceAsyncTask.OnAllAttendanceInitializedListener, DatePickerFrag.OnSelectedStartDateListener {
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
    @BindView(R.id.btn_first_run_continue)
    Button btnContinue;
    @BindView(R.id.toolbar_first_run)
    Toolbar mToolbar;

    private FirebaseAuth mAuth;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    private HolidayFetcher holidayFetcher;
    private TimetableDataFetcher dataFetcher;
    private InternetAvailabilityChecker mInternetChecker;
    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPrefsUtils.setThemeOfUser(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        ButterKnife.bind(this);
        mProgress.setVisibility(View.GONE);
        tvProgressTag.setVisibility(View.GONE);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Registration");
        mAuth = FirebaseAuth.getInstance();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mInternetChecker = InternetAvailabilityChecker.getInstance();
        mEditor = mPrefs.edit();
        mEditor.apply();
        enableButtons();
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

    @Override
    protected void onResume() {
        super.onResume();
        mInternetChecker.addInternetConnectivityListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mInternetChecker.removeInternetConnectivityChangeListener(this);
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
        } else if (getDissefenceBtwnDates(tvStartDate.getText().toString(), tvEndDate.getText().toString()) < 15 || getDissefenceBtwnDates(tvStartDate.getText().toString(), tvEndDate.getText().toString()) > 545) {
            Toast.makeText(this, "Difference between dates can not be less than 15 days and more than 1.5 years", Toast.LENGTH_SHORT).show();
        } else if (!isConnected) {
            Toast.makeText(this, "Internet Connection is not available\nYou need Internet Connection for fetching data from servers!", Toast.LENGTH_SHORT).show();
        } else {
            startFetchingNecessaryData();
            mProgress.setVisibility(View.VISIBLE);
            tvProgressTag.setVisibility(View.VISIBLE);
        }
    }

    private int getDissefenceBtwnDates(String startDate, String endDate) {
        Converters.CustomDate start = Converters.extractElementsFromDate(Converters.convertStringToDate(startDate));
        Converters.CustomDate end = Converters.extractElementsFromDate(Converters.convertStringToDate(endDate));
        int currYear = start.getYear();
        int diff = 0;
        if (start.getYear() == end.getYear()) {
            diff = end.getDayOfYear() - start.getDayOfYear();
        } else {
            while (end.getYear() > currYear) {
                diff = (currYear % 4 == 0 ? 366 : 365) - start.getDayOfYear();
                currYear++;
            }
        }
        return diff;
    }


    public void startFetchingNecessaryData() {
        disableButtons();
        //Fetch TimeTable Data
        tvProgressTag.setText(getString(R.string.java_first_run_progress_timetable));
        dataFetcher = new TimetableDataFetcher(this, this);
        dataFetcher.fetchTimetable();
    }

    /*When Timetable is successfully retrieved than Start retrieving the Holidays Database*/
    @Override
    public void OnTimetableReceived() {
        tvProgressTag.setText(getString(R.string.java_first_run_progress_holiday));
        Toast.makeText(this, "Time Table Recieved", Toast.LENGTH_SHORT).show();
        holidayFetcher = new HolidayFetcher(this, this);
        holidayFetcher.startFetching();
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

    private void cancelFetching() {
        if (holidayFetcher != null) {
            if (holidayFetcher.asyncTask != null) {
                holidayFetcher.cancelFetching();
            }
        }
        if (dataFetcher != null) {
            if (dataFetcher.addTimetableAsyncTask != null) {
                dataFetcher.cancelFetching();
            }
        }
        tvProgressTag.setVisibility(View.GONE);
        mProgress.setVisibility(View.GONE);
        enableButtons();
    }

    private void enableButtons() {
        endDateBtn.setEnabled(true);
        startDateBtn.setEnabled(true);
        btnContinue.setEnabled(true);
    }

    private void disableButtons() {
        endDateBtn.setEnabled(false);
        startDateBtn.setEnabled(false);
        btnContinue.setEnabled(false);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        this.isConnected = isConnected;
        if (isConnected) {
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        } else {
            cancelFetching();
            Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
        }
    }
}
