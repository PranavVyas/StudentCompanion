package com.vyas.pranav.studentcompanion.extraUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
