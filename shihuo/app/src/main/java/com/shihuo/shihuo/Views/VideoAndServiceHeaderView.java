package com.shihuo.shihuo.Views;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.shihuo.shihuo.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiahengqiu on 2016/10/23.
 * 便民服务、微视频的headerview
 */

public class VideoAndServiceHeaderView extends LinearLayout {

    @BindView(R.id.binner)
    ViewPager banner;

    @BindView(R.id.indicator)
    CirclePageIndicator indicator;
    @BindView(R.id.label_view)
    AutoLabelUI labelView;
    @BindView(R.id.autolabel_title)
    TextView autolabelTitle;

    private ArrayList<View> viewList = new ArrayList<>();

    public VideoAndServiceHeaderView(Context context) {
        super(context);
        initView();
    }

    public VideoAndServiceHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VideoAndServiceHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.video_service_header_view, null);
        ButterKnife.bind(this, view);
        View view02 = inflater.inflate(R.layout.banner_view, null);
        View view03 = inflater.inflate(R.layout.banner_view, null);
        View view04 = inflater.inflate(R.layout.banner_view, null);

        viewList.add(view02);
        viewList.add(view03);
        viewList.add(view04);

        BannerViewPagerAdapter adapter = new BannerViewPagerAdapter();
        banner.setAdapter(adapter);
        indicator.setViewPager(banner);
        addView(view);
    }

    public void addAutoLabels(ArrayList<String> labels) {
        for (int i = 0; i < labels.size(); i++) {
            labelView.addLabel(labels.get(i), i);
        }
    }

    public void setAutolabelTitle(int resId) {
        autolabelTitle.setText(resId);
    }

    public void setListeners(AutoLabelUI.OnLabelsCompletedListener onLabelsCompletedListener,
                              AutoLabelUI.OnRemoveLabelListener onRemoveLabelListener,
                              AutoLabelUI.OnLabelsEmptyListener onLabelsEmptyListener,
                              AutoLabelUI.OnLabelClickListener onLabelClickListener) {
        labelView.setOnLabelsCompletedListener(onLabelsCompletedListener);

        labelView.setOnRemoveLabelListener(onRemoveLabelListener);

        labelView.setOnLabelsEmptyListener(onLabelsEmptyListener);

        labelView.setOnLabelClickListener(onLabelClickListener);

    }

    public class BannerViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(viewList.get(position));

        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            //直接用适配器来完成标题的显示，所以从上面可以看到，我们没有使用PagerTitleStrip。当然你可以使用。
//            return titleList.get(position);
//
//        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            //这个需要注意，我们是在重写adapter里面实例化button组件的，如果你在onCreate()方法里这样做会报错的。
            return viewList.get(position);
        }
    }
}
