package com.vyas.pranav.studentcompanion.jobs;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import com.evernote.android.job.DailyJob;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.dashboard.DashboardActivity;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class DailyReminderCreator extends DailyJob {

    public static final String TAG = "DailyReminderCreator";
    private static final int REQ_OPEN_APP = 100;

    public static void cancelReminder() {
        if (!JobManager.instance().getAllJobRequestsForTag(TAG).isEmpty()) {
            JobManager.instance().cancelAllForTag(TAG);
            Logger.d("Reminder Cancelled");
        }
    }

    public static void sheduleJob(String timeStr) {
        Converters.CustomTime mCustomTime = Converters.extractHourandMinFromTime(timeStr);
        int startHour = mCustomTime.getHour();
        int startMin = mCustomTime.getMin();
        Logger.addLogAdapter(new AndroidLogAdapter());
        if (!JobManager.instance().getAllJobRequestsForTag(TAG).isEmpty()) {
            cancelReminder();
        }
        Logger.d("Job is not running now ..starting job now");
        JobRequest.Builder builder = new JobRequest.Builder(TAG).setUpdateCurrent(true);
        long startJob = TimeUnit.HOURS.toMillis(startHour) + TimeUnit.MINUTES.toMillis(startMin);
        long endjob = startJob + TimeUnit.MINUTES.toMillis(0);
        DailyJob.schedule(builder, startJob, endjob);
    }

    @NonNull
    @Override
    protected DailyJobResult onRunDailyJob(@NonNull Params params) {
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
        Intent openAppIntent = new Intent(getContext(), DashboardActivity.class);
        PendingIntent openAppFromNotification = PendingIntent.getActivity(getContext(), REQ_OPEN_APP, openAppIntent, 0);
        NotificationCompat.Action actionOpenAppBuilder = new NotificationCompat.Action.Builder(R.drawable.ic_navigation_dashboard, "Open App", openAppFromNotification).build();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), "NOTIFICATION_MAIN")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Add Attendance Now")
                .setContentText("Hi There! If you have not yet completed the attendance yet ,Better do it now!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(actionOpenAppBuilder)
                .addAction(actionOpenAppBuilder);
        //TODO Set Notification to cancel when tapped on Open App
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(263, mBuilder.build());
    }

}