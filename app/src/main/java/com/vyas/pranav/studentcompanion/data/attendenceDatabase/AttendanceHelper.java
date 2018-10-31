package com.vyas.pranav.studentcompanion.data.attendenceDatabase;

import android.content.Context;

import com.vyas.pranav.studentcompanion.extraUtils.AppExecutors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttendanceHelper {
    AppExecutors mExecutors;
    AttendanceIndividualDatabase mDb;
    List<AttendanceIndividualEntry> allAttendances = new ArrayList<>();
    List<AttendanceIndividualEntry> attendancesForDay;

    public AttendanceHelper(Context context) {
        this.mExecutors = AppExecutors.getInstance();
        this.mDb = AttendanceIndividualDatabase.getInstance(context);
    }

    public void insertAttendanceForDate(final AttendanceIndividualEntry newAttendance){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.attendanceIndividualDao().insertAttendance(newAttendance);
            }
        });
    }

    public List<AttendanceIndividualEntry> getAllAttendence(){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                allAttendances = mDb.attendanceIndividualDao().getAllAttendance();
            }
        });
        return allAttendances;
    }

    public List<AttendanceIndividualEntry> getAttendanceForDate(final Date date){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                attendancesForDay = new ArrayList<>();
                attendancesForDay = mDb.attendanceIndividualDao().getAttendanceForDate(date);
            }
        });
        return attendancesForDay;
    }
}
