package com.mazealpha01.abhishekgowda.todo;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mazealpha01.abhishekgowda.todo.Adapter.ItemAdapter;
import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.Model.Item;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewActivity  extends AppCompatActivity {
    private static final String TAG = "ViewActivity";
    private Dbhelper dbhelper;
    private EditText iteam;
    private ImageView checked,cancle,additem;
    private RecyclerView recyclerView;
    private TextView iteamDef,Notedef,notetxt,taskname_txt,time_txt;
    private Item itemmodel;
    private Integer id;
    private String nameoftask,itemstr;
    private String tag,name,note,time,date;
    private ItemAdapter itemAdapter;
    private ArrayList<Item> listitem;
    int mSomeMemberVariable = 123;
    private RelativeLayout relativeLayout;
    private Context mcontext;
    private RelativeLayout additemtxtlay;
    private ArrayList<Usersettings> usersettingsdata;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);
        Intent recivedIntent =  getIntent();
        dbhelper = new Dbhelper(this);
        itemmodel =  new Item();
        mcontext = ViewActivity.this;
        usersettingsdata = dbhelper.getusersettings();
         id = recivedIntent.getIntExtra("id",-1);
         name = recivedIntent.getStringExtra("name");
        tag = recivedIntent.getStringExtra("tag");
        note = recivedIntent.getStringExtra("note");
        time = recivedIntent.getStringExtra("time");
        date = recivedIntent.getStringExtra("date");

        iteam = (EditText)findViewById(R.id.iteam_gettext);
        checked = (ImageView) findViewById(R.id.add_iteam_done);
        cancle = (ImageView) findViewById(R.id.cancle_review);
        recyclerView = (RecyclerView) findViewById(R.id.listviewiteam);
        iteamDef = (TextView) findViewById(R.id.iteams_deafault);
        Notedef = (TextView) findViewById(R.id.note_txt_default);
        notetxt = (TextView) findViewById(R.id.note_text);
        additemtxtlay = (RelativeLayout)findViewById(R.id.additeams);
        relativeLayout = (RelativeLayout)findViewById(R.id.relativelay);
        taskname_txt = (TextView)findViewById(R.id.task_text);
        time_txt = (TextView)findViewById(R.id.timetext);
        additem = (ImageView)findViewById(R.id.addtask);
        relativeLayout.setBackgroundColor(Integer.valueOf(tag));
        checked.setColorFilter(Integer.valueOf(tag), PorterDuff.Mode.SRC_IN);
        hideSoftKeyboard();
        extrainit();
        inititem();
      //  getscreenwidth();
        btnanim();
        additeam();

    }


    private void btnanim(){
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //set position TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta
                    final Animation btn1anim = new TranslateAnimation(0,120,0,0);
                    btn1anim.setDuration(700);
                    btn1anim.setFillAfter(true);
                    btn1anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            additem.setClickable(false);

                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            final Animation relanim = new TranslateAnimation(0,0,150,0);
                            relanim.setDuration(600);
                            relanim.setFillAfter(true);
                            relanim.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    additemtxtlay.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    additem.setVisibility(View.GONE);
                                    checked.setClickable(true);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            additemtxtlay.startAnimation(relanim);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    additem.startAnimation(btn1anim);


                    //   btnanim();


            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    private void extrainit(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Integer.valueOf(tag));
        }

        Drawable mDrawable = ContextCompat.getDrawable(mcontext, R.drawable.add_item_icn);
     //   DrawableCompat.set(mDrawable, ContextCompat.getColor(mcontext, Integer.parseInt(tag)));

        try{
        }catch (NullPointerException e){
            mDrawable.setColorFilter(new
                    PorterDuffColorFilter(getResources().getColor(R.color.yellow),PorterDuff.Mode.SRC_IN));
        }

    }

    private void additeam(){
        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   additem.setClickable(false);
                final Animation animation = new TranslateAnimation(0,0,0,150);
                animation.setDuration(600);
                animation.setFillAfter(true);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        checked.setClickable(false);
                        additemtxtlay.setClickable(false);

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        additemtxtlay.setVisibility(View.GONE);
                        additem.setClickable(true);
                        additem.setVisibility(View.VISIBLE);
                        final Animation btn2anim = new TranslateAnimation(120,0,0,0);
                        btn2anim.setDuration(800);
                        btn2anim.setFillAfter(true);
                        additem.startAnimation(btn2anim);


                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                additemtxtlay.startAnimation(animation);

                itemstr = iteam.getText().toString();
                if (!itemstr.equals("")){
                    itemmodel.setItem(itemstr);
                    itemmodel.setTaskID(id);
                    itemmodel.setComplete("0");
                    itemmodel.setTag(tag);
                    boolean  insertdata = dbhelper.addnewitem(itemmodel,id);
                    if (insertdata == true ){
                        Toast.makeText(ViewActivity.this, "Successfully added task :)", Toast.LENGTH_SHORT).show();
                        oniteminserted();

                    }else {
                        Toast.makeText(ViewActivity.this, "Ops something went wrong :(", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });

        if (usersettingsdata.get(0).getLongpress() == 1 ){

            checked.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemstr = iteam.getText().toString();
                    if (!itemstr.equals("")){
                        itemmodel.setItem(itemstr);
                        itemmodel.setTaskID(id);
                        itemmodel.setComplete("0");
                        itemmodel.setTag(tag);
                        boolean  insertdata = dbhelper.addnewitem(itemmodel,id);
                        if (insertdata == true ){
                            Toast.makeText(ViewActivity.this, "Successfully added task :)", Toast.LENGTH_SHORT).show();
                            oniteminserted();
                        }else {
                            Toast.makeText(ViewActivity.this, "Ops something went wrong :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;

                }
            });
        }


    }

    private void inititem(){
        Log.d(TAG, "inititem: note"+note);

        if (note != null){
            notetxt.setText(note);
        }else {
            notetxt.setText("No note");
        }

        if (name != null){
            taskname_txt.setText(name);
        }else {
            taskname_txt.setText("error displaying task name");
        }
        if (time != null || date !=null){
            time_txt.setText(date+" - "+time);
        }else {
            time_txt.setText("You haven't added time to this task");
        }



        listitem = new ArrayList<>();
        itemAdapter = new ItemAdapter(listitem, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(null);
        new MyTask(ViewActivity.this).execute();
    }

    private void oniteminserted(){
/*        listitem.add(itemmodel);
        itemAdapter.notifyItemInserted(listitem.size() -1 );*/
        hideSoftKeyboard();
        iteam.getText().clear();
        new MyTask(ViewActivity.this).execute();
    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private static class MyTask extends AsyncTask<Void, Void, String> {
        private WeakReference<ViewActivity> activityReference;
        // only retain a weak reference to the activity
        MyTask(ViewActivity context) {
            activityReference = new WeakReference<>(context);
        }
        @Override
        protected String doInBackground(Void... params) {
            // do some long running task...
            ViewActivity activity = activityReference.get();
            activity.listitem.clear();
            activity.listitem.addAll(activity.dbhelper.getAllItem(activity.id));
            return "task finished";
        }

        @Override
        protected void onPostExecute(String result) {
            // get a reference to the activity if it is still there
            ViewActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            // modify the activity's UI
            // access Activity member variables
            activity.mSomeMemberVariable = 321;
            activity.itemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideSoftKeyboard();
        overridePendingTransition(R.anim.stay,R.anim.slide_in_down);
    }

    @Override
    public void finish() {
        super.finish();

        hideSoftKeyboard();
        overridePendingTransition(R.anim.stay,R.anim.slide_in_down);

    }

    @Override
    protected void onStart() {
        super.onStart();
        hideSoftKeyboard();
    }

    @Override
    protected void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.stay,R.anim.slide_in_down);
    }

}
