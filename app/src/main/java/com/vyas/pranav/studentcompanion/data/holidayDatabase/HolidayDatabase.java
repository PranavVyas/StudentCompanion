package com.vyas.pranav.studentcompanion.data.holidayDatabase;

import android.content.Context;

import com.vyas.pranav.studentcompanion.extraUtils.DateConverter;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = HolidayEntry.class,version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class HolidayDatabase extends RoomDatabase {

    public static final Object LOCK = new Object();
    public static final String DB_NAME = "HolidayDB";
    static HolidayDatabase sInstance;

    public static HolidayDatabase getsInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext()
                ,HolidayDatabase.class,DB_NAME).allowMainThreadQueries().build();
            }
            return sInstance;
        }
        return sInstance;
    }

    public abstract HolidayDao holidayDao();
}
