package com.vyas.pranav.studentcompanion.data.attendenceDatabase;


import android.content.Context;

import com.vyas.pranav.studentcompanion.extraUtils.DateConverter;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = AttendanceIndividualEntry.class, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AttendanceIndividualDatabase extends RoomDatabase {

    public static final Object LOCK = new Object();
    public static final String DB_NAME = "Individual Attendance DB";
    public static AttendanceIndividualDatabase sInstance;

    public static AttendanceIndividualDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext()
                        ,AttendanceIndividualDatabase.class,
                        DB_NAME).build();
            }
            return sInstance;
        }
        return sInstance;
    }

    public abstract AttendanceIndividualDao attendanceIndividualDao();
}
