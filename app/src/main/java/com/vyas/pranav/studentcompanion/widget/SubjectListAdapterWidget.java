package com.vyas.pranav.studentcompanion.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableDatabase;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.util.Date;

public class SubjectListAdapterWidget implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    TimetableEntry mTimeTableDay;
    String mDay;
    TimetableDatabase mDb;

    public SubjectListAdapterWidget(Context mContext) {
        this.mContext = mContext;
        mDb = TimetableDatabase.getInstance(mContext);
    }

    @Override
    public void onCreate() {
        String day = Converters.getDayOfWeek(new Date());
        mTimeTableDay = mDb.timetableDao().getTimetableForDay(day);
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return Constances.NO_OF_LECTURES_PER_DAY;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_holder_listitem_widget);
        if (mTimeTableDay == null) {
            rv.setTextViewText(R.id.tv_list_widget_main, "Error Occured");
        } else {
            switch (i) {
                case 0:
                    rv.setTextViewText(R.id.tv_list_widget_main, mTimeTableDay.getLacture1Name());
                    break;

                case 1:
                    rv.setTextViewText(R.id.tv_list_widget_main, mTimeTableDay.getLacture2Name());
                    break;

                case 2:
                    rv.setTextViewText(R.id.tv_list_widget_main, mTimeTableDay.getLacture3Name());
                    break;

                case 3:
                    rv.setTextViewText(R.id.tv_list_widget_main, mTimeTableDay.getLacture4Name());
                    break;

                default:
                    rv.setTextViewText(R.id.tv_list_widget_main, "Error Occured");
                    break;
            }
        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
