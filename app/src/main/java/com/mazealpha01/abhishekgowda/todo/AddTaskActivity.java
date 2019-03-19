package com.mazealpha01.abhishekgowda.todo;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mazealpha01.abhishekgowda.todo.Extra.WidgetService;
import com.mazealpha01.abhishekgowda.todo.Helper.AdditionalHelper;
import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.Extra.ReminderManager;
import com.mazealpha01.abhishekgowda.todo.Model.CalenderMark;
import com.mazealpha01.abhishekgowda.todo.Model.Item;
import com.mazealpha01.abhishekgowda.todo.Model.Task;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;
import com.fastaccess.datetimepicker.DatePickerFragmentDialog;
import com.fastaccess.datetimepicker.DateTimeBuilder;
import com.fastaccess.datetimepicker.TimePickerFragmentDialog;
import com.fastaccess.datetimepicker.callback.DatePickerCallback;
import com.fastaccess.datetimepicker.callback.TimePickerCallback;

import com.google.android.material.snackbar.Snackbar;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Queue;
import java.util.Random;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity  extends AppCompatActivity  implements DatePickerCallback,TimePickerCallback {
    private Dbhelper dbhelper;
    private EditText task,note;
    private TextView datet,time,tune,item,warning;
    private ImageView dateimage,timeimage,tuneimage,itemimage,tag,noteimage,done_img,cancle_img;
    private Task taskmodel;
    private View tagcol;
    private Item itemmodel;
    private String dateresult,timeresult;
    private static final String TAG = "AddTaskActivity";
    private String tagcolor,note_task,todo_task;
    private Context mContext;
    private MediaPlayer mediaPlayer;
    private int calendarMardId,getMonth ,getapm,alarmID;
    private String hours,mins,day,month,year,apm;
    private ArrayList<Usersettings> usersettingsdata;
    private RelativeLayout layout;
    private AdditionalHelper additionalHelper;
    private String todaysdate , selecteddate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        taskmodel = new Task();
        itemmodel = new Item();
        usersettingsdata = new ArrayList<>();
        dbhelper = new Dbhelper(this);
        usersettingsdata = dbhelper.getusersettings();
        setheme(Integer.valueOf(usersettingsdata.get(0).getColourtheme()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        additionalHelper = new AdditionalHelper();
        dateimage = (ImageView) findViewById(R.id.calender_img);
        timeimage = (ImageView) findViewById(R.id.time_img);
        tuneimage = (ImageView) findViewById(R.id.notification_bell);
        itemimage = (ImageView) findViewById(R.id.list_img);
        tag = (ImageView) findViewById(R.id.tags_img);
        noteimage = (ImageView) findViewById(R.id.note_img);
        datet = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        tune = (TextView) findViewById(R.id.tune);
        item = (TextView) findViewById(R.id.listitem);
        task = (EditText) findViewById(R.id.task_txt);
        note = (EditText) findViewById(R.id.notes);
        layout = (RelativeLayout)findViewById(R.id.layout);
        cancle_img = (ImageView)findViewById(R.id.cancel_action);
        done_img =(ImageView)findViewById(R.id.done_action);
        warning = (TextView)findViewById(R.id.warnin) ;
        tagcol = (View) findViewById(R.id.tagcol);
        mContext = AddTaskActivity.this;


        cancle_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

            }
        });


        layout.setBackgroundColor(Integer.valueOf(usersettingsdata.get(0).getColourtheme()));
        tune.setText(additionalHelper.gettune(usersettingsdata.get(0).getNotificationtune()));

        createNotificationChannel();
        addtask();
        multasfuc();
        //init();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Integer.valueOf(usersettingsdata.get(0).getColourtheme()));
        }


        GradientDrawable drawable = (GradientDrawable)tagcol.getBackground();
        drawable.setColor(Color.WHITE);
        Random random = new Random();
        calendarMardId = random.nextInt(9999 - 1000) + 1000;
        calendarMardId = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        alarmID = random.nextInt(9999 - 1000) + 1000;
        alarmID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

    }

    private void multasfuc(){
        datet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragmentDialog.newInstance().show(getSupportFragmentManager(), "DatePickerFragmentDialog");

            }
        });

        dateimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragmentDialog.newInstance(
                ).show(getSupportFragmentManager(), "DatePickerFragmentDialog");
            }
        });

        timeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragmentDialog.newInstance().show(getSupportFragmentManager(), "TimePickerFragmentDialog");
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragmentDialog.newInstance(
                ).show(getSupportFragmentManager(), "TimePickerFragmentDialog");

            }
        });

        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gettagcolor();
            }
        });

        tagcol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gettagcolor();

            }
        });
        tuneimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Change notification tune in settings", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }



    private void setupreminder(){
        Intent intent =  new Intent(AddTaskActivity.this,ReminderManager.class);
        intent.putExtra("notificationId", calendarMardId);
        intent.putExtra("task",todo_task);
        intent.putExtra("alarmID",alarmID);

        PendingIntent reminderIntent = PendingIntent.getBroadcast(mContext,alarmID,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager remindermanager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar starttime =  Calendar.getInstance();
        starttime.set(Calendar.HOUR,Integer.valueOf(hours));
        starttime.set(Calendar.MINUTE,Integer.valueOf(mins));
        starttime.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
        starttime.set(Calendar.MILLISECOND, 0);
        starttime.set(Calendar.AM_PM,getapmint(apm));
        starttime.set(Calendar.YEAR,Integer.valueOf(year));
        starttime.set(Calendar.MONTH,getCalenderMonth(month));
        starttime.set(Calendar.SECOND,0);

        long remindertms = starttime.getTimeInMillis();
        remindermanager.set(AlarmManager.RTC_WAKEUP,  remindertms, reminderIntent);

    }
    private  void setupmark() {

        CalenderMark calenderMark =  new CalenderMark();
        calenderMark.setTaskid(calendarMardId);
        calenderMark.setDate(removeLeadingZeroes(day));
        calenderMark.setYear(year);
        calenderMark.setMonth(removeLeadingZeroes(month));
        boolean result = dbhelper.addmark(calenderMark);
        if (result){
            Log.d(TAG, "setupmark: calender successfully marked");
        }
    }

    private int getCalenderMonth(String month){
        switch (month){
            case "01" :
                getMonth = Calendar.JANUARY;
                break;
            case "02":
                getMonth = Calendar.FEBRUARY;
                break;
            case "03":
                getMonth = Calendar.MARCH;
                break;
            case "04":
                getMonth = Calendar.APRIL;
                break;
            case "05":
                getMonth = Calendar.MAY;
                break;
            case "06":
                getMonth = Calendar.JUNE;
                break;
            case "07":
                getMonth = Calendar.JULY;
                break;
            case "08":
                getMonth = Calendar.AUGUST;
                break;
            case "09":
                getMonth = Calendar.SEPTEMBER;
                break;
            case "10":
                getMonth = Calendar.OCTOBER;
                break;
            case "11":
                getMonth = Calendar.NOVEMBER;
                break;
            case "12":
                getMonth = Calendar.DECEMBER;
                break;
        }
        return getMonth;
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

    private void createNotificationChannel() {
       /* Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.pikachuuuuuuu);*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             /*   AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build();*/

            CharSequence name = "Task";
            String description = "For task notification";

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("NOTIFICATION", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            /*channel.setSound(soundUri, audioAttributes);*/
            channel.setSound(null, null);
            /*channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});*/
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }

    private void addtask(){
        done_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done_img.setClickable(false);
                 todo_task = task.getText().toString();
                 note_task = note.getText().toString();
                if (todo_task.equals("")){
                    Snackbar.make(v, "Task is empty ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {

                    if (tagcolor != null){

                        if (dateresult != null && timeresult == null){
                            Snackbar.make(v, "Add time to proceed ", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }else if (dateresult == null && timeresult != null){

                            Snackbar.make(v, "Add Date to proceed ", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }else {
                            warning.setVisibility(View.GONE);
                            tagcol.setVisibility(View.VISIBLE);
                            taskmodel.setTasktodo(todo_task);
                            taskmodel.setNote(note_task);
                            taskmodel.setTag(tagcolor);
                            taskmodel.setDate(dateresult);
                            taskmodel.setComplete("0");
                            taskmodel.setTime(timeresult);
                            taskmodel.setCalendarMark(calendarMardId);
                            taskmodel.setAlarmID(alarmID);
                            taskmodel.setSound(tune.getText().toString());
                            boolean  insertdata = dbhelper.addnewtodo(taskmodel);
                            if (insertdata == true ){
                                //  Toast.makeText(AddTaskActivity.this, "Successfully added task :)", Toast.LENGTH_SHORT).show();
                                if (timeresult != null && dateresult != null){
                                    setupreminder();
                                    setupmark();
                                }

                               /* Intent updateIntent = new Intent();
                                updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                                updateIntent.putExtra(Widget.UPDATE_MEETING_ACTION, "android.appwidget.action.APPWIDGET_UPDATE");

                                onMessage(mContext,updateIntent);*/
                                Intent toMain = new Intent(AddTaskActivity.this,MainActivity.class);
                                startActivity(toMain);
                                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                            }else {
                                Snackbar.make(v, "Oops something went wrong :(", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }

                        }


                    }else {
                        tagcol.setVisibility(View.GONE);
                        warning.setVisibility(View.VISIBLE);
                    }

                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        done_img.setClickable(true);
                    }
                }, 1000);
            }
        });
    }
  /*  protected void onMessage(Context context, Intent data) {

        Intent intent_meeting_update=new  Intent(context,Widget.class);
        intent_meeting_update.setAction(Widget.UPDATE_MEETING_ACTION);
        sendBroadcast(intent_meeting_update);
    }
*/
    private void gettagcolor() {
        new SpectrumDialog.Builder(getApplication().getBaseContext())
                .setColors(R.array.rainbow)
                .setSelectedColorRes(R.color.colorAccent)
                .setDismissOnColorSelected(true)
                .setOutlineWidth(2)
                .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                    @Override public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                        if (positiveResult) {
                            Log.d(TAG, "onColorSelected: colour"+Integer.toHexString(color).toUpperCase());
                            tagcolor = String.valueOf(color);
                            warning.setVisibility(View.GONE);
                            tagcol.setVisibility(View.VISIBLE);
                            GradientDrawable drawable = (GradientDrawable)tagcol.getBackground();
                            drawable.setColor(color);
                        }
                    }
                }).build().show(getSupportFragmentManager(), "dialog");

    }

    @Override
    public void onDateSet(long date) {
        Date dates=new Date(date);
        DateFormat df2 = DateFormat.getDateInstance();
        String dateText = df2.format(dates);
        System.out.println(dateText);
        dateresult = String.valueOf(dateText);
        datet.setText(dateText);


        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
         month = monthFormat .format(date);
        SimpleDateFormat yearfor = new SimpleDateFormat("YYYY");
         year = yearfor .format(date);
        SimpleDateFormat dayfor = new SimpleDateFormat("dd");
         day = dayfor .format(date);

        SimpleDateFormat dateformate = new SimpleDateFormat("MM/dd/yyyy");
        selecteddate = dateformate.format(date);





    }

    @Override
    public void onTimeSet(long timeOnly, long dateWithTime) {
        Date timestr = new Date(timeOnly);
        DateFormat formatter = DateFormat.getTimeInstance();
        String timeformat = formatter.format(timestr);
        timeresult = String.valueOf(timeformat);
        time.setText(timeformat);
     //  hours = String.valueOf(timestr.getHours());
     //   mins = String.valueOf(timestr.getMinutes());
         DateFormat df = new SimpleDateFormat("hh");
         DateFormat dfs = new SimpleDateFormat("mm");
         DateFormat apmsdf = new SimpleDateFormat("a");
         mins = dfs.format(timestr);
         hours = df.format(timestr);
         apm = apmsdf.format(timestr);




    }

    public static boolean isValidDate(String pDateString) throws ParseException {
        Date date = new SimpleDateFormat("MM/dd/yyyy").parse(pDateString);

        return new Date().before(date);
    }

    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }
    private void setheme(int colour){
        switch (colour){
            case -16737057:
                setTheme(R.style.SkyBlue);
                break;
            case -14129173:
                setTheme(R.style.DarkBlue);
                break;
            case -3317248:
                setTheme(R.style.Brown);
                break;
            case -16743277:
                setTheme(R.style.Green);
            case -9579063:
                setTheme(R.style.LightGreen);
                break;
            case -1791216:
                setTheme(R.style.Yellow);
                break;
            case -2003306:
                setTheme(R.style.Pink);
                break;
            case -2811066:
                setTheme(R.style.Red);
                break;
            case -8906554:
                setTheme(R.style.Purple);
                break;
            case -10064414:
                setTheme(R.style.LightPurple);
                break;
            case -153460:
                setTheme(R.style.LightOrange);
                break;
        }
    }
    public static String removeLeadingZeroes(String value) {
        return new Integer(value).toString();
    }


}


