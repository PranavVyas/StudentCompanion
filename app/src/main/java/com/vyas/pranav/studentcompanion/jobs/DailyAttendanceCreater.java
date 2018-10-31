package com.vyas.pranav.studentcompanion.jobs;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.evernote.android.job.DailyJob;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.R;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class DailyAttendanceCreater extends DailyJob {

    public static final String TAG = "DailyAttendanceCreater";

    public static void schedule(){
        Logger.addLogAdapter(new AndroidLogAdapter());
        if(!JobManager.instance().getAllJobRequestsForTag(TAG).isEmpty()){
           Logger.d("Job is running already.....Cancelling Now...");
           JobManager.instance().cancelAll();
        }
        Logger.d("Job is not running now ..starting job now");
        JobRequest.Builder builder = new JobRequest.Builder(TAG);
        long startJob = TimeUnit.HOURS.toMillis(18)+TimeUnit.MINUTES.toMillis(25);
        long endjob = startJob + TimeUnit.MINUTES.toMillis(1);
        DailyJob.schedule(builder,startJob,endjob);
    }

    @NonNull
    @Override
    protected DailyJobResult onRunDailyJob(@NonNull Params params) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d("Executing Job Now...");
        showNotification();
        return DailyJobResult.SUCCESS;
    }

    public void showNotification(){
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
                .setContentTitle("Title")
                .setContentText("Job Done")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(263, mBuilder.build());
    }
}
