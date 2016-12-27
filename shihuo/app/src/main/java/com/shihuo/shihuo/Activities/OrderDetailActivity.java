package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.OrderModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/12/27.
 * 订单详情界面
 */

public class OrderDetailActivity extends BaseActivity {

    public static final String ORDER_MODEL = "orderModel";
    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.button_confirm)
    Button buttonConfirm;
    @BindView(R.id.text_order_status)
    TextView textOrderStatus;
    @BindView(R.id.text_default_delivery)
    TextView textDefaultDelivery;
    @BindView(R.id.text_consignee)
    TextView textConsignee;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_phone)
    TextView textPhone;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.layout_goods)
    LinearLayout layoutGoods;
    @BindView(R.id.text_price)
    TextView textPrice;
    @BindView(R.id.text_payprice)
    TextView textPayprice;
    @BindView(R.id.text_deliver)
    TextView textDeliver;
    @BindView(R.id.text_deliver_commpany)
    TextView textDeliverCommpany;
    @BindView(R.id.text_order_num)
    TextView textOrderNum;
    @BindView(R.id.text_order_createtime)
    TextView textOrderCreatetime;
    @BindView(R.id.text_order_detail)
    TextView textOrderDetail;
    @BindView(R.id.layout_order_detail)
    LinearLayout layoutOrderDetail;

    public static void start(Context context, OrderModel orderModel) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(ORDER_MODEL, orderModel);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_order_detail);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText("订单详情");

    }

    @OnClick({R.id.imag_left, R.id.button_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.button_confirm:
                break;
        }
    }
}
