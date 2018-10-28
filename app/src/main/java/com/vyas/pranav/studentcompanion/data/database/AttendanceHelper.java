package com.vyas.pranav.studentcompanion.data.database;

import android.content.Context;

import com.vyas.pranav.studentcompanion.extraUtils.AppExecutors;

public class AttendanceHelper {
    AppExecutors mExecutors;
    AttendanceIndividualDatabase mDb;

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

//    public List<AttendanceIndividualEntry> getAllAttendence(){
//        final List<AttendanceIndividualEntry> allAttendances;
//        mExecutors.diskIO().execute(new Runnable() {
//            @Override
//            public void run() { }
//        });
//    }

}
