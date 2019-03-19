package com.mazealpha01.abhishekgowda.todo.Preference;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.Model.BasePreferenceData;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;
import com.mazealpha01.abhishekgowda.todo.R;

public class AboutPreferenceData extends BasePreferenceData<AboutPreferenceData.ViewHolder> {

    private Usersettings usersettings;
    private Context context;
    private Dbhelper dbhelper;

    public AboutPreferenceData(Usersettings usersettings, Context context) {
        this.usersettings =  usersettings;
        this.context = context;

    }

    public class ViewHolder extends BasePreferenceData.ViewHolder {
        private TextView versionname;

        public ViewHolder(View v) {
            super(v);
            versionname  =  v.findViewById(R.id.version_name);

        }
    }

    @Override
    public BasePreferenceData.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.aboutitem,parent,false));
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            holder.versionname.setText("Version"+version);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }



}
