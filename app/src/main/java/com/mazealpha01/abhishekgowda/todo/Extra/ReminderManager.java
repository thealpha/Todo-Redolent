package com.mazealpha01.abhishekgowda.todo.Extra;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.mazealpha01.abhishekgowda.todo.AddTaskActivity;
import com.mazealpha01.abhishekgowda.todo.Helper.AdditionalHelper;
import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.MainActivity;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;
import com.mazealpha01.abhishekgowda.todo.R;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderManager extends BroadcastReceiver {
    private static final String TAG = "ReminderManager";
    private ArrayList<Usersettings> usersettingsdata;
    private Dbhelper dbhelper;
    private AdditionalHelper additionalHelper;


    @Override
    public void onReceive(Context context, Intent intent) {
        dbhelper = new Dbhelper(context);
        additionalHelper = new AdditionalHelper();
        usersettingsdata = new ArrayList<>();
        usersettingsdata = dbhelper.getusersettings();



      //  Uri soundUri = Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + R.raw.pew_pew_pew);
        int notification_ID = intent.getIntExtra("notificationId", 0);
        String tasktodo = intent.getStringExtra("task");
        Integer alarmId = intent.getIntExtra("alarmID",0);
        Intent intent1 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, alarmId, intent1, 0);

        NotificationCompat.Builder noit = new NotificationCompat.Builder(context, "NOTIFICATION");
        noit.setSmallIcon(R.drawable.ic_correct_symbol);
        noit.setContentTitle("Reminder");
        noit.setCategory(NotificationCompat.CATEGORY_REMINDER);
        noit.setContentText(tasktodo);
        noit.setPriority(NotificationCompat.PRIORITY_MAX);
        noit.setContentIntent(pendingIntent);
        noit.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(tasktodo));
       // noit.setSound(false);
        noit.setAutoCancel(true);
        noit.setColor(Integer.valueOf(usersettingsdata.get(0).getColourtheme()));
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notification_ID, noit.build());
    
        if (usersettingsdata.get(0).getNotificationtune() == 0){
            Log.d(TAG, "onReceive: no sound");
        }else {
            MediaPlayer mp= MediaPlayer.create(context,additionalHelper.getRawtune(usersettingsdata.get(0).getNotificationtune()));
            mp.start();
        }


    }

}
