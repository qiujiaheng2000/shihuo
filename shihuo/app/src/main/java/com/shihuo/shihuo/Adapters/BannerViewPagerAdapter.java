
package com.shihuo.shihuo.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BannerViewPagerAdapter extends PagerAdapter {
    private List<View> mViewList;

    private Context mContext;

    public BannerViewPagerAdapter(List<View> viewList, Context mContext) {
        this.mViewList = viewList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));

    }

    @Override
    public int getItemPosition(Object object) {

        return super.getItemPosition(object);
    }

    public void bindData(List<View> list) {
        mViewList = list;
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view);
        // 这个需要注意，我们是在重写adapter里面实例化button组件的，如果你在onCreate()方法里这样做会报错的。
        return mViewList.get(position);
    }
}
