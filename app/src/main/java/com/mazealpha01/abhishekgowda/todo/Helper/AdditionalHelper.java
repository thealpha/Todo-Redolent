package com.mazealpha01.abhishekgowda.todo.Helper;

import android.media.MediaPlayer;
import android.util.Log;

import com.mazealpha01.abhishekgowda.todo.R;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

public class AdditionalHelper {
    private static final String TAG = "AdditionalHelper";
    public String month;
    public String tune;
    public int rawtune;
    public int colour;


    public String CnvrtMnT(CalendarView calendarView)
    {
        switch (calendarView.getCurMonth()) {
            case 1 : month = "Jan";
                break;
            case 2 : month = "Feb";
                break;
            case 3 : month = "Mar";
                break;
            case 4 : month = "Apr";
                break;
            case 5 : month = "May";
                break;
            case 6 : month = "Jun";
                break;
            case 7 : month = "Jul";
                break;
            case 8 : month = "Aug";
                break;
            case 9 : month = "Sep";
                break;
            case 10 : month = "Oct";
                break;
            case 11 : month = "Nov";
                break;
            case 12 : month = "Dec";
                break;
        }
        return month;
    }

    public String onCnvrtMnT(Calendar calendar) {

        switch (calendar.getMonth()) {
            case 1 : month = "Jan";
                break;
            case 2 : month = "Feb";
                break;
            case 3 : month = "Mar";
                break;
            case 4 : month = "Apr";
                break;
            case 5 : month = "May";
                break;
            case 6 : month = "Jun";
                break;
            case 7 : month = "Jul";
                break;
            case 8 : month = "Aug";
                break;
            case 9 : month = "Sep";
                break;
            case 10 : month = "Oct";
                break;
            case 11 : month = "Nov";
                break;
            case 12 : month = "Dec";
                break;
        }
        return month;

    }

    public String getCalendarText(Calendar calendar) {
        return String.format(" " +
                calendar.getMonth() + "Month " + calendar.getDay() + "day" );
    }

    public String gettune(int index){
        Log.d(TAG, "gettune: index"+index);
        switch (index){
            case 0:
                tune = "none";
                break;
            case 1:
                tune = "peal";
                break;
            case 2:
                tune = "crystal";
                break;
            case 3:
                tune = "circles";
                break;
            case 4:
                tune = "g3";
                break;
            case 5:
                tune = "greetings";
                break;
            case 6:
                tune = "mail notification";
                break;
            case 7:
                tune = "Carillon";
                break;
            case 8:
                tune = "secret";
                break;
            case 9:
                tune = "Angry birds";
                break;
            case 10:
                tune = "Wall-E";
                break;
            case 11:
                tune = "spirit";
                break;
            case 12:
                tune = "google event";
                break;
            case 13:
                tune = "dropped spinner";
                break;
            case 14:
                tune ="reactive";
                break;
            case 15:
                tune = "mario";
                break;
            case 16:
                tune = "chime";
                break;
            case 17:
                tune  = "pew pew pew";
                break;
            case 18:
                tune = "sneeze";
                break;
            case 19:
                tune = "tuturu";
                break;
            case 20:
                tune = "pikamu";
                break;
            case 21:
                tune = "normal";
                break;

        }

        return  tune;
    }



    public int getRawtune(int index){
        Log.d(TAG, "gettune: index"+index);
        switch (index){
            case 0:
                tune = "none";
                break;
            case 1:
                tune = "peal";
                rawtune = R.raw.apple_pay;
                break;
            case 2:
                tune = "crystal";
                rawtune = R.raw.crystal;
                break;
            case 3:
                tune = "circles";
                rawtune = R.raw.iphone_circles;
                break;
            case 4:
                tune = "g3";
                rawtune = R.raw.ig_g3_org;
                break;
            case 5:
                tune = "greetings";
                rawtune = R.raw.greetings;
                break;
            case 6:
                tune = "mail notification";
                rawtune = R.raw.mail_notification;
                break;
            case 7:
                tune = "Carillon";
                rawtune = R.raw.one_plus_tune;
                break;
            case 8:
                tune = "secret";
                rawtune = R.raw.secret;
                break;
            case 9:
                tune = "Angry birds";
                rawtune = R.raw.angrybirds;
                break;
            case 10:
                tune = "Wall-E";
                rawtune = R.raw.are_you_kidding;
                break;
            case 11:
                tune = "spirit";
                rawtune = R.raw.spirit;
                break;
            case 12:
                tune = "google event";
                rawtune = R.raw.google_event;
                break;
            case 13:
                tune = "dropped spinner";
                rawtune = R.raw.dropped_spinner;
                break;
            case 14:
                tune ="reactive";
                rawtune = R.raw.reactive;
                break;
            case 15:
                tune = "mario";
                rawtune = R.raw.mario_power;
                break;
            case 16:
                tune = "chime";
                rawtune = R.raw.iphone_whatsapp;
                break;
            case 17:
                tune  = "pew pew pew";
                rawtune = R.raw.pew_pew_pew;
                break;
            case 18:
                tune = "sneeze";
                rawtune = R.raw.sneeze;
                break;
            case 19:
                tune = "tuturu";
                rawtune = R.raw.tuturu;
                break;
            case 20:
                tune = "pikamu";
                rawtune = R.raw.pikachuuuuuuu;
                break;
            case 21:
                tune = "normal";
                rawtune = R.raw.message_tone;
                break;

        }

        return  rawtune;
    }

    public int getColour(int clr){
        switch (clr){
            case -16737057:
               /* clr = "sky blue";*/
                colour = R.color.sky_blue;
                break;
            case -14129173:
            /*    themename = "dark blue";*/
                colour = R.color.dark_blue;
                break;
            case -3317248:
/*
                themename = "brown";
*/
                colour = R.color.orange;
                break;
            case -16743277:
             /*   themename = "green";*/
                colour = R.color.green;
            case -9579063:
           /*     themename = "light green";*/
                colour = R.color.light_green;
                break;
            case -1791216:
                /*themename = "yellow";*/
                colour = R.color.yellow;
                break;
            case -2003306:
                /*themename = "pink";*/
                colour = R.color.pink;
                break;
            case -2811066:
           /*     themename = "red";*/
                colour = R.color.red;
                break;
            case -8906554:
           /*     themename = "purple";*/
                colour = R.color.purple;
                break;
            case -10064414:
           /*     themename = "light purple";*/
                colour = R.color.light_purple;
                break;
            case -153460:
           /*     themename = "light orange";*/
                colour = R.color.light_orange;
                break;
        }
        return colour;
    }


}
