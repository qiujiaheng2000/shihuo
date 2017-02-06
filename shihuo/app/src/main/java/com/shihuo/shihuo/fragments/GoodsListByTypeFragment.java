package com.shihuo.shihuo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.shihuo.shihuo.Activities.GoodsDetailActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.CustomAutolabelHeaderView;
import com.shihuo.shihuo.Views.GoodsView;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreGridViewContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.models.GoodsModel;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.StoreDetailModel;
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
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2016/12/10.
 * 商品列表分类界面
 */

public class GoodsListByTypeFragment extends Fragment implements CustomAutolabelHeaderView.LabelChangeListener {


    public static final String KEY_GOODSTYPE = "goodsType";
    @BindView(R.id.load_more_grid_view)
    GridViewWithHeaderAndFooter loadMoreGridView;
    @BindView(R.id.load_more_grid_view_container)
    LoadMoreGridViewContainer loadMoreGridViewContainer;
    @BindView(R.id.load_more_grid_view_ptr_frame)
    PtrClassicFrameLayout loadMoreGridViewPtrFrame;


    private String goodsType;

    public static GoodsListByTypeFragment newInstance(int goodsType) {
        Bundle args = new Bundle();
        args.putInt(KEY_GOODSTYPE, goodsType);
        GoodsListByTypeFragment fragment = new GoodsListByTypeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<GoodsModel> goods = new ArrayList<>();
    //当前一级分类下的二级分类列表
    private ArrayList<GoodsTypeModel> secondGoodsTypeModel = new ArrayList<>();
    //banner图集合
    private ArrayList<GoodsTypeModel> banners = new ArrayList<>();
    //推荐的店铺列表
    private ArrayList<StoreDetailModel> stores = new ArrayList<>();
    private BaseAdapter mAdapter;

    private String mCurrentsysSecondTypeId = "0";//当前选中的二级分类
    private String mCurrentsysStoreId = "";//当前选中推荐店铺
    private String mCurrentOrderType = "1";//当前的排序类型    //orderType:1 价格 2 销量
    private String mCurrentDescribe = "asc";//当前的排序方法 describe: asc 降序 desc 升序

    private int mPageNum;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodsType = String.valueOf(getArguments().getInt(KEY_GOODSTYPE, 0));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_shophome_goods_list, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void initViews() {
        loadMoreGridViewPtrFrame.setLoadingMinTime(1000);
        loadMoreGridViewPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPageNum = 0;
                goods.clear();
                refreshData("" + mPageNum);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, loadMoreGridView, header);
            }
        });
        loadMoreGridViewPtrFrame.disableWhenHorizontalMove(true);
        loadMoreGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsModel goodsModel = goods.get(position);
                GoodsDetailActivity.start(getContext(), goodsModel.goodsId);
            }
        });
        loadMoreGridViewContainer.setAutoLoadMore(false);
        loadMoreGridViewContainer.useDefaultFooter();
//        mAdapter = new ShopHomeGoodsListAdapter();
//        loadMoreGridView.setAdapter(mAdapter);

        loadMoreGridViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {


            }
        });
        loadMoreGridViewPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMoreGridViewPtrFrame.autoRefresh();
            }
        }, 100);
    }

    private void loadMoreData() {
        getGoodsList("" + mPageNum);
    }

    private void refreshData(final String pageNum) {
        getSystypeInfo(pageNum);
    }

    private void getSystypeInfo(final String pageNum) {
        //获取系统一级分类对应的相关信息
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_SYSTYPEINFO))
                .addParams("typeId", goodsType)
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {

                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {

                                JSONObject jsonObject = new JSONObject(response.data);
                                JSONArray jsonArray = null;
                                if (!TextUtils.isEmpty(jsonObject.getString("shSysGoodsSecondTypeList"))) {
                                    jsonArray = jsonObject.getJSONArray("shSysGoodsSecondTypeList");
                                    secondGoodsTypeModel.clear();
                                    GoodsTypeModel allGoodsTypeModel = new GoodsTypeModel();
                                    allGoodsTypeModel.typeId = 0;
                                    allGoodsTypeModel.typeName = "全部";
                                    secondGoodsTypeModel.add(allGoodsTypeModel);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        GoodsTypeModel goodsTypeModel = GoodsTypeModel.parseJsonStr(jsonArray.getJSONObject(i));
                                        secondGoodsTypeModel.add(goodsTypeModel);
                                    }
                                }
                                //解析推荐的店铺信息
                                if (!TextUtils.isEmpty(jsonObject.getString("shStores"))) {
                                    jsonArray = jsonObject.getJSONArray("shStores");
                                    stores.clear();
                                    StoreDetailModel allStoreDetailModel = new StoreDetailModel();
                                    allStoreDetailModel.storeId = "";
                                    allStoreDetailModel.storeName = "全部";
                                    stores.add(allStoreDetailModel);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        StoreDetailModel storeDetailModel = StoreDetailModel.parseJsonStr(jsonArray.getJSONObject(i));
                                        stores.add(storeDetailModel);
                                    }
                                }

                                //解析banner
                                if (!TextUtils.isEmpty(jsonObject.getString("shAdvertisingList"))) {
                                    jsonArray = jsonObject.getJSONArray("shAdvertisingList");
                                    banners.clear();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        GoodsTypeModel goodsTypeModel = GoodsTypeModel.parseJsonStr(jsonArray.getJSONObject(i));
                                        banners.add(goodsTypeModel);
                                    }
                                }


                                addHeaderView();
                                getGoodsList(pageNum);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadMoreGridViewPtrFrame.refreshComplete();
                        AppUtils.showToast(getContext(), "获取二级系统分类出错");
                    }
                });
    }

    private void addHeaderView() {
        if (mAdapter == null) {
            CustomAutolabelHeaderView customAutolabelHeaderView = new CustomAutolabelHeaderView(getContext(), this);
            customAutolabelHeaderView.addAutoLabels(secondGoodsTypeModel, stores, banners);
            loadMoreGridView.addHeaderView(customAutolabelHeaderView);
            mAdapter = new ShopHomeGoodsListAdapter();
            loadMoreGridView.setAdapter(mAdapter);
        }
    }

    private void getGoodsList(String pageNum) {
        //本店商品列表
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_GOODS_LIST_BY_SYSTYPE))
                .addParams("sysTypeId", goodsType)
                .addParams("storeId", mCurrentsysStoreId)
                .addParams("pageNum", pageNum)
                .addParams("sysTypeSecondId", mCurrentsysSecondTypeId)
                .addParams("orderType", mCurrentOrderType)//orderType:1 价格 2 销量
                .addParams("describe", mCurrentDescribe)//describe: asc 降序 desc 升序
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        loadMoreGridViewPtrFrame.refreshComplete();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                mPageNum += 1;
                                if (!TextUtils.isEmpty(response.resultList)) {
                                    JSONArray jsonArray = new JSONArray(response.resultList);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        GoodsModel goodsTypeModel = GoodsModel.parseJsonStr(jsonArray.getJSONObject(i));
                                        goods.add(goodsTypeModel);
                                    }
                                    mAdapter.notifyDataSetChanged();
                                    loadMoreGridViewContainer.setAutoLoadMore(true);
                                    loadMoreGridViewContainer.loadMoreFinish(jsonArray.length() > 0, true);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadMoreGridViewPtrFrame.refreshComplete();
                        AppUtils.showToast(getContext(), "获取商品列表出错");
                    }
                });
    }

    @Override
    public void onTypeLabelChanged(GoodsTypeModel goodsTypeModel) {
        mCurrentsysSecondTypeId = String.valueOf(goodsTypeModel.typeId);
        mPageNum = 0;
        goods.clear();
        getGoodsList("" + mPageNum);
//        loadMoreGridViewPtrFrame.autoRefresh();
    }

    @Override
    public void onStoreLabelChanged(StoreDetailModel storeDetailModel) {
        mCurrentsysStoreId = storeDetailModel.storeId;
        mPageNum = 0;
        goods.clear();
        getGoodsList("" + mPageNum);
//        loadMoreGridViewPtrFrame.autoRefresh();
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
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_goods_shop_home, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
