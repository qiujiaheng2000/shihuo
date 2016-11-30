
package com.shihuo.shihuo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.shihuo.shihuo.application.BaseApplication;

/**
 * Created by jiahengqiu on 2016/10/23.
 */
public class BaseFragment extends Fragment {
    protected final String REFRESH_TIME = "latest_refresh_time";

    String TAG = getClass().getSimpleName();

    String mRequestTag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mRequestTag = initRequestTag();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Trace.v(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void addRequest(Request req) {
        BaseApplication.getInstance().addToRequestQueue(req, mRequestTag);
    }

    public void cancelRequests() {
        cancelRequests(mRequestTag);
    }

    public void cancelRequests(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            BaseApplication.getInstance().cancelPendingRequests(tag);
        }
    }

    public String initRequestTag() {
        return getRequestTag();
    }

    public String getRequestTag() {
        return getClass().getName();
    }

    public String getUniqueRequestTag() {
        return getClass().getName() + System.currentTimeMillis();
    }
}
