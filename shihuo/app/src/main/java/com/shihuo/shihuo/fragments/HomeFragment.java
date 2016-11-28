package com.shihuo.shihuo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.GoodsDetailActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.HomeHeaderView;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreGridViewContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.models.GoodsModel;
import com.shihuo.shihuo.models.HomeHorScrollConfigModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by jiahengqiu on 2016/10/23.
 * 首页
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.title_bar)
    RelativeLayout titleBar;

    public static ArrayList<GoodsModel> mGoodsListTest = new ArrayList<>();
    @BindView(R.id.load_more_grid_view)
    GridViewWithHeaderAndFooter loadMoreGridView;
    @BindView(R.id.load_more_grid_view_container)
    LoadMoreGridViewContainer loadMoreGridViewContainer;
    @BindView(R.id.load_more_grid_view_ptr_frame)
    PtrClassicFrameLayout loadMoreGridViewPtrFrame;
    @BindView(R.id.btn_msg)
    ImageButton btnMsg;
    @BindView(R.id.btn_more)
    ImageButton btnMore;


    private Handler mHandler = new Handler();

    private MyHomeGridViewAdapter mAdapter;

    static {
        for (int i = 0; i < 15; i++) {
            mGoodsListTest.add(new GoodsModel(String.valueOf(i),
                    "连衣裙 " + i,
                    "商品的描述是，这个是好商品 " + i,
                    "￥176" + i,
                    "￥256" + i,
                    " " + i,
                    "goodsDiscount " + i,
                    "imageUrl = " + i));
        }
    }

    public ArrayList<GoodsModel> mGoodsList = new ArrayList<>();

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.home_activity, null);
        ButterKnife.bind(this, view);
        initRefreshView();
        return view;
    }

    private void initRefreshView() {


        loadMoreGridViewPtrFrame.setLoadingMinTime(1000);
        loadMoreGridViewPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // here check list view, not content.
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, loadMoreGridView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mGoodsList.clear();
                        mGoodsList.addAll(mGoodsListTest);
                        loadMoreGridViewPtrFrame.refreshComplete();
                        mAdapter.notifyDataSetChanged();
                        loadMoreGridViewContainer.setAutoLoadMore(true);
                        loadMoreGridViewContainer.loadMoreFinish(mGoodsList.isEmpty(), true);
                    }
                }, 2000);
            }
        });

        loadMoreGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsModel goodsModel = (GoodsModel) parent.getItemAtPosition(position);
                GoodsDetailActivity.startGoodsDetailActivity(getContext(), goodsModel);
            }
        });
        mAdapter = new MyHomeGridViewAdapter(getContext(), mGoodsList);
        HomeHeaderView homeHeaderView = new HomeHeaderView(getContext());
        homeHeaderView.setHorScrollViewDatas(HomeHorScrollConfigModel.getTestDatas());
        loadMoreGridView.addHeaderView(homeHeaderView);

        loadMoreGridViewContainer.setAutoLoadMore(false);
        loadMoreGridViewContainer.useDefaultFooter();
        loadMoreGridView.setAdapter(mAdapter);

        loadMoreGridViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // load more complete

                        mGoodsList.addAll(mGoodsListTest);
                        loadMoreGridViewPtrFrame.refreshComplete();
                        loadMoreGridViewContainer.loadMoreFinish(mGoodsList.isEmpty(), true);
                        mAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });

        loadMoreGridViewPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMoreGridViewPtrFrame.autoRefresh();
            }
        }, 100);
    }

    @OnClick({R.id.btn_msg, R.id.btn_more})
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


    public static class MyHomeGridViewAdapter extends BaseAdapter {
        public Context mContext;

        public ArrayList<GoodsModel> mGoodsList;

        public MyHomeGridViewAdapter() {
            super();
        }

        public MyHomeGridViewAdapter(Context context, ArrayList<GoodsModel> mGoodsList) {
            this.mContext = context;
            this.mGoodsList = mGoodsList;
        }

        @Override
        public int getCount() {
            return mGoodsList.size();
        }

        @Override
        public Object getItem(int position) {
            return mGoodsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView != null) {
                viewHolder = (ViewHolder) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.goods_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            GoodsModel goods = (GoodsModel) getItem(position);
            viewHolder.goodsTitle.setText(goods.goodsTitle);
            viewHolder.goodsOriginPrice.setText(goods.goodsOriginPrice);
            viewHolder.goodsNewPrice.setText(goods.goodsNewPrice);
            viewHolder.sales.setText(String.format(mContext.getString(R.string.sales), goods.salesNum));
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.goods_title)
            TextView goodsTitle;
            @BindView(R.id.goods_new_price)
            TextView goodsNewPrice;
            @BindView(R.id.goods_origin_price)
            TextView goodsOriginPrice;
            @BindView(R.id.sales)
            TextView sales;
            @BindView(R.id.detail_layout)
            LinearLayout detailLayout;
            @BindView(R.id.imageView)
            SimpleDraweeView imageView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
