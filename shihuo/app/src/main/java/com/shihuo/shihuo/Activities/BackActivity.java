package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.OrderModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.Toaster;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;


/**
 * Created by cm_qiujiaheng on 2016/12/27.
 * 退货界面
 */

public class BackActivity extends BaseActivity {

    public static final String ORDER_MODEL = "orderModel";
    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.spinner_system_type_two)
    AppCompatSpinner spinnerSystemTypeTwo;
    @BindView(R.id.edit_return_price)
    TextView editReturnPrice;
    @BindView(R.id.edit_back_content)
    EditText editBackContent;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    private OrderModel mOrderModel;

    public static void start(Context context, OrderModel orderModel) {
        Intent intent = new Intent(context, BackActivity.class);
        intent.putExtra(ORDER_MODEL, orderModel);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderModel = getIntent().getParcelableExtra(ORDER_MODEL);
        setContentView(R.layout.layout_back_goods);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText("退货理由");
        editReturnPrice.setText("" + mOrderModel.orderPrice);
        spinnerSystemTypeTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                String selectedStr = (String) spinnerSystemTypeTwo.getItemAtPosition(position);
                editBackContent.clearComposingText();
                editBackContent.setText(selectedStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 申请退货
     */
    private void refundOrder() {
        showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("orderId", mOrderModel.orderId);
            params.put("refundReason", editBackContent.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_REFUND_GOODS) + "?token=" + AppShareUitl.getUserInfo(this).token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            Toaster.toastShort("申请退货成功");
                            finish();
                        } else {
                            AppUtils.showToast(BackActivity.this, response.msg);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                    }
                });
    }

    @OnClick({R.id.imag_left, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_confirm:
                refundOrder();
                break;
        }
    }
}
