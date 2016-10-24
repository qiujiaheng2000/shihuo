package com.shihuo.shihuo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shihuo.shihuo.R;

/**
 * Created by jiahengqiu on 2016/10/23.
 * 便民服务
 */
public class ServiceFragment extends BaseFragment {

    public static ServiceFragment newInstance() {
        ServiceFragment frament = new ServiceFragment();
        Bundle bundle = new Bundle();
        frament.setArguments(bundle);
        return frament;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.service_activity, null);
        return view;
    }
}
