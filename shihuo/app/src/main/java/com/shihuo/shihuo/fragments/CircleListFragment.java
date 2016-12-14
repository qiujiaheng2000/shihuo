
package com.shihuo.shihuo.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shihuo.shihuo.Activities.FavGoodsListActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.GoodsFavItemView;
import com.shihuo.shihuo.models.GoodsTypeModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品列表frag Created by lishuai on 16/12/13.
 */

public class CircleListFragment extends BaseListFragment {

    public static final String TAG = "CircleListFragment";

    public static CircleListFragment newInstance(GoodsTypeModel model, int position) {
        CircleListFragment f = new CircleListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", model);
        bundle.putInt("position", position);
        f.setArguments(bundle);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    protected BaseAdapter getCustomAdapter() {
        return null;
    }

    @Override
    protected void refreshData() {

    }

    @Override
    protected void loadMoreData() {

    }

//    public class CircleListAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return mGoodsFavList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return mGoodsFavList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            FavGoodsListActivity.FavGoodsAdapter.ViewHolder viewHolder;
//            if (convertView == null) {
//                convertView = LayoutInflater.from(FavGoodsListActivity.this).inflate(
//                        R.layout.view_goods_fav_item, null);
//                viewHolder = new FavGoodsListActivity.FavGoodsAdapter.ViewHolder(convertView);
//                convertView.setTag(viewHolder);
//            }
//            viewHolder = (FavGoodsListActivity.FavGoodsAdapter.ViewHolder)convertView.getTag();
//            viewHolder.view_goods_fav.setData(mGoodsFavList.get(position));
//            return convertView;
//        }
//
//        class ViewHolder {
//            @BindView(R.id.view_goods_fav)
//            GoodsFavItemView view_goods_fav;
//
//            ViewHolder(View view) {
//                ButterKnife.bind(this, view);
//            }
//        }
//    }
}
