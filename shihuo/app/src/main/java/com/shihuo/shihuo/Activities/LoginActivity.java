package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/13.
 * 登录界面
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.edit_customer_name)
    EditText editCustomerName;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;

    public static void startLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.login);
    }


    @OnClick({R.id.imag_left, R.id.btn_forget_pass, R.id.btn_regist, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_forget_pass:
                ForgetPasswordActivity.startForgetPasswordActivity(this);
                break;
            case R.id.btn_regist:
                RegisterActivity.startForgetPasswordActivity(this);
                break;
            case R.id.btn_login:
                break;
        }
    }
}
