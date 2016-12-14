
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.GoodsFavItemView;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsDetailListModel;
import com.shihuo.shihuo.models.GoodsDetailModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2016/11/3. 商品收藏界面
 */

public class FavGoodsListActivity extends AbstractBaseListActivity {

    private List<GoodsDetailModel> mGoodsFavList = new ArrayList<>();

    public static void start(Context context) {
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

    private void request(final boolean isRefresh) {
        if (isRefresh) {
            pageNum = 0;
        }
        String url = NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_GOODS_FAV_LIST) + "?token="
                + AppShareUitl.getToken(FavGoodsListActivity.this) + "&pageNum=" + pageNum;
        try {
            OkHttpUtils.get().url(url).build().execute(new ShihuoStringCallback() {
                @Override
                public void onResponse(ShiHuoResponse response, int id) {
                    if (response.code == ShiHuoResponse.SUCCESS
                            && !TextUtils.isEmpty(response.resultList)) {
                        mGoodsFavList = GoodsDetailListModel.parseStrJson(response.resultList);
                        if(isRefresh){
                            refreshFrame.refreshComplete();
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    hideProgressDialog();
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
            return mGoodsFavList.size();
        }

        @Override
        public Object getItem(int position) {
            return mGoodsFavList.get(position);
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
                        R.layout.view_goods_fav_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder)convertView.getTag();
            viewHolder.view_goods_fav.setData(mGoodsFavList.get(position));
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.view_goods_fav)
            GoodsFavItemView view_goods_fav;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
