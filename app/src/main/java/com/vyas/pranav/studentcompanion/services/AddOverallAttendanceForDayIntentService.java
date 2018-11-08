package com.vyas.pranav.studentcompanion.services;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.vyas.pranav.studentcompanion.asynTasks.OverallAttendanceAsyncTask;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;

import java.util.Date;

import androidx.annotation.Nullable;

public class AddOverallAttendanceForDayIntentService extends IntentService implements OverallAttendanceAsyncTask.OnOverallAttendanceAddedListener {

    public AddOverallAttendanceForDayIntentService() {
        super("AddOverallAttendanceForDayIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent.hasExtra(Constances.KEY_SEND_END_DATE_TO_SERVICE_OVERALL)) {
            //Date currDate = Converters.convertStringToDate(intent.getStringExtra(Constances.KEY_SEND_END_DATE_TO_SERVICE_OVERALL));
            Date currDate = new Date();
            OverallAttendanceAsyncTask overallAttendanceAsyncTask = new OverallAttendanceAsyncTask(this, this);
            overallAttendanceAsyncTask.setCurrDate(currDate);
            overallAttendanceAsyncTask.execute();
        } else {
            Toast.makeText(getApplicationContext(), "Error occured updating overall database", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnOverallAttendanceAdded() {
        //Toast.makeText(getApplicationContext(), "Successfully Updated Overall Attendance Database", Toast.LENGTH_SHORT).show();
    }
}
