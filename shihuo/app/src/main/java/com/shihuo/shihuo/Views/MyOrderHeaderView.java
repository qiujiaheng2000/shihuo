package com.shihuo.shihuo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/3.
 */

public class MyOrderHeaderView extends LinearLayout {

    public static class OrderHeaderModel {
        public String allNums = "0";//所有订单数
        public String paidNums = "0";//待发货订单数量
        public String sendedNums = "0";//待收货订单数量
        public String completedNums = "0";//完成订单数量
        public String afterSaleNums = "0";//售后订单数量
    }

    public interface ButtonOnClickListener {
        void onClick(int type);
    }

    @BindView(R.id.order_all_nums)
    TextView orderAllNums;
    @BindView(R.id.order_paid_nums)
    TextView orderPaidNums;
    @BindView(R.id.order_sended_nums)
    TextView orderSendedNums;
    @BindView(R.id.order_completed_nums)
    TextView orderCompletedNums;
    @BindView(R.id.order_after_sale_nums)
    TextView orderAfterSaleNums;

    private ButtonOnClickListener buttonOnClickListener;

    public void setButtonOnClickListener(ButtonOnClickListener buttonOnClickListener) {
        this.buttonOnClickListener = buttonOnClickListener;
    }

    public MyOrderHeaderView(Context context) {
        super(context);
        initView();
    }

    public MyOrderHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyOrderHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.my_order_header, null);
        ButterKnife.bind(this, view);
        this.addView(view);
    }


    @OnClick({R.id.order_all, R.id.order_paid, R.id.order_sended, R.id.order_completed, R.id.order_after_sale})
    public void onClick(View view) {
        int type = 0;
        switch (view.getId()) {
            case R.id.order_all:
                type = 0;
                break;
            case R.id.order_paid:
                type = 1;
                break;
            case R.id.order_sended:
                type = 2;
                break;
            case R.id.order_completed:
                type = 3;
                break;
            case R.id.order_after_sale:
                type = 4;
                break;
        }
        if (null != buttonOnClickListener) {
            buttonOnClickListener.onClick(type);
        }
    }

    /**
     * 设置头部数据
     *
     * @param headers
     */
    public void setHeaders(OrderHeaderModel headers) {
        this.orderAllNums.setText(headers.allNums);//全部
        this.orderPaidNums.setText(headers.paidNums);//待发货
        this.orderSendedNums.setText(headers.sendedNums);//待收货
        this.orderCompletedNums.setText(headers.completedNums);//完成数量
        this.orderAfterSaleNums.setText(headers.afterSaleNums);//售后数量
    }
}
