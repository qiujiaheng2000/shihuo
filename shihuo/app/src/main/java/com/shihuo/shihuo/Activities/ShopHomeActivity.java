package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.shop.models.ShopManagerInfo;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.TabPageIndicator;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by cm_qiujiaheng on 2016/12/17.
 * 店铺首页界面
 */

public class ShopHomeActivity extends BaseActivity {
    private static final String[] CONTENT = new String[]{"Recent", "Artists", "Albums", "Songs", "Playlists", "Genres", "Songs", "Playlists", "Genres", "Songs", "Playlists", "Genres"};
    public static final String KEY_STORE_ID = "storeId";
    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.image_shop_logo)
    SimpleDraweeView imageShopLogo;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_desc)
    TextView textDesc;
    @BindView(R.id.text_customnumber)
    TextView textCustomnumber;
    @BindView(R.id.text_qr)
    TextView textQr;
    @BindView(R.id.text_notice)
    TextView textNotice;

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

    //本店分类列表
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

    }

    private void initTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_NOWEIGHT_EXPAND_NOSAME);// 设置模式，一定要先设置模式
//        indicator.setDividerColor(Color.parseColor("#00bbcf"));// 设置分割线的颜色
//        indicator.setDividerPadding(AppUtils.dip2px(CircleListActivity.this, 10));
        indicator.setIndicatorColor(getResources().getColor(R.color.common_theme));// 设置底部导航线的颜色
        indicator.setUnderlineHeight(0);
        indicator.setTextColorSelected(getResources().getColor(R.color.common_theme));// 设置tab标题选中的颜色
        indicator.setTextColor(getResources().getColor(R.color.common_font_black));// 设置tab标题未被选中的颜色
        indicator.setTextSize(AppUtils.dip2px(ShopHomeActivity.this, 16));// 设置字体大小
    }


    @OnClick({R.id.imag_left, R.id.image_shop_logo, R.id.text_customnumber, R.id.text_qr, R.id.text_notice, R.id.text_deliever, R.id.rightbtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.image_shop_logo:

                break;
            case R.id.text_customnumber:

                break;
            case R.id.text_qr:

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
                .addParams("storeId", mStoreId).build()
                .execute(new ShihuoStringCallback() {
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
        //本店商品分类
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_GOODSTYPELIST))
//                .addParams("token", userModel.token)
                .addParams("storeId", mStoreId)
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                JSONArray jsonArray = new JSONObject(response.data).getJSONArray("dataList");
                                goodsTypeModels.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    GoodsTypeModel goodsTypeModel = GoodsTypeModel.parseJsonStr(jsonArray.getJSONObject(i));
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
                            Toast.makeText(ShopHomeActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                    }
                });
    }


    private void resetHeaderView() {
        imageShopLogo.setImageURI(AppUtils.parse(AliyunHelper.getFullPathByName(mShopManagerInfo.storeLogoPicUrl)));
        textTitle.setText(mShopManagerInfo.storeName);
        textDesc.setText(mShopManagerInfo.storeDetail);
        textCustomnumber.setText(String.format("客服电话：%1$s", mShopManagerInfo.csPhoneNum));
        textNotice.setText(mShopManagerInfo.storeAnnouncement);
        title.setText(String.format("%1$s店铺首页", mShopManagerInfo.storeName));

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
//            return TodayFragment.newInstance();
            return ShopHomeGoodsListFragment.newInstance(goodsTypeModels.get(position).typeId);
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return CONTENT[position % CONTENT.length].toUpperCase();
            return goodsTypeModels.get(position).typeName;
        }

        @Override
        public int getCount() {
            return goodsTypeModels.size();
//            return CONTENT.length;
        }
    }
}
