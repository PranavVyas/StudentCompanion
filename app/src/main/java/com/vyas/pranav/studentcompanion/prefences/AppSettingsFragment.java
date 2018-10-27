package com.vyas.pranav.studentcompanion.prefences;

import android.os.Bundle;

import com.vyas.pranav.studentcompanion.R;

import androidx.preference.PreferenceFragmentCompat;

public class AppSettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.app_settings);
    }
}
