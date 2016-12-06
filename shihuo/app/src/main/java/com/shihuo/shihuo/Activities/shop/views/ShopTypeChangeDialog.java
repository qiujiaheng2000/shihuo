package com.shihuo.shihuo.Activities.shop.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 */

public class ShopTypeChangeDialog extends Dialog {


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

    public ShopTypeChangeDialog(Context context) {
        super(context);
        context = context;
    }

    public ShopTypeChangeDialog(Context context, int themeResId) {
        super(context, themeResId);
        context = context;
    }

    protected ShopTypeChangeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_shoptype);
        ButterKnife.bind(this);

        textTitle.setText(this.title);
        editTypename.setHint(this.hintText);
    }

    public ShopTypeChangeDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public ShopTypeChangeDialog setHintText(String hintText) {
        this.hintText = hintText;
        return this;
    }

    @OnClick({R.id.btn_cancel, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_ok:
                dismiss();
                break;
        }
    }
}
