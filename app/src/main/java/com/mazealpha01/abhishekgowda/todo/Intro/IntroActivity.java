package com.mazealpha01.abhishekgowda.todo.Intro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.mazealpha01.abhishekgowda.todo.Adapter.Intropageradapter;
import com.mazealpha01.abhishekgowda.todo.Adapter.TaskAdapter;
import com.mazealpha01.abhishekgowda.todo.BuildConfig;
import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.MainActivity;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;
import com.mazealpha01.abhishekgowda.todo.R;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class IntroActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TextView[] mDots;
    private LinearLayout mDotLayout;
    private Context mContext;
    private Dbhelper dbhelper;
    private ArrayList<Usersettings> usersettingsdata;
    private SharedPreferences preferences = null;
    private Usersettings usersettings;
    private Button getstarted;

    private static final String TAG = "IntroActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preferences = getSharedPreferences("com.mazealpha01.abhishekgowda.todo", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introactivity);
        usersettings = new Usersettings();
        dbhelper = new Dbhelper(this);
        usersettingsdata =  new ArrayList<>();
        usersettingsdata = dbhelper.getusersettings();
        
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mDotLayout = (LinearLayout)findViewById(R.id.dot_indicator) ;
        getstarted = (Button)findViewById(R.id.getstarted);
        // Set an Adapter on the ViewPager
        mViewPager.setAdapter(new Intropageradapter(getSupportFragmentManager()));
        // Set a PageTransformer
        mContext = IntroActivity.this;
        mViewPager.setPageTransformer(false, new IntroPageTransformer());
        getWindow().setNavigationBarColor(getResources().getColor(R.color.dark_blue));
        getWindow().setStatusBarColor(getResources().getColor(R.color.dark_blue));
        mViewPager.addOnPageChangeListener(viewpager);
        addDotsIndicator(0);
        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTomain();

            }
        });

    }

    private void addDotsIndicator(int position){
        mDots = new TextView[4];
        mDotLayout.removeAllViews();
        for(int i=0; i<mDots.length; i++ ){
            mDots[i] = new  TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));


            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.transpernt));
            mDotLayout.addView(mDots[i]);
        }
        if (mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewpager = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            switch (position){
                case 0 :
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.dark_blue));
                    getWindow().setStatusBarColor(getResources().getColor(R.color.dark_blue));
                    break;
                case 1:
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.yellow));
                    getWindow().setStatusBarColor(getResources().getColor(R.color.yellow));
                    break;
                case 2:
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.green));
                    getWindow().setStatusBarColor(getResources().getColor(R.color.green));
                    break;
                default:
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.dark_pink));
                    getWindow().setStatusBarColor(getResources().getColor(R.color.dark_pink));


            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private void sendTomain(){
        Intent mainintent =  new Intent(IntroActivity.this, MainActivity.class);
        startActivity(mainintent);
    }

    private void checkFirstRun() {
        final String PREFS_NAME = "PREFERENCE";
        final String PREF_VERSION_CODE_KEY = "1";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {
      sendTomain();
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {
            setupuser();
            // TODO This is a new install (or the user cleared the shared preferences)

        } else if (currentVersionCode > savedVersionCode) {
            
            // TODO This is an upgrade
            if (usersettingsdata.get(0).getColourtheme().equals(" ")){
                setupuser();
            }else {
               sendTomain();
            }
            
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        checkFirstRun();

        }

    private void setupuser(){
        usersettings.setColourtheme("-10064414");
        usersettings.setWalkthrough(1);
        usersettings.setDisablenotification(0);
        usersettings.setHidecompletedtask(0);
        usersettings.setLongpress(0);
        usersettings.setNotificationtune(0);
        usersettings.setReminderlayout(0);
        usersettings.setShowDate(0);
        usersettings.setSleeptimenotify("none");
        boolean setupuserdata = dbhelper.Setupdata(usersettings);
        if (setupuserdata == true) {
            Log.d(TAG, "setupuser: yup noice to carry on");
        }else {
            Log.d(TAG, "try again later");
        }

    }
    }


