package com.mazealpha01.abhishekgowda.todo.Extra;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.mazealpha01.abhishekgowda.todo.Helper.Dbhelper;
import com.mazealpha01.abhishekgowda.todo.R;


/**
 * Created by User on 8/21/2017.
 */

public class CheckAnimatorItem {

    private static final String TAG = "CheckAnimatorItem";
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    public ImageView uncheck, check;
    public TextView task;
    private Context mContext;
    private Dbhelper dbhelper;
    private Integer id;



    public CheckAnimatorItem(ImageView uncheck, ImageView check, TextView task, Context mContext, Dbhelper dbhelper, Integer id) {
        this.uncheck = uncheck;
        this.check = check;
        this.task = task;
        this.mContext = mContext;
        this.dbhelper = dbhelper;
        this.id = id;

    }

    public void tooglecheck(){
        Log.d(TAG, "toggleLike:  checking .");

        AnimatorSet animationSet =  new AnimatorSet();
        if(check.getVisibility() == View.VISIBLE){
            Log.d(TAG, "toggleLike: toggling unchecked.");
            check.setScaleX(0.1f);
            check.setScaleY(0.1f);
            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(check, "scaleY", 1f, 0f);
            scaleDownY.setDuration(300);
            scaleDownY.setInterpolator(ACCELERATE_INTERPOLATOR);
            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(check, "scaleX", 1f, 0f);
            scaleDownX.setDuration(300);
            scaleDownX.setInterpolator(ACCELERATE_INTERPOLATOR);
            check.setVisibility(View.GONE);
            uncheck.setVisibility(View.VISIBLE);
            task.setTextColor(mContext.getResources().getColor(R.color.black));
            task.setPaintFlags( task.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            dbhelper.updateitem(id,"0");
            animationSet.playTogether(scaleDownY, scaleDownX);
        }

        else if(check.getVisibility() == View.GONE){
            Log.d(TAG, "toggleLike: toggling checked.");
            check.setScaleX(0.1f);
            check.setScaleY(0.1f);

            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(check, "scaleY", 0.1f, 1f);
            scaleDownY.setDuration(300);
            scaleDownY.setInterpolator(DECCELERATE_INTERPOLATOR);
            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(check, "scaleX", 0.1f, 1f);
            scaleDownX.setDuration(300);
            scaleDownX.setInterpolator(DECCELERATE_INTERPOLATOR);
            check.setVisibility(View.VISIBLE);
            uncheck.setVisibility(View.GONE);
            task.setPaintFlags(task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            task.setTextColor(mContext.getResources().getColor(R.color.text_grey));
            dbhelper.updateitem(id,"1");
            animationSet.playTogether(scaleDownY, scaleDownX);

        }

        animationSet.start();

    }
}




