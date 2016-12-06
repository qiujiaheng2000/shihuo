
package com.shihuo.shihuo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.BaseGoodsModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 一行两个商品view
 */
public class GoodsItemView extends LinearLayout {

    @BindView(R.id.view_goods_left)
    GoodsView mGoodsLeftView;

    @BindView(R.id.view_goods_right)
    GoodsView mGoodsRightView;

    public GoodsItemView(Context context) {
        super(context);
        initViews();
    }

    public GoodsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public GoodsItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_view_goods, null);
        ButterKnife.bind(this, view);
        addView(view);
    }

    /**
     * 绑定数据
     */
    public void setData(BaseGoodsModel model) {
        if (model != null) {
            if (model.goodsLeftModel != null) {
                mGoodsLeftView.bindData(model.goodsLeftModel);
            }
            if (model.goodsRightModel != null) {
                mGoodsRightView.bindData(model.goodsRightModel);
            }
        }
    }

}
