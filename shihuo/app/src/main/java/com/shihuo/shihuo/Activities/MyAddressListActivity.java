package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.MyAddressModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myAddressModels.clear();
                myAddressModels.addAll(MyAddressModel.getTestDatas(15));
                refreshFrame.refreshComplete();
                mAdapter.notifyDataSetChanged();
                loadMoreListViewContainer.setAutoLoadMore(true);
                loadMoreListViewContainer.loadMoreFinish(myAddressModels.isEmpty(), true);
            }
        }, 2000);
    }

    @Override
    protected void loadMoreData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // load more complete
                myAddressModels.addAll(MyAddressModel.getTestDatas(15));
                refreshFrame.refreshComplete();
                loadMoreListViewContainer.loadMoreFinish(myAddressModels.isEmpty(), true);
                mAdapter.notifyDataSetChanged();
            }
        }, 2000);
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
                convertView = LayoutInflater.from(MyAddressListActivity.this).inflate(R.layout.my_address_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            final MyAddressModel addressModel = (MyAddressModel) getItem(position);
            viewHolder.itemName.setText(addressModel.addressUser);
            viewHolder.itemPhoneNumber.setText(addressModel.addressPhone);
            viewHolder.itemAdd.setText(addressModel.addressDesc);
            viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewAddressActivity.startNewAddressActivity(MyAddressListActivity.this, addressModel, NewAddressActivity.FLAG_EDIT_ADDRESS);
                }
            });
            viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                }
            });
            return convertView;
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
}
