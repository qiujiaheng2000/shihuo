package com.shihuo.shihuo.Activities.shop.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 * 拒绝退货dialog
 */

public class RefuseBackDialog extends Dialog {


    @BindView(R.id.edit_typename)
    EditText editTypename;
    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_refund_reason)
    TextView textRefundReason;


    private Context context;
    private String title;
    private String hintText;
    private String text;
    private String secondTitle;
    private CustomCallback customCallback;

    public void setCustomCallback(CustomCallback customCallback) {
        this.customCallback = customCallback;
    }


    public interface CustomCallback {
        void onOkClick(Dialog dialog, String typeName);
    }

    public RefuseBackDialog(Context context) {
        super(context);
        context = context;
    }

    public RefuseBackDialog(Context context, int themeResId) {
        super(context, themeResId);
        context = context;
    }

    protected RefuseBackDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_refuse_back);
        ButterKnife.bind(this);
        textTitle.setText(this.title);
        editTypename.setHint(this.hintText);
        editTypename.setText(this.text);
        textRefundReason.setText(this.secondTitle);
    }

    public RefuseBackDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public RefuseBackDialog setHintText(String hintText) {
        this.hintText = hintText;
        return this;
    }
    public RefuseBackDialog setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;

        return this;
    }

    public RefuseBackDialog setText(String text) {
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
                if (TextUtils.isEmpty(editTypename.getText())) {
                    editTypename.setError("请输入商品分类名称");
                    return;
                }
                if (null != customCallback) {
                    customCallback.onOkClick(this, editTypename.getText().toString());
                }
                break;
        }
    }
}
