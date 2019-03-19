package com.mazealpha01.abhishekgowda.todo;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.mazealpha01.abhishekgowda.todo.Adapter.TaskAdapter;

import com.mazealpha01.abhishekgowda.todo.Extra.BottomNavigationHelper;
import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.Helper.AdditionalHelper;
import com.mazealpha01.abhishekgowda.todo.Model.CalenderMark;
import com.mazealpha01.abhishekgowda.todo.Model.Task;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public  class CalenderActivity extends AppCompatActivity implements  CalendarView.OnCalendarSelectListener,
        CalendarView.OnCalendarLongClickListener,
        CalendarView.OnMonthChangeListener,
        CalendarView.OnYearChangeListener,
        CalendarView.OnWeekChangeListener,
        CalendarView.OnViewChangeListener,
        CalendarView.OnCalendarInterceptListener,
        View.OnClickListener
{


    private BottomNavigationView bottomNavigationView;
    private static final String TAG = CalenderActivity.class.getName();
    private static final int ACTIVITY_NUM = 1;
    private Context context = CalenderActivity.this;
    private CalendarView mCalenderView;
    private TextView year,month_date;
    private CalendarLayout mCalendarLayout;
    // private Integer month;
    private RecyclerView taskRecyclerView;
    private Dbhelper dbhelper;
    private ArrayList<Task> listTask;
    private TaskAdapter taskadapter;
    int mSomeMemberVariable = 123;
    private String mthdate;
    private AdditionalHelper additionalHelper;
    private Usersettings usersettings;
    private ArrayList<Usersettings> usersettingsdata;

    private List<CalenderMark> calenderlistdata;
    private Map<String, Calendar> map = new HashMap<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        dbhelper = new Dbhelper(context);
        usersettings = new Usersettings();
        usersettingsdata =  new ArrayList<>();
        usersettingsdata = dbhelper.getusersettings();
        setheme(Integer.valueOf(usersettingsdata.get(0).getColourtheme()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_activity);
        additionalHelper = new AdditionalHelper();
        mCalenderView = (CalendarView) findViewById(R.id.calendarView);
        year = (TextView)findViewById(R.id.year_txt);
        month_date = (TextView)findViewById(R.id.month_date);
        taskRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        // horizontalExpCalendar.setWeekPagerVisibility(View.GONE);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        BottomNavigationHelper.enableNavigation(context,this,bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
        init();
        initObjects();

    }

    private void init() {
        mCalendarLayout = (CalendarLayout) findViewById(R.id.calendarLayout);
        mCalenderView.setOnYearChangeListener(this);
        mCalenderView.setOnCalendarSelectListener(this);
        mCalenderView.setOnMonthChangeListener(this);
        mCalenderView.setOnCalendarLongClickListener(this, true);
        mCalenderView.setOnWeekChangeListener(this);
        mCalenderView.setOnCalendarInterceptListener(this);
        mCalenderView.setOnViewChangeListener(this);

        year.setText(String.valueOf(mCalenderView.getCurYear()));

        calenderlistdata = new ArrayList<>();
        //mYear = mCalenderView.getCurYear();
        month_date.setText(additionalHelper.CnvrtMnT(mCalenderView) + ","+ mCalenderView.getCurDay() );
        mthdate = additionalHelper.CnvrtMnT(mCalenderView) + " " + mCalenderView.getCurDay() + "," +" " + mCalenderView.getCurYear();
        new MyTask(CalenderActivity.this).execute();


    }

    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        return false;
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
    }

    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {

    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {
    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        year.setVisibility(View.VISIBLE);
        month_date.setText(additionalHelper.onCnvrtMnT(calendar) + "," + calendar.getDay());
        mthdate = additionalHelper.onCnvrtMnT(calendar)+ " " + calendar.getDay() + "," + " "+  calendar.getYear();
        year.setText(String.valueOf(calendar.getYear()));
        if (isClick) {
            mthdate = additionalHelper.onCnvrtMnT(calendar)+ " " + calendar.getDay() + "," + " "+  calendar.getYear();
        }

        new MyTask(CalenderActivity.this).execute();



    }

    public void settint(Map<String,Calendar> map) {
        mCalenderView.setSchemeDate(map);
    }


    public Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {

        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }


    @Override
    public void onMonthChange(int year, int month) {

        calenderlistdata = dbhelper.calendarMark(String.valueOf(year),String.valueOf(month));
        for (int i = 0; i < calenderlistdata.size() ; i++ ){
            Log.d(TAG, "onCalendarSelect: date " +  calenderlistdata.get(i).getDate());

            if (calenderlistdata.get(i).getDate().equals( "1") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 1, Color.WHITE, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 1, Color.WHITE, "."));
            }
            if (calenderlistdata.get(i).getDate().equals( "2") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 2, Color.WHITE, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 2, Color.WHITE, "."));
            }
            if (calenderlistdata.get(i).getDate().equals( "3") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 3, Color.WHITE, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 3, Color.WHITE, "."));
            }
            if (calenderlistdata.get(i).getDate().equals( "4") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 4, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 4, 0xFF40db25, "."));
            }
            if (calenderlistdata.get(i).getDate().equals( "5") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 5, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 5, 0xFF40db25, "."));
            }
            if (calenderlistdata.get(i).getDate().equals( "6") ) {

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 6, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 6, 0xFF40db25, "."));
            }
            if (calenderlistdata.get(i).getDate().equals( "7") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 7, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 7, 0xFF40db25, "."));
            }  if (calenderlistdata.get(i).getDate().equals( "8") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 8, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 8, 0xFF40db25, "."));
            }  if (calenderlistdata.get(i).getDate().equals( "9") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 9, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 9, 0xFF40db25, "."));
            }  if (calenderlistdata.get(i).getDate().equals( "10") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 10, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 10, 0xFF40db25, "."));
            }  if (calenderlistdata.get(i).getDate().equals( "11") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 11, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 11, 0xFF40db25, "."));
            }  if (calenderlistdata.get(i).getDate().equals( "12") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 12, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 12, 0xFF40db25, "."));
            }  if (calenderlistdata.get(i).getDate().equals( "13") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 13, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 13, 0xFF40db25, "."));
            }  if (calenderlistdata.get(i).getDate().equals( "14") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 14, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 14, 0xFF40db25, "."));
            }  if (calenderlistdata.get(i).getDate().equals( "15") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 15, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 15, 0xFF40db25, "."));
            }  if (calenderlistdata.get(i).getDate().equals( "16") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 16, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 16, 0xFF40db25, "."));
            }  if (calenderlistdata.get(i).getDate().equals( "17") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 17, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 17, 0xFF40db25, "."));
            }  if (calenderlistdata.get(i).getDate().equals( "18") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 18, Color.WHITE, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 18, Color.WHITE, "."));
            }  if (calenderlistdata.get(i).getDate().equals( "19") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 19, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 19, 0xFF40db25, "."));
            } if (calenderlistdata.get(i).getDate().equals( "20") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 20, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 20, 0xFF40db25, "."));
            } if (calenderlistdata.get(i).getDate().equals( "21") ){

                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 21, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 21, 0xFF40db25, "."));
            } if (calenderlistdata.get(i).getDate().equals( "22") ){
                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 22, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 22, 0xFF40db25, "."));
            } if (calenderlistdata.get(i).getDate().equals( "23") ){
                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 23, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 23, 0xFF40db25, "."));
            } if (calenderlistdata.get(i).getDate().equals( "24") ){
                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 24, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 24, 0xFF40db25, "."));
            } if (calenderlistdata.get(i).getDate().equals( "25") ){
                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 25, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 25, 0xFF40db25, "."));
            } if (calenderlistdata.get(i).getDate().equals( "26") ){
                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 26, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 26, 0xFF40db25, "."));
            } if (calenderlistdata.get(i).getDate().equals( "27")){
                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 27, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 27, 0xFF40db25, "."));
            } if (calenderlistdata.get(i).getDate().equals( "28") ){
                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 28, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 28, 0xFF40db25, "."));
            } if (calenderlistdata.get(i).getDate().equals( "29") ){
                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 29, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 29, 0xFF40db25, "."));
            } if (calenderlistdata.get(i).getDate().equals( "30") ){
                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 30, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 30, 0xFF40db25, "."));
            }if (calenderlistdata.get(i).getDate().equals( "31") ){
                map.put(getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 31, 0xFF40db25, ".").toString(),
                        getSchemeCalendar(Integer.valueOf(calenderlistdata.get(i).getYear()), Integer.valueOf(calenderlistdata.get(i).getMonth()), 31, 0xFF40db25, "."));
            }

            settint(map);
        }

    }

    @Override
    public void onViewChange(boolean isMonthView) {

    }

    @Override
    public void onWeekChange(List<Calendar> weekCalendars) {

    }

    @Override
    public void onYearChange(int year) {

    }

    @Override
    public void onClick(View v) {

    }




    private void initObjects() {
        listTask = new ArrayList<>();
        taskadapter = new TaskAdapter(listTask, this,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        taskRecyclerView.setLayoutManager(mLayoutManager);
        taskRecyclerView.setItemAnimator(new DefaultItemAnimator());
        taskRecyclerView.setHasFixedSize(false);
        taskRecyclerView.setAdapter(taskadapter);
        taskRecyclerView.setNestedScrollingEnabled(false);
        new MyTask(CalenderActivity.this).execute();



    }

    private static class MyTask extends AsyncTask<Void, Void, String> {

        private WeakReference<CalenderActivity> activityReference;
        // only retain a weak reference to the activity

        MyTask(CalenderActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(Void... params) {
            // do some long running task...


                    final CalenderActivity activity = activityReference.get();
                    activity.listTask.clear();
                    activity.listTask.addAll(activity.dbhelper.getSpecificTask(activity.mthdate));


            return "task finished";
        }

        @Override
        protected void onPostExecute(String result) {
            // get a reference to the activity if it is still there
            CalenderActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            // modify the activity's UI
            // access Activity member variables
            activity.mSomeMemberVariable = 321;
            activity.taskadapter.notifyDataSetChanged();
        }
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


}

