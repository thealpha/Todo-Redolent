package com.mazealpha01.abhishekgowda.todo.Adapter;
import android.graphics.Color;

import com.mazealpha01.abhishekgowda.todo.Intro.IntroFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;




public class Intropageradapter extends FragmentPagerAdapter {
    public Intropageradapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return IntroFragment.newInstance(Color.parseColor("#b263d6"), position);
            case 1:
                return IntroFragment.newInstance(Color.parseColor("#e76464"), position);
            case 2:
                return IntroFragment.newInstance(Color.parseColor("#81d5ea"), position);
            case 3:
                return IntroFragment.newInstance(Color.parseColor("#e2c357"), position);
            default:
                return IntroFragment.newInstance(Color.parseColor("#4ace7d"), position);
        }
    }


    @Override
    public int getCount() {
        return 4;
    }

}
