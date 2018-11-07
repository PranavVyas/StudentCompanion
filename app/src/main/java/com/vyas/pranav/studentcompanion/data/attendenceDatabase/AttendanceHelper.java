package com.vyas.pranav.studentcompanion.data.attendenceDatabase;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class AttendanceHelper {

    //TODO Not needed any more remove after testing new update
    Context mContext;
    List<AttendanceIndividualEntry> mEntries = new ArrayList<>();
    OnAttendanceDatabaseInitializedListener mCallback;

    public AttendanceHelper(Context mContext) {
        this.mContext = mContext;
        this.mCallback = (OnAttendanceDatabaseInitializedListener) mContext;

    }
//
//    public void initAttendanceDatabaseFirstTime(Date startDate, Date endDate) {
//        HolidayDatabase mHolidayDb = HolidayDatabase.getsInstance(mContext);
//        List<Date> holidays = mHolidayDb.holidayDao().getAllDates();
//        Logger.d(holidays + ",");
//        Logger.d("Sending start date : " + startDate + "\nEnd DAte : " + endDate + " To getEligibleDatesBetween");
//        List<Date> finalDates = getEligibleDatesbetweenDates(startDate, endDate, mHolidayDb);
//        Logger.d(finalDates);
//        for (Date tempDate :
//                finalDates) {
//            addDataInIndividualDatabase(tempDate);
//        }
//    }
//
//    public void addDataInIndividualDatabase(Date date) {
//        AppExecutors mExecutors = AppExecutors.getInstance();
//        String dayOfWeek = Converters.getDayOfWeek(date);
//        TimetableDatabase timetableDb = TimetableDatabase.getInstance(mContext);
//        final AttendanceIndividualDatabase mAttendanceDb = AttendanceIndividualDatabase.getInstance(mContext);
//        TimetableEntry mTimetable = timetableDb.timetableDao().getTimetableForDay(dayOfWeek);
//        for (int i = 0; i < NO_OF_LACTURES; i++) {
//            AttendanceIndividualEntry newEntry = new AttendanceIndividualEntry();
//            newEntry.setDate(date);
//            newEntry.setAttended(VALUE_ABSENT);
//            switch (i + 1) {
//                case 1:
//                    newEntry.setSubName(mTimetable.getLacture1Name());
//                    newEntry.setFacultyName(mTimetable.getLacture1Faculty());
//                    newEntry.set_ID(Converters.generateIdForIndividualAttendance(date, 1));
//                    break;
//
//                case 2:
//                    newEntry.setSubName(mTimetable.getLacture2Name());
//                    newEntry.setFacultyName(mTimetable.getLacture2Faculty());
//                    newEntry.set_ID(Converters.generateIdForIndividualAttendance(date, 2));
//                    break;
//
//                case 3:
//                    newEntry.setSubName(mTimetable.getLacture3Name());
//                    newEntry.setFacultyName(mTimetable.getLacture3Faculty());
//                    newEntry.set_ID(Converters.generateIdForIndividualAttendance(date, 3));
//                    break;
//
//                case 4:
//                    newEntry.setSubName(mTimetable.getLacture4Name());
//                    newEntry.setFacultyName(mTimetable.getLacture4Faculty());
//                    newEntry.set_ID(Converters.generateIdForIndividualAttendance(date, 4));
//                    break;
//            }
//            newEntry.setLactureNo(i + 1);
//            newEntry.setDurationInMillis(3600);
//            newEntry.setLactureType("L");
//            mEntries.add(newEntry);
//            //mAttendanceDb.attendanceIndividualDao().insertAttendance(newEntry);
//        }
//        mExecutors.diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                mAttendanceDb.attendanceIndividualDao().insertAttendances(mEntries);
//                mCallback.OnAttendanceDatabaseInitialized();
//            }
//        });
//    }
//
//
//    /*Helper Method for Initalization of attendance database*/
//    public List<Date> getEligibleDatesbetweenDates(Date startDate, Date endDate, HolidayDatabase mHolidayDb) {
//        List<Date> tempDates = getDatesListBetweenDates(startDate, endDate);
//        List<Date> holidays = mHolidayDb.holidayDao().getAllDates();
//        List<Date> finalDates = new ArrayList<>();
//        for (Date x :
//                tempDates) {
//            if ((holidays.contains(x))
//                    || (Converters.getDayOfWeek(x).equals("Saturday"))
//                    || (Converters.getDayOfWeek(x).equals("Sunday"))) {
//                //Logger.d("The Date " + x + " is in Holidays");
//            } else {
//                finalDates.add(x);
//                //Logger.d("Added to the Final List now...");
//            }
//        }
//        return finalDates;
//    }
//
//    /*Helper Method for Initalization of attendance database*/
//    public List<Date> getDatesListBetweenDates(Date startDate, Date endDate) {
//        Converters.CustomDate startCustomDate = Converters.extractElementsFromDate(startDate);
//        Converters.CustomDate endCusomDate = Converters.extractElementsFromDate(endDate);
//        int startYear = startCustomDate.getYear();
//        int endYear = endCusomDate.getYear();
//        int startDay = startCustomDate.getDayOfYear();
//        int endDay = endCusomDate.getDayOfYear();
//        int currDay = startDay;
//        int currYear = startYear;
//        int daysInCurrYear = (currYear % 4 == 0) ? 366 : 365;
//        if (startYear != endYear && (endYear - startYear == 1)) {
//            endDay = daysInCurrYear + endDay;
//            Logger.d("Total No of Days are : " + endDay);
//        }
//        List<Date> result = new ArrayList<>();
//        Date tempdate;
//        while (currYear <= endYear) {
//            while (currDay <= endDay) {
//                try {
//                    tempdate = new SimpleDateFormat("D yyyy").parse(currDay + " " + currYear);
//                    result.add(tempdate);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                currDay++;
//            }
//            if (startDay != endYear) {
//                currDay = 1;
//                endDay = endDay - daysInCurrYear;
//                currYear++;
//            }
//        }
//        return result;
//    }

    public interface OnAttendanceDatabaseInitializedListener {
        void OnAttendanceDatabaseInitialized();
    }

}
