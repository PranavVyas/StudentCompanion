package com.vyas.pranav.studentcompanion.firstRun;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;

public class DatePickerFrag extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private OnSelectedStartDateListener mCallback;

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        if (getTag().equals("StartDate")) {
            Toast.makeText(getContext(), "Start Date : " + i + "month" + i1 + "Year " + i2, Toast.LENGTH_SHORT).show();
            mCallback.OnSelectedStartDate(Converters.formatDateStringfromCalender(i2, i1 + 1, i));
        } else if (getTag().equals("EndDate")) {
            Toast.makeText(getContext(), "End Date : " + i + "month" + i1 + "Year " + i2, Toast.LENGTH_SHORT).show();
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

    public interface OnSelectedStartDateListener {
        void OnSelectedStartDate(String dateStr);

        void OnSelectedEndDate(String dateStr);
    }
}
