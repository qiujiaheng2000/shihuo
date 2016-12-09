package com.shihuo.shihuo.Activities.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.Activities.shop.models.ShopManagerInfo;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.ShopHeaderView;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.ShopMainGridModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.Toaster;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 * 商铺界面
 */

public class ShopActivity extends BaseActivity {


    private static final long OPERATIONID_PUBLISHGOODS = 1;//商品发布
    private static final long OPERATIONID_GOODSMANAGER = 2;//商品管理
    private static final long OPERATIONID_ORDERSMANAGER = 3;//订单管理
    private static final long OPERATIONID_SHOPTYPEMANAGER = 4;//店铺分类管理
    private static final long OPERATIONID_SHOPSTATISTICS = 5;//店铺统计
    private static final long OPERATIONID_SHOPSETTING = 6;//店铺设置
    private static final long OPERATIONID_SHOPEXTRACT = 7;//申请提现
    private static final int STROE_UNHAVEGOODSTYPE = 0;
    private static final int STROE_HAVEGOODSTYPE = 1;

    private static ShopManagerInfo SHOP_MANAGER_INFO;

    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.shop_main_gridview)
    GridViewWithHeaderAndFooter shopMainGridview;

    private ArrayList<ShopMainGridModel> mainGridModels = new ArrayList<>();
    private ShopHeaderView shopHeaderView;

    public static void start(Context context) {
        Intent intent = new Intent(context, ShopActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shop);
        ButterKnife.bind(this);
        initViews();
    }

    /**
     * 获取商铺管理信息
     */
    private void getShopManagerInfo() {
        showProgressBar();
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_STOREINFO))
                .addParams("token", AppShareUitl.getToken(ShopActivity.this))
                .addParams("storeId", AppShareUitl.getUserInfo(ShopActivity.this).storeId)
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressBar();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            SHOP_MANAGER_INFO = ShopManagerInfo.parseFormJsonStr(response.data);
                            resetView();
                        } else {
                            Toast.makeText(ShopActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressBar();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getShopManagerInfo();
    }

    private void resetView() {
        shopHeaderView.setData(SHOP_MANAGER_INFO);
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.shihuo_shop_main);
        getGridViewDatas();
    }

    /**
     * 获取商铺操作台数据
     */
    private void getGridViewDatas() {
        ShopMainGridModel publishGoods = new ShopMainGridModel("1", "", "发布新品");
        ShopMainGridModel goodsManager = new ShopMainGridModel("2", "", "商品管理");
        ShopMainGridModel ordersManager = new ShopMainGridModel("3", "", "订单管理");
        ShopMainGridModel shopTypeManager = new ShopMainGridModel("4", "", "店铺分类管理");
        final ShopMainGridModel shopStatistics = new ShopMainGridModel("5", "", "店铺统计");
        ShopMainGridModel shopSetting = new ShopMainGridModel("6", "", "店铺设置");
        ShopMainGridModel shopxtract = new ShopMainGridModel("7", "", "申请提现");
        mainGridModels.add(publishGoods);
        mainGridModels.add(goodsManager);
        mainGridModels.add(ordersManager);
        mainGridModels.add(shopTypeManager);
        mainGridModels.add(shopStatistics);
        mainGridModels.add(shopSetting);
        mainGridModels.add(shopxtract);
        MyGridViewAdatpter myGridViewAdatpter = new MyGridViewAdatpter();
        shopHeaderView = new ShopHeaderView(ShopActivity.this);
        shopMainGridview.addHeaderView(shopHeaderView, null, false);
        shopMainGridview.setAdapter(myGridViewAdatpter);
        shopMainGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (OPERATIONID_PUBLISHGOODS == id) {//商品发布
                    if (SHOP_MANAGER_INFO.validateHaveGoodsType == STROE_HAVEGOODSTYPE) {
                        PublishGoodsActivity.start(ShopActivity.this);
                    } else {
                        Toaster.toastShort("您还未添加店铺商品类别，请到商铺分类管理添加商铺分类");
                    }
                }
                if (OPERATIONID_GOODSMANAGER == id) {//商品管理
//                    if (SHOP_MANAGER_INFO.validateHaveGoodsType == STROE_HAVEGOODSTYPE) {
                        GoodsManagerActivity.start(ShopActivity.this);
//                    } else {
//                        Toaster.toastShort("您还未添加店铺商品类别，请到商铺分类管理添加商铺分类");
//                    }
                }
                if (OPERATIONID_ORDERSMANAGER == id) {//订单管理
                    OrdersManagerActivity.start(ShopActivity.this);
                }
                if (OPERATIONID_SHOPTYPEMANAGER == id) {//店铺分类管理
                    ShopTypeManagerActivity.start(ShopActivity.this);
                }
                if (OPERATIONID_SHOPSTATISTICS == id) {//店铺统计
                    ShopStatisticsManagerActivity.start(ShopActivity.this);
                }
                if (OPERATIONID_SHOPSETTING == id) {//店铺设置
                    ShopSettingActivity.start(ShopActivity.this);
                }
                if (OPERATIONID_SHOPEXTRACT == id) {//申请提现
                    ShopExtractActivity.start(ShopActivity.this);
                }
            }
        });
    }

    @OnClick(R.id.imag_left)
    public void onClick() {
        finish();
    }

    class MyGridViewAdatpter extends BaseAdapter {

        @Override
        public int getCount() {
            return mainGridModels.size();
        }

        @Override
        public Object getItem(int position) {
            return mainGridModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return Long.parseLong(mainGridModels.get(position).id);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(ShopActivity.this).inflate(R.layout.item_shop_main_grid_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            ShopMainGridModel shopMainGridModel = (ShopMainGridModel) getItem(position);
            viewHolder.textOperateName.setText(shopMainGridModel.name);
            //TODO 设置图片
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.image_icon)
            SimpleDraweeView imageIcon;
            @BindView(R.id.text_operate_name)
            TextView textOperateName;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
