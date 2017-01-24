
package com.shihuo.shihuo.Activities.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.Views.loadmore.LoadMoreListViewContainer;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.ReflectHistoryModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2017/1/16. 提现历史记录界面
 */

public class ReflectHistoryActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public List<ReflectHistoryModel> reflectHistoryModels = new ArrayList<>();

    @BindView(R.id.imag_left)
    ImageView imagLeft;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.total_amount)
    TextView totalAmount;

    @BindView(R.id.list_view)
    ListView listView;

    @BindView(R.id.load_more_list_view_container)
    LoadMoreListViewContainer loadMoreListViewContainer;

    @BindView(R.id.refresh_frame)
    PtrClassicFrameLayout refreshFrame;

    private int mPageNum;

    private BaseAdapter mAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, ReflectHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reflect_history);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        title.setText("提现记录");
        imagLeft.setVisibility(View.VISIBLE);
        refreshFrame.setLoadingMinTime(1000);
        refreshFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listView, header);
            }
        });

        mAdapter = new ReflectAdapter();

        loadMoreListViewContainer.setAutoLoadMore(false);
        loadMoreListViewContainer.useDefaultFooter();

        listView.setAdapter(mAdapter);

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                loadMoreData();
            }
        });

        listView.setOnItemClickListener(this);
        refreshFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshFrame.autoRefresh();
            }
        }, 100);
    }

    protected void refreshData() {
        mPageNum = 0;
        reflectHistoryModels.clear();
        getDatas();
    }

    protected void loadMoreData() {
        getDatas();
    }

    private void getDatas() {
        // 当前用户消息中心列表
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_CASH_HISTORY))
                .addParams("token", AppShareUitl.getToken(ReflectHistoryActivity.this))
                .addParams("storeId", AppShareUitl.getUserInfo(ReflectHistoryActivity.this).storeId)
                .addParams("pageNum", String.valueOf(mPageNum)).build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        refreshFrame.refreshComplete();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                mPageNum++;
                                if (!TextUtils.isEmpty(response.data)) {
                                    JSONObject jsonObject = new JSONObject(response.data);
                                    double withdrawTotal = jsonObject.getDouble("withdrawTotal");
                                    totalAmount.setText("你已累计提取了￥" + Math.abs(withdrawTotal) + "元");
                                    jsonObject = jsonObject.getJSONObject("page");
                                    JSONArray jsonArray = null;
                                    if (!TextUtils.isEmpty(jsonObject.getString("resultList"))) {
                                        jsonArray = jsonObject.getJSONArray("resultList");
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            ReflectHistoryModel notifyModel = ReflectHistoryModel
                                                    .parseFromJsonStr(jsonArray.getJSONObject(i)
                                                            .toString());
                                            reflectHistoryModels.add(notifyModel);
                                        }
                                    }
                                    mAdapter.notifyDataSetChanged();
                                    loadMoreListViewContainer.setAutoLoadMore(true);
                                    loadMoreListViewContainer.loadMoreFinish(
                                            jsonArray.length() > 0, true);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Toast.makeText(ReflectHistoryActivity.this,
                            // response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        refreshFrame.refreshComplete();
                        AppUtils.showToast(ReflectHistoryActivity.this, "获取提现历史列表出错");
                    }
                });
    }

    @OnClick(R.id.imag_left)
    public void onClick() {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class ReflectAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return reflectHistoryModels.size();
        }

        @Override
        public Object getItem(int position) {
            return reflectHistoryModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ReflectHistoryActivity.this).inflate(
                        R.layout.item_my_reflecthistory, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            ReflectHistoryModel reflectHistoryModel = (ReflectHistoryModel) getItem(position);
            if (reflectHistoryModel != null) {
                viewHolder.itemAmount.setText("提取金额：￥" + Math.abs(reflectHistoryModel.amount) + "元");
                viewHolder.itemCreatetime.setText(String.format("提取时间：%1$s",
                        reflectHistoryModel.createTime));
                if (reflectHistoryModel.status == 1) {
                    // 已打款
                    viewHolder.tv_status.setText("已打款");
                } else {
                    viewHolder.tv_status.setText("未打款");
                }
            }
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.item_amount)
            TextView itemAmount;

            @BindView(R.id.item_createtime)
            TextView itemCreatetime;

            @BindView(R.id.tv_status)
            TextView tv_status;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
