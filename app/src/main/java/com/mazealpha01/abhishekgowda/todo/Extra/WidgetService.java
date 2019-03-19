package com.mazealpha01.abhishekgowda.todo.Extra;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.mazealpha01.abhishekgowda.todo.Helper.AdditionalHelper;
import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.Model.Task;
import com.mazealpha01.abhishekgowda.todo.R;
import com.mazealpha01.abhishekgowda.todo.Widget;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class WidgetService extends RemoteViewsService {
    private int colour;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetItemFactory(getApplicationContext(), intent);
    }

    class WidgetItemFactory implements RemoteViewsFactory {
        private Context context;

        private int appWidgetId;


        private Dbhelper dbhelper;
        private ArrayList<Task> list = new ArrayList<>();
        private String todaysdate;


        WidgetItemFactory(Context context, Intent intent) {
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);


        }

        @Override
        public void onCreate() {
            dbhelper = new Dbhelper(context);

            Date dt = new Date();
            Calendar calendartdy = Calendar.getInstance();
            calendartdy.setTime(dt);
            dt = calendartdy.getTime();
            DateFormat df2 = DateFormat.getDateInstance();
            todaysdate = df2.format(dt);
            list.addAll(dbhelper.getTodaysTask(todaysdate));
            //connect to data source
/*            SystemClock.sleep(3000);*/
        }

        @Override
        public void onDataSetChanged() {
            list.clear();
            list.addAll(dbhelper.getTodaysTask(todaysdate));

        }

        @Override
        public void onDestroy() {
            //close data source
            list.clear();
            dbhelper.close();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);
            views.setTextViewText(R.id.mazealpha01_widget_item_text, list.get(position).getTasktodo());
            views.setTextViewText(R.id.time_widget_txt, list.get(position).getTime());
            views.setInt(R.id.widget_tag_colour, "setBackgroundColor", Integer.valueOf(list.get(position).getTag()));

   /*         SystemClock.sleep(500);*/
            return views;
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



}