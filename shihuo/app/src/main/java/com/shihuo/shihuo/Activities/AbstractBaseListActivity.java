package com.shihuo.shihuo.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.Views.loadmore.LoadMoreListViewContainer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by cm_qiujiaheng on 2016/11/3.
 * 基础列表页面
 */

public abstract class AbstractBaseListActivity extends BaseActivity {

    @BindView(R.id.imag_left)
    ImageView leftbtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rightbtn)
    Button rightbtn;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.load_more_list_view_container)
    LoadMoreListViewContainer loadMoreListViewContainer;
    @BindView(R.id.refresh_frame)
    PtrClassicFrameLayout refreshFrame;

    protected BaseAdapter mAdapter;

    protected Handler mHandler = new Handler();
    @BindView(R.id.new_address)
    Button newAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_list_layout);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {

        setTitle();
        leftbtn.setVisibility(View.VISIBLE);
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

    protected View getHeaderView() {
        return null;
    }


    @OnClick(R.id.imag_left)
    public void onClick() {
        this.finish();
    }


    public abstract void setTitle();

    protected abstract BaseAdapter getCustomAdapter();

    protected abstract void refreshData();

    protected abstract void loadMoreData();
}
