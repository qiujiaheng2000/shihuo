
package com.shihuo.shihuo.Views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Adapters.BannerViewPagerAdapter;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.util.AppUtils;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 小banner图View
 */
public class BannerView extends LinearLayout {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.indicator)
    CirclePageIndicator indicator;

    private ArrayList<View> viewList = new ArrayList<>();

    private BannerViewPagerAdapter adapter;

    public BannerView(Context context) {
        super(context);
        initViews();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_banner, null);
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
    public void setData(final List<GoodsTypeModel> list) {
        if (list.isEmpty())
            return;
        viewList.clear();
        for (int i = 0; i < list.size(); i++) {
            final GoodsTypeModel model = list.get(i);
            View viewItem = LayoutInflater.from(getContext()).inflate(R.layout.banner_view, null);
            SimpleDraweeView imageView = (SimpleDraweeView)viewItem.findViewById(R.id.image_banner);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showToast(getContext(), model.adId + "");
                }
            });
            if (model != null) {
                imageView.setImageURI(AppUtils.parse(Contants.IMAGE_URL + model.adPicUrl));
            }
            viewList.add(viewItem);
        }
        adapter.bindData(viewList);
    }

}
