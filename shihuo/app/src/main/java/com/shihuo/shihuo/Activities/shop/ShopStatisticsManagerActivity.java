package com.shihuo.shihuo.Activities.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.R;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 * 店铺统计管理界面
 */
public class ShopStatisticsManagerActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ShopStatisticsManagerActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_publishgoods);
        initViews();
    }

    @Override
    public void initViews() {

    }
}
