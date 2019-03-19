package com.mazealpha01.abhishekgowda.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager.widget.ViewPager;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mazealpha01.abhishekgowda.todo.Adapter.TaskAdapter;
import com.mazealpha01.abhishekgowda.todo.Extra.BottomNavigationHelper;
import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.Model.Task;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity  {
    private static final String TAG = MainActivity.class.getName();
    private Dbhelper dbhelper;
    // private ListView listView;
    private ArrayList<Task> listTask;
    private ArrayList<Usersettings> usersettingsdata;
    private TaskAdapter taskadapter;
    private RecyclerView taskRecyclerView;
    private Context context = MainActivity.this;
    int mSomeMemberVariable = 123;
    CountDownTimer cd;
    private View addtask;
    private BottomNavigationView bottomNavigationView;
    private static final int ACTIVITY_NUM = 0;
    private ViewPager viewPager;
    private Usersettings usersettings;
    private SharedPreferences preferences = null;
    private String themename;
    private RelativeLayout slctdy,slctmr, slcinbx;
    private TextView txttdy,txttmr, txtinbx,notsk1,notsk2;
    private View vtdy,vtmr, vinbx;
    private boolean tdy = false ,tmr = false , ibx = true;
    private Animation animshrink,animexpand;
    private String todaysdate , tommorowsdate;
/*    private InterstitialAd mInterstitialAd;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences("com.mazealpha01.abhishekgowda.todo", MODE_PRIVATE);
        usersettings = new Usersettings();
        dbhelper =  new Dbhelper(this);
        usersettingsdata = new ArrayList<>();
        usersettingsdata = dbhelper.getusersettings();
        setheme(Integer.valueOf(usersettingsdata.get(0).getColourtheme()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO if to implement ads which are annoying
  /*      MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/8691691433");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());*/


        slctdy = (RelativeLayout)findViewById(R.id.today_lay);
        slctmr = (RelativeLayout)findViewById(R.id.tommorow_lay);
        slcinbx = (RelativeLayout)findViewById(R.id.inbox_lay);

        txttdy = (TextView) findViewById(R.id.today);
        txttmr = (TextView) findViewById(R.id.tommorow);
        txtinbx = (TextView) findViewById(R.id.inbox);

        notsk1 = (TextView)findViewById(R.id.ntask1);
        notsk2 = (TextView)findViewById(R.id.ntask2);


        vtdy = (View) findViewById(R.id.v_tdy);
        vtmr = (View) findViewById(R.id.v_tmr);
        vinbx = (View) findViewById(R.id.v_inbox);
        addtask = (View) findViewById(R.id.addtask);

        taskRecyclerView = (RecyclerView)findViewById(R.id.list_view);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        setuptheme();



        Date dt = new Date();
        Calendar calendartdy = Calendar.getInstance();
        calendartdy.setTime(dt);
        dt = calendartdy.getTime();
        DateFormat df2 = DateFormat.getDateInstance();
        todaysdate = df2.format(dt);

        Calendar calendartmr = Calendar.getInstance();
        calendartmr.setTime(dt);
        calendartmr.add(Calendar.DATE,1);
        dt = calendartmr.getTime();
        tommorowsdate = df2.format(dt);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date before = null;


        try {
            before = sdf.parse("2017-01-13 10:20:30");
            if (before.equals(dt)){
                Toast.makeText(context, "true", Toast.LENGTH_SHORT).show();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }



        animshrink = AnimationUtils.loadAnimation(getBaseContext(), R.anim.shrink);
        animexpand = AnimationUtils.loadAnimation(getBaseContext(), R.anim.expand);

        BottomNavigationHelper.enableNavigation(context,this,bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
        // listView = (ListView)findViewById(R.id.list_view);

        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtask.setClickable(false);
                Intent intent = new Intent(MainActivity.this,AddTaskActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addtask.setClickable(true);
                    }
                }, 1000);
            }
        });

        selectoranim();
      /*  getalltask();*/
        initObjects();

        Intent updateIntent = new Intent();
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(Widget.UPDATE_MEETING_ACTION, "android.appwidget.action.APPWIDGET_UPDATE");

        onMessage(context,updateIntent);




    }

    //------------------------------------------------>ADS<--------------------------------------------------//
/*
    private void showsamplead(){
        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });
        mInterstitialAd.setAdListener(new AdListener() {
            @Override

            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Toast.makeText(context, "ad loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(context, "its an add", Toast.LENGTH_SHORT).show();
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Toast.makeText(context, "left app", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                Toast.makeText(context, "ad closed", Toast.LENGTH_SHORT).show();
            }
        });
    }
*/

    private void selectoranim(){
        slctdy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slctdy.setClickable(false);
                slcinbx.setClickable(true);
                slctmr.setClickable(true);

                tdy = true;
                expandanim(vtdy,txttdy);
                if (tmr){
                    shrink(vtmr,txttmr);
                    tmr = false;
                } else if (ibx) {
                    shrink(vinbx, txtinbx);
                    ibx = false;
                }
                updatelist();


            }
        });

        slctmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slctmr.setClickable(false);
                slcinbx.setClickable(true);
                slctdy.setClickable(true);
                tmr = true;
                expandanim(vtmr,txttmr);
                if (tdy){
                    shrink(vtdy,txttdy);
                    tdy = false;
                } else if (ibx) {
                    shrink(vinbx, txtinbx);
                    ibx = false;
                }
                updatelist();

            }
        });

        slcinbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slcinbx.setClickable(false);
                slctmr.setClickable(true);
                slctdy.setClickable(true);
                ibx = true;
                expandanim(vinbx, txtinbx);
                if (tdy){
                    shrink(vtdy,txttdy);
                    tdy = false;

                } else if (tmr) {
                    shrink(vtmr,txttmr);
                    tmr = false;
                }
                updatelist();

            }
        });


    }



    private void expandanim(final View view , final TextView textView) {
        //TODO for expandanimation
    /*    view.startAnimation(animexpand);
        animexpand.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textView.setTextColor(getColor(R.color.white));
                view.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/
        textView.setTextColor(getColor(R.color.white));
        view.setVisibility(View.VISIBLE);

    }

    private void shrink(final View view , final TextView textView) {

        //TODO for shrinkanimation
     /*   view.startAnimation(animshrink);
        animshrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setTextColor(getColor(R.color.black));
                view.setVisibility(View.INVISIBLE);

            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/

        textView.setTextColor(getColor(R.color.black));
        view.setVisibility(View.INVISIBLE);

    }


    private void initObjects() {
        listTask = new ArrayList<>();
        taskadapter = new TaskAdapter(listTask, this,this);
        taskadapter.setHasStableIds(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        taskRecyclerView.setLayoutManager(mLayoutManager);
        taskRecyclerView.setHasFixedSize(true);
        taskRecyclerView.setItemAnimator(null);
        taskRecyclerView.setAdapter(taskadapter);
         updatelist();


    }

    public void updatelist(){
        new MyTask(MainActivity.this).execute();
    }


    private static class MyTask extends AsyncTask<Void, Void, String> {

        private WeakReference<MainActivity> activityReference;
        // only retain a weak reference to the activity

        MyTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(Void... params) {
            // do some long running task...
            MainActivity activity = activityReference.get();
            activity.listTask.clear();
            if (activity.tdy){
                activity.listTask.addAll(activity.dbhelper.getTodaysTask(activity.todaysdate));
            }else if (activity.tmr){
                activity.listTask.addAll(activity.dbhelper.getTommorowsTask(activity.tommorowsdate));
            } else if (activity.ibx) {
                activity.listTask.addAll(activity.dbhelper.getAllTask());
            }

            return "task finished";
        }

        @Override
        protected void onPostExecute(String result) {
            // get a reference to the activity if it is still there
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            // modify the activity's UI
            // access Activity member variables
            activity.mSomeMemberVariable = 321;
            activity.taskadapter.notifyDataSetChanged();
            activity.checkempty();
        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


private void setuptheme(){

    LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(MainActivity.this,R.drawable.drawable_button);
    assert layerDrawable != null;

    GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable
            .findDrawableByLayerId(R.id.gradientDrawble);
    gradientDrawable.setColor(Integer.valueOf(usersettingsdata.get(0).getColourtheme()));
    addtask.setBackground(layerDrawable);

        GradientDrawable drawabletdy = (GradientDrawable)vtdy.getBackground();
        drawabletdy.setColor(Integer.valueOf(usersettingsdata.get(0).getColourtheme()));

        GradientDrawable drawabletmr = (GradientDrawable)vtmr.getBackground();
        drawabletmr.setColor(Integer.valueOf(usersettingsdata.get(0).getColourtheme()));

        GradientDrawable drawableinbx = (GradientDrawable)vinbx.getBackground();
        drawableinbx.setColor(Integer.valueOf(usersettingsdata.get(0).getColourtheme()));


}

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void checkempty() {
        if (listTask.size() == 0){
            notsk1.setVisibility(View.VISIBLE);
            notsk2.setVisibility(View.VISIBLE);
        }else {
            notsk1.setVisibility(View.GONE);
            notsk2.setVisibility(View.GONE);
        }
    }
    private String setheme(int colour){
        switch (colour){
            case -16737057:
                themename = "sky blue";
                setTheme(R.style.SkyBlue);
                break;
            case -14129173:
                themename = "dark blue";
                setTheme(R.style.DarkBlue);
                break;
            case -3317248:
                themename = "brown";
                setTheme(R.style.Brown);
                break;
            case -16743277:
                themename = "green";
                setTheme(R.style.Green);
            case -9579063:
                themename = "light green";
                setTheme(R.style.LightGreen);
                break;
            case -1791216:
                themename = "yellow";
                setTheme(R.style.Yellow);
                break;
            case -2003306:
                themename = "pink";
                setTheme(R.style.Pink);
                break;
            case -2811066:
                themename = "red";
                setTheme(R.style.Red);
                break;
            case -8906554:
                themename = "purple";
                setTheme(R.style.Purple);
                break;
            case -10064414:
                themename = "light purple";
                setTheme(R.style.LightPurple);
                break;
            case -153460:
                themename = "light orange";
                setTheme(R.style.LightOrange);
                break;
        }
        return themename;
    }

    protected void onMessage(Context context, Intent data) {
        Intent intent_meeting_update=new  Intent(context,Widget.class);
        intent_meeting_update.setAction(Widget.UPDATE_MEETING_ACTION);
        sendBroadcast(intent_meeting_update);
    }
}


