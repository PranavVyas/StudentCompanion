package com.vyas.pranav.studentcompanion.data.firebase;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.asyntasks.AddTimetableAsyncTask;

import androidx.annotation.NonNull;

public class TimetableDataFetcher {

    private Context context;
    private OnTimeTableReceived mCallback;
    private FirebaseDatabase mDb;
    private DatabaseReference mRef;
    public AddTimetableAsyncTask addTimetableAsyncTask;


    public TimetableDataFetcher(Context context, OnTimeTableReceived mCallback) {
        this.context = context;
        this.mCallback = mCallback;
        mDb = FirebaseDatabase.getInstance();
        mRef = mDb.getReference();
    }

    public void fetchTimetable() {
        ValueEventListener mListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addTimetableAsyncTask = new AddTimetableAsyncTask(context);
                addTimetableAsyncTask.setDataSnapshot(dataSnapshot);
                addTimetableAsyncTask.execute();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Logger.d("Error Occureed : " + databaseError.getMessage());
                Toast.makeText(context, "" + databaseError.getDetails(), Toast.LENGTH_SHORT).show();
            }
        };
        mRef.child("TimeTable").addListenerForSingleValueEvent(mListener);
    }

    public void cancelFetching() {
        addTimetableAsyncTask.cancel(true);
    }

    public interface OnTimeTableReceived {
        void OnTimetableReceived();
    }
}
