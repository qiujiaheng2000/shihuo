package com.shihuo.shihuo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.MyOrdersListActivity;
import com.shihuo.shihuo.Activities.OrderDetailActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.Views.loadmore.LoadMoreListViewContainer;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.OrderModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
 * 订单列表frag Created by lishuai on 16/12/13.
 */

public class MyOrderListFragment extends BaseFragment {

    public static final String TAG = "MyOrderListFragment";

    public static MyOrderListFragment newInstance(MyOrdersListActivity.OrderType orderType) {
        MyOrderListFragment f = new MyOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("orderType", orderType);
        f.setArguments(bundle);
        return f;
    }

    @BindView(R.id.rotate_header_list_view)
    ListView rotateHeaderListView;

    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout rotateHeaderListViewFrame;

    @BindView(R.id.load_more_list_view_container)
    LoadMoreListViewContainer loadMoreListViewContainer;

    private MyOrderAdapter mAdapter;

    /**
     * 当前订单列表的类型
     */
    private MyOrdersListActivity.OrderType mOrderType;

    /**
     * 商铺列表数据
     */
    private List<OrderModel> orderModelArrayList = new ArrayList<>();

    private int pageNum = 1;

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
        mOrderType = bundle.getParcelable("orderType");
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        if (mOrderType == null)
            return;
        rotateHeaderListViewFrame.setLoadingMinTime(1000);
        mAdapter = new MyOrderAdapter();
        loadMoreListViewContainer.setAutoLoadMore(false);
        loadMoreListViewContainer.useDefaultFooter();
        rotateHeaderListView.setAdapter(mAdapter);
        rotateHeaderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderDetailActivity.start(getContext(), (OrderModel) parent.getItemAtPosition(position), OrderDetailActivity.ORDER_FROM_USER);
            }
        });

        rotateHeaderListViewFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                request(true);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, rotateHeaderListView, header);
            }
        });

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {

            }
        });
        rotateHeaderListViewFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                rotateHeaderListViewFrame.autoRefresh();
            }
        }, 100);
//        request(true);
    }

    private void request(final boolean isRefresh) {
        if (isRefresh) {
            pageNum = 1;
        }
        String url = NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_MYORDERS) + "?token="
                + AppShareUitl.getToken(getContext()) + "&status=" + mOrderType.status + "&pageNum=" + pageNum;
        try {
            OkHttpUtils.get().url(url).build().execute(new ShihuoStringCallback() {
                @Override
                public void onResponse(ShiHuoResponse response, int id) {
                    rotateHeaderListViewFrame.refreshComplete();
                    if (response.code == ShiHuoResponse.SUCCESS
                            && !TextUtils.isEmpty(response.data)) {
                        orderModelArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response.data);
                            jsonObject = jsonObject.getJSONObject("page");
                            if (!TextUtils.isEmpty(jsonObject.getString("resultList"))) {
                                org.json.JSONArray jsonArray = jsonObject.getJSONArray("resultList");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    OrderModel orderModel = OrderModel.fromJson(jsonArray.getJSONObject(i).toString());
                                    orderModelArrayList.add(orderModel);
                                }
                            }
                            //TODO 手动添加测试工具
//                            getTestData();
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
//                        getTestData();
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

    class MyOrderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return orderModelArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return orderModelArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_my_orders, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            OrderModel orderModel = (OrderModel) getItem(position);
            viewHolder.imageView.setImageURI(AppUtils.parse(AliyunHelper.getFullPathByName(orderModel.picUrl)));
            viewHolder.itemTitle.setText(orderModel.goodsName);
            viewHolder.itemDesc.setText(orderModel.goodsDetail);
            viewHolder.orderPrice.setText("" + orderModel.goodsPrice);
            viewHolder.numbs.setText("x" + orderModel.goodsNum);

            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.imageView)
            SimpleDraweeView imageView;

            @BindView(R.id.item_title)
            TextView itemTitle;
            @BindView(R.id.item_desc)
            TextView itemDesc;
            @BindView(R.id.order_price)
            TextView orderPrice;
            @BindView(R.id.numbs)
            TextView numbs;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }

}
