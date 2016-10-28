package com.shihuo.shihuo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.Goods;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shihuo.shihuo.fragments.HomeFragment.mGoodsListTest;

/**
 * Created by jiahengqiu on 2016/10/23.
 * 微视频
 */
public class VideoFragment extends BaseFragment {

    @BindView(R.id.rotate_header_grid_view)
    GridView rotateHeaderGridView;

    private MyHomeGridViewAdapter mAdapter;
    public ArrayList<Goods> mGoodsList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.video_activity, null);
        ButterKnife.bind(this, view);

        mGoodsList.addAll(mGoodsListTest);
        mAdapter = new MyHomeGridViewAdapter();
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.banner_view,null);
//        HeaderView headerView = new HeaderView(getContext());
//        rotateHeaderGridView.addHeaderView(headerView);

        rotateHeaderGridView.setAdapter(mAdapter);

        return view;
    }

    public static VideoFragment newInstance() {
        VideoFragment frament = new VideoFragment();
        Bundle bundle = new Bundle();
        frament.setArguments(bundle);
        return frament;
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
