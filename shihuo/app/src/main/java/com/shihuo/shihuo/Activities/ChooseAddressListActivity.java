package com.shihuo.shihuo.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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
 * 选择收货地址列表
 */

public class ChooseAddressListActivity extends AbstractBaseListActivity {

    public static final int REQUEST_CODE = 1;
    public static final int RESULT_CODE = 2;
    public static final String RESULT_ADDRESS = "resultAddress";

    private ArrayList<MyAddressModel> myAddressModels = new ArrayList<>();

    public static void startChooseAddressListActivityForResult(Activity context) {
        Intent intent = new Intent(context, ChooseAddressListActivity.class);
        context.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void setTitle() {
        title.setText("选择收货地址");
    }

    @Override
    protected BaseAdapter getCustomAdapter() {
        return new MyAddressAdapter();
    }

    @Override
    public void initViews() {
        super.initViews();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyAddressModel addressModel = (MyAddressModel) parent.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra(RESULT_ADDRESS, addressModel);
                setResult(RESULT_CODE, intent);
                finish();
            }
        });
    }

    @Override
    protected void refreshData() {
        getAddressList();
    }

    @Override
    protected void loadMoreData() {

    }


    private void getAddressList() {
        //当前用户收获地址列表
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_MY_ADDRESS))
                .addParams("token", AppShareUitl.getToken(ChooseAddressListActivity.this))
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        refreshFrame.refreshComplete();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.data);
                                JSONArray jsonArray = jsonObject.getJSONArray("dataList");
                                myAddressModels.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    MyAddressModel addressModel = MyAddressModel.parseFormJson(jsonArray.getJSONObject(i).toString());
                                    myAddressModels.add(addressModel);
                                }
                                mAdapter.notifyDataSetChanged();
                                loadMoreListViewContainer.setAutoLoadMore(true);
                                loadMoreListViewContainer.loadMoreFinish(myAddressModels.isEmpty(), true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(ChooseAddressListActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        refreshFrame.refreshComplete();
                        AppUtils.showToast(ChooseAddressListActivity.this, "获取地址列表出错");
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
                convertView = LayoutInflater.from(ChooseAddressListActivity.this).inflate(R.layout.item_choose_address, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            final MyAddressModel addressModel = (MyAddressModel) getItem(position);
            viewHolder.itemName.setText(addressModel.receiverName);
            viewHolder.itemPhoneNumber.setText(addressModel.receiverPhoneNum);
            viewHolder.itemAdd.setText(addressModel.addressDetail);

            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.item_name)
            TextView itemName;
            @BindView(R.id.item_phone_number)
            TextView itemPhoneNumber;
            @BindView(R.id.item_add)
            TextView itemAdd;

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
