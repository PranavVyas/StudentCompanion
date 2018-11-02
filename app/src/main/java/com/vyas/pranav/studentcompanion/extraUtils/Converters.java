package com.vyas.pranav.studentcompanion.extraUtils;

import android.text.format.DateFormat;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Converters {

    public static Date convertStringToDate(String dateString){
        SimpleDateFormat dateFormator = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = dateFormator.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDateToString(Date date){
        SimpleDateFormat dateFormator = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormator.format(date);
    }

    public static String formatDateStringfromCalender(int date,int month,int year){
        String dateS,monthS;
        if ((date <= 9)) {
            dateS = 0 + "" + date;
        } else {
            dateS = "" + date;
        }
        if ((month <= 9)) {
            monthS = 0 + "" + month;
        } else {
            monthS = "" + month;
        }
        return dateS + "/" +monthS+ "/" +year;
    }

    public static String getDayOfWeek(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = convertStringToDate(dateString);
        return sdf.format(d);
    }

    public static String getDayOfWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        return sdf.format(date);
    }

    public static String generateIdForIndividualAttendance(Date date, int lectureNo) {
        return convertDateToString(date) + lectureNo;
    }

    public static CustomDate extractElementsFromDate(Date date) {
        int day = Integer.parseInt((String) DateFormat.format("dd", date));
        int monthNumber = Integer.parseInt((String) DateFormat.format("MM", date));
        int year = Integer.parseInt((String) DateFormat.format("yyyy", date));
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(GregorianCalendar.DAY_OF_MONTH, day);
        calendar.set(GregorianCalendar.MONTH, monthNumber - 1);
        calendar.set(GregorianCalendar.YEAR, year);
        int dayOfYear = calendar.get(GregorianCalendar.DAY_OF_YEAR);
        Logger.d("Received Date : " + date + "\tDay is : " + day + "\tMOnth is " + monthNumber + "\tYear : " + year + "\tDay Of The Year : " + dayOfYear);
        return new CustomDate(day, monthNumber, year, dayOfYear);
    }

    public static class CustomDate {
        private int date;
        private int month;
        private int year;
        private int dayOfYear;

        public CustomDate(int date, int month, int year, int dayOfYear) {
            this.date = date;
            this.month = month;
            this.year = year;
            this.dayOfYear = dayOfYear;
        }

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getDayOfYear() {
            return dayOfYear;
        }

        public void setDayOfYear(int dayOfYear) {
            this.dayOfYear = dayOfYear;
        }
    }

}
