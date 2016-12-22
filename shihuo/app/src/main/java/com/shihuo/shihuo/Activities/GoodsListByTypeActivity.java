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

import com.shihuo.shihuo.Adapters.GoodsGrideListAdapter;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.TabPageIndicator;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.fragments.GoodsListByTypeFragment;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.HomeModel;
import com.shihuo.shihuo.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/12/14.
 * 类别商品列表页面
 */

public class GoodsListByTypeActivity extends BaseActivity {


    public static final String LIST_TYPE = "listType";

    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.indicator)
    TabPageIndicator indicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    //记录当前列表的分类
    private int mFlag;

    //点击的商品类型
    private GoodsTypeModel mGoodsTypeModel;

    private GoodsGrideListAdapter mAdapter;
    private List<HomeModel> mList = new ArrayList<>();
    private int mCurrentIndex;


    //系统分类
    private List<GoodsTypeModel> mGoodsTypeList = new ArrayList<>();
    private TabAdapter adapter;
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            mCurrentIndex = position;
            setTitleText(mGoodsTypeList.get(position).typeName);
//            AppUtils.showToast(GoodsListByTypeActivity.this, mGoodsTypeList.get(position).typeName);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    public static void start(Context context, GoodsTypeModel flag) {
        Intent intent = new Intent(context, GoodsListByTypeActivity.class);
        intent.putExtra(LIST_TYPE, flag);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_goodsbytype_list);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        mGoodsTypeModel = getIntent().getParcelableExtra(LIST_TYPE);
        imagLeft.setVisibility(View.VISIBLE);
        setTitleText(mGoodsTypeModel.typeName);
        mGoodsTypeList = GoodsTypeModel.parseStrJson(AppShareUitl.getSysGoodsType(GoodsListByTypeActivity.this));
        adapter = new TabAdapter(getSupportFragmentManager(), mGoodsTypeList);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        indicator.setOnPageChangeListener(mOnPageChangeListener);
        initTabPagerIndicator();
    }

    private void setTitleText(String titleText) {
        title.setText(String.format(getResources().getString(R.string.goodslist_by_type), titleText));
    }

    private void initTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_NOWEIGHT_EXPAND_NOSAME);// 设置模式，一定要先设置模式
//        indicator.setDividerColor(Color.parseColor("#00bbcf"));// 设置分割线的颜色
//        indicator.setDividerPadding(AppUtils.dip2px(CircleListActivity.this, 10));
        indicator.setIndicatorColor(getResources().getColor(R.color.common_theme));// 设置底部导航线的颜色
        indicator.setUnderlineHeight(0);
        indicator.setTextColorSelected(getResources().getColor(R.color.common_theme));// 设置tab标题选中的颜色
        indicator.setTextColor(getResources().getColor(R.color.common_font_black));// 设置tab标题未被选中的颜色
        indicator.setTextSize(AppUtils.dip2px(GoodsListByTypeActivity.this, 16));// 设置字体大小
    }


    @OnClick({R.id.imag_left})
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
            return GoodsListByTypeFragment.newInstance(data.get(position).typeId);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TextUtils.isEmpty(data.get(position).typeName) ? "" : data.get(position).typeName;
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
