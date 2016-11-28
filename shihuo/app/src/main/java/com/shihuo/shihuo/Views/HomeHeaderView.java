package com.shihuo.shihuo.Views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.PrefectureActivity;
import com.shihuo.shihuo.Adapters.BannerViewPagerAdapter;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.HomeHorScrollConfigModel;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jiahengqiu on 2016/10/23.
 */

public class HomeHeaderView extends LinearLayout {

    @BindView(R.id.binner)
    ViewPager banner;
    @BindView(R.id.ten_prefecture)
    SimpleDraweeView tenPrefecture;
    @BindView(R.id.twenty_prefecture)
    SimpleDraweeView twentyPrefecture;
    @BindView(R.id.thirty_prefecture)
    SimpleDraweeView thirtyPrefecture;
    @BindView(R.id.sales_prefecture)
    SimpleDraweeView salesPrefecture;
    @BindView(R.id.card_layout)
    LinearLayout cardLayout;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;
    @BindView(R.id.layout_horizontalscrollvier_goods)
    LinearLayout layoutHorizontalscrollvierGoods;
    @BindView(R.id.layout_horizontalscrollvier_shops)
    LinearLayout layoutHorizontalscrollvierShops;
    @BindView(R.id.layout_horizontalscrollvier)
    HorizontalScrollView layoutHorizontalscrollvier;
    @BindView(R.id.list_title)
    TextView listTitle;
    @BindView(R.id.layout_horizontalscrollvier_2)
    HorizontalScrollView layoutHorizontalscrollvier2;


    private ArrayList<View> viewList = new ArrayList<>();
    private Context mContext;

    public HomeHeaderView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HomeHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public HomeHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.home_header_view, null);
        ButterKnife.bind(this, view);
        initBanner(inflater);
        addView(view);
    }

    /**
     * 配置顶部横向滚动栏
     */
    public void setHorScrollViewDatas(HomeHorScrollConfigModel horScrollViewDatas) {
        if (null != horScrollViewDatas &&
                (horScrollViewDatas.goodsScrolls.size() > 0 || horScrollViewDatas.shopsScrolls.size() > 0)) {
            layoutHorizontalscrollvier.setVisibility(View.VISIBLE);
            layoutHorizontalscrollvier2.setVisibility(View.VISIBLE);
            if (horScrollViewDatas.goodsScrolls.size() > 0) {
                layoutHorizontalscrollvierGoods.setVisibility(View.VISIBLE);

                for (int i = 0; i < horScrollViewDatas.goodsScrolls.size(); i++) {
                    final HomeHorScrollConfigModel.HorScrollItemModel itemModel = horScrollViewDatas.goodsScrolls.get(i);
                    View viewItem = LayoutInflater.from(getContext()).inflate(R.layout.layout_home_horscrollview_item, null);
                    SimpleDraweeView image = (SimpleDraweeView) viewItem.findViewById(R.id.image);
                    TextView name = (TextView) viewItem.findViewById(R.id.name);
                    name.setText(itemModel.name);
                    layoutHorizontalscrollvierGoods.addView(viewItem);
                    viewItem.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "id = " + itemModel.Id + ", name = " + itemModel.name, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            if (horScrollViewDatas.shopsScrolls.size() > 0) {
                layoutHorizontalscrollvierShops.setVisibility(View.VISIBLE);

                for (int i = 0; i < horScrollViewDatas.shopsScrolls.size(); i++) {

                    final HomeHorScrollConfigModel.HorScrollItemModel itemModel = horScrollViewDatas.shopsScrolls.get(i);

                    View viewItem = LayoutInflater.from(getContext()).inflate(R.layout.layout_home_horscrollview_item, null);
                    SimpleDraweeView image = (SimpleDraweeView) viewItem.findViewById(R.id.image);
                    TextView name = (TextView) viewItem.findViewById(R.id.name);
                    name.setText(horScrollViewDatas.shopsScrolls.get(i).name);
                    layoutHorizontalscrollvierShops.addView(viewItem);
                    viewItem.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "id = " + itemModel.Id + ", name = " + itemModel.name, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        }
    }

    private void initBanner(LayoutInflater inflater) {
        View view02 = inflater.inflate(R.layout.banner_view, null);
        View view03 = inflater.inflate(R.layout.banner_view, null);
        View view04 = inflater.inflate(R.layout.banner_view, null);

        viewList.add(view02);
        viewList.add(view03);
        viewList.add(view04);

        BannerViewPagerAdapter adapter = new BannerViewPagerAdapter(viewList, getContext());
        banner.setAdapter(adapter);
        indicator.setViewPager(banner);
    }

    @OnClick({R.id.ten_prefecture, R.id.twenty_prefecture, R.id.thirty_prefecture, R.id.sales_prefecture})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ten_prefecture:
                PrefectureActivity.startPrefectureActivity(mContext, 1);
                break;
            case R.id.twenty_prefecture:
                PrefectureActivity.startPrefectureActivity(mContext, 2);
                break;
            case R.id.thirty_prefecture:
                PrefectureActivity.startPrefectureActivity(mContext, 3);
                break;
            case R.id.sales_prefecture:
                PrefectureActivity.startPrefectureActivity(mContext, 4);
                break;
        }
    }

    @OnClick(R.id.layout_horizontalscrollvier_2)
    public void onClick() {
    }
}
