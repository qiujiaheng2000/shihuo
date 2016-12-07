
package com.shihuo.shihuo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购物车view
 */
public class ShoppingCarView extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.iv_shopping_car)
    ImageView mShoppingCarIv;

    @BindView(R.id.iv_back_top)
    ImageView mBackTopIv;

    public ShoppingCarView(Context context) {
        super(context);
        initViews();
    }

    public ShoppingCarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public ShoppingCarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_shopping_car, null);
        ButterKnife.bind(this, view);
        addView(view);
        mShoppingCarIv.setOnClickListener(this);
        mBackTopIv.setOnClickListener(this);
    }

    public void setOnClickListener(OnViewClickListener listener) {
        this.listener = listener;

    }

    public void setGoBackGone(){
        mBackTopIv.setVisibility(View.GONE);
    }

    /**
     * 绑定数据
     */
    public void setData(int shopCarNum) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_shopping_car:
                listener.onShoppingCarListener();
                break;
            case R.id.iv_back_top:
                listener.onBackTopListener();
                break;
        }
    }

    private OnViewClickListener listener;

    public interface OnViewClickListener {
        void onShoppingCarListener();

        void onBackTopListener();
    }
}
