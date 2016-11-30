package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TakePhotoOptions;
import com.shihuo.shihuo.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/27.
 * 店铺入驻界面
 */

public class ShopsLocatedActivity extends BaseActivity implements ActionSheet.ActionSheetListener {


    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit_phone_number)
    EditText editPhoneNumber;
    @BindView(R.id.edittext_verifycode)
    EditText edittextVerifycode;
    @BindView(R.id.btn_get_verfiy_code)
    TextView btnGetVerfiyCode;
    @BindView(R.id.edittext_shop_name)
    EditText edittextShopName;
    @BindView(R.id.edittext_shop_desc)
    EditText edittextShopDesc;
    @BindView(R.id.shop_type_spinner)
    AppCompatSpinner shopTypeSpinner;
    @BindView(R.id.shop_area_spinner)
    AppCompatSpinner shopAreaSpinner;
    @BindView(R.id.shop_add_spinner)
    AppCompatSpinner shopAddSpinner;
    @BindView(R.id.image_shop_logo)
    ImageView imageShopLogo;
    @BindView(R.id.layout_shop_logo)
    RelativeLayout layoutShopLogo;
    @BindView(R.id.image_idcard_positive)
    ImageView imageIdcardPositive;
    @BindView(R.id.layout_idcard_positive)
    RelativeLayout layoutIdcardPositive;
    @BindView(R.id.image_idcard_reverse)
    ImageView imageIdcardReverse;
    @BindView(R.id.layout_idcard_reverse)
    RelativeLayout layoutIdcardReverse;
    @BindView(R.id.image_idcard_hand)
    ImageView imageIdcardHand;
    @BindView(R.id.layout_idcard_hand)
    RelativeLayout layoutIdcardHand;
    @BindView(R.id.bank_spinner)
    AppCompatSpinner bankSpinner;
    @BindView(R.id.edittext_bank_card)
    EditText edittextBankCard;
    @BindView(R.id.edittext_bank_card_again)
    EditText edittextBankCardAgain;
    @BindView(R.id.checkbox_notice)
    CheckBox checkboxNotice;
    @BindView(R.id.btn_shoplocatd_protocol)
    TextView btnShoplocatdProtocol;

    public static void startShopsLocatedActivity(Context context) {
        Intent intent = new Intent(context, ShopsLocatedActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shoplocated);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.shihuo_shop);
    }

    @OnClick({R.id.imag_left, R.id.layout_shop_logo, R.id.layout_idcard_positive, R.id.layout_idcard_reverse, R.id.layout_idcard_hand, R.id.btn_shoplocatd_protocol, R.id.btn_shoplocated_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.layout_shop_logo://商铺logo
                ActionSheet.createBuilder(this, getSupportFragmentManager())
                        .setCancelButtonTitle("取消")
                        .setOtherButtonTitles("拍照", "相册")
                        .setCancelableOnTouchOutside(true)
                        .setListener(this).show();
                break;
            case R.id.layout_idcard_positive://身份证（证明）
                break;
            case R.id.layout_idcard_reverse://身份证（反面）
                break;
            case R.id.layout_idcard_hand://手持身份证
                break;
            case R.id.btn_shoplocatd_protocol://同意协议
                break;
            case R.id.btn_shoplocated_commit://提交审核
                break;
        }
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
        actionSheet.dismiss();
    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        onClick(index, 1, true);
    }


    public void onClick(int index, int limit, boolean isCrop) {
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

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);//选择takephoto自带相册
//            builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());

    }

    private void configCompress(TakePhoto takePhoto) {

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

    private CropOptions getCropOptions() {
        int height = 800;
        int width = 800;
        boolean withWonCrop = true;

        CropOptions.Builder builder = new CropOptions.Builder();

//            builder.setAspectX(width).setAspectY(height);//宽/高
        builder.setOutputX(width).setOutputY(height);//宽X高
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }

}
