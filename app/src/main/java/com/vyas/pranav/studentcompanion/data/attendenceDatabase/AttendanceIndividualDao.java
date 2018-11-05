package com.vyas.pranav.studentcompanion.data.attendenceDatabase;

import java.util.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface AttendanceIndividualDao {

    @Query("SELECT * FROM IndividualAttandance WHERE date = :date ORDER BY _ID")
    List<AttendanceIndividualEntry> getAttendanceForDate(Date date);

    @Query("SELECT COUNT(_ID) FROM individualattandance WHERE subName = :subName")
    int getTotalDaysForSubject(String subName);

    @Query("SELECT COUNT(_ID) FROM individualattandance WHERE subName = :subName AND attended = :attended")
    int getAttendedDays(String subName, String attended);


    @Query("SELECT COUNT(_ID) FROM individualattandance WHERE subName = :subName AND date <= :date")
    int getElapsedDaysForSubject(String subName, Date date);

    @Query("SELECT COUNT(_ID) FROM IndividualAttandance WHERE subName = :subName AND date <= :endDate AND date >= :startDate")
    int getDatesBetweenForSubject(String subName, Date startDate, Date endDate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAttendances(List<AttendanceIndividualEntry> attendanceEnries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAttendance(AttendanceIndividualEntry newAttendanceEntry);

    @Query("DELETE FROM IndividualAttandance")
    void deleteAllAttendance();

    //    @Query("SELECT * FROM IndividualAttandance ORDER BY _ID")
    //    List<AttendanceIndividualEntry> getAllAttendance();

//    @Query("SELECT * FROM IndividualAttandance WHERE subName = :subName ORDER BY _ID")
//    List<AttendanceIndividualEntry> getAttendanceForSubject(String subName);

//    @Query("SELECT * FROM IndividualAttandance WHERE subName = :subName AND attended = :attended ORDER BY _ID")
//    List<AttendanceIndividualEntry> getAttendanceForSubject(String subName, String attended);

//
//    @Query("SELECT * FROM IndividualAttandance WHERE subName = :subName AND date <= :date ORDER BY _ID")
//    List<AttendanceIndividualEntry> getAttendanceForSubjectBeforeDate(String subName, Date date);

    //    @Query("SELECT * FROM IndividualAttandance WHERE subName = :subName AND date <= :endDate AND date >=  :startDate ORDER BY _ID")
//    List<AttendanceIndividualEntry> getAttendanceForSubjectForTimePeriod(String subName, Date startDate, Date endDate);

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    void updateAttendance(AttendanceIndividualEntry newAttendanceEntry);

    //    @Delete
//    void deleteAttendance(AttendanceIndividualEntry attendanceEntry);

}
