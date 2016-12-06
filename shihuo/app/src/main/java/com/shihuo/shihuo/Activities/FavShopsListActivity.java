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
import com.shihuo.shihuo.models.ShopsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cm_qiujiaheng on 2016/11/3.
 * 店铺收藏界面
 */

public class FavShopsListActivity extends AbstractBaseListActivity {

    private ArrayList<ShopsModel> shopsModelArrayList = new ArrayList<>();


    public static void startFavShopsListActivity(Context context) {
        Intent intent = new Intent(context, FavShopsListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setTitle() {
        title.setText(R.string.fav_shops_list);
    }

    @Override
    protected BaseAdapter getCustomAdapter() {
        return new FavShopsAdapter();
    }

    @Override
    protected void refreshData() {

    }

    @Override
    protected void loadMoreData() {

    }

    public class FavShopsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return shopsModelArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return shopsModelArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(FavShopsListActivity.this).inflate(R.layout.item_fav_shops, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            ShopsModel shopsModel = (ShopsModel) getItem(position);
            viewHolder.itemTitle.setText(shopsModel.shopName);
            viewHolder.itemDesc.setText(shopsModel.shopDesc);
            viewHolder.prefixNumbs.setText("销量");
            viewHolder.numbs.setText(shopsModel.shopSales);
            viewHolder.shopAdd.setText(shopsModel.shopAdd);

            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.imageView)
            ImageView imageView;
            @BindView(R.id.item_title)
            TextView itemTitle;
            @BindView(R.id.item_desc)
            TextView itemDesc;
            @BindView(R.id.prefix_numbs)
            TextView prefixNumbs;
            @BindView(R.id.numbs)
            TextView numbs;
            @BindView(R.id.shop_add)
            TextView shopAdd;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
