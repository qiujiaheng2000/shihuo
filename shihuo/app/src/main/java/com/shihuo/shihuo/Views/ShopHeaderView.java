
package com.shihuo.shihuo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.shop.models.ShopManagerInfo;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.SysTypeModel;
import com.shihuo.shihuo.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shihuo.shihuo.R.id.image_shop_logo;

/**
 * Created by jiahengqiu on 2016/10/23. 商铺版headerview
 */

public class ShopHeaderView extends LinearLayout {

    @BindView(image_shop_logo)
    SimpleDraweeView imageShopLogo;

    @BindView(R.id.text_shop_add)
    TextView textShopAdd;

    @BindView(R.id.iv_store_start)
    ImageView iv_store_start;

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
        ButterKnife.bind(this, view);
        addView(view);
    }

    /**
     * 绑定数据
     */
    public void setData(ShopManagerInfo shopManagerInfo) {
        textShopAdd.setText(shopManagerInfo.circleName);
        textMainProducts.setText(shopManagerInfo.storeDetail);
        imageShopLogo.setImageURI(AppUtils.parse(Contants.IMAGE_URL
                + shopManagerInfo.storeLogoPicUrl));
        if (shopManagerInfo.isRecommended == 1) {
            iv_store_start.setVisibility(View.VISIBLE);
        } else {
            iv_store_start.setVisibility(View.GONE);
        }
    }

    @OnClick(image_shop_logo)
    public void onClick() {

    }
}
