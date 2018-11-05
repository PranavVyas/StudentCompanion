package com.vyas.pranav.studentcompanion.data.overallDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface OverallAttandanceDao {

    @Query("SELECT * FROM OverallAttendance")
    LiveData<List<OverallAttendanceEntry>> getAllOverallAttedance();

    @Query("SELECT * FROM OverallAttendance WHERE subjectName = :subjectName")
    OverallAttendanceEntry getOverallAttedanceOfSubject(String subjectName);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSubjectOverallAttedance(OverallAttendanceEntry newOverallAttedance);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubjectOverallAttedance(OverallAttendanceEntry newOverallAttedance);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllSubjectOverallAttedance(List<OverallAttendanceEntry> newOverallAttedances);

    @Delete
    void deleteSubjectOverallAttedance(OverallAttendanceEntry subjectOverallAttedance);

    @Query("DELETE FROM OverallAttendance")
    void deleteAllSubjects();
}
