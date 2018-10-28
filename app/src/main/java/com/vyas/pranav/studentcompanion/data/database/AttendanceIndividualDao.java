package com.vyas.pranav.studentcompanion.data.database;

import java.util.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AttendanceIndividualDao {

    @Query("SELECT * FROM IndividualAttandance")
    List<AttendanceIndividualEntry> getAllAttendance();

    @Query("SELECT * FROM IndividualAttandance WHERE date = :date")
    List<AttendanceIndividualEntry> getAttendanceForDate(Date date);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAttendance(AttendanceIndividualEntry newAttendanceEntry);

    @Insert
    void insertAttendance(AttendanceIndividualEntry newAttendanceEntry);

    @Delete
    void deleteAttendance(AttendanceIndividualEntry attendanceEntry);

    //TODO CHeck here
    @Query("DELETE FROM IndividualAttandance")
    void deleteAllAttendance();
}
