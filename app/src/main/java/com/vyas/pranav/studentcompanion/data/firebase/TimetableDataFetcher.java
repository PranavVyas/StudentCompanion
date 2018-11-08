package com.vyas.pranav.studentcompanion.data.firebase;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.asynTasks.AddTimetableAsyncTask;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableDatabase;

import androidx.annotation.NonNull;

public class TimetableDataFetcher {

    private Context context;
    private OnTimeTableReceived mCallback;
    private FirebaseDatabase mDb;
    private TimetableDatabase mTDB;
    private DatabaseReference mRef;


    public TimetableDataFetcher(Context context, OnTimeTableReceived mCallback) {
        this.context = context;
        this.mCallback = mCallback;
        mTDB = TimetableDatabase.getInstance(context);
        mDb = FirebaseDatabase.getInstance();
        mRef = mDb.getReference();
    }

    public void fetchTimetable() {
        ValueEventListener mListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AddTimetableAsyncTask addTimetableAsyncTask = new AddTimetableAsyncTask(context);
                addTimetableAsyncTask.setDataSnapshot(dataSnapshot);
                addTimetableAsyncTask.execute();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Logger.d("Error Occureed : " + databaseError.getMessage());
                Toast.makeText(context, "Error : " + databaseError.getDetails(), Toast.LENGTH_SHORT).show();
            }
        };
        mRef.child("TimeTable").addListenerForSingleValueEvent(mListener);
    }

    public interface OnTimeTableReceived {
        void OnTimetableReceived();
    }
}
