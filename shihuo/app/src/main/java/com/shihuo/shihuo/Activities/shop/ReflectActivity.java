
package com.shihuo.shihuo.Activities.shop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.Activities.shop.views.ReflectDialog;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
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
 * Created by cm_qiujiaheng on 2017/1/16. 提现界面
 */

public class ReflectActivity extends BaseActivity {

    @BindView(R.id.imag_left)
    ImageView imagLeft;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.txBtnRight)
    TextView txBtnRight;

    @BindView(R.id.btn_commit)
    Button btnCommit;

    @BindView(R.id.reflect_total)
    TextView reflectTotal;

    @BindView(R.id.text_warging)
    TextView textWarging;

    public static void start(Context context) {
        Intent intent = new Intent(context, ReflectActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reflect);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        txBtnRight.setVisibility(View.VISIBLE);
        txBtnRight.setText("提现记录");
        title.setText("提现");
    }

    @OnClick({
            R.id.imag_left, R.id.txBtnRight, R.id.btn_commit
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.txBtnRight:
                ReflectHistoryActivity.start(ReflectActivity.this);
                break;
            case R.id.btn_commit:
                reflect();
                break;
        }
    }

    private void reflect() {
        ReflectDialog shopTypeChangeDialog = new ReflectDialog(ReflectActivity.this,
                R.style.CustomDialog).setTitle("申请提现").setHintText("请输入要提取的金额");
        shopTypeChangeDialog.setCustomCallback(new ReflectDialog.CustomCallback() {
            @Override
            public void onOkClick(Dialog dialog, String reflectCount) {
                dialog.dismiss();
                commitReflect(reflectCount);
            }
        });
        shopTypeChangeDialog.show();
    }

    /**
     * 申请提现
     */
    private void commitReflect(String reflectCount) {
        showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("storeId", AppShareUitl.getUserInfo(this).storeId);
            params.put("withdrawAmount", reflectCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_CASH) + "?token="
                        + AppShareUitl.getUserInfo(this).token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString()).build().execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {

                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDatas();

    }

    /**
     * 获取可提现金额
     */
    private void getDatas() {
        showProgressDialog();
        OkHttpUtils.get().url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_CASH))
                .addParams("token", AppShareUitl.getToken(ReflectActivity.this))
                .addParams("storeId", AppShareUitl.getUserInfo(ReflectActivity.this).storeId)
                .build().execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                if (!TextUtils.isEmpty(response.data)) {
                                    JSONObject jsonObject = new JSONObject(response.data);
                                    setViewData(jsonObject);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(ReflectActivity.this, response.msg, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                        AppUtils.showToast(ReflectActivity.this, "获取当日可提现金额信息出错");
                    }
                });
    }

    /**
     * 获取剩余可提现金额
     *
     * @param jsonObject
     * @throws JSONException
     */
    private void setViewData(JSONObject jsonObject) throws JSONException {
        double crashAvailable = jsonObject.getDouble("cashAvailable");
        reflectTotal.setText(String.format("￥%1$s", "" + crashAvailable));

    }

}
