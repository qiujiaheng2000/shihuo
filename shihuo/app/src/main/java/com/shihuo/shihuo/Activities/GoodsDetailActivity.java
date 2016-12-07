
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.EmptyView;
import com.shihuo.shihuo.Views.GoodsBannerView;
import com.shihuo.shihuo.Views.ShoppingCarView;
import com.shihuo.shihuo.models.GoodsDetailModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

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

    @BindView(R.id.more_informations)
    Button moreInformations;

    @BindView(R.id.webview)
    WebView webview;

    @BindView(R.id.view_shoppingCar)
    ShoppingCarView mShoppingCarView;

    @BindView(R.id.scrollview)
    ScrollView scrollview;

    private EmptyView mEmptyView;

    private String mGoodsId;

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
        thirdbtn.setVisibility(View.VISIBLE);
        scrollview.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollview.scrollTo(0, 0);
            }
        }, 200);
        mEmptyView.show(true);
        int height = AppUtils.getScreenWidthAndHeight(GoodsDetailActivity.this)[0];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, height);
        bannerview.setLayoutParams(params);
        mShoppingCarView.setGoBackGone();
        request();
    }

    private void request() {
        String url = NetWorkHelper.API_GET_SHOP_DETAIL + "?goodsId=" + mGoodsId;
        try {
            OkHttpUtils.get().url(NetWorkHelper.getApiUrl(url)).build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                mEmptyView.show(false);
                                GoodsDetailModel model = GoodsDetailModel.parseStrJson(response.data);
                                setRequestData(model);
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

    private void setRequestData(GoodsDetailModel model){
        if(model != null){
            if(!model.goodsPicsList.isEmpty()){
                // 设置banner图
                bannerview.setData(model.goodsPicsList);
                goodsTitle.setText(AppUtils.isEmpty(model.goodsName));
                goodsDesc.setText(AppUtils.isEmpty(model.goodsDetail));
                area.setText(AppUtils.isEmpty(model.circleName));
                StringBuilder builder = new StringBuilder();
                builder.append(getResources().getString(R.string.delivery));
                if(model.takeGoods ==1){
                    builder.append("   " + getResources().getString(R.string.delivery1));
                }
                if(model.courierDelivery ==1){
                    builder.append("/" + getResources().getString(R.string.delivery2));
                }
                if(model.noShipFees ==1){
                    builder.append("/" + getResources().getString(R.string.delivery3));
                }
                delivery.setText(builder);
//                goodsDesc.setText(Html.fromHtml(model.goodsRichTextDetail));
                webview.loadUrl(model.goodsRichTextDetail);
            }
        }
    }

    @OnClick({
            R.id.imag_left, R.id.rightbtn, R.id.thirdbtn, R.id.layout_parameters,
            R.id.more_informations
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.rightbtn:
                break;
            case R.id.thirdbtn:
                break;
            case R.id.layout_parameters:
                break;
            case R.id.more_informations:
                break;
        }
    }
}
