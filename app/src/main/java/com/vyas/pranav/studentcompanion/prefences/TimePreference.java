package com.vyas.pranav.studentcompanion.prefences;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.vyas.pranav.studentcompanion.R;

import androidx.preference.DialogPreference;

public class TimePreference extends DialogPreference {

    private int mTime;

    public TimePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TimePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, defStyleAttr);
    }

    public TimePreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.dialogPreferenceStyle);
    }

    public TimePreference(Context context) {
        this(context, null);
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        this.mTime = time;

        //Time Saved to Shared Preference
        persistInt(time);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        //Get Default Value of preference
        //If don't have the default value (the value is not set via xml attribute "android:defaultValue : " returns "0" as default value
        return a.getInt(index, 0);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        // Read the value. Use the default value if it is not possible.
        setTime(restorePersistedValue ? getPersistedInt(mTime) : (int) defaultValue);
    }

    @Override
    public int getDialogLayoutResource() {
        return R.layout.pref_dialog_time;
    }
}
