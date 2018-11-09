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
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayDatabase;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/*
 * Job to execute showing attendance as reminders at given time on workday (Not shown for holidays and Weekends*/
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
        showNotificationIfNotHoliday();
        return DailyJobResult.SUCCESS;
    }

    private void showNotification() {
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
                .setContentTitle(getContext().getString(R.string.java_reminder_notification_title))
                .setContentText(getContext().getString(R.string.java_reminder_notification_msg))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(getOpenAppAction())
                .setAutoCancel(true);
        //TODO BUG Set Notification to cancel when tapped on Open App
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());

        notificationManager.notify(263, mBuilder.build());
    }

    private NotificationCompat.Action getOpenAppAction() {
        Intent openAppIntent = new Intent(getContext(), DashboardActivity.class);
        PendingIntent openAppFromNotification = PendingIntent.getActivity(getContext(), REQ_OPEN_APP, openAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return (new NotificationCompat.Action.Builder(R.drawable.ic_navigation_dashboard, "Open App", openAppFromNotification).build());
    }

    private void showNotificationIfNotHoliday() {
        List<Date> holidays = HolidayDatabase.getsInstance(getContext()).holidayDao().getAllDates();
        if (!holidays.contains(new Date())
                && !Converters.getDayOfWeek(new Date()).equals("Saturday")
                && !Converters.getDayOfWeek(new Date()).equals("Sunday")) {
            showNotification();
        }
    }
}
