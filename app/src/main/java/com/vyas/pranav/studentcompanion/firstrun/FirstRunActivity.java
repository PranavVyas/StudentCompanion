package com.vyas.pranav.studentcompanion.firstrun;

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
import com.vyas.pranav.studentcompanion.asyntasks.AddAllAttendanceAsyncTask;
import com.vyas.pranav.studentcompanion.asyntasks.OverallAttendanceAsyncTask;
import com.vyas.pranav.studentcompanion.dashboard.DashboardActivity;
import com.vyas.pranav.studentcompanion.data.SharedPrefsUtils;
import com.vyas.pranav.studentcompanion.data.firebase.HolidayFetcher;
import com.vyas.pranav.studentcompanion.data.firebase.TimetableDataFetcher;
import com.vyas.pranav.studentcompanion.extrautils.Constances;
import com.vyas.pranav.studentcompanion.extrautils.Converters;
import com.vyas.pranav.studentcompanion.jobs.DailyExecutingJobs;
import com.vyas.pranav.studentcompanion.login.LoginActivity;
import com.vyas.pranav.studentcompanion.toturial.ToturialActivity;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The type First run activity.
 */
public class FirstRunActivity extends AppCompatActivity implements
//        InternetConnectivityListener,
        TimetableDataFetcher.OnTimeTableReceived, HolidayFetcher.OnHolidayFechedListener, OverallAttendanceAsyncTask.OnOverallAttendanceAddedListener, AddAllAttendanceAsyncTask.OnAllAttendanceInitializedListener, DatePickerFrag.OnSelectedStartDateListener {
    /**
     * The constant TAG.
     */
    public static final String TAG = "FirstRunActivity";

    /**
     * The textview greetings.
     */
    @BindView(R.id.tv_first_run_greeting)
    TextView tvGreetings;
    /**
     * The progressbar instance
     */
    @BindView(R.id.progress_first_run_term)
    ProgressBar mProgress;
    /**
     * The TextView progress tag used to show what is being happened in background thread
     */
    @BindView(R.id.tv_first_run_progress_tag)
    TextView tvProgressTag;
    /**
     * The TextView start date.
     */
    @BindView(R.id.tv_first_run_start_date)
    TextView tvStartDate;
    /**
     * The TextView end date.
     */
    @BindView(R.id.tv_first_run_end_date)
    TextView tvEndDate;
    /**
     * The Start date Button.
     */
    @BindView(R.id.btn_first_run_open_start_date)
    Button startDateBtn;
    /**
     * The End date Button.
     */
    @BindView(R.id.btn_first_run_open_end_date)
    Button endDateBtn;
    /**
     * The continue button
     */
    @BindView(R.id.btn_first_run_continue)
    Button btnContinue;
    /**
     * The toolbar.
     */
    @BindView(R.id.toolbar_first_run)
    Toolbar mToolbar;

    private FirebaseAuth mAuth;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    private HolidayFetcher holidayFetcher;
    private TimetableDataFetcher dataFetcher;
    private InternetAvailabilityChecker mInternetChecker;
//    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        ButterKnife.bind(this);
        mProgress.setVisibility(View.GONE);
        tvProgressTag.setVisibility(View.GONE);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.java_firstrun_title));
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

    /**
     * Attach the internet connectivity listener
     */
    @Override
    protected void onResume() {
        super.onResume();
//        mInternetChecker.addInternetConnectivityListener(this);
    }

    /**
     * Detach the internet connectivity listener
     */
    @Override
    protected void onPause() {
        super.onPause();
//        mInternetChecker.removeInternetConnectivityChangeListener(this);
    }

    /**
     * Clicked start date selector.
     */
    @OnClick(R.id.btn_first_run_open_start_date)
    void clickedStartDateSelector() {
        DialogFragment datePicker = new DatePickerFrag();
        datePicker.show(getSupportFragmentManager(), "StartDate");
    }

    /**
     * Clicked end date selector.
     */
    @OnClick(R.id.btn_first_run_open_end_date)
    void clickedEndDateSelector() {
        DialogFragment datePicker = new DatePickerFrag();
        datePicker.show(getSupportFragmentManager(), "EndDate");
    }

    /**
     * Continue Button clicked.
     */
    @OnClick(R.id.btn_first_run_continue)
    public void continueBtnClicked() {
        if (tvStartDate.getText().equals(getString(R.string.tv_first_run_please_select)) || tvEndDate.getText().equals(getString(R.string.tv_first_run_please_select))) {
            Toast.makeText(this, getString(R.string.java_error_date_not_selected_first_run), Toast.LENGTH_SHORT).show();
        } else if (tvEndDate.getText().equals(tvStartDate.getText())) {
            Toast.makeText(this, getString(R.string.java_error_dates_are_same_first_run), Toast.LENGTH_SHORT).show();
        } else if (getDissefenceBtwnDates(tvStartDate.getText().toString(), tvEndDate.getText().toString()) < 15 || getDissefenceBtwnDates(tvStartDate.getText().toString(), tvEndDate.getText().toString()) > 545) {
            Toast.makeText(this, getString(R.string.java_error_diffe_in_dates_first_run), Toast.LENGTH_SHORT).show();
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


    /**
     * Start fetching necessary data.
     */
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
        Toast.makeText(this, getString(R.string.java_timetable_success_first_run), Toast.LENGTH_SHORT).show();
        holidayFetcher = new HolidayFetcher(this, this);
        holidayFetcher.startFetching();
    }

    /*When we have both the database ready (Holiday and Timetable)
     * Initialize Attendance Database for current Term
     * Initialize Overall Attendance database after attendance Database is initialized*/
    @Override
    public void OnHolidayFeched() {
        tvProgressTag.setText(getString(R.string.java_progress_holiday_first_run));
        Toast.makeText(this, getString(R.string.java_holiday_success_first_run), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, getString(R.string.java_attendance_success_first_run), Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(this, ToturialActivity.class));
        SharedPrefsUtils.setFirstTimeRunActivity(this, TAG, true);
        finish();
    }

    /**
     * On back to login pressed.
     */
    /*
     * User pressed Login button*/
    @OnClick(R.id.btn_first_run_login)
    public void onBackToLoginPressed() {
        mAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    /**
     * Start date selected
     *
     * @param dateStr starting date
     */
    @Override
    public void OnSelectedStartDate(String dateStr) {
        mEditor.putString(Constances.START_DATE_SEM, dateStr);
        mEditor.apply();
        tvStartDate.setText(dateStr);
    }

    /**
     * End date selected
     * @param dateStr Ending date
     */
    @Override
    public void OnSelectedEndDate(String dateStr) {
        mEditor.putString(Constances.END_DATE_SEM, dateStr);
        mEditor.apply();
        tvEndDate.setText(dateStr);
    }

    /**
     * The internet connection is broken while fetching data, so we should stop fetching data if any asyncTask is available
     */
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

    /**
     * To enable buttons
     */
    private void enableButtons() {
        endDateBtn.setEnabled(true);
        startDateBtn.setEnabled(true);
        btnContinue.setEnabled(true);
    }

    /**
     * To disable buttons
     */
    private void disableButtons() {
        endDateBtn.setEnabled(false);
        startDateBtn.setEnabled(false);
        btnContinue.setEnabled(false);
    }

//    /**
//     *  Used to indicate if network is gone or available
//     *  if not connected stop fetching data now
//     * @param isConnected is connected to internet
//     */
//    @Override
//    public void onInternetConnectivityChanged(boolean isConnected) {
//        this.isConnected = isConnected;
//        if (isConnected) {
//            Toast.makeText(this, getString(R.string.java_connection_success_first_run), Toast.LENGTH_SHORT).show();
//        } else {
//            cancelFetching();
//            Toast.makeText(this, getString(R.string.java_conetion_failure_first_run), Toast.LENGTH_SHORT).show();
//        }
//    }
}
