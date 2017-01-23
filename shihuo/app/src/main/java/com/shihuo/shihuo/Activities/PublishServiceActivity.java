
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/2. 发布便民服务页面
 */

public class PublishServiceActivity extends BaseActivity {
    @BindView(R.id.imag_left)
    ImageView leftbtn;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.layout_phone)
    RelativeLayout layout_phone;

    @BindView(R.id.layout_public)
    RelativeLayout layout_public;


    public static void start(Context context) {
        Intent intent = new Intent(context, PublishServiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.activity_publish_service);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        title.setText("发布便民服务");
        leftbtn.setVisibility(View.VISIBLE);
    }

    @OnClick({
            R.id.imag_left, R.id.layout_phone, R.id.layout_public
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.layout_phone:
                AppUtils.callPhone(PublishServiceActivity.this, "0359-6382822");
                break;
            case R.id.layout_public:
//                AppUtils.showToast(PublishServiceActivity.this, "跳转一个h5");
                WebViewActivity.start(PublishServiceActivity.this, "http://d.eqxiu.com/s/jbwQdODI");
                break;
        }
    }
}
