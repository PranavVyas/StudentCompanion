package com.vyas.pranav.studentcompanion.prefences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;
import com.vyas.pranav.studentcompanion.jobs.DailyReminderCreator;

import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class AppSettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.app_settings);
        setTimepickerStateFromPrefs();
        setTimePrefSummery(getString(R.string.pref_time_key_set_reminder));
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        // Try if the preference is one of our custom Preferences
        DialogFragment dialogFragment = null;
        if (preference instanceof TimePreference) {
            // Create a new instance of TimePreferenceDialogFragment with the key of the relatee Preference
            dialogFragment = TimePreferenceDialogFragmentCompat
                    .getNewInstance(preference.getKey());
        }

        // If it was one of our custom Preferences, show its dialog
        if (dialogFragment != null) {
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(this.getFragmentManager(),
                    "android.support.v7.preference.PreferenceFragment.DIALOG");
        }
        // Could not be handled here. Try with the super method.
        else {
            super.onDisplayPreferenceDialog(preference);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        setTimePrefSummery(s);
        if (s.equals(getString(R.string.pref_switch_key_reminder))) {
            setTimepickerStateFromPrefs();
        } else if (s.equals(getString(R.string.pref_time_key_set_reminder))) {
            setUpDailyReminders(getTimeFromSharedPrefences(sharedPreferences));
        }
    }

    private void setTimePrefSummery(String key) {
        //show time from the sharedprefs
        Preference preference = getPreferenceManager().findPreference(key);
        if (preference != null) {
            if (preference instanceof TimePreference) {
                TimePreference mTimePreference = (TimePreference) preference;
                int time = getTimeFromSharedPrefences(getPreferenceScreen().getSharedPreferences());
                String timeStr = Converters.convertTimeIntInString(time);
                Toast.makeText(getContext(), "New Time is " + timeStr, Toast.LENGTH_SHORT).show();
                mTimePreference.setSummary(timeStr);
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    public void setTimepickerStateFromPrefs() {
        boolean isEnabled = getPreferenceScreen().getSharedPreferences().getBoolean(getString(R.string.pref_switch_key_reminder), false);
        if (isEnabled) {
            findPreference(getString(R.string.pref_time_key_set_reminder)).setEnabled(true);
        } else {
            DailyReminderCreator.cancelReminder();
            findPreference(getString(R.string.pref_time_key_set_reminder)).setEnabled(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    public void setUpDailyReminders(int time) {
        String timeStr = Converters.convertTimeIntInString(time);
        DailyReminderCreator.sheduleJob(timeStr);
    }

    private int getTimeFromSharedPrefences(SharedPreferences mPrefs) {
        return mPrefs.getInt(getString(R.string.pref_time_key_set_reminder), 0);
    }

}
