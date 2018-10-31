package com.vyas.pranav.studentcompanion.data.holidayDatabase;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Holidays")
public class HolidayEntry {
    @PrimaryKey(autoGenerate = true)
    int _ID;
    @NonNull
    Date holidayDate;
    String holidayName;
    String holidayDay;

    public HolidayEntry(int _ID, Date holidayDate, String holidayName, String holidayDay) {
        this._ID = _ID;
        this.holidayDate = holidayDate;
        this.holidayName = holidayName;
        this.holidayDay = holidayDay;
    }

    @Ignore
    public HolidayEntry(Date holidayDate, String holidayName, String holidayDay) {
        this.holidayDate = holidayDate;
        this.holidayName = holidayName;
        this.holidayDay = holidayDay;
    }

    @Ignore
    public HolidayEntry() {
    }

    public String getHolidayDay() {
        return holidayDay;
    }

    public void setHolidayDay(String holidayDay) {
        this.holidayDay = holidayDay;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
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
}
