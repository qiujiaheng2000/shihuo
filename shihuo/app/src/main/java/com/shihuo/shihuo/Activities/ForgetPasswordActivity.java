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
 * Created by cm_qiujiaheng on 2016/11/15.
 * 忘记密码界面
 */

public class ForgetPasswordActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit_phone_number)
    EditText editPhoneNumber;
    @BindView(R.id.edit_verify)
    EditText editVerify;
    @BindView(R.id.edit_new_pass)
    EditText editNewPass;
    @BindView(R.id.imag_left)
    ImageView imagLeft;

    public static void startForgetPasswordActivity(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.layout_forget_password);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.reset_pass);
    }


    @OnClick({R.id.imag_left, R.id.btn_get_verfiy_code, R.id.btn_completed})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_get_verfiy_code:
                break;
            case R.id.btn_completed:
                break;
        }
    }
}
