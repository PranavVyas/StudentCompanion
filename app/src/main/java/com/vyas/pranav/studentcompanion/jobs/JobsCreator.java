package com.vyas.pranav.studentcompanion.jobs;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*
 * Returns Appropriate job when init jobs*/
public class JobsCreator implements JobCreator {
    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        switch (tag) {
            case DailyReminderCreator.TAG:
                return new DailyReminderCreator();

            case DailyExecutingJobs.TAG:
                return new DailyExecutingJobs();

            default:
                return null;
        }

    }
}
