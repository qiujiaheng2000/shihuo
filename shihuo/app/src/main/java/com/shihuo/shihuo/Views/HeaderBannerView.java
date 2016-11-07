package com.shihuo.shihuo.Views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.shihuo.shihuo.Adapters.BannerViewPagerAdapter;
import com.shihuo.shihuo.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cm_qiujiaheng on 2016/11/5.
 * bannerView
 */

public class HeaderBannerView extends LinearLayout {

    @BindView(R.id.binner)
    ViewPager banner;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;

    private ArrayList<View> viewList = new ArrayList<>();

    public HeaderBannerView(Context context) {
        super(context);
        initViews();
    }

    public HeaderBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public HeaderBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_banner_view_header, null);
        addView(view);
        ButterKnife.bind(this, view);
        initBanner(LayoutInflater.from(getContext()));
    }

    private void initBanner(LayoutInflater inflater) {
        View view02 = inflater.inflate(R.layout.banner_view, null);
        View view03 = inflater.inflate(R.layout.banner_view, null);
        View view04 = inflater.inflate(R.layout.banner_view, null);

        viewList.add(view02);
        viewList.add(view03);
        viewList.add(view04);

        BannerViewPagerAdapter adapter = new BannerViewPagerAdapter(this.viewList, getContext());
        banner.setAdapter(adapter);
        indicator.setViewPager(banner);
    }

}
