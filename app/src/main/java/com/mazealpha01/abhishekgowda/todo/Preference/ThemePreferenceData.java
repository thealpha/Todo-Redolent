package com.mazealpha01.abhishekgowda.todo.Preference;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.Model.BasePreferenceData;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;
import com.mazealpha01.abhishekgowda.todo.R;
import com.mazealpha01.abhishekgowda.todo.SettingsActivity;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.util.ArrayList;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

public class ThemePreferenceData extends BasePreferenceData<ThemePreferenceData.ViewHolder> {


    private Context context;
    private Dbhelper dbhelper;
    private int themeresult;
    private static final String TAG = "ThemePreferenceData";
    private ArrayList<Usersettings> userseettingsdata;


    public ThemePreferenceData( Context context) {
        this.context = context;
    }

    public class ViewHolder extends BasePreferenceData.ViewHolder {
        private TextView title;
        private View themeview;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            themeview = v.findViewById(R.id.themeView);

        }
    }

    @Override
    public BasePreferenceData.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.themeitem,parent,false));
    }

    @Override
    public void bindViewHolder(final ViewHolder holder) {
        dbhelper = new Dbhelper(context);
        dbhelper =  new Dbhelper(context);
        userseettingsdata = new ArrayList<>();
        userseettingsdata = dbhelper.getusersettings();
        GradientDrawable drawable = (GradientDrawable) holder.themeview.getBackground();
        drawable.setColor(Integer.valueOf(userseettingsdata.get(0).getColourtheme()));
        holder.themeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettagcolor(holder);
                Log.d(TAG, "onClick: colour " +themeresult);

            }
        });
    }

    private void gettagcolor(final ViewHolder holder) {
        new SpectrumDialog.Builder(context)
                .setColors(R.array.rainbow)
                .setSelectedColorRes(R.color.colorAccent)
                .setDismissOnColorSelected(true)
                .setOutlineWidth(2)
                .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                    @Override public void onColorSelected(boolean positiveResult, @ColorInt int color) {

                        if (positiveResult) {
                            GradientDrawable drawable = (GradientDrawable) holder.themeview.getBackground();
                            drawable.setColor(color);
                            themeresult = color;
                            dbhelper.updatetheme(String.valueOf(color));
                            ((SettingsActivity)context).reload();

                        } else {
                            Toast.makeText(context, " Color ", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).build().show(((AppCompatActivity)context).getSupportFragmentManager(), "dialog");

    }


}
