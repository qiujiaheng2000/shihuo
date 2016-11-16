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
import android.widget.Toast;

import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/4.
 * 修改密码界面
 */

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.imag_left)
    ImageView leftbtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit_old_pass)
    EditText editOldPass;
    @BindView(R.id.edit_new_pass)
    EditText editNewPass;
    @BindView(R.id.edit_check_new_pass)
    EditText editCheckNewPass;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_forget_pass)
    TextView btnForgetPass;

    public static void startChangePasswordActivity(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_changepassword);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        title.setText(R.string.change_password);
        leftbtn.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.imag_left, R.id.btn_commit, R.id.btn_forget_pass})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_commit:
                Toast.makeText(this, "提交修改~！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_forget_pass:
                Toast.makeText(this, "忘记密码~！", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
