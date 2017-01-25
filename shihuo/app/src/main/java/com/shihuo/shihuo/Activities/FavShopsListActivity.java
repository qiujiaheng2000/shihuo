package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.ShopsModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2016/11/3.
 * 店铺收藏界面
 */

public class FavShopsListActivity extends AbstractBaseListActivity {

    private List<ShopsModel> shopsModelArrayList = new ArrayList<>();


    public static void startFavShopsListActivity(Context context) {
        Intent intent = new Intent(context, FavShopsListActivity.class);
        context.startActivity(intent);
    }

    private int pageNum;

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
        request(true);
    }

    @Override
    protected void loadMoreData() {
        request(false);
    }

    private void request(final boolean isRefresh) {
        if (isRefresh) {
            pageNum = 0;
        }
        String url = NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_STORES_FAV_LIST) + "?token="
                + AppShareUitl.getToken(FavShopsListActivity.this) + "&pageNum=" + pageNum;
        try {
            OkHttpUtils.get().url(url).build().execute(new ShihuoStringCallback() {
                @Override
                public void onResponse(ShiHuoResponse response, int id) {
                    if (response.code == ShiHuoResponse.SUCCESS
                            && !TextUtils.isEmpty(response.resultList)) {
                        shopsModelArrayList = ShopsModel.parseStrJson(response.resultList);
                        if (isRefresh) {
                            refreshFrame.refreshComplete();
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    refreshFrame.refreshComplete();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            viewHolder.itemTitle.setText(shopsModel.storeName);
            viewHolder.itemDesc.setText(shopsModel.storeDetail);
//            viewHolder.prefixNumbs.setText("销量：" + shopsModel.orderNum);
//            viewHolder.numbs.setText("" + shopsModel.orderNum);
            viewHolder.shopAdd.setText(shopsModel.favTime);
            viewHolder.imageView.setImageURI(AppUtils.parse(AliyunHelper.getFullPathByName(shopsModel.imgUrl)));//0018ae25-cefa-4260-8f4f-926920c3aa1f.jpeg
            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.imageView)
            SimpleDraweeView imageView;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ShopsModel itemAtPosition = (ShopsModel) parent.getItemAtPosition(position);
        ShopHomeActivity.start(this, itemAtPosition.storeId);
    }
}
