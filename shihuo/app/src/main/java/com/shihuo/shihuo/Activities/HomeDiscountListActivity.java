package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.BannerView;
import com.shihuo.shihuo.Views.GoodsView;
import com.shihuo.shihuo.Views.ShoppingCarView;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreGridViewContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsModel;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2017/1/13.
 * 首页优惠专区 折扣区列表
 */

public class HomeDiscountListActivity extends BaseActivity {


    public static final String DISCOUNT_MODLE = "discount";
    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.load_more_grid_view)
    GridViewWithHeaderAndFooter loadMoreGridView;
    @BindView(R.id.load_more_grid_view_container)
    LoadMoreGridViewContainer loadMoreGridViewContainer;
    @BindView(R.id.load_more_grid_view_ptr_frame)
    PtrClassicFrameLayout loadMoreGridViewPtrFrame;
    @BindView(R.id.view_shoppingCar)
    ShoppingCarView mShoppingCarView;

    private ShopHomeGoodsListAdapter mAdapter;

    private int mPageNum = 1;

    private ArrayList<GoodsModel> goods = new ArrayList<>();
    //banner图集合
    private ArrayList<GoodsTypeModel> banners = new ArrayList<>();
    private BannerView bannerView;

    private String titleStr;
    private String id;

    public static void start(Context context, String title, String id) {
        Intent intent = new Intent(context, HomeDiscountListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_discount_list);
        ButterKnife.bind(this);
        titleStr = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");

        initViews();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(titleStr)) {
            title.setText(titleStr);
        } else {
            title.setText("运城识货购物网");
        }
        mShoppingCarView.setOnClickListener(new ShoppingCarView.OnViewClickListener() {
            @Override
            public void onShoppingCarListener() {
                if (AppShareUitl.isLogin(HomeDiscountListActivity.this)) {
                    ShoppingCarListActivity.start(HomeDiscountListActivity.this);
                } else {
                    LoginActivity.start(HomeDiscountListActivity.this);
                }
            }

            @Override
            public void onBackTopListener() {
                loadMoreGridView.smoothScrollToPosition(0);
            }
        });


        loadMoreGridViewPtrFrame.setLoadingMinTime(1000);
        loadMoreGridViewPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getBanner();
                mPageNum = 1;
                refreshData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, loadMoreGridView, header);
            }
        });

        loadMoreGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsModel goodsModel = goods.get(position);
                GoodsDetailActivity.start(HomeDiscountListActivity.this, goodsModel.goodsId);
            }
        });
        loadMoreGridViewContainer.setAutoLoadMore(false);
        loadMoreGridViewContainer.useDefaultFooter();
        bannerView = new BannerView(HomeDiscountListActivity.this);
        loadMoreGridView.addHeaderView(bannerView);
        mAdapter = new ShopHomeGoodsListAdapter();
        loadMoreGridView.setAdapter(mAdapter);

        loadMoreGridViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                refreshData();
            }
        });
        loadMoreGridViewPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMoreGridViewPtrFrame.autoRefresh();
            }
        }, 100);


    }

    /**
     * 获取列表
     */
    private void refreshData() {
        //获取系统一级分类对应的相关信息
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_DISCOUNT_LIST))
                .addParams("discountId", id)
                .addParams("pageNum", String.valueOf(mPageNum))
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        loadMoreGridViewPtrFrame.refreshComplete();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                if (!TextUtils.isEmpty(response.resultList)) {
                                    JSONArray jsonArray = new JSONArray(response.resultList);
                                    goods.clear();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        GoodsModel goodsModel = GoodsModel.parseJsonStr(jsonArray.getJSONObject(i));
                                        goods.add(goodsModel);
                                    }
                                    mAdapter.notifyDataSetChanged();
                                    mPageNum += 1;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(HomeDiscountListActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadMoreGridViewPtrFrame.refreshComplete();
                        AppUtils.showToast(HomeDiscountListActivity.this, "获取列表信息错误");
                    }
                });
    }

    @OnClick(R.id.imag_left)
    public void onClick() {
        finish();
    }


    private void getBanner() {
        //获取系统一级分类对应的相关信息
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_DISCOUNT_BANNER))
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {

                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                if (!TextUtils.isEmpty(response.data)) {
                                    JSONObject jsonObject = new JSONObject(response.data);
                                    //解析banner
                                    if (!TextUtils.isEmpty(jsonObject.getString("dataList"))) {
                                        JSONArray jsonArray = jsonObject.getJSONArray("dataList");
                                        banners.clear();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            GoodsTypeModel goodsTypeModel = GoodsTypeModel.parseJsonStr(jsonArray.getJSONObject(i));
                                            banners.add(goodsTypeModel);
                                        }
                                    }
                                    bannerView.setData(banners);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(HomeDiscountListActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadMoreGridViewPtrFrame.refreshComplete();
                        AppUtils.showToast(HomeDiscountListActivity.this, "获取二级系统分类出错");
                    }
                });
    }

    public class ShopHomeGoodsListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return goods.size();
        }

        @Override
        public Object getItem(int position) {
            return goods.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ShopHomeGoodsListAdapter.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(HomeDiscountListActivity.this).inflate(R.layout.item_goods_shop_home, null);
                viewHolder = new ShopHomeGoodsListAdapter.ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ShopHomeGoodsListAdapter.ViewHolder) convertView.getTag();
            final GoodsModel goodsModel = (GoodsModel) getItem(position);
            viewHolder.view_goods.bindData(goodsModel);
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.view_goods)
            GoodsView view_goods;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
