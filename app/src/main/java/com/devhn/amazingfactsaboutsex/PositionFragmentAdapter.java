package com.devhn.amazingfactsaboutsex;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by thaodv on 2/16/16.
 */
public class PositionFragmentAdapter extends FragmentPagerAdapter {
    protected static final String[] POSITION_SPINES = Constants.position_spines;
    private int mCount = POSITION_SPINES.length;
    private Context mContext;
    SharedPreferences mPref;
    SharedPreferences.Editor editor;

    public PositionFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        mPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = mPref.edit();
    }

    @Override
    public Fragment getItem(int position) {
        int chapterId = 1 + position % POSITION_SPINES.length;
        return PositionFragment.newInstance(chapterId);
    }


    @Override
    public int getCount() {
        return mCount;
    }
}
