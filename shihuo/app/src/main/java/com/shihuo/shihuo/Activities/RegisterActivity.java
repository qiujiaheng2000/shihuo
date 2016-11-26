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
 * 注册用户
 */

public class RegisterActivity extends BaseActivity {


    private static final String TAG = RegisterActivity.class.getName();
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
    @BindView(R.id.edit_phone_number)
    EditText editPhoneNumber;

    public static void startForgetPasswordActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
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
                getVerifyCode();
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        String nickName = editNickname.getText().toString();
        String password = editInputPass.getText().toString();
        String aginpassword = editNewPass.getText().toString();
        String phoneNum = editPhoneNumber.getText().toString();
        String verifyCode = editVerify.getText().toString();
        if (TextUtils.isEmpty(nickName)) {
            editNickname.setError(getString(R.string.error_nickname));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editInputPass.setError(getString(R.string.error_input_pass));
            return;
        }
        if (TextUtils.isEmpty(aginpassword)) {
            editNewPass.setError(getString(R.string.error_input_pass));
            return;
        }
        if (!password.equals(aginpassword)) {
            editNewPass.setError(getString(R.string.error_correct_verify));
            return;
        }
        if (TextUtils.isEmpty(phoneNum)) {
            editPhoneNumber.setError(getString(R.string.error_phonenum));
            return;
        }
        if (TextUtils.isEmpty(verifyCode)) {
            editVerify.setError(getString(R.string.error_verify_coode));
            return;
        }
        try {
            JSONObject params = new JSONObject();
            params.put("userName", nickName);
            params.put("password", password);
            params.put("phoneNum", phoneNum);
            params.put("verifyCode", verifyCode);
            OkHttpUtils
                    .postString()
                    .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_REGISTER))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(params.toString())
                    .build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                finish();
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, response.msg, Toast.LENGTH_SHORT).show();
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

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        String phoneNum = editPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            editPhoneNumber.setError(getString(R.string.error_phonenum));
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phoneNum", "18510015257");

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

                            } else {
                                Toast.makeText(RegisterActivity.this, response.msg, Toast.LENGTH_SHORT).show();
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
}
