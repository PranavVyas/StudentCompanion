package com.vyas.pranav.studentcompanion.asynTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.data.overallDatabase.OverallAttendanceDatabase;
import com.vyas.pranav.studentcompanion.data.overallDatabase.OverallAttendanceEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.vyas.pranav.studentcompanion.extraUtils.Constances.VALUE_PRESENT;

public class OverallAttendanceAsyncTask extends AsyncTask<Void, Void, Void> {

    Context mContext;
    Date currDate;
    OnOverallAttendanceAddedListener mCallback;

    public OverallAttendanceAsyncTask(Context mContext, OnOverallAttendanceAddedListener mCallback) {
        this.mContext = mContext;
        this.mCallback = mCallback;
    }

    public void setCurrDate(Date currDate) {
        this.currDate = currDate;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AttendanceIndividualDatabase mIndividualDb = AttendanceIndividualDatabase.getInstance(mContext);
        OverallAttendanceDatabase mOverallDb = OverallAttendanceDatabase.getInstance(mContext);
        List<OverallAttendanceEntry> overallAttendanceEntries = new ArrayList<>();
        for (int i = 0; i < Constances.NO_OF_SUBJECTS; i++) {
            String subName = Constances.SUBJECTS.get(i);
            int daysTotal = mIndividualDb.attendanceIndividualDao().getTotalDaysForSubject(subName);
            int daysPresent = mIndividualDb.attendanceIndividualDao().getAttendedDays(subName, VALUE_PRESENT);
            double presentPercentage = (daysPresent * 100) / daysTotal;
            int minDaysThreshold = (int) Math.ceil((daysTotal * 0.75f));
            int daysElapsedTillToday = mIndividualDb.attendanceIndividualDao().getDatesBetweenForSubject(subName, Converters.convertStringToDate(Constances.startOfSem), currDate);
            int daysBunked = daysElapsedTillToday - daysPresent;
            int daysTotalAvailableForBunk = daysTotal - minDaysThreshold;
            int daysAvailableForBunkNow = daysTotalAvailableForBunk - daysBunked;
            final OverallAttendanceEntry tempSubjectAttendance = new OverallAttendanceEntry();
            tempSubjectAttendance.setSubjectName(subName);
            tempSubjectAttendance.setTotalDays(daysTotal);
            tempSubjectAttendance.setPercentPresent(presentPercentage);
            tempSubjectAttendance.setDaysAvailableToBunk(daysAvailableForBunkNow);
            tempSubjectAttendance.setDaysBunked(daysBunked);
            overallAttendanceEntries.add(tempSubjectAttendance);
            String log = "Current Date : " + currDate + "\t\tSub name : " + subName + "\nTotal Days : " + daysTotal + "\t\tPresent Days : " + daysPresent + "\nMin Days : " + minDaysThreshold
                    + "\t\tPresent Percent : " + presentPercentage + "\nDays Elapsed : " + daysElapsedTillToday + "\t\tDays Bunked : " + daysBunked +
                    "\nDays Total available to bunk : " + daysTotalAvailableForBunk + "\t\tDays Can Be Bunked : " + daysAvailableForBunkNow;
            Logger.d(log);
        }
        mOverallDb.overallAttandanceDao().insertAllSubjectOverallAttedance(overallAttendanceEntries);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mCallback.OnOverallAttendanceAdded();
    }

    public interface OnOverallAttendanceAddedListener {
        void OnOverallAttendanceAdded();
    }
}