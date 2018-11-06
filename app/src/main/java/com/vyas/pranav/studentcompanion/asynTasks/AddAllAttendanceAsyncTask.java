package com.vyas.pranav.studentcompanion.asynTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualEntry;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayDatabase;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableDatabase;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.vyas.pranav.studentcompanion.extraUtils.Constances.VALUE_ABSENT;
import static com.vyas.pranav.studentcompanion.services.AddEmptyAttendanceIntentService.NO_OF_LACTURES;

public class AddAllAttendanceAsyncTask extends AsyncTask<Void, Void, Void> {

    Context context;
    Date startDate, endDate;
    HolidayDatabase mHolidayDb;
    TimetableDatabase mTimetableDb;
    AttendanceIndividualDatabase mAttendanceDb;
    OnAllAttendanceInitializedListener mCallback;

    public AddAllAttendanceAsyncTask(Context context, OnAllAttendanceInitializedListener mCallback) {
        this.context = context;
        mHolidayDb = HolidayDatabase.getsInstance(context);
        mTimetableDb = TimetableDatabase.getInstance(context);
        mAttendanceDb = AttendanceIndividualDatabase.getInstance(context);
        this.mCallback = mCallback;
    }

    public void setDates(Date startDate, Date endDate) {
        this.endDate = endDate;
        this.startDate = startDate;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        List<Date> holidays = mHolidayDb.holidayDao().getAllDates();
        //Logger.d(holidays + ",");
        //Logger.d("Sending start date : " + startDate + "\nEnd DAte : " + endDate + " To getEligibleDatesBetween");
        List<Date> finalDates = getEligibleDatesbetweenDates();
        Logger.d(finalDates);
        for (Date tempDate :
                finalDates) {
            addDataInIndividualDatabase(tempDate);
        }
        return null;
    }

    public void addDataInIndividualDatabase(Date date) {
        String dayOfWeek = Converters.getDayOfWeek(date);
        TimetableEntry mTimetable = mTimetableDb.timetableDao().getTimetableForDay(dayOfWeek);
        List<AttendanceIndividualEntry> mEntries = new ArrayList<>();
        for (int i = 0; i < NO_OF_LACTURES; i++) {
            AttendanceIndividualEntry newEntry = new AttendanceIndividualEntry();
            newEntry.setDate(date);
            newEntry.setAttended(VALUE_ABSENT);
            switch (i + 1) {
                case 1:
                    newEntry.setSubName(mTimetable.getLacture1Name());
                    newEntry.setFacultyName(mTimetable.getLacture1Faculty());
                    newEntry.set_ID(Converters.generateIdForIndividualAttendance(date, 1));
                    break;

                case 2:
                    newEntry.setSubName(mTimetable.getLacture2Name());
                    newEntry.setFacultyName(mTimetable.getLacture2Faculty());
                    newEntry.set_ID(Converters.generateIdForIndividualAttendance(date, 2));
                    break;

                case 3:
                    newEntry.setSubName(mTimetable.getLacture3Name());
                    newEntry.setFacultyName(mTimetable.getLacture3Faculty());
                    newEntry.set_ID(Converters.generateIdForIndividualAttendance(date, 3));
                    break;

                case 4:
                    newEntry.setSubName(mTimetable.getLacture4Name());
                    newEntry.setFacultyName(mTimetable.getLacture4Faculty());
                    newEntry.set_ID(Converters.generateIdForIndividualAttendance(date, 4));
                    break;
            }
            newEntry.setLactureNo(i + 1);
            newEntry.setDurationInMillis(3600);
            newEntry.setLactureType("L");
            mEntries.add(newEntry);
            //mAttendanceDb.attendanceIndividualDao().insertAttendance(newEntry);
        }
        mAttendanceDb.attendanceIndividualDao().insertAttendances(mEntries);
    }

    /*Helper Method for Initalization of attendance database*/
    public List<Date> getEligibleDatesbetweenDates() {
        List<Date> tempDates = getDatesListBetweenDates();
        List<Date> holidays = mHolidayDb.holidayDao().getAllDates();
        List<Date> finalDates = new ArrayList<>();
        for (Date x :
                tempDates) {
            if ((holidays.contains(x))
                    || (Converters.getDayOfWeek(x).equals("Saturday"))
                    || (Converters.getDayOfWeek(x).equals("Sunday"))) {
                //Logger.d("The Date " + x + " is in Holidays");
            } else {
                finalDates.add(x);
                //Logger.d("Added to the Final List now...");
            }
        }
        return finalDates;
    }

    /*Helper Method for Initalization of attendance database*/
    public List<Date> getDatesListBetweenDates() {
        Converters.CustomDate startCustomDate = Converters.extractElementsFromDate(startDate);
        Converters.CustomDate endCusomDate = Converters.extractElementsFromDate(endDate);
        int startYear = startCustomDate.getYear();
        int endYear = endCusomDate.getYear();
        int startDay = startCustomDate.getDayOfYear();
        int endDay = endCusomDate.getDayOfYear();
        int currDay = startDay;
        int currYear = startYear;
        int daysInCurrYear = (currYear % 4 == 0) ? 366 : 365;
        if (startYear != endYear && (endYear - startYear == 1)) {
            endDay = daysInCurrYear + endDay;
            Logger.d("Total No of Days are : " + endDay);
        }
        List<Date> result = new ArrayList<>();
        Date tempdate;
        while (currYear <= endYear) {
            while (currDay <= endDay) {
                try {
                    tempdate = new SimpleDateFormat("D yyyy").parse(currDay + " " + currYear);
                    result.add(tempdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                currDay++;
            }
            if (startDay != endYear) {
                currDay = 1;
                endDay = endDay - daysInCurrYear;
                currYear++;
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mCallback.OnAllAttendanceInitialized();
    }

    public interface OnAllAttendanceInitializedListener {
        void OnAllAttendanceInitialized();
    }
}
