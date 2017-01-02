package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.ConfirmOrderItemView;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsDetailModel;
import com.shihuo.shihuo.models.MyAddressModel;
import com.shihuo.shihuo.models.SpecificationModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.Toaster;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    }

    /**
     * 设置总价 总数量
     */
    private void setTotalPrice() {
        int totalPrice = 0;
        int totalNumber = 0;
        for (GoodsDetailModel goodsDetailModel : mGoodsDetailModels) {
            totalPrice += goodsDetailModel.curPrice * goodsDetailModel.amount;
            totalNumber += goodsDetailModel.amount;
        }
        textTotalNumber.setText(String.format("共%1$s件", String.valueOf(totalNumber)));
        textTotalPrice.setText(String.format("￥%1$s", String.valueOf(totalPrice)));
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
            textNamePhone.setText(String.format("%1$s   %1$s", mCurrentAddress.receiverName, mCurrentAddress.receiverPhoneNum));
            textAddress.setText(mCurrentAddress.addressDetail);
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

            if (radiogroupPayType.getCheckedRadioButtonId() == R.id.radiobutton_weixin) {
                params.put("payment", PAYMENT_WEIXIN);
            } else {
                params.put("payment", PAYMENT_ALIPAY);
            }
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
                            Toaster.toastShort("调用支付sdk");
//                            finish();
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
