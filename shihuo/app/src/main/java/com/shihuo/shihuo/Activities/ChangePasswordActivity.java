
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by cm_qiujiaheng on 2016/11/4. 修改密码界面
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

    public static void start(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.layout_changepassword);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        title.setText(R.string.change_password);
        leftbtn.setVisibility(View.VISIBLE);

    }

    private void fixPassword() {
        String oldPassword = editOldPass.getText().toString().trim();
        String newPassword = editNewPass.getText().toString().trim();
        String new2Password = editCheckNewPass.getText().toString().trim();
        if (TextUtils.isEmpty(oldPassword)) {
            editOldPass.setError(getString(R.string.error_input_pass));
            return;
        }
        if (TextUtils.isEmpty(newPassword)) {
            editNewPass.setError(getString(R.string.error_input_pass));
            return;
        }
        if (TextUtils.isEmpty(new2Password)) {
            editCheckNewPass.setError(getString(R.string.error_input_pass));
            return;
        }
        if (!newPassword.equals(new2Password)) {
            editCheckNewPass.setError(getString(R.string.error_correct_verify));
            return;
        }
        try {
            JSONObject params = new JSONObject();
            params.put("oldPassword", oldPassword);
            params.put("newPassword", newPassword);
            OkHttpUtils.postString().url(NetWorkHelper.getApiUrl(NetWorkHelper.API_FIX_PASSWORD))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(params.toString()).build().execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                AppUtils.showToast(ChangePasswordActivity.this, getResources()
                                        .getString(R.string.fix_password));
                                finish();
                            } else {
                                AppUtils.showToast(ChangePasswordActivity.this, response.msg);
                            }
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({
            R.id.imag_left, R.id.btn_commit
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_commit:
                fixPassword();
                break;
        }
    }
}
