package com.mazealpha01.abhishekgowda.todo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.TranslateAnimation;

import com.mazealpha01.abhishekgowda.todo.Adapter.PreferenceAdapter;
import com.mazealpha01.abhishekgowda.todo.Extra.BottomNavigationHelper;
import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.Model.BasePreferenceData;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;
import com.mazealpha01.abhishekgowda.todo.Preference.AboutPreferenceData;
import com.mazealpha01.abhishekgowda.todo.Preference.Privacyinfo;
import com.mazealpha01.abhishekgowda.todo.Preference.LngPreferenceData;
import com.mazealpha01.abhishekgowda.todo.Preference.NotifytunePreferenceData;
import com.mazealpha01.abhishekgowda.todo.Preference.ShowdatePreferenceData;
import com.mazealpha01.abhishekgowda.todo.Preference.SleeptmePreferenceData;
import com.mazealpha01.abhishekgowda.todo.Preference.SupportPreferenceData;
import com.mazealpha01.abhishekgowda.todo.Preference.ThemePreferenceData;
import com.fastaccess.datetimepicker.TimePickerFragmentDialog;
import com.fastaccess.datetimepicker.callback.TimePickerCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mazealpha01.abhishekgowda.todo.Preference.donation;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SettingsActivity extends AppCompatActivity  implements TimePickerCallback {
    private BottomNavigationView bottomNavigationView;
    private Context context = SettingsActivity.this;
    private static final int ACTIVITY_NUM = 2;
    private RecyclerView preferenceRecyclerview;
    private Usersettings usersettings;
    private Context mcontext;
    private PreferenceAdapter preferenceAdapter;
    private TranslateAnimation anim = null;
    private Dbhelper dbhelper;
    private ArrayList<Usersettings> usersettingsdata;
    private static final String TAG = "SettingsActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mcontext = SettingsActivity.this;
        dbhelper = new Dbhelper(context);
        usersettings = new Usersettings();
        usersettingsdata =  new ArrayList<>();
        usersettingsdata = dbhelper.getusersettings();
        setheme(Integer.valueOf(usersettingsdata.get(0).getColourtheme()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        Log.d(TAG, "onCreate: usersettings"+usersettings.getNotificationtune());
        preferenceRecyclerview = findViewById(R.id.preference_recyclerview);

        preferenceAdapter = new PreferenceAdapter(new ArrayList<BasePreferenceData>(Arrays.asList( new ThemePreferenceData(mcontext),
                new ShowdatePreferenceData(usersettings,mcontext),
                new LngPreferenceData(usersettings,mcontext),
                new NotifytunePreferenceData(usersettings,mcontext),
                new SleeptmePreferenceData(usersettings,mcontext),
                new SupportPreferenceData(usersettings,mcontext),
                new Privacyinfo(usersettings,mcontext),
                new AboutPreferenceData(usersettings,mcontext),
                new donation(mcontext))));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        preferenceRecyclerview.setLayoutManager(layoutManager);
        preferenceRecyclerview.setNestedScrollingEnabled(false);
        preferenceRecyclerview.setItemAnimator(new DefaultItemAnimator());
        preferenceRecyclerview.setHasFixedSize(false);
        preferenceRecyclerview.setAdapter(preferenceAdapter);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        BottomNavigationHelper.enableNavigation(context,this,bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }


    @Override
    public void onTimeSet(long timeOnly, long dateWithTime) {
        usersettings.setSleeptimenotify(String.valueOf(timeOnly));
        dbhelper.updatetime(usersettings);
    }

    public void showtimedailog(){
        TimePickerFragmentDialog.newInstance().show(getSupportFragmentManager(), "TimePickerFragmentDialog");
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


    public void reload() {
        finish();
        startActivity(getIntent());
    }


}
