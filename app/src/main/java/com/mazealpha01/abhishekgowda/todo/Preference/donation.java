package com.mazealpha01.abhishekgowda.todo.Preference;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.button.MaterialButton;
import com.mazealpha01.abhishekgowda.todo.Model.BasePreferenceData;
import com.mazealpha01.abhishekgowda.todo.R;

public class donation extends BasePreferenceData<donation.ViewHolder> {

    private Context context;

    public donation( Context context) {
        this.context = context;

    }

    public class ViewHolder extends BasePreferenceData.ViewHolder {
        private RelativeLayout layout;
        private Button paypal;
        private Button discord;
        public ViewHolder(View v) {
            super(v);
            layout = (RelativeLayout)v.findViewById(R.id.lay);
            paypal = (Button)v.findViewById(R.id.paypal_btn);
            discord = (Button)v.findViewById(R.id.discord_btn);

        }
    }

    @Override
    public BasePreferenceData.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.donation,parent,false));
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {

        holder.paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://paypal.me/mazealpha01?locale.x=en_GB"));
                context.startActivity(browserIntent);

            }
        });

        holder.discord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/NwP978f"));
                context.startActivity(browserIntent);
            }
        });
    }


}
