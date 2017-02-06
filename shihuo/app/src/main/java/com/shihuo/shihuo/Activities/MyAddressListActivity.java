package com.shihuo.shihuo.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.shihuo.shihuo.Activities.shop.views.GoBackDialog;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.MyAddressModel;
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
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by cm_qiujiaheng on 2016/11/3.
 * 我的收获地址列表
 */

public class MyAddressListActivity extends AbstractBaseListActivity {

    private ArrayList<MyAddressModel> myAddressModels = new ArrayList<>();

    public static void startMyAddressListActivity(Context context) {
        Intent intent = new Intent(context, MyAddressListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setTitle() {
        title.setText(R.string.myaddress_list);
    }

    @Override
    protected BaseAdapter getCustomAdapter() {
        return new MyAddressAdapter();
    }

    @Override
    public void initViews() {
        super.initViews();
        newAddress.setVisibility(View.VISIBLE);
        newAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewAddressActivity.startNewAddressActivity(MyAddressListActivity.this, null, NewAddressActivity.FLAG_NEW_ADDRESS);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyAddressModel addressModel = (MyAddressModel) parent.getItemAtPosition(position);
                NewAddressActivity.startNewAddressActivity(MyAddressListActivity.this, addressModel, NewAddressActivity.FLAG_EDIT_ADDRESS);
            }
        });
    }

    @Override
    protected void refreshData() {
        mPageNum = 1;
        myAddressModels.clear();
        getAddressList();

    }

    @Override
    protected void loadMoreData() {
        getAddressList();
    }


    private void getAddressList() {
        //当前用户收获地址列表
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_MY_ADDRESS))
                .addParams("token", AppShareUitl.getToken(MyAddressListActivity.this))
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        refreshFrame.refreshComplete();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            mPageNum++;
                            try {
                                if (!TextUtils.isEmpty(response.data)) {
                                    JSONObject jsonObject = new JSONObject(response.data);
                                    if (!TextUtils.isEmpty(jsonObject.getString("dataList"))) {
                                        JSONArray jsonArray = jsonObject.getJSONArray("dataList");
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            MyAddressModel addressModel = MyAddressModel.parseFormJson(jsonArray.getJSONObject(i).toString());
                                            myAddressModels.add(addressModel);
                                        }
                                        mAdapter.notifyDataSetChanged();
                                        loadMoreListViewContainer.setAutoLoadMore(true);
                                        loadMoreListViewContainer.loadMoreFinish(jsonArray.length() > 0, true);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MyAddressListActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        refreshFrame.refreshComplete();
                        AppUtils.showToast(MyAddressListActivity.this, "获取地址列表出错");
                    }
                });
    }

    class MyAddressAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myAddressModels.size();
        }

        @Override
        public Object getItem(int position) {
            return myAddressModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MyAddressListActivity.this).inflate(R.layout.item_my_address, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            final MyAddressModel addressModel = (MyAddressModel) getItem(position);
            viewHolder.itemName.setText(addressModel.receiverName);
            viewHolder.itemPhoneNumber.setText(addressModel.receiverPhoneNum);
            viewHolder.itemAdd.setText(addressModel.addressZone + addressModel.addressDetail);
            viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewAddressActivity.startNewAddressActivity(MyAddressListActivity.this, addressModel, NewAddressActivity.FLAG_EDIT_ADDRESS);
                }
            });

            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoBackDialog shopTypeChangeDialog = new GoBackDialog(
                            MyAddressListActivity.this, R.style.CustomDialog)
                            .setTitle("确认删除吗？");
                    shopTypeChangeDialog.setCustomCallback(new GoBackDialog.CustomCallback() {
                        @Override
                        public void onOkClick(Dialog dialog) {
                            dialog.dismiss();
                            deleteAddress(addressModel);
                        }

                    });
                    shopTypeChangeDialog.show();
                }
            });
            return convertView;
        }

        private void deleteAddress(MyAddressModel addressModel) {
            showProgressDialog();
            JSONObject params = new JSONObject();
            try {
                params.put("addressId", addressModel.addressId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpUtils
                    .postString()
                    .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_DELETE_ADDRESS) + "?token=" + AppShareUitl.getToken(MyAddressListActivity.this))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(params.toString())
                    .build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            hideProgressDialog();
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                AppUtils.showToast(MyAddressListActivity.this, "删除地址成功");
                                refreshFrame.autoRefresh();
                            } else {
                                AppUtils.showToast(MyAddressListActivity.this, response.msg);
                            }
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            hideProgressDialog();
                        }
                    });
        }


        class ViewHolder {
            @BindView(R.id.item_name)
            TextView itemName;
            @BindView(R.id.item_phone_number)
            TextView itemPhoneNumber;
            @BindView(R.id.item_add)
            TextView itemAdd;
            @BindView(R.id.checkbox_addr)
            CheckBox checkboxAddr;
            @BindView(R.id.btn_delete)
            TextView btnDelete;
            @BindView(R.id.btn_edit)
            TextView btnEdit;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshFrame.autoRefresh();
    }
}
