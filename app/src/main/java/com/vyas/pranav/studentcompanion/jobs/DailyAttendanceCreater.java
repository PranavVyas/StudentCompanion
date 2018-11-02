package com.vyas.pranav.studentcompanion.jobs;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.evernote.android.job.DailyJob;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;
import com.vyas.pranav.studentcompanion.services.AddEmptyAttendanceIntentService;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class DailyAttendanceCreater extends DailyJob {

    public static final String TAG = "DailyAttendanceCreater";

    public static void schedule(int startTimeHour, int startTimeMin, int endTimeHour, int endTimeMin) {
        //TODO Throw a error when the starttime is less than end time
        Logger.addLogAdapter(new AndroidLogAdapter());
        if (!JobManager.instance().getAllJobRequestsForTag(TAG).isEmpty()) {
            Logger.d("Job is running already...Skipping Setting...");
            return;
        }
        Logger.d("Job is not running now ..starting job now");
        JobRequest.Builder builder = new JobRequest.Builder(TAG).setUpdateCurrent(true);
        long startJob = TimeUnit.HOURS.toMillis(startTimeHour) + TimeUnit.MINUTES.toMillis(startTimeMin);
        long endjob = TimeUnit.MINUTES.toMillis(endTimeHour) + TimeUnit.MINUTES.toMillis(endTimeMin);
        DailyJob.schedule(builder, startJob, endjob);
        //TODO Check if job is set true even after Reboot
    }

    public static void cancelDailyJob() {
        if (!JobManager.instance().getAllJobRequestsForTag(TAG).isEmpty()) {
            JobManager.instance().cancelAllForTag(TAG);
        }
    }

    @NonNull
    @Override
    protected DailyJobResult onRunDailyJob(@NonNull Params params) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d("Executing Job Now...");
        startServiceToAddAttendance();
        showNotification();
        return DailyJobResult.SUCCESS;
    }

    public void showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MainChannel";
            String description = "Show Main Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("NOTIFICATION_MAIN", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), "NOTIFICATION_MAIN")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Done!")
                .setContentText("Added Empty Attendance For Today")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(263, mBuilder.build());
    }

    //Fires an Intent to start Service to add attendance in the today's date at given time
    public void startServiceToAddAttendance() {
        Intent addAttendance = new Intent(getContext(), AddEmptyAttendanceIntentService.class);
        Bundle sendData = new Bundle();
        Date date = new Date();
        sendData.putString(Constances.KEY_SEND_DATE_TO_SERVICE, Converters.convertDateToString(date));
        addAttendance.putExtras(sendData);
        getContext().startService(addAttendance);
    }
}
