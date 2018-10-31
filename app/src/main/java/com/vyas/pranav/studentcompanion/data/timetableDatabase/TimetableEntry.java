package com.vyas.pranav.studentcompanion.data.timetableDatabase;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "TimeTable")
public class TimetableEntry {
    @PrimaryKey(autoGenerate = true)
    int _ID;
    String day;
    String lacture1Name;
    String lacture1Faculty;
    String lacture2Name;
    String lacture2Faculty;
    String lacture3Name;
    String lacture3Faculty;
    String lacture4Name;
    String lacture4Faculty;

    @Ignore
    public TimetableEntry() {
    }
    @Ignore
    public TimetableEntry(String day, String lacture1Name, String lacture1Faculty, String lacture2Name, String lacture2Faculty, String lacture3Name, String lacture3Faculty, String lacture4Name, String lacture4Faculty) {
        this.day = day;
        this.lacture1Name = lacture1Name;
        this.lacture1Faculty = lacture1Faculty;
        this.lacture2Name = lacture2Name;
        this.lacture2Faculty = lacture2Faculty;
        this.lacture3Name = lacture3Name;
        this.lacture3Faculty = lacture3Faculty;
        this.lacture4Name = lacture4Name;
        this.lacture4Faculty = lacture4Faculty;
    }

    public TimetableEntry(int _ID, String day, String lacture1Name, String lacture1Faculty, String lacture2Name, String lacture2Faculty, String lacture3Name, String lacture3Faculty, String lacture4Name, String lacture4Faculty) {
        this._ID = _ID;
        this.day = day;
        this.lacture1Name = lacture1Name;
        this.lacture1Faculty = lacture1Faculty;
        this.lacture2Name = lacture2Name;
        this.lacture2Faculty = lacture2Faculty;
        this.lacture3Name = lacture3Name;
        this.lacture3Faculty = lacture3Faculty;
        this.lacture4Name = lacture4Name;
        this.lacture4Faculty = lacture4Faculty;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLacture1Name() {
        return lacture1Name;
    }

    public void setLacture1Name(String lacture1Name) {
        this.lacture1Name = lacture1Name;
    }

    public String getLacture1Faculty() {
        return lacture1Faculty;
    }

    public void setLacture1Faculty(String lacture1Faculty) {
        this.lacture1Faculty = lacture1Faculty;
    }

    public String getLacture2Name() {
        return lacture2Name;
    }

    public void setLacture2Name(String lacture2Name) {
        this.lacture2Name = lacture2Name;
    }

    public String getLacture2Faculty() {
        return lacture2Faculty;
    }

    public void setLacture2Faculty(String lacture2Faculty) {
        this.lacture2Faculty = lacture2Faculty;
    }

    public String getLacture3Name() {
        return lacture3Name;
    }

    public void setLacture3Name(String lacture3Name) {
        this.lacture3Name = lacture3Name;
    }

    public String getLacture3Faculty() {
        return lacture3Faculty;
    }

    public void setLacture3Faculty(String lacture3Faculty) {
        this.lacture3Faculty = lacture3Faculty;
    }

    public String getLacture4Name() {
        return lacture4Name;
    }

    public void setLacture4Name(String lacture4Name) {
        this.lacture4Name = lacture4Name;
    }

    public String getLacture4Faculty() {
        return lacture4Faculty;
    }

    public void setLacture4Faculty(String lacture4Faculty) {
        this.lacture4Faculty = lacture4Faculty;
    }
}
