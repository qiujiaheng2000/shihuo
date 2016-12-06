package com.shihuo.shihuo.Activities.shop.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.MyHorizontalScrollView;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.FileUtils;
import com.shihuo.shihuo.util.Toaster;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/12/5.
 * 添加照片自定义view
 */

public class AddImageView extends LinearLayout {
    @BindView(R.id.btn_addimage)
    Button btnAddimage;
    @BindView(R.id.hor_scrollview)
    MyHorizontalScrollView horScrollview;
    @BindView(R.id.layout_image)
    LinearLayout layoutImage;

    private ArrayList<String> imageNames = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public OnAddImageClickListener getOnAddImageClickListener() {
        return onAddImageClickListener;
    }

    public void setOnAddImageClickListener(OnAddImageClickListener onAddImageClickListener) {
        this.onAddImageClickListener = onAddImageClickListener;
    }

    private OnAddImageClickListener onAddImageClickListener;

    public static interface OnAddImageClickListener {
        void onBtnClick(AddImageView addImageView);
    }

    public AddImageView(Context context) {
        super(context);
        initView();
    }


    public AddImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AddImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.add_images, null);
        ButterKnife.bind(this, view);
        addView(view);
    }

    @OnClick(R.id.btn_addimage)
    public void onClick() {

        if (onAddImageClickListener != null) {
            onAddImageClickListener.onBtnClick(this);
        }
    }

    public void addImageView(String path) {
        if (imageNames.size() == 5) {
            Toaster.toastShort("最多上传5张图片！");
            return;
        }
        imageNames.add(FileUtils.getFileName(path));
        layoutImage.addView(buildNewImageView(path), layoutImage.getChildCount() - 1);
    }

    public List<String> getImageNames() {
        return imageNames;
    }

    /**
     * 创建一个
     *
     * @param compressPath
     * @return
     */
    private View buildNewImageView(String compressPath) {
        final View view = layoutInflater.inflate(R.layout.item_image_view, null);
        ((ImageView) view.findViewById(R.id.image)).setImageURI(AppUtils.parse(compressPath));
        view.findViewById(R.id.btn_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutImage.removeView(view);
            }
        });
        return view;
    }
}
