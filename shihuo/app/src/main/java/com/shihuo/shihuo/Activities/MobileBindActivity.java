package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/4.
 * 绑定手机界面
 */

public class MobileBindActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit_mobile_number)
    EditText editMobileNumber;
    @BindView(R.id.edit_verify)
    EditText editVerify;
    @BindView(R.id.imag_left)
    ImageView imagLeft;


    public static void startMobileBindActivity(Context context) {
        Intent intent = new Intent(context, MobileBindActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.layout_mobile_bind);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        title.setText(R.string.change_mobile);
        imagLeft.setVisibility(View.VISIBLE);

    }


    @OnClick({R.id.imag_left, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_commit:
                break;
        }
    }
}
