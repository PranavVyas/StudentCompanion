package com.vyas.pranav.studentcompanion.data.timetableDatabase;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TimetableDao {
    @Query("SELECT * FROM  timetable")
    List<TimetableEntry> getFullTimetable();

    @Query("SELECT * FROM timetable WHERE day = :day")
    TimetableEntry getTimetableForDay(String day);

    @Query("DELETE FROM timetable")
    void deleteWholeTimetable();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTimetableEntry(TimetableEntry newTimetableEntry);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTimeTableEntry(TimetableEntry newTimetableEntry);

    @Delete
    void deleteTimetableEntry(TimetableEntry timetableEntry);
}
