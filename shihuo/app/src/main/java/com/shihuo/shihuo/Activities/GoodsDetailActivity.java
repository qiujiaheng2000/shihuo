
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.shop.GoodsSetParameterActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.EmptyView;
import com.shihuo.shihuo.Views.GoodsBannerView;
import com.shihuo.shihuo.Views.ShoppingCarView;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.GoodsDetailModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by cm_qiujiaheng on 2016/11/5. 商品详情界面
 */

public class GoodsDetailActivity extends BaseActivity {
    public static final String FLAG_GOODS = "flag_goods";

    @BindView(R.id.imag_left)
    ImageView leftbtn;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.rightbtn)
    Button rightbtn;

    @BindView(R.id.thirdbtn)
    Button thirdbtn;

    @BindView(R.id.view_banner)
    GoodsBannerView bannerview;

    @BindView(R.id.goods_title)
    TextView goodsTitle;

    @BindView(R.id.goods_desc)
    TextView goodsDesc;

    @BindView(R.id.new_price)
    TextView newPrice;

    @BindView(R.id.old_price)
    TextView oldPrice;

    @BindView(R.id.volume)
    TextView volume;

    @BindView(R.id.area)
    TextView area;

    @BindView(R.id.ratingbar_env)
    RatingBar ratingbarEnv;

    @BindView(R.id.capacities)
    TextView capacities;

    @BindView(R.id.delivery)
    TextView delivery;

    @BindView(R.id.layout_parameters)
    RelativeLayout layoutParameters;

    @BindView(R.id.view_shoppingCar)
    ShoppingCarView mShoppingCarView;

    @BindView(R.id.scrollview)
    ScrollView scrollview;

    @BindView(R.id.layout_image_list)
    LinearLayout mImageListLayout;

    private EmptyView mEmptyView;

    private String mGoodsId;

    private int height;

    private GoodsDetailModel mGoodsDetailModel;

    /**
     * 是否收藏过
     */
    private boolean isFav;

    public static void start(Context context, String goodsId) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(FLAG_GOODS, goodsId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.layout_goods_details);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        mGoodsId = getIntent().getStringExtra(FLAG_GOODS);
        title.setText(R.string.goods_detail);
        if (TextUtils.isEmpty(mGoodsId))
            return;
        mEmptyView = (EmptyView)findViewById(R.id.view_list_empty_layout);
        leftbtn.setVisibility(View.VISIBLE);
        rightbtn.setVisibility(View.VISIBLE);
        scrollview.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollview.scrollTo(0, 0);
            }
        }, 200);
        mEmptyView.show(true);
        height = AppUtils.getScreenWidthAndHeight(GoodsDetailActivity.this)[0];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, height);
        bannerview.setLayoutParams(params);
        mShoppingCarView.setGoBackGone();

        rightbtn.setBackground(getResources().getDrawable(R.drawable.selector_collect));

        request();
    }

    private void request() {
        String url;
        if (AppShareUitl.isLogin(GoodsDetailActivity.this)) {
            url = NetWorkHelper.API_GET_SHOP_DETAIL + "?goodsId=" + mGoodsId + "&token="
                    + AppShareUitl.getToken(GoodsDetailActivity.this);
        } else {
            url = NetWorkHelper.API_GET_SHOP_DETAIL + "?goodsId=" + mGoodsId;
        }

        try {
            OkHttpUtils.get().url(NetWorkHelper.getApiUrl(url)).build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                mEmptyView.show(false);
                                mGoodsDetailModel = GoodsDetailModel.parseStrJson(response.data);
                                setRequestData(mGoodsDetailModel);
                            } else {
                                mEmptyView.show(false);
                            }
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mEmptyView.show(false);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRequestData(GoodsDetailModel model) {
        if (model != null) {
            if (!model.goodsPicsList.isEmpty()) {
                // 设置banner图
                bannerview.setData(model.goodsPicsList);

                // 设置商品名称
                goodsTitle.setText(AppUtils.isEmpty(model.goodsName));

                // 设置商品描述
                goodsDesc.setText(AppUtils.isEmpty(model.goodsDetail));

                // 设置商品价格
                if (model.curPrice == model.prePrice) {
                    newPrice.setText(String.format(getResources().getString(R.string.price),
                            model.curPrice + ""));
                    oldPrice.setVisibility(View.GONE);
                } else {
                    newPrice.setText(String.format(getResources().getString(R.string.price),
                            model.curPrice + ""));
                    if (model.prePrice == 0) {
                        oldPrice.setVisibility(View.GONE);
                    } else {
                        oldPrice.setVisibility(View.VISIBLE);
                        oldPrice.setText(String.format(getResources().getString(R.string.price),
                                model.prePrice + ""));
                        oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }

                // 设置商品销量
                volume.setText(String.format(getResources().getString(R.string.sales),
                        model.salesNum + ""));

                // 设置商圈信息
                area.setText(AppUtils.isEmpty(model.circleName));

                // 设置配送方式
                StringBuilder builder = new StringBuilder();
                builder.append(getResources().getString(R.string.delivery));
                if (model.takeGoods == 1) {
                    builder.append("   " + getResources().getString(R.string.delivery1));
                }
                if (model.courierDelivery == 1) {
                    builder.append("/" + getResources().getString(R.string.delivery2));
                }
                if (model.noShipFees == 1) {
                    builder.append("/" + getResources().getString(R.string.delivery3));
                }
                delivery.setText(builder);

                // 设置图片详情
                if (model.goodsDetailPicsList.size() > 0) {
                    for (int i = 0; i < model.goodsDetailPicsList.size(); i++) {
                        SimpleDraweeView image = new SimpleDraweeView(GoodsDetailActivity.this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, height);
                        if (i != model.goodsDetailPicsList.size() - 1) {
                            params.setMargins(0, 0, 0, AppUtils.dip2px(GoodsDetailActivity.this, 5));
                        }
                        image.setLayoutParams(params);
                        image.setImageURI(AppUtils.parse(Contants.IMAGE_URL
                                + model.goodsDetailPicsList.get(i).picUrl));
                        mImageListLayout.addView(image);
                    }
                }

                // 设置收藏信息
                if (model.isFav == 0) {
                    rightbtn.setSelected(false);
                    isFav = false;
                } else {
                    rightbtn.setSelected(true);
                    isFav = true;
                }
            }
        }
    }

    private void requestFavGoods(String url) {
        if (!mDialog.isShowing())
            mDialog.show();
        try {
            JSONObject params = new JSONObject();
            params.put("goodsId", mGoodsDetailModel.goodsId);
            OkHttpUtils.postString().url(NetWorkHelper.getApiUrl(url))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(params.toString()).build().execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                if (isFav) {
                                    isFav = false;
                                    rightbtn.setSelected(false);
                                } else {
                                    isFav = true;
                                    rightbtn.setSelected(true);
                                }
                            }
                            if (mDialog.isShowing())
                                mDialog.dismiss();
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            if (mDialog.isShowing())
                                mDialog.dismiss();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({
            R.id.imag_left, R.id.rightbtn, R.id.layout_parameters, R.id.btn_buy_now,
            R.id.btn_shopping_card, R.id.btn_service, R.id.btn_shop, R.id.btn_share
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.rightbtn: // 商品收藏
                if (isFav) {
                    requestFavGoods(NetWorkHelper.API_POST_UN_FAV_GOODS + "?token="
                            + AppShareUitl.getToken(GoodsDetailActivity.this));
                } else {
                    requestFavGoods(NetWorkHelper.API_POST_FAV_GOODS + "?token="
                            + AppShareUitl.getToken(GoodsDetailActivity.this));
                }
                break;
            case R.id.layout_parameters:

                break;
            case R.id.btn_shop: // 进入店铺
                AppUtils.showToast(GoodsDetailActivity.this, "进入店铺");
                break;
            case R.id.btn_share: // 分享
                AppUtils.showToast(GoodsDetailActivity.this, "分享");

                break;
            case R.id.btn_service:// 客服电话
                if (mGoodsDetailModel != null && !TextUtils.isEmpty(mGoodsDetailModel.csPhoneNum)) {
                    AppUtils.callPhone(GoodsDetailActivity.this, mGoodsDetailModel.csPhoneNum);
                } else {
                    AppUtils.showToast(GoodsDetailActivity.this,
                            getResources().getString(R.string.toast_no_phone));
                }
                break;
            case R.id.btn_shopping_card: // 加入购物车
                if (AppShareUitl.isLogin(GoodsDetailActivity.this)) {
                    GoodsSetParameterActivity.start(GoodsDetailActivity.this, mGoodsDetailModel);
                } else {
                    LoginActivity.start(GoodsDetailActivity.this);
                }
                break;
            case R.id.btn_buy_now: // 立即购买
                if (AppShareUitl.isLogin(GoodsDetailActivity.this)) {
                    GoodsSetParameterActivity.start(GoodsDetailActivity.this, mGoodsDetailModel);
                } else {
                    LoginActivity.start(GoodsDetailActivity.this);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
