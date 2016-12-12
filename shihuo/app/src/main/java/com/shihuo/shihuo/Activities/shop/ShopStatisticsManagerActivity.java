package com.shihuo.shihuo.Activities.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.Activities.shop.fragments.MonthFragment;
import com.shihuo.shihuo.Activities.shop.fragments.TodayFragment;
import com.shihuo.shihuo.Activities.shop.fragments.YearFragment;
import com.shihuo.shihuo.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 * 店铺统计管理界面
 */
public class ShopStatisticsManagerActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.radiobutton_today)
    RadioButton radiobuttonToday;
    @BindView(R.id.radiobutton_month)
    RadioButton radiobuttonMonth;
    @BindView(R.id.radiobutton_year)
    RadioButton radiobuttonYear;
    @BindView(R.id.tabbar)
    RadioGroup tabbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.imag_left)
    ImageView imagLeft;

    TodayFragment todayFragment;
    MonthFragment monthFragment;
    YearFragment yearFragment;

    MyViewPagerAdapter myViewPagerAdapter;

    public static final int TAB_TODAY = 0;
    public static final int TAB_MONTH = 1;
    public static final int TAB_YEAR = 2;


    public static void start(Context context) {
        Intent intent = new Intent(context, ShopStatisticsManagerActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_statistics);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.shop_statistics_title);
        todayFragment = TodayFragment.newInstance();
        monthFragment = MonthFragment.newInstance();
        yearFragment = YearFragment.newInstance();
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        myViewPagerAdapter.addFrgment(todayFragment);
        myViewPagerAdapter.addFrgment(monthFragment);
        myViewPagerAdapter.addFrgment(yearFragment);
        viewpager.setAdapter(myViewPagerAdapter);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case TAB_TODAY:
                        radiobuttonToday.setChecked(true);
                        radiobuttonToday.setSelected(true);
                        radiobuttonMonth.setSelected(false);
                        radiobuttonYear.setSelected(false);
                        break;
                    case TAB_MONTH:
                        radiobuttonMonth.setChecked(true);
                        radiobuttonMonth.setSelected(true);
                        radiobuttonToday.setSelected(false);
                        radiobuttonYear.setSelected(false);
                        break;
                    case TAB_YEAR:
                        radiobuttonYear.setChecked(true);
                        radiobuttonYear.setSelected(true);
                        radiobuttonToday.setSelected(false);
                        radiobuttonMonth.setSelected(false);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabbar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobutton_today:
                        viewpager.setCurrentItem(TAB_TODAY);
                        break;
                    case R.id.radiobutton_month:
                        viewpager.setCurrentItem(TAB_MONTH);
                        break;
                    case R.id.radiobutton_year:
                        viewpager.setCurrentItem(TAB_YEAR);
                        break;
                    default:
                        break;
                }
            }
        });
        radiobuttonToday.setChecked(true);
        radiobuttonToday.setSelected(true);
    }

    @OnClick(R.id.imag_left)
    public void onClick() {
        finish();
    }

    public static class MyViewPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments = new ArrayList<>();

        public void addFrgment(Fragment frg) {
            fragments.add(frg);
        }

        public void removeFragment(Fragment frg) {
            fragments.remove(frg);
        }


        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position > fragments.size() - 1) {
                return null;
            }
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
