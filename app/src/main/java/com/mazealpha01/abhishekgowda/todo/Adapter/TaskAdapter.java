package com.mazealpha01.abhishekgowda.todo.Adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mazealpha01.abhishekgowda.todo.CalenderActivity;
import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.Extra.CheckAnimator;
import com.mazealpha01.abhishekgowda.todo.Extra.OnitemClickListner;
import com.mazealpha01.abhishekgowda.todo.Extra.ReminderManager;
import com.mazealpha01.abhishekgowda.todo.MainActivity;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;
import com.mazealpha01.abhishekgowda.todo.R;
import com.mazealpha01.abhishekgowda.todo.Model.Task;
import com.mazealpha01.abhishekgowda.todo.ViewActivity;
import com.mazealpha01.abhishekgowda.todo.Widget;
import com.haibin.calendarview.Calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import static java.lang.String.format;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    private ArrayList<Task> listTask;
    public ImageView overflow;
    private Context mContext;
    private Dbhelper dbhelper;
    private static final String TAG = "TaskAdapter";
    private Activity activity;
    private ItemTouchHelper touchHelper;
    private ArrayList<Usersettings> usersettingsdata;

    public TaskAdapter(ArrayList<Task> listTask, Context mContext,Activity activity ) {
        this.listTask = listTask;
        this.mContext = mContext;
        this.activity = activity;

    }


    public class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textViewName,date;
        public  ImageView checked,uncheckeditem,delete;
        private View tagViewC;
        private  OnitemClickListner onitemClickListner;
        private  GestureDetector gestureDetector;
        private CheckAnimator checkAnimator;
        private boolean ischecked;
        private Task task;


        public TaskHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.task_name);
            checked = (ImageView) view.findViewById(R.id.checked);
            tagViewC = (View) view.findViewById(R.id.tag);
            uncheckeditem = (ImageView)view.findViewById(R.id.unchecked_item) ;
            //  Typeface hface = Typeface.createFromAsset(mContext.getAssets(), "Helvetica Neu Bold.ttf");
            //  textViewName.setTypeface(hface);
            delete = (ImageView)view.findViewById(R.id.bin);
            date = (TextView)view.findViewById(R.id.task_date);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onitemClickListner.OnClick(v,this.getAdapterPosition());
        }

        public void setItemClickListner(OnitemClickListner onitemClickListner){
            this.onitemClickListner = onitemClickListner;


        }
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_iteam, parent, false);
        return new TaskHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final TaskHolder holder, final int position) {
        dbhelper = new Dbhelper(mContext);
        usersettingsdata = dbhelper.getusersettings();
        holder.task = getItem(position);

        Integer colour = Integer.valueOf(holder.task.getTag());
        if (listTask.get(position).getTasktodo()!=null){
            holder.textViewName.setText(holder.task.getTasktodo());
            holder.checked.setColorFilter(colour, PorterDuff.Mode.SRC_IN);
            holder.uncheckeditem.setColorFilter(colour, PorterDuff.Mode.SRC_IN);
            holder.tagViewC.setBackgroundColor(Integer.valueOf(holder.task.getTag()));
        }

        if (usersettingsdata.get(0).getShowDate() == 1) {
            holder.date.setText(listTask.get(position).getDate() + " - " + listTask.get(position).getTime());
            holder.date.setVisibility(View.VISIBLE);
        } else {
            holder.date.setVisibility(View.GONE);
        }

        Log.d(TAG, "onBindViewHolder: position "+ position);


        holder.gestureDetector = new GestureDetector(mContext, new GestureListener(holder));
        holder.checkAnimator = new CheckAnimator(holder.uncheckeditem, holder.checked,holder.textViewName,mContext,dbhelper,holder.task.getID(),holder.task.getCalendarMark(),holder.task.getTasktodo());

            Check(holder);



        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    cancelnotification(holder.task.getTasktodo(),holder.task.getAlarmID());
                    removeAt(holder.getAdapterPosition());
            }
        });

        holder.setItemClickListner(new OnitemClickListner() {
            @Override
            public void OnClick(View v, int Position) {

                //  Toast.makeText(mContext, "" +listTask.get(Position).getID(), Toast.LENGTH_SHORT).show();
                String id = listTask.get(Position).getID().toString();
                String name = listTask.get(Position).getTasktodo();
                Cursor data = dbhelper.getId(id);
                String parcletag = listTask.get(Position).getTag();
                String note = listTask.get(Position).getNote();
                String time = listTask.get(Position).getTime();
                String date = listTask.get(Position).getDate();
                int IteamID= -1;
                while (data.moveToNext()){
                    IteamID = data.getInt(0);
                }

                if (IteamID > -1){
                    Intent intent = new Intent(mContext,ViewActivity.class);
                    intent.putExtra("id",IteamID);
                    intent.putExtra("name",name);
                    intent.putExtra("tag",parcletag);
                    intent.putExtra("note",note);
                    intent.putExtra("time",time);
                    intent.putExtra("date",date);
                    //     Pair[] pairs =  new Pair[3];
                    //     pairs[0] = new Pair<View,String>(holder.textViewName,"taskname");
                    // ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(activity,Pair.create(v,"taskname"));
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_up,R.anim.stay);
                }else {
                    Toast.makeText(mContext, " no id associated", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.checked.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return holder.gestureDetector.onTouchEvent(event);
            }
        });

        holder.uncheckeditem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return holder.gestureDetector.onTouchEvent(event);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTask.size();
    }
    @Override
    public int getItemViewType(int position){
        return position;
    }

    public Task getItem(int position) {
        return listTask.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listTask.get(position).hashCode();
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener{

        TaskHolder mHolder;

        public GestureListener(TaskHolder holder ) {
            mHolder = holder;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            mHolder.checkAnimator.tooglecheck();
            //  mHolder.ischecked=true;
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }

    private void Check(TaskHolder holder){
        if (dbhelper.check(holder.task.getID())){
            holder.uncheckeditem.setVisibility(View.GONE);
            Log.d(TAG, "Check: equals 1");
            holder.checked.setVisibility(View.VISIBLE);
            holder.textViewName.setPaintFlags( holder.textViewName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textViewName.setTextColor(mContext.getResources().getColor(R.color.text_grey));
        }else {
            Log.d(TAG, "Check: equals null");
            holder.uncheckeditem.setVisibility(View.VISIBLE);
            holder.checked.setVisibility(View.GONE);
            holder.textViewName.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.textViewName.setPaintFlags(holder. textViewName.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

    }


    private void removeAt(int position) {
        dbhelper.deletecoloumn(listTask.get(position).getID());
        dbhelper.deleteallitem(listTask.get(position).getID());
        dbhelper.deleteMark(listTask.get(position).getCalendarMark());
        listTask.remove(position);
        notifyItemRemoved(position);

        Intent updateIntent = new Intent();
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(Widget.UPDATE_MEETING_ACTION, "android.appwidget.action.APPWIDGET_UPDATE");
        onMessage(mContext,updateIntent);
        ((MainActivity)mContext).checkempty();

    }

    private void cancelnotification(String taskstring,Integer alaramID){
        Intent notificationIntent = new Intent(mContext, ReminderManager.class);
        notificationIntent.putExtra("task", taskstring);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(), alaramID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    protected void onMessage(Context context,Intent data) {
        Intent intent_meeting_update=new  Intent(context, Widget.class);
        intent_meeting_update.setAction(Widget.UPDATE_MEETING_ACTION);
        context.sendBroadcast(intent_meeting_update);
    }

}





