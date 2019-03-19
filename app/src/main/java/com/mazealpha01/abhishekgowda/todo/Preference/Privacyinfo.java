package com.mazealpha01.abhishekgowda.todo.Preference;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mazealpha01.abhishekgowda.todo.Model.BasePreferenceData;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;
import com.mazealpha01.abhishekgowda.todo.R;

public class Privacyinfo extends BasePreferenceData<Privacyinfo.ViewHolder> {

    private Usersettings usersettings;
    private Context context;

    public Privacyinfo(Usersettings usersettings, Context context) {
        this.usersettings =  usersettings;
        this.context = context;

    }

    public class ViewHolder extends BasePreferenceData.ViewHolder {
        private RelativeLayout layout;
        public ViewHolder(View v) {
            super(v);
            layout = (RelativeLayout)v.findViewById(R.id.lay);

        }
    }

    @Override
    public BasePreferenceData.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.privacyinfoitem,parent,false));
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/redolent-privacy-policy/home"));
                context.startActivity(browserIntent);

            }
        });
    }


}
