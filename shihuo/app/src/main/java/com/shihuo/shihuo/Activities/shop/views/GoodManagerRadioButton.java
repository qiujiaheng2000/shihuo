package com.shihuo.shihuo.Activities.shop.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.shihuo.shihuo.R;

import butterknife.ButterKnife;

/**
 * Created by cm_qiujiaheng on 2016/12/10.
 * 商品管理列表界面-自定义的radiobutton
 */

public class GoodManagerRadioButton extends LinearLayout {
    public GoodManagerRadioButton(Context context) {
        super(context);
        init();
    }


    public GoodManagerRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GoodManagerRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.layout_goodsmanager_radiobutton, null);
        ButterKnife.bind(this, view);
        addView(view);
    }
}
