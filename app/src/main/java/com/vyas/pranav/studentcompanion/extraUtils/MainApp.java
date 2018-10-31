package com.vyas.pranav.studentcompanion.extraUtils;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.vyas.pranav.studentcompanion.jobs.DemoJobCreator;

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new DemoJobCreator());
        //TODO Add JobCreator
    }
}
