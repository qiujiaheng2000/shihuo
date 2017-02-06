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
import com.shihuo.shihuo.Activities.ShopHomeActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.GoodsView;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreGridViewContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsModel;
import com.shihuo.shihuo.models.LoginModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;

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
 * 店铺首页商品列表
 */

public class ShopHomeGoodsListFragment extends Fragment {


    public static final String KEY_GOODSTYPE = "goodsType";
    @BindView(R.id.load_more_grid_view)
    GridViewWithHeaderAndFooter loadMoreGridView;
    @BindView(R.id.load_more_grid_view_container)
    LoadMoreGridViewContainer loadMoreGridViewContainer;
    @BindView(R.id.load_more_grid_view_ptr_frame)
    PtrClassicFrameLayout loadMoreGridViewPtrFrame;


    private String goodsType;

    private int mPageNum = 1;

    public static ShopHomeGoodsListFragment newInstance(int goodsType) {
        Bundle args = new Bundle();
        args.putInt(KEY_GOODSTYPE, goodsType);
        ShopHomeGoodsListFragment fragment = new ShopHomeGoodsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<GoodsModel> goods = new ArrayList<>();
    private BaseAdapter mAdapter;

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
                mPageNum = 1;
                goods.clear();
                refreshData("" + mPageNum);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, loadMoreGridView, header);
            }
        });

        loadMoreGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsModel goodsModel = (GoodsModel) parent.getItemAtPosition(position);
                if (goodsModel != null && !TextUtils.isEmpty(goodsModel.goodsId)) {
                    GoodsDetailActivity.start(getContext(), goodsModel.goodsId);
                }
            }
        });
        mAdapter = new ShopHomeGoodsListAdapter();
        loadMoreGridViewContainer.setAutoLoadMore(false);
        loadMoreGridViewContainer.useDefaultFooter();
        loadMoreGridView.setAdapter(mAdapter);

        loadMoreGridViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                refreshData("" + mPageNum);
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

    }

    private void refreshData(final String pageNum) {
        final LoginModel userModel = AppShareUitl.getUserInfo(getContext());
        //本店商品分类
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_GOODS_BYTYPE_HOME))
                .addParams("typeId", goodsType)
                .addParams("storeId", ((ShopHomeActivity) getActivity()).getmShopManagerInfo().storeId)
                .addParams("pageNum", pageNum)
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        loadMoreGridViewPtrFrame.refreshComplete();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            mPageNum += 1;
                            try {
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

}
