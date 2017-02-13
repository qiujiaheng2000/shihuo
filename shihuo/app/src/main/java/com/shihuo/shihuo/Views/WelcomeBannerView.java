
package com.shihuo.shihuo.Views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shihuo.shihuo.Adapters.BannerViewPagerAdapter;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 引导图banner
 */
public class WelcomeBannerView extends LinearLayout {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.indicator)
    CirclePageIndicator indicator;

    private ArrayList<View> viewList = new ArrayList<>();

    private BannerViewPagerAdapter adapter;

    private Context context;

    public WelcomeBannerView(Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    public WelcomeBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    public WelcomeBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
//        initData();
    }

    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_banner_goods_detail,
                null);
        ButterKnife.bind(this, view);
        addView(view);
        adapter = new BannerViewPagerAdapter(viewList, getContext());
        mViewPager.setAdapter(adapter);
        indicator.setViewPager(mViewPager);
    }

    public void initData() {
        viewList.clear();
        for (int i = 0; i < 4; i++) {
            View viewItem = LayoutInflater.from(getContext()).inflate(R.layout.view_splash_image, null);
            ImageView imageView = (ImageView) viewItem.findViewById(R.id.image_banner);
            if (i == 0) {
                imageView.setBackgroundResource(R.mipmap.bg_splash_1);
            } else if (i == 1) {
                imageView.setBackgroundResource(R.mipmap.bg_splash_2);
            } else if (i == 2) {
                imageView.setBackgroundResource(R.mipmap.bg_splash_3);
            } else if (i == 3) {
                imageView.setBackgroundResource(R.mipmap.bg_splash_4);
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnLastClickListener.onLastClickListener();
                        AppShareUitl.saveIsFirstInstall(context, false);
                    }
                });
            }
            viewList.add(viewItem);
        }
        adapter.bindData(viewList);
    }

    public void setOnLastClickListener(OnLastClickListener listener){
        this.mOnLastClickListener = listener;
    }

    private OnLastClickListener mOnLastClickListener;
    public interface OnLastClickListener{
        void onLastClickListener();
    }

}
