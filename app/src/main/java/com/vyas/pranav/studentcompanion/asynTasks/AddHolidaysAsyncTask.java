package com.vyas.pranav.studentcompanion.asynTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.vyas.pranav.studentcompanion.data.firebase.HolidayFetcher;
import com.vyas.pranav.studentcompanion.data.firebase.HolidayModel;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayDatabase;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.util.ArrayList;
import java.util.List;

/*AsyncTask to perform adding holidays in database when app is run first time in background*/
public class AddHolidaysAsyncTask extends AsyncTask<Void, Void, Void> {

    private DataSnapshot data;
    private HolidayDatabase mHolidayDb;
    private HolidayFetcher.OnHolidayFechedListener mCallback;

    public AddHolidaysAsyncTask(Context context, HolidayFetcher.OnHolidayFechedListener mCallback) {
        mHolidayDb = HolidayDatabase.getsInstance(context);
        this.mCallback = mCallback;
    }

    /*
     * Getting Datasnapshots from firebase Data fetcher activity*/
    public void setDataSnapShot(DataSnapshot data) {
        this.data = data;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Iterable<DataSnapshot> iterable = data.getChildren();
        List<HolidayEntry> holidays = new ArrayList<>();
        while (iterable.iterator().hasNext()) {
            HolidayModel currHoliday = iterable.iterator().next().getValue(HolidayModel.class);
            HolidayEntry tempHoliday = new HolidayEntry();
            tempHoliday.setHolidayDate(Converters.convertStringToDate(currHoliday.getDate()));
            tempHoliday.setHolidayName(currHoliday.getName());
            tempHoliday.setHolidayDay(Converters.getDayOfWeek(currHoliday.getDate()));
            holidays.add(tempHoliday);
            //TODO BUG Solve problem of loading multiple times
        }
        //Adding holidays to database
        mHolidayDb.holidayDao().insertAllHolidays(holidays);
        return null;
    }

    /*
     * To Notify FirstRunActivity when the holidays are fetched and added to the database*/
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mCallback.OnHolidayFeched();
    }

}
