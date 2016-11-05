package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/4.
 * 绑定手机界面
 */

public class MobileBindActivity extends BaseActivity {


    @BindView(R.id.leftbtn)
    Button leftbtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit_mobile_number)
    EditText editMobileNumber;
    @BindView(R.id.edit_verify)
    EditText editVerify;
    @BindView(R.id.btn_verify)
    Button btnVerify;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    public static void startMobileBindActivity(Context context) {
        Intent intent = new Intent(context, MobileBindActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mobile_bind);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        title.setText(R.string.change_mobile);
        leftbtn.setText(R.string.back);
        leftbtn.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.leftbtn, R.id.btn_verify, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftbtn:
                finish();
                break;
            case R.id.btn_verify:
                Toast.makeText(this, "获取验证码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_commit:
                Toast.makeText(this, "提交修改", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
