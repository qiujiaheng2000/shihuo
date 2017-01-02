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
import com.shihuo.shihuo.Views.ConfirmOrderItemView;
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
    @BindView(R.id.text_order_paytime)
    TextView textOrderPayTime;
    @BindView(R.id.layout_order_detail)
    LinearLayout layoutOrderDetail;
    @BindView(R.id.text_default_order_msg)
    TextView textDefaultOrderMsg;
    @BindView(R.id.image_status_icon)
    ImageView imageStatusIcon;
    @BindView(R.id.text_refund_reason)
    TextView textRefundReason;
    @BindView(R.id.text_refund_time)
    TextView textRefundTime;
    @BindView(R.id.layout_refund_detail)
    LinearLayout layoutRefundDetail;
    @BindView(R.id.text_refuse_reason)
    TextView textRefuseReason;
    @BindView(R.id.text_refuse_time)
    TextView textRefuseTime;
    @BindView(R.id.layout_refuse_detail)
    LinearLayout layoutRefuseDetail;

    private OrderModel mOrderModel;

    public static void start(Context context, OrderModel orderModel) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(ORDER_MODEL, orderModel);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderModel = getIntent().getParcelableExtra(ORDER_MODEL);
        setContentView(R.layout.layout_order_detail);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText("订单详情");
        setStatus();
        setAddress();
        //设置价格
        textPrice.setText("￥" + mOrderModel.goodsPrice);
        textPayprice.setText("￥" + mOrderModel.orderPrice);
        setshipMethod();
        textDeliverCommpany.setText("配送单号：" + mOrderModel.trackingNum);
        textOrderNum.setText("订单号：" + mOrderModel.paymentNum);
        textOrderCreatetime.setText("订单创建时间：" + mOrderModel.createTime);
        textOrderPayTime.setText("订单创建时间：" + mOrderModel.paymentTime);

        ConfirmOrderItemView confirmOrderItemView = new ConfirmOrderItemView(this);
        confirmOrderItemView.setOrderData(mOrderModel);
        layoutGoods.addView(confirmOrderItemView);

        textRefundReason.setText(mOrderModel.refundReason);
        textRefundTime.setText("退货时间："+mOrderModel.refundTime);
        textRefuseReason.setText(mOrderModel.refuseReason);
        textRefuseTime.setText("拒绝时间："+mOrderModel.refuseTime);

    }

    /**
     * 设置配送方式
     */
    private void setshipMethod() {
        textDeliver.setText(String.format("配送方式：%1$s", mOrderModel.shipMethod));
    }

    private void setAddress() {
        textName.setText(mOrderModel.receiverName);
        textPhone.setText(mOrderModel.receiverPhoneNum);
        textAddress.setText(mOrderModel.address);

    }

    /**
     * 根据订单状态设置界面
     */
    private void setStatus() {
        switch (mOrderModel.status) {
            case OrderModel.ORDER_STATUS_UNSHIP:
                textOrderStatus.setText("待发货");
                textDefaultOrderMsg.setText("七天以内为默认收货");
                imageStatusIcon.setImageResource(R.mipmap.icon_unship);
                break;
            case OrderModel.ORDER_STATUS_SHIPED:
                textOrderStatus.setText("待收货");
                textDefaultOrderMsg.setText("七天以内为默认收货");
                imageStatusIcon.setImageResource(R.mipmap.icon_shiped);
                buttonConfirm.setVisibility(View.VISIBLE);
                break;
            case OrderModel.ORDER_STATUS_COMPLETED:
                textOrderStatus.setText("已完成");
                textDefaultOrderMsg.setVisibility(View.GONE);
                imageStatusIcon.setImageResource(R.mipmap.icon_completed);
                break;
            case OrderModel.ORDER_STATUS_BACK:
                textOrderStatus.setText("退货中");
                textDefaultOrderMsg.setVisibility(View.GONE);
                imageStatusIcon.setImageResource(R.mipmap.icon_back);
                layoutRefundDetail.setVisibility(View.VISIBLE);
//                layoutRefuseDetail.setVisibility(View.VISIBLE);
                break;
            case OrderModel.ORDER_STATUS_BACKED:
                textOrderStatus.setText("已退货");
                imageStatusIcon.setImageResource(R.mipmap.icon_backed);
                textDefaultOrderMsg.setText("您的订单已经退货成功，请留意您的微信或支付宝，我们将会在三个工作日内给您退款，如果没用收到退款，请及时联系客服，给您造成的不便，敬请原谅，客服电话010-2324523");
                layoutRefundDetail.setVisibility(View.VISIBLE);
//                layoutRefuseDetail.setVisibility(View.VISIBLE);
                break;
            case OrderModel.ORDER_STATUS_PROCESSING:
                imageStatusIcon.setImageResource(R.mipmap.icon_processing);
                textOrderStatus.setText("处理中");
                break;
            case OrderModel.ORDER_STATUS_CLOSED:
                textOrderStatus.setText("已关闭");
                imageStatusIcon.setImageResource(R.mipmap.icon_closed);
                textDefaultOrderMsg.setText("您的订单已经处理，如有疑问请联系客服，客服电话010-2324523");
                layoutRefuseDetail.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
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
