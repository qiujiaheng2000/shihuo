
package com.shihuo.shihuo.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 分享弹框 Created by lishuai on 16/12/10.
 */

public class ShareDialog extends Activity {

    private final static String TAG = "ShareDialog";

    public static void start(Context context) {
        Intent intent = new Intent(context, ShareDialog.class);
        context.startActivity(intent);
    }

    @BindView(R.id.layout_weixin_firend)
    LinearLayout layout_weixin_firend;

    @BindView(R.id.layout_weixin_circle)
    LinearLayout layout_weixin_circle;

    @BindView(R.id.layout_qq_friend)
    LinearLayout layout_qq_friend;

    @BindView(R.id.layout_qq_qzone)
    LinearLayout layout_qq_qzone;

    @BindView(R.id.layout_weibo)
    LinearLayout layout_weibo;

    @BindView(R.id.tv_cancle)
    TextView tv_cancle;

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);
        ButterKnife.bind(this);
        context = this;
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        getWindow().setAttributes(lp);

    }

    @OnClick({
            R.id.layout_weixin_firend, R.id.layout_weixin_circle, R.id.layout_qq_friend,
            R.id.layout_qq_qzone, R.id.layout_weibo
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_weixin_firend:

                break;
            case R.id.layout_weixin_circle:

                break;
            case R.id.layout_qq_friend:

                break;
            case R.id.layout_qq_qzone:

                break;
            case R.id.layout_weibo:

                break;
            case R.id.tv_cancle:
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.fade_out);

    }

}
