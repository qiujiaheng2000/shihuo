package com.shihuo.shihuo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.GoodsDetailModel;
import com.shihuo.shihuo.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cm_qiujiaheng on 2017/1/2.
 * 订单确认里面的 订单展示view
 */

public class ConfirmOrderItemView extends LinearLayout {


    @BindView(R.id.imageView)
    SimpleDraweeView imageView;
    @BindView(R.id.text_order_title)
    TextView textOrderTitle;
    @BindView(R.id.text_order_spec)
    TextView textOrderSpec;
    @BindView(R.id.text_order_size)
    TextView textOrderSize;
    @BindView(R.id.text_item_price)
    TextView textItemPrice;
    @BindView(R.id.text_item_number)
    TextView textItemNumber;
    @BindView(R.id.text_price)
    TextView textPrice;

    public ConfirmOrderItemView(Context context) {
        super(context);
        initView();
    }

    public ConfirmOrderItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ConfirmOrderItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.confirm_order_item_view, null);
        ButterKnife.bind(this, view);
        addView(view);
    }

    public void setOrderDetail(GoodsDetailModel orderDetail) {
        if (orderDetail != null) {
            if (orderDetail.picUrl != null) {
                imageView.setImageURI(AppUtils.parse(Contants.IMAGE_URL
                        + orderDetail.picUrl));
            } else if (orderDetail.goodsPicsList != null) {
                imageView.setImageURI(AppUtils.parse(Contants.IMAGE_URL
                        + orderDetail.goodsPicsList.get(0).picUrl));
            }
            textOrderTitle.setText(orderDetail.goodsName);
            textOrderSpec.setText(orderDetail.specName);
            textItemPrice.setText(String.valueOf(orderDetail.curPrice));
            textItemNumber.setText(String.format("x %1$s", orderDetail.amount));
            textPrice.setText(String.format("￥%1$s", orderDetail.curPrice * orderDetail.amount));
        }
    }

}
