package com.vyas.pranav.studentcompanion.data.holidayDatabase;

import java.util.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface HolidayDao {

    @Query("SELECT * FROM Holidays")
    List<HolidayEntry> getAllHolidays();

    @Query("SELECT * FROM Holidays WHERE holidayDate = :holidayDate")
    HolidayEntry getHolidayByDate(Date holidayDate);

    @Query("SELECT * FROM Holidays WHERE holidayDay = :holidayDay")
    List<HolidayEntry> getHolidaysByDay(String holidayDay);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateHoliday(HolidayEntry newHoliday);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHoliday(HolidayEntry newHoliday);

    @Delete
    void deleteHoliday(HolidayEntry holidayToDelete);

    @Query("DELETE FROM Holidays")
    void deleteAllHolidays();

    @Query("SELECT holidayDate FROM holidays")
    List<Date> getAllDates();
}
