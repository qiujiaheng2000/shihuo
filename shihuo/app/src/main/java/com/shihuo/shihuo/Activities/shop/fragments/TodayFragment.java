package com.shihuo.shihuo.Activities.shop.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.Activities.shop.models.StatisticsDayModel;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.fragments.BaseFragment;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.DateHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2016/12/13.
 * 今日统计界面
 */

public class TodayFragment extends BaseFragment {
    @BindView(R.id.statistics_date)
    TextView statisticsDate;
    @BindView(R.id.count)
    TextView count;
    @BindView(R.id.total_amount)
    TextView totalAmount;

    public static TodayFragment newInstance() {

        Bundle args = new Bundle();

        TodayFragment fragment = new TodayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_today, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        getDatas();
    }

    private void getDatas() {
        ((BaseActivity) getActivity()).showProgressDialog();
        //当前当日统计信息
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_STATISTICAL_DAY))
                .addParams("token", AppShareUitl.getToken(getActivity()))
                .addParams("storeId", AppShareUitl.getUserInfo(getActivity()).storeId)
                .addParams("date", DateHelper.getStringDateDay())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        ((BaseActivity) getActivity()).hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                if (!TextUtils.isEmpty(response.data)) {
                                    JSONObject jsonObject = new JSONObject(response.data);
                                    setViewData(jsonObject);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getActivity(), response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ((BaseActivity) getActivity()).hideProgressDialog();
                        AppUtils.showToast(getActivity(), "获取当日统计信息出错");
                    }
                });
    }

    private void setViewData(JSONObject jsonObject) throws JSONException {
        StatisticsDayModel statisticsDayModel = StatisticsDayModel.parseFromJson(jsonObject.toString());
        statisticsDate.setText(String.format("%1$s 数据统计", statisticsDayModel.date));
        count.setText(String.format("今日订单量 %1$s单", "" + statisticsDayModel.ordersCnt));
        totalAmount.setText(String.format("今日交易金额 %1$s 元", "" + statisticsDayModel.totalAmount));
    }
}
