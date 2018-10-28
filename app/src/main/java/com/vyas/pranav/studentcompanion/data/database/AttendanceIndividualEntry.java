package com.vyas.pranav.studentcompanion.data.database;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "IndividualAttandance")
public class AttendanceIndividualEntry {

    @PrimaryKey(autoGenerate = true)
    private long _ID;
    @NonNull
    private Date date;
    private int lactureNo;
    private String subName;
    private String facultyName;
    private String attended;
    private String lactureType;
    private long durationInMillis;

    @Ignore
    public AttendanceIndividualEntry() {
    }

    public AttendanceIndividualEntry(long _ID, Date date, int lactureNo, String subName, String facultyName, String attended, String lactureType, long durationInMillis) {
        this._ID = _ID;
        this.date = date;
        this.lactureNo = lactureNo;
        this.subName = subName;
        this.facultyName = facultyName;
        this.attended = attended;
        this.lactureType = lactureType;
        this.durationInMillis = durationInMillis;
    }

    @Ignore
    public AttendanceIndividualEntry(Date date, int lactureNo, String subName, String facultyName, String attended, String lactureType, long durationInMillis) {
        this.date = date;
        this.lactureNo = lactureNo;
        this.subName = subName;
        this.facultyName = facultyName;
        this.attended = attended;
        this.lactureType = lactureType;
        this.durationInMillis = durationInMillis;
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLactureNo() {
        return lactureNo;
    }

    public void setLactureNo(int lactureNo) {
        this.lactureNo = lactureNo;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getAttended() {
        return attended;
    }

    public void setAttended(String attended) {
        this.attended = attended;
    }

    public String getLactureType() {
        return lactureType;
    }

    public void setLactureType(String lactureType) {
        this.lactureType = lactureType;
    }

    public long getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }
}
