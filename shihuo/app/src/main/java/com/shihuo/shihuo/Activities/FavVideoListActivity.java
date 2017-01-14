package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.VideoModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
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

public class FavVideoListActivity extends AbstractBaseListActivity {
    private int pageNum;
    private ArrayList<VideoModel> videoModelArrayList = new ArrayList<>();

    public static void startFavVideoListActivity(Context context) {
        Intent intent = new Intent(context, FavVideoListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setTitle() {
        title.setText(R.string.fav_videos_list);
    }

    @Override
    protected BaseAdapter getCustomAdapter() {
        return new FavVideosAdapter();
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
        String url = NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_VIDOE_FAV_LIST) + "?token="
                + AppShareUitl.getToken(FavVideoListActivity.this) + "&pageNum=" + pageNum;
        try {
            OkHttpUtils.get().url(url).build().execute(new ShihuoStringCallback() {
                @Override
                public void onResponse(ShiHuoResponse response, int id) {
                    refreshFrame.refreshComplete();
                    try {
                        if (response.code == ShiHuoResponse.SUCCESS
                                && !TextUtils.isEmpty(response.resultList)) {
                            JSONArray array = new JSONArray(response.resultList);
                            for (int i = 0; i < array.length(); i++) {
                                VideoModel serviceModel = VideoModel.parseFromJsonStr(array.get(i).toString());
                                videoModelArrayList.add(serviceModel);
                            }
                            loadMoreListViewContainer.setAutoLoadMore(true);
                            loadMoreListViewContainer.loadMoreFinish(videoModelArrayList.isEmpty(), true);
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

    public class FavVideosAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return videoModelArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return videoModelArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(FavVideoListActivity.this).inflate(R.layout.item_fav_videos, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            VideoModel videoModel = (VideoModel) getItem(position);
            viewHolder.itemTitle.setText(videoModel.mName);
            viewHolder.itemDesc.setText(videoModel.mDetail);
            viewHolder.prefixNumbs.setText("浏览次数：");
            viewHolder.numbs.setText(videoModel.browseNum);
            viewHolder.date.setText(videoModel.createTime);

            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.imageView)
            ImageView imageView;
            @BindView(R.id.enjoy_btn)
            Button enjoyBtn;
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
}
