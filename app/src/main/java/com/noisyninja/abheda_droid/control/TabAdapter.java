package com.noisyninja.abheda_droid.control;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.noisyninja.abheda_droid.fragment.LessonDetailFrag;
import com.noisyninja.abheda_droid.fragment.MCQDetailFrag;
import com.noisyninja.abheda_droid.util.Constants;
import com.noisyninja.abheda_droid.fragment.InfoFrag;

/**
 * Created by ir2pi on 11/30/2014.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }
        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    //Fragement for Android Tab
                    return new LessonDetailFrag();
                case 1:
                    //Fragment for Ios Tab
                    return new MCQDetailFrag();
                case 2:
                    //Fragment for Windows Tab
                    return new InfoFrag();
            }
            return null;
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Constants.TAB_COUNT; //No of Tabs
        }
    }
