package com.shihuo.shihuo.Views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 * 订单评价dialog
 */

public class EvaluateOrderDialog extends Dialog {


    @BindView(R.id.edit_typename)
    EditText editTypename;
    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.ratingbar_env)
    RatingBar ratingbarEnv;

    private Context context;
    private String title;
    private String hintText;
    private String text;
    private CustomCallback customCallback;

    public void setCustomCallback(CustomCallback customCallback) {
        this.customCallback = customCallback;
    }


    public interface CustomCallback {
        void onOkClick(Dialog dialog, String content, float rating);
    }

    public EvaluateOrderDialog(Context context) {
        super(context);
        context = context;
    }

    public EvaluateOrderDialog(Context context, int themeResId) {
        super(context, themeResId);
        context = context;
    }

    protected EvaluateOrderDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_evaluate_order);
        ButterKnife.bind(this);
        textTitle.setText(this.title);
        editTypename.setHint(this.hintText);
        editTypename.setText(this.text);
    }

    public EvaluateOrderDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public EvaluateOrderDialog setHintText(String hintText) {
        this.hintText = hintText;
        return this;
    }

    public EvaluateOrderDialog setText(String text) {
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
//                if (TextUtils.isEmpty(editTypename.getText())) {
//                    editTypename.setError("在这里添加文字描述");
//                    return;
//                }
                if (null != customCallback) {
                    customCallback.onOkClick(this, editTypename.getText().toString(), ratingbarEnv.getRating());
                }
                break;
        }
    }
}
