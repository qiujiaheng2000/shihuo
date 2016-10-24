package com.shihuo.shihuo.Views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiahengqiu on 2016/10/23.
 */

public class HeaderView extends LinearLayout {

    @BindView(R.id.binner)
    ViewPager binner;
    @BindView(R.id.ten_prefecture)
    Button tenPrefecture;
    @BindView(R.id.twenty_prefecture)
    Button twentyPrefecture;
    @BindView(R.id.thirty_prefecture)
    Button thirtyPrefecture;
    @BindView(R.id.sales_prefecture)
    Button salesPrefecture;
    @BindView(R.id.card_layout)
    LinearLayout cardLayout;

    public HeaderView(Context context) {
        super(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.header_view, null);


    }

    @OnClick({R.id.ten_prefecture, R.id.twenty_prefecture, R.id.thirty_prefecture, R.id.sales_prefecture})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ten_prefecture:
                break;
            case R.id.twenty_prefecture:
                break;
            case R.id.thirty_prefecture:
                break;
            case R.id.sales_prefecture:
                break;
        }
    }
}
