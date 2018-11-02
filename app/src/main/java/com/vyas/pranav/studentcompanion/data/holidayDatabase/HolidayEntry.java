package com.vyas.pranav.studentcompanion.data.holidayDatabase;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Holidays")
public class HolidayEntry {
    @PrimaryKey
    @NonNull
    Date holidayDate;
    String holidayName;
    String holidayDay;

    @Ignore
    public HolidayEntry() {
    }

    public HolidayEntry(Date holidayDate, String holidayName, String holidayDay) {
        this.holidayDate = holidayDate;
        this.holidayName = holidayName;
        this.holidayDay = holidayDay;
    }

    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public String getHolidayDay() {
        return holidayDay;
    }

    public void setHolidayDay(String holidayDay) {
        this.holidayDay = holidayDay;
    }
}
