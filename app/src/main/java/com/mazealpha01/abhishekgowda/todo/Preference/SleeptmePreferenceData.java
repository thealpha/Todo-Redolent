package com.mazealpha01.abhishekgowda.todo.Preference;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mazealpha01.abhishekgowda.todo.AddTaskActivity;
import com.mazealpha01.abhishekgowda.todo.Extra.ReminderManager;
import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.Model.BasePreferenceData;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;
import com.mazealpha01.abhishekgowda.todo.R;
import com.mazealpha01.abhishekgowda.todo.SettingsActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SleeptmePreferenceData extends BasePreferenceData<SleeptmePreferenceData.ViewHolder> {

    private Usersettings usersettings;
    private Context context;
    private Dbhelper dbhelper;
    private ArrayList<Usersettings> usersettingsdata;
    private int getapm;


    public SleeptmePreferenceData(Usersettings usersettings, Context context) {
        this.usersettings =  usersettings;
        this.context = context;

    }

    public class ViewHolder extends BasePreferenceData.ViewHolder {
        private Button set;
        private TextView time;
        private ImageView cancel;

        public ViewHolder(View v) {
            super(v);
            time  = v.findViewById(R.id.time);
            set = v.findViewById(R.id.set);
            cancel = v.findViewById(R.id.cancel_sleep_notitfication);
        }
    }

    @Override
    public BasePreferenceData.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.sleeptmeitem,parent,false));

    }

    @Override
    public void bindViewHolder(final ViewHolder holder) {
        dbhelper = new Dbhelper(context);
        usersettingsdata =  new ArrayList<>();
        usersettingsdata = dbhelper.getusersettings();



        holder.set.setVisibility(View.GONE);
        holder.cancel.setVisibility(View.GONE);
        holder.time.setVisibility(View.VISIBLE);

        if (!usersettingsdata.get(0).getSleeptimenotify().equals("none")){
            cancel(holder);
            Date timestr = new Date(Long.valueOf(usersettingsdata.get(0).getSleeptimenotify()));
            DateFormat formatter = DateFormat.getTimeInstance();
            String timeformat = formatter.format(timestr);
            holder.time.setText(timeformat);
        }else {
            holder.time.setText(usersettingsdata.get(0).getSleeptimenotify());
        }

        holder.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SettingsActivity)context).showtimedailog();
                holder.set.setVisibility(View.VISIBLE);
                holder.time.setVisibility(View.GONE);

            }
        });





        holder.set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mins , hours , apm;
                usersettingsdata = dbhelper.getusersettings();
                holder.set.setVisibility(View.GONE);
                holder.time.setVisibility(View.VISIBLE);
                if (!usersettingsdata.get(0).getSleeptimenotify().equals("none")){
                    holder.cancel.setVisibility(View.VISIBLE);
                    Date timestr = new Date(Long.valueOf(usersettingsdata.get(0).getSleeptimenotify()));
                    DateFormat formatter = DateFormat.getTimeInstance();
                    String timeformat = formatter.format(timestr);
                    holder.time.setText(timeformat);
                    //  hours = String.valueOf(timestr.getHours());
                    //   mins = String.valueOf(timestr.getMinutes());
                    DateFormat df = new SimpleDateFormat("hh");
                    DateFormat dfs = new SimpleDateFormat("mm");
                    DateFormat apmsdf = new SimpleDateFormat("a");

                    mins = dfs.format(timestr);
                    hours = df.format(timestr);
                    apm = apmsdf.format(timestr);


                    Intent intent =  new Intent(context, ReminderManager.class);
                    intent.putExtra("notificationId", 101);
                    intent.putExtra("task","Time to sleep. Good night :)");
                    intent.putExtra("alarmID",101);

                    PendingIntent reminderIntent = PendingIntent.getBroadcast(context,101,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager remindermanager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                    Calendar starttime =  Calendar.getInstance();
                    starttime.set(Calendar.HOUR,Integer.valueOf(hours));
                    starttime.set(Calendar.MINUTE,Integer.valueOf(mins));
                    starttime.set(Calendar.MILLISECOND,0);
                    starttime.set(Calendar.AM_PM,getapmint(apm));
                    starttime.set(Calendar.SECOND,0);

                    long remindertms = starttime.getTimeInMillis();
                    remindermanager.setRepeating(AlarmManager.RTC_WAKEUP,
                            remindertms, AlarmManager.INTERVAL_DAY, reminderIntent);
                    cancel(holder);
                }



            }
        });


    }

    public void cancel(final ViewHolder holder){
        holder.cancel.setVisibility(View.VISIBLE);
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notificationIntent = new Intent(context, ReminderManager.class);
                notificationIntent.putExtra("task", "Sleep time now Good Night :)");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 101, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

                usersettings.setSleeptimenotify("none");
                dbhelper.updatetime(usersettings);

                usersettingsdata = dbhelper.getusersettings();
                holder.time.setText(usersettingsdata.get(0).getSleeptimenotify());
                holder.cancel.setVisibility(View.GONE);

            }
        });

    }



    private int getapmint(String apm){
        switch (apm){
            case "AM" :
                getapm = Calendar.AM;
                break;
            case "PM" :
                getapm = Calendar.PM;
                break;
        }
        return getapm;

    }
}
