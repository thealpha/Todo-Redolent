package com.mazealpha01.abhishekgowda.todo.Extra;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.mazealpha01.abhishekgowda.todo.CalenderActivity;
import com.mazealpha01.abhishekgowda.todo.MainActivity;
import com.mazealpha01.abhishekgowda.todo.R;

import com.mazealpha01.abhishekgowda.todo.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;

public class BottomNavigationHelper {

    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationView view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_list:
                        Intent intent1 = new Intent(context, MainActivity.class);//ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.ic_cal:
                        Intent intent2 = new Intent(context, CalenderActivity.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.ic_settings:
                        Intent intent3 = new Intent(context, SettingsActivity.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent3);
                       callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                }


                return false;
            }
        });
    }
}

