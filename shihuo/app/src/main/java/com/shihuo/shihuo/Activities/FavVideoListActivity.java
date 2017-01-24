
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.VideoModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2016/11/3. 微视频收藏界面
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
            videoModelArrayList.clear();
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
                                VideoModel serviceModel = VideoModel.parseFromJsonStr(array.get(i)
                                        .toString());
                                videoModelArrayList.add(serviceModel);
                            }
                            loadMoreListViewContainer.setAutoLoadMore(true);
                            loadMoreListViewContainer.loadMoreFinish(array.length() > 0,
                                    true);
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(FavVideoListActivity.this).inflate(
                        R.layout.videos_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            final VideoModel videoModel = (VideoModel) getItem(position);
            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoPlayActivity.start(FavVideoListActivity.this, videoModel.videoUrl,
                            videoModel.isFav, videoModel.mId);
                }
            });
            viewHolder.imageView
                    .setImageURI(AppUtils.parse(Contants.IMAGE_URL + videoModel.imgUrl));
            viewHolder.itemTitle.setText(TextUtils.isEmpty(videoModel.mName) ? ""
                    : videoModel.mName);
            viewHolder.itemDesc.setText(TextUtils.isEmpty(videoModel.mDetail) ? ""
                    : videoModel.mDetail);
            viewHolder.numbs.setText(videoModel.browseNum + "");
            viewHolder.date.setText(TextUtils.isEmpty(videoModel.createTime) ? ""
                    : videoModel.createTime);

            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.layout)
            RelativeLayout layout;

            @BindView(R.id.imageView)
            SimpleDraweeView imageView;

            @BindView(R.id.imageView_arrow)
            ImageView imageViewArrow;

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

            @BindView(R.id.detail_layout)
            LinearLayout detailLayout;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
