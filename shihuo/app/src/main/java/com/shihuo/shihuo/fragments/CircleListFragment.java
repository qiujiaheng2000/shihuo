
package com.shihuo.shihuo.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 商品列表frag Created by lishuai on 16/12/13.
 */

public class CircleListFragment extends BaseFragment {

    public static final String TAG = "CircleListFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }
}
