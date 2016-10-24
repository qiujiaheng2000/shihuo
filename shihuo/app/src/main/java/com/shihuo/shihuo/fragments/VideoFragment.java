package com.shihuo.shihuo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shihuo.shihuo.R;

/**
 * Created by jiahengqiu on 2016/10/23.
 * 微视频
 */
public class VideoFragment extends BaseFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.video_activity, null);
        return view;
    }

    public static VideoFragment newInstance() {
        VideoFragment frament = new VideoFragment();
        Bundle bundle = new Bundle();
        frament.setArguments(bundle);
        return frament;
    }
}
