package com.vyas.pranav.studentcompanion.prefences;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.vyas.pranav.studentcompanion.R;

public class AppSettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.app_settings);
    }
}
