
package com.shihuo.shihuo.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商铺信息展示页面
 */

public class ShopInfoActivity extends Activity {

    @BindView(R.id.imag_left)
    ImageView imagLeft;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tv_content)
    TextView tv_content;

    public static void start(Context context, String title, String content) {
        Intent intent = new Intent(context, ShopInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("content", content);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.activity_shop_info);
        ButterKnife.bind(this);
        initData();
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

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(bundle.getString("title"));
        tv_content.setText(bundle.getString("content"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
