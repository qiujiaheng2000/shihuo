package com.shihuo.shihuo.Activities.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.jph.takephoto.model.TResult;
import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.BuildConfig;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.LoginModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.FileUtils;
import com.shihuo.shihuo.util.Toaster;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by cm_qiujiaheng on 2016/11/27.
 * 店铺入驻界面
 */

public class ShopsLocatedActivity extends BaseActivity {


    private static final String TAG = "ShopsLocatedActivity";
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
    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.edit_idcardnumber)
    EditText editIdcardnumber;

    //记录当前点击的图片id
    private int onClickViewId;

    private String imageShopLogoName;//店铺logo的图片的名称
    private String imageIdcardPositiveName;//身份证正面的图片的名称
    private String imageIdcardReverseName;//身份证反面的图片的名称
    private String imageIdcardHandName;//手持身份证的图片的名称

    private ArrayList<GoodsTypeModel> goodsTypeModels;
    private ArrayList<GoodsTypeModel> goodsCycleModels;
    private ArrayList<GoodsTypeModel> goodsAreaModels;

    private GoodsTypeSpinnerAdapter goodsTypeSpinnerAdapter;
    private GoodsCycleSpinnerAdapter goodsCycleSpinnerAdapter;
    private GoodsAreaSpinnerAdapter goodsAreaSpinnerAdapter;
    private TimeCount mTimer;
    public static void startShopsLocatedActivity(Context context) {
        Intent intent = new Intent(context, ShopsLocatedActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.layout_shoplocated);
        ButterKnife.bind(this);
        initViews();
        getSpinnerDatas();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.shihuo_shop);
        //分类
        goodsTypeModels = new ArrayList<>();
        goodsTypeSpinnerAdapter = new GoodsTypeSpinnerAdapter();
        shopTypeSpinner.setAdapter(goodsTypeSpinnerAdapter);
        //商圈
        goodsCycleModels = new ArrayList<>();
        goodsCycleSpinnerAdapter = new GoodsCycleSpinnerAdapter();
        shopAreaSpinner.setAdapter(goodsCycleSpinnerAdapter);
        shopAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getCycleAreaDatas((int) parent.getSelectedItemId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //区域
        goodsAreaModels = new ArrayList<>();
        goodsAreaSpinnerAdapter = new GoodsAreaSpinnerAdapter();
        shopAddSpinner.setAdapter(goodsAreaSpinnerAdapter);
        mTimer = new TimeCount(60000, 1000);
    }

    private void getSpinnerDatas() {
        //类别
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_SYSTEM_GOODSTYPES))
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                JSONArray jsonArray = new JSONObject(response.data).getJSONArray("dataList");
                                goodsTypeModels.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    GoodsTypeModel goodsTypeModel = GoodsTypeModel.parseJsonStr(jsonArray.getJSONObject(i));
                                    goodsTypeModels.add(goodsTypeModel);
                                }
                                goodsTypeSpinnerAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(ShopsLocatedActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                });
        //商圈
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GETCIRCLE))
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                JSONArray jsonArray = new JSONObject(response.data).getJSONArray("dataList");
                                goodsCycleModels.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    GoodsTypeModel goodsTypeModel = GoodsTypeModel.parseJsonStr(jsonArray.getJSONObject(i));
                                    goodsCycleModels.add(goodsTypeModel);
                                    if (i == 0) {//获取第一个商圈对应的区域
                                        getCycleAreaDatas(goodsTypeModel.circleId);
                                    }
                                }
                                goodsCycleSpinnerAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(ShopsLocatedActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                });


    }

    /**
     * 获取对应商圈的区域列表
     *
     * @param circleId
     */
    private void getCycleAreaDatas(int circleId) {
        //商圈区域
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GETCIRCLEAREA))
                .addParams("circleId", circleId + "")
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                JSONArray jsonArray = new JSONObject(response.data).getJSONArray("dataList");
                                goodsAreaModels.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    GoodsTypeModel goodsTypeModel = GoodsTypeModel.parseJsonStr(jsonArray.getJSONObject(i));
                                    goodsAreaModels.add(goodsTypeModel);
                                }
                                goodsAreaSpinnerAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(ShopsLocatedActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                });
    }

    @OnClick({R.id.imag_left, R.id.layout_shop_logo, R.id.layout_idcard_positive, R.id.layout_idcard_reverse, R.id.layout_idcard_hand, R.id.btn_shoplocatd_protocol, R.id.btn_shoplocated_commit, R.id.btn_get_verfiy_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_get_verfiy_code:
                getVerifyCode();
                break;
            case R.id.layout_shop_logo://商铺logo
                onClickViewId = R.id.layout_shop_logo;
                getPhoto();
                break;
            case R.id.layout_idcard_positive://身份证（证明）
                onClickViewId = R.id.layout_idcard_positive;
                getPhoto();
                break;
            case R.id.layout_idcard_reverse://身份证（反面）
                onClickViewId = R.id.layout_idcard_reverse;
                getPhoto();
                break;
            case R.id.layout_idcard_hand://手持身份证
                onClickViewId = R.id.layout_idcard_hand;
                getPhoto();
                break;
            case R.id.btn_shoplocatd_protocol://同意协议
                //TODO

                break;
            case R.id.btn_shoplocated_commit://提交审核
                commitNewShop();

                break;
        }
    }


    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        String phoneNum = editPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            editPhoneNumber.setError(getString(R.string.error_phonenum));
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phoneNum", editPhoneNumber.getText());

            OkHttpUtils
                    .postString()
                    .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_VERIFY_CODE))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(jsonObject.toString())
                    .build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                AppUtils.showToast(ShopsLocatedActivity.this,
                                        getResources().getString(R.string.toast_verify_code));
                                mTimer.start();
                            } else {
                                Toast.makeText(ShopsLocatedActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交开店审核
     */
    private void commitNewShop() {
        if (TextUtils.isEmpty(editPhoneNumber.getText())) {
            editPhoneNumber.setError(getResources().getString(R.string.error_phonenum));
            return;
        }
        if (TextUtils.isEmpty(edittextVerifycode.getText())) {
            edittextVerifycode.setError(getResources().getString(R.string.error_verify_coode));
            return;
        }

        if (TextUtils.isEmpty(edittextShopName.getText())) {
            edittextShopName.setError(getResources().getString(R.string.error_shopname));
            return;
        }
        //店铺logo上传
        if (TextUtils.isEmpty(imageShopLogoName)) {
            Toaster.toastShort(getResources().getString(R.string.error_upload_shop_logo));
            return;
        }

        if (TextUtils.isEmpty(editUsername.getText())) {
            editUsername.setError(getResources().getString(R.string.error_username));
            return;
        }
        if (TextUtils.isEmpty(editIdcardnumber.getText())) {
            editIdcardnumber.setError(getResources().getString(R.string.error_idcardnumber));
            return;
        }
        if (TextUtils.isEmpty(imageIdcardPositiveName)) {
            Toaster.toastShort(getResources().getString(R.string.error_upload_idcard_positive));
            return;
        }

        if (TextUtils.isEmpty(imageIdcardHandName)) {
            Toaster.toastShort(getResources().getString(R.string.error_upload_idcard_hand));
            return;
        }
        if (TextUtils.isEmpty(imageIdcardReverseName)) {
            Toaster.toastShort(getResources().getString(R.string.error_upload_idcard_reverse));
            return;
        }
        if (TextUtils.isEmpty(edittextBankCard.getText())) {
            edittextBankCard.setError(getResources().getString(R.string.error_bankcard));
            return;
        }
        if (TextUtils.isEmpty(edittextBankCardAgain.getText())) {
            edittextBankCardAgain.setError(getResources().getString(R.string.error_bankcard_again));
            return;
        }
        if (!checkboxNotice.isChecked()) {
            Toaster.toastShort(getResources().getString(R.string.new_shop_notice));
            return;
        }

        request();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null)
            mTimer.cancel();
    }

    private void request() {
        JSONObject params = new JSONObject();
        try {
            params.put("holderIdNum", editUsername.getText());

            params.put("verifyCode", edittextVerifycode.getText());
//            params.put("csPhoneNum", "");//客服电话
            params.put("storeName", edittextShopName.getText());
            params.put("storeDetail", edittextShopDesc.getText());
            params.put("storeLogoPicUrl", imageShopLogoName);
            params.put("sysGoodTypeId", shopTypeSpinner.getSelectedItemId());
            params.put("circleId", shopAddSpinner.getSelectedItemId());
            params.put("holderName", editUsername.getText());
            params.put("holderPhoneNum", editPhoneNumber.getText());
            params.put("holderIdNum", editIdcardnumber.getText());
            params.put("bankName", bankSpinner.getSelectedItem());
            params.put("bankCardNum", edittextBankCard.getText());
            params.put("idCardFrontPicUrl", imageIdcardPositiveName);
            params.put("idCardBackPicUrl", imageIdcardReverseName);
            params.put("idCardSelfieUrl", imageIdcardHandName);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        LoginModel userModel = AppShareUitl.getUserInfo(this);
        if (null == userModel.token) {
            Toaster.toastShort("用户token错误");
            return;
        }

        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_OPENSHOP) + "?token=" + userModel.token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            if (BuildConfig.DEBUG) {
                                Toast.makeText(ShopsLocatedActivity.this, response.data, Toast.LENGTH_SHORT).show();
                            } else {
                                Toaster.toastShort(getResources().getString(R.string.shoplocated_ok));
                            }
                            finish();
                        } else {
                            Toast.makeText(ShopsLocatedActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                });
    }


    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG, "takeSuccess：" + result.getImage().getCompressPath());
        setImageView(result.getImage().getCompressPath());
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


    private void setImageView(String compressPath) {
        String fileName = FileUtils.getFileName(compressPath);
        switch (onClickViewId) {
            case R.id.layout_shop_logo://商铺logo
                imageShopLogo.setImageURI(AppUtils.parse(compressPath));
                imageShopLogoName = fileName;
                break;
            case R.id.layout_idcard_positive://身份证（证明）
                imageIdcardPositive.setImageURI(AppUtils.parse(compressPath));
                imageIdcardPositiveName = fileName;
                break;
            case R.id.layout_idcard_reverse://身份证（反面）
                imageIdcardReverse.setImageURI(AppUtils.parse(compressPath));
                imageIdcardReverseName = fileName;
                break;
            case R.id.layout_idcard_hand://手持身份证
                imageIdcardHand.setImageURI(AppUtils.parse(compressPath));
                imageIdcardHandName = fileName;
                break;
        }
    }

    /**
     * 系统分类spinner适配器
     */
    class GoodsTypeSpinnerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return goodsTypeModels.size();
        }

        @Override
        public Object getItem(int position) {
            return goodsTypeModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return goodsTypeModels.get(position).typeId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(ShopsLocatedActivity.this).inflate(R.layout.item_spinner, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            GoodsTypeModel goodsTypeModel = (GoodsTypeModel) getItem(position);
            viewHolder.spinnerItem.setText(goodsTypeModel.typeName);
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.spinner_item)
            TextView spinnerItem;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * 商圈spinner适配器
     */
    class GoodsCycleSpinnerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return goodsCycleModels.size();
        }

        @Override
        public Object getItem(int position) {
            return goodsCycleModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return goodsCycleModels.get(position).circleId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(ShopsLocatedActivity.this).inflate(R.layout.item_spinner, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            GoodsTypeModel goodsTypeModel = (GoodsTypeModel) getItem(position);
            viewHolder.spinnerItem.setText(goodsTypeModel.circleName);
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.spinner_item)
            TextView spinnerItem;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * 商圈区域spinner适配器
     */
    class GoodsAreaSpinnerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return goodsAreaModels.size();
        }

        @Override
        public Object getItem(int position) {
            return goodsAreaModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return goodsAreaModels.get(position).circleId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(ShopsLocatedActivity.this).inflate(R.layout.item_spinner, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            GoodsTypeModel goodsTypeModel = (GoodsTypeModel) getItem(position);
            viewHolder.spinnerItem.setText(goodsTypeModel.circleName);
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.spinner_item)
            TextView spinnerItem;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            btnGetVerfiyCode.setText(getResources().getString(R.string.get_verify));
            btnGetVerfiyCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            btnGetVerfiyCode.setClickable(false);
            btnGetVerfiyCode.setText(millisUntilFinished / 1000 + getResources().getString(R.string.phone_code));
        }
    }

}
