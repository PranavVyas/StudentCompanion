package com.vyas.pranav.studentcompanion.data.overallDatabase;

import android.content.Context;

import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.extraUtils.AppExecutors;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.util.Date;

import static com.vyas.pranav.studentcompanion.extraUtils.Constances.VALUE_PRESENT;

public class OverallAttendanceHelper {

    public static void addDataInOverallAttendance(Context context, Date currDate) {
        OnOverallDatabaseInitializedListener mCallback = (OnOverallDatabaseInitializedListener) context;
        AttendanceIndividualDatabase mIndividualDb = AttendanceIndividualDatabase.getInstance(context);
        final OverallAttendanceDatabase mOverallDb = OverallAttendanceDatabase.getInstance(context);
        AppExecutors mExecutors = AppExecutors.getInstance();
        //SKIPPING AS TRIAL
//        if (mIndividualDb.attendanceIndividualDao().getAllAttendance().isEmpty()) {
//            Toast.makeText(context, "Individual Attendance is Empty", Toast.LENGTH_SHORT).show();
//        } else {
        for (int i = 0; i < Constances.NO_OF_SUBJECTS; i++) {
            String subName = Constances.SUBJECTS.get(i);
            int daysTotal = mIndividualDb.attendanceIndividualDao().getAttendanceForSubject(subName).size();
            int daysPresent = mIndividualDb.attendanceIndividualDao().getAttendanceForSubject(subName, VALUE_PRESENT).size();
            double presentPercentage = (daysPresent * 100) / daysTotal;
            int minDaysThreshold = (int) Math.ceil((daysTotal * 0.75f));
            int daysElapsedTillToday = mIndividualDb.attendanceIndividualDao().getAttendanceForSubjectForTimePeriod(subName, Converters.convertStringToDate(Constances.startOfSem), currDate).size();
            int daysBunked = daysElapsedTillToday - daysPresent;
            int daysTotalAvailableForBunk = daysTotal - minDaysThreshold;
            int daysAvailableForBunkNow = daysTotalAvailableForBunk - daysBunked;
            final OverallAttendanceEntry tempSubjectAttendance = new OverallAttendanceEntry();
            tempSubjectAttendance.setSubjectName(subName);
            tempSubjectAttendance.setTotalDays(daysTotal);
            tempSubjectAttendance.setPercentPresent(presentPercentage);
            tempSubjectAttendance.setDaysAvailableToBunk(daysAvailableForBunkNow);
            tempSubjectAttendance.setDaysBunked(daysBunked);
            mExecutors.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mOverallDb.overallAttandanceDao().insertSubjectOverallAttedance(tempSubjectAttendance);
                }
            });
        }
        mCallback.onOverallDatabaseinitilazed();

//        }
    }

    public interface OnOverallDatabaseInitializedListener {
        void onOverallDatabaseinitilazed();
    }
}
