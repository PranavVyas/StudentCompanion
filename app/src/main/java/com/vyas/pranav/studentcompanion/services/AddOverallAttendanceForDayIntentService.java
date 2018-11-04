package com.vyas.pranav.studentcompanion.services;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.vyas.pranav.studentcompanion.data.overallDatabase.OverallAttendanceHelper;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.util.Date;

import androidx.annotation.Nullable;

public class AddOverallAttendanceForDayIntentService extends IntentService implements OverallAttendanceHelper.OnOverallDatabaseInitializedListener {

    public AddOverallAttendanceForDayIntentService() {
        super("AddOverallAttendanceForDayIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent.hasExtra(Constances.KEY_SEND_END_DATE_TO_SERVICE_OVERALL)) {
            Date currDate = Converters.convertStringToDate(intent.getStringExtra(Constances.KEY_SEND_END_DATE_TO_SERVICE_OVERALL));
            OverallAttendanceHelper.addDataInOverallAttendance(this, currDate);
        } else {
            Toast.makeText(getApplicationContext(), "Error occured updating overall database", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onOverallDatabaseinitilazed() {
        //TODO
    }
}
