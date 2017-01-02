package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.NumEditTextView;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.Views.loadmore.LoadMoreListViewContainer;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsDetailModel;
import com.shihuo.shihuo.models.LoginModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.Toaster;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import okhttp3.MediaType;

/**
 * Created by cm_qiujiaheng on 2016/12/13.
 * 购物车列表界面
 */

public class ShoppingCarListActivity extends BaseActivity {


    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.txBtnRight)
    TextView txBtnRight;
    @BindView(R.id.checkbox_check_all)
    CheckBox checkboxCheckAll;
    @BindView(R.id.textview_total_price)
    TextView textviewTotalPrice;
    @BindView(R.id.textview_total_num)
    TextView textviewTotalNum;
    @BindView(R.id.rotate_header_list_view)
    ListView rotateHeaderListView;
    @BindView(R.id.load_more_list_view_container)
    LoadMoreListViewContainer loadMoreListViewContainer;
    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout rotateHeaderListViewFrame;
    @BindView(R.id.btn_delete)
    TextView btnDelete;
    @BindView(R.id.layout_settlement)
    RelativeLayout layoutSettlement;

    //是否是编辑模式
    private boolean isEditModel;

    private MyShoppingCarAdapter myShoppingCarAdapter;
    private ArrayList<GoodsDetailModel> goodsDetailModels = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, ShoppingCarListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shoppingcar);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.title_shoppingcar);
        txBtnRight.setText(R.string.edit);
        txBtnRight.setVisibility(View.VISIBLE);

        rotateHeaderListViewFrame.setLoadingMinTime(1000);
        rotateHeaderListViewFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, rotateHeaderListView, header);
            }
        });

        myShoppingCarAdapter = new MyShoppingCarAdapter();

        loadMoreListViewContainer.setAutoLoadMore(false);
        loadMoreListViewContainer.useDefaultFooter();
        rotateHeaderListView.setAdapter(myShoppingCarAdapter);

        rotateHeaderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsDetailActivity.start(ShoppingCarListActivity.this, ((GoodsDetailModel) parent.getItemAtPosition(position)).goodsId);
            }
        });
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {

            }
        });
        rotateHeaderListViewFrame.autoRefresh();

        checkboxCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < goodsDetailModels.size(); i++) {
                        goodsDetailModels.get(i).isChecked = true;
                    }
                    checkboxCheckAll.setText("取消全选");
                } else {
                    for (int i = 0; i < goodsDetailModels.size(); i++) {
                        goodsDetailModels.get(i).isChecked = false;
                    }
                    checkboxCheckAll.setText("全选");
                }
                myShoppingCarAdapter.notifyDataSetChanged();
            }

        });

    }

    private void refreshData() {
        final LoginModel userModel = AppShareUitl.getUserInfo(this);
        //购物车商品列表
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_CART_LIST))
                .addParams("token", userModel.token)
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        rotateHeaderListViewFrame.refreshComplete();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.data);
                                JSONArray jsonArray = jsonObject.getJSONArray("dataList");
                                goodsDetailModels.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    GoodsDetailModel goodsDetailModel = GoodsDetailModel.parseJsonStr(jsonArray.getJSONObject(i));
                                    goodsDetailModels.add(goodsDetailModel);
                                }
                                myShoppingCarAdapter.notifyDataSetChanged();
                                loadMoreListViewContainer.setAutoLoadMore(false);
                                loadMoreListViewContainer.loadMoreFinish(goodsDetailModels.isEmpty(), true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(ShoppingCarListActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        rotateHeaderListViewFrame.refreshComplete();
                    }
                });
    }


    @OnClick({R.id.imag_left, R.id.txBtnRight, R.id.btn_settlement, R.id.btn_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.txBtnRight:
                switchListStatus();
                break;
            case R.id.btn_settlement:
                ConfirmOrdersActivity.start(this, (ArrayList<GoodsDetailModel>) getSelectedGodos());
                break;
            case R.id.btn_delete:
                deleteGoods();
                break;
        }
    }

    /**
     * 切换列表状态
     */
    private void switchListStatus() {
        if (txBtnRight.getText().toString().equals("编辑")) {//点击"编辑"
            txBtnRight.setText("完成");
            isEditModel = true;
//            //全部变成编辑模式
//            for (int i = 0; i < goodsDetailModels.size(); i++) {
//                goodsDetailModels.get(i).isEdit = true;
//            }
            layoutSettlement.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

        } else {//点击"完成"
            txBtnRight.setText("编辑");
            isEditModel = false;
//            //全部变成非编辑模式
//            for (int i = 0; i < goodsDetailModels.size(); i++) {
//                goodsDetailModels.get(i).isEdit = false;
//            }
            layoutSettlement.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);

            modifyGoods();
        }
        myShoppingCarAdapter.notifyDataSetChanged();
    }

    /**
     * 修改购物车的商品
     */
    private void modifyGoods() {
        JSONObject params = null;
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < goodsDetailModels.size(); i++) {
                GoodsDetailModel goodsDetailModel = goodsDetailModels.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("goodsId", goodsDetailModel.goodsId);
                jsonObject.put("specId", goodsDetailModel.specId);
                jsonObject.put("amount", goodsDetailModel.amount);
                jsonArray.put(jsonObject);
            }
            showProgressDialog();
            params = new JSONObject();

            params.put("carts", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_MODIFY_SHOPPINGCAR_GOODS) + "?token=" + AppShareUitl.getUserInfo(this).token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
//                            switchListStatus();
                            rotateHeaderListViewFrame.autoRefresh();

                        } else {
                            Toaster.toastShort("编辑失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                        Toaster.toastShort("编辑失败");
                    }
                });
    }

    /**
     * 删除购物车的商品
     */
    private void deleteGoods() {
        List<GoodsDetailModel> selectedGoods = getSelectedGodos();

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < selectedGoods.size(); i++) {
            jsonArray.put(selectedGoods.get(i).specId);
        }


        showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("specIds", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_DELETE_SHOPPINGCAR_GOODS) + "?token=" + AppShareUitl.getUserInfo(this).token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
//                            switchListStatus();
                            rotateHeaderListViewFrame.autoRefresh();
                        } else {
                            Toaster.toastShort("删除失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                        Toaster.toastShort("删除失败");
                    }
                });
    }

    /**
     * 获取选中的商品
     *
     * @return
     */
    private List<GoodsDetailModel> getSelectedGodos() {
        List<GoodsDetailModel> selectedGoods = new ArrayList<>();
        for (int i = 0; i < goodsDetailModels.size(); i++) {
            GoodsDetailModel goodsDetailModel = goodsDetailModels.get(i);
            if (goodsDetailModel.isChecked) {
                selectedGoods.add(goodsDetailModel);
            }
        }
        return selectedGoods;
    }


    public class MyShoppingCarAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return goodsDetailModels.size();
        }

        @Override
        public Object getItem(int position) {
            return goodsDetailModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ShoppingCarListActivity.this).inflate(R.layout.item_shoppincar, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            final GoodsDetailModel goodsDetailModel = (GoodsDetailModel) getItem(position);
            viewHolder.imageView.setImageURI(AppUtils.parse(AliyunHelper.getFullPathByName(goodsDetailModel.picUrl)));
            viewHolder.itemTitle.setText(goodsDetailModel.goodsName);
            viewHolder.textSpecName.setText(goodsDetailModel.specName);
            viewHolder.textPrice.setText("￥" + goodsDetailModel.curPrice);
            viewHolder.numbs.setText("X" + goodsDetailModel.amount);

            if (goodsDetailModel.isChecked) {
                viewHolder.checkbox.setChecked(true);
            } else {
                viewHolder.checkbox.setChecked(false);
            }

            viewHolder.viewCartNum.setText(String.valueOf(goodsDetailModel.amount));
            if (isEditModel) {//编辑模式
                viewHolder.viewCartNum.setVisibility(View.VISIBLE);
                viewHolder.numbs.setVisibility(View.GONE);
            } else {//非编辑模式
                viewHolder.viewCartNum.setVisibility(View.INVISIBLE);
                viewHolder.numbs.setVisibility(View.VISIBLE);
            }


            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        goodsDetailModel.isChecked = true;
                    } else {
                        goodsDetailModel.isChecked = false;
                    }
//                    resetAllCheckBoxStatus();
                }
            });
            viewHolder.viewCartNum.setOnOrderEditCallBack(new NumEditTextView.OnOrderEditCallBack() {
                @Override
                public void OnCallBack(int num) {
                    goodsDetailModel.amount = num;
                }
            });

            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.checkbox)
            CheckBox checkbox;
            @BindView(R.id.imageView)
            SimpleDraweeView imageView;
            @BindView(R.id.item_title)
            TextView itemTitle;
            @BindView(R.id.text_spec_name)
            TextView textSpecName;
            @BindView(R.id.text_price)
            TextView textPrice;
            @BindView(R.id.numbs)
            TextView numbs;
            @BindView(R.id.view_cart_num)
            NumEditTextView viewCartNum;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }

//    public void resetAllCheckBoxStatus() {
//        boolean haveUnChecked = false;
//        for (int i = 0; i < goodsDetailModels.size(); i++) {
//            if (!goodsDetailModels.get(i).isChecked) {
//                haveUnChecked = true;
//                break;
//            }
//        }
//        if (haveUnChecked) {
//            checkboxCheckAll.setChecked(false);
//            checkboxCheckAll.setText("全选");
//        } else {
//            checkboxCheckAll.setChecked(true);
//            checkboxCheckAll.setText("取消全选");
//        }
//    }
}
