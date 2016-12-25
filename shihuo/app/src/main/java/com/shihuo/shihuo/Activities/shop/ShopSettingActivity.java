
package com.shihuo.shihuo.Activities.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.Activities.ZxingCreateActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/12/4. 店铺设置界面
 */
public class ShopSettingActivity extends BaseActivity {

    @BindView(R.id.imag_left)
    ImageView imagLeft;

    @BindView(R.id.title)
    TextView title;

    public static void start(Context context) {
        Intent intent = new Intent(context, ShopSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shop_setting);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.shopsetting);
    }

    @OnClick({
            R.id.imag_left, R.id.layout_servicenum, R.id.layout_pic, R.id.layout_shop_desc,
            R.id.layout_notice, R.id.layout_free_of_charge, R.id.layout_delivery_time,
            R.id.layout_business_time, R.id.layout_shop_address
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.layout_servicenum:
                SettingEditActivity.start(this, SettingEditActivity.FLAG_SETTING_CS_NUMBER);
                break;
            case R.id.layout_shop_desc:
                SettingEditActivity.start(this, SettingEditActivity.FLAG_SETTING_SHOP_DESC);
                break;
            case R.id.layout_notice:
                SettingEditActivity.start(this, SettingEditActivity.FLAG_SETTING_SHOP_NOTIC);
                break;
            case R.id.layout_free_of_charge:
                break;
            case R.id.layout_pic:
                AppUtils.showToast(ShopSettingActivity.this, "h5全景");
                break;
            case R.id.layout_delivery_time:
                SettingEditActivity
                        .start(this, SettingEditActivity.FLAG_SETTING_SHOP_DILIVERY_TIME);
                break;
            case R.id.layout_business_time:
                SettingEditActivity
                        .start(this, SettingEditActivity.FLAG_SETTING_SHOP_BUSINESS_TIME);
                break;
            case R.id.layout_shop_address:
                SettingEditActivity.start(this, SettingEditActivity.FLAG_SETTING_SHOP_ADDRESS);
                break;
        }
    }
}
