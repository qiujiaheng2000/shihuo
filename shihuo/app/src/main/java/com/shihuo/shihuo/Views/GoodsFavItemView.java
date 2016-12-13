
package com.shihuo.shihuo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.GoodsDetailActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.GoodsDetailModel;
import com.shihuo.shihuo.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品收藏item
 */

public class GoodsFavItemView extends LinearLayout {

    private Context mContext;

    @BindView(R.id.imageView)
    SimpleDraweeView imageView;

    @BindView(R.id.item_title)
    TextView itemTitle;

    @BindView(R.id.item_desc)
    TextView itemDesc;

    @BindView(R.id.real_price)
    TextView realPrice;

    @BindView(R.id.old_price)
    TextView oldPrice;

    @BindView(R.id.buys)
    TextView buys;

    private GoodsDetailModel model;

    public GoodsFavItemView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public GoodsFavItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public GoodsFavItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.item_fav_goods, null);
        ButterKnife.bind(this, view);
        addView(view);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetailActivity.start(mContext, model.goodsId);
            }
        });
    }

    /**
     * 绑定数据
     */
    public void setData(GoodsDetailModel model) {
        if (model != null) {
            this.model = model;
            imageView.setImageURI(AppUtils.parse(Contants.IMAGE_URL + model.picUrl));
            itemTitle.setText(AppUtils.isEmpty(model.goodsName));
            itemDesc.setText(AppUtils.isEmpty(model.goodsDetail));
            realPrice.setText(String.format(mContext.getResources().getString(R.string.price),
                    model.curPrice + ""));
            buys.setText(String.format(mContext.getResources().getString(R.string.sales),
                    model.salesNum));
        }
    }

}
