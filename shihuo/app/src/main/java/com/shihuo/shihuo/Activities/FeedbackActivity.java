package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.MyAddressModel;
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
 * Created by cm_qiujiaheng on 2016/11/4.
 * 意见反馈界面
 */

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.imag_left)
    ImageView leftbtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rightbtn)
    Button rightbtn;
    @BindView(R.id.edit_feedback)
    EditText editFeedback;
    @BindView(R.id.btn_feedback)
    Button btnFeedback;

    public static void stardFeedbackActivity(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.layout_feedback);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        title.setText(R.string.feedback_title);
        leftbtn.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.imag_left, R.id.btn_feedback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_feedback:
                feedBack();
                break;
        }
    }

    private void feedBack() {

        if (TextUtils.isEmpty(editFeedback.getText().toString())) {
            AppUtils.showToast(this, "请输入您的反馈意见");
            return;
        }
        showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("content", editFeedback.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_FEEDBACK) + "?token=" + AppShareUitl.getToken(FeedbackActivity.this))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            AppUtils.showToast(FeedbackActivity.this, "反馈成功");
                            finish();
                        } else {
                            AppUtils.showToast(FeedbackActivity.this, response.msg);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                    }
                });
    }
}
