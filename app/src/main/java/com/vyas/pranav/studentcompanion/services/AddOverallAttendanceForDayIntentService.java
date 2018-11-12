package com.vyas.pranav.studentcompanion.services;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.asyntasks.OverallAttendanceAsyncTask;
import com.vyas.pranav.studentcompanion.extrautils.Constances;

import java.util.Date;

import androidx.annotation.Nullable;

/**
 * The type Add overall attendance for day intent service.
 * To start adding overall attendance when needed (Everyday at midnight to refresh database)
 */
public class AddOverallAttendanceForDayIntentService extends IntentService implements OverallAttendanceAsyncTask.OnOverallAttendanceAddedListener {

    /**
     * Instantiates a new Add overall attendance for day intent service.
     */
    public AddOverallAttendanceForDayIntentService() {
        super("AddOverallAttendanceForDayIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent.hasExtra(Constances.KEY_SEND_END_DATE_TO_SERVICE_OVERALL)) {
            //set Today's Date
            Date currDate = new Date();
            OverallAttendanceAsyncTask overallAttendanceAsyncTask = new OverallAttendanceAsyncTask(this, this);
            overallAttendanceAsyncTask.setCurrDate(currDate);
            overallAttendanceAsyncTask.execute();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.java_add_overall_attendance_service_error_service), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnOverallAttendanceAdded() {
        //Toast.makeText(getApplicationContext(), "Successfully Updated Overall Attendance Database", Toast.LENGTH_SHORT).show();
    }
}
