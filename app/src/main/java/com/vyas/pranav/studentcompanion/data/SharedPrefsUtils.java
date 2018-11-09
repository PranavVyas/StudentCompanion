package com.vyas.pranav.studentcompanion.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.vyas.pranav.studentcompanion.R;

public class SharedPrefsUtils {

    public static final String KEY_FIRST_TIME_APP = "FirstTimeUsingApp";
    public static final boolean DEFAULT_FIRST_RUN = false;

    public static boolean isFirstTimeRunApp(Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return !mPrefs.getBoolean(KEY_FIRST_TIME_APP, false);
    }

    public static void setFirstTimeRunApp(Context context, boolean isFirstTimeValue) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putBoolean(KEY_FIRST_TIME_APP, isFirstTimeValue);
        mEditor.apply();
    }

    public static boolean isFirstTimeRunActivity(Context context, String activityName) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return !mPrefs.getBoolean(KEY_FIRST_TIME_APP + activityName, false);
    }

    public static void setFirstTimeRunActivity(Context context, String activityName, boolean isFirstTimeRunActivityValue) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putBoolean(KEY_FIRST_TIME_APP + activityName, isFirstTimeRunActivityValue);
        mEditor.apply();
    }

    public static void setThemeOfUser(Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (mPrefs.getBoolean(context.getString(R.string.pref_switch_key_dark_theme), false)) {
            context.setTheme(R.style.AppTheme_Dark);
        } else {
            context.setTheme(R.style.AppTheme);
        }
    }

}
