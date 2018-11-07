package com.vyas.pranav.studentcompanion.data.holidayDatabase;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface HolidayDao {

    @Query("SELECT * FROM Holidays ORDER BY holidayDate")
    LiveData<List<HolidayEntry>> getAllHolidays();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllHolidays(List<HolidayEntry> holidays);

    @Query("SELECT holidayDate FROM holidays")
    List<Date> getAllDates();

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertHoliday(HolidayEntry newHoliday);
//
//    @Delete
//    void deleteHoliday(HolidayEntry holidayToDelete);
//
//    @Query("DELETE FROM Holidays")
//    void deleteAllHolidays();
//
//    @Query("SELECT * FROM Holidays WHERE holidayDate = :holidayDate")
//    HolidayEntry getHolidayByDate(Date holidayDate);
//
//    @Query("SELECT * FROM Holidays WHERE holidayDay = :holidayDay")
//    List<HolidayEntry> getHolidaysByDay(String holidayDay);
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    void updateHoliday(HolidayEntry newHoliday);
}
