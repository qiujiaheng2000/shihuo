
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
import android.widget.Toast;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
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
 * Created by cm_qiujiaheng on 2016/11/13. 登录界面
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

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.layout_login);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.login);
    }

    @OnClick({
            R.id.imag_left, R.id.btn_forget_pass, R.id.btn_regist, R.id.btn_login
    })
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
                login();
                break;
        }
    }

    private void login() {
        String username = editCustomerName.getText().toString();
        String password = editPassword.getText().toString();

        if (TextUtils.isEmpty(username)) {
            AppUtils.showToast(LoginActivity.this, getString(R.string.error_nickname));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            AppUtils.showToast(LoginActivity.this, getString(R.string.error_input_pass));
            return;
        }
        try {
            JSONObject params = new JSONObject();
            params.put("phoneNum", username);
            params.put("password", password);
            OkHttpUtils
                    .postString()
                    .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_LOGIN))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(params.toString())
                    .build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                AppShareUitl.saveUserInfo(LoginActivity.this, response.data);
                                finish();
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT)
                                        .show();
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
}
