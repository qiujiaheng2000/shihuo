package com.shihuo.shihuo.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.dpizarro.autolabel.library.Label;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.VideoAndServiceHeaderView;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.Views.loadmore.LoadMoreListViewContainer;
import com.shihuo.shihuo.models.ServiceModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by jiahengqiu on 2016/10/23.
 * 便民服务
 */
public class ServiceFragment extends BaseFragment {

    @BindView(R.id.leftbtn)
    Button leftbtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rightbtn)
    Button rightbtn;
    @BindView(R.id.rotate_header_list_view)
    ListView rotateHeaderListView;
    @BindView(R.id.load_more_list_view_container)
    LoadMoreListViewContainer loadMoreListViewContainer;
    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout rotateHeaderListViewFrame;

    private Handler mHandler = new Handler();

    public ArrayList<ServiceModel> serviceModels = new ArrayList<>();

    public static ArrayList<ServiceModel> testServiceModels = new ArrayList<>();

    private MyListViewAdapter mAdapter;

    static {
        for (int i = 0; i < 15; i++) {
            testServiceModels.add(new ServiceModel("" + i, "imageurl+" + i, "视频标题", "视频的介绍，这是一个恶心的视频，欢迎收看！", "1873" + i, "2016-10-30", "videoUrl"));
        }
    }

    public static ServiceFragment newInstance() {
        ServiceFragment frament = new ServiceFragment();
        Bundle bundle = new Bundle();
        frament.setArguments(bundle);
        return frament;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.service_activity, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        title.setText(R.string.tab_server);
        rotateHeaderListViewFrame.setLoadingMinTime(1000);
        rotateHeaderListViewFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        serviceModels.clear();
                        serviceModels.addAll(testServiceModels);
                        rotateHeaderListViewFrame.refreshComplete();
                        mAdapter.notifyDataSetChanged();
                        loadMoreListViewContainer.setAutoLoadMore(true);
                        loadMoreListViewContainer.loadMoreFinish(serviceModels.isEmpty(), true);
                    }
                }, 2000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, rotateHeaderListView, header);
            }
        });

        mAdapter = new MyListViewAdapter();
        VideoAndServiceHeaderView homeHeaderView = new VideoAndServiceHeaderView(getContext());
        ArrayList<String> labels = new ArrayList<>();
        labels.add("育儿宝典");
        labels.add("小常识");
        labels.add("教育");
        labels.add("游戏");
        labels.add("阅读");
        labels.add("美食");
        labels.add("体育");
        labels.add("爱心");
        labels.add("水电");
        homeHeaderView.addAutoLabels(labels);
        homeHeaderView.setAutolabelTitle(R.string.service_autolabel_title);
        homeHeaderView.setListeners(null, null, null, new AutoLabelUI.OnLabelClickListener() {
            @Override
            public void onClickLabel(View v) {
                Label label = (Label) v;
                Toast.makeText(getContext(), label.getText() + "   tag = " + label.getTag(),Toast.LENGTH_SHORT).show();
            }
        });
        rotateHeaderListView.addHeaderView(homeHeaderView);

        loadMoreListViewContainer.setAutoLoadMore(false);
        loadMoreListViewContainer.useDefaultFooter();
        rotateHeaderListView.setAdapter(mAdapter);
        rotateHeaderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // load more complete
                        rotateHeaderListViewFrame.refreshComplete();
                        serviceModels.addAll(testServiceModels);
                        loadMoreListViewContainer.loadMoreFinish(serviceModels.isEmpty(), true);
                        mAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
        rotateHeaderListViewFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                rotateHeaderListViewFrame.autoRefresh();
            }
        }, 100);
    }
    public class MyListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return serviceModels.size();
        }

        @Override
        public Object getItem(int position) {
            return serviceModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.videos_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            ServiceModel serviceModel = (ServiceModel) getItem(position);
            viewHolder.itemTitle.setText(serviceModel.serviceTitle);
            viewHolder.itemDesc.setText(serviceModel.serviceDesc);
            viewHolder.numbs.setText(serviceModel.serviceNumbs);
            viewHolder.date.setText(serviceModel.serviceDate);
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.imageView)
            ImageView imageView;
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
