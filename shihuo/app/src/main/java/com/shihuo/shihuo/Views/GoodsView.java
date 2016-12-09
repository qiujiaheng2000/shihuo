
package com.shihuo.shihuo.Views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.GoodsModel;
import com.shihuo.shihuo.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品列表单独一个商品view bannerView
 */

public class GoodsView extends LinearLayout {

    @BindView(R.id.imageView)
    SimpleDraweeView mImageView;

    @BindView(R.id.goods_title)
    TextView mGoodsTitleTv;

    @BindView(R.id.goods_new_price)
    TextView mGoodsNewPriceTv;

    @BindView(R.id.goods_origin_price)
    TextView mGoodsOriginPriceTv;

    @BindView(R.id.sales)
    TextView mSalesTv;

    private Context context;

    public GoodsView(Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    public GoodsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    public GoodsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_goods, null);
        LinearLayout.LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.width = AppUtils.getScreenWidthAndHeight((Activity)context)[0];
        params.setMargins(AppUtils.dip2px(context, 5), AppUtils.dip2px(context, 5),
                AppUtils.dip2px(context, 5), AppUtils.dip2px(context, 5));
        view.setLayoutParams(params);
        addView(view);
        ButterKnife.bind(this, view);
    }

    /**
     * 绑定数据
     * 
     * @param model
     */
    public void bindData(GoodsModel model) {
        int widthScreen = AppUtils.getScreenWidthAndHeight((Activity)context)[0];
        int widthImage = (widthScreen - AppUtils.dip2px(context, 15)) / 2;
        LinearLayout.LayoutParams params = new LayoutParams(widthImage, widthImage);
        mImageView.setLayoutParams(params);
        mImageView.setImageURI(AppUtils.parse(Contants.IMAGE_URL + model.picUrl));
        mGoodsTitleTv.setText(AppUtils.isEmpty(model.goodsName));
        if (model.curPrice == model.curPrice) {
            mGoodsNewPriceTv.setText(String.format(
                    context.getResources().getString(R.string.price), model.curPrice + ""));
            mGoodsOriginPriceTv.setVisibility(View.GONE);
        } else {
            mGoodsNewPriceTv.setText(String.format(getResources().getString(R.string.price),
                    model.curPrice + ""));
            if (model.prePrice == 0 && TextUtils.isEmpty(model.prePrice + "")) {
                mGoodsOriginPriceTv.setVisibility(View.GONE);
            } else {
                mGoodsOriginPriceTv.setVisibility(View.VISIBLE);
                mGoodsOriginPriceTv.setText(String.format(
                        context.getResources().getString(R.string.price), model.prePrice + ""));
                mGoodsOriginPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

        mSalesTv.setText(AppUtils.isEmpty(String.format(
                context.getResources().getString(R.string.sales), model.salesNum + "")));
    }
}
