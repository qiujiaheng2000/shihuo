
package com.shihuo.shihuo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.SysTypeModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiahengqiu on 2016/10/23.
 */

public class HomeHeaderView extends LinearLayout {

    private Context mContext;

    /**
     * 商品分类
     */
    @BindView(R.id.view_goods_type)
    HorizontalTagView mGoodsTypeView;

    /**
     * 商圈分类
     */
    @BindView(R.id.view_circle_type)
    HorizontalTagView mCircleTypeView;

    /**
     * banner图
     */
    @BindView(R.id.view_banner)
    BannerView mBannerView;

    /**
     * 折扣优惠区
     */
    @BindView(R.id.view_discount)
    DiscountView mDiscountView;

    public HomeHeaderView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HomeHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public HomeHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.home_header_view, null);
        ButterKnife.bind(this, view);
        addView(view);
    }

    /**
     * 绑定数据
     */
    public void setData(SysTypeModel model) {
        if (model.data != null) {
            // 商品
            mGoodsTypeView.setData(model.data.shSysGoodsTypeList, true);
            // 商圈
            mCircleTypeView.setData(model.data.shSysStoreCircleList, false);
            // 设置banner
            mBannerView.setData(model.data.shAdvertisingList);
            // 折扣区
            mDiscountView.setData(model.data.shSysDiscountType1List, model.data.shSysDiscountType2List);
        }
    }

}
