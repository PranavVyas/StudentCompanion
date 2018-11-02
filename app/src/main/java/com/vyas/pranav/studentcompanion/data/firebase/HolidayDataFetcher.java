package com.vyas.pranav.studentcompanion.data.firebase;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayDatabase;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import androidx.annotation.NonNull;

public class HolidayDataFetcher{

    static FirebaseDatabase mDb;
    static DatabaseReference mRef;
    static HolidayDatabase mHolidayDb;
    static ValueEventListener mListener;

    public static void fetchHolidays(final Context context){
        Logger.addLogAdapter(new AndroidLogAdapter());

        mDb = FirebaseDatabase.getInstance();
        mRef = mDb.getReference();
        mHolidayDb = HolidayDatabase.getsInstance(context);

        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                //HolidayHelper helper = new HolidayHelper(context);
                while(iterable.iterator().hasNext()){
                    HolidayModel currHoliday = iterable.iterator().next().getValue(HolidayModel.class);
                    HolidayEntry tempHoliday = new HolidayEntry();
                    tempHoliday.setHolidayDate(Converters.convertStringToDate(currHoliday.getDate()));
                    tempHoliday.setHolidayName(currHoliday.getName());
                    tempHoliday.setHolidayDay(Converters.getDayOfWeek(currHoliday.getDate()));
                    mHolidayDb.holidayDao().insertHoliday(tempHoliday);
                    //TODO BUG Solve problem of loading multiple times
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Logger.d("Connection Cancelled");
            }
        };
        mRef.child("Holidays").addListenerForSingleValueEvent(mListener);
        mRef.child("Holidays").removeEventListener(mListener);
    }
}
