package com.mazealpha01.abhishekgowda.todo.Preference;

import android.content.ActivityNotFoundException;
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

public class SupportPreferenceData extends BasePreferenceData<SupportPreferenceData.ViewHolder> {

    private Usersettings usersettings;
    private Context context;

    public SupportPreferenceData(Usersettings usersettings, Context context) {
        this.usersettings =  usersettings;
        this.context = context;

    }

    public class ViewHolder extends BasePreferenceData.ViewHolder {
        private RelativeLayout layout;
        public ViewHolder(View v) {
            super(v);
            layout = (RelativeLayout)v.findViewById(R.id.layout);

        }
    }

    @Override
    public BasePreferenceData.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.supportitem,parent,false));
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "teammazealpha01@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    intent.putExtra(Intent.EXTRA_TEXT, "Feedback_text");
                    context.startActivity(intent);
                }catch(ActivityNotFoundException e){
                    //TODO smth
                }
            }
        });

    }


}
