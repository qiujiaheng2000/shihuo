
package com.shihuo.shihuo.Activities.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.NumEditTextView;
import com.shihuo.shihuo.Views.autolabel.CustomAutoLabelUi;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.GoodsDetailModel;
import com.shihuo.shihuo.models.SpecificationModel;
import com.shihuo.shihuo.util.AppUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品参数界面 Created by lishuai on 16/12/10.
 */

public class GoodsSetParameterActivity extends Activity {

    private final static String TAG = "GoodsSetParameterActivity";
    private final static String MODEL_TAG = "GoodsDetailModel";

    public static void start(Context context, GoodsDetailModel model) {
        Intent intent = new Intent(context, GoodsSetParameterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(MODEL_TAG, model);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @BindView(R.id.image_close)
    ImageView image_close;

    @BindView(R.id.tv_ok)
    TextView tv_ok;

    @BindView(R.id.view_cart_num)
    NumEditTextView view_cart_num;

    @BindView(R.id.label_view)
    CustomAutoLabelUi labelView;

    @BindView(R.id.imageView)
    SimpleDraweeView imageView;

    @BindView(R.id.goods_title)
    TextView goods_title;

    @BindView(R.id.goods_new_price)
    TextView goods_new_price;

    @BindView(R.id.sales)
    TextView mSalesTv;

    private GoodsDetailModel mGoodsDetailModel;

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_param);
        ButterKnife.bind(this);
        context = this;
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        getWindow().setAttributes(lp);

        mGoodsDetailModel = getIntent().getParcelableExtra(MODEL_TAG);
        if (mGoodsDetailModel == null) {
            AppUtils.showToast(context, getResources().getString(R.string.no_data));
            return;
        }
        initViews();
    }

    public void initViews() {
        view_cart_num.setMax(10);

        for (int i = 0; i < mGoodsDetailModel.goodsSpecList.size(); i++) {
            labelView.addLabel(mGoodsDetailModel.goodsSpecList.get(i));
        }

//        ArrayList<String> labels = new ArrayList<>();
//        labels.add("42-白色");
//        labels.add("42-黑色");
//        labels.add("42-红色");
//        labels.add("41-白色");
//        labels.add("41-黑色");
//        labels.add("41-红色");
//        labels.add("39-白色");
//        labels.add("39-黑色");
//        labels.add("38-红色");

//        for (int i = 0; i < labels.size(); i++) {
//            labelView.addLabel(labels.get(i));
//        }

        if (!mGoodsDetailModel.goodsPicsList.isEmpty()) {
            imageView.setImageURI(AppUtils.parse(Contants.IMAGE_URL + mGoodsDetailModel.goodsPicsList.get(0).picUrl));
        }

        goods_title.setText(AppUtils.isEmpty(mGoodsDetailModel.goodsName));
        goods_new_price.setText(String.format(
                context.getResources().getString(R.string.price), mGoodsDetailModel.curPrice + ""));
        mSalesTv.setText(AppUtils.isEmpty(String.format(
                context.getResources().getString(R.string.sales), mGoodsDetailModel.salesNum + "")));
    }

    @OnClick({
            R.id.image_close, R.id.tv_ok
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_close:
                finish();
                break;
            case R.id.tv_ok:
                SpecificationModel specificationModel = labelView.getCheckedSpecificationModel();
                AppUtils.showToast(GoodsSetParameterActivity.this, specificationModel.specName);
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.fade_out);

    }
}
