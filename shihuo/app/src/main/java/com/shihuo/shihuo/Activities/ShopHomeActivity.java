
package com.shihuo.shihuo.Activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.shop.models.ShopManagerInfo;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.MarqueeTextView;
import com.shihuo.shihuo.Views.TabPageIndicator;
import com.shihuo.shihuo.Views.menu.SatelliteMenu;
import com.shihuo.shihuo.Views.menu.SatelliteMenuItem;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.fragments.ShopHomeGoodsListFragment;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by cm_qiujiaheng on 2016/12/17. 店铺首页界面
 */

public class ShopHomeActivity extends BaseActivity {
    public static final String KEY_STORE_ID = "storeId";

    @BindView(R.id.imag_left)
    ImageView imagLeft;

    @BindView(R.id.iv_store_start)
    ImageView iv_store_start;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tv_liulan)
    TextView tv_liulan;

    @BindView(R.id.image_shop_logo)
    SimpleDraweeView imageShopLogo;

    @BindView(R.id.text_title)
    TextView textTitle;

    @BindView(R.id.text_desc)
    MarqueeTextView textDesc;

    @BindView(R.id.text_notice)
    MarqueeTextView textNotice;

    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_circle)
    TextView tv_circle;

    @BindView(R.id.tv_time)
    TextView tv_time;

    @BindView(R.id.tv_send_time)
    TextView tv_send_time;

    @BindView(R.id.text_customnumber)
    TextView textCustomnumber;

    @BindView(R.id.layout_send_price)
    LinearLayout layout_send_price;

    @BindView(R.id.text_qr)
    TextView textQr;

    @BindView(R.id.text_deliever)
    TextView textDeliever;

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.rightbtn)
    Button rightbtn;

    @BindView(R.id.indicator)
    TabPageIndicator indicator;

    private String mStoreId;

    private ShopManagerInfo mShopManagerInfo;

    private FragmentPagerAdapter adapter;

    public ShopManagerInfo getmShopManagerInfo() {
        return mShopManagerInfo;
    }

    // 本店分类列表
    protected ArrayList<GoodsTypeModel> goodsTypeModels = new ArrayList<>();

    public static void start(Context context, String storeId) {
        Intent intent = new Intent(context, ShopHomeActivity.class);
        intent.putExtra(KEY_STORE_ID, storeId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStoreId = getIntent().getStringExtra(KEY_STORE_ID);
        setContentView(R.layout.layout_shop_home);
        ButterKnife.bind(this);
        initViews();
        getShopManagerInfo();

    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        rightbtn.setVisibility(View.VISIBLE);
        rightbtn.setBackground(getResources().getDrawable(R.drawable.selector_collect));
        initMenu();
    }

    private void initMenu() {
        SatelliteMenu menu = (SatelliteMenu) findViewById(R.id.menu);
        List<SatelliteMenuItem> items = new ArrayList<>();
        items.add(new SatelliteMenuItem(5, R.mipmap.icon_store_fenxiang));
        items.add(new SatelliteMenuItem(4, R.mipmap.icon_store_erweima));
        items.add(new SatelliteMenuItem(3, R.mipmap.icon_store_peisong));
        items.add(new SatelliteMenuItem(2, R.mipmap.icon_store_yingye));
        items.add(new SatelliteMenuItem(1, R.mipmap.icon_store_kefu));
        menu.addItems(items);
        menu.setVisibility(View.VISIBLE);
        menu.setOnItemClickedListener(new SatelliteMenu.SateliteClickedListener() {
            public void eventOccured(int id) {
                if (id == 1) {
                    // 客服
                    if (mShopManagerInfo != null && !TextUtils.isEmpty(mShopManagerInfo.csPhoneNum)) {
                        AppUtils.callPhone(ShopHomeActivity.this, mShopManagerInfo.csPhoneNum);
                    } else {
                        AppUtils.showToast(ShopHomeActivity.this,
                                getResources().getString(R.string.toast_no_phone));
                    }
                } else if (id == 2) {
                    // 营业时间
                    if(mShopManagerInfo != null && !TextUtils.isEmpty(mShopManagerInfo.businessTime)){
                        ShopInfoActivity.start(ShopHomeActivity.this, "营业时间", mShopManagerInfo.businessTime);
                    }
                } else if (id == 3) {
                    // 配送时间
                    if (mShopManagerInfo != null && !TextUtils.isEmpty(mShopManagerInfo.distributionTime)) {
                        ShopInfoActivity.start(ShopHomeActivity.this, "配送时间", mShopManagerInfo.distributionTime);
                    }
                } else if (id == 4) {
                    // 店铺二维码
                    if(mShopManagerInfo != null && !TextUtils.isEmpty(mShopManagerInfo.storeId)){
                        ZxingCreateActivity.start(ShopHomeActivity.this, 0, mShopManagerInfo.storeId);
                    }
                } else if (id == 5) {
                    // 店铺分享
                   ShareDialog.start(ShopHomeActivity.this);
                }
            }
        });
    }

    private void initTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_NOWEIGHT_EXPAND_NOSAME);// 设置模式，一定要先设置模式
        // indicator.setDividerColor(Color.parseColor("#00bbcf"));// 设置分割线的颜色
        // indicator.setDividerPadding(AppUtils.dip2px(CircleListActivity.this,
        // 10));
        indicator.setIndicatorColor(getResources().getColor(R.color.common_theme));// 设置底部导航线的颜色
        indicator.setUnderlineHeight(0);
        indicator.setTextColorSelected(getResources().getColor(R.color.common_theme));// 设置tab标题选中的颜色
        indicator.setTextColor(getResources().getColor(R.color.common_font_black));// 设置tab标题未被选中的颜色
        indicator.setTextSize(AppUtils.dip2px(ShopHomeActivity.this, 14));// 设置字体大小
    }

    @OnClick({
            R.id.imag_left, R.id.image_shop_logo, R.id.text_customnumber, R.id.text_qr,
            R.id.text_notice, R.id.text_deliever, R.id.rightbtn
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.image_shop_logo:
                if(mShopManagerInfo != null && !TextUtils.isEmpty(mShopManagerInfo.overAllUrl)){
//                    AppUtils.showToast(ShopHomeActivity.this, "360全景图");
                    WebViewActivity.start(ShopHomeActivity.this, mShopManagerInfo.overAllUrl);
                }else{
                    List<String> list = new ArrayList<>();
                    list.add(mShopManagerInfo.storeLogoPicUrl);
                    ImageShowActivity.start(ShopHomeActivity.this, list);
                }
                break;
            case R.id.text_customnumber:
                if (mShopManagerInfo != null && !TextUtils.isEmpty(mShopManagerInfo.csPhoneNum)) {
                    AppUtils.callPhone(ShopHomeActivity.this, mShopManagerInfo.csPhoneNum);
                } else {
                    AppUtils.showToast(ShopHomeActivity.this,
                            getResources().getString(R.string.toast_no_phone));
                }
                break;
            case R.id.text_qr:
                ZxingCreateActivity.start(ShopHomeActivity.this, 0, mShopManagerInfo.storeId);
                break;
            case R.id.text_notice:

                break;

            case R.id.text_deliever:

                break;
            case R.id.rightbtn:
                if (mShopManagerInfo.isFav == 1) {
                    requestFavStore(NetWorkHelper.API_POST_UN_FAV_STORE + "?token="
                            + AppShareUitl.getToken(ShopHomeActivity.this));
                } else {
                    requestFavStore(NetWorkHelper.API_POST_FAV_STORE + "?token="
                            + AppShareUitl.getToken(ShopHomeActivity.this));
                }
                break;
        }
    }

    private void requestFavStore(String url) {
        if (!mDialog.isShowing())
            mDialog.show();
        try {
            JSONObject params = new JSONObject();
            params.put("storeId", mShopManagerInfo.storeId);
            OkHttpUtils.postString().url(NetWorkHelper.getApiUrl(url))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(params.toString()).build().execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                if (mShopManagerInfo.isFav == 1) {
                                    mShopManagerInfo.isFav = 0;
                                    rightbtn.setSelected(false);
                                } else {
                                    mShopManagerInfo.isFav = 1;
                                    rightbtn.setSelected(true);
                                }
                            }
                            if (mDialog.isShowing())
                                mDialog.dismiss();
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            if (mDialog.isShowing())
                                mDialog.dismiss();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取商铺管理信息
     */
    private void getShopManagerInfo() {
        showProgressDialog();
        OkHttpUtils.get().url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_STOREINFO))
                .addParams("token", AppShareUitl.getToken(ShopHomeActivity.this))
                .addParams("storeId", mStoreId).build().execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {

                        if (response.code == ShiHuoResponse.SUCCESS) {
                            mShopManagerInfo = ShopManagerInfo.parseFormJsonStr(response.data);
                            getGoodsTypeList();
                            resetHeaderView();
                        } else {
                            Toast.makeText(ShopHomeActivity.this, response.msg, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                    }
                });
    }

    private void getGoodsTypeList() {
        // 本店商品分类
        OkHttpUtils.get().url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_GOODSTYPELIST))
        // .addParams("token", userModel.token)
                .addParams("storeId", mStoreId).build().execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                JSONArray jsonArray = new JSONObject(response.data)
                                        .getJSONArray("dataList");
                                goodsTypeModels.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    GoodsTypeModel goodsTypeModel = GoodsTypeModel
                                            .parseJsonStr(jsonArray.getJSONObject(i));
                                    goodsTypeModels.add(goodsTypeModel);
                                }
                                adapter = new GoogleMusicAdapter(getSupportFragmentManager());
                                pager.setAdapter(adapter);
                                indicator.setVisibility(View.VISIBLE);
                                indicator.setViewPager(pager);
                                initTabPagerIndicator();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(ShopHomeActivity.this, response.msg, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                    }
                });
    }

    private void resetHeaderView() {
        imageShopLogo.setImageURI(AppUtils.parse(AliyunHelper
                .getFullPathByName(mShopManagerInfo.storeLogoPicUrl)));
        textTitle.setText("店铺详情");
        if (TextUtils.isEmpty(mShopManagerInfo.storeDetail)) {
            textDesc.setText("暂无数据");
        } else {
            textDesc.setText(mShopManagerInfo.storeDetail);
        }
        if (TextUtils.isEmpty(mShopManagerInfo.circleName)) {
            tv_circle.setText("商圈:暂无数据");
        } else {
            tv_circle.setText("商圈:" + mShopManagerInfo.circleName);
        }

        if (TextUtils.isEmpty(mShopManagerInfo.businessTime)) {
            tv_time.setText("营业时间:暂无数据");
        } else {
            tv_time.setText("营业时间:" + mShopManagerInfo.businessTime);
        }
        if (!TextUtils.isEmpty(mShopManagerInfo.distributionTime)) {
            tv_send_time.setText("配送时间:" + mShopManagerInfo.distributionTime);
        } else {
            tv_send_time.setText("配送时间:无");
        }

        if (TextUtils.isEmpty(mShopManagerInfo.storeAddress)) {
            tv_address.setText("地址:暂无数据");
        } else {
            tv_address.setText("地址:" + mShopManagerInfo.storeAddress);
        }
        if (TextUtils.isEmpty(mShopManagerInfo.csPhoneNum)) {
            textCustomnumber.setText("客服:暂无数据");
        } else {
            textCustomnumber.setText("客服:" + mShopManagerInfo.csPhoneNum);
        }
        if (TextUtils.isEmpty(mShopManagerInfo.storeAnnouncement)) {
            textNotice.setText("暂无数据");
        } else {
            textNotice.setText(mShopManagerInfo.storeAnnouncement);
        }

        tv_liulan.setText("浏览量:" + mShopManagerInfo.browseNum);

        if (mShopManagerInfo.storeFreeShippingPrice >= 0) {
            layout_send_price.setVisibility(View.VISIBLE);
            textDeliever.setText("免费配送" + mShopManagerInfo.storeFreeShippingPrice + "元起");
        } else {
            layout_send_price.setVisibility(View.GONE);
        }
        if (mShopManagerInfo.isRecommended == 0) {
            iv_store_start.setVisibility(View.GONE);
        } else {
            iv_store_start.setVisibility(View.VISIBLE);
        }
        title.setText(mShopManagerInfo.storeName);

        if (mShopManagerInfo.isFav == 1) {
            rightbtn.setSelected(true);
        } else {
            rightbtn.setSelected(false);
        }
    }

    class GoogleMusicAdapter extends FragmentPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ShopHomeGoodsListFragment.newInstance(goodsTypeModels.get(position).typeId);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return goodsTypeModels.get(position).typeName;
        }

        @Override
        public int getCount() {
            return goodsTypeModels.size();
        }
    }
}
