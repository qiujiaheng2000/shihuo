package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.HeaderBannerView;
import com.shihuo.shihuo.models.GoodsModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/5.
 * 商品详情界面
 */

public class GoodsDetailActivity extends BaseActivity {


    @BindView(R.id.imag_left)
    ImageView leftbtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rightbtn)
    Button rightbtn;
    @BindView(R.id.thirdbtn)
    Button thirdbtn;
    @BindView(R.id.bannerview)
    HeaderBannerView bannerview;
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

    public static final String FLAG_GOODS = "flag_goods";
    @BindView(R.id.scrollview)
    ScrollView scrollview;

    public static void startGoodsDetailActivity(Context context, GoodsModel goodsModel) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(FLAG_GOODS, goodsModel);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_goods_details);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        title.setText(R.string.goods_detail);
        leftbtn.setVisibility(View.VISIBLE);
        rightbtn.setVisibility(View.VISIBLE);
        thirdbtn.setVisibility(View.VISIBLE);
        scrollview.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollview.scrollTo(0, 0);
            }
        }, 200);
    }


    @OnClick({R.id.imag_left, R.id.rightbtn, R.id.thirdbtn, R.id.layout_parameters, R.id.more_informations})
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
