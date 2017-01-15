package com.shihuo.shihuo.Activities.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.R;

/**
 * Created by cm_qiujiaheng on 2017/1/16.
 * 提现历史记录界面
 */

public class ReflectHistoryActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ReflectHistoryActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reflect_history);

    }

    @Override
    public void initViews() {

    }
}
