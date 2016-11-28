package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shihuo.shihuo.R;

/**
 * Created by cm_qiujiaheng on 2016/11/27.
 * 店铺入驻界面
 */

public class ShopsLocatedActivity extends BaseActivity {


    public static void startShopsLocatedActivity(Context context) {
        Intent intent = new Intent(context, ShopsLocatedActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shoplocated);
    }

    @Override
    public void initViews() {

    }
}
