package com.vyas.pranav.studentcompanion.asynTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.data.overallDatabase.OverallAttendanceDatabase;
import com.vyas.pranav.studentcompanion.data.overallDatabase.OverallAttendanceEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.preference.PreferenceManager;

import static com.vyas.pranav.studentcompanion.extraUtils.Constances.VALUE_PRESENT;

/*
 * To add overall attendance to the database in background*/
public class OverallAttendanceAsyncTask extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private Date currDate;
    private OnOverallAttendanceAddedListener mCallback;

    public OverallAttendanceAsyncTask(Context mContext, OnOverallAttendanceAddedListener mCallback) {
        this.mContext = mContext;
        this.mCallback = mCallback;
    }

    /*
     * For setting current date in adding overall attendance
     * This is necessary for calculating days available to bunk from given date
     * */
    public void setCurrDate(Date currDate) {
        this.currDate = currDate;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AttendanceIndividualDatabase mIndividualDb = AttendanceIndividualDatabase.getInstance(mContext);
        OverallAttendanceDatabase mOverallDb = OverallAttendanceDatabase.getInstance(mContext);
        List<OverallAttendanceEntry> overallAttendanceEntries = new ArrayList<>();
        String startDateStr = PreferenceManager.getDefaultSharedPreferences(mContext).getString(Constances.START_DATE_SEM, "Please Select");
        for (int i = 0; i < Constances.NO_OF_SUBJECTS; i++) {
            String subName = Constances.SUBJECTS.get(i);
            int daysTotal = mIndividualDb.attendanceIndividualDao().getTotalDaysForSubject(subName);
            int daysPresent = mIndividualDb.attendanceIndividualDao().getAttendedDays(subName, VALUE_PRESENT);
            float presentPercentage = (daysPresent * 100) / daysTotal;
            int minDaysThreshold = (int) Math.ceil((daysTotal * 0.75f));
            int daysElapsedTillToday = mIndividualDb.attendanceIndividualDao().getDatesBetweenForSubject(subName, Converters.convertStringToDate(startDateStr), currDate);
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
        //Save data in SharedPrefs if needed for smart card
        addDataToSmartCardIfNeeded(overallAttendanceEntries);
        mOverallDb.overallAttandanceDao().insertAllSubjectOverallAttedance(overallAttendanceEntries);
        return null;
    }

    /*
     * To provide callback to First run activity when the attendance is added successfully*/
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //FirstRunActivity Notified
        mCallback.OnOverallAttendanceAdded();
    }

    /*
     * Method to save the data in shared preferences if condition matched
     * This information will be used to populate data inside Smartcard in Dashboard Activity*/
    private void addDataToSmartCardIfNeeded(List<OverallAttendanceEntry> mEnties) {
        List<String> warnings = new ArrayList<>();
        for (OverallAttendanceEntry x :
                mEnties) {
            String warning = "";
            int daysTotal = x.getTotalDays();
            float percentPresent = x.getPercentPresent();
            int daysPresent = (int) Math.ceil((percentPresent * daysTotal) / 100);
            int daysThreshold = (int) Math.ceil(daysTotal * 0.75);
            int daysAlreadyBunked = x.getDaysBunked();
            int daysAvailableToBunk = x.getDaysAvailableToBunk();
            int daysElapsed = daysPresent + daysAlreadyBunked;
            int daysTillThreshold = daysThreshold - daysPresent;
            int daysLeft = daysTotal - daysElapsed;
            if (daysAvailableToBunk > 5) {
                warning = "* You are safe in Subject : <b>" + x.getSubjectName() + "</b>";
            } else if (daysAvailableToBunk > 0) {
                warning = "* You Only have <b>" + daysAvailableToBunk + "</b> days to bunk in Subject : <b>" + x.getSubjectName() + "</b>, Attend at least <b>" + daysTillThreshold + "</b> days out of <b>" + daysLeft + "</b> days to get Attendance greater than 75 %";
            } else if (daysAvailableToBunk == 0) {
                warning = "* You Should attend All the coming lectures in Subject : <b>" + x.getSubjectName() + "</b>";
            } else {
                warning = "* You have less than needed attendance in  Subject <b>" + x.getSubjectName() + "</b>";
            }
            warnings.add(warning);
        }
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        Set<String> warningSet = new HashSet<>(warnings);
        mEditor.putStringSet(Constances.KEY_SMART_CARD_DETAILS, warningSet);
        mEditor.apply();
    }

    public interface OnOverallAttendanceAddedListener {
        void OnOverallAttendanceAdded();
    }

}