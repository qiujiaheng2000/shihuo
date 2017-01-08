
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
 * Created by cm_qiujiaheng on 2016/11/24.
 */

public class NewAddressActivity extends BaseActivity {
    @BindView(R.id.imag_left)
    ImageView imagLeft;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.txBtnRight)
    TextView txBtn;

    @BindView(R.id.edte_consignee)
    EditText edteConsignee;

    @BindView(R.id.edte_phone)
    EditText edtePhone;

    @BindView(R.id.edte_province)
    EditText edteProvince;

    @BindView(R.id.edte_address)
    EditText edteAddress;

    public static final int FLAG_NEW_ADDRESS = 0;// 新增

    public static final int FLAG_EDIT_ADDRESS = 1;// 编辑

    public static final String KEY_TYPE = "key_type";// 当前界面类型 ？

    public static final String KEY_ADDRESS = "key_address";// 要修改的地址对象

    private MyAddressModel myAddressModel;// 要修改的地址对象

    // 当前界面是编辑还是新增地址
    private int type;

    public static void startNewAddressActivity(Context context, MyAddressModel addressModel,
            int flag) {
        Intent intent = new Intent(context, NewAddressActivity.class);
        intent.putExtra(KEY_ADDRESS, addressModel);
        intent.putExtra(KEY_TYPE, flag);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_address);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        txBtn.setText(R.string.save);
        imagLeft.setVisibility(View.VISIBLE);
        txBtn.setVisibility(View.VISIBLE);
        type = getIntent().getIntExtra(KEY_TYPE, FLAG_NEW_ADDRESS);
        if (FLAG_NEW_ADDRESS == type) {
            title.setText(R.string.new_address_title);
        } else {
            type = FLAG_EDIT_ADDRESS;
            title.setText(R.string.edit_address_title);
            myAddressModel = getIntent().getParcelableExtra(KEY_ADDRESS);
            setUIByAddress();
        }
    }

    /**
     * 根据要修改的地址，初始化界面
     */
    private void setUIByAddress() {
        if (!TextUtils.isEmpty(myAddressModel.receiverName)) {
            edteConsignee.setText(myAddressModel.receiverName);
        }
        if (!TextUtils.isEmpty(myAddressModel.receiverPhoneNum)) {
            edtePhone.setText(myAddressModel.receiverPhoneNum);
        }
        if (!TextUtils.isEmpty(myAddressModel.addressZone)) {
            edteProvince.setText(myAddressModel.addressZone);
        }
        if (!TextUtils.isEmpty(myAddressModel.addressDetail)) {
            edteAddress.setText(myAddressModel.addressDetail);
        }
    }

    @OnClick({
            R.id.imag_left, R.id.txBtnRight
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.txBtnRight:
                if (FLAG_NEW_ADDRESS == type) {
                    save();
                } else {
                    updateAddress();
                }
                break;
        }
    }

    /**
     * 保存新地址
     */
    private void save() {
        if (TextUtils.isEmpty(edteConsignee.getText().toString())) {
            AppUtils.showToast(this, "请输入收货人姓名");
            return;
        }
        if (TextUtils.isEmpty(edtePhone.getText().toString())) {
            AppUtils.showToast(this, "请输入收货人联系电话");
            return;
        }
        if (TextUtils.isEmpty(edteProvince.getText().toString())) {
            AppUtils.showToast(this, "请输入收货人省市区");
            return;
        }
        if (TextUtils.isEmpty(edteAddress.getText().toString())) {
            AppUtils.showToast(this, "请输入收货人详细地址");
            return;
        }
        showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("receiverName", edteConsignee.getText().toString());
            params.put("receiverPhoneNum", edtePhone.getText().toString());
            params.put("addressZone", edteProvince.getText().toString());
            params.put("addressDetail", edteAddress.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_NEW_ADDRESS) + "?token="
                        + AppShareUitl.getToken(this))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString()).build().execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            AppUtils.showToast(NewAddressActivity.this, "新增地址成功");
                            finish();
                        } else {
                            AppUtils.showToast(NewAddressActivity.this, response.msg);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                    }
                });

    }

    /**
     * 修改新地址
     */
    private void updateAddress() {
        if (TextUtils.isEmpty(edteConsignee.getText().toString())) {
            AppUtils.showToast(this, "请输入收货人姓名");
            return;
        }
        if (TextUtils.isEmpty(edtePhone.getText().toString())) {
            AppUtils.showToast(this, "请输入收货人联系电话");
            return;
        }
        if (TextUtils.isEmpty(edteProvince.getText().toString())) {
            AppUtils.showToast(this, "请输入收货人省市区");
            return;
        }
        if (TextUtils.isEmpty(edteAddress.getText().toString())) {
            AppUtils.showToast(this, "请输入收货人详细地址");
            return;
        }
        showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("addressId", myAddressModel.addressId);
            params.put("receiverName", edteConsignee.getText().toString());
            params.put("receiverPhoneNum", edtePhone.getText().toString());
            params.put("addressZone", edteProvince.getText().toString());
            params.put("addressDetail", edteAddress.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_UPDATE_ADDRESS) + "?token="
                        + AppShareUitl.getToken(this))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString()).build().execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            AppUtils.showToast(NewAddressActivity.this, "更新地址成功");
                            finish();
                        } else {
                            AppUtils.showToast(NewAddressActivity.this, response.msg);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                    }
                });

    }
}
