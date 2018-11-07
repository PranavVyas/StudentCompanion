package com.vyas.pranav.studentcompanion.extraUtils;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableDatabase;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableEntry;

import java.util.Date;

public class ServiceUtils {

    public static void setTodaysAttendanceInSharedPref(Context context) {
        TimetableEntry mEntry = TimetableDatabase.getInstance(context).timetableDao().getTimetableForDay(Converters.getDayOfWeek(new Date()));
        Gson gson = new Gson();
        String timetableDayJson = gson.toJson(mEntry);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(Constances.KEY_TODAY_TIMETABLE_STRING, timetableDayJson).apply();
    }
}
