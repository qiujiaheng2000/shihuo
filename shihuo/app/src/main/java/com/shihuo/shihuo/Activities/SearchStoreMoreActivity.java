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
import android.widget.ListView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.Views.loadmore.LoadMoreListViewContainer;
import com.shihuo.shihuo.models.StoreDetailModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Call;

/**
 * Created by lishuai on 16/12/26.
 * 搜索结果的店铺的 "更多"列表
 */

public class SearchStoreMoreActivity extends BaseActivity {

    private static final String KEYWORD = "keyWord";
    @BindView(R.id.rotate_header_list_view)
    ListView rotateHeaderListView;
    @BindView(R.id.load_more_list_view_container)
    LoadMoreListViewContainer loadMoreListViewContainer;
    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout rotateHeaderListViewFrame;
    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;

    private String mKeyWord;
    private int pageNum = 1;
    /**
     * 商铺列表
     */
    public List<StoreDetailModel> storeListModelList = new ArrayList<>();

    private BaseAdapter mAdapter;

    public static void start(Context context, String keyWord) {
        Intent intent = new Intent(context, SearchStoreMoreActivity.class);
        intent.putExtra(KEYWORD, keyWord);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_store_more);
        ButterKnife.bind(this);
        mKeyWord = getIntent().getStringExtra(KEYWORD);
        initViews();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);

        title.setText(String.format("\"%1$s\"的搜索", mKeyWord));

        rotateHeaderListViewFrame.setLoadingMinTime(1000);
        mAdapter = new MyListViewAdapter();
        loadMoreListViewContainer.setAutoLoadMore(true);
        loadMoreListViewContainer.useDefaultFooter();
        rotateHeaderListView.setAdapter(mAdapter);
        rotateHeaderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoreDetailModel storeDetailModel = (StoreDetailModel) parent.getItemAtPosition(position);
                ShopHomeActivity.start(SearchStoreMoreActivity.this, storeDetailModel.storeId);
            }
        });

        rotateHeaderListViewFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 1;
                storeListModelList.clear();
                request();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, rotateHeaderListView, header);
            }

        });
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                request();
            }
        });
        rotateHeaderListViewFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                rotateHeaderListViewFrame.autoRefresh();
            }
        }, 100);

    }

    private void request() {
        String url = NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_SEARCH_MORE)
                + "?keywords=" + mKeyWord
                + "&pageNum=" + pageNum
                + "&flag=" + 1;
        try {
            OkHttpUtils.get().url(url).build().execute(new ShihuoStringCallback() {
                @Override
                public void onResponse(ShiHuoResponse response, int id) {
                    rotateHeaderListViewFrame.refreshComplete();
                    if (response.code == ShiHuoResponse.SUCCESS
                            && !TextUtils.isEmpty(response.data)) {
                        pageNum += 1;
                        try {
                            if (!TextUtils.isEmpty(response.resultList)) {
                                JSONArray jsonArray = new JSONArray(response.resultList);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    StoreDetailModel storeDetailModel = StoreDetailModel.parseJsonStr(jsonArray.getJSONObject(i));
                                    storeListModelList.add(storeDetailModel);
                                }
                                loadMoreListViewContainer.loadMoreFinish(jsonArray.length() > 0, true);
                            }
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        rotateHeaderListViewFrame.refreshComplete();
                    }
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    rotateHeaderListViewFrame.refreshComplete();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.imag_left)
    public void onClick() {
        finish();
    }

    public class MyListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return storeListModelList.size();
        }

        @Override
        public Object getItem(int position) {
            return storeListModelList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(SearchStoreMoreActivity.this).inflate(R.layout.item_fav_shops, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            StoreDetailModel storeDetailModel = (StoreDetailModel) getItem(position);
            viewHolder.itemTitle.setText(TextUtils.isEmpty(storeDetailModel.storeName) ? ""
                    : storeDetailModel.storeName);
            viewHolder.itemDesc.setText(TextUtils.isEmpty(storeDetailModel.storeDetail) ? ""
                    : storeDetailModel.storeDetail);
            viewHolder.prefixNumbs.setText("销量：" + storeDetailModel.orderNum);
//            viewHolder.numbs.setText("" + storeDetailModel.orderNum);
            viewHolder.shopAdd.setText(TextUtils.isEmpty(storeDetailModel.circleName) ? ""
                    : storeDetailModel.circleName);
            viewHolder.imageView.setImageURI(AppUtils.parse(AliyunHelper.getFullPathByName(storeDetailModel.storeLogoPicUrl)));//0018ae25-cefa-4260-8f4f-926920c3aa1f.jpeg
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.imageView)
            ImageView imageView;
            @BindView(R.id.item_title)
            TextView itemTitle;
            @BindView(R.id.item_desc)
            TextView itemDesc;
            @BindView(R.id.prefix_numbs)
            TextView prefixNumbs;
            @BindView(R.id.numbs)
            TextView numbs;
            @BindView(R.id.shop_add)
            TextView shopAdd;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
