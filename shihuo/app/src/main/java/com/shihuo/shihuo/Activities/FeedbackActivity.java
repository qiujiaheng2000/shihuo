package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/4.
 * 意见反馈界面
 */

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.imag_left)
    ImageView leftbtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rightbtn)
    Button rightbtn;
    @BindView(R.id.edit_feedback)
    EditText editFeedback;
    @BindView(R.id.btn_feedback)
    Button btnFeedback;

    public static void stardFeedbackActivity(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_feedback);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        title.setText(R.string.feedback_title);
        leftbtn.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.imag_left, R.id.btn_feedback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_feedback:
                //TODO
                Toast.makeText(FeedbackActivity.this, "提交反馈", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
