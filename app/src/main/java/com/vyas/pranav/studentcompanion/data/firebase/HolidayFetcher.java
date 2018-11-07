package com.vyas.pranav.studentcompanion.data.firebase;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.asynTasks.AddHolidaysAsyncTask;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayDatabase;

import androidx.annotation.NonNull;

public class HolidayFetcher implements ValueEventListener {

    FirebaseDatabase mDb;
    DatabaseReference mRef;
    HolidayDatabase mHolidayDb;
    Context context;
    OnHolidayFechedListener mCallback;

    public HolidayFetcher(Context context, OnHolidayFechedListener mCallback) {
        this.context = context;
        mDb = FirebaseDatabase.getInstance();
        mRef = mDb.getReference();
        mHolidayDb = HolidayDatabase.getsInstance(context);
        this.mCallback = mCallback;
    }

    public void startFething() {
        mRef.child("Holidays").addListenerForSingleValueEvent(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        AddHolidaysAsyncTask asyncTask = new AddHolidaysAsyncTask(context);
        asyncTask.setDataSnapShot(dataSnapshot);
        asyncTask.execute();
        mRef.child("Holidays").removeEventListener(this);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Logger.d("Connection Cancelled");
    }

    public interface OnHolidayFechedListener {
        void OnHolidayFeched();
    }


}
