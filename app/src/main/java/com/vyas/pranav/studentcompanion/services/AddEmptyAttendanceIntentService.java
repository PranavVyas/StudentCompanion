package com.vyas.pranav.studentcompanion.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceHelper;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import androidx.annotation.Nullable;

public class AddEmptyAttendanceIntentService extends IntentService {
    public AddEmptyAttendanceIntentService() {
        super("AddEmptyAttendanceIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle receivedData = intent.getExtras();
        if(receivedData != null){
            String dateStr = receivedData.getString(Constances.KEY_SEND_DATE_TO_SERVICE);
            AttendanceHelper helper = new AttendanceHelper(getApplicationContext());
            AttendanceIndividualEntry newAttendanceEntry = new AttendanceIndividualEntry();
            newAttendanceEntry.setDate(Converters.convertStringToDate(dateStr));
            helper.insertAttendanceForDate(newAttendanceEntry);
        }
    }
}
