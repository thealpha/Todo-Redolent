package com.mazealpha01.abhishekgowda.todo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.mazealpha01.abhishekgowda.todo.Extra.WidgetService;
import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;

import java.util.ArrayList;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;


/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    public static final String UPDATE_MEETING_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";

    public static final String EXTRA_ITEM = "com.mazealpha01.edockh.EXTRA_ITEM";
    private Dbhelper dbhelper;
    private ArrayList<Usersettings> usersettingsdata =  new ArrayList<>();


    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);

        if (intent.getAction().equals(UPDATE_MEETING_ACTION)) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            int appWidgetIds[] = mgr.getAppWidgetIds(new ComponentName(context,Widget.class));

            Log.e("received", intent.getAction());

            Log.d(TAG, "onReceive: id"+appWidgetIds);
            mgr.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.mazealpha01_widget_stack_view);


        }
        super.onReceive(context, intent);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        dbhelper =  new Dbhelper(context);
        usersettingsdata = dbhelper.getusersettings();

        updatewidget(context,appWidgetManager,appWidgetIds);

    }


    private void updatewidget(Context context , AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, AddTaskActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
/*
            SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            String buttonText = prefs.getString(KEY_BUTTON_TEXT + appWidgetId, "Press me");*/

            Intent serviceIntent = new Intent(context, WidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setOnClickPendingIntent(R.id.add_task_widget, pendingIntent);
            /*       views.setCharSequence(R.id.mazealpha01_widget_button, "setText", buttonText);*/
            views.setInt(R.id.top_bar,"setBackgroundColor",Integer.valueOf(usersettingsdata.get(0).getColourtheme()));
            views.setRemoteAdapter(R.id.mazealpha01_widget_stack_view, serviceIntent);
            views.setEmptyView(R.id.mazealpha01_widget_stack_view, R.id.mazealpha01_widget_empty_view);

            Intent view_main = new Intent(context, MainActivity.class);
            PendingIntent pmain = PendingIntent.getActivity(context, 0, view_main, 0);
            views.setOnClickPendingIntent(R.id.view_main, pmain);

            Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
            /*resizeWidget(appWidgetOptions, views);*/


            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }




}
