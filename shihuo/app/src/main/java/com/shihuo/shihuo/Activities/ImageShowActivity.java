
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.ImageShowView;
import com.shihuo.shihuo.util.AppUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/5. 图片大图预览界面
 */

public class ImageShowActivity extends BaseActivity {
    public static final String FLAG_IMAGE_LIST = "image_list";

    @BindView(R.id.imag_left)
    ImageView leftbtn;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tv_page)
    TextView tv_page;

    @BindView(R.id.view_banner)
    ImageShowView bannerview;

//    private int height;

    private List<String> mImageList = new ArrayList<>();

    public static void start(Context context, List<String> list) {
        Intent intent = new Intent(context, ImageShowActivity.class);
        intent.putExtra(FLAG_IMAGE_LIST, (Serializable) list);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.layout_image_show);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        title.setText("商品大图预览");
        leftbtn.setVisibility(View.VISIBLE);
        mImageList = (List<String>) getIntent().getSerializableExtra(FLAG_IMAGE_LIST);
        bannerview.setData(mImageList);
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
}
