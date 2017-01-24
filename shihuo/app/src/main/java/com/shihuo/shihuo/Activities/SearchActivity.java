
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylhyl.crlayout.SwipeRefreshAdapterView;
import com.mylhyl.crlayout.SwipeRefreshRecyclerView;
import com.shihuo.shihuo.Adapters.SearchAdapter;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.ClearEditText;
import com.shihuo.shihuo.models.BaseGoodsModel;
import com.shihuo.shihuo.models.SearchModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.shihuo.shihuo.models.SearchModel.ITEM_TYPE_TITLE_RESULTE;

/**
 * Created by lishuai on 16/12/26.
 */

public class SearchActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        SwipeRefreshAdapterView.OnListLoadListener {
    @BindView(R.id.imag_left)
    ImageView leftbtn;

    @BindView(R.id.view_search)
    ClearEditText view_search;

    @BindView(R.id.tv_search)
    TextView tv_search;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshRecyclerView mSwipeRefresh;

    private List<SearchModel> mList;

    private SearchAdapter mAdapter;

    private List<String> mHotKeyWords = new ArrayList<>();


    public static void start(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        mList = new ArrayList<>();
        AppUtils.initSwipeRefresh(SearchActivity.this, mSwipeRefresh);
        mSwipeRefresh.setOnRefreshListener(this);
        mAdapter = new SearchAdapter(SearchActivity.this, mList, new SearchAdapter.OnSearchItermClickListener() {
            @Override
            public void onSearCh(String keyWords) {
                view_search.setText(keyWords);
                requestSearch(keyWords);
            }

            @Override
            public void onClear() {
                AppShareUitl.saveHistorySearchWords(getApplicationContext(), new ArrayList<String>());
                requestHot();
            }

            @Override
            public void onMoreClick(String moreStr) {

                if (moreStr.contains("店铺")) {
                    SearchStoreMoreActivity.start(SearchActivity.this, tv_search.getText().toString());
                } else {
                    SearchGoodsMoreActivity.start(SearchActivity.this, tv_search.getText().toString());
                }

            }
        });

        mSwipeRefresh.setAdapter(mAdapter);
//        requestHot();
        initHistory();
        view_search.setOnClickDeleteListener(new ClearEditText.OnClickDeleteListener() {
            @Override
            public void onClickDeleteListener() {
                initHistory();
            }
        });
    }

//    private void requestHot() {
//        try {
//            OkHttpUtils.get()
//                    .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_SEARCH_HOT_KEWWORDS))
//                    .build()
//                    .execute(new ShihuoStringCallback() {
//                        @Override
//                        public void onResponse(ShiHuoResponse response, int id) {
//                            if (response.code == ShiHuoResponse.SUCCESS) {
//                                mHotKeyWords = SearchModel.parseJsonHotKeyWords(response.data);
//                                initHistory();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void initHistory() {
        mList.clear();
        // 历史搜索item
        List<String> keyWordList = AppShareUitl.getHistorySearchWordString(getApplicationContext());
        for (int i = 0; i < keyWordList.size(); i++) {
            SearchModel historyModel = new SearchModel();
            historyModel.item_type = SearchModel.ITEM_TYPE_TITLE;
            historyModel.item_type_title = keyWordList.get(i);
            mList.add(historyModel);
        }
        if (keyWordList.size() > 0) {
            // 清除历史搜索
            SearchModel clearModel = new SearchModel();
            clearModel.item_type = SearchModel.ITEM_TYPE_TITLE_CLEAR;
            clearModel.item_type_title = "清除搜索历史";
            mList.add(clearModel);
        }
        // 热门搜索
        SearchModel hotModel = new SearchModel();
        hotModel.item_type = SearchModel.ITEM_TYPE_HOT;
        hotModel.dataList = mHotKeyWords;
        mList.add(hotModel);

        mAdapter.bindData(mList);
    }

    private void requestSearch(String keywords) {
        AppUtils.hideKeyBord(SearchActivity.this, view_search);
        if (TextUtils.isEmpty(keywords)) {
            AppUtils.showToast(SearchActivity.this, "请输入搜索关键字");
            return;
        }

        saveSearchWords(keywords);

        String url = NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_SEARCH_RESULT) + "?keywords=" + keywords;
        try {
            OkHttpUtils.get()
                    .url(url)
                    .build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            mSwipeRefresh.setRefreshing(false);
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                SearchModel model = SearchModel.parseJsonStoreAndGoods(response.data);
                                handlerData(model);
                            }
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mSwipeRefresh.setRefreshing(false);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存搜索关键字
     *
     * @param keywords
     */
    private void saveSearchWords(String keywords) {
        List<String> keyWordList = AppShareUitl.getHistorySearchWordString(getApplicationContext());
        List<String> newWords = new ArrayList<>();
        newWords.add(keywords);
        for (int i = 0; i < keyWordList.size(); i++) {
            if (!keywords.equals(keyWordList.get(i)) && newWords.size() < 5) {
                newWords.add(keyWordList.get(i));
            }
        }
        AppShareUitl.saveHistorySearchWords(getApplicationContext(), newWords);
    }

    /**
     * 解析数据
     *
     * @param modelTemp
     */
    private void handlerData(SearchModel modelTemp) {
        if (modelTemp == null) {
            return;
        }
        mList.clear();
        // 拼装店铺数据
        SearchModel modelStoreTitle = new SearchModel();
        modelStoreTitle.item_type = ITEM_TYPE_TITLE_RESULTE;
        modelStoreTitle.item_type_title = "含 '" + view_search.getText().toString().trim() + "' 的店铺";
        mList.add(modelStoreTitle);
        if (!modelTemp.shStoresList.isEmpty()) {
            for (int i = 0; i < modelTemp.shStoresList.size(); i++) {
                SearchModel modelStoreResult = new SearchModel();
                modelStoreResult.shStores = modelTemp.shStoresList.get(i);
                modelStoreResult.item_type = SearchModel.ITEM_TYPE_STORE;
                mList.add(modelStoreResult);
            }
        }

        // 拼装商品数据
        SearchModel modelGoodsTitle = new SearchModel();
        modelGoodsTitle.item_type = ITEM_TYPE_TITLE_RESULTE;
        modelGoodsTitle.item_type_title = "含 '" + view_search.getText().toString().trim() + "' 的商品";
        mList.add(modelGoodsTitle);
        if (!modelTemp.shGoodsList.isEmpty()) {
            int temp = modelTemp.shGoodsList.size() / 2;
            if (modelTemp.shGoodsList.size() % 2 != 0) {
                temp = temp + 1;
            }
            for (int i = 0; i < temp; i++) {
                SearchModel searModel = new SearchModel();
                searModel.item_type = SearchModel.ITEM_TYPE_GOODS;
                BaseGoodsModel baseGoodsModel = new BaseGoodsModel();
                baseGoodsModel.goodsLeftModel = modelTemp.shGoodsList.get(2 * i);
                if (2 * i + 1 < modelTemp.shGoodsList.size()) {
                    baseGoodsModel.goodsRightModel = modelTemp.shGoodsList.get(2 * i + 1);
                }
                searModel.shGoods = baseGoodsModel;
                mList.add(searModel);
            }
        }
        mAdapter.bindData(mList);
    }

    @OnClick({
            R.id.imag_left, R.id.tv_search
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;

            case R.id.tv_search:
                requestSearch(view_search.getText().toString().trim());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
//        requestSearch();
    }

    @Override
    public void onListLoad() {

    }
}
