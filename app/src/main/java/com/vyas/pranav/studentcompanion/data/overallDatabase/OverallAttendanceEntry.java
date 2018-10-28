package com.vyas.pranav.studentcompanion.data.overallDatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "OverallAttendance")
public class OverallAttendanceEntry {
    @PrimaryKey(autoGenerate = true)
    private long _ID;
    @NonNull
    private String subjectName;
    private double percentPresent;
    private int totalDays;
    private int daysAvailableToBunk;
    private int daysBunked;

    @Ignore
    public OverallAttendanceEntry() {
    }

    @Ignore
    public OverallAttendanceEntry(String subjectName, double percentPresent, int totalDays, int daysAvailableToBunk, int daysBunked) {
        this.subjectName = subjectName;
        this.percentPresent = percentPresent;
        this.totalDays = totalDays;
        this.daysAvailableToBunk = daysAvailableToBunk;
        this.daysBunked = daysBunked;
    }

    public OverallAttendanceEntry(long _ID, String subjectName, double percentPresent, int totalDays, int daysAvailableToBunk, int daysBunked) {
        this._ID = _ID;
        this.subjectName = subjectName;
        this.percentPresent = percentPresent;
        this.totalDays = totalDays;
        this.daysAvailableToBunk = daysAvailableToBunk;
        this.daysBunked = daysBunked;
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getPercentPresent() {
        return percentPresent;
    }

    public void setPercentPresent(double percentPresent) {
        this.percentPresent = percentPresent;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getDaysAvailableToBunk() {
        return daysAvailableToBunk;
    }

    public void setDaysAvailableToBunk(int daysAvailableToBunk) {
        this.daysAvailableToBunk = daysAvailableToBunk;
    }

    public int getDaysBunked() {
        return daysBunked;
    }

    public void setDaysBunked(int daysBunked) {
        this.daysBunked = daysBunked;
    }
}
