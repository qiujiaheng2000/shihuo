
package com.shihuo.shihuo.Views;

import android.content.Context;
import android.text.TextUtils;
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

    @BindView(R.id.tv_name)
    TextView tv_name;

    private Context mContext;

    public interface OnLogoClick {
        void onLogoClick(SimpleDraweeView imageLogo);
    }

    private OnLogoClick mOnclick;

    public ShopHeaderView(Context context,OnLogoClick onLogoClick) {
        super(context);
        mContext = context;
        mOnclick = onLogoClick;
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
        if (!TextUtils.isEmpty(shopManagerInfo.storeDetail)) {
            textMainProducts.setText("店铺主营:" + shopManagerInfo.storeDetail);
        } else {
            textMainProducts.setText("店铺主营:暂无数据");
        }

        tv_name.setText(shopManagerInfo.storeName);

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
        if (mOnclick != null) {
            mOnclick.onLogoClick(imageShopLogo);
        }
    }
}
