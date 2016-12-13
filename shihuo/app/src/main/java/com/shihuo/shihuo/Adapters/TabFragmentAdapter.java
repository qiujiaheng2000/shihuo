
package com.shihuo.shihuo.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by lishuai on 16/12/13.
 */

public class TabFragmentAdapter extends FragmentPagerAdapter {

    private Context context;

    private List<Fragment> fragments;

    public TabFragmentAdapter(List<Fragment> fragments, FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
