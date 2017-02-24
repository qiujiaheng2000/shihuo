
package com.shihuo.shihuo.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.shihuo.shihuo.MainActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.WelcomeBannerView;
import com.shihuo.shihuo.application.AppShareUitl;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 引导界面
 */

public class SplashActivity extends Activity {

    @BindView(R.id.layout_splash)
    RelativeLayout layout_splash;

    @BindView(R.id.view_banner)
    WelcomeBannerView view_banner;

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    };

    public static void start(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppUtils.fullScreenColor(this);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        if (AppShareUitl.isFirstInstall(SplashActivity.this)) {
            layout_splash.setVisibility(View.GONE);
            view_banner.setVisibility(View.VISIBLE);
            view_banner.initData();
            view_banner.setOnLastClickListener(new WelcomeBannerView.OnLastClickListener() {
                @Override
                public void onLastClickListener() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            view_banner.setVisibility(View.GONE);
            layout_splash.setVisibility(View.VISIBLE);
            mHandler.postDelayed(mRunnable, 3000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }


}
