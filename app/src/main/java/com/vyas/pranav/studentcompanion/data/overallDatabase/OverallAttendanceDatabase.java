package com.vyas.pranav.studentcompanion.data.overallDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = OverallAttendanceEntry.class, version = 1, exportSchema = false)
public abstract class OverallAttendanceDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DB_NAME = "OverallAttendanceDB";
    private static OverallAttendanceDatabase sInstance;

    public static OverallAttendanceDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext()
                        , OverallAttendanceDatabase.class, DB_NAME).build();
            }
            return sInstance;
        }
        return sInstance;
    }

    public abstract OverallAttandanceDao overallAttandanceDao();
}
