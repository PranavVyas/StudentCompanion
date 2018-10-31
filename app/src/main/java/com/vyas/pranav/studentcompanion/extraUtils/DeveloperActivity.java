package com.vyas.pranav.studentcompanion.extraUtils;

import android.os.Bundle;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualEntry;
import com.vyas.pranav.studentcompanion.data.firebase.HolidayDataFetcher;
import com.vyas.pranav.studentcompanion.data.firebase.TimetableDataFetcher;

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
        AttendanceIndividualEntry test2 = new AttendanceIndividualEntry();
        test2.setAttended(VALUE_ABSENT);
        test2.setDate(convertStringToDate("01/01/2018"));
        test2.setDurationInMillis(3600);
        test2.setFacultyName("N/A");
        test2.setLactureNo(2);
        test2.setLactureType("Lacture");
        test2.setSubName("SCIENCE");
        if(mDb.attendanceIndividualDao().getAttendanceForDate(convertStringToDate("01/01/2018")).size() > 1){
            mDb.attendanceIndividualDao().updateAttendance(test1);
            mDb.attendanceIndividualDao().updateAttendance(test2);
        }else{
            mDb.attendanceIndividualDao().insertAttendance(test1);
            mDb.attendanceIndividualDao().insertAttendance(test2);
        }
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
}
