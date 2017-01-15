package com.shihuo.shihuo.Activities.shop.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shihuo.shihuo.Activities.shop.models.StatisticsDayModel;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.Views.loadmore.LoadMoreListViewContainer;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.fragments.BaseFragment;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.DateHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2016/12/13.
 * 今年统计界面
 */

public class YearFragment extends BaseFragment {

    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.load_more_list_view_container)
    LoadMoreListViewContainer loadMoreListViewContainer;
    @BindView(R.id.refresh_frame)
    PtrClassicFrameLayout refreshFrame;

    private List<StatisticsDayModel> statisticsDayModels = new ArrayList<>();

    private BaseAdapter mAdapter;

    public static YearFragment newInstance() {
        Bundle args = new Bundle();
        YearFragment fragment = new YearFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_year, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {

        refreshFrame.setLoadingMinTime(1000);
        refreshFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getDatas();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listView, header);
            }
        });

        mAdapter = new MyStatisticsAdapter();


        loadMoreListViewContainer.setAutoLoadMore(false);
        loadMoreListViewContainer.useDefaultFooter();


        listView.setAdapter(mAdapter);

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }

        });
        refreshFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshFrame.autoRefresh();
            }
        }, 100);


    }

    private void getDatas() {
        //当前当月统计信息
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_STATISTICAL_YEAR))
                .addParams("token", AppShareUitl.getToken(getActivity()))
                .addParams("storeId", AppShareUitl.getUserInfo(getActivity()).storeId)
                .addParams("date", DateHelper.getStringDateYear())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        refreshFrame.refreshComplete();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                if (!TextUtils.isEmpty(response.data)) {
                                    JSONObject jsonObject = new JSONObject(response.data);
                                    if (!TextUtils.isEmpty(jsonObject.getString("dataList"))) {
                                        JSONArray jsonArray = jsonObject.getJSONArray("dataList");
                                        statisticsDayModels.clear();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            StatisticsDayModel statisticsDayModel = StatisticsDayModel.parseFromJson(jsonArray.getJSONObject(i).toString());
                                            statisticsDayModels.add(statisticsDayModel);
                                        }
                                        mAdapter.notifyDataSetChanged();
                                        loadMoreListViewContainer.setAutoLoadMore(true);
                                        loadMoreListViewContainer.loadMoreFinish(statisticsDayModels.isEmpty(), true);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getActivity(), response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        refreshFrame.refreshComplete();
                        AppUtils.showToast(getActivity(), "获取当年统计列表出错");
                    }
                });
    }

    class MyStatisticsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return statisticsDayModels.size();
        }

        @Override
        public Object getItem(int position) {
            return statisticsDayModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_statistics, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            final StatisticsDayModel statisticsDayModel = (StatisticsDayModel) getItem(position);
            viewHolder.itemOrderCount.setText(String.format("订单量 %1$s", "" + statisticsDayModel.ordersCnt));
            viewHolder.itemTotalAmount.setText(String.format("交易金额 %1$s", "" + statisticsDayModel.totalAmount));
            viewHolder.itemDate.setText(statisticsDayModel.date);
            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.item_order_count)
            TextView itemOrderCount;
            @BindView(R.id.item_total_amount)
            TextView itemTotalAmount;
            @BindView(R.id.item_date)
            TextView itemDate;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
