
package com.shihuo.shihuo.dialog;


import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shihuo.shihuo.R;

/**
 * 版本更新
 * Created by lishuai on 16/8/30.
 */
public class AlertDialog implements View.OnClickListener {
    Context context;

    android.app.AlertDialog ad;

    TextView titleView;

    TextView messageView;

    LinearLayout buttonLayout;

    private Button btn_left, btn_right;

    public AlertDialog(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        ad = new android.app.AlertDialog.Builder(context).create();
        ad.show();
        // 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        Window window = ad.getWindow();
        window.setContentView(R.layout.dialog_ok_cancle);
        titleView = (TextView)window.findViewById(R.id.title);
        btn_left = (Button)window.findViewById(R.id.btn_left);
        btn_right = (Button)window.findViewById(R.id.btn_right);
        messageView = (TextView)window.findViewById(R.id.message);
        buttonLayout = (LinearLayout)window.findViewById(R.id.buttonLayout);

        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
    }

    public void setDialogContent(String title, String content, String btnLeft, String btnRight) {
        titleView.setText(title);
        messageView.setText(content);
        btn_left.setText(btnLeft);
        btn_right.setText(btnRight);
    }

    public void setLeftRightClick(OnLeftRightClick listener) {
        mOnLeftRightClick = listener;
    }

    OnLeftRightClick mOnLeftRightClick;

    public interface OnLeftRightClick {
        public void onLeftClick();

        public void onRightClick();
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        ad.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                mOnLeftRightClick.onLeftClick();
                break;

            case R.id.btn_right:
                mOnLeftRightClick.onRightClick();
                break;
        }
    }
}
