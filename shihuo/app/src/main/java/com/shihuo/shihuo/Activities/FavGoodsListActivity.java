
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
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2016/11/3. 商品收藏界面
 */

public class FavGoodsListActivity extends AbstractBaseListActivity {

    private ArrayList<GoodsModel> goodsArrayList = new ArrayList<>();

    public static void startFavGoodsListActivity(Context context) {
        Intent intent = new Intent(context, FavGoodsListActivity.class);
        context.startActivity(intent);
    }

    private int pageNum;

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
        request(true);
    }

    private void request(boolean isRefresh) {
        if (isRefresh) {
            pageNum = 0;
        }
        String url = NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_GOODS_FAV_LIST) + "?token="
                + AppShareUitl.getToken(FavGoodsListActivity.this) + "&pageNum=" + pageNum;
        try {
            OkHttpUtils
                    .get()
                    .url(url)
                    .build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            if (response.code == ShiHuoResponse.SUCCESS) {
                            }
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadMoreData() {
        request(false);
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
                convertView = LayoutInflater.from(FavGoodsListActivity.this).inflate(
                        R.layout.item_fav_goods, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder)convertView.getTag();
            GoodsModel goods = (GoodsModel)getItem(position);
            // viewHolder.itemTitle.setText(goods.goodsTitle);
            // viewHolder.itemDesc.setText(goods.goodsDesc);
            // viewHolder.oldPrice.setText(goods.goodsOriginPrice);
            // viewHolder.realPrice.setText(goods.goodsNewPrice);
            // viewHolder.buys.setText(goods.salesNum);

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
