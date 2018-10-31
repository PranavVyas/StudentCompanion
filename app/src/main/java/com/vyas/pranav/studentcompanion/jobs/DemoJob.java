package com.vyas.pranav.studentcompanion.jobs;

import com.evernote.android.job.Job;

import androidx.annotation.NonNull;

public class DemoJob extends Job {

    public static final String TAG = "DemoJob";
    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        return null;
    }
}
