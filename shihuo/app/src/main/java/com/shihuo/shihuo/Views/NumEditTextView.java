
package com.shihuo.shihuo.Views;


import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.AppUtils;

/**
 * 带±的输入框
 */
public class NumEditTextView extends FrameLayout implements View.OnClickListener {

    private View add;

    private View del;

    private EditText edit;

    private OnOrderEditCallBack callback;

    private int max = 10;

    private final int MIN = 1;

    public boolean canToast = true;

    public NumEditTextView(Context context) {
        super(context);
        init();
    }

    public NumEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_num_edit, null);
        this.addView(view);
        add = findViewById(R.id.add);
        del = findViewById(R.id.delete);
        edit = (EditText)findViewById(R.id.edit);
        edit.setText("1");
        add.setOnClickListener(this);
        del.setOnClickListener(this);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int num = Integer.parseInt(TextUtils.isEmpty(edit.getText().toString()) ? "1"
                        : edit.getText().toString());
                if (max < MIN) {
                    max = MIN;
                }
                if (num > max) {
                    edit.setText(String.valueOf(max));
//                    if (edit.getVisibility() == View.VISIBLE) {
//                        if (canToast) {
//                            AppUtils.showToast(getContext(),
//                                    String.format(getContext().getString(R.string.sotck_max), max));
//                        }
//
//                    }

                } else if (num < MIN) {
                    edit.setText(String.valueOf(MIN));
                }

                if (callback != null) {
                    callback.OnCallBack(getText());
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        int num = Integer.parseInt(
                TextUtils.isEmpty(edit.getText().toString()) ? "1" : edit.getText().toString());
        switch (view.getId()) {
            case R.id.add:
                if (num < max) {
                    edit.setText(String.valueOf(num + 1));
                } else {
                    AppUtils.showToast(getContext(), getContext().getString(R.string.toast_is_max));

                }
                break;
            case R.id.delete:
                int n = num - 1;
                if (num > MIN) {
                    edit.setText(String.valueOf(num - 1));
                }
                if (n < MIN) {
                    AppUtils.showToast(getContext(),
                            getContext().getString(R.string.toast_count_cant_delete));
                }
                break;
        }

        edit.setSelection(edit.getText().length());
    }

    public int getText() {
        try {
            return Integer.parseInt(edit.getText().toString());
        } catch (Exception e) {
            return 1;
        }
    }

    public void setText(String text) {
        try {
            edit.setText(TextUtils.isEmpty(text) ? "1" : String.valueOf(Integer.parseInt(text)));
        } catch (Exception e) {
            edit.setText("1");
        }
    }

    public void setOnOrderEditCallBack(OnOrderEditCallBack callback) {
        this.callback = callback;
    }

    /**
     * 设置edit最大值
     * 
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * 点击事件的回调
     */
    public interface OnOrderEditCallBack {
        void OnCallBack(int num);
    }
}
