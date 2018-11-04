package com.vyas.pranav.studentcompanion.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;

import java.util.List;

import androidx.preference.PreferenceManager;

public class SubjectListAdapterWidget implements RemoteViewsService.RemoteViewsFactory {

    public static final String KEY_L_1 = "L1";
    public static final String KEY_L_2 = "L2";
    public static final String KEY_L_3 = "L3";
    public static final String KEY_L_4 = "L4";
    SharedPreferences mPrefs;
    List<String> lactures;
    private Context mContext;
    //String str;
    //private Intent mIntent;
    //private TimetableDatabase mDb;
    //private TimetableEntry mEntry;
    //private  String currDay;

    public SubjectListAdapterWidget(Context mContext, Intent callingIntent) {
        this.mContext = mContext;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        // str = "Old";
        // this.mIntent = callingIntent;

//        if (mIntent.hasExtra(Constances.KEY_SEND_DATA_TO_WIDGET_SERVICE)) {
//         //   String currDate = mIntent.getStringExtra(Constances.KEY_SEND_DATA_TO_WIDGET_SERVICE);
//         //   this.currDay = Converters.getDayOfWeek(currDate);
//        }
    }

    @Override
    public void onCreate() {
        lactures.add(mPrefs.getString(KEY_L_1, "Errorr"));
        lactures.add(mPrefs.getString(KEY_L_2, "Errorr"));
        lactures.add(mPrefs.getString(KEY_L_3, "Errorr"));
        lactures.add(mPrefs.getString(KEY_L_4, "Errorr"));
        //Handling init of Timetable Database Now
        //mDb = TimetableDatabase.getInstance(mContext);
        //this.mEntry = mDb.timetableDao().getTimetableForDay(currDay);
//        Logger.d("Sucessfully Initlized Database From WidgetAdapter");
    }

    @Override
    public void onDataSetChanged() {
        //Toast.makeText(mContext, "Dataset Changed", Toast.LENGTH_LONG).show();
        //loadUpdatedEntry();
    }

    public void loadUpdatedEntry() {
        //str = "New";
        //this.mEntry = mDb.timetableDao().getTimetableForDay(currDay);
//        //this.onDataSetChanged();
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        //if (mEntry == null) {
        //    Toast.makeText(mContext, "Entry is Empty", Toast.LENGTH_SHORT).show();
        //    return 1;
        //} else {
        //    Toast.makeText(mContext, "Entry is initlized Sucessfully", Toast.LENGTH_SHORT).show();
        return Constances.NO_OF_LECTURES_PER_DAY;
        //}
        //return 1;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Toast.makeText(mContext, "Setting Up Widget With Data", Toast.LENGTH_LONG).show();
        Logger.d("Setting Up Widget With Data");
        RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.item_holder_listitem_widget);
//        if(mEntry != null) {
//            switch (i) {
//                case 0:
//                    remoteView.setTextViewText(R.id.tv_list_widget_main, mEntry.getLacture1Name());
//                    break;
//
//                case 1:
//                    remoteView.setTextViewText(R.id.tv_list_widget_main, mEntry.getLacture2Name());
//                    break;
//
//                case 2:
//                    remoteView.setTextViewText(R.id.tv_list_widget_main, mEntry.getLacture3Name());
//                    break;
//
//                case 3:
//                    remoteView.setTextViewText(R.id.tv_list_widget_main, mEntry.getLacture4Name());
//                    break;
//
//                default:
//                    remoteView.setTextViewText(R.id.tv_list_widget_main, "Error Occured");
//                    break;
//            }
//        }
        return remoteView;
//        RemoteViews rv = new RemoteViews(mContext.getPackageName(),R.layout.item_holder_listitem_widget);
//        rv.setTextViewText(R.id.tv_list_widget_main,str);
//        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
//        RemoteViews rv = new RemoteViews(mContext.getPackageName(),R.layout.item_holder_listitem_widget);
//        rv.setTextViewText(R.id.tv_list_widget_main,"Please wait...");
//        return rv;
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
