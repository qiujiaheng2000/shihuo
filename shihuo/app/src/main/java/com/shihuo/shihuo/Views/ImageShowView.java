
package com.shihuo.shihuo.Views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.shihuo.shihuo.Adapters.BannerViewPagerAdapter;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.util.AppUtils;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 大图预览View
 */
public class ImageShowView extends LinearLayout {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.indicator)
    CirclePageIndicator indicator;

    private ArrayList<View> viewList = new ArrayList<>();

    private BannerViewPagerAdapter adapter;

    private Context context;

    public ImageShowView(Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    public ImageShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    public ImageShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_banner_goods_detail, null);
        ButterKnife.bind(this, view);
        addView(view);
        adapter = new BannerViewPagerAdapter(viewList, getContext());
        mViewPager.setAdapter(adapter);
        indicator.setViewPager(mViewPager);
    }

    /**
     * 绑定数据
     * 
     * @param list
     */
    public void setData(final List<String> list) {
        if (list.isEmpty())
            return;
        viewList.clear();
        for (int i = 0; i < list.size(); i++) {
            View viewItem = LayoutInflater.from(getContext()).inflate(R.layout.image_show, null);
            ZoomableDraweeView imageView = (ZoomableDraweeView)viewItem.findViewById(R.id.image_banner);
            imageView.setImageURI(AppUtils.parse(Contants.IMAGE_URL + list.get(i)));
            viewList.add(viewItem);
        }
        adapter.bindData(viewList);
    }

}
