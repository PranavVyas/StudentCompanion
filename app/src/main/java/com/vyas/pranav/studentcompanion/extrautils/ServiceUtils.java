package com.vyas.pranav.studentcompanion.extrautils;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableDatabase;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableEntry;

import java.util.Date;

/**
 * The type Service utils.
 * Contains the utilities for the Services in app
 */
public class ServiceUtils {

    /**
     * Sets todays attendance in shared pref.
     *
     * @param context the context
     */
    public static void setTodaysAttendanceInSharedPref(Context context) {
        TimetableEntry mEntry = TimetableDatabase.getInstance(context).timetableDao().getTimetableForDay(Converters.getDayOfWeek(new Date()));
        Gson gson = new Gson();
        String timetableDayJson = gson.toJson(mEntry);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(Constances.KEY_TODAY_TIMETABLE_STRING, timetableDayJson).apply();
    }
}
