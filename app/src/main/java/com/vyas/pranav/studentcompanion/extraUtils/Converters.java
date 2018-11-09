package com.vyas.pranav.studentcompanion.extraUtils;

import android.text.format.DateFormat;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Converters {

    public static Date convertStringToDate(String dateString){
        SimpleDateFormat dateFormator = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date date = new Date();
        try {
            date = dateFormator.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDateToString(Date date){
        SimpleDateFormat dateFormator = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
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
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.US);
        Date d = convertStringToDate(dateString);
        return sdf.format(d);
    }

    public static String getDayOfWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.US);
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

    public static int convertTimeInInt(String timeStr) {
        String[] newStr = timeStr.split(":");
        int min = Integer.valueOf(newStr[1]);
        int hour = Integer.valueOf(newStr[0]);
        return ((hour * 60) + min);
    }

    public static String convertTimeIntInString(int time) {
        CustomTime customTime = extractHourandMinFromTime(time);
        int hour = customTime.getHour();
        int min = customTime.getMin();
        String hourStr = ((hour < 10) ? "0" + hour : "" + hour);
        String minStr = ((min < 10) ? "0" + min : "" + min);

        return hourStr + ":" + minStr;
    }

    public static CustomTime extractHourandMinFromTime(int time) {
        int hour = time / 60;
        int min = time % 60;
        return new CustomTime(min, hour);
    }

    public static CustomTime extractHourandMinFromTime(String timeStr) {
        int time = convertTimeInInt(timeStr);
        int hour = time / 60;
        int min = time % 60;
        return new CustomTime(min, hour);
    }

    public static class CustomTime {
        private int min;
        private int hour;

        public CustomTime(int min, int hour) {
            this.min = min;
            this.hour = hour;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }
    }

}
