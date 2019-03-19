package com.mazealpha01.abhishekgowda.todo.Preference;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.Helper.AdditionalHelper;
import com.mazealpha01.abhishekgowda.todo.Model.BasePreferenceData;
import com.mazealpha01.abhishekgowda.todo.Model.Usersettings;
import com.mazealpha01.abhishekgowda.todo.R;

import java.util.ArrayList;

public class NotifytunePreferenceData extends BasePreferenceData<NotifytunePreferenceData.ViewHolder> {

    private Usersettings usersettings;
    private Context context;
    private Dbhelper dbhelper;
    private AdditionalHelper helper;
    private static final String TAG = "NotifytunePreferenceDat";
    private ArrayList<Usersettings> usersettingslist;
    private MediaPlayer mediaPlayer;
    private int rawtune;
    private String tune;

    public NotifytunePreferenceData(Usersettings usersettings, Context context) {
        this.usersettings =  usersettings;
        this.context = context;

    }

    public class ViewHolder extends BasePreferenceData.ViewHolder {
        private TextView tune;
        private LottieAnimationView bellanim;
        public ViewHolder(View v) {
            super(v);
            tune = v.findViewById(R.id.tunename);
            bellanim = v.findViewById(R.id.bell_anim);

        }
    }

    @Override
    public BasePreferenceData.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.notifytuneitem,parent,false));
    }

    @Override
    public void bindViewHolder(final ViewHolder holder) {
        dbhelper =  new Dbhelper(context);
        helper =  new AdditionalHelper();
        usersettingslist = new ArrayList<>();
        usersettingslist = dbhelper.getusersettings();
        usersettings =  new Usersettings();

        holder.tune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picktunedialog(holder);
            }
        });

        holder.tune.setText(helper.gettune(usersettingslist.get(0).getNotificationtune()));
        holder.bellanim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersettingslist = dbhelper.getusersettings();
                if (usersettingslist.get(0).getNotificationtune()!= 0){
                    holder.bellanim.playAnimation();
                    mediaPlayer = MediaPlayer.create(context.getApplicationContext(),helper.getRawtune(usersettingslist.get(0).getNotificationtune()));
                    mediaPlayer.start();
                    holder.bellanim.setClickable(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            holder.bellanim.setClickable(true);

                        }
                    }, 3000);
                }
            }
        });


        Log.d(TAG, "bindViewHolder:  hmm " +usersettings.getNotificationtune());
    }

    private void picktunedialog(final ViewHolder holder){


        final CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setTitle("Select notification tone!");
        builder.setDialogBackgroundColor(context.getResources().getColor(R.color.white));

        builder.setSingleChoiceItems(new String[]{"None", "Peal", "Crystal", "Circles", "g3", "Greetings","Mail notification","Carillon","Secret","Angry birds","Wall-e","Spirit", "Google event" , "Spinner drop" , "Reactive", "Mario", "Chime", "pew pew pew" ,"Sneeze","Tuturu","Pikamu","Normal"}, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index) {
                dbhelper.updatenotifytune(index);
                holder.tune.setText(helper.gettune(index));


            }
        });

        builder.addButton("Done", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        builder.show();
    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}
