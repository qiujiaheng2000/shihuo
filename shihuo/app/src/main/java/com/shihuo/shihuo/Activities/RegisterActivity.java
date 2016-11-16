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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/15.
 * 注册用户
 */

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit_nickname)
    EditText editNickname;
    @BindView(R.id.edit_input_pass)
    EditText editInputPass;
    @BindView(R.id.edit_new_pass)
    EditText editNewPass;
    @BindView(R.id.edit_verify)
    EditText editVerify;
    @BindView(R.id.imag_left)
    ImageView imagLeft;

    public static void startForgetPasswordActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.regist_account);
    }


    @OnClick({R.id.imag_left, R.id.btn_get_verfiy_code, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_get_verfiy_code:
                break;
            case R.id.btn_register:
                break;
        }
    }
}
