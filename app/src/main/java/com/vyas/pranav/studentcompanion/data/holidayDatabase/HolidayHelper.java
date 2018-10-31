package com.vyas.pranav.studentcompanion.data.holidayDatabase;

import android.content.Context;

import com.vyas.pranav.studentcompanion.extraUtils.AppExecutors;

import java.util.List;

public class HolidayHelper {

    HolidayDatabase mDb;
    AppExecutors mExecutors;

    public HolidayHelper(Context context) {
        mDb = HolidayDatabase.getsInstance(context);
        mExecutors = AppExecutors.getInstance();
    }

    public void insertData(final HolidayEntry holidayEntry){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.holidayDao().insertHoliday(holidayEntry);
            }
        });
    }

    public void getAllAttendance(List<HolidayEntry> allHolidays){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //allHolidays = mDb.HolidayDao().getAllHolidays();
            }
        });
    }
}
