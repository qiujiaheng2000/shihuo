
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * 生成二维码界面
 */

public class ZxingCreateActivity extends BaseActivity {

    @BindView(R.id.imag_left)
    ImageView leftbtn;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.iv_zxing)
    ImageView iv_zxing;

    private String id;

    private int type;

    private Bitmap mBitmap;

    /**
     * @param context
     * @param type    0 = 商铺，1 = 商品
     * @param id
     */
    public static void start(Context context, int type, String id) {
        Intent intent = new Intent(context, ZxingCreateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("id", id);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.activity_create_zxing);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        type = bundle.getInt("type");
        initViews();
    }

    public void initViews() {
        title.setText("店铺二维码");
        leftbtn.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(id)) {
            return;
        }
        String zxing = "shihuo:///"
                + (type == 0 ? Contants.ZXING_TYPE_STORE : Contants.ZXING_TYPE_GOODS) + "/" + id;
//        mBitmap = CodeUtils.createImage(zxing, 300, 300, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
//        if (mBitmap != null) {
//            iv_zxing.setImageBitmap(mBitmap);
//        }

        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        logoBitmap = QRCodeEncoder.syncEncodeQRCode(zxing, BGAQRCodeUtil.dp2px(ZxingCreateActivity.this, 150), Color.BLACK, Color.WHITE, logoBitmap);
        iv_zxing.setImageBitmap(logoBitmap);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBitmap = null;
    }
}
