
package com.shihuo.shihuo.Views;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.ShopHomeActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.GoodsModel;
import com.shihuo.shihuo.models.StoreDetailModel;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商铺item view bannerView
 */

public class StoreItemView extends RelativeLayout {

    @BindView(R.id.imageView)
    SimpleDraweeView imageView;

    @BindView(R.id.item_title)
    TextView item_title;

    @BindView(R.id.item_desc)
    TextView item_desc;

    @BindView(R.id.prefix_numbs)
    TextView prefixNumbs;

    @BindView(R.id.numbs)
    TextView numbs;

    @BindView(R.id.shop_add)
    TextView shop_add;

    @BindView(R.id.layout)
    RelativeLayout layout;

    private Context context;

    private StoreDetailModel mModel;

    public StoreItemView(Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    public StoreItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    public StoreItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_fav_shops, null);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        params.width = AppUtils.getScreenWidthAndHeight((Activity)context)[0];
        params.setMargins(AppUtils.dip2px(context, 5), AppUtils.dip2px(context, 5),
                AppUtils.dip2px(context, 5), AppUtils.dip2px(context, 5));
        view.setLayoutParams(params);
        addView(view);
        ButterKnife.bind(this, view);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mModel != null && !TextUtils.isEmpty(mModel.storeId)){
                    ShopHomeActivity.start(context, mModel.storeId);
                }
            }
        });
    }

    /**
     * 绑定数据
     *
     * @param model
     */
    public void bindData(StoreDetailModel model) {
        if (model != null) {
            mModel = model;
            item_title.setText(model.storeName);
            item_desc.setText(model.storeDetail);
            prefixNumbs.setText("销量：");
            numbs.setText("" + model.orderNum);
            shop_add.setText(model.circleName);
            imageView.setImageURI(AppUtils.parse(AliyunHelper.getFullPathByName(model.storeLogoPicUrl)));
        }
    }
}
