package com.vyas.pranav.studentcompanion.data.overallDatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayDatabase;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableDatabase;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;
import com.vyas.pranav.studentcompanion.services.AddEmptyAttendanceIntentService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OverallAttendanceHelper {

    public static List<Date> testMethod2(Date startDate, Date endDate) {
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

    public static void initDatabaseFirsTime(Context context, Date startDate, Date endDate) {
        AttendanceIndividualDatabase mIndividualDb = AttendanceIndividualDatabase.getInstance(context);
        HolidayDatabase mHolidayDb = HolidayDatabase.getsInstance(context);
        TimetableDatabase mTimeTableDb = TimetableDatabase.getInstance(context);
        //mIndividualDb.attendanceIndividualDao().deleteAllAttendance();
        if (mTimeTableDb.timetableDao().getFullTimetable().isEmpty()) {
            Toast.makeText(context, "Timetable is Empty", Toast.LENGTH_SHORT).show();
            Logger.d("TimeTable is Empty");
        } else if (mHolidayDb.holidayDao().getAllHolidays().isEmpty()) {
            Toast.makeText(context, "Holiday is Empty", Toast.LENGTH_SHORT).show();
            Logger.d("Holiday is Empty");
        } else {
            List<Date> holidays = mHolidayDb.holidayDao().getAllDates();
            Logger.d(holidays + ",");
            Logger.d("Sending start date : " + startDate + "\nEnd DAte : " + endDate + " To getEligibleDatesBetween");
            List<Date> finalDates = getEligibleDatesbetweenDates(startDate, endDate, mHolidayDb);
            Logger.d(finalDates);
            for (Date tempDate :
                    finalDates) {
                Intent markAttendance = new Intent(context, AddEmptyAttendanceIntentService.class);
                Bundle sendData = new Bundle();
                sendData.putString(Constances.KEY_SEND_DATE_TO_SERVICE, Converters.convertDateToString(tempDate));
                markAttendance.putExtras(sendData);
                context.startService(markAttendance);
            }
        }
    }

    public static List<Date> getEligibleDatesbetweenDates(Date startDate, Date endDate, HolidayDatabase mHolidayDb) {
        List<Date> tempDates = testMethod2(startDate, endDate);
        List<Date> holidays = mHolidayDb.holidayDao().getAllDates();
        List<Date> finalDates = new ArrayList<>();
        for (Date x :
                tempDates) {
            if ((holidays.contains(x))
                    || (Converters.getDayOfWeek(x).equals("Saturday"))
                    || (Converters.getDayOfWeek(x).equals("Sunday"))) {
                //Logger.d("The Date " + x + " is in Holidays bro!!!");
            } else {
                finalDates.add(x);
                //Logger.d("Added to the Final List now...");
            }
        }
        Logger.d("Test Log");
        return finalDates;
    }

    public static int getDaysBetween(Context context, Date startDate, Date endDate) {
        HolidayDatabase mHolidayDb = HolidayDatabase.getsInstance(context);
        return getEligibleDatesbetweenDates(startDate, endDate, mHolidayDb).size();
    }

    public static void addDataInOverallAttendance(Context context, Date currDate) {
        AttendanceIndividualDatabase mIndividualDb = AttendanceIndividualDatabase.getInstance(context);
        OverallAttendanceDatabase mOverallDb = OverallAttendanceDatabase.getInstance(context);
        if (mIndividualDb.attendanceIndividualDao().getAllAttendance().isEmpty()) {
            Toast.makeText(context, "Individual Attendance is Empty", Toast.LENGTH_SHORT).show();
        } else {
            String Sub1 = Constances.SUB_1;
            int TotalDaysSub1 = mIndividualDb.attendanceIndividualDao().getAttendanceForSubject(Constances.SUB_1).size();
            int AttendedDaysSub1 = mIndividualDb.attendanceIndividualDao().getAttendanceForSubject(Constances.SUB_1, Constances.VALUE_PRESENT).size();
            OverallAttendanceEntry sub1 = new OverallAttendanceEntry();
            sub1.setTotalDays(TotalDaysSub1);
            float persentPresent1 = (AttendedDaysSub1 * 100) / TotalDaysSub1;
            int minDaysThreshold1 = (int) Math.ceil(TotalDaysSub1 * 0.75);
            int daysBetweenStartAndToday1 = mIndividualDb.attendanceIndividualDao().getAttendanceForSubjectForTimePeriod(Sub1, Converters.convertStringToDate(Constances.startOfSem), currDate).size();
            //int daysBetweenStartAndToday1 = getDaysBetween(context,Converters.convertStringToDate(Constances.startOfSem),currDate);
            int daysAlreadyBunked1 = daysBetweenStartAndToday1 - AttendedDaysSub1;
            int totalDaysAvailableForBunk1 = TotalDaysSub1 - minDaysThreshold1;
            int daysCanBeBunkedFromNow1 = totalDaysAvailableForBunk1 - daysAlreadyBunked1;
            sub1.setTotalDays(TotalDaysSub1);
            sub1.setSubjectName(Sub1);
            sub1.setPercentPresent(persentPresent1);
            sub1.setDaysBunked(daysAlreadyBunked1);
            sub1.setDaysAvailableToBunk(daysCanBeBunkedFromNow1);
            //mOverallDb.overallAttandanceDao().deleteAllSubjects(); //TODO REMOVE
            //Logger.d(sub1);
            String log = "Total Days " + TotalDaysSub1 + "\tAttended Days " + AttendedDaysSub1
                    + "\npresent percent " + persentPresent1 + "\t min Days Threshold : " + minDaysThreshold1
                    + "\n Days bunked : " + daysAlreadyBunked1 + "\t DAys available for bunk : " + daysCanBeBunkedFromNow1
                    + "\n Days passed " + daysBetweenStartAndToday1;
            Logger.d(log);
            mOverallDb.overallAttandanceDao().insertSubjectOverallAttedance(sub1);
        }

    }

    public static List<String> testMethod1(int startDay, int endDay, int startYear, int endYear) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        int currYear = startYear;
        int dayOfYear = startDay;
        int JAN = 31;
        int FEB = (currYear % 4 == 0) ? 29 : 28;
        int MAR = 31;
        int APR = 30;
        int MAY = 31;
        int JUN = 30;
        int JUL = 31;
        int AUG = 31;
        int SEP = 30;
        int OCT = 31;
        int NOV = 30;
        int DEC = 31;
        int DAYS_TILL_FEB = JAN + FEB;
        int DAYS_TILL_MAR = DAYS_TILL_FEB + MAR;
        int DAYS_TILL_APR = DAYS_TILL_MAR + APR;
        int DAYS_TILL_MAY = DAYS_TILL_APR + MAY;
        int DAYS_TILL_JUN = DAYS_TILL_MAY + JUN;
        int DAYS_TILL_JUL = DAYS_TILL_JUN + JUL;
        int DAYS_TILL_AUG = DAYS_TILL_JUL + AUG;
        int DAYS_TILL_SEP = DAYS_TILL_AUG + SEP;
        int DAYS_TILL_OCT = DAYS_TILL_SEP + OCT;
        int DAYS_TILL_NOV = DAYS_TILL_OCT + NOV;
        int DAYS_TILL_DEC = DAYS_TILL_NOV + DEC;
        List<String> dateStrs = new ArrayList<>();
        if (startYear != endYear) {
            endDay = DAYS_TILL_DEC + endDay;
            Logger.d("Total No of Days are : " + endDay);
        }
        while (currYear <= endYear) {
            for (; dayOfYear <= JAN && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear, 1, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_FEB && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - JAN, 2, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_MAR && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_FEB, 3, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_APR && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_MAR, 4, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_MAY && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_APR, 5, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_JUN && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_MAY, 6, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_JUL && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_JUN, 7, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_AUG && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_JUL, 8, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_SEP && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_AUG, 9, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_OCT && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_SEP, 10, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_NOV && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_OCT, 11, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_DEC && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_NOV, 12, currYear));
            }
            if (startDay != endYear) {
                dayOfYear = 1;
                endDay = endDay - DAYS_TILL_DEC;
                currYear++;
            }
        }
        Logger.d(dateStrs);
        return dateStrs;
    }

    public static void initDatabaseFirsTime(Context context, int startDay, int endDay, int startYear, int endYear) {
        AttendanceIndividualDatabase mIndividualDb = AttendanceIndividualDatabase.getInstance(context);
        HolidayDatabase mHolidayDb = HolidayDatabase.getsInstance(context);
        TimetableDatabase mTimeTableDb = TimetableDatabase.getInstance(context);
        mIndividualDb.attendanceIndividualDao().deleteAllAttendance();
        if (mTimeTableDb.timetableDao().getFullTimetable().isEmpty()) {
            Toast.makeText(context, "Timetable is Empty", Toast.LENGTH_SHORT).show();
            Logger.d("TimeTable is Empty");
        } else if (mHolidayDb.holidayDao().getAllHolidays().isEmpty()) {
            Toast.makeText(context, "Holiday is Empty", Toast.LENGTH_SHORT).show();
            Logger.d("Holiday is Empty");
        } else {
            List<Date> holidays = mHolidayDb.holidayDao().getAllDates();
            Logger.d(holidays + ",");
            List<Date> finalDates = getEligibleDatesbetweenDates(startDay, endDay, startYear, endYear, mHolidayDb);
            Logger.d(finalDates);

            for (Date tempDate :
                    finalDates) {
                Intent markAttendance = new Intent(context, AddEmptyAttendanceIntentService.class);
                Bundle sendData = new Bundle();
                sendData.putString(Constances.KEY_SEND_DATE_TO_SERVICE, Converters.convertDateToString(tempDate));
                markAttendance.putExtras(sendData);
                context.startService(markAttendance);
            }
        }
    }

    public static List<Date> getEligibleDatesbetweenDates(int startDay, int endDay, int startYear, int endYear, HolidayDatabase mHolidayDb) {
        List<String> tempDates = testMethod1(startDay, endDay, startYear, endYear);
        List<Date> holidays = mHolidayDb.holidayDao().getAllDates();
        List<Date> finalDates = new ArrayList<>();
        for (String x :
                tempDates) {
            Date tempDate = Converters.convertStringToDate(x);
            if ((holidays.contains(tempDate))
                    || (Converters.getDayOfWeek(x).equals("Saturday"))
                    || (Converters.getDayOfWeek(x).equals("Sunday"))) {
                Logger.d("The Date " + x + " is in Holidays bro!!!");
            } else {
                Date tempDatefinal = Converters.convertStringToDate(x);
                finalDates.add(tempDatefinal);
                //Logger.d("Added to the Final List now...");
            }
        }
        return finalDates;
    }

    public static List<String> testMethod1(Date startDate, Date endDate) {
        Converters.CustomDate startCustomDate = Converters.extractElementsFromDate(startDate);
        Converters.CustomDate endCusomDate = Converters.extractElementsFromDate(endDate);
        Logger.addLogAdapter(new AndroidLogAdapter());
        int startYear = startCustomDate.getYear();
        int endYear = endCusomDate.getYear();
        int startDay = startCustomDate.getDayOfYear();
        int endDay = endCusomDate.getDayOfYear();
        int currYear = startYear;
        int dayOfYear = startDay;
        int JAN = 31;
        int FEB = (currYear % 4 == 0) ? 29 : 28;
        int MAR = 31;
        int APR = 30;
        int MAY = 31;
        int JUN = 30;
        int JUL = 31;
        int AUG = 31;
        int SEP = 30;
        int OCT = 31;
        int NOV = 30;
        int DEC = 31;
        int DAYS_TILL_FEB = JAN + FEB;
        int DAYS_TILL_MAR = DAYS_TILL_FEB + MAR;
        int DAYS_TILL_APR = DAYS_TILL_MAR + APR;
        int DAYS_TILL_MAY = DAYS_TILL_APR + MAY;
        int DAYS_TILL_JUN = DAYS_TILL_MAY + JUN;
        int DAYS_TILL_JUL = DAYS_TILL_JUN + JUL;
        int DAYS_TILL_AUG = DAYS_TILL_JUL + AUG;
        int DAYS_TILL_SEP = DAYS_TILL_AUG + SEP;
        int DAYS_TILL_OCT = DAYS_TILL_SEP + OCT;
        int DAYS_TILL_NOV = DAYS_TILL_OCT + NOV;
        int DAYS_TILL_DEC = DAYS_TILL_NOV + DEC;
        List<String> dateStrs = new ArrayList<>();
        if (startYear != endYear) {
            endDay = DAYS_TILL_DEC + endDay;
            Logger.d("Total No of Days are : " + endDay);
        }
        Logger.d("Data : \nstartYear : " + startYear +
                "\tend Year : " + endYear + "\nStartDate : " + startDate + "\tEndDate : " + endDate
                + "\nStartDay : " + startDay + "\tEndDay : " + endDay);
        while (currYear <= endYear) {
            for (; dayOfYear <= JAN && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear, 1, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_FEB && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - JAN, 2, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_MAR && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_FEB, 3, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_APR && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_MAR, 4, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_MAY && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_APR, 5, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_JUN && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_MAY, 6, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_JUL && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_JUN, 7, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_AUG && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_JUL, 8, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_SEP && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_AUG, 9, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_OCT && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_SEP, 10, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_NOV && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_OCT, 11, currYear));
            }
            for (; dayOfYear <= DAYS_TILL_DEC && dayOfYear <= endDay; dayOfYear++) {
                dateStrs.add(Converters.formatDateStringfromCalender(dayOfYear - DAYS_TILL_NOV, 12, currYear));
            }
            if (startDay != endYear) {
                dayOfYear = 1;
                endDay = endDay - DAYS_TILL_DEC;
                currYear++;
            }
        }
        Logger.d(dateStrs);
        return dateStrs;
    }

}
