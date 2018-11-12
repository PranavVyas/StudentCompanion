package com.vyas.pranav.studentcompanion.extrautils;

import java.util.Arrays;
import java.util.List;

public class Constances {
    public static final String KEY_SEND_DATA_TO_INDIVIDUAL_FRAG = "SendingDataFromSourceToIndividualAttendanceFragment";
    public static final String KEY_SEND_DATA_TO_INDIVIDUAL_ACTIVITY = "Sending data from CalenderView to Attendance Activity";

    public static final String VALUE_PRESENT = "PRESENT";
    public static final String VALUE_ABSENT = "ABSENT";
    public static final String VALUE_HOLIDAY = "HOLIDAY";

    public static final String KEY_SEND_DATE_TO_SERVICE = "Sending Data From Activity to Empty Attendance Services";

    public static final List<String> SUBJECTS = Arrays.asList(
            "CET 1",
            "MO",
            "MTO 1",
            "HTO 1",
            "ELECTIVE"
    );
    public static final int NO_OF_SUBJECTS = 5;

    public static final String KEY_SEND_DATA_TO_OVERALL_DETAIL = "SendingDataFromOverallToOverallDetailActivity";
    public static final String KEY_SEND_END_DATE_TO_SERVICE_OVERALL = "SendingDataToUpdateOverallAttendanceDatabase";
    public static final String KEY_SEND_DATA_TO_WIDGET_SERVICE = "SendingDataFromWidgetTOWidgetServiceToUpdateWidget";
    public static final int NO_OF_LECTURES_PER_DAY = 4;
    public static final String KEY_SMART_CARD_NEEDED_OR_NOT = "KeyForPrefenceIfSmartCardNeeded";
    public static final boolean VALUE_SMART_CARD_DEFAULT = false;
    public static final String KEY_SMART_CARD_DETAILS = "DetailsForSmartCards";
    public static final String SAVE_STATE_DASHBOARD_ACTVITY_DRAWER_ITEM = "ExitStateOfActivity";
    public static final int NO_OF_DAYS_PER_WEEK = 5;
    public static final String KEY_TODAY_TIMETABLE_STRING = "TodaysTimeTable";
    public static final String START_DATE_SEM = "StartingOfTheSem";
    public static final String END_DATE_SEM = "EndingOfSem";
    public static final String SEND_DATA_TO_TUTORIAL_FRAG_FROM_ACTIVITY = "Sending tutorial no to the fragment";
}
