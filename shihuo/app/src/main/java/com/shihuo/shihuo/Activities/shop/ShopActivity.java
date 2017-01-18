
package com.shihuo.shihuo.Activities.shop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TResult;
import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.Activities.ShopHomeActivity;
import com.shihuo.shihuo.Activities.WuliuActivity;
import com.shihuo.shihuo.Activities.shop.models.ShopManagerInfo;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.ShopHeaderView;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.ShopMainGridModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.FileUtils;
import com.shihuo.shihuo.util.Toaster;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by cm_qiujiaheng on 2016/12/4. 商铺界面
 */

public class ShopActivity extends BaseActivity implements ShopHeaderView.OnLogoClick {

    private static final long OPERATIONID_PUBLISHGOODS = 1;// 商品发布

    private static final long OPERATIONID_GOODSMANAGER = 2;// 商品管理

    private static final long OPERATIONID_ORDERSMANAGER = 3;// 订单管理

    private static final long OPERATIONID_SHOPTYPEMANAGER = 4;// 店铺分类管理

    private static final long OPERATIONID_SHOPSTATISTICS = 5;// 店铺统计

    private static final long OPERATIONID_SHOPSETTING = 6;// 店铺设置

    private static final long OPERATIONID_SHOPEXTRACT = 7;// 申请提现

    private static final long OPERATIONID_WULIU = 8;// 物流信息

    private static final int STROE_UNHAVEGOODSTYPE = 0;

    private static final int STROE_HAVEGOODSTYPE = 1;

    public static ShopManagerInfo SHOP_MANAGER_INFO;

    @BindView(R.id.imag_left)
    ImageView imagLeft;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.rightbtn)
    Button rightbtn;

    @BindView(R.id.shop_main_gridview)
    GridViewWithHeaderAndFooter shopMainGridview;

    private ArrayList<ShopMainGridModel> mainGridModels = new ArrayList<>();

    private ShopHeaderView shopHeaderView;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(SettingEditActivity.RefreshStoreInfo_Action)) {
                getShopManagerInfo();
            }
        }
    };

    public static void start(Context context) {
        Intent intent = new Intent(context, ShopActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SettingEditActivity.RefreshStoreInfo_Action);
        registerReceiver(broadcastReceiver, intentFilter);

        setContentView(R.layout.layout_shop);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    /**
     * 获取商铺管理信息
     */
    private void getShopManagerInfo() {
        showProgressDialog();
        OkHttpUtils.get().url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_STOREINFO))
                .addParams("token", AppShareUitl.getToken(ShopActivity.this))
                .addParams("storeId", AppShareUitl.getUserInfo(ShopActivity.this).storeId).build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            SHOP_MANAGER_INFO = ShopManagerInfo.parseFormJsonStr(response.data);
                            resetView();
                        } else {
                            Toast.makeText(ShopActivity.this, response.msg, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
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
        rightbtn.setVisibility(View.VISIBLE);
        rightbtn.setBackground(getResources().getDrawable(R.mipmap.icon_store_title));
        rightbtn.setText("预览");
        title.setText("商铺管理");
        getGridViewDatas();
    }

    /**
     * 获取商铺操作台数据
     */
    private void getGridViewDatas() {
        ShopMainGridModel publishGoods = new ShopMainGridModel("1", R.mipmap.icon_publish_goods,
                "发布新品");
        ShopMainGridModel goodsManager = new ShopMainGridModel("2", R.mipmap.icon_goods_manager,
                "商品管理");
        ShopMainGridModel ordersManager = new ShopMainGridModel("3", R.mipmap.icon_order_manager,
                "订单管理");
        ShopMainGridModel shopTypeManager = new ShopMainGridModel("4",
                R.mipmap.icon_shop_type_manager, "店铺分类管理");
        final ShopMainGridModel shopStatistics = new ShopMainGridModel("5",
                R.mipmap.icon_shop_statistics, "店铺统计");
        ShopMainGridModel shopSetting = new ShopMainGridModel("6", R.mipmap.icon_shop_setting,
                "店铺设置");
        ShopMainGridModel shopxtract = new ShopMainGridModel("7", R.mipmap.icon_extract, "申请提现");
        ShopMainGridModel wuliu = new ShopMainGridModel("8", R.mipmap.icon_extract, "同城配送");
        mainGridModels.add(publishGoods);
        mainGridModels.add(goodsManager);
        mainGridModels.add(ordersManager);
        mainGridModels.add(shopTypeManager);
        mainGridModels.add(shopStatistics);
        mainGridModels.add(shopSetting);
        mainGridModels.add(shopxtract);
        mainGridModels.add(wuliu);
        MyGridViewAdatpter myGridViewAdatpter = new MyGridViewAdatpter();
        shopHeaderView = new ShopHeaderView(ShopActivity.this, this);
        shopMainGridview.addHeaderView(shopHeaderView, null, false);
        shopMainGridview.setAdapter(myGridViewAdatpter);
        shopMainGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (OPERATIONID_PUBLISHGOODS == id) {// 商品发布
                    if (SHOP_MANAGER_INFO.validateHaveGoodsType == STROE_HAVEGOODSTYPE) {
                        PublishGoodsActivity.start(ShopActivity.this);
                    } else {
                        Toaster.toastShort("请先添加商品分类");
                    }
                }
                if (OPERATIONID_GOODSMANAGER == id) {// 商品管理
                    // if (SHOP_MANAGER_INFO.validateHaveGoodsType ==
                    // STROE_HAVEGOODSTYPE) {
                    GoodsManagerActivity.start(ShopActivity.this);
                    // } else {
                    // Toaster.toastShort("您还未添加店铺商品类别，请到商铺分类管理添加商铺分类");
                    // }
                }
                if (OPERATIONID_ORDERSMANAGER == id) {// 订单管理
//                    OrdersManagerActivity.start(ShopActivity.this);
                    MyShopOrdersListActivity.startMyOrdersListActivity(ShopActivity.this);
                }
                if (OPERATIONID_SHOPTYPEMANAGER == id) {// 店铺分类管理
                    ShopTypeManagerActivity.start(ShopActivity.this);
                }
                if (OPERATIONID_SHOPSTATISTICS == id) {// 店铺统计
                    ShopStatisticsManagerActivity.start(ShopActivity.this);
                }
                if (OPERATIONID_SHOPSETTING == id) {// 店铺设置
                    ShopSettingActivity.start(ShopActivity.this);
                }
                if (OPERATIONID_SHOPEXTRACT == id) {// 申请提现
                    ReflectActivity.start(ShopActivity.this);
                }
                if (OPERATIONID_WULIU == id) {// 物流信息
                    WuliuActivity.start(ShopActivity.this);
                }
            }
        });
    }

    @OnClick({
            R.id.imag_left, R.id.rightbtn
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.rightbtn:
                if (SHOP_MANAGER_INFO != null) {
                    ShopHomeActivity.start(ShopActivity.this, SHOP_MANAGER_INFO.storeId);
                }
                break;
        }
    }

    private SimpleDraweeView toSetImageLogo;

    @Override
    public void onLogoClick(SimpleDraweeView imageLogo) {
        toSetImageLogo = imageLogo;
        getPhoto();
    }


    @Override
    public void takeSuccess(final TResult takeResult) {
        Log.i("takePhoto", "takeSuccess：" + takeResult.getImage().getCompressPath());
        toSetImageLogo.setImageURI(AppUtils.parseFromSDCard(takeResult.getImage().getCompressPath()));
        AliyunHelper.getInstance().asyncUplodaFile(takeResult.getImage().getCompressPath(), new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                //阿里云上传成功了，上传到自己的服务器
                modifyStoreLogoPicUrl(FileUtils.getFileName(takeResult.getImage().getCompressPath()));
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
                Toaster.toastShort("修改头像失败");
            }
        });
    }

    /**
     * 修改店铺logo
     */
    private void modifyStoreLogoPicUrl(String avatarUrl) {
//        showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("storeId", AppShareUitl.getUserInfo(this).storeId);
            params.put("storeLogoPicUrl", avatarUrl);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_STORE_ICON) + "?token=" + AppShareUitl.getUserInfo(this).token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
//                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            Toaster.toastShort("修改logo成功");
                        } else {
                            AppUtils.showToast(ShopActivity.this, response.msg);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        hideProgressDialog();
                        Toaster.toastShort("修改logo失败");
                    }
                });
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
                convertView = LayoutInflater.from(ShopActivity.this).inflate(
                        R.layout.item_shop_main_grid_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            ShopMainGridModel shopMainGridModel = (ShopMainGridModel) getItem(position);
            viewHolder.textOperateName.setText(shopMainGridModel.name);
            viewHolder.imageIcon.setImageResource(shopMainGridModel.iconUrlResid);
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.image_icon)
            ImageView imageIcon;

            @BindView(R.id.text_operate_name)
            TextView textOperateName;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
