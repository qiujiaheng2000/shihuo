package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.ConfirmOrderItemView;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsDetailModel;
import com.shihuo.shihuo.models.MyAddressModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.pay.PayHelper;
import com.shihuo.shihuo.util.pay.PayResult;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by cm_qiujiaheng on 2017/1/1.
 * 确认订单界面
 */

public class ConfirmOrdersActivity extends BaseActivity {

    public static final String GOODS_DETAIL_MODELS = "goodsDetailModels";
    public static final int PAYMENT_WEIXIN = 1;
    public static final int PAYMENT_ALIPAY = 2;

    public static final int SDK_PAY_FLAG = 1;

    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.text_total_price)
    TextView textTotalPrice;
    @BindView(R.id.text_total_number)
    TextView textTotalNumber;
    @BindView(R.id.btn_pay_now)
    TextView btnPayNow;
    @BindView(R.id.layout_delivery_address)
    RelativeLayout layoutDeliveryAddress;
    @BindView(R.id.text_name_phone)
    TextView textNamePhone;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.layout_address_detail)
    LinearLayout layoutAddressDetail;
    @BindView(R.id.layout_orders)
    LinearLayout layoutOrders;
    @BindView(R.id.radiobutton_pickup)
    RadioButton radiobuttonPickup;
    @BindView(R.id.radiobutton_city)
    RadioButton radiobuttonCity;
    @BindView(R.id.radiobutton_free)
    RadioButton radiobuttonFree;
    @BindView(R.id.radiogroup_delivery_type)
    RadioGroup radiogroupDeliveryType;
    @BindView(R.id.radiobutton_alipay)
    RadioButton radiobuttonAlipay;
    @BindView(R.id.radiobutton_weixin)
    RadioButton radiobuttonWeixin;
    @BindView(R.id.radiogroup_pay_type)
    RadioGroup radiogroupPayType;
    //订单列表
    private List<GoodsDetailModel> mGoodsDetailModels;
    private MyAddressModel mCurrentAddress;//当前选中的收获地址

    private int mCurrentPaymentType = PAYMENT_ALIPAY;

    private String alipayTestStr = "_input_charset=\"utf-8\"&body=\"测试商品支付功能\"&it_b_pay=\"1h\"&notify_url=\"http://59.110.10.19:8080/payment/payment\"&out_trade_no=\"2016121401316123\"&partner=\"2088521333250291\"&payment_type=\"1\"&seller_id=\"2088521333250291\"&service=\"mobile.securitypay.pay\"&subject=\"识货支付测试\"&total_fee=\"0.01\"&sign_type=\"RSA\"&paySign=\"tI66%2BOsUOuSFHLtQ1BJc987RrNj7Rz%2BnDxDNViYJ%2FEJrolcGn5j1w3cyUWuer4PVEb4AOkkfezRT3Ul%2F6ycFgxnKqijSyG40wht6uk7XBxdqloJ2FsCww1%2FRM4MbkLQLfGQ%2FNvUOK88g%2FX6kymiaXLReiFrXqiBIx7arER%2BfUQ4%3D\"";

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(ConfirmOrdersActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ConfirmOrdersActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

                default:
                    break;
            }
        }

        ;
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoodsDetailModels = new ArrayList<>();
        ArrayList<GoodsDetailModel> goodsDetailModels = getIntent().getParcelableArrayListExtra(GOODS_DETAIL_MODELS);

        if (null == goodsDetailModels) {
            throw new IllegalArgumentException("参数错误~！");
        }
        mGoodsDetailModels.addAll(goodsDetailModels);
        setContentView(R.layout.layout_confirm_orders);
        ButterKnife.bind(this);
        initViews();
    }

    public static void start(Context context, ArrayList<GoodsDetailModel> goodsDetailModel) {
        Intent intent = new Intent(context, ConfirmOrdersActivity.class);
        intent.putParcelableArrayListExtra(GOODS_DETAIL_MODELS, goodsDetailModel);
        context.startActivity(intent);
    }


    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText("确认订单");
        setOrdersViews();
        setTotalPrice();

        radiogroupPayType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobutton_weixin:
                        mCurrentPaymentType = PAYMENT_WEIXIN;
                        break;
                    case R.id.radiobutton_alipay:
                        mCurrentPaymentType = PAYMENT_ALIPAY;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 设置总价 总数量
     */
    private void setTotalPrice() {
        float totalPrice = 0;
        int totalNumber = 0;
        for (GoodsDetailModel goodsDetailModel : mGoodsDetailModels) {
            totalPrice += goodsDetailModel.curPrice * goodsDetailModel.amount;
            totalNumber += goodsDetailModel.amount;
        }
        BigDecimal b = new BigDecimal(totalPrice);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        textTotalNumber.setText(String.format("共%1$s件", String.valueOf(totalNumber)));
        textTotalPrice.setText(String.format("￥%1$s", f1));
    }

    private void setOrdersViews() {
        for (GoodsDetailModel goodsDetailModel : mGoodsDetailModels) {
            ConfirmOrderItemView confirmOrderItemView = new ConfirmOrderItemView(this);
            confirmOrderItemView.setOrderDetail(goodsDetailModel);
            layoutOrders.addView(confirmOrderItemView);
        }
    }

    @OnClick({R.id.imag_left, R.id.btn_pay_now, R.id.layout_delivery_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_pay_now:
                conmitOrders();
                break;
            case R.id.layout_delivery_address:
                ChooseAddressListActivity.startChooseAddressListActivityForResult(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ChooseAddressListActivity.REQUEST_CODE && resultCode == ChooseAddressListActivity.RESULT_CODE) {
            mCurrentAddress = data.getParcelableExtra(ChooseAddressListActivity.RESULT_ADDRESS);
            //设置地址显示
            layoutAddressDetail.setVisibility(View.VISIBLE);
            layoutDeliveryAddress.setVisibility(View.GONE);
            textNamePhone.setText(mCurrentAddress.receiverName + "  " + mCurrentAddress.receiverPhoneNum);
            textAddress.setText(mCurrentAddress.addressZone + mCurrentAddress.addressDetail);
        }else{
            layoutDeliveryAddress.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取订单列表提交参数
     *
     * @return
     */
    private JSONArray getParams() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        for (GoodsDetailModel goodsDetailModel : mGoodsDetailModels) {
            jsonObject = new JSONObject();
            jsonObject.put("specId", goodsDetailModel.specId);
            jsonObject.put("amount", goodsDetailModel.amount);
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    /**
     * 提交订单
     */
    private void conmitOrders() {
        if (!verifyData()) {
            return;
        }
        showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("goodsList", getParams());
            params.put("addressId", mCurrentAddress.addressId);
            params.put("payment", mCurrentPaymentType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_NEWORDERS) + "?token=" + AppShareUitl.getUserInfo(this).token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                if (!TextUtils.isEmpty(response.data)) {
                                    JSONObject jsonObject = new JSONObject(response.data);
                                    if (mCurrentPaymentType == PAYMENT_WEIXIN) {//微信支付
                                        if (!TextUtils.isEmpty(jsonObject.getString("wxPayResponse"))) {
                                            PayHelper.weixinPay(jsonObject.getString("wxPayResponse"));
                                        } else {
                                            AppUtils.showToast(ConfirmOrdersActivity.this, "获取支付信息失败！");
                                        }
                                    } else {//支付宝支付
                                        if (!TextUtils.isEmpty(jsonObject.getString("paySign"))) {
                                            PayHelper.alipay(ConfirmOrdersActivity.this, jsonObject.getString("paySign"), mHandler);
                                        } else {
                                            AppUtils.showToast(ConfirmOrdersActivity.this, "获取支付信息失败！");
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            AppUtils.showToast(ConfirmOrdersActivity.this, response.msg);
                            hideProgressDialog();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                    }
                });
    }

    private boolean verifyData() {
        if (null == mCurrentAddress) {
            AppUtils.showToast(this, "请选择收获地址");
            return false;
        }
        return true;
    }

}
