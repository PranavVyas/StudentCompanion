package com.vyas.pranav.studentcompanion.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;

public class SubjectListAdapterWidget implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private TimetableEntry mTimeTableDay = null;

    public SubjectListAdapterWidget(Context mContext) {
        this.mContext = mContext;
        Gson gson = new Gson();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String today = mPrefs.getString(Constances.KEY_TODAY_TIMETABLE_STRING, null);
        if (today != null) {
            this.mTimeTableDay = gson.fromJson(today, TimetableEntry.class);
        }
    }

    @Override
    public void onCreate() {

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
