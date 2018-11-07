package com.vyas.pranav.studentcompanion.asynTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.data.firebase.HolidayFetcher;
import com.vyas.pranav.studentcompanion.data.firebase.HolidayModel;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayDatabase;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.util.ArrayList;
import java.util.List;

public class AddHolidaysAsyncTask extends AsyncTask<Void, Void, Void> {

    Context context;
    DataSnapshot data;
    HolidayDatabase mHolidayDb;
    HolidayFetcher.OnHolidayFechedListener mCallback;

    public AddHolidaysAsyncTask(Context context) {
        this.context = context;
        mHolidayDb = HolidayDatabase.getsInstance(context);
        mCallback = (HolidayFetcher.OnHolidayFechedListener) context;
    }

    public void setDataSnapShot(DataSnapshot data) {
        this.data = data;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Iterable<DataSnapshot> iterable = data.getChildren();
        //HolidayHelper helper = new HolidayHelper(context);
        List<HolidayEntry> holidays = new ArrayList<>();
        while (iterable.iterator().hasNext()) {
            HolidayModel currHoliday = iterable.iterator().next().getValue(HolidayModel.class);
            HolidayEntry tempHoliday = new HolidayEntry();
            tempHoliday.setHolidayDate(Converters.convertStringToDate(currHoliday.getDate()));
            tempHoliday.setHolidayName(currHoliday.getName());
            tempHoliday.setHolidayDay(Converters.getDayOfWeek(currHoliday.getDate()));
            holidays.add(tempHoliday);
            Logger.d("This is to check which thread is this?");
            //TODO BUG Solve problem of loading multiple times
        }
        mHolidayDb.holidayDao().insertAllHolidays(holidays);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mCallback.OnHolidayFeched();
    }

}
