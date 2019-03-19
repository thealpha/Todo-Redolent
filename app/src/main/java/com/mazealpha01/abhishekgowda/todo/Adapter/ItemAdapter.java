package com.mazealpha01.abhishekgowda.todo.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.Extra.CheckAnimatorItem;
import com.mazealpha01.abhishekgowda.todo.Model.Item;
import com.mazealpha01.abhishekgowda.todo.Model.Task;
import com.mazealpha01.abhishekgowda.todo.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.IteamViewHolder> {
    private  ArrayList<Item> listitem;
    private Context context;
    private int lastposition =-1;
    private Dbhelper dbhelper;
    public ItemAdapter(ArrayList<Item> listitem, Context context) {
        this.listitem = listitem;
        this.context = context;
        notifyDataSetChanged();
    }
   public  class IteamViewHolder extends RecyclerView.ViewHolder{
        private TextView itemText;
        private View itemtag;
        private ImageView checkeditem,uncheckeditem,deleteitem;
       private  GestureDetector gestureDetector;
       private CheckAnimatorItem checkAnimatorItem;
       private Item item;


       private IteamViewHolder(@NonNull View itemView) {
           super(itemView);
           itemText = (TextView)itemView.findViewById(R.id.task_name_item);
           itemtag = (View)itemView.findViewById(R.id.tag_item);
           checkeditem = (ImageView)itemView.findViewById(R.id.checked_item);
           uncheckeditem = (ImageView)itemView.findViewById(R.id.unchecked_item);
           deleteitem = (ImageView)itemView.findViewById(R.id.bin_item);
       }
   }

    @NonNull
    @Override
    public IteamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ItemAdapter.IteamViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final IteamViewHolder holder, final int position) {
        Integer colourtag = Integer.valueOf(listitem.get(position).getTag());
      //  setAnimation(holder.itemView,position);
        if (listitem.get(position).getItem()!=null){
            holder.itemText.setText(listitem.get(position).getItem());
            holder.checkeditem.setColorFilter(colourtag, PorterDuff.Mode.SRC_IN);
            holder.uncheckeditem.setColorFilter(colourtag, PorterDuff.Mode.SRC_IN);
            holder.itemtag.setBackgroundColor(Integer.valueOf(listitem.get(position).getTag()));
        }
        holder.item = getItem(holder.getAdapterPosition());
        dbhelper = new Dbhelper(context);
        Check(holder);

        holder.checkeditem.setOnTouchListener(new View.OnTouchListener() {
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

        holder.deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(holder.getAdapterPosition());
            }
        });

        holder.gestureDetector = new GestureDetector(context, new GestureListener(holder));
        holder.checkAnimatorItem = new CheckAnimatorItem(holder.uncheckeditem, holder.checkeditem,holder.itemText,context,dbhelper,listitem.get(position).getID());

    }
    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public Item getItem(int position) {
        return listitem.get(position);
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener{

        IteamViewHolder mHolder;
        public GestureListener(IteamViewHolder holder) {
            mHolder = holder;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            mHolder.checkAnimatorItem.tooglecheck();
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }

    private void Check(IteamViewHolder holder){
        if (dbhelper.checkitem(holder.item.getID())){

            holder.uncheckeditem.setVisibility(View.GONE);
            holder.checkeditem.setVisibility(View.VISIBLE);
            holder.itemText.setPaintFlags( holder.itemText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.itemText.setTextColor(context.getResources().getColor(R.color.text_grey));

        }else{
            holder.uncheckeditem.setVisibility(View.VISIBLE);
            holder.checkeditem.setVisibility(View.GONE);
            holder.itemText.setTextColor(context.getResources().getColor(R.color.black));
            holder.itemText.setPaintFlags(holder. itemText.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));

        }
    }



    public void removeAt(int position) {
        dbhelper.deleteitem(listitem.get(position).getID());
        listitem.remove(position);
        notifyItemRemoved(position);

    }


}
