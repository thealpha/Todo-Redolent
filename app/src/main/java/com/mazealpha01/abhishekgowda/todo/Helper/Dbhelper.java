package com.mazealpha01.abhishekgowda.todo.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mazealpha01.abhishekgowda.todo.Model.CalenderMark;
import com.mazealpha01.abhishekgowda.todo.Model.Item;
import com.mazealpha01.abhishekgowda.todo.Model.Task;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;

import org.joda.time.YearMonth;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {
    private static final String TAG = "Dbhelper";


    private static final String DB_NAME = "DB_TASK";

    //Table task
    private static final String TABLE_NAME = "Task";
    private static final String TASK_NAME ="Todo";
    private static final String TASK_NOTE = "Note";
    private static final String TASK_DATE = "Date";
    private static final String TASK_TIME = "Time";
    private static final String TASK_SOUND = "Sound";
    private static final String TASK_TAG = "Tag";
    private static final String TASK_ID = "ID";
    private static final String TASK_COMPLETE = "Complete";
    private static final String CALENDAT_MARK_ID = "Calendar_Mark_ID";
    private static final String ALARM_ID = "Alarm_ID";

    //Table item
    private static final String ITEM_NAME = "Item";
    private static final String ITEM_COMPLETE = "Complete";
    private static final String ITEM_TAG = "TAG";
    private static final String ITEM = "Item";
    private final String TABLE_PARENT_TASK_ID = "parent_id";
    private static final String ITEM_ID = "ID";

    //Usersettings
    private static final String USER_SETTINGS = "Usersettings";
    private static final String COLOUR_THEME = "Theme";
    private static final String SHOW_DATE = "Show_date";
    private static final String LONG_PRESS = "Long_Press_item";
    private static final String HIDE_COMPLETED_TASK = "Hide_completed_task";
    private static  final String REMINDER_LAYOUT = "Show_adv_notification";
    private static  final String DISABLE_NOTIFICATION = "Show_notification";
    private final String SLEEP_TIME = "Sleep_time";
    private static  String TUNE = "Tune";
    private static final String WALKTHROUGH = "Walkthrough";
    private static final String USER_ID = "User_ID";

    //CalenderMark
    private static final String CALENDAR_MARK = "Calendar_mark";
    private static final String DATE = "Date";
    private static final String MONTH = "Month";
    private static final String YEAR = "Year";
    private static final String PARENT_ID = "Parent_id";
    private static final String CALENDAR_ID = "ID";







    private static final int DB_VER = 1;




    public Dbhelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

      /*  String createtable = "CREATE TABLE "+ TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOCRIMENT, " +
                "TASK TEXT,NOTE TEXT,DATE TEXT,TIME TEXT,SOUND TEXT,TAG TEXT)" ;*/



        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + TASK_ID + " INTEGER PRIMARY KEY,"
                + TASK_NAME + " TEXT,"
                + TASK_NOTE + " TEXT,"
                + TASK_DATE + " TEXT,"
                + TASK_TIME + " TEXT,"
                + TASK_SOUND + " TEXT,"
                + TASK_TAG + " TEXT,"
                + TASK_COMPLETE + " TEXT,"
                + CALENDAT_MARK_ID + " INTEGER,"
                + ALARM_ID + " INTEGER"
                +")";
        String CREATE_ITEM_TABLE = "CREATE TABLE " + ITEM + "("
                + ITEM_ID + " INTEGER PRIMARY KEY,"
                + TABLE_PARENT_TASK_ID + " INTEGER,"
                + ITEM_NAME + " TEXT,"
                + ITEM_TAG + " TEXT,"
                + ITEM_COMPLETE + " TEXT"
                +")";
        String CREATE_SETTINGS_TABLE = "CREATE TABLE " + USER_SETTINGS + "("
                + USER_ID + " INTEGER,"
                + WALKTHROUGH + " INTEGER,"
                + COLOUR_THEME + " TEXT,"
                + TUNE + " INTEGER,"
                + SHOW_DATE + " INTEGER,"
                + LONG_PRESS + " INTEGER,"
                + HIDE_COMPLETED_TASK + " INTEGER,"
                + REMINDER_LAYOUT + " INTEGER,"
                + DISABLE_NOTIFICATION + " INTEGER,"
                + SLEEP_TIME + " TEXT"
                +")";

        String CREATE_CALENDAR_MARK = "CREATE TABLE " + CALENDAR_MARK + "("
                + CALENDAR_ID + " INTEGER PRIMARY KEY,"
                + PARENT_ID + " INTEGER,"
                + YEAR + " TEXT,"
                + MONTH + " TEXT,"
                + DATE + " TEXT"
                +")";



       // String createitem = "CREATE TABLE "+ TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOCRIMENT, " +
           //     "TASK TEXT,TAG TEXT)";

        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_ITEM_TABLE);
        db.execSQL(CREATE_SETTINGS_TABLE);
        db.execSQL(CREATE_CALENDAR_MARK);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ITEM);
        db.execSQL("DROP TABLE IF EXISTS "+USER_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS "+CALENDAR_MARK);

        onCreate(db);
    }

    public boolean Setupdata(Usersettings usersettings){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, 1);
        values.put(WALKTHROUGH, usersettings.getWalkthrough());
        values.put(COLOUR_THEME,usersettings.getColourtheme());
        values.put(SHOW_DATE, usersettings.getShowDate());
        values.put(LONG_PRESS, usersettings.getLongpress());
        values.put(HIDE_COMPLETED_TASK, usersettings.getHidecompletedtask());
        values.put(TUNE, usersettings.getNotificationtune());
        values.put(REMINDER_LAYOUT, usersettings.getReminderlayout());
        values.put(DISABLE_NOTIFICATION, usersettings.getDisablenotification());
        values.put(SLEEP_TIME, usersettings.getSleeptimenotify());

        long result = db.insert(USER_SETTINGS, null, values);
        if (result == -1){
            return false;
        }{
            return true;
        }

    }

    public boolean addmark(CalenderMark calenderMark) {
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PARENT_ID,calenderMark.getTaskid());
        values.put(YEAR,calenderMark.getYear());
        values.put(MONTH,calenderMark.getMonth());
        values.put(DATE,calenderMark.getDate());
        long result = db.insert(CALENDAR_MARK, null ,values);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addnewtodo(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_NAME, task.getTasktodo());
        values.put(TASK_NOTE, task.getNote());
        values.put(TASK_DATE, task.getDate());
         values.put(TASK_TIME, task.getTime());
         values.put(TASK_SOUND, task.getSound());
         values.put(TASK_TAG, task.getTag());
        values.put(TASK_COMPLETE, task.getComplete());
        values.put(CALENDAT_MARK_ID, task.getCalendarMark());
        values.put(ALARM_ID, task.getAlarmID());

        Log.d(TAG, "addnewtodo: task"+task.getTasktodo());

        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1){
            return false;
        }{
            return true;
        }

    }


    public boolean addnewitem(Item item, Integer Parentid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, item.getItem());
        values.put(ITEM_TAG, item.getTag());
        values.put(ITEM_COMPLETE, item.getComplete());
        values.put(TABLE_PARENT_TASK_ID, Parentid);
        long result = db.insert(ITEM, null, values);

        if (result == -1){
            return false;
        }{
            return true;

        }
    }



    public List<Task> getAllTasks() {
        List<Task> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Task item = new Task();
            item.setID(getIntByColumName(cursor, ITEM_ID));
            item.setTasktodo(getStringByColumName(cursor, TASK_NAME));
            result.add(item);
        }
       // cursor.close();
        //db.close();
        return result;
    }

    public Cursor getdata() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query =  "SELECT * FROM "+ TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
       // data.close();          // Dont forget to close your cursor
      //  db.close();              //AND your Database!
        return data;


    }
    public Cursor getiteamdata(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectString = "SELECT * FROM " + ITEM + " WHERE " + TABLE_PARENT_TASK_ID + " =?";
        Cursor data = db.rawQuery(selectString,new String[] {String.valueOf(id)});
        Log.d(TAG, "getiteamdata: da"+data);
     //   data.close();          // Dont forget to close your cursor
      //  db.close();              //AND your Database!
        return data;

    }


    public int getIntByColumName(Cursor cursor, String tableColumn) {
        return cursor.getInt(cursor.getColumnIndex(tableColumn));
    }


    public double getDoubleByColumName(Cursor cursor, String tableColumn) {
        return cursor.getDouble(cursor.getColumnIndex(tableColumn));
    }


    public String getStringByColumName(Cursor cursor, String tableColumn) {
        return cursor.getString(cursor.getColumnIndex(tableColumn));
    }

    public Cursor getId(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + TASK_ID + " FROM " +
                TABLE_NAME + " WHERE "+ TASK_ID + " = '"+ id + "'";
        Cursor data =  db.rawQuery(query,null);
        return data;

    }






    public List<Item> getAllItem(Integer id) {
        // array of columns to fetch
        String[] columns = {
               ITEM_ID,
                TABLE_PARENT_TASK_ID,
                ITEM_NAME,
              ITEM_TAG,
              ITEM_COMPLETE

        };
        // sorting orders
        String sortOrder =
                ITEM_NAME + " ASC";
        List<Item> itemList = new ArrayList<Item>();
      //  SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectString = "SELECT * FROM " + ITEM + " WHERE " + TABLE_PARENT_TASK_ID + " =?";
        Cursor cursor = db.rawQuery(selectString,new String[] {String.valueOf(id)});
       /* Cursor cursor = db.query(ITEM, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order
        // Traversing through all rows and adding to list
        */
       if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ITEM_ID))));
                item.setTaskID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TABLE_PARENT_TASK_ID))));
                item.setItem(cursor.getString(cursor.getColumnIndex(ITEM_NAME)));
                item.setTag(cursor.getString(cursor.getColumnIndex(ITEM_TAG)));
                item.setComplete(cursor.getString(cursor.getColumnIndex(ITEM_COMPLETE)));
                // Adding user record to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return itemList;
    }



    public ArrayList<Task> getAllTask() {
        // array of columns to fetch
        String[] columns = {
                TASK_ID,
                TASK_NAME,
                TASK_NOTE,
                TASK_DATE,
                TASK_TIME,
                TASK_SOUND,
                TASK_TAG,
                TASK_COMPLETE,
                CALENDAT_MARK_ID,
                ALARM_ID
        };
        // sorting orders
        String sortOrder =
                TASK_NAME + " ASC";
        ArrayList<Task> taskList = new ArrayList<Task>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TASK_ID))));
                task.setTasktodo(cursor.getString(cursor.getColumnIndex(TASK_NAME)));
                task.setDate(cursor.getString(cursor.getColumnIndex(TASK_DATE)));
                task.setTime(cursor.getString(cursor.getColumnIndex(TASK_TIME)));
                task.setSound(cursor.getString(cursor.getColumnIndex(TASK_SOUND)));
                task.setTag(cursor.getString(cursor.getColumnIndex(TASK_TAG)));
                task.setComplete(cursor.getString(cursor.getColumnIndex(TASK_COMPLETE)));
                task.setNote(cursor.getString(cursor.getColumnIndex(TASK_NOTE)));
                task.setCalendarMark(cursor.getInt(cursor.getColumnIndex(CALENDAT_MARK_ID)));
                task.setAlarmID(cursor.getInt(cursor.getColumnIndex(ALARM_ID)));
                // Adding user record to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return taskList;

    }

    public List<Task> getSpecificTask(String date) {
        // array of columns to fetch
        String[] columns = {
                TASK_ID,
                TASK_NAME,
                TASK_NOTE,
                TASK_DATE,
                TASK_TIME,
                TASK_SOUND,
                TASK_TAG,
                TASK_COMPLETE,
                CALENDAT_MARK_ID,
                ALARM_ID
        };
        // sorting orders
        String sortOrder =
                TASK_NAME + " ASC";
        List<Task> taskList = new ArrayList<Task>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + TASK_DATE + " =?" ;
        Cursor cursor = db.rawQuery(selectString,new String[] {String.valueOf(date)});


        Log.d(TAG, "getSpecificTask: tt " + date);
        if (TASK_DATE == "Jan 18, 2019"){
            Log.d(TAG, "getSpecificTask: bool " + true);
            Log.d(TAG, "getSpecificTask: date  " + TASK_DATE);
        }else {
            Log.d(TAG, "getSpecificTask: bool " + false);
        }


        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TASK_ID))));
                task.setTasktodo(cursor.getString(cursor.getColumnIndex(TASK_NAME)));
                task.setDate(cursor.getString(cursor.getColumnIndex(TASK_DATE)));
                task.setTime(cursor.getString(cursor.getColumnIndex(TASK_TIME)));
                task.setSound(cursor.getString(cursor.getColumnIndex(TASK_SOUND)));
                task.setTag(cursor.getString(cursor.getColumnIndex(TASK_TAG)));
                task.setComplete(cursor.getString(cursor.getColumnIndex(TASK_COMPLETE)));
                task.setNote(cursor.getString(cursor.getColumnIndex(TASK_NOTE)));
                task.setCalendarMark(cursor.getInt(cursor.getColumnIndex(CALENDAT_MARK_ID)));
                task.setAlarmID(cursor.getInt(cursor.getColumnIndex(ALARM_ID)));
                // Adding user record to list
                taskList.add(task);
                Log.d(TAG, "getSpecificTask: date "+  task.getDate());
            } while (cursor.moveToNext());
        }
     /*   cursor.close();
        db.close();*/
        // return user list
        return taskList;

    }
    public ArrayList<Task> getTodaysTask(String date) {
        // array of columns to fetch
        String[] columns = {
                TASK_ID,
                TASK_NAME,
                TASK_NOTE,
                TASK_DATE,
                TASK_TIME,
                TASK_SOUND,
                TASK_TAG,
                TASK_COMPLETE,
                CALENDAT_MARK_ID,
                ALARM_ID
        };
        // sorting orders
        String sortOrder =
                TASK_NAME + " ASC";
        ArrayList<Task> taskList = new ArrayList<Task>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + TASK_DATE + " =?"  ;
        Cursor cursor = db.rawQuery(selectString,new String[] {String.valueOf(date)});
        Log.d(TAG, "getSpecificTask: tt " + date);
        if (TASK_DATE == "Jan 18, 2019"){
            Log.d(TAG, "getSpecificTask: bool " + true);
            Log.d(TAG, "getSpecificTask: date  " + TASK_DATE);
        }else {
            Log.d(TAG, "getSpecificTask: bool " + false);
        }
      /*  Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order
        // Traversing through all rows and adding to list
        */

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TASK_ID))));
                task.setTasktodo(cursor.getString(cursor.getColumnIndex(TASK_NAME)));
                task.setDate(cursor.getString(cursor.getColumnIndex(TASK_DATE)));
                task.setTime(cursor.getString(cursor.getColumnIndex(TASK_TIME)));
                task.setSound(cursor.getString(cursor.getColumnIndex(TASK_SOUND)));
                task.setTag(cursor.getString(cursor.getColumnIndex(TASK_TAG)));
                task.setComplete(cursor.getString(cursor.getColumnIndex(TASK_COMPLETE)));
                task.setNote(cursor.getString(cursor.getColumnIndex(TASK_NOTE)));
                task.setCalendarMark(cursor.getInt(cursor.getColumnIndex(CALENDAT_MARK_ID)));
                task.setAlarmID(cursor.getInt(cursor.getColumnIndex(ALARM_ID)));
                // Adding user record to list
                taskList.add(task);
                Log.d(TAG, "getSpecificTask: date "+  task.getDate());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return taskList;

    }

    public List<Task> getCompletedTask() {
        // array of columns to fetch
        String[] columns = {
                TASK_ID,
                TASK_NAME,
                TASK_NOTE,
                TASK_DATE,
                TASK_TIME,
                TASK_SOUND,
                TASK_TAG,
                TASK_COMPLETE,
                CALENDAT_MARK_ID,
                ALARM_ID
        };
        // sorting orders
        String sortOrder =
                TASK_NAME + " ASC";
        List<Task> taskList = new ArrayList<Task>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + TASK_COMPLETE + " =?"  ;
        Cursor cursor = db.rawQuery(selectString,new String[] {String.valueOf(1)});

      /*  Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order
        // Traversing through all rows and adding to list
        */

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TASK_ID))));
                task.setTasktodo(cursor.getString(cursor.getColumnIndex(TASK_NAME)));
                task.setDate(cursor.getString(cursor.getColumnIndex(TASK_DATE)));
                task.setTime(cursor.getString(cursor.getColumnIndex(TASK_TIME)));
                task.setSound(cursor.getString(cursor.getColumnIndex(TASK_SOUND)));
                task.setTag(cursor.getString(cursor.getColumnIndex(TASK_TAG)));
                task.setComplete(cursor.getString(cursor.getColumnIndex(TASK_COMPLETE)));
                task.setNote(cursor.getString(cursor.getColumnIndex(TASK_NOTE)));
                task.setCalendarMark(cursor.getInt(cursor.getColumnIndex(CALENDAT_MARK_ID)));
                task.setAlarmID(cursor.getInt(cursor.getColumnIndex(ALARM_ID)));
                // Adding user record to list
                taskList.add(task);
                Log.d(TAG, "getSpecificTask: date "+  task.getDate());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return taskList;

    }



    public List<Task> getTommorowsTask(String date) {
        // array of columns to fetch
        String[] columns = {
                TASK_ID,
                TASK_NAME,
                TASK_NOTE,
                TASK_DATE,
                TASK_TIME,
                TASK_SOUND,
                TASK_TAG,
                TASK_COMPLETE,
                CALENDAT_MARK_ID,
                ALARM_ID
        };
        // sorting orders
        String sortOrder =
                TASK_NAME + " ASC";
        List<Task> taskList = new ArrayList<Task>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + TASK_DATE + " =?" ;
        Cursor cursor = db.rawQuery(selectString,new String[] {String.valueOf(date)});
        Log.d(TAG, "getSpecificTask: tt " + date);
        if (TASK_DATE == "Jan 18, 2019"){
            Log.d(TAG, "getSpecificTask: bool " + true);
            Log.d(TAG, "getSpecificTask: date  " + TASK_DATE);
        }else {
            Log.d(TAG, "getSpecificTask: bool " + false);
        }
      /*  Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order
        // Traversing through all rows and adding to list
        */

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TASK_ID))));
                task.setTasktodo(cursor.getString(cursor.getColumnIndex(TASK_NAME)));
                task.setDate(cursor.getString(cursor.getColumnIndex(TASK_DATE)));
                task.setTime(cursor.getString(cursor.getColumnIndex(TASK_TIME)));
                task.setSound(cursor.getString(cursor.getColumnIndex(TASK_SOUND)));
                task.setTag(cursor.getString(cursor.getColumnIndex(TASK_TAG)));
                task.setComplete(cursor.getString(cursor.getColumnIndex(TASK_COMPLETE)));
                task.setNote(cursor.getString(cursor.getColumnIndex(TASK_NOTE)));
                task.setCalendarMark(cursor.getInt(cursor.getColumnIndex(CALENDAT_MARK_ID)));
                task.setAlarmID(cursor.getInt(cursor.getColumnIndex(ALARM_ID)));
                // Adding user record to list
                taskList.add(task);
                Log.d(TAG, "getSpecificTask: date "+  task.getDate());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return taskList;

    }

    public Cursor getsettings(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query =  " SELECT * FROM " + USER_SETTINGS;
        Cursor data =  db.rawQuery(query,null);
        return data;

    }



    public ArrayList<Usersettings> getusersettings() {
        final String TABLE_NAME = "name of table";
        String selectQuery = "SELECT  * FROM " + USER_SETTINGS;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        ArrayList<Usersettings> data = new ArrayList<Usersettings>();
        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                Usersettings usersettings = new Usersettings();
                usersettings.setShowDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SHOW_DATE))));
                usersettings.setReminderlayout(cursor.getInt(cursor.getColumnIndex(REMINDER_LAYOUT)));
                usersettings.setLongpress(cursor.getInt(cursor.getColumnIndex(LONG_PRESS)));
                usersettings.setHidecompletedtask(cursor.getInt(cursor.getColumnIndex(HIDE_COMPLETED_TASK)));
                usersettings.setDisablenotification(cursor.getInt(cursor.getColumnIndex(DISABLE_NOTIFICATION)));
                usersettings.setColourtheme(cursor.getString(cursor.getColumnIndex(COLOUR_THEME)));
                usersettings.setWalkthrough(cursor.getInt(cursor.getColumnIndex(WALKTHROUGH)));
                usersettings.setNotificationtune(cursor.getInt(cursor.getColumnIndex(TUNE)));
                usersettings.setSleeptimenotify(cursor.getString(cursor.getColumnIndex(SLEEP_TIME)));
                data.add(usersettings);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }


    public void updatedatabase(Integer id , String comp){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + TABLE_NAME + " SET " +
                TASK_COMPLETE + " = " + comp  + " WHERE " + TASK_ID + " = '" + id + "'";
        db.execSQL(query);


    }

    public void deletecoloumn(Integer id ){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " DELETE FROM " + TABLE_NAME + " WHERE " +
                TASK_ID + " = '" + id  +  "'";
        db.execSQL(query);
    }

    public void updateitem(Integer id , String comp){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + ITEM + " SET " +
                ITEM_COMPLETE + " = " + comp  + " WHERE " + ITEM_ID + " = '" + id + "'";
        db.execSQL(query);
    }

    public void deleteitem(Integer id ){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " DELETE FROM " + ITEM + " WHERE " +
                ITEM_ID + " = '" + id  +  "'";
        db.execSQL(query);
    }

    public void deleteallitem(Integer parent_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " DELETE FROM " + ITEM + " WHERE " +
                TABLE_PARENT_TASK_ID + " = '" + parent_id  +  "'";
        db.execSQL(query);
    }

    public void updatetheme(String theme){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + USER_SETTINGS + " SET " +
                COLOUR_THEME + " = " + theme;
        db.execSQL(query);
    }
    public void updateshowdate(Integer bool){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + USER_SETTINGS + " SET " +
                SHOW_DATE + " = " + bool;
        db.execSQL(query);
    }
    public void updatelngprs(Integer bool){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + USER_SETTINGS + " SET " +
                LONG_PRESS + " = " + bool;
        db.execSQL(query);
    }
    public void updatedisablenotifi(Integer bool){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + USER_SETTINGS + " SET " +
                DISABLE_NOTIFICATION + " = " + bool;
        db.execSQL(query);
    }
    public void updatecompleted(Integer bool){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + USER_SETTINGS + " SET " +
                HIDE_COMPLETED_TASK + " = " + bool;
        db.execSQL(query);
    }
    public void updatereminderlay(Integer bool){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + USER_SETTINGS + " SET " +
                REMINDER_LAYOUT + " = " + bool;
        db.execSQL(query);
    }

    public void updatesleeptime(String time){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + USER_SETTINGS + " SET " +
                SLEEP_TIME + " = " + time;
        db.execSQL(query);
    }
    public void updatenotifytune(Integer tune){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + USER_SETTINGS + " SET " +
                TUNE + " = " + tune;
        db.execSQL(query);
    }

    public boolean updatetime(Usersettings usersettings ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SLEEP_TIME,usersettings.getSleeptimenotify());
        String selectString = "SELECT * FROM " + USER_SETTINGS + " WHERE " + USER_ID + " =?";
        long result = db.update(USER_SETTINGS, values, USER_ID + "=?", new String[] {String.valueOf(1)});
        if (result == -1){
            return false;
        }{
            return true;
        }

    }


    public List<CalenderMark> calendarMark(String year,String month) {
        // array of columns to fetch
        String[] columns = {
                TASK_ID,
                TASK_NAME,
                TASK_NOTE,
                TASK_DATE,
                TASK_TIME,
                TASK_SOUND,
                TASK_TAG,
                TASK_COMPLETE,
                CALENDAT_MARK_ID,
                ALARM_ID
        };
        // sorting orders
        String sortOrder =
                TASK_NAME + " ASC";
        List<CalenderMark> calendarlist = new ArrayList<CalenderMark>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectString = "SELECT * FROM " + CALENDAR_MARK + " WHERE " + YEAR + " =?" + " AND " + MONTH + "=?" ;
        Cursor cursor = db.rawQuery(selectString,new String[] {String.valueOf(year) , String.valueOf(month)});


      /*  Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order
        // Traversing through all rows and adding to list
        */

        Log.d(TAG, "calendarMark: hmm"+cursor.moveToFirst());
        if (cursor.moveToFirst()) {
            do {
                CalenderMark calenderMark = new CalenderMark();
                calenderMark.setYear(cursor.getString(cursor.getColumnIndex(YEAR)));
                calenderMark.setMonth(cursor.getString(cursor.getColumnIndex(MONTH)));
                calenderMark.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                calenderMark.setTaskid(cursor.getInt(cursor.getColumnIndex(PARENT_ID)));
                // Adding user record to list
                calendarlist.add(calenderMark);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return calendarlist ;

    }


    public void deleteMark(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " DELETE FROM " + CALENDAR_MARK + " WHERE " +
                PARENT_ID + " = '" + id  +  "'";
        db.execSQL(query);
    }

    public boolean check(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String checkquery = "SELECT * FROM " + TABLE_NAME + " WHERE " + TASK_ID + " =?" + " AND " + TASK_COMPLETE + "=?" ;
        Cursor cursor = db.rawQuery(checkquery,new String[] {String.valueOf(id) , String.valueOf("1")});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public boolean checkitem(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String checkquery = "SELECT * FROM " + ITEM + " WHERE " + ITEM_ID + " =?" + " AND " + ITEM_COMPLETE + "=?" ;
        Cursor cursor = db.rawQuery(checkquery,new String[] {String.valueOf(id) , String.valueOf("1")});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
}
