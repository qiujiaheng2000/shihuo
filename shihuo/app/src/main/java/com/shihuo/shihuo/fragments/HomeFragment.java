
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
import com.shihuo.shihuo.Views.ShoppingCarView;
import com.shihuo.shihuo.models.BaseGoodsListModel;
import com.shihuo.shihuo.models.BaseGoodsModel;
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

    @BindView(R.id.view_shoppingCar)
    ShoppingCarView mShoppingCarView;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshRecyclerView mSwipeRefresh;

    private Context mContext;

    private HomeAdapter mAdapter;

    private List<HomeModel> mList;

    private int page = 1;

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
        mShoppingCarView.setOnClickListener(new ShoppingCarView.OnViewClickListener() {
            @Override
            public void onShoppingCarListener() {
                AppUtils.showToast(getContext(), "购物车");
            }

            @Override
            public void onBackTopListener() {
                mSwipeRefresh.getScrollView().smoothScrollToPosition(0);
            }
        });
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
                            requestHotGoods(false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mSwipeRefresh.setRefreshing(false);
                    }
                });
        addRequest(request);
    }

    /**
     * 请求热销商品
     */
    private void requestHotGoods(final boolean isLoadMore) {
        if (isLoadMore) {
            page++;
        } else {
            page = 1;
        }
        String url = NetWorkHelper.API_GET_HOT_GOODS + "?pageNum=" + page;
        final GsonRequest<BaseGoodsListModel> request = new GsonRequest<>(
                NetWorkHelper.getApiUrl(url), BaseGoodsListModel.class,
                AppUtils.getOAuthMap(getActivity()), new Response.Listener<BaseGoodsListModel>() {
                    @Override
                    public void onResponse(BaseGoodsListModel response) {
                        if (response != null && response.data != null && response.data.page != null) {
                            // 设置热销商品
                            HomeModel model = new HomeModel();
                            model.item_type = HomeModel.ITEM_TYPE_GOODS;
                            if (!response.data.page.resultList.isEmpty()) {
                                for (int i = 0; i < response.data.page.resultList.size(); i++) {
                                    BaseGoodsModel baseGoodsModel = new BaseGoodsModel();
                                    baseGoodsModel.goodsLeftModel = response.data.page.resultList
                                            .get(2 * i);
                                    if ((i * 2 + 1) < response.data.page.resultList.size()) {
                                        baseGoodsModel.goodsRightModel = response.data.page.resultList
                                                .get(2 * i + 1);
                                    }
                                    model.baseGoodsModel = baseGoodsModel;
                                    mList.add(model);
                                }
                            }
                            if (isLoadMore) {
                                mSwipeRefresh.setLoading(false);
                            } else {
                                mSwipeRefresh.setRefreshing(false);
                            }
                        }
                        mAdapter.bindData(mList);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (isLoadMore) {
                            mSwipeRefresh.setLoading(false);
                        } else {
                            page = 0;
                            mSwipeRefresh.setRefreshing(false);
                        }
                        mAdapter.bindData(mList);
                        mSwipeRefresh.setRefreshing(false);
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
        requestHotGoods(true);
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
