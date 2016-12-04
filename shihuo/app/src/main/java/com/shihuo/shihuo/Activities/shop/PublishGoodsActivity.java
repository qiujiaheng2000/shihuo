package com.shihuo.shihuo.Activities.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.jph.takephoto.model.TResult;
import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.Activities.shop.views.AddImageView;
import com.shihuo.shihuo.Activities.shop.views.PublishPropertyView;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 * 商品发布界面
 */

public class PublishGoodsActivity extends BaseActivity implements PublishPropertyView.RemoveListener, AddImageView.OnAddImageClickListener {

    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.spinner_shop_type)
    AppCompatSpinner spinnerShopType;
    @BindView(R.id.spinner_goods_type)
    AppCompatSpinner spinnerGoodsType;
    @BindView(R.id.edittext_shop_name)
    EditText edittextShopName;
    @BindView(R.id.edittext_goods_desc)
    EditText edittextGoodsDesc;
    @BindView(R.id.layout_properties)
    LinearLayout layoutProperties;
    @BindView(R.id.btn_addproperties)
    Button btnAddproperties;
    @BindView(R.id.radiogroup_distribution)
    RadioGroup radiogroupDistribution;
    @BindView(R.id.btn_publishgoods)
    Button btnPublishgoods;
    @BindView(R.id.addiamge_1)
    AddImageView addiamge1;
    @BindView(R.id.addiamge_2)
    AddImageView addiamge2;

    private AddImageView currentAddImageView;//当前点击的图片选择器

    public static void start(Context context) {
        Intent intent = new Intent(context, PublishGoodsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_publishgoods);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.title_publish_new_goods);
        addiamge1.setOnAddImageClickListener(this);
        addiamge2.setOnAddImageClickListener(this);
    }

    @OnClick({R.id.imag_left, R.id.btn_addproperties, R.id.btn_publishgoods})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                break;
            case R.id.btn_addproperties:
                addProperties();
                break;
            case R.id.btn_publishgoods:
                break;
        }
    }

    private void addProperties() {
        PublishPropertyView publishPropertyView = new PublishPropertyView(PublishGoodsActivity.this);
        layoutProperties.addView(publishPropertyView);
        publishPropertyView.setRemoveListener(this);
    }

    @Override
    public void removeFromParent(View view) {
        layoutProperties.removeView(view);
    }

    @Override
    public void onBtnClick(AddImageView addImageView) {
        currentAddImageView = addImageView;
        getPhoto();
    }

    @Override
    public void takeSuccess(TResult result) {
        Log.i("takePhoto", "takeSuccess：" + result.getImage().getCompressPath());
        currentAddImageView.addImageView(result.getImage().getCompressPath());
        AliyunHelper.getInstance().asyncUplodaFile(result.getImage().getCompressPath(), new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });

    }



}
