
package com.shihuo.shihuo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.GsonRequest;
import com.mylhyl.crlayout.SwipeRefreshAdapterView;
import com.mylhyl.crlayout.SwipeRefreshRecyclerView;
import com.shihuo.shihuo.Adapters.HomeAdapter;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.HomeModel;
import com.shihuo.shihuo.models.SysTypeModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jiahengqiu on 2016/10/23. 首页
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        SwipeRefreshAdapterView.OnListLoadListener {

    @BindView(R.id.title_bar)
    RelativeLayout titleBar;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshRecyclerView mSwipeRefresh;

    private Context mContext;

    private HomeAdapter mAdapter;

    private List<HomeModel> mList;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.home_activity, null);
        ButterKnife.bind(this, view);
        initView();
        requestSysData();
        return view;
    }

    private void initView() {
        mContext = getActivity();
        mList = new ArrayList<>();
        AppUtils.initSwipeRefresh(mContext, mSwipeRefresh);
        mSwipeRefresh.setOnListLoadListener(this);
        mSwipeRefresh.setOnRefreshListener(this);
        mAdapter = new HomeAdapter(getActivity(), mList);
        mSwipeRefresh.setAdapter(mAdapter);
    }

    /**
     * 请求首页商圈，商品分类，广告
     */
    private void requestSysData() {
        final GsonRequest<SysTypeModel> request = new GsonRequest<>(
                NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_SYS_TYPE), SysTypeModel.class,
                AppUtils.getOAuthMap(getActivity()), new Response.Listener<SysTypeModel>() {
                    @Override
                    public void onResponse(SysTypeModel response) {
                        if (response != null) {
                            // 设置商品分类
                            mList.clear();
                            HomeModel model = new HomeModel();
                            model.typeData = response;
                            mList.add(model);
                            mAdapter.bindData(mList);
                            mSwipeRefresh.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        addRequest(request);
    }

    @Override
    public void onRefresh() {
        requestSysData();
    }

    @Override
    public void onListLoad() {
        // mSwipeRefresh.setLoadCompleted(true);
        // if (isLoadMore) {
        // for (int i = 0; i < 10; i++) {
        // objects.add("数据" + i);
        // }
        // } else {
        // mSwipeRefresh.setEmptyText("没有数据了");
        // }
        // isLoadMore = false;
        // adapter.notifyDataSetChanged();
        // mSwipeRefresh.setLoading(false);
    }

    @OnClick({
            R.id.btn_msg, R.id.btn_more
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_msg:
                Toast.makeText(getActivity(), "消息按钮点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_more:
                Toast.makeText(getActivity(), "更多按钮点击", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
