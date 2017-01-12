
package com.shihuo.shihuo.Activities.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.ConfirmOrdersActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.NumEditTextView;
import com.shihuo.shihuo.Views.autolabel.CustomAutoLabelUi;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.GoodsDetailModel;
import com.shihuo.shihuo.models.SpecificationModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.Toaster;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 商品参数界面 Created by lishuai on 16/12/10.
 */

public class GoodsSetParameterActivity extends Activity implements
        CustomAutoLabelUi.LabelClickListner {

    private final static String TAG = "GoodsSetParameterActivity";

    private final static String MODEL_TAG = "GoodsDetailModel";

    private final static String FLAG = "flag";

    public static void start(Context context, int flag, GoodsDetailModel model) {
        Intent intent = new Intent(context, GoodsSetParameterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(MODEL_TAG, model);
        bundle.putInt(FLAG, flag);
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

    @BindView(R.id.old_price)
    TextView old_price;

    @BindView(R.id.sales)
    TextView mSalesTv;

    @BindView(R.id.tv_stock)
    TextView mStockTv;

    private GoodsDetailModel mGoodsDetailModel;

    private Context context;

    //当前选中的规格对象
    private SpecificationModel mCurrentSpeciModel;

    /**
     * 0=加入购物车，1=立即购买
     */
    private int mFlag;

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
        mFlag = getIntent().getIntExtra(FLAG, 0);
        if (mGoodsDetailModel == null) {
            AppUtils.showToast(context, getResources().getString(R.string.no_data));
            return;
        }
        initViews();
    }

    public void initViews() {
        labelView.setLabelClickListner(this);
        for (int i = 0; i < mGoodsDetailModel.goodsSpecList.size(); i++) {
            labelView.addLabel(mGoodsDetailModel.goodsSpecList.get(i));
        }

        if (!mGoodsDetailModel.goodsPicsList.isEmpty()) {
            imageView.setImageURI(AppUtils.parse(Contants.IMAGE_URL
                    + mGoodsDetailModel.goodsPicsList.get(0).picUrl));
        }

        goods_title.setText(AppUtils.isEmpty(mGoodsDetailModel.goodsName));
        mSalesTv.setText(AppUtils.isEmpty(String.format(
                context.getResources().getString(R.string.sales), mGoodsDetailModel.salesNum + "")));
        mCurrentSpeciModel = labelView.getCheckedSpecificationModel();
        if (mCurrentSpeciModel != null) {
            // 设置商品价格
            if (mCurrentSpeciModel.curPrice == mCurrentSpeciModel.prePrice) {
                goods_new_price.setText(String.format(getResources().getString(R.string.price),
                        mCurrentSpeciModel.curPrice + ""));
                old_price.setVisibility(View.GONE);
            } else {
                goods_new_price.setText(String.format(getResources().getString(R.string.price),
                        mCurrentSpeciModel.curPrice + ""));
                if (mCurrentSpeciModel.prePrice == 0) {
                    old_price.setVisibility(View.GONE);
                } else {
                    old_price.setVisibility(View.VISIBLE);
                    old_price.setText(String.format(getResources().getString(R.string.price),
                            mCurrentSpeciModel.prePrice + ""));
                    old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }

            view_cart_num.setMax(mCurrentSpeciModel.stockNum);
            mStockTv.setText(String.format(getResources().getString(R.string.stock_max),
                    AppUtils.isEmpty(mCurrentSpeciModel.stockNum + "")));
        }
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
                if (mFlag == 0) {
//                    AppUtils.showToast(GoodsSetParameterActivity.this, "加入购物车");
                    addToShoppingcar();

                } else {
//                    AppUtils.showToast(GoodsSetParameterActivity.this, "立即购买");
                    mGoodsDetailModel.specId = mCurrentSpeciModel.specId;
                    mGoodsDetailModel.specName = mCurrentSpeciModel.specName;
                    mGoodsDetailModel.amount = Integer.valueOf(view_cart_num.getText());
                    ArrayList<GoodsDetailModel> goodsDetailModels = new ArrayList<>();
                    goodsDetailModels.add(mGoodsDetailModel);


                    ConfirmOrdersActivity.start(GoodsSetParameterActivity.this, goodsDetailModels);
                }
                // SpecificationModel specificationModel =
                // labelView.getCheckedSpecificationModel();
                // AppUtils.showToast(GoodsSetParameterActivity.this,
                // specificationModel.specName);
                // finish();
                break;
        }
    }

    private void addToShoppingcar() {
//        showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("goodsId", mGoodsDetailModel.goodsId);
            params.put("specId", labelView.getCheckedSpecificationModel().specId);
            params.put("amount", Integer.valueOf(view_cart_num.getText()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_CART_ADD) + "?token=" + AppShareUitl.getUserInfo(this).token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
//                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            Toaster.toastShort("已加入购物车");
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        hideProgressDialog();
                    }
                });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.fade_out);

    }

    @Override
    public void onLabelClick(SpecificationModel specificationModel) {
        if (specificationModel != null) {
//            goods_new_price.setText(String.format(context.getResources().getString(R.string.price),
//                    specificationModel.curPrice + ""));

            // 设置商品价格
            if (specificationModel.curPrice == specificationModel.prePrice) {
                goods_new_price.setText(String.format(getResources().getString(R.string.price),
                        specificationModel.curPrice + ""));
                old_price.setVisibility(View.GONE);
            } else {
                goods_new_price.setText(String.format(getResources().getString(R.string.price),
                        specificationModel.curPrice + ""));
                if (specificationModel.prePrice == 0) {
                    old_price.setVisibility(View.GONE);
                } else {
                    old_price.setVisibility(View.VISIBLE);
                    old_price.setText(String.format(getResources().getString(R.string.price),
                            specificationModel.prePrice + ""));
                    old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }

            view_cart_num.setMax(specificationModel.stockNum);
            mStockTv.setText(String.format(getResources().getString(R.string.stock_max),
                    AppUtils.isEmpty(specificationModel.stockNum + "")));

            mCurrentSpeciModel = specificationModel;
        }
    }
}
