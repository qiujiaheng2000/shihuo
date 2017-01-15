package com.shihuo.shihuo.Activities.shop.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 * 关闭dialog
 */

public class GoBackDialog extends Dialog {

    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    @BindView(R.id.text_title)
    TextView textTitle;

    private Context context;
    private String title;
    private String hintText;
    private String text;
    private CustomCallback customCallback;

    public void setCustomCallback(CustomCallback customCallback) {
        this.customCallback = customCallback;
    }


    public interface CustomCallback {
        void onOkClick(Dialog dialog);
    }

    public GoBackDialog(Context context) {
        super(context);
        context = context;
    }

    public GoBackDialog(Context context, int themeResId) {
        super(context, themeResId);
        context = context;
    }

    protected GoBackDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_go_back);
        ButterKnife.bind(this);
        textTitle.setText(this.title);
    }

    public GoBackDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public GoBackDialog setHintText(String hintText) {
        this.hintText = hintText;
        return this;
    }

    public GoBackDialog setText(String text) {
        this.text = text;
        return this;
    }

    @OnClick({R.id.btn_cancel, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_ok:
                if (null != customCallback) {
                    customCallback.onOkClick(this);
                }
                break;
        }
    }
}
