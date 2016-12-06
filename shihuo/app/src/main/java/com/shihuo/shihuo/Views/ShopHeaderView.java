
package com.shihuo.shihuo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.SysTypeModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jiahengqiu on 2016/10/23.
 * 商铺版headerview
 */

public class ShopHeaderView extends LinearLayout {

    @BindView(R.id.image_shop_logo)
    SimpleDraweeView imageShopLogo;
    @BindView(R.id.text_shop_add)
    TextView textShopAdd;
    @BindView(R.id.text_main_products)
    TextView textMainProducts;
    private Context mContext;


    public ShopHeaderView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public ShopHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public ShopHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.shop_header_view, null);

        addView(view);
    }

    /**
     * 绑定数据
     */
    public void setData() {

    }

    @OnClick(R.id.image_shop_logo)
    public void onClick() {


    }
}
