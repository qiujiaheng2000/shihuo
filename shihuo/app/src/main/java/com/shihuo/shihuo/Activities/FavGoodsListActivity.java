package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.GoodsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cm_qiujiaheng on 2016/11/3.
 * 商品收藏界面
 */

public class FavGoodsListActivity extends AbstractBaseListActivity {

    private ArrayList<GoodsModel> goodsArrayList = new ArrayList<>();

    public static void startFavGoodsListActivity(Context context) {
        Intent intent = new Intent(context, FavGoodsListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setTitle() {
        title.setText(R.string.fav_goods_list);
    }

    @Override
    protected BaseAdapter getCustomAdapter() {
        return new FavGoodsAdapter();
    }

    @Override
    protected void refreshData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goodsArrayList.clear();
//                goodsArrayList.addAll(HomeFragment.mGoodsListTest);
                refreshFrame.refreshComplete();
                mAdapter.notifyDataSetChanged();
                loadMoreListViewContainer.setAutoLoadMore(true);
                loadMoreListViewContainer.loadMoreFinish(goodsArrayList.isEmpty(), true);
            }
        }, 2000);
    }

    @Override
    protected void loadMoreData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // load more complete
//                goodsArrayList.addAll(HomeFragment.mGoodsListTest);
                refreshFrame.refreshComplete();
                loadMoreListViewContainer.loadMoreFinish(goodsArrayList.isEmpty(), true);
                mAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    public class FavGoodsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return goodsArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return goodsArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(FavGoodsListActivity.this).inflate(R.layout.item_fav_goods, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            GoodsModel goods = (GoodsModel) getItem(position);
//            viewHolder.itemTitle.setText(goods.goodsTitle);
//            viewHolder.itemDesc.setText(goods.goodsDesc);
//            viewHolder.oldPrice.setText(goods.goodsOriginPrice);
//            viewHolder.realPrice.setText(goods.goodsNewPrice);
//            viewHolder.buys.setText(goods.salesNum);

            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.imageView)
            ImageView imageView;
            @BindView(R.id.item_title)
            TextView itemTitle;
            @BindView(R.id.item_desc)
            TextView itemDesc;
            @BindView(R.id.real_price)
            TextView realPrice;
            @BindView(R.id.old_price)
            TextView oldPrice;
            @BindView(R.id.buys)
            TextView buys;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
