package com.shihuo.shihuo.Activities.shop.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.fragments.BaseFragment;

/**
 * Created by cm_qiujiaheng on 2016/12/13.
 * 今月统计界面
 */

public class MonthFragment extends BaseFragment {

    public static MonthFragment newInstance() {
        Bundle args = new Bundle();
        MonthFragment fragment = new MonthFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_month, null);
        return view;
    }
}
