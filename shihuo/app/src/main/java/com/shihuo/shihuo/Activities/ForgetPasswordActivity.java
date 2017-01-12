package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

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
    @BindView(R.id.btn_get_verfiy_code)
    TextView btn_get_verfiy_code;
    private TimeCount mTimer;

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
        mTimer = new TimeCount(60000, 1000);
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        String phone = editPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            AppUtils.showToast(ForgetPasswordActivity.this, "手机号不能为空");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phoneNum", phone);

            OkHttpUtils
                    .postString()
                    .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_VERIFY_CODE))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(jsonObject.toString())
                    .build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                AppUtils.showToast(ForgetPasswordActivity.this,
                                        getResources().getString(R.string.toast_verify_code));
                                mTimer.start();
                            } else {
                                AppUtils.showToast(ForgetPasswordActivity.this, response.msg);
                            }
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void request(){
        String phone = editPhoneNumber.getText().toString().trim();
        String code = editVerify.getText().toString().trim();
        String password = editNewPass.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            AppUtils.showToast(ForgetPasswordActivity.this, "手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            AppUtils.showToast(ForgetPasswordActivity.this, "验证码不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            AppUtils.showToast(ForgetPasswordActivity.this, "密码不能为空");
            return;
        }
        if (password.length() < 6) {
            AppUtils.showToast(ForgetPasswordActivity.this, "密码不能少于6位");
            return;
        }

        try {
            JSONObject params = new JSONObject();
            params.put("phoneNum", phone);
            params.put("verifyCode", code);
            params.put("password", password);
            OkHttpUtils
                    .postString()
                    .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_FORGET_PASSWORD_POST))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(params.toString())
                    .build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                AppUtils.showToast(ForgetPasswordActivity.this, "密码修改成功");
                                finish();
                            } else {
                                AppUtils.showToast(ForgetPasswordActivity.this, response.msg);
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


    @OnClick({R.id.imag_left, R.id.btn_get_verfiy_code, R.id.btn_completed})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_get_verfiy_code:
                getVerifyCode();
                break;
            case R.id.btn_completed:
                request();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null)
            mTimer.cancel();
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            btn_get_verfiy_code.setText(getResources().getString(R.string.get_verify));
            btn_get_verfiy_code.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            btn_get_verfiy_code.setClickable(false);
            btn_get_verfiy_code.setText(millisUntilFinished / 1000
                    + getResources().getString(R.string.phone_code));
        }
    }
}
