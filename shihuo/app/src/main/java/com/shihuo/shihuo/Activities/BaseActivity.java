package com.shihuo.shihuo.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.android.volley.Request;
import com.baoyz.actionsheet.ActionSheet;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragmentActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TakePhotoOptions;
import com.shihuo.shihuo.application.BaseApplication;
import com.shihuo.shihuo.dialog.ProgressDialog;
import com.shihuo.shihuo.util.AppUtils;

import java.io.File;

/**
 * Created by cm_qiujiaheng on 2016/11/2.
 */

public abstract class BaseActivity extends TakePhotoFragmentActivity implements ActionSheet.ActionSheetListener {
    protected final String REFRESH_TIME = "latest_refresh_time";

    public String mRequestTag;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestTag = initRequestTag();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        AppUtils.fullScreenColor(this);
        mDialog = new ProgressDialog(BaseActivity.this);
    }

    public abstract void initViews();

    public void addRequest(Request req) {
        BaseApplication.getInstance().addToRequestQueue(req, mRequestTag);
    }

    public String initRequestTag() {
        return getRequestTag();
    }

    public String getRequestTag() {
        return getClass().getName();
    }

    protected void getPhoto() {
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("拍照", "相册")
                .setCancelableOnTouchOutside(true)
                .setListener(this).show();
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
        actionSheet.dismiss();
    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        onClick(index, 1, true);
    }


    protected void onClick(int index, int limit, boolean isCrop) {
        TakePhoto takePhoto = getTakePhoto();
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        switch (index) {
            case 0://相机
                if (isCrop) {
                    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                } else {
                    takePhoto.onPickFromCapture(imageUri);
                }
                break;
            case 1://相册
                if (limit > 1) {
                    if (isCrop) {//剪裁
                        takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
                    } else {
                        takePhoto.onPickMultiple(limit);
                    }
                    return;
                }
                //从相册选择图片
                if (isCrop) {
                    takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                } else {
                    takePhoto.onPickFromGallery();
                }
                break;
            default:
                break;
        }
    }

    protected void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);//选择takephoto自带相册
//            builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());

    }

    protected void configCompress(TakePhoto takePhoto) {

        int maxSize = 102400;
        int width = 800;
        int height = 800;
        boolean showProgressBar = false;//是否显示压缩进度
        boolean enableRawFile = true;//压缩后是否保存原图
        CompressConfig config;
        //使用自带压缩工具
        config = new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(enableRawFile)
                .create();
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    protected CropOptions getCropOptions() {
        int height = 800;
        int width = 800;
        boolean withWonCrop = true;

        CropOptions.Builder builder = new CropOptions.Builder();

//            builder.setAspectX(width).setAspectY(height);//宽/高
        builder.setOutputX(width).setOutputY(height);//宽X高
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }

    protected void showProgressDialog() {
        if (null == mDialog) {
            mDialog = new ProgressDialog(this);
        }
        mDialog.show();
    }

    protected void hideProgressDialog() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
    }
}
