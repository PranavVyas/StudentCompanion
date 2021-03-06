package com.vyas.pranav.studentcompanion.data.timetableDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = TimetableEntry.class,version = 1,exportSchema = false)
public abstract class TimetableDatabase extends RoomDatabase {

    public static final Object LOCK = new Object();
    public static final String DB_NAME = "TimeTableDB";
    static TimetableDatabase sInstance;

    public static TimetableDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        TimetableDatabase.class, DB_NAME).build();
            }
            return sInstance;
        }else{
            return sInstance;
        }
    }

    public abstract TimetableDao timetableDao();
}
