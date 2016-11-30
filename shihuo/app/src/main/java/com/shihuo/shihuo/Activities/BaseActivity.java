package com.shihuo.shihuo.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.jph.takephoto.app.TakePhotoFragmentActivity;

import com.android.volley.Request;
import com.shihuo.shihuo.application.BaseApplication;

/**
 * Created by cm_qiujiaheng on 2016/11/2.
 */

public abstract class BaseActivity extends TakePhotoFragmentActivity {
    protected final String REFRESH_TIME = "latest_refresh_time";

    public String mRequestTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestTag = initRequestTag();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public abstract void initViews();

    public void addRequest(Request req) {
        BaseApplication.getInstance().addToRequestQueue(req, mRequestTag);
    }

    public String initRequestTag() {
        return getRequestTag();
    }

    public String getRequestTag() {
        return getClass().getName();
    }

}
