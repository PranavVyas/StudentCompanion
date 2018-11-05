package com.vyas.pranav.studentcompanion.services;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualEntry;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableDatabase;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.vyas.pranav.studentcompanion.extraUtils.Constances.KEY_SEND_DATE_TO_SERVICE;
import static com.vyas.pranav.studentcompanion.extraUtils.Constances.VALUE_ABSENT;

public class AddEmptyAttendanceIntentService extends IntentService {

    public static final int NO_OF_LACTURES = 4;
    TimetableDatabase mDb;
    AttendanceIndividualDatabase mAttendanceDb;

    public AddEmptyAttendanceIntentService() {
        super("AddEmptyAttendanceIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mDb = TimetableDatabase.getInstance(getApplicationContext());
        mAttendanceDb = AttendanceIndividualDatabase.getInstance(getApplicationContext());
        if (intent.hasExtra(KEY_SEND_DATE_TO_SERVICE)) {
            String dateStr = intent.getStringExtra(Constances.KEY_SEND_DATE_TO_SERVICE);
            Date date = Converters.convertStringToDate(dateStr);
            String dayOfWeek = Converters.getDayOfWeek(dateStr);
            //Toast.makeText(this, "Days is "+dayOfWeek, Toast.LENGTH_SHORT).show();
            TimetableEntry mTimetable = mDb.timetableDao().getTimetableForDay(dayOfWeek);
            //Logger.d(mTimetable.getLacture1Name());

            List<AttendanceIndividualEntry> mEntries = new ArrayList<>();
            for (int i = 0; i < NO_OF_LACTURES; i++) {
                AttendanceIndividualEntry newEntry = new AttendanceIndividualEntry();
                newEntry.setDate(date);
                newEntry.setAttended(VALUE_ABSENT);
                switch (i + 1) {
                    case 1:
                        newEntry.setSubName(mTimetable.getLacture1Name());
                        newEntry.setFacultyName(mTimetable.getLacture1Faculty());
                        newEntry.set_ID(Converters.generateIdForIndividualAttendance(date, 1));
                        break;

                    case 2:
                        newEntry.setSubName(mTimetable.getLacture2Name());
                        newEntry.setFacultyName(mTimetable.getLacture2Faculty());
                        newEntry.set_ID(Converters.generateIdForIndividualAttendance(date, 2));
                        break;

                    case 3:
                        newEntry.setSubName(mTimetable.getLacture3Name());
                        newEntry.setFacultyName(mTimetable.getLacture3Faculty());
                        newEntry.set_ID(Converters.generateIdForIndividualAttendance(date, 3));
                        break;

                    case 4:
                        newEntry.setSubName(mTimetable.getLacture4Name());
                        newEntry.setFacultyName(mTimetable.getLacture4Faculty());
                        newEntry.set_ID(Converters.generateIdForIndividualAttendance(date, 4));
                        break;
                }
                newEntry.setLactureNo(i + 1);
                newEntry.setDurationInMillis(3600);
                newEntry.setLactureType("L");
                mEntries.add(newEntry);
                //mAttendanceDb.attendanceIndividualDao().insertAttendance(newEntry);
            }
            mAttendanceDb.attendanceIndividualDao().insertAttendances(mEntries);
            showNotification("Added");
        } else {
            showNotification("Null");
        }
    }

    public void showNotification(String title) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Main Channel";
            String description = "Show Main Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("NOTIFICATION_MAIN", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "NOTIFICATION_MAIN")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText("Job Done")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(263, mBuilder.build());
    }
}

