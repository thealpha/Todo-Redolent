package com.mazealpha01.abhishekgowda.todo.Preference;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.Model.BasePreferenceData;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;
import com.mazealpha01.abhishekgowda.todo.R;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;

public class CmptPreferenceData extends BasePreferenceData<CmptPreferenceData.ViewHolder> {

    private Usersettings usersettings;
    private Context context;
    private Dbhelper dbhelper;
    private ArrayList<Usersettings> usersettingsdata;

    public CmptPreferenceData(Usersettings usersettings, Context context) {
        this.usersettings =  usersettings;
        this.context = context;

    }

    public class ViewHolder extends BasePreferenceData.ViewHolder {
        private TextView title;
        private SwitchButton switchButton;
        public ViewHolder(View v) {
            super(v);
            switchButton = v.findViewById(R.id.switch_completed);

        }
    }

    @Override
    public BasePreferenceData.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemboolcompleted,parent,false));
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {
        dbhelper =  new Dbhelper(context);
        usersettingsdata = new ArrayList<>();
        usersettingsdata = dbhelper.getusersettings();
        if (usersettingsdata.get(0).getHidecompletedtask().equals(0)) {
            holder.switchButton.setChecked(false);
        } else {
            holder.switchButton.setChecked(true);
        }

        holder.switchButton.isChecked();
        holder.switchButton.toggle();     //switch state
        holder.switchButton.toggle(true);//switch without animation
        holder.switchButton.setShadowEffect(true);//disable shadow effect
        holder.switchButton.setEnabled(true);//disable button
        holder.switchButton.setEnableEffect(true);//disable the switch animation
        holder.switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do your job
                if (isChecked)
                    dbhelper.updatecompleted(1);
                else
                    dbhelper.updatecompleted(0);


            }
        });

    }


}
