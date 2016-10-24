package com.shihuo.shihuo.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.GridViewWithHeaderAndFooter;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.Goods;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jiahengqiu on 2016/10/23.
 * 首页
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.btn_msg)
    ImageButton btnMsg;
    @BindView(R.id.btn_more)
    ImageButton btnMore;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    @BindView(R.id.refresh_view_frame)
    PtrClassicFrameLayout refreshViewFrame;

    public static ArrayList<Goods> mGoodsListTest = new ArrayList<>();
    @BindView(R.id.rotate_header_grid_view)
    GridViewWithHeaderAndFooter rotateHeaderGridView;
    private Handler mHandler = new Handler();

    private MyHomeGridViewAdapter mAdapter;

    static {
        for (int i = 0; i < 15; i++) {
            mGoodsListTest.add(new Goods(String.valueOf(i),
                    "goodTitle " + i,
                    "￥ " + i,
                    "￥ " + i,
                    " " + i,
                    "goodsDiscount " + i,
                    "imageUrl = " + i));
        }
    }

    public ArrayList<Goods> mGoodsList = new ArrayList<>();

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
        mAdapter = new MyHomeGridViewAdapter();
        rotateHeaderGridView.setAdapter(mAdapter);
        refreshViewFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mGoodsList.clear();
                        mGoodsList.addAll(mGoodsListTest);
                        mAdapter.notifyDataSetChanged();
                        refreshViewFrame.refreshComplete();
                        refreshViewFrame.setLoadMoreEnable(true);
                    }
                }, 2000);
            }
        });
        refreshViewFrame.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mGoodsList.addAll(mGoodsListTest);
                        mAdapter.notifyDataSetChanged();
                        refreshViewFrame.loadMoreComplete(true);
                    }
                }, 2000);
            }
        });
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

    public class MyHomeGridViewAdapter extends BaseAdapter {

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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.goods_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            Goods goods = (Goods) getItem(position);
            viewHolder.goodsTitle.setText(goods.goodsTitle);
            viewHolder.goodsOriginPrice.setText(goods.goodsOriginPrice);
            viewHolder.goodsNewPrice.setText(goods.goodsNewPrice);
            viewHolder.sales.setText(String.format(getString(R.string.sales), goods.salesNum));
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
            ImageView imageView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
