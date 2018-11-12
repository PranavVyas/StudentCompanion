package com.vyas.pranav.studentcompanion.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.dashboard.DashboardActivity;

public class ShowSubjectAppWidget extends AppWidgetProvider {

    private static final int REQ_CODE_OPEN_APP = 1;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.show_subject_app_widget);

        //Create Intent to send to RemoteAdapter
        Intent updateWidgetIntent = new Intent(context, WidgetUpdateService.class);

        //Set Remote adapter with intent
        views.setRemoteAdapter(R.id.list_widget_main, updateWidgetIntent);
        Logger.d("UpdateAppWidget called just now");

        //Intent to open activity when clicked the button
        Intent openAppIntent = new Intent(context, DashboardActivity.class);
        PendingIntent openAppPendingIntent = PendingIntent.getActivity(context, REQ_CODE_OPEN_APP, openAppIntent, 0);
        views.setOnClickPendingIntent(R.id.button_widget_attendance, openAppPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        //super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    //Widget created first time
    @Override
    public void onEnabled(Context context) {
        Toast.makeText(context, context.getString(R.string.java_widget_create), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    //Broadcast receiver to listen for Widget Updates
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            Logger.d("onReceive: Received Update Now");
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, ShowSubjectAppWidget.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.list_widget_main);
        }
        super.onReceive(context, intent);
    }

    //Method to force update by sending broadcast to update widget now
    public static void UpdateWidgetNow(Context context) {
        Logger.d("UpdateWidget: Update Broadcast Sending");
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, ShowSubjectAppWidget.class));
        context.sendBroadcast(intent);
    }
}

