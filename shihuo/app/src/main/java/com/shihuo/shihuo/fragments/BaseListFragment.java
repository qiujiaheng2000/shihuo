
package com.shihuo.shihuo.fragments;

import com.android.volley.Request;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.Views.loadmore.LoadMoreListViewContainer;
import com.shihuo.shihuo.application.BaseApplication;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiahengqiu on 2016/10/23.
 */
public abstract class BaseListFragment extends BaseFragment {
    protected final String REFRESH_TIME = "latest_refresh_time";

    String TAG = getClass().getSimpleName();

    String mRequestTag;

    @BindView(R.id.list_view)
    public ListView listView;

    @BindView(R.id.load_more_list_view_container)
    public LoadMoreListViewContainer loadMoreListViewContainer;

    @BindView(R.id.refresh_frame)
    public PtrClassicFrameLayout refreshFrame;

    protected BaseAdapter mAdapter;

    protected Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Trace.v(TAG, "onCreateView");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.frag_base_list, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        refreshFrame.setLoadingMinTime(1000);
        refreshFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listView, header);
            }
        });

        mAdapter = getCustomAdapter();


        loadMoreListViewContainer.setAutoLoadMore(false);
        loadMoreListViewContainer.useDefaultFooter();

        View headerView = getHeaderView();
        if (null != headerView)
            listView.addHeaderView(headerView);

        listView.setAdapter(mAdapter);

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                loadMoreData();
            }
        });
        refreshFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshFrame.autoRefresh();
            }
        }, 100);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected View getHeaderView() {
        return null;
    }

    protected abstract BaseAdapter getCustomAdapter();

    protected abstract void refreshData();

    protected abstract void loadMoreData();
}
