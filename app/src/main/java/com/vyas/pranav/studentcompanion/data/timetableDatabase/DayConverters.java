package com.vyas.pranav.studentcompanion.data.timetableDatabase;

import androidx.room.TypeConverter;

public class DayConverters {
    @TypeConverter
    public static String toDay(String day) {
        return "1" + day;
    }
}
