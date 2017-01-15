package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.SettingUtil;
import com.shihuo.shihuo.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 同城配送
 * Created by lishuai on 17/1/14.
 */

public class WuliuActivity extends BaseActivity {

    @BindView(R.id.imag_left)
    ImageView imagLeft;

    @BindView(R.id.title)
    TextView title;

    public static void start(Context context) {
        Intent intent = new Intent(context, WuliuActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuliu);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        title.setText("同城配送");
    }

    @OnClick({
            R.id.imag_left, R.id.layout_service_phone, R.id.layout_complaint_phone, R.id.layout_html_1, R.id.layout_html_2
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.layout_service_phone:
                AppUtils.callPhone(WuliuActivity.this, "0359-6363375");
                break;
            case R.id.layout_complaint_phone:
                AppUtils.callPhone(WuliuActivity.this, "18634598543");
                break;
            case R.id.layout_html_1:
                WebViewActivity.start(WuliuActivity.this, SettingUtil.URL_ZHIFUSHUOMING);
                break;
            case R.id.layout_html_2:
                WebViewActivity.start(WuliuActivity.this, SettingUtil.URL_PEISONGHETONG);
                break;
        }
    }
}
