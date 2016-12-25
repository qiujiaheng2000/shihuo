package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.util.AppUtils;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shihuo.shihuo.R.id.iv_zxing;

/**
 * 二维码扫描
 * Created by lishuai on 16/12/25.
 */

public class ZxingLookActivity  extends BaseActivity{
    @BindView(R.id.imag_left)
    ImageView leftbtn;

    @BindView(R.id.title)
    TextView title;

    /**
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, ZxingLookActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.activity_look_zxing);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        title.setText("扫描二维码");
        leftbtn.setVisibility(View.VISIBLE);
        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//            Intent resultIntent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
//            bundle.putString(CodeUtils.RESULT_STRING, result);
            AppUtils.showToast(ZxingLookActivity.this, result);
//            resultIntent.putExtras(bundle);
//            ZxingLookActivity.this.setResult(RESULT_OK, resultIntent);
//            ZxingLookActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            AppUtils.showToast(ZxingLookActivity.this, "扫描失败");
//            Intent resultIntent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
//            bundle.putString(CodeUtils.RESULT_STRING, "");
//            resultIntent.putExtras(bundle);
//            ZxingLookActivity.this.setResult(RESULT_OK, resultIntent);
//            ZxingLookActivity.this.finish();
        }
    };

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
