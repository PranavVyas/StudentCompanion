package com.vyas.pranav.studentcompanion.extraUtils;

import java.util.Arrays;
import java.util.List;

public class Constances {
    public static final String KEY_REG_TO_FRAG_ARGS_LECTURES = "SendingLecturesFromRegistarationToFragment";
    public static final int DEFAULT_REG_TO_FRAG_ARGS_LECTURES = -1;
    public static final String KEY_REG_TO_FRAG_ARGS_SUBJECTS = "SendingTotalsubjectsFromRegistarationToFragment";
    public static final int DEFAULT_REG_TO_FRAG_ARGS_SUBJECTS = -2;
    public static final String KEY_REG_TO_FRAG_ARGS_CURR_DATE = "SendingTotalsubjectsFromRegistarationToFragment";
    public static final int DEFAULT_REG_TO_FRAG_ARGS_CURR_DATE = -3;

    public static final String KEY_SEND_DATA_TO_INDIVIDUAL_FRAG = "SendingDataFromSourceToIndividualAttendanceFragment";
    public static final String KEY_SEND_DATA_TO_INDIVIDUAL_ACTIVITY = "Sending data from CalenderView to Attendance Activity";

    public static final String VALUE_PRESENT = "PRESENT";
    public static final String VALUE_ABSENT = "ABSENT";
    public static final String VALUE_HOLIDAY = "HOLIDAY";
    public static final String KEY_SEND_DATE_TO_SERVICE = "Sending Data From Activity to Empty Attendance Services";

    //TODO [Enhancement] Set this from Firebase DB Metadata and in Shareprefs and Retrive it from there
    public static final List<String> SUBJECTS = Arrays.asList(
            "CET 1",
            "MO",
            "MTO 1",
            "HTO 1",
            "ELECTIVE"
    );
    public static final String SUB_1 = "CET 1";
    public static final String SUB_2 = "MO";
    public static final String SUB_3 = "MTO 1";
    public static final String SUB_4 = "HTO 1";
    public static final String SUB_5 = "ELECTIVE";
    public static final int NO_OF_SUBJECTS = 5;

    public static final String startOfSem = "01/11/2018";
    public static final String endOfSem = "30/11/2018";


    public static final String KEY_SEND_DATA_TO_OVERALL_DETAIL = "SendingDataFromOverallToOverallDetailActivity";
    public static final String KEY_SEND_END_DATE_TO_SERVICE_OVERALL = "SendingDataToUpdateOverallAttendanceDatabase";
    public static final String KEY_SEND_DATA_TO_WIDGET_SERVICE = "SendingDataFromWidgetTOWidgetServiceToUpdateWidget";
    public static final int NO_OF_LECTURES_PER_DAY = 4;
    public static final String KEY_SMART_CARD_NEEDED_OR_NOT = "KeyForPrefenceIfSmartCardNeeded";
    public static final boolean VALUE_SMART_CARD_DEFAULT = false;
    public static final String KEY_SMART_CARD_DETAILS = "DetailsForSmartCards";
    public static final String SAVE_STATE_DASHBOARD_ACTVITY_DRAWER_ITEM = "ExitStateOfActivity";
}
