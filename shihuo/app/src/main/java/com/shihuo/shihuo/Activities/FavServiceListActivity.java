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
import com.shihuo.shihuo.fragments.ServiceFragment;
import com.shihuo.shihuo.models.ServiceModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cm_qiujiaheng on 2016/11/3.
 * 微视频收藏界面
 */

public class FavServiceListActivity extends AbstractBaseListActivity {

    private ArrayList<ServiceModel> serviceModelArrayList = new ArrayList<>();


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
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                serviceModelArrayList.clear();
                serviceModelArrayList.addAll(ServiceFragment.testServiceModels);
                refreshFrame.refreshComplete();
                mAdapter.notifyDataSetChanged();
                loadMoreListViewContainer.setAutoLoadMore(true);
                loadMoreListViewContainer.loadMoreFinish(serviceModelArrayList.isEmpty(), true);
            }
        }, 2000);
    }

    @Override
    protected void loadMoreData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // load more complete
                serviceModelArrayList.addAll(ServiceFragment.testServiceModels);
                refreshFrame.refreshComplete();
                loadMoreListViewContainer.loadMoreFinish(serviceModelArrayList.isEmpty(), true);
                mAdapter.notifyDataSetChanged();
            }
        }, 2000);
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
            viewHolder.itemTitle.setText(serviceModel.serviceTitle);
            viewHolder.itemDesc.setText(serviceModel.serviceDesc);
            viewHolder.prefixNumbs.setText("浏览次数：");
            viewHolder.numbs.setText(serviceModel.serviceNumbs);
            viewHolder.date.setText(serviceModel.serviceDate);

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
            @BindView(R.id.date)
            TextView date;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
