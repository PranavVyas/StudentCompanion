package com.vyas.pranav.studentcompanion.asynTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.data.firebase.DayModel;
import com.vyas.pranav.studentcompanion.data.firebase.TimetableDataFetcher;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableDatabase;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableEntry;

import java.util.ArrayList;
import java.util.List;

import static com.vyas.pranav.studentcompanion.extraUtils.ServiceUtils.setTodaysAttendanceInSharedPref;

/*
 * Adding timetable to database in background*/
public class AddTimetableAsyncTask extends AsyncTask<Void, Void, Void> {

    private TimetableDatabase mTimetableDb;
    private TimetableDataFetcher.OnTimeTableReceived mCallback;
    private DataSnapshot data;
    private Context context;

    public AddTimetableAsyncTask(Context context) {
        this.context = context;
        mTimetableDb = TimetableDatabase.getInstance(context);
        mCallback = (TimetableDataFetcher.OnTimeTableReceived) context;
    }

    /*
     * Setting datasnapshot from Firebase data fetcher in asynctask*/
    public void setDataSnapshot(DataSnapshot data) {
        this.data = data;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        List<TimetableEntry> timeTableEntries = new ArrayList<>();
        for (DataSnapshot daysSnapshot : data.getChildren()) {
            DayModel currDay = daysSnapshot.getValue(DayModel.class);
            Logger.json(new Gson().toJson(currDay));
            TimetableEntry tempDay = new TimetableEntry();
            String day = daysSnapshot.getKey();
            //To Sort data when the timetable is shown in Timetable Fragment
            switch (day) {
                case "Monday":
                    tempDay.setDayOfWeek(2);
                    break;

                case "Tuesday":
                    tempDay.setDayOfWeek(3);
                    break;

                case "Wednesday":
                    tempDay.setDayOfWeek(4);
                    break;

                case "Thursday":
                    tempDay.setDayOfWeek(5);
                    break;

                case "Friday":
                    tempDay.setDayOfWeek(6);
                    break;

                default:
                    tempDay.setDayOfWeek(10);
                    break;
            }
            tempDay.setDay(day);
            tempDay.setLacture1Name(currDay.getLecture1());
            tempDay.setLacture2Name(currDay.getLecture2());
            tempDay.setLacture3Name(currDay.getLecture3());
            tempDay.setLacture4Name(currDay.getLecture4());
            tempDay.setLacture1Faculty(currDay.getFaculty1());
            tempDay.setLacture2Faculty(currDay.getFaculty2());
            tempDay.setLacture3Faculty(currDay.getFaculty3());
            tempDay.setLacture4Faculty(currDay.getFaculty4());
            timeTableEntries.add(tempDay);
        }
        mTimetableDb.timetableDao().insertAllTimeTableEntry(timeTableEntries);
        setTodaysAttendanceInSharedPref(context);
        return null;
    }


    /*To Notify the FirstRunActivity When the timetable is fetched and added to database*/
    @Override
    protected void onPostExecute(Void aVoid) {
        mCallback.OnTimetableReceived();
        super.onPostExecute(aVoid);
    }
}
