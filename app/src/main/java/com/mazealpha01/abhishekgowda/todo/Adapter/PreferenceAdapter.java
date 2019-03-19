package com.mazealpha01.abhishekgowda.todo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mazealpha01.abhishekgowda.todo.Extra.RecyclerViewTouchHelper;
import com.mazealpha01.abhishekgowda.todo.Model.BasePreferenceData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PreferenceAdapter extends RecyclerView.Adapter<BasePreferenceData.ViewHolder> {
    private List<BasePreferenceData> items;
    public PreferenceAdapter(List<BasePreferenceData> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BasePreferenceData.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return items.get(viewType).getViewHolder(LayoutInflater.from(parent.getContext()),parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BasePreferenceData.ViewHolder holder, int position) {

        items.get(position).bindViewHolder(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
