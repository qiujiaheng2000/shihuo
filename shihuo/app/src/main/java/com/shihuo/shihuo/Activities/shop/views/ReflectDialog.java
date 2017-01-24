package com.shihuo.shihuo.Activities.shop.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 */

public class ReflectDialog extends Dialog {


    @BindView(R.id.edit_typename)
    EditText editTypename;
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
        void onOkClick(Dialog dialog, String typeName);
    }

    public ReflectDialog(Context context) {
        super(context);
        context = context;
    }

    public ReflectDialog(Context context, int themeResId) {
        super(context, themeResId);
        context = context;
    }

    protected ReflectDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reflect);
        ButterKnife.bind(this);

        textTitle.setText(this.title);
        editTypename.setHint(this.hintText);
        editTypename.setText(this.text);
    }

    public ReflectDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public ReflectDialog setHintText(String hintText) {
        this.hintText = hintText;
        return this;
    }

    public ReflectDialog setText(String text) {
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
//                    editTypename.setError("请输入商品分类名称");
                    AppUtils.showToast(getContext(), "请输入提取的金额");
                    return;
                }
                if (null != customCallback) {
                    customCallback.onOkClick(this, editTypename.getText().toString());
                }
                break;
        }
    }
}
