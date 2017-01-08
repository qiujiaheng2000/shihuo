
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.TabPageIndicator;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.fragments.CircleListFragment;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商圈列表界面
 */

public class CircleListActivity extends BaseActivity {
    private final static String TAG_MODEL = "GoodsTypeModel";

    private final static String TAG_INDEX = "index";

    @BindView(R.id.imag_left)
    ImageView leftbtn;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.indicator)
    TabPageIndicator indicator;

    // private GoodsTypeModel mGoodsTypeModel;
    private int mCurrentIndex;

    private TabAdapter adapter;

    private List<GoodsTypeModel> mTypeList = new ArrayList<>();

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            mCurrentIndex = position;
            AppUtils.showToast(CircleListActivity.this, mTypeList.get(position).typeName);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public static void start(Context context, int position) {
        Intent intent = new Intent(context, CircleListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TAG_INDEX, position);
        // bundle.putParcelable(TAG_MODEL, model);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.activity_circle_list);
        ButterKnife.bind(this);
        mCurrentIndex = getIntent().getIntExtra(TAG_INDEX, 0);
        // mGoodsTypeModel = getIntent().getParcelableExtra(TAG_MODEL);
        // if (mGoodsTypeModel == null) {
        // return;
        // }

        initViews();
    }

    public void initViews() {
        title.setText(R.string.title_circle_list);
        leftbtn.setVisibility(View.VISIBLE);

        mTypeList = GoodsTypeModel.parseStrJson(AppShareUitl.getSysCircleType(CircleListActivity.this));
        adapter = new TabAdapter(getSupportFragmentManager(), mTypeList);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        indicator.setOnPageChangeListener(mOnPageChangeListener);
        viewPager.setCurrentItem(mCurrentIndex);
        initTabPagerIndicator();
    }

    private void initTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_NOWEIGHT_EXPAND_NOSAME);// 设置模式，一定要先设置模式
//        indicator.setDividerColor(Color.parseColor("#00bbcf"));// 设置分割线的颜色
//        indicator.setDividerPadding(AppUtils.dip2px(CircleListActivity.this, 10));
        indicator.setIndicatorColor(getResources().getColor(R.color.common_theme));// 设置底部导航线的颜色
        indicator.setUnderlineHeight(0);
        indicator.setTextColorSelected(getResources().getColor(R.color.common_theme));// 设置tab标题选中的颜色
        indicator.setTextColor(getResources().getColor(R.color.common_font_black));// 设置tab标题未被选中的颜色
        indicator.setTextSize(AppUtils.dip2px(CircleListActivity.this, 14));// 设置字体大小
    }

    @OnClick({
            R.id.imag_left
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
        }
    }

    class TabAdapter extends FragmentPagerAdapter {

        private List<GoodsTypeModel> data;

        public TabAdapter(FragmentManager fm, List<GoodsTypeModel> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public Fragment getItem(int position) {
            return CircleListFragment.newInstance(data.get(position), position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TextUtils.isEmpty(data.get(position).circleName) ? "" : data.get(position).circleName;
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        public void clear() {
            if (data != null && !data.isEmpty()) {
                data.clear();
                notifyDataSetChanged();
                data = null;
            }
        }
    }
}
