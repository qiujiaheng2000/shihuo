package com.shihuo.shihuo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.shihuo.shihuo.fragments.HomeFragment;
import com.shihuo.shihuo.fragments.MeFragment;
import com.shihuo.shihuo.fragments.ServiceFragment;
import com.shihuo.shihuo.fragments.VideoFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tabbar)
    RadioGroup tabbar;
    @BindView(R.id.home)
    RadioButton home;
    @BindView(R.id.video)
    RadioButton video;
    @BindView(R.id.service)
    RadioButton service;
    @BindView(R.id.me)
    RadioButton me;

    HomeFragment mHomeFragment;
    VideoFragment mVideoFragment;
    ServiceFragment mServiceFragment;
    MeFragment mMeFragment;
    MainViewPagerAdapter mainViewPagerAdapter;

    public static final int TAB_HOME = 0;
    public static final int TAB_VIDEO = 1;
    public static final int TAB_SERVICE = 2;
    public static final int TAB_ME = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mHomeFragment = HomeFragment.newInstance();
        mVideoFragment = VideoFragment.newInstance();
        mServiceFragment = ServiceFragment.newInstance();
        mMeFragment = MeFragment.newInstance();
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFrgment(mHomeFragment);
        mainViewPagerAdapter.addFrgment(mVideoFragment);
        mainViewPagerAdapter.addFrgment(mServiceFragment);
        mainViewPagerAdapter.addFrgment(mMeFragment);
        viewpager.setAdapter(mainViewPagerAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case TAB_HOME:
                        home.setChecked(true);
                        break;
                    case TAB_VIDEO:
                        video.setChecked(true);
                        break;
                    case TAB_SERVICE:
                        service.setChecked(true);
                        break;
                    case TAB_ME:
                        me.setChecked(true);
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
                    case R.id.home:
                        viewpager.setCurrentItem(TAB_HOME);
                        break;
                    case R.id.video:
                        viewpager.setCurrentItem(TAB_VIDEO);
                        break;
                    case R.id.service:
                        viewpager.setCurrentItem(TAB_SERVICE);
                        break;
                    case R.id.me:
                        viewpager.setCurrentItem(TAB_ME);
                        break;
                    default:
                        break;
                }
            }
        });
        home.setChecked(true);
    }

    public static class MainViewPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments = new ArrayList<>();

        public void addFrgment(Fragment frg) {
            fragments.add(frg);
        }

        public void removeFragment(Fragment frg) {
            fragments.remove(frg);
        }


        public MainViewPagerAdapter(FragmentManager fm) {
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
