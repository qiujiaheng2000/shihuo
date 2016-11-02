package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.fragments.ServiceFragment;
import com.shihuo.shihuo.fragments.VideoFragment;
import com.shihuo.shihuo.models.VideoModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cm_qiujiaheng on 2016/11/3.
 * 微视频收藏界面
 */

public class FavVideoListActivity extends AbstractBaseListActivity {

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
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                videoModelArrayList.clear();
                videoModelArrayList.addAll(VideoFragment.testVideoModels);
                refreshFrame.refreshComplete();
                mAdapter.notifyDataSetChanged();
                loadMoreListViewContainer.setAutoLoadMore(true);
                loadMoreListViewContainer.loadMoreFinish(videoModelArrayList.isEmpty(), true);
            }
        }, 2000);
    }

    @Override
    protected void loadMoreData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // load more complete
                videoModelArrayList.addAll(VideoFragment.testVideoModels);
                refreshFrame.refreshComplete();
                loadMoreListViewContainer.loadMoreFinish(videoModelArrayList.isEmpty(), true);
                mAdapter.notifyDataSetChanged();
            }
        }, 2000);
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
                convertView = LayoutInflater.from(FavVideoListActivity.this).inflate(R.layout.fav_videos_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            VideoModel videoModel = (VideoModel) getItem(position);
            viewHolder.itemTitle.setText(videoModel.videoTitle);
            viewHolder.itemDesc.setText(videoModel.videoDesc);
            viewHolder.prefixNumbs.setText("浏览次数：");
            viewHolder.numbs.setText(videoModel.videoNumbs);
            viewHolder.date.setText(videoModel.videoDate);

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
