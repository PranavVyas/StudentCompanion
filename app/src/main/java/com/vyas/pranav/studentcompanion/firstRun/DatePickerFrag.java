package com.vyas.pranav.studentcompanion.firstRun;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;

/**
 * The type Date picker frag.
 * Used to select date of starting of sem and end date of sem
 */
public class DatePickerFrag extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private OnSelectedStartDateListener mCallback;

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        if (getTag().equals("StartDate")) {
            //The dialog is used to set Start date so return callback as starting date selected
            mCallback.OnSelectedStartDate(Converters.formatDateStringfromCalender(i2, i1 + 1, i));
        } else if (getTag().equals("EndDate")) {
            //The dialog is used to set End date so return callback as end date selected
            mCallback.OnSelectedEndDate(Converters.formatDateStringfromCalender(i2, i1 + 1, i));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (OnSelectedStartDateListener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * The interface On selected start date listener.
     */
    public interface OnSelectedStartDateListener {
        /**
         * On selected start date.
         *
         * @param dateStr the date str
         */
        void OnSelectedStartDate(String dateStr);

        /**
         * On selected end date.
         *
         * @param dateStr the date str
         */
        void OnSelectedEndDate(String dateStr);
    }
}
