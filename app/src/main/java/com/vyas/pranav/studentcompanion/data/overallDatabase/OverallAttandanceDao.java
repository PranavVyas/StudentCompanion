package com.vyas.pranav.studentcompanion.data.overallDatabase;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface OverallAttandanceDao {

    @Query("SELECT * FROM OverallAttendance")
    List<OverallAttendanceEntry> getAllOverallAttedance();

    @Query("SELECT * FROM OverallAttendance WHERE subjectName = :subjectName")
    OverallAttendanceEntry getOverallAttedanceOfSubject(String subjectName);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSubjectOverallAttedance(OverallAttendanceEntry newOverallAttedance);

    @Insert
    void insertSubjectOverallAttedance(OverallAttendanceEntry newOverallAttedance);

    @Delete
    void deleteSubjectOverallAttedance(OverallAttendanceEntry subjectOverallAttedance);

    @Query("DELETE FROM OverallAttendance")
    void deleteAllSubjects();
}
