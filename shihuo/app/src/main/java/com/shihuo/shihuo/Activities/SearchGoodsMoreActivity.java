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

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.GoodsView;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreGridViewContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.models.GoodsModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;

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
 * Created by lishuai on 16/12/26.
 * 搜索结果的店铺的 "更多"列表
 */

public class SearchGoodsMoreActivity extends BaseActivity {

    private static final String KEYWORD = "keyWord";
    @BindView(R.id.load_more_grid_view)
    GridViewWithHeaderAndFooter loadMoreGridView;
    @BindView(R.id.load_more_grid_view_container)
    LoadMoreGridViewContainer loadMoreGridViewContainer;
    @BindView(R.id.load_more_grid_view_ptr_frame)
    PtrClassicFrameLayout loadMoreGridViewPtrFrame;
    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;

    private String mKeyWord;

    private ArrayList<GoodsModel> goods = new ArrayList<>();

    private int mPageNum = 1;

    private BaseAdapter mAdapter;

    public static void start(Context context, String keyWord) {
        Intent intent = new Intent(context, SearchGoodsMoreActivity.class);
        intent.putExtra(KEYWORD, keyWord);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_goods_more);
        ButterKnife.bind(this);
        mKeyWord = getIntent().getStringExtra(KEYWORD);
        initViews();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(String.format("\"%1$s\"的搜索", mKeyWord));

        loadMoreGridViewPtrFrame.setLoadingMinTime(1000);
        loadMoreGridViewPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPageNum = 1;
                goods.clear();
                request();
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
                GoodsDetailActivity.start(SearchGoodsMoreActivity.this, goodsModel.goodsId);
            }
        });
        loadMoreGridViewContainer.setAutoLoadMore(false);
        loadMoreGridViewContainer.useDefaultFooter();
        mAdapter = new SearchGoodsListAdapter();
        loadMoreGridView.setAdapter(mAdapter);

        loadMoreGridViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                request();
            }
        });
        loadMoreGridViewPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMoreGridViewPtrFrame.autoRefresh();
            }
        }, 100);

    }

    @OnClick(R.id.imag_left)
    public void onClick() {
        finish();
    }

    private void request() {
        String url = NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_SEARCH_MORE)
                + "?keywords=" + mKeyWord
                + "&pageNum=" + mPageNum
                + "&flag=" + 2;
        try {
            OkHttpUtils.get().url(url).build().execute(new ShihuoStringCallback() {
                @Override
                public void onResponse(ShiHuoResponse response, int id) {
                    loadMoreGridViewPtrFrame.refreshComplete();
                    if (response.code == ShiHuoResponse.SUCCESS
                            && !TextUtils.isEmpty(response.data)) {
                        mPageNum += 1;
                        try {
                            if (!TextUtils.isEmpty(response.resultList)) {
                                JSONArray jsonArray = new JSONArray(response.resultList);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    GoodsModel goodsModel = GoodsModel.parseJsonStr(jsonArray.getJSONObject(i));
                                    goods.add(goodsModel);
                                }
                                loadMoreGridViewContainer.loadMoreFinish(jsonArray.length() > 0, true);
                            }
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        loadMoreGridViewPtrFrame.refreshComplete();
                    }
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    loadMoreGridViewPtrFrame.refreshComplete();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class SearchGoodsListAdapter extends BaseAdapter {

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
                convertView = LayoutInflater.from(SearchGoodsMoreActivity.this).inflate(R.layout.item_goods_shop_home, null);
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
