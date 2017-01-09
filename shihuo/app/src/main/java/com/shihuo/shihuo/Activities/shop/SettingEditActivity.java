package com.shihuo.shihuo.Activities.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.BuildConfig;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.LoginModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.Toaster;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by cm_qiujiaheng on 2016/12/11.
 * 商品设置，修改文本
 */

public class SettingEditActivity extends BaseActivity {

    public static final String RefreshStoreInfo_Action = "com.shihuo.shihuo.refreshstoreinfo";

    public static final int FLAG_SETTING_CS_NUMBER = 0;//客服电话
    public static final int FLAG_SETTING_SHOP_DESC = 1;//店铺描述
    public static final int FLAG_SETTING_SHOP_NOTIC = 2;//店铺公告
    public static final int FLAG_SETTING_SHOP_DILIVERY_TIME = 3;//配送时间
    public static final int FLAG_SETTING_SHOP_BUSINESS_TIME = 4;//营业时间
    public static final int FLAG_SETTING_SHOP_ADDRESS = 5;//商铺地址
    public static final int FLAG_SETTING_SHOP_SEND_PRICE = 6;//配送价格
    public static final String KEY_FLAG = "setting_key";
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit_setting)
    EditText editSetting;
    @BindView(R.id.imag_left)
    ImageView imagLeft;

    private int flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        flag = getIntent().getIntExtra(KEY_FLAG, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shop_setting_item);
        ButterKnife.bind(this);
        initViews();
    }

    public static void start(Context context, int flag) {
        Intent intent = new Intent(context, SettingEditActivity.class);
        intent.putExtra(KEY_FLAG, flag);
        context.startActivity(intent);
    }


    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(getSettingTitle());
        editSetting.setHint(getSettingHint());
        editSetting.setText(getValue());
    }

    /**
     * 获取店铺信息的当前设置
     *
     * @return
     */
    private String getValue() {
        String editString;
        switch (flag) {
            case FLAG_SETTING_CS_NUMBER:
                editString = ShopActivity.SHOP_MANAGER_INFO.csPhoneNum;
                break;
            case FLAG_SETTING_SHOP_DESC:
                editString = ShopActivity.SHOP_MANAGER_INFO.storeDetail;
                break;
            case FLAG_SETTING_SHOP_NOTIC:
                editString = ShopActivity.SHOP_MANAGER_INFO.storeAnnouncement;
                break;
            case FLAG_SETTING_SHOP_DILIVERY_TIME:
                editString = ShopActivity.SHOP_MANAGER_INFO.distributionTime;
                break;
            case FLAG_SETTING_SHOP_BUSINESS_TIME:
                editString = ShopActivity.SHOP_MANAGER_INFO.businessTime;
                break;
            case FLAG_SETTING_SHOP_ADDRESS:
                editString = ShopActivity.SHOP_MANAGER_INFO.storeAddress;
                break;
            case FLAG_SETTING_SHOP_SEND_PRICE:
                editString = ShopActivity.SHOP_MANAGER_INFO.storeFreeShippingPrice;
                break;
            default:
                editString = "";
                break;
        }
        return editString;
    }

    private String getSettingTitle() {
        String title;
        switch (flag) {
            case FLAG_SETTING_CS_NUMBER:
                title = getResources().getString(R.string.shopsetting_service_number);
                break;
            case FLAG_SETTING_SHOP_DESC:
                title = getResources().getString(R.string.shopsetting_desc);
                break;
            case FLAG_SETTING_SHOP_NOTIC:
                title = getResources().getString(R.string.shopsetting_notice);
                break;
            case FLAG_SETTING_SHOP_DILIVERY_TIME:
                title = getResources().getString(R.string.shopsetting_delivery_time);
                break;
            case FLAG_SETTING_SHOP_BUSINESS_TIME:
                title = getResources().getString(R.string.shopsetting_business_time);
                break;
            case FLAG_SETTING_SHOP_ADDRESS:
                title = getResources().getString(R.string.shopsetting_shop_address);
                break;
            case FLAG_SETTING_SHOP_SEND_PRICE:
                title = "设置免费配送价格";
                break;
            default:
                title = "";
                break;
        }
        return title;
    }

    private String getSettingHint() {
        String title;
        switch (flag) {
            case FLAG_SETTING_CS_NUMBER:
                title = getResources().getString(R.string.hint_shopsetting_service_number);
                break;
            case FLAG_SETTING_SHOP_DESC:
                title = getResources().getString(R.string.hint_shopsetting_desc);
                break;
            case FLAG_SETTING_SHOP_NOTIC:
                title = getResources().getString(R.string.hint_shopsetting_notice);
                break;
            case FLAG_SETTING_SHOP_DILIVERY_TIME:
                title = getResources().getString(R.string.hint_shopsetting_delivery_time);
                break;
            case FLAG_SETTING_SHOP_BUSINESS_TIME:
                title = getResources().getString(R.string.hint_shopsetting_business_time);
                break;
            case FLAG_SETTING_SHOP_ADDRESS:
                title = getResources().getString(R.string.hint_shopsetting_shop_address);
                break;
            case FLAG_SETTING_SHOP_SEND_PRICE:
                title = "请输入店铺免费配送价格";
                break;
            default:
                title = "";
                break;
        }
        return title;
    }

    @OnClick({R.id.imag_left, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_commit:
                if (editSetting.getText().toString().length() > 100) {
                    AppUtils.showToast(SettingEditActivity.this, "最多输入100个字符");
                } else {
                    updateStoreInfo();
                }
                break;
        }
    }

    private void updateStoreInfo() {
        showProgressDialog();
        try {
            JSONObject params = new JSONObject();
            String paramsflag = "";
            int type = 0;
            switch (flag) {
                case FLAG_SETTING_CS_NUMBER:
                    paramsflag = "csPhoneNum";
                    type = 4;
                    break;
                case FLAG_SETTING_SHOP_DESC:
                    paramsflag = "storeDetail";
                    type = 1;
                    break;
                case FLAG_SETTING_SHOP_NOTIC:
                    paramsflag = "storeAnnouncement";
                    type = 2;
                    break;
                case FLAG_SETTING_SHOP_SEND_PRICE:
                    paramsflag = "storeFreeShippingPrice";
                    type = 3;
                    break;
                case FLAG_SETTING_SHOP_DILIVERY_TIME:
                    paramsflag = "distributionTime";
                    type = 5;
                    break;
                case FLAG_SETTING_SHOP_BUSINESS_TIME:
                    paramsflag = "businessTime";
                    type = 6;
                    break;
                case FLAG_SETTING_SHOP_ADDRESS:
                    paramsflag = "storeAddress";
                    type = 7;
                    break;
                default:
                    break;
            }
            params.put("content", editSetting.getText().toString());
            params.put("type", type);


            LoginModel userModel = AppShareUitl.getUserInfo(this);
            if (null == userModel.token) {
                Toaster.toastShort("用户token错误");
                return;
            }
            params.put("storeId", AppShareUitl.getUserInfo(this).storeId);
            OkHttpUtils
                    .postString()
                    .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_SHOPINFO_UPDATE) + "?token=" + userModel.token)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(params.toString())
                    .build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            hideProgressDialog();
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                if (BuildConfig.DEBUG) {
                                    Toast.makeText(SettingEditActivity.this, response.data, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toaster.toastShort(getResources().getString(R.string.goods_info_update_ok));
                                }
                                sendRefreshStoreInfoMsg();
                                finish();
                            } else {
                                Toast.makeText(SettingEditActivity.this, response.msg, Toast.LENGTH_SHORT).show();
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
     * 发送更新商铺设置广播
     */
    private void sendRefreshStoreInfoMsg() {
        Intent intent = new Intent(RefreshStoreInfo_Action);
        sendBroadcast(intent);

    }
}
