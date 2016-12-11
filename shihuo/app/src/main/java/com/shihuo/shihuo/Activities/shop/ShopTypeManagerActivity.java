package com.shihuo.shihuo.Activities.shop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.Activities.AbstractBaseListActivity;
import com.shihuo.shihuo.Activities.shop.views.ShopTypeChangeDialog;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.Toaster;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 * 店铺分类管理界面
 */
public class ShopTypeManagerActivity extends AbstractBaseListActivity {

    private ArrayList<GoodsTypeModel> shopTypeModels = new ArrayList<>();


    public static void start(Context context) {
        Intent intent = new Intent(context, ShopTypeManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initViews() {
        super.initViews();
        txBtnRight.setVisibility(View.VISIBLE);
        txBtnRight.setText("");
        txBtnRight.setBackgroundResource(R.mipmap.icon_add);
        txBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopTypeChangeDialog shopTypeChangeDialog = new ShopTypeChangeDialog(ShopTypeManagerActivity.this, R.style.CustomDialog)
                        .setTitle("添加分类")
                        .setHintText("请输入商品分类名称");
                shopTypeChangeDialog.setCustomCallback(new ShopTypeChangeDialog.CustomCallback() {
                    @Override
                    public void onOkClick(Dialog dialog, String goodsTypeName) {
                        dialog.dismiss();
                        addShopGoodsType(dialog, goodsTypeName);
                    }
                });
                shopTypeChangeDialog.show();
            }
        });
    }

    private void addShopGoodsType(final Dialog dialog, String goodsTypeName) {
        showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("storeId", AppShareUitl.getUserInfo(this).storeId);
            params.put("typeName", goodsTypeName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_ADD_GOODSTYPELIST) + "?token=" + AppShareUitl.getUserInfo(this).token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            refreshFrame.autoRefresh();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                    }
                });
    }

    @Override
    public void setTitle() {
        title.setText(R.string.shop_type_manager);
    }

    @Override
    protected BaseAdapter getCustomAdapter() {
        return new ShopTypeManagerAdapter();
    }

    @Override
    protected void refreshData() {
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_GOODSTYPELIST))
                .addParams("token", AppShareUitl.getToken(this))
                .addParams("storeId", AppShareUitl.getUserInfo(this).storeId)
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                org.json.JSONArray jsonArray = new JSONObject(response.data).getJSONArray("dataList");
                                shopTypeModels.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    GoodsTypeModel goodsTypeModel = GoodsTypeModel.parseJsonStr(jsonArray.getJSONObject(i));
                                    shopTypeModels.add(goodsTypeModel);
                                }
                                mAdapter.notifyDataSetChanged();
                                refreshFrame.refreshComplete();
                                loadMoreListViewContainer.setAutoLoadMore(false);
                                loadMoreListViewContainer.loadMoreFinish(shopTypeModels.isEmpty(), true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            refreshFrame.refreshComplete();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        refreshFrame.refreshComplete();
                    }
                });
    }

    @Override
    protected void loadMoreData() {

    }

    public class ShopTypeManagerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return shopTypeModels.size();
        }

        @Override
        public Object getItem(int position) {
            return shopTypeModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return shopTypeModels.get(position).typeId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ShopTypeManagerActivity.this).inflate(R.layout.item_shoptype_manager, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            final GoodsTypeModel shopTypeModel = (GoodsTypeModel) getItem(position);

            viewHolder.textShoptypeName.setText(shopTypeModel.typeName);
            viewHolder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteType(shopTypeModel.typeId);
                }
            });
            viewHolder.imageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopTypeChangeDialog shopTypeChangeDialog = new ShopTypeChangeDialog(ShopTypeManagerActivity.this, R.style.CustomDialog)
                            .setTitle("修改分类")
                            .setHintText("请输入商品分类名称");
                    shopTypeChangeDialog.setCustomCallback(new ShopTypeChangeDialog.CustomCallback() {
                        @Override
                        public void onOkClick(Dialog dialog, String typeName) {
                            dialog.dismiss();
                            updateType(shopTypeModel.typeId,typeName);
                        }
                    });
                    shopTypeChangeDialog.show();
                }
            });
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.text_shoptype_name)
            TextView textShoptypeName;
            @BindView(R.id.image_delete)
            ImageView imageDelete;
            @BindView(R.id.image_edit)
            ImageView imageEdit;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    private void deleteType(int typeId) {
        showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("typeId", typeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_GOODS_TYPE_DELETE) + "?token=" + AppShareUitl.getUserInfo(this).token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            refreshFrame.autoRefresh();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                    }
                });
    }

    private void updateType(int typeId, String typeName) {
        showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("typeId", typeId);
            params.put("typeName", typeName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_GOODS_TYPE_UPDATE) + "?token=" + AppShareUitl.getUserInfo(this).token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            refreshFrame.autoRefresh();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                    }
                });
    }


}
