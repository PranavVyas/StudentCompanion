package com.vyas.pranav.studentcompanion.jobs;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.evernote.android.job.DailyJob;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.asyntasks.OverallAttendanceAsyncTask;
import com.vyas.pranav.studentcompanion.extrautils.Converters;
import com.vyas.pranav.studentcompanion.widget.ShowSubjectAppWidget;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.vyas.pranav.studentcompanion.extrautils.ServiceUtils.setTodaysAttendanceInSharedPref;

public class DailyExecutingJobs extends DailyJob implements OverallAttendanceAsyncTask.OnOverallAttendanceAddedListener {
    public static final String TAG = "DailyExecutingJobs";

    public static void scheduleDailyJob() {
        if (!JobManager.instance().getAllJobRequestsForTag(TAG).isEmpty()) {
            Logger.d("Skipping starting of daily tasks as it has started already");
            return;
        }
        Logger.d("Starting new Daily executing job as old was not working fine");
        JobRequest.Builder builder = new JobRequest.Builder(TAG).setUpdateCurrent(true);
        long startTime = TimeUnit.HOURS.toMillis(0) + TimeUnit.MINUTES.toMillis(0);
        long endTime = startTime + TimeUnit.MINUTES.toMillis(20);
        DailyJob.schedule(builder, startTime, endTime);
    }

    public static void cancleJob() {
        if (!JobManager.instance().getAllJobRequestsForTag(TAG).isEmpty()) {
            JobManager.instance().cancelAllForTag(TAG);
        }
    }

    @NonNull
    @Override
    protected DailyJobResult onRunDailyJob(@NonNull Params params) {
        updateOverallDatabase();
        setTodaysAttendanceInSharedPref(getContext());
        ShowSubjectAppWidget.UpdateWidgetNow(getContext().getApplicationContext());
        //showNotification("DONE DAIY JOBS");
        return DailyJobResult.SUCCESS;
    }

    public void updateOverallDatabase() {
        OverallAttendanceAsyncTask addOverallAttendance = new OverallAttendanceAsyncTask(getContext(), this);
        addOverallAttendance.setCurrDate(new Date());
        addOverallAttendance.execute();
    }

    public void showNotification(String content) {
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
                .setContentTitle("JOB")
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.getContext());
        notificationManager.notify(264, mBuilder.build());
    }

    @Override
    public void OnOverallAttendanceAdded() {
        showNotification(getContext().getString(R.string.java_overall_attendance_job_notification) + Converters.convertDateToString(new Date()));
    }
}
