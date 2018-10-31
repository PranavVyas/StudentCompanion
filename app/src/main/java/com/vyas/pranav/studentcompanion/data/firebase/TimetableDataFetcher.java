package com.vyas.pranav.studentcompanion.data.firebase;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableDatabase;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableEntry;

import androidx.annotation.NonNull;

public class TimetableDataFetcher {

    public static void getTimetableFromFirebase(Context context){

        final FirebaseDatabase mDb;
        final TimetableDatabase mTDB = TimetableDatabase.getInstance(context);
        DatabaseReference mRef;

        mDb = FirebaseDatabase.getInstance();
        mRef = mDb.getReference();
        Logger.addLogAdapter(new AndroidLogAdapter());

        ValueEventListener mListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //TODO Improvise
                for(DataSnapshot daysSnapshot : dataSnapshot.getChildren()){
                    DayModel currDay = daysSnapshot.getValue(DayModel.class);
                    TimetableEntry tempDay = new TimetableEntry();
                    tempDay.setDay(daysSnapshot.getKey());
                    tempDay.setLacture1Name(currDay.getLecture1());
                    tempDay.setLacture2Name(currDay.getLecture2());
                    tempDay.setLacture3Name(currDay.getLecture3());
                    tempDay.setLacture4Name(currDay.getLecture4());
                    tempDay.setLacture1Faculty(currDay.getFaculty1());
                    tempDay.setLacture2Faculty(currDay.getFaculty2());
                    tempDay.setLacture3Faculty(currDay.getFaculty3());
                    tempDay.setLacture4Faculty(currDay.getFaculty4());
                    mTDB.timetableDao().insertTimeTableEntry(tempDay);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mRef.child("TimeTable").addListenerForSingleValueEvent(mListener);
    }
}
