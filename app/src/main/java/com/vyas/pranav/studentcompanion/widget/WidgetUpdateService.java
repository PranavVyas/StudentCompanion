package com.vyas.pranav.studentcompanion.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

//Service to handling Widget Update task in background
public class WidgetUpdateService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Logger.d("Widget Update Service Running");
        Toast.makeText(this, "Upadting from factory", Toast.LENGTH_SHORT).show();
//        TimetableDatabase mDb;
//        mDb = TimetableDatabase.getInstance(this);
//        String day = Converters.getDayOfWeek(new Date());
//        TimetableEntry mTimeTableDay = mDb.timetableDao().getTimetableForDay(day);
        return (new SubjectListAdapterWidget(this.getApplicationContext()));
    }
}
