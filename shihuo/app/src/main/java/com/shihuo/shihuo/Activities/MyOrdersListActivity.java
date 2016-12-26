package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
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
import com.shihuo.shihuo.fragments.MyOrderListFragment;
import com.shihuo.shihuo.models.OrderModel;
import com.shihuo.shihuo.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/3.
 * 我的订单列表
 */

public class MyOrdersListActivity extends BaseActivity {

    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.indicator)
    TabPageIndicator indicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;


    private TabAdapter adapter;
    private int mCurrentIndex;

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            mCurrentIndex = position;
//            setTitleText(mGoodsTypeList.get(position).typeName);
//            AppUtils.showToast(GoodsListByTypeActivity.this, mGoodsTypeList.get(position).typeName);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public static class OrderType implements Parcelable {
        public String typeName;//订单类型名称
        public String status;//订单类型

        public OrderType(String typeName, String status) {
            this.typeName = typeName;
            this.status = status;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.typeName);
            dest.writeString(this.status);
        }

        protected OrderType(Parcel in) {
            this.typeName = in.readString();
            this.status = in.readString();
        }

        public static final Parcelable.Creator<OrderType> CREATOR = new Parcelable.Creator<OrderType>() {
            @Override
            public OrderType createFromParcel(Parcel source) {
                return new OrderType(source);
            }

            @Override
            public OrderType[] newArray(int size) {
                return new OrderType[size];
            }
        };
    }

    private ArrayList<OrderType> orderTypes = new ArrayList<>();
    private ArrayList<OrderModel> orderModelArrayList = new ArrayList<>();


    public static void startMyOrdersListActivity(Context context) {
        Intent intent = new Intent(context, MyOrdersListActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_orders_list);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText("我的订单");
        orderTypes.add(new OrderType("全部", "all"));
        orderTypes.add(new OrderType("待发货", "1"));
        orderTypes.add(new OrderType("待收货", "2"));
        orderTypes.add(new OrderType("已完成", "3"));
        orderTypes.add(new OrderType("售后中", "sc"));
//        orderTypes.add(new OrderType("已退货", "5"));
//        orderTypes.add(new OrderType("客服处理中", "6"));
//        orderTypes.add(new OrderType("已关闭", "7"));
        adapter = new TabAdapter(getSupportFragmentManager(), orderTypes);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        indicator.setOnPageChangeListener(mOnPageChangeListener);
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
        indicator.setTextSize(AppUtils.dip2px(MyOrdersListActivity.this, 14));// 设置字体大小
    }

    @OnClick(R.id.imag_left)
    public void onClick() {
        finish();
    }

    class TabAdapter extends FragmentPagerAdapter {

        private List<OrderType> data;

        public TabAdapter(FragmentManager fm, List<OrderType> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public Fragment getItem(int position) {
            return MyOrderListFragment.newInstance(data.get(position));
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
