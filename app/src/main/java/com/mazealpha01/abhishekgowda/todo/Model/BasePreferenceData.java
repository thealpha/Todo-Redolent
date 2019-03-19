package com.mazealpha01.abhishekgowda.todo.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BasePreferenceData<V  extends BasePreferenceData.ViewHolder>{

    public abstract ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent);

    public abstract void bindViewHolder(V holder);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View v) {
            super(v);
        }

        public final Context getContext() {
            return itemView.getContext();
        }



    }

}

