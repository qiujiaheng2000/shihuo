package com.shihuo.shihuo.Activities.shop.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.Activities.shop.GoodsEditActivity;
import com.shihuo.shihuo.Activities.shop.views.GoBackDialog;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.Views.loadmore.LoadMoreListViewContainer;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsModel;
import com.shihuo.shihuo.models.LoginModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by cm_qiujiaheng on 2016/12/10.
 * 商品管理列表
 */

public class GoodsManagerFragment extends Fragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.load_more_list_view_container)
    LoadMoreListViewContainer loadMoreListViewContainer;
    @BindView(R.id.refresh_frame)
    PtrClassicFrameLayout refreshFrame;

    public static final String KEY_GOODSTYPE = "goodsType";

    private String goodsType;
    private int pageNum = 1;

    public static GoodsManagerFragment newInstance(int goodsType) {

        Bundle args = new Bundle();
        args.putInt(KEY_GOODSTYPE, goodsType);
        GoodsManagerFragment fragment = new GoodsManagerFragment();
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
        View view = inflater.inflate(R.layout.layout_goods_manager_list_layout, null);
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
        refreshFrame.setLoadingMinTime(1000);
        refreshFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 1;
                goods.clear();
                refreshData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listView, header);
            }
        });

        mAdapter = new GoodsManagerAdapter();
        loadMoreListViewContainer.setAutoLoadMore(false);
        loadMoreListViewContainer.useDefaultFooter();
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                loadMoreData();
            }
        });
        refreshFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshFrame.autoRefresh();
            }
        }, 100);
    }

    private void loadMoreData() {
        pageNum++;
        refreshData();
    }

    private void refreshData() {
        final LoginModel userModel = AppShareUitl.getUserInfo(getContext());
        //本店商品分类
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_GOODS_BYTYPE))
                .addParams("typeId", goodsType)
                .addParams("storeId", userModel.storeId)
                .addParams("pageNum", "" + pageNum)
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        refreshFrame.refreshComplete();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                if (!TextUtils.isEmpty(response.resultList)) {
                                    JSONArray jsonArray = new JSONArray(response.resultList);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        GoodsModel goodsTypeModel = GoodsModel.parseJsonStr(jsonArray.getJSONObject(i));
                                        goods.add(goodsTypeModel);
                                    }
                                    mAdapter.notifyDataSetChanged();
                                    loadMoreListViewContainer.setAutoLoadMore(true);
                                    loadMoreListViewContainer.loadMoreFinish(jsonArray.length() > 0, true);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GoodsEditActivity.start(getContext(), goods.get(position));

    }

    public class GoodsManagerAdapter extends BaseAdapter {

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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_goods_manager, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            final GoodsModel goodsModel = (GoodsModel) getItem(position);
            viewHolder.imageView.setImageURI(AppUtils.parse(AliyunHelper.getFullPathByName(goodsModel.picUrl)));//0018ae25-cefa-4260-8f4f-926920c3aa1f.jpeg
            viewHolder.goodsTitle.setText(goodsModel.goodsName);
            viewHolder.goodsNewPrice.setText("￥" + goodsModel.curPrice);
            viewHolder.goodsStock.setText("库存：" + goodsModel.inventor);

            viewHolder.sales.setText("销量：" + goodsModel.salesNum);
            String soldOutStr = goodsModel.isValid == 0 ? "上架" : "下架";
            viewHolder.btnSoldOut.setText(soldOutStr);
            if (goodsModel.isValid == 0) {
                viewHolder.iv_goods_status.setSelected(true);
            } else {
                viewHolder.iv_goods_status.setSelected(false);
            }
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoBackDialog shopTypeChangeDialog = new GoBackDialog(
                            getContext(), R.style.CustomDialog)
                            .setTitle("确认删除该商品吗？");
                    shopTypeChangeDialog.setCustomCallback(new GoBackDialog.CustomCallback() {
                        @Override
                        public void onOkClick(Dialog dialog) {
                            dialog.dismiss();
                            deleteGoods(goodsModel.goodsId);
                        }

                    });
                    shopTypeChangeDialog.show();
                }
            });
            viewHolder.btnSoldOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int isValid = goodsModel.isValid == 0 ? 1 : 0;
                    updateStatus(goodsModel.goodsId, isValid);
                }
            });
            viewHolder.iv_goods_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int isValid = goodsModel.isValid == 0 ? 1 : 0;
                    updateStatus(goodsModel.goodsId, isValid);
                }
            });

            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.imageView)
            SimpleDraweeView imageView;
            @BindView(R.id.goods_title)
            TextView goodsTitle;
            @BindView(R.id.goods_new_price)
            TextView goodsNewPrice;
            @BindView(R.id.goods_stock)
            TextView goodsStock;
            @BindView(R.id.sales)
            TextView sales;
            @BindView(R.id.btn_delete)
            TextView btnDelete;
            @BindView(R.id.btn_sold_out)
            TextView btnSoldOut;
            @BindView(R.id.iv_goods_status)
            ImageView iv_goods_status;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }


    }

    /**
     * 删除商品
     *
     * @param goodsId
     */
    private void deleteGoods(String goodsId) {
        ((BaseActivity) getActivity()).showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("goodsId", goodsId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_GOODS_DELETE) + "?token=" + AppShareUitl.getUserInfo(getActivity()).token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        ((BaseActivity) getActivity()).hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            refreshFrame.autoRefresh();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ((BaseActivity) getActivity()).hideProgressDialog();
                    }
                });
    }

    /**
     * 删除商品
     *
     * @param goodsId
     * @param isValid 1:上架  0：下架
     */
    private void updateStatus(String goodsId, int isValid) {
        ((BaseActivity) getActivity()).showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("goodsId", goodsId);
            params.put("isValid", isValid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_GOODS_UPDATESTATUS) + "?token=" + AppShareUitl.getUserInfo(getActivity()).token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        ((BaseActivity) getActivity()).hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            refreshFrame.autoRefresh();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ((BaseActivity) getActivity()).hideProgressDialog();
                    }
                });
    }
}
