package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/2.
 * 设置界面
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.leftbtn)
    Button leftbtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.layout_change_pass)
    RelativeLayout layoutChangePass;
    @BindView(R.id.layout_bind_mobile)
    RelativeLayout layoutBindMobile;
    @BindView(R.id.layout_push_switch)
    RelativeLayout layoutPushSwitch;
    @BindView(R.id.rightbtn)
    Button rightbtn;
    @BindView(R.id.change_pass_icon)
    ImageView changePassIcon;
    @BindView(R.id.change_pass_item)
    TextView changePassItem;
    @BindView(R.id.imageView_arrow_change_pass)
    ImageView imageViewArrowChangePass;
    @BindView(R.id.bind_mobile_icon)
    ImageView bindMobileIcon;
    @BindView(R.id.bind_mobile_item)
    TextView bindMobileItem;
    @BindView(R.id.imageView_arrow_bind_mobile)
    ImageView imageViewArrowBindMobile;
    @BindView(R.id.push_switch_icon)
    ImageView pushSwitchIcon;
    @BindView(R.id.push_switch_item)
    TextView pushSwitchItem;
    @BindView(R.id.imageView_arrow_push_switch)
    ImageView imageViewArrowPushSwitch;
    @BindView(R.id.logout)
    Button logout;

    public static void startSettingActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        title.setText(R.string.setting);
        leftbtn.setVisibility(View.VISIBLE);
        leftbtn.setText(R.string.back);
    }


    @OnClick({R.id.leftbtn, R.id.layout_change_pass, R.id.layout_bind_mobile, R.id.layout_push_switch, R.id.logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftbtn:
                finish();
                break;
            case R.id.layout_change_pass:
                break;
            case R.id.layout_bind_mobile:
                break;
            case R.id.layout_push_switch:
                break;
            case R.id.logout:
                break;
        }
    }
}
