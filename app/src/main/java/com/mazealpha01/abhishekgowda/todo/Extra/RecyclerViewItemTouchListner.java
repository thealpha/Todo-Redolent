package com.mazealpha01.abhishekgowda.todo.Extra;

import androidx.recyclerview.widget.RecyclerView;

interface RecyclerViewItemTouchListner {
    void onSwiped(RecyclerView.ViewHolder viewHolder , int direction , int  position);
}
