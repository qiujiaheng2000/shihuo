
package com.shihuo.shihuo.fragments;

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

import com.shihuo.shihuo.Activities.ShopHomeActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.CircleListHeaderView;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.Views.loadmore.LoadMoreListViewContainer;
import com.shihuo.shihuo.models.CircleListTopModel;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.SpecificationModel;
import com.shihuo.shihuo.models.StoreDetailModel;
import com.shihuo.shihuo.models.StoreListModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Call;

/**
 * 商品列表frag Created by lishuai on 16/12/13.
 */

public class CircleListFragment extends BaseFragment implements CircleListHeaderView.OnLabelChangeListener {

    public static final String TAG = "CircleListFragment";

    public static CircleListFragment newInstance(GoodsTypeModel model, int position) {
        CircleListFragment f = new CircleListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", model);
        bundle.putInt("position", position);
        f.setArguments(bundle);
        return f;
    }

    @BindView(R.id.rotate_header_list_view)
    ListView rotateHeaderListView;

    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout rotateHeaderListViewFrame;

    @BindView(R.id.load_more_list_view_container)
    LoadMoreListViewContainer loadMoreListViewContainer;

    private MyListViewAdapter mAdapter;

    /**
     * 商圈一级分类
     */
    private GoodsTypeModel mGoodsTypeModel;

    /**
     * 商圈列表header数据
     */
    private CircleListTopModel circleListTopModel;

    /**
     * 商铺列表数据
     */
    private List<StoreDetailModel> storeListModelList = new ArrayList<>();

    private int pageNum;

    private String mCurrentCircleId = "0";//当前选中的商圈id
    private String mCurrentStoreId = "";//当前选中的店铺名称

    private CircleListHeaderView circleListHeaderView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.frag_circle_list, null);
        Bundle bundle = getArguments();
        mGoodsTypeModel = bundle.getParcelable("model");
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        if (mGoodsTypeModel == null)
            return;
        rotateHeaderListViewFrame.setLoadingMinTime(1000);
        rotateHeaderListViewFrame.disableWhenHorizontalMove(true);
        mAdapter = new MyListViewAdapter();
        circleListHeaderView = new CircleListHeaderView(getContext(), this);
        rotateHeaderListView.addHeaderView(circleListHeaderView);
        loadMoreListViewContainer.setAutoLoadMore(false);
        loadMoreListViewContainer.useDefaultFooter();
        rotateHeaderListView.setAdapter(mAdapter);
        rotateHeaderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoreDetailModel storeDetailModel = (StoreDetailModel) parent.getItemAtPosition(position);
                ShopHomeActivity.start(getContext(),storeDetailModel.storeId);
            }
        });

        rotateHeaderListViewFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
//                request(true);
                requestStore(true);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, rotateHeaderListView, header);
            }
        });

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                requestStore(false);
            }
        });
//        rotateHeaderListViewFrame.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                rotateHeaderListViewFrame.autoRefresh();
//            }
//        }, 100);
        request(true);
    }

    private void request(final boolean isRefresh) {
        if (isRefresh) {
            pageNum = 0;
        }
        String url = NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_CIRCLE_LIST_TOP) + "?circleId="
                + mGoodsTypeModel.circleId;
        try {
            OkHttpUtils.get().url(url).build().execute(new ShihuoStringCallback() {
                @Override
                public void onResponse(ShiHuoResponse response, int id) {
                    if (response.code == ShiHuoResponse.SUCCESS
                            && !TextUtils.isEmpty(response.data)) {
                        circleListTopModel = CircleListTopModel.parseStrJson(response.data);
                        SpecificationModel allCircle = new SpecificationModel();
                        allCircle.circleId = 0;
                        allCircle.circleName = "全部";
                        circleListTopModel.shSysStoreCircleList.add(0, allCircle);
                        SpecificationModel allStore = new SpecificationModel();
                        allStore.storeId = "";
                        allStore.storeName = "全部";
                        circleListTopModel.shStoresList.add(0, allStore);
                        circleListHeaderView.bindData(circleListTopModel);
                        requestStore(true);
                    }
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestStore(final boolean isRefresh) {
        if (isRefresh) {
            pageNum = 0;
        }

        String url = NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_CIRCLE_LIST) + "?areaId="
                + mCurrentCircleId + "&storeId=" + mCurrentStoreId + "&pageNum=" + pageNum + "&circleId="+ mGoodsTypeModel.circleId;
        try {
            OkHttpUtils.get().url(url).build().execute(new ShihuoStringCallback() {
                @Override
                public void onResponse(ShiHuoResponse response, int id) {
                    rotateHeaderListViewFrame.refreshComplete();
                    if (response.code == ShiHuoResponse.SUCCESS
                            && !TextUtils.isEmpty(response.data)) {
                        storeListModelList = StoreListModel.parseStrJson(response.resultList);
                        mAdapter.notifyDataSetChanged();
                        rotateHeaderListViewFrame.refreshComplete();
                        pageNum++;
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

    @Override
    public void onCircleChanged(SpecificationModel specificationModel) {
        mCurrentCircleId = String.valueOf(specificationModel.circleId);
        rotateHeaderListViewFrame.autoRefresh();
    }

    @Override
    public void onStoreChanged(SpecificationModel specificationModel) {
        mCurrentStoreId = specificationModel.storeId;
        rotateHeaderListViewFrame.autoRefresh();
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_fav_shops, null);
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
