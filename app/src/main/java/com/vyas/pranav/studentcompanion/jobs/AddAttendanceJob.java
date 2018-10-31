package com.vyas.pranav.studentcompanion.jobs;

import android.app.Notification;
import android.graphics.Color;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.vyas.pranav.studentcompanion.R;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AddAttendanceJob extends Job {

    private static final String TAG = "AddAttendanceJob";
    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        Notification notification = new NotificationCompat.Builder(getContext())
                .setContentTitle("Android Job Demo")
                .setContentText("Notification from Android Job Demo App.")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setShowWhen(true)
                .setColor(Color.RED)
                .setLocalOnly(true)
                .build();

        NotificationManagerCompat.from(getContext())
                .notify(new Random().nextInt(), notification);
        return Result.SUCCESS;
    }

    public void sheduleJobDaily(){
        new JobRequest.Builder(AddAttendanceJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }
}
