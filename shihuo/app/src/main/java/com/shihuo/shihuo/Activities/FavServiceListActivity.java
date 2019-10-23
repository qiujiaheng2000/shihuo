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
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.ServiceModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2016/11/3.
 * 微视频收藏界面
 */

public class FavServiceListActivity extends AbstractBaseListActivity {

    private ArrayList<ServiceModel> serviceModelArrayList = new ArrayList<>();

    private int pageNum = 1;

    public static void startFavServiceListActivity(Context context) {
        Intent intent = new Intent(context, FavServiceListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setTitle() {
        title.setText(R.string.fav_services_list);
    }

    @Override
    protected BaseAdapter getCustomAdapter() {
        return new FavServicesAdapter();
    }

    @Override
    protected void refreshData() {
        request(true);
    }

    @Override
    protected void loadMoreData() {
        pageNum++;
        request(false);
    }


    private void request(final boolean isRefresh) {
        if (isRefresh) {
            pageNum = 1;
            serviceModelArrayList.clear();
        }
        String url = NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_SERVICE_FAV_LIST) + "?token="
                + AppShareUitl.getToken(FavServiceListActivity.this) + "&pageNum=" + pageNum;
        try {
            OkHttpUtils.get().url(url).build().execute(new ShihuoStringCallback() {
                @Override
                public void onResponse(ShiHuoResponse response, int id) {
                    refreshFrame.refreshComplete();
                    try {
                        if (response.code == ShiHuoResponse.SUCCESS
                                && !TextUtils.isEmpty(response.resultList)) {
                            pageNum += 1;
                            JSONArray array = new JSONArray(response.resultList);
                            for (int i = 0; i < array.length(); i++) {
                                ServiceModel serviceModel = ServiceModel.parseFromJsonStr(array.get(i).toString());
                                serviceModelArrayList.add(serviceModel);
                            }
                            loadMoreListViewContainer.setAutoLoadMore(true);
                            loadMoreListViewContainer.loadMoreFinish(array.length() > 0, true);
                            mAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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

    public class FavServicesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return serviceModelArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return serviceModelArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(FavServiceListActivity.this).inflate(R.layout.item_fav_services, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            ServiceModel serviceModel = (ServiceModel) getItem(position);
            viewHolder.itemTitle.setText(serviceModel.cName);
            viewHolder.itemDesc.setText(serviceModel.cDetail);
            viewHolder.prefixNumbs.setText("收藏时间：");
            viewHolder.numbs.setText(serviceModel.favTime + "");
            viewHolder.date.setText(serviceModel.createTime);
            viewHolder.imageView.setImageURI(AppUtils.parse(AliyunHelper.getFullPathByName(serviceModel.imgUrl)));

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
            @BindView(R.id.date)
            TextView date;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ServiceModel itemAtPosition = (ServiceModel) parent.getItemAtPosition(position);
        if(itemAtPosition != null && !TextUtils.isEmpty(itemAtPosition.linkUrl)){
            WebViewServiceActivity.start(FavServiceListActivity.this,
                    Contants.IMAGE_URL + itemAtPosition.linkUrl, itemAtPosition.cId);
        }
    }
}
