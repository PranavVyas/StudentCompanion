package com.vyas.pranav.studentcompanion.asyntasks;

import android.content.Context;
import android.os.AsyncTask;

import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualEntry;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayDatabase;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableDatabase;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableEntry;
import com.vyas.pranav.studentcompanion.extrautils.Converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.vyas.pranav.studentcompanion.extrautils.Constances.VALUE_ABSENT;

/*AsyncTask to perform all attendance init when app is run first time in background*/
public class AddAllAttendanceAsyncTask extends AsyncTask<Void, Void, Void> {

    public static final int NO_OF_LACTURES = 4;
    private Date startDate, endDate;
    private HolidayDatabase mHolidayDb;
    private TimetableDatabase mTimetableDb;
    private AttendanceIndividualDatabase mAttendanceDb;
    private OnAllAttendanceInitializedListener mCallback;

    public AddAllAttendanceAsyncTask(Context context, OnAllAttendanceInitializedListener mCallback) {
        mHolidayDb = HolidayDatabase.getsInstance(context);
        mTimetableDb = TimetableDatabase.getInstance(context);
        mAttendanceDb = AttendanceIndividualDatabase.getInstance(context);
        this.mCallback = mCallback;
    }

    /*Setting dates in AsyncTask to add attendance from startDate to endDate*/
    public void setDates(Date startDate, Date endDate) {
        this.endDate = endDate;
        this.startDate = startDate;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        List<Date> finalDates = getEligibleDatesbetweenDates();
        Logger.d(finalDates);
        addAllAttendanceAtOnce(finalDates);
        return null;
    }

    /*
     * Method to add all of the attendance between startDate and endDate in database
     */
    private void addAllAttendanceAtOnce(List<Date> dates) {
        List<AttendanceIndividualEntry> mEntries = new ArrayList<>();
        for (Date x :
                dates) {
            String dayOfWeek = Converters.getDayOfWeek(x);
            TimetableEntry mTimetable = mTimetableDb.timetableDao().getTimetableForDay(dayOfWeek);
            for (int i = 0; i < NO_OF_LACTURES; i++) {
                AttendanceIndividualEntry newEntry = new AttendanceIndividualEntry();
                newEntry.setDate(x);
                newEntry.setAttended(VALUE_ABSENT);
                switch (i + 1) {
                    case 1:
                        newEntry.setSubName(mTimetable.getLacture1Name());
                        newEntry.setFacultyName(mTimetable.getLacture1Faculty());
                        newEntry.set_ID(Converters.generateIdForIndividualAttendance(x, 1));
                        break;

                    case 2:
                        newEntry.setSubName(mTimetable.getLacture2Name());
                        newEntry.setFacultyName(mTimetable.getLacture2Faculty());
                        newEntry.set_ID(Converters.generateIdForIndividualAttendance(x, 2));
                        break;

                    case 3:
                        newEntry.setSubName(mTimetable.getLacture3Name());
                        newEntry.setFacultyName(mTimetable.getLacture3Faculty());
                        newEntry.set_ID(Converters.generateIdForIndividualAttendance(x, 3));
                        break;

                    case 4:
                        newEntry.setSubName(mTimetable.getLacture4Name());
                        newEntry.setFacultyName(mTimetable.getLacture4Faculty());
                        newEntry.set_ID(Converters.generateIdForIndividualAttendance(x, 4));
                        break;
                }
                newEntry.setLactureNo(i + 1);
                newEntry.setDurationInMillis(3600);
                newEntry.setLactureType("L");
                mEntries.add(newEntry);
            }
        }
        mAttendanceDb.attendanceIndividualDao().insertAttendances(mEntries);
    }

    /*
     * Helper Method for Initialization of attendance database
     * Returns dates list that have either Saturday,Sunday or holiday on that date*/
    private List<Date> getEligibleDatesbetweenDates() {
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

    /*
     * Helper Method for Initialization of attendance database
     * Returns all the dates between the startDate and endDate (including saturday,sunday, holiday etc.)
     * tested for max span of 2 years but generally no one has semester more than 2 years!  */
    private List<Date> getDatesListBetweenDates() {
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
                    tempdate = new SimpleDateFormat("D yyyy", Locale.US).parse(currDay + " " + currYear);
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

    /*
     * To Notify FirstRunActivity that the data has been successfully initialized */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mCallback.OnAllAttendanceInitialized();
    }

    /*
     * Callback for notifying the FirstRunActivity from this asynctask*/
    public interface OnAllAttendanceInitializedListener {
        void OnAllAttendanceInitialized();
    }
}
