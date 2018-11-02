package com.vyas.pranav.studentcompanion.extraUtils;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.evernote.android.job.JobManager;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualEntry;
import com.vyas.pranav.studentcompanion.data.firebase.HolidayDataFetcher;
import com.vyas.pranav.studentcompanion.data.firebase.TimetableDataFetcher;
import com.vyas.pranav.studentcompanion.data.overallDatabase.OverallAttendanceHelper;
import com.vyas.pranav.studentcompanion.jobs.DailyAttendanceCreater;
import com.vyas.pranav.studentcompanion.services.AddEmptyAttendanceIntentService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.vyas.pranav.studentcompanion.extraUtils.Constances.VALUE_ABSENT;
import static com.vyas.pranav.studentcompanion.extraUtils.Constances.VALUE_PRESENT;
import static com.vyas.pranav.studentcompanion.extraUtils.Converters.convertStringToDate;

public class DeveloperActivity extends AppCompatActivity {

    AttendanceIndividualDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        ButterKnife.bind(this);
        mDb = AttendanceIndividualDatabase.getInstance(this);
    }

    @OnClick(R.id.developer_add_attendance)
    public void testDatabase(){
        AttendanceIndividualEntry test1 = new AttendanceIndividualEntry();
        test1.setAttended(VALUE_PRESENT);
        test1.setDate(convertStringToDate("01/01/2018"));
        test1.setDurationInMillis(3600);
        test1.setFacultyName("N/A");
        test1.setLactureNo(1);
        test1.setLactureType("Lacture");
        test1.setSubName("MATHS");
        test1.set_ID(Converters.generateIdForIndividualAttendance(convertStringToDate("01/01/2018"), 1));
        AttendanceIndividualEntry test2 = new AttendanceIndividualEntry();
        test2.setAttended(VALUE_ABSENT);
        test2.setDate(convertStringToDate("01/01/2018"));
        test2.setDurationInMillis(3600);
        test2.setFacultyName("N/A");
        test2.setLactureNo(2);
        test2.setLactureType("Lacture");
        test2.setSubName("SCIENCE");
        test2.set_ID(Converters.generateIdForIndividualAttendance(convertStringToDate("01/01/2018"), 2));
        mDb.attendanceIndividualDao().insertAttendance(test1);
        mDb.attendanceIndividualDao().insertAttendance(test2);
    }

    @OnClick(R.id.developer_delete_all_attendance)
    public void deleteAtten(){
        mDb.attendanceIndividualDao().deleteAllAttendance();
    }

    @OnClick(R.id.developer_get_holidays)
    public void checkFirebase(){
        HolidayDataFetcher.fetchHolidays(this);
    }

    @OnClick(R.id.developer_get_timetable)
    public void getTimetable(){
        TimetableDataFetcher.getTimetableFromFirebase(this);
    }

    @OnClick(R.id.developer_start_services)
    public void markAttendance() {
        Intent markAttendance = new Intent(this, AddEmptyAttendanceIntentService.class);
        Bundle sendData = new Bundle();
        Date date = new Date();
        sendData.putString(Constances.KEY_SEND_DATE_TO_SERVICE, Converters.convertDateToString(date));
        markAttendance.putExtras(sendData);
        startService(markAttendance);
    }

    @OnClick(R.id.developer_start_sheduling)
    public void startService() {
        if (!JobManager.instance().getAllJobRequestsForTag(DailyAttendanceCreater.TAG).isEmpty()) {
            DailyAttendanceCreater.schedule(0, 10, 0, 12);
        }
    }

    @OnClick(R.id.developer_stop_shedule)
    public void stopService() {
        DailyAttendanceCreater.cancelDailyJob();
    }

    @OnClick(R.id.developer_confogure_init_database)
    public void configureInitDatabase() {
        OverallAttendanceHelper.initDatabaseFirsTime(this, Converters.convertStringToDate(Constances.startOfSem), Converters.convertStringToDate(Constances.endOfSem));
        //helper.initDatabaseFirsTime(360,365,2018,2018);
    }

    @OnClick(R.id.developer_insert_overall)
    public void TestInit() {
        OverallAttendanceHelper.addDataInOverallAttendance(this, new Date());
    }

    @OnClick(R.id.developer_test_anything)
    public void TestAnything() {
        try {
            Date date = new SimpleDateFormat("D yyyy").parse("34" + " " + 2018);
            Toast.makeText(this, "Date for 364,2018 is " + Converters.convertDateToString(date), Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
