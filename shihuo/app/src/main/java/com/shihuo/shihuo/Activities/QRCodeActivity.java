package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.AppSchemeHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by cm_qiujiaheng on 2017/1/9.
 * 二维码扫描界面
 */

public class QRCodeActivity extends BaseActivity implements QRCodeView.Delegate {
    private static final String TAG = QRCodeActivity.class.getSimpleName();

    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.zxingview)
    ZXingView zxingview;

    public static void start(Context context) {
        Intent intent = new Intent(context, QRCodeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_qrcode);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        title.setVisibility(View.VISIBLE);
        title.setText("扫描二维码");
        imagLeft.setVisibility(View.VISIBLE);
        zxingview.setDelegate(this);
    }

    @OnClick(R.id.imag_left)
    public void onClick() {
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        zxingview.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        zxingview.showScanRect();
        zxingview.startSpot();
    }

    @Override
    protected void onStop() {
        zxingview.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        zxingview.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        AppSchemeHelper.dealScheme(QRCodeActivity.this, result);
        vibrate();
        zxingview.startSpot();
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }
}
