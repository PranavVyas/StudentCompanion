package com.vyas.pranav.studentcompanion.extrautils;

import java.util.Date;

import androidx.room.TypeConverter;

/**
 * The type Date converter.
 * Used by the Room database to save data of date
 */
public class DateConverter {
    /**
     * To date date.
     *
     * @param timestamp the timestamp
     * @return the date
     */
    @TypeConverter
    public static Date toDate(Long timestamp){
        return (timestamp == null) ? null : new Date(timestamp);
    }

    /**
     * To time stamp long.
     *
     * @param date the date
     * @return the long
     */
    @TypeConverter
    public static Long toTimeStamp(Date date){
        return (date == null) ? null : date.getTime();
    }
}
