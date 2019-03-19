package com.mazealpha01.abhishekgowda.todo.Extra;

import android.view.View;

import com.mazealpha01.abhishekgowda.todo.Adapter.TaskAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RecyclerViewItemTouchListner itemTouchListner;
    public RecyclerViewTouchHelper(int dragDirs, int swipeDirs,RecyclerViewItemTouchListner itemTouchListner) {
        super(dragDirs, swipeDirs);
        this.itemTouchListner = itemTouchListner;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (itemTouchListner != null)
            itemTouchListner.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());

    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }
}
