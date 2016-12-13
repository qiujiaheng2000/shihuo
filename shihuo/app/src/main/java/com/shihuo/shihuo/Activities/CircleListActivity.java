
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.Adapters.TabFragmentAdapter;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.fragments.CircleListFragment;
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

    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    // private GoodsTypeModel mGoodsTypeModel;
    private int mCurrentIndex;

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
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Fragment fragment = new CircleListFragment();
            fragments.add(fragment);
        }
        viewPager.setAdapter(new TabFragmentAdapter(fragments, getSupportFragmentManager(), this));
        // 将ViewPager和TabLayout绑定
        tablayout.setupWithViewPager(viewPager);
        // 设置tab文本的没有选中（第一个参数）和选中（第二个参数）的颜色
        tablayout.setTabTextColors(getResources().getColor(R.color.common_theme),
                R.color.common_font_black);
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
}
